/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;
import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_SHORT;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;
import android.os.Build;

import com.miviclin.droidengine2d.BuildConfig;
import com.miviclin.droidengine2d.graphics.GLDebugger;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.BlendingOptions;
import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Base class for all batch renderers that render rectangles.<br>
 * Allows rendering a batch of rectangles, each rectangle having its own translation, rotation and scale, in one draw
 * call.
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> Material
 */
public abstract class RectangleBatchRenderer<M extends Material> extends GraphicsBatchRenderer<M> {

	private int verticesDataStride;

	private ShortBuffer indexBuffer;
	private FloatBuffer vertexBuffer;
	private FloatBuffer mvpIndexBuffer;

	private RectangleBatchGeometry geometry;

	/**
	 * Creates a new RectangleBatchRenderer.
	 * 
	 * @param verticesDataStride Data stride of the vertices.
	 * @param batchCapacity Maximum size of this GraphicsBatch.
	 */
	public RectangleBatchRenderer(int verticesDataStride, int batchCapacity) {
		super(batchCapacity);
		this.verticesDataStride = verticesDataStride;
		this.geometry = new RectangleBatchGeometry(32, false, true);
	}

	@Override
	protected void beginDraw() {
		ShaderProgram shaderProgram = getShaderProgram();
		if (!shaderProgram.isLinked()) {
			shaderProgram.compileAndLink();
		}
		shaderProgram.use();
	}

	@Override
	protected void endDraw() {
		if (getBatchSize() > 0) {
			drawBatch();
		}
	}

	/**
	 * Initializes this batch renderer.
	 */
	public void initialize() {
		setupIndices();
		setupVerticesData();

		indexBuffer = ByteBuffer.allocateDirect(getBatchCapacity() * 6 * SIZE_OF_SHORT)
				.order(ByteOrder.nativeOrder())
				.asShortBuffer();
		copyIndicesToIndexBuffer();

		vertexBuffer = ByteBuffer.allocateDirect(getBatchCapacity() * 4 * verticesDataStride * SIZE_OF_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		copyGeometryToVertexBuffer(getBatchCapacity());
		setVertexBufferLimit(getBatchCapacity());

		mvpIndexBuffer = ByteBuffer.allocateDirect(geometry.getMvpIndices().length * SIZE_OF_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		mvpIndexBuffer.put(geometry.getMvpIndices()).flip();
	}

	/**
	 * Removes all elements of the vertex buffer and then copies the current geometry to the vertex buffer.<br>
	 * After this method is called, {@link #setVertexBufferLimit()} must be called.
	 * 
	 * @param batchSize Number of elements of the current batch to be copied to the vertex buffer.
	 */
	protected abstract void copyGeometryToVertexBuffer(int batchSize);

	/**
	 * Sets the limit of the vertex buffer.<br>
	 * This method must be called after {@link #copyGeometryToVertexBuffer()} is called.
	 * 
	 * @param batchSize Number of elements of the current batch copied to the vertex buffer.
	 */
	protected void setVertexBufferLimit(int batchSize) {
		vertexBuffer.position(batchSize * verticesDataStride * 4).flip();
	}

	/**
	 * Copies the indices of the geometry to the index buffer.
	 */
	protected void copyIndicesToIndexBuffer() {
		int numIndices = getBatchCapacity() * 6;
		for (int i = 0; i < numIndices; i++) {
			indexBuffer.put(geometry.getIndex(i));
		}
		indexBuffer.flip();
	}

	/**
	 * Sets up the array of indices of the geometry of the mesh used to render this batch.
	 */
	protected void setupIndices() {
		int numIndices = getBatchCapacity() * 6;
		for (int i = 0, j = 0; i < numIndices; i += 6, j += 4) {
			geometry.addIndex((short) (j + 0));
			geometry.addIndex((short) (j + 1));
			geometry.addIndex((short) (j + 2));
			geometry.addIndex((short) (j + 2));
			geometry.addIndex((short) (j + 3));
			geometry.addIndex((short) (j + 0));
		}
	}

	/**
	 * Sets up the vertices of the geometry of the mesh used to render this batch.<br>
	 * The vertices of each rectangle must be defined in the following order:<br>
	 * 1. Bottom-left.<br>
	 * 2. Bottom-right.<br>
	 * 3. Top-right.<br>
	 * 4. Top-left.
	 */
	protected abstract void setupVerticesData();

	/**
	 * Transforms the rectangle located at the specified index of the batch.
	 * 
	 * @param index Index where the rectangle whose transform we want to update is located.
	 * @param position Position.
	 * @param scale Scale.
	 * @param origin Origin of the rectangle (value between 0.0f and 1.0f).
	 * @param rotation Rotation angle around the origin.
	 * @param camera Camera.
	 * 
	 * @see RectangleBatchRenderer#setupVerticesData()
	 */
	protected void updateTransform(int index, Vector2 position, Vector2 scale, Vector2 origin, float rotation,
			Camera camera) {

		if (origin.getX() < 0 || origin.getX() > 1 || origin.getY() < 0 || origin.getY() > 1) {
			throw new IllegalArgumentException("The origin coordinates must be in the [0..1] interval.");
		}

		int i = index * 4;
		float modelOriginX = 0.0f - (origin.getX() - 0.5f);
		float modelOriginY = 0.0f - (origin.getY() - 0.5f);
		// Bottom-Left
		geometry.getVertex(i + 0).set(modelOriginX - 0.5f, modelOriginY - 0.5f, 0.0f);
		// Bottom-Right
		geometry.getVertex(i + 1).set(modelOriginX + 0.5f, modelOriginY - 0.5f, 0.0f);
		// Top-Right
		geometry.getVertex(i + 2).set(modelOriginX + 0.5f, modelOriginY + 0.5f, 0.0f);
		// Top-Left
		geometry.getVertex(i + 3).set(modelOriginX - 0.5f, modelOriginY + 0.5f, 0.0f);
		// Update MVP matrix
		geometry.updateMVPMatrix(index, position, scale, rotation, camera);
	}

	/**
	 * Prepares the geometry to be sent to the shader program.
	 * 
	 * @param batchSize Number of elements in the batch.
	 */
	protected abstract void setupVertexShaderVariables(int batchSize);

	/**
	 * Prepares the batch to be rendered.
	 */
	protected void prepareDrawBatch() {
		int batchSize = getBatchSize();
		copyGeometryToVertexBuffer(batchSize);
		setVertexBufferLimit(batchSize);
		mvpIndexBuffer.limit(batchSize * 4).position(0);
		indexBuffer.limit(batchSize * 6).position(0);
		setupVertexShaderVariables(batchSize);
	}

	/**
	 * Renders all the elements that are currently in this batch in one draw call.
	 */
	protected void drawBatch() {
		prepareDrawBatch();

		BlendingOptions blendingOptions = getCurrentBatchBlendingOptions();
		GLES20.glBlendFunc(blendingOptions.getSourceFactor(), blendingOptions.getDestinationFactor());

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
			GLES20.glBlendEquation(blendingOptions.getBlendEquationMode());
		}

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexBuffer.limit(), GLES20.GL_UNSIGNED_SHORT, indexBuffer);

		if (BuildConfig.DEBUG) {
			GLDebugger.getInstance().incrementNumDrawCallsInCurrentFrame();
		}

		getCurrentBatchBlendingOptions().copy(getNextBatchBlendingOptions());
		setForceDraw(false);
		resetBatchSize();

		GLDebugger.getInstance().passiveCheckGLError();
	}

	/**
	 * Adds a rectangle to this batch.<br>
	 * If the batch was full, it will be rendered in one draw call and it will be left empty. Then, the specified
	 * rectangle will be added to this batch.
	 * 
	 * @param position Position.
	 * @param scale Scale.
	 * @param origin Origin of the rectangle (value between 0.0f and 1.0f).
	 * @param rotation Rotation angle around the origin.
	 * @param camera Camera.
	 */
	public abstract void draw(Vector2 position, Vector2 scale, Vector2 origin, float rotation, Camera camera);

	/**
	 * Returns the stride of the data of the vertices of this batch.
	 * 
	 * @return stride
	 */
	protected int getVerticesDataStride() {
		return verticesDataStride;
	}

	/**
	 * Returns the stride of the data of the vertices of this batch in bytes.
	 * 
	 * @return Stride
	 */
	public int getVerticesDataStrideBytes() {
		return verticesDataStride * SIZE_OF_FLOAT;
	}

	/**
	 * Sets the stride of the data of the vertices of this batch.
	 * 
	 * @param verticesDataStride stride
	 */
	protected void setVerticesDataStride(int verticesDataStride) {
		this.verticesDataStride = verticesDataStride;
	}

	/**
	 * Returns the index buffer of this batch.
	 * 
	 * @return index buffer
	 */
	public ShortBuffer getIndexBuffer() {
		return indexBuffer;
	}

	/**
	 * Returns the vertex buffer of this batch.
	 * 
	 * @return vertex buffer
	 */
	public FloatBuffer getVertexBuffer() {
		return vertexBuffer;
	}

	/**
	 * Returns the MVP index buffer of this batch.
	 * 
	 * @return MVP index buffer
	 */
	public FloatBuffer getMvpIndexBuffer() {
		return mvpIndexBuffer;
	}

	/**
	 * Returns the Geometry of this batch of this batch.
	 * 
	 * @return Geometry
	 */
	public RectangleBatchGeometry getGeometry() {
		return geometry;
	}

	/**
	 * Sets the Geometry of this batch of this batch.
	 * 
	 * @param geometry RectangleBatchGeometry.
	 */
	public void setGeometry(RectangleBatchGeometry geometry) {
		this.geometry = geometry;
	}
}
