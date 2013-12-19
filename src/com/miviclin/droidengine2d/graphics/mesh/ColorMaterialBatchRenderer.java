package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.ColorMaterial;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderVars;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * ColorMaterialBatchRenderer allows rendering batches of up to 32 rectangles with ColorMaterial in one draw call. Each
 * rectangle has its own translation, rotation and scale.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ColorMaterialBatchRenderer<M extends ColorMaterial> extends RectangleBatchRenderer<M> {

	private int vertexPositionOffset;
	private int vertexColorOffset;

	/**
	 * Creates a new ColorMaterialBatchRenderer.
	 */
	public ColorMaterialBatchRenderer() {
		super(7, 32);
		setVerticesDataStride(7);
		this.vertexPositionOffset = 0;
		this.vertexColorOffset = 3;
		setGeometry(new RectangleBatchGeometry(32, true, false));
	}

	@Override
	public void setupShaderProgram() {

		// @formatter:off
		
		String vertexShaderSource = "" +
				"uniform mat4 " + ShaderVars.U_MVP_MATRIX + "[32];\n" +
				"attribute float " + ShaderVars.A_MVP_MATRIX_INDEX + ";\n" +
				"attribute vec4 " + ShaderVars.A_POSITION + ";\n" +
				"attribute vec4 " + ShaderVars.A_COLOR + ";\n" +
				"varying vec4 " + ShaderVars.V_COLOR + ";\n" +
				"void main() {\n" +
				"    gl_Position = " + ShaderVars.U_MVP_MATRIX + "[int(" + ShaderVars.A_MVP_MATRIX_INDEX + ")] * " + ShaderVars.A_POSITION + ";\n" +
				"    " + ShaderVars.V_COLOR + " = " + ShaderVars.A_COLOR + ";\n" +
				"}";

		String fragmentShaderSource = "" +
				"precision mediump float;\n" +
				"varying vec4 " + ShaderVars.V_COLOR + ";\n" +
				"void main() {\n" +
				"    gl_FragColor = " + ShaderVars.V_COLOR + ";\n" +
				"}";
		
		// @formatter:on

		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(ShaderVars.A_MVP_MATRIX_INDEX);
		attributes.add(ShaderVars.A_POSITION);
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
		shaderProgram.setAttribute(ShaderVars.A_POSITION, 3, strideBytes, getVertexBuffer(), vertexPositionOffset);
		shaderProgram.setAttribute(ShaderVars.A_COLOR, 4, strideBytes, getVertexBuffer(), vertexColorOffset);
	}

	@Override
	protected void copyGeometryToVertexBuffer(int batchSize) {
		FloatBuffer vertexBuffer = getVertexBuffer();
		vertexBuffer.clear();
		int nVertices = getBatchCapacity() * 4;
		Vector3 position;
		Color color;
		for (int i = 0; i < nVertices; i++) {
			position = getGeometry().getVertex(i);
			vertexBuffer.put(position.getX());
			vertexBuffer.put(position.getY());
			vertexBuffer.put(position.getZ());

			color = getGeometry().getColor(i);
			vertexBuffer.put(color.getR());
			vertexBuffer.put(color.getG());
			vertexBuffer.put(color.getB());
			vertexBuffer.put(color.getA());
		}
	}

	@Override
	protected void setupVerticesData() {
		RectangleBatchGeometry geometry = getGeometry();
		int nVertices = getBatchCapacity() * 4;
		for (int i = 0; i < nVertices; i++) {
			// Bottom-Left
			geometry.addVertex(new Vector3(-0.5f, -0.5f, 0.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			// Bottom-Right
			geometry.addVertex(new Vector3(0.5f, -0.5f, 0.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			// Top-Right
			geometry.addVertex(new Vector3(0.5f, 0.5f, 0.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			// Top-Left
			geometry.addVertex(new Vector3(-0.5f, 0.5f, 0.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		}
	}

	@Override
	public void draw(Vector2 position, Vector2 scale, Vector2 origin, float rotation, Camera camera) {
		checkInBeginEndPair();
		ColorMaterial material = getCurrentMaterial();
		setupRectangularShape(material, position, scale, origin, rotation, camera);
		incrementBatchSize();
	}

	/**
	 * Adds the specified rectangle to this batch.<br>
	 * If the batch was full, it will be rendered in one draw call and it will be left empty. Then, the specified
	 * rectangle will be added to this batch.
	 * 
	 * @param material ColorMaterial
	 * @param position Posicion
	 * @param scale Escala
	 * @param origin Origen de la figura (debe ser un valor entre 0.0 y 1.0)
	 * @param rotation Angulo de rotacion sobre el centro
	 * @param camera Camara
	 */
	protected void setupRectangularShape(ColorMaterial material, Vector2 position, Vector2 scale, Vector2 origin,
			float rotation, Camera camera) {

		if ((getBatchSize() > 0) && (getBatchSize() == getBatchCapacity() || isForceDraw())) {
			drawBatch();
		}
		setSpriteVerticesColorData(material.getColor());
		updateTransform(getBatchSize(), position, scale, origin, rotation, camera);
	}

	/**
	 * Sets the color of the vertices of the last rectangle added to this batch.
	 * 
	 * @param color Color.
	 */
	protected void setSpriteVerticesColorData(Color color) {
		int i = getBatchSize() * 4;
		RectangleBatchGeometry geometry = getGeometry();
		// Bottom-Left
		geometry.getColor(i + 0).set(color);
		// Bottom-Right
		geometry.getColor(i + 1).set(color);
		// Top-Right
		geometry.getColor(i + 2).set(color);
		// Top-Left
		geometry.getColor(i + 3).set(color);
	}

	/**
	 * Vertex position offset in the vertex buffer.
	 * 
	 * @return Offset
	 */
	public int getVertexPositionOffset() {
		return vertexPositionOffset;
	}

	/**
	 * Color offset in the vertex buffer.
	 * 
	 * @return Offset
	 */
	public int getVertexColorOffset() {
		return vertexColorOffset;
	}

}
