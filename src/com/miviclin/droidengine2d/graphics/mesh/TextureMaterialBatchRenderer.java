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

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.TextureMaterial;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderVars;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * TextureMaterialBatchRenderer allows rendering batches of up to 32 rectangles with TextureMaterial in one draw call.
 * Each rectangle has its own translation, rotation and scale.
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> TextureMaterial
 */
public class TextureMaterialBatchRenderer<M extends TextureMaterial> extends TextureMaterialBatchRendererBase<M> {

	/**
	 * Creates a new TextureMaterialBatchRenderer.
	 * 
	 * @param context Context.
	 */
	public TextureMaterialBatchRenderer(Context context) {
		super(5, context);
	}

	@Override
	public void setupShaderProgram() {

		// @formatter:off
		
		String vertexShaderSource = "" +
				"uniform mat4 " + ShaderVars.U_MVP_MATRIX + "[32];\n" +
				"attribute float " + ShaderVars.A_MVP_MATRIX_INDEX + ";\n" +
				"attribute vec4 " + ShaderVars.A_POSITION + ";\n" +
				"attribute vec2 " + ShaderVars.A_TEXTURE_COORD + ";\n" +
				"varying vec2 " + ShaderVars.V_TEXTURE_COORD + ";\n" +
				"void main() {\n" +
				"    gl_Position = " + ShaderVars.U_MVP_MATRIX + "[int(" + ShaderVars.A_MVP_MATRIX_INDEX + ")] * " + ShaderVars.A_POSITION + ";\n" +
				"    " + ShaderVars.V_TEXTURE_COORD + " = " + ShaderVars.A_TEXTURE_COORD + ";\n" +
				"}";
		
		String fragmentShaderSource = "" +
				"precision mediump float;\n" +
				"varying vec2 " + ShaderVars.V_TEXTURE_COORD + ";\n" +
				"uniform sampler2D sTexture;\n" +
				"void main() {\n" +
				"    gl_FragColor = texture2D(sTexture, " + ShaderVars.V_TEXTURE_COORD + ");\n" +
				"}";
		
		// @formatter:on

		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(ShaderVars.A_MVP_MATRIX_INDEX);
		attributes.add(ShaderVars.A_POSITION);
		attributes.add(ShaderVars.A_TEXTURE_COORD);

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
	}

	@Override
	protected void setupVerticesData() {
		RectangleBatchGeometry geometry = getGeometry();
		int nVertices = getBatchCapacity() * 4;
		for (int i = 0; i < nVertices; i++) {
			// Bottom-Left
			geometry.addVertex(new Vector3(-0.5f, -0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(0.0f, 1.0f));
			// Bottom-Right
			geometry.addVertex(new Vector3(0.5f, -0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(1.0f, 1.0f));
			// Top-Right
			geometry.addVertex(new Vector3(0.5f, 0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(1.0f, 0.0f));
			// Top-Left
			geometry.addVertex(new Vector3(-0.5f, 0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(0.0f, 0.0f));
		}
	}

	@Override
	protected void copyGeometryToVertexBuffer(int batchSize) {
		FloatBuffer vertexBuffer = getVertexBuffer();
		vertexBuffer.clear();
		int nVertices = getBatchCapacity() * 4;
		Vector3 position;
		Vector2 textureUV;
		for (int i = 0; i < nVertices; i++) {
			position = getGeometry().getVertex(i);
			vertexBuffer.put(position.getX());
			vertexBuffer.put(position.getY());
			vertexBuffer.put(position.getZ());

			textureUV = getGeometry().getTextureUV(i);
			vertexBuffer.put(textureUV.getX());
			vertexBuffer.put(textureUV.getY());
		}
	}

	@Override
	public void draw(Vector2 position, Vector2 scale, Vector2 origin, float rotation, Camera camera) {
		checkInBeginEndPair();
		TextureMaterial material = getCurrentMaterial();
		setupTexturedRectangle(material.getTextureRegion(), position, scale, origin, rotation, camera);
		incrementBatchSize();
	}

}
