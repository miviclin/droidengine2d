package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.TransparentTextureMaterial;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderVars;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * Clase base de la que deben heredar los renderers de mallas que representen batches de figuras rectangulares cuyo
 * material sea TransparentTextureMaterial
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> TransparentTextureMaterial
 */
public class TransparentTextureMaterialBatchRenderer<M extends TransparentTextureMaterial>
		extends TextureMaterialBatchRendererBase<M> {

	private int vertexOpacityOffset;

	/**
	 * Constructor
	 * 
	 * @param context Context en el que se ejecuta el juego
	 */
	public TransparentTextureMaterialBatchRenderer(Context context) {
		super(6, context);
		this.vertexOpacityOffset = 5;
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
				"attribute float " + ShaderVars.A_OPACITY + ";\n" +
				"varying vec2 " + ShaderVars.V_TEXTURE_COORD + ";\n" +
				"varying float " + ShaderVars.V_OPACITY + ";\n" +
				"void main() {\n" +
				"    gl_Position = " + ShaderVars.U_MVP_MATRIX + "[int(" + ShaderVars.A_MVP_MATRIX_INDEX + ")] * " + ShaderVars.A_POSITION + ";\n" +
				"    " + ShaderVars.V_TEXTURE_COORD + " = " + ShaderVars.A_TEXTURE_COORD + ";\n" +
				"    " + ShaderVars.V_OPACITY + " = " + ShaderVars.A_OPACITY + ";\n" +
				"}";
		
		String fragmentShaderSource = "" +
				"precision mediump float;\n" +
				"varying vec2 " + ShaderVars.V_TEXTURE_COORD + ";\n" +
				"varying float " + ShaderVars.V_OPACITY + ";\n" +
				"uniform sampler2D sTexture;\n" +
				"void main() {\n" +
				"    gl_FragColor = texture2D(sTexture, " + ShaderVars.V_TEXTURE_COORD + ");\n" +
				"    gl_FragColor.w *= " + ShaderVars.V_OPACITY + ";\n" +
				"}";
		
		// @formatter:on

		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(ShaderVars.A_MVP_MATRIX_INDEX);
		attributes.add(ShaderVars.A_POSITION);
		attributes.add(ShaderVars.A_TEXTURE_COORD);
		attributes.add(ShaderVars.A_OPACITY);

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
		shaderProgram.setAttribute(ShaderVars.A_OPACITY, 1, strideBytes, getVertexBuffer(), vertexOpacityOffset);
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
			vertexBuffer.put(color.getA());
		}
	}

	@Override
	public void draw(Vector2 position, Vector2 scale, Vector2 origin, float rotation, Camera camera) {
		checkInBeginEndPair();
		TransparentTextureMaterial material = getCurrentMaterial();
		setupSprite(material.getTextureRegion(), position, scale, origin, rotation, camera);
		setupOpacity(material.getOpacity());
		incrementBatchSize();
	}

	/**
	 * Define la opacidad del siguiente sprite en el batch
	 * 
	 * @param opacity Opacidad
	 */
	private void setupOpacity(float opacity) {
		int spriteOffset = getBatchSize() * 4;
		int limit = spriteOffset + 4;
		for (int i = spriteOffset; i < limit; i++) {
			getGeometry().getColor(i).setA(opacity);
		}
	}

}
