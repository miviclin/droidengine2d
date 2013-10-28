package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;
import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_SHORT;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

import com.miviclin.droidengine2d.BuildConfig;
import com.miviclin.droidengine2d.graphics.GLDebugger;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.BlendingOptions;
import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Clase base de la que deben heredar los renderers de mallas que representen batches de figuras rectangulares.<br>
 * Permite renderizar en una llamada hasta 32 sprites con transformaciones (traslacion, rotacion y escala) distintas.
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> Material
 */
public abstract class RectangleBatchRenderer<M extends Material> extends GraphicsBatch<M> {

	private int verticesDataStride;

	private ShortBuffer indexBuffer;
	private FloatBuffer vertexBuffer;
	private FloatBuffer mvpIndexBuffer;

	private RectangleBatchGeometry geometry;

	/**
	 * Crea un PositionTextureSpriteBatch
	 * 
	 * @param verticesDataStride Stride de los datos de los vertices
	 * @param shaderProgram ShaderProgram
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
	 * Inicializa. Llamar tras crear el objeto.
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
	 * Vacia el vertex buffer y copia los datos de la geometria al buffer. Llamar a {@link #setVertexBufferLimit()}
	 * despues de este metodo.
	 * 
	 * @param batchSize Numero de elementos del batch que se copiaran
	 */
	protected abstract void copyGeometryToVertexBuffer(int batchSize);

	/**
	 * Asigna el limite del vertex buffer. Llamar a este metodo despues de {@link #copyGeometryToVertexBuffer()}
	 * 
	 * @param batchSize Numero de elementos del batch que se copiaran
	 */
	protected void setVertexBufferLimit(int batchSize) {
		vertexBuffer.position(batchSize * verticesDataStride * 4).flip();
	}

	/**
	 * Copia los datos de la geometria al buffer
	 */
	protected void copyIndicesToIndexBuffer() {
		int numIndices = getBatchCapacity() * 6;
		for (int i = 0; i < numIndices; i++) {
			indexBuffer.put(geometry.getIndex(i));
		}
		indexBuffer.flip();
	}

	/**
	 * Inicializa el array de indices de los vertices que definen la geometria de la malla de sprites
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
	 * Inicializa el array de vertices que definen la geometria de la malla del batch.<br>
	 * El orden de inicializacion de los vertices debe ser:<br>
	 * 1. Bottom-left<br>
	 * 2. Bottom-right<br>
	 * 3. Top-right<br>
	 * 4. Top-left
	 */
	protected abstract void setupVerticesData();

	/**
	 * Transforma la figura situada en el indice (mvpIndex) especificado con los datos especificados.<br>
	 * Para que este metodo funcione como se espera, el orden de inicializacion de los vertices debe ser:<br>
	 * 1. Bottom-left<br>
	 * 2. Bottom-right<br>
	 * 3. Top-right<br>
	 * 4. Top-left
	 * 
	 * @param index Indice de la figura a actualizar
	 * @param position Posicion
	 * @param scale Escala
	 * @param origin Origen de la figura (debe ser un valor entre 0.0 y 1.0)
	 * @param rotation Angulo de rotacion sobre el centro
	 * @param camera Camara
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
	 * Prepara los datos de la geometria para ser enviados a los shaders
	 * 
	 * @param batchSize Numero de elementos en el batch
	 */
	protected abstract void setupVertexShaderVariables(int batchSize);

	/**
	 * Prepara el batch para ser renderizado
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
	 * Renderiza todos los sprites del batch en 1 sola llamada
	 */
	protected void drawBatch() {
		prepareDrawBatch();

		BlendingOptions blendingOptions = getCurrentBatchBlendingOptions();
		GLES20.glBlendFunc(blendingOptions.getSourceFactor(), blendingOptions.getDestinationFactor());
		GLES20.glBlendEquation(blendingOptions.getBlendEquationMode());

		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexBuffer.limit(), GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		GLDebugger.getInstance().passiveCheckGLError();

		if (BuildConfig.DEBUG) {
			GLDebugger.getInstance().incrementNumDrawCallsFrame();
		}

		getCurrentBatchBlendingOptions().copy(getNextBatchBlendingOptions());
		setForceDraw(false);
		resetBatchSize();
	}

	/**
	 * Agrega el Sprite al batch.<br>
	 * En caso de que el batch estuviera lleno, se renderiza en 1 sola llamada y se vacia para agregar el nuevo sprite.
	 * 
	 * @param position Posicion
	 * @param scale Escala
	 * @param origin Origen de la figura (debe ser un valor entre 0.0 y 1.0)
	 * @param rotation Angulo de rotacion sobre el centro
	 * @param camera Camara
	 */
	public abstract void draw(Vector2 position, Vector2 scale, Vector2 origin, float rotation, Camera camera);

	/**
	 * Devuelve el stride
	 * 
	 * @return stride
	 */
	protected int getVerticesDataStride() {
		return verticesDataStride;
	}

	/**
	 * Stride de los datos de los vertices en bytes
	 * 
	 * @return Stride
	 */
	public int getVerticesDataStrideBytes() {
		return verticesDataStride * SIZE_OF_FLOAT;
	}

	/**
	 * Asigna el stride del buffer que contiene los datos de los vertices
	 * 
	 * @param verticesDataStride stride
	 */
	protected void setVerticesDataStride(int verticesDataStride) {
		this.verticesDataStride = verticesDataStride;
	}

	/**
	 * Devuelve el index buffer
	 * 
	 * @return index buffer
	 */
	public ShortBuffer getIndexBuffer() {
		return indexBuffer;
	}

	/**
	 * Devuelve el vertex buffer
	 * 
	 * @return vertex buffer
	 */
	public FloatBuffer getVertexBuffer() {
		return vertexBuffer;
	}

	/**
	 * Devuelve el MVP index buffer
	 * 
	 * @return MVP index buffer
	 */
	public FloatBuffer getMvpIndexBuffer() {
		return mvpIndexBuffer;
	}

	/**
	 * Devuelve la geometria
	 * 
	 * @return Geometry
	 */
	public RectangleBatchGeometry getGeometry() {
		return geometry;
	}

	/**
	 * Asigna la geometria
	 * 
	 * @param geometry RectangleBatchGeometry
	 */
	public void setGeometry(RectangleBatchGeometry geometry) {
		this.geometry = geometry;
	}
}
