package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.TextureHSVMaterial;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderVariables;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * Clase base de la que deben heredar los renderers de mallas que representen batches de figuras rectangulares cuyo material sea
 * TextureHSVMaterial
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> TextureHSVMaterial
 */
public class TextureHSVMaterialBatchRenderer<M extends TextureHSVMaterial> extends TextureMaterialBatchRendererBase<M> {
	
	private int vertexColorOffset;
	
	/**
	 * Constructor
	 * 
	 * @param context Context en el que se ejecuta el juego
	 */
	public TextureHSVMaterialBatchRenderer(Context context) {
		super(9, context);
		this.vertexColorOffset = 5;
		setGeometry(new RectangleBatchGeometry(getBatchCapacity(), true, true));
	}
	
	@Override
	public void setupShaderProgram() {
		String vertexShaderSource = "" +
				"uniform mat4 " + ShaderVariables.U_MVP_MATRIX + "[32];\n" +
				"attribute float " + ShaderVariables.A_MVP_MATRIX_INDEX + ";\n" +
				"attribute vec4 " + ShaderVariables.A_POSITION + ";\n" +
				"attribute vec2 " + ShaderVariables.A_TEXTURE_COORD + ";\n" +
				"attribute vec4 " + ShaderVariables.A_COLOR + ";\n" +
				"varying vec2 " + ShaderVariables.V_TEXTURE_COORD + ";\n" +
				"varying vec4 " + ShaderVariables.V_COLOR + ";\n" +
				"void main() {\n" +
				"    gl_Position = " + ShaderVariables.U_MVP_MATRIX + "[int(" + ShaderVariables.A_MVP_MATRIX_INDEX + ")] * " + ShaderVariables.A_POSITION + ";\n" +
				"    " + ShaderVariables.V_TEXTURE_COORD + " = " + ShaderVariables.A_TEXTURE_COORD + ";\n" +
				"    " + ShaderVariables.V_COLOR + " = " + ShaderVariables.A_COLOR + ";\n" +
				"}";
		
		String fragmentShaderSource = "" +
				"precision mediump float;\n" +
				"varying vec2 " + ShaderVariables.V_TEXTURE_COORD + ";\n" +
				"varying vec4 " + ShaderVariables.V_COLOR + ";\n" +
				"uniform sampler2D sTexture;\n" +
				"vec3 RGBtoHSV(vec3 c) {\n" +
				"    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\n" +
				"    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\n" +
				"    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\n" +
				"    float d = q.x - min(q.w, q.y);\n" +
				"    float e = 1.0e-10;\n" +
				"    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);\n" +
				"}\n" +
				"vec3 HSVtoRGB(vec3 c) {\n" +
				"    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);\n" +
				"    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);\n" +
				"    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);\n" +
				"}\n" +
				"void main() {\n" +
				"    vec4 textureColor = texture2D(sTexture, " + ShaderVariables.V_TEXTURE_COORD + ");\n" +
				"    vec3 fragRGB = textureColor.rgb;\n" +
				"    vec3 fragHSV = RGBtoHSV(fragRGB);\n" +
				"    fragHSV.x += " + ShaderVariables.V_COLOR + ".x / 360.0;\n" +
				"    fragHSV.yz *= " + ShaderVariables.V_COLOR + ".yz;\n" +
				"    fragHSV.xyz = mod(fragHSV.xyz, 1.0);\n" +
				"    fragRGB = HSVtoRGB(fragHSV);\n" +
				"    gl_FragColor = vec4(fragRGB, textureColor.w);\n" +
				"}";
		
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(ShaderVariables.A_MVP_MATRIX_INDEX);
		attributes.add(ShaderVariables.A_POSITION);
		attributes.add(ShaderVariables.A_TEXTURE_COORD);
		attributes.add(ShaderVariables.A_COLOR);
		
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
		shaderProgram.setAttribute(ShaderVariables.A_COLOR, 4, strideBytes, getVertexBuffer(), vertexColorOffset);
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
			vertexBuffer.put(color.getH());
			vertexBuffer.put(color.getS());
			vertexBuffer.put(color.getV());
			vertexBuffer.put(color.getA());
		}
	}
	
	@Override
	public void draw(Vector2 position, Vector2 scale, Vector2 origin, float rotation, Camera camera) {
		checkInBeginEndPair();
		TextureHSVMaterial material = getCurrentMaterial();
		setupSprite(material.getTextureRegion(), position, scale, origin, rotation, camera);
		setupHSV(material.getHOffset(), material.getSMulti(), material.getVMulti());
		incrementBatchSize();
	}
	
	/**
	 * Define el color del siguiente sprite en el batch
	 * 
	 * @param color Color
	 */
	private void setupHSV(float hOffset, float sMulti, float vMulti) {
		int spriteOffset = getBatchSize() * 4;
		int limit = spriteOffset + 4;
		for (int i = spriteOffset; i < limit; i++) {
			getGeometry().getColor(i).setHSV(hOffset, sMulti, vMulti);
		}
	}
	
}
