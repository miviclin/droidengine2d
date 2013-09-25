package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.TextureMaterial;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderVariables;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * Clase base de la que deben heredar los renderers de mallas que representen batches de figuras rectangulares cuyo material sea
 * TextureMaterial
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> TextureMaterial
 */
public class TextureMaterialBatchRenderer<M extends TextureMaterial> extends TextureMaterialBatchRendererBase<M> {
	
	/**
	 * Constructor
	 * 
	 * @param context Context en el que se ejecuta el juego
	 */
	public TextureMaterialBatchRenderer(Context context) {
		super(5, context);
	}
	
	@Override
	public void setupShaderProgram() {
		String vertexShaderSource = "" +
				"uniform mat4 " + ShaderVariables.U_MVP_MATRIX + "[32];\n" +
				"attribute float " + ShaderVariables.A_MVP_MATRIX_INDEX + ";\n" +
				"attribute vec4 " + ShaderVariables.A_POSITION + ";\n" +
				"attribute vec2 " + ShaderVariables.A_TEXTURE_COORD + ";\n" +
				"varying vec2 " + ShaderVariables.V_TEXTURE_COORD + ";\n" +
				"void main() {\n" +
				"    gl_Position = " + ShaderVariables.U_MVP_MATRIX + "[int(" + ShaderVariables.A_MVP_MATRIX_INDEX + ")] * " + ShaderVariables.A_POSITION + ";\n" +
				"    " + ShaderVariables.V_TEXTURE_COORD + " = " + ShaderVariables.A_TEXTURE_COORD + ";\n" +
				"}";
		
		String fragmentShaderSource = "" +
				"precision mediump float;\n" +
				"varying vec2 " + ShaderVariables.V_TEXTURE_COORD + ";\n" +
				"uniform sampler2D sTexture;\n" +
				"void main() {\n" +
				"    gl_FragColor = texture2D(sTexture, " + ShaderVariables.V_TEXTURE_COORD + ");\n" +
				"}";
		
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(ShaderVariables.A_MVP_MATRIX_INDEX);
		attributes.add(ShaderVariables.A_POSITION);
		attributes.add(ShaderVariables.A_TEXTURE_COORD);
		
		ArrayList<String> uniforms = new ArrayList<String>();
		uniforms.add(ShaderVariables.U_MVP_MATRIX);
		
		getShaderProgram().setShaders(vertexShaderSource, fragmentShaderSource, attributes, uniforms);
	}
	
	@Override
	protected void setupVertexShaderVariables(int batchSize) {
		int strideBytes = getVerticesDataStrideBytes();
		ShaderProgram shaderProgram = getShaderProgram();
		shaderProgram.setUniformMatrix4fv(ShaderVariables.U_MVP_MATRIX, batchSize, getGeometry().getMvpMatrices(), 0);
		shaderProgram.setAttribute(ShaderVariables.A_MVP_MATRIX_INDEX, 1, SIZE_OF_FLOAT, getMvpIndexBuffer(), 0);
		shaderProgram.setAttribute(ShaderVariables.A_POSITION, 3, strideBytes, getVertexBuffer(), getVertexPositionOffset());
		shaderProgram.setAttribute(ShaderVariables.A_TEXTURE_COORD, 2, strideBytes, getVertexBuffer(), getVertexUVOffset());
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
		setupSprite(material.getTextureRegion(), position, scale, origin, rotation, camera);
		incrementBatchSize();
	}
	
}
