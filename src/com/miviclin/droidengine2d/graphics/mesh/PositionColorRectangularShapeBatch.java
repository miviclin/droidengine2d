package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;

import java.nio.FloatBuffer;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.ColorMaterial;
import com.miviclin.droidengine2d.graphics.shader.PositionColorBatchShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * RectangularShapeBatch que permite renderizar en una llamada hasta 32 RectangularShape con transformaciones (traslacion, rotacion y
 * escala) distintas.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class PositionColorRectangularShapeBatch<M extends ColorMaterial> extends RectangleBatchMesh<M> {
	
	protected static final int BATCH_CAPACITY = 32;
	
	private int vertexPositionOffset;
	private int vertexColorOffset;
	private int batchSize;
	
	/**
	 * Crea un PositionColorRectangularShapeBatch
	 */
	public PositionColorRectangularShapeBatch() {
		super(7, new PositionColorBatchShaderProgram());
		setVerticesDataStride(7);
		this.vertexPositionOffset = 0;
		this.vertexColorOffset = 3;
		this.batchSize = 0;
		setGeometry(new RectangleBatchGeometry(BATCH_CAPACITY, true, false));
	}
	
	@Override
	public void beginDraw() {
		ShaderProgram shaderProgram = getShaderProgram();
		if (!shaderProgram.isLinked()) {
			shaderProgram.link();
		}
		shaderProgram.use();
	}
	
	@Override
	public void endDraw() {
		if (batchSize > 0) {
			prepareDrawBatch(batchSize);
			drawBatch();
			batchSize = 0;
		}
	}
	
	@Override
	public PositionColorBatchShaderProgram getShaderProgram() {
		return (PositionColorBatchShaderProgram) super.getShaderProgram();
	}
	
	@Override
	protected void copyGeometryToVertexBuffer(int batchSize) {
		FloatBuffer vertexBuffer = getVertexBuffer();
		vertexBuffer.clear();
		int nVertices = BATCH_CAPACITY * 4;
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
		int nVertices = BATCH_CAPACITY * 4;
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
	protected void setupVertexShaderVariables(int batchSize) {
		PositionColorBatchShaderProgram shaderProgram = getShaderProgram();
		shaderProgram.specifyMVPMatrices(getGeometry().getMvpMatrices(), 0, batchSize);
		shaderProgram.specifyVerticesPosition(getVertexBuffer(), vertexPositionOffset, 3, getVerticesDataStrideBytes());
		shaderProgram.specifyVerticesColors(getVertexBuffer(), vertexColorOffset, 4, getVerticesDataStrideBytes());
		shaderProgram.specifyVerticesMVPIndices(getMvpIndexBuffer(), 0, SIZE_OF_FLOAT);
	}
	
	
	@Override
	public void draw(Vector2 position, Dimensions2D dimensions, Vector2 origin, float rotation, Camera camera) {
		checkInBeginEndPair();
		ColorMaterial material = getCurrentMaterial();
		setupRectangularShape(material, position, dimensions, origin, rotation, camera);
		batchSize++;
	}
	
	/**
	 * Agrega la figura rectangular especificada al batch.<br>
	 * En caso de que el batch estuviera lleno, se renderiza en 1 sola llamada y se vacia para agregar la nueva figura.
	 * 
	 * @param material ColorMaterial
	 * @param position Posicion
	 * @param dimensions Dimensiones
	 * @param origin Origen de la figura (debe ser un valor entre 0.0 y 1.0)
	 * @param rotation Angulo de rotacion sobre el centro
	 * @param camera Camara
	 */
	protected void setupRectangularShape(ColorMaterial material, Vector2 position, Dimensions2D dimensions, Vector2 origin, float rotation, Camera camera) {
		if (batchSize == BATCH_CAPACITY) {
			prepareDrawBatch(batchSize);
			drawBatch();
			batchSize = 0;
		}
		setSpriteVerticesColorData(material.getColor());
		updateTransform(batchSize, position, dimensions, origin, rotation, camera);
	}
	
	/**
	 * Actualiza los colores del rectangulo actual en la geometria de la malla del batch.
	 * 
	 * @param color Color que se asignara a los vertices del rectangulo actual
	 */
	protected void setSpriteVerticesColorData(Color color) {
		int i = batchSize * 4;
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
	 * Devuelve el numero de sprites que hay en el batch
	 * 
	 * @return Numero de sprites en el batch
	 */
	public int getBatchSize() {
		return batchSize;
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
