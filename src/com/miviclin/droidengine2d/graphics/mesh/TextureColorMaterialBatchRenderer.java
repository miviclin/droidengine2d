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

import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.TextureColorMaterial;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderVars;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * TextureColorMaterialBatchRenderer allows rendering batches of up to 32 rectangles with TextureColorMaterial in one
 * draw call. Each rectangle has its own translation, rotation and scale.
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> TextureColorMaterial
 */
public class TextureColorMaterialBatchRenderer<M extends TextureColorMaterial> extends
		TextureMaterialBatchRendererBase<M> {

	private int vertexColorOffset;

	/**
	 * Creates a new TextureColorMaterialBatchRenderer.
	 * 
	 * @param context Context.
	 */
	public TextureColorMaterialBatchRenderer(Context context) {
		super(9, context);
		this.vertexColorOffset = 5;
		setGeometry(new RectangleBatchGeometry(getBatchCapacity(), true, true));
	}

	@Override
	public void setupShaderProgram() {

		// @formatter:off
		
		String vertexShaderSource = "" +
				"uniform mat4 " + ShaderVars.U_MVP_MATRIX + "[32];\n" +
				"attribute float " + ShaderVars.A_MVP_MATRIX_INDEX + ";\n" +
				"attribute vec4 " + ShaderVars.A_POSITION + ";\n" +
				"attribute vec2 " + ShaderVars.A_TEXTURE_COORD + ";\n" +
				"attribute vec4 " + ShaderVars.A_COLOR + ";\n" +
				"varying vec2 " + ShaderVars.V_TEXTURE_COORD + ";\n" +
				"varying vec4 " + ShaderVars.V_COLOR + ";\n" +
				"void main() {\n" +
				"    gl_Position = " + ShaderVars.U_MVP_MATRIX + "[int(" + ShaderVars.A_MVP_MATRIX_INDEX + ")] * " + ShaderVars.A_POSITION + ";\n" +
				"    " + ShaderVars.V_TEXTURE_COORD + " = " + ShaderVars.A_TEXTURE_COORD + ";\n" +
				"    " + ShaderVars.V_COLOR + " = " + ShaderVars.A_COLOR + ";\n" +
				"}";
		
		String fragmentShaderSource = "" +
				"precision mediump float;\n" +
				"varying vec2 " + ShaderVars.V_TEXTURE_COORD + ";\n" +
				"varying vec4 " + ShaderVars.V_COLOR + ";\n" +
				"uniform sampler2D sTexture;\n" +
				"void main() {\n" +
				"    gl_FragColor = texture2D(sTexture, " + ShaderVars.V_TEXTURE_COORD + ") * " + ShaderVars.V_COLOR + ";\n" +
				"}";
		
		// @formatter:on

		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(ShaderVars.A_MVP_MATRIX_INDEX);
		attributes.add(ShaderVars.A_POSITION);
		attributes.add(ShaderVars.A_TEXTURE_COORD);
		attributes.add(ShaderVars.A_COLOR);

		ArrayList<String> uniforms = new ArrayList<String>();
		uniforms.add(ShaderVars.U_MVP_MATRIX);

		getShaderProgram().setShaders(vertexShaderSource, fragmentShaderSource, attributes, uniforms);
	}

	@Override
	protected void setupVertexShaderVariables(int batchSize) {
		int strideBytes = getVerticesDataStrideBytes();
		ShaderProgram shaderProgram = getShaderProgram();
		shaderProgram.setUniformMatrix4fv(ShaderVars.U_MVP_MATRIX, batchSize, getGeometry().getMvpMatrices(), 0);
		shaderProgram.setAttribute(ShaderVars.A_MVP_MATRIX_INDEX, 1, SIZE_OF_FLOAT, getMvpIndexBuffer(), 0);
		shaderProgram.setAttribute(ShaderVars.A_POSITION, 3, strideBytes, getVertexBuffer(), getVertexPositionOffset());
		shaderProgram.setAttribute(ShaderVars.A_TEXTURE_COORD, 2, strideBytes, getVertexBuffer(), getVertexUVOffset());
		shaderProgram.setAttribute(ShaderVars.A_COLOR, 4, strideBytes, getVertexBuffer(), vertexColorOffset);
	}

	@Override
	protected void setupVerticesData() {
		RectangleBatchGeometry geometry = getGeometry();
		int nVertices = getBatchCapacity() * 4;
		for (int i = 0; i < nVertices; i++) {
			// Bottom-Left
			geometry.addVertex(new Vector3(-0.5f, -0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(0.0f, 1.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			// Bottom-Right
			geometry.addVertex(new Vector3(0.5f, -0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(1.0f, 1.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			// Top-Right
			geometry.addVertex(new Vector3(0.5f, 0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(1.0f, 0.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			// Top-Left
			geometry.addVertex(new Vector3(-0.5f, 0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(0.0f, 0.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		}
	}

	@Override
	protected void copyGeometryToVertexBuffer(int batchSize) {
		FloatBuffer vertexBuffer = getVertexBuffer();
		vertexBuffer.clear();
		int nVertices = getBatchCapacity() * 4;
		Vector3 position;
		Vector2 textureUV;
		Color color;
		for (int i = 0; i < nVertices; i++) {
			position = getGeometry().getVertex(i);
			vertexBuffer.put(position.getX());
			vertexBuffer.put(position.getY());
			vertexBuffer.put(position.getZ());

			textureUV = getGeometry().getTextureUV(i);
			vertexBuffer.put(textureUV.getX());
			vertexBuffer.put(textureUV.getY());

			color = getGeometry().getColor(i);
			vertexBuffer.put(color.getR());
			vertexBuffer.put(color.getG());
			vertexBuffer.put(color.getB());
			vertexBuffer.put(color.getA());
		}
	}

	@Override
	public void draw(Vector2 position, Vector2 scale, Vector2 origin, float rotation, Camera camera) {
		checkInBeginEndPair();
		TextureColorMaterial material = getCurrentMaterial();
		setupTexturedRectangle(material.getTextureRegion(), position, scale, origin, rotation, camera);
		setupColor(material.getColor());
		incrementBatchSize();
	}

	/**
	 * Sets up the color of the vertices of the last rectangle added to this batch.
	 * 
	 * @param color Color.
	 */
	private void setupColor(Color color) {
		int spriteOffset = getBatchSize() * 4;
		int limit = spriteOffset + 4;
		for (int i = spriteOffset; i < limit; i++) {
			getGeometry().getColor(i).set(color);
		}
	}

}
