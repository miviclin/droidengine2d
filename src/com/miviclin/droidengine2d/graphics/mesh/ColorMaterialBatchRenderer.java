package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.ColorMaterial;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderVariables;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * RectangleBatchRenderer que permite renderizar en una llamada hasta 32 RectangularShape con transformaciones (traslacion, rotacion y
 * escala) distintas.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ColorMaterialBatchRenderer<M extends ColorMaterial> extends RectangleBatchRenderer<M> {
	
	private int vertexPositionOffset;
	private int vertexColorOffset;
	
	/**
	 * Crea un PositionColorRectangularShapeBatch
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
		String vertexShaderSource = "" +
				"uniform mat4 " + ShaderVariables.U_MVP_MATRIX + "[32];\n" +
				"attribute float " + ShaderVariables.A_MVP_MATRIX_INDEX + ";\n" +
				"attribute vec4 " + ShaderVariables.A_POSITION + ";\n" +
				"attribute vec4 " + ShaderVariables.A_COLOR + ";\n" +
				"varying vec4 " + ShaderVariables.V_COLOR + ";\n" +
				"void main() {\n" +
				"    gl_Position = " + ShaderVariables.U_MVP_MATRIX + "[int(" + ShaderVariables.A_MVP_MATRIX_INDEX + ")] * " + ShaderVariables.A_POSITION + ";\n" +
				"    " + ShaderVariables.V_COLOR + " = " + ShaderVariables.A_COLOR + ";\n" +
				"}";
		
		String fragmentShaderSource = "" +
				"precision mediump float;\n" +
				"varying vec4 " + ShaderVariables.V_COLOR + ";\n" +
				"void main() {\n" +
				"    gl_FragColor = " + ShaderVariables.V_COLOR + ";\n" +
				"}";
		
		ArrayList<String> attributes = new ArrayList<String>();
		attributes.add(ShaderVariables.A_MVP_MATRIX_INDEX);
		attributes.add(ShaderVariables.A_POSITION);
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
		shaderProgram.setAttribute(ShaderVariables.A_POSITION, 3, strideBytes, getVertexBuffer(), vertexPositionOffset);
		shaderProgram.setAttribute(ShaderVariables.A_COLOR, 4, strideBytes, getVertexBuffer(), vertexColorOffset);
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
	 * Agrega la figura rectangular especificada al batch.<br>
	 * En caso de que el batch estuviera lleno, se renderiza en 1 sola llamada y se vacia para agregar la nueva figura.
	 * 
	 * @param material ColorMaterial
	 * @param position Posicion
	 * @param scale Escala
	 * @param origin Origen de la figura (debe ser un valor entre 0.0 y 1.0)
	 * @param rotation Angulo de rotacion sobre el centro
	 * @param camera Camara
	 */
	protected void setupRectangularShape(ColorMaterial material, Vector2 position, Vector2 scale, Vector2 origin, float rotation, Camera camera) {
		if ((getBatchSize() > 0) && (getBatchSize() == getBatchCapacity() || isForceDraw())) {
			drawBatch();
		}
		setSpriteVerticesColorData(material.getColor());
		updateTransform(getBatchSize(), position, scale, origin, rotation, camera);
	}
	
	/**
	 * Actualiza los colores del rectangulo actual en la geometria de la malla del batch.
	 * 
	 * @param color Color que se asignara a los vertices del rectangulo actual
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
	 * Offset de la posicion en los datos de un vertice
	 * 
	 * @return Offset
	 */
	public int getVertexPositionOffset() {
		return vertexPositionOffset;
	}
	
	/**
	 * Offset del color en los datos de un vertice
	 * 
	 * @return Offset
	 */
	public int getVertexColorOffset() {
		return vertexColorOffset;
	}
	
}
