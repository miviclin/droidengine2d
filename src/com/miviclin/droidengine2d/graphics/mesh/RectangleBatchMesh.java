package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;
import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_SHORT;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

import com.miviclin.droidengine2d.graphics.GLDebugger;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.math.Vector2;

public abstract class RectangleBatchMesh<M extends Material> extends GraphicsBatch<M> {
	
	protected static final int BATCH_CAPACITY = 32;
	
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
	public RectangleBatchMesh(int verticesDataStride, ShaderProgram shaderProgram) {
		super(shaderProgram);
		this.verticesDataStride = verticesDataStride;
		this.geometry = new RectangleBatchGeometry(BATCH_CAPACITY, false, true);
	}
	
	/**
	 * Inicializa. Llamar tras crear el objeto.
	 */
	public void initialize() {
		setupIndices();
		setupVerticesData();
		
		indexBuffer = ByteBuffer.allocateDirect(BATCH_CAPACITY * 6 * SIZE_OF_SHORT)
				.order(ByteOrder.nativeOrder())
				.asShortBuffer();
		copyIndicesToIndexBuffer();
		
		vertexBuffer = ByteBuffer.allocateDirect(BATCH_CAPACITY * 4 * verticesDataStride * SIZE_OF_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		copyGeometryToVertexBuffer(BATCH_CAPACITY);
		setVertexBufferLimit(BATCH_CAPACITY);
		
		mvpIndexBuffer = ByteBuffer.allocateDirect(geometry.getMvpIndices().length * SIZE_OF_FLOAT)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		mvpIndexBuffer.put(geometry.getMvpIndices()).flip();
	}
	
	/**
	 * Vacia el vertex buffer y copia los datos de la geometria al buffer. Llamar a {@link #setVertexBufferLimit()} despues de este metodo.
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
		int numIndices = BATCH_CAPACITY * 6;
		for (int i = 0; i < numIndices; i++) {
			indexBuffer.put(geometry.getIndex(i));
		}
		indexBuffer.flip();
	}
	
	/**
	 * Inicializa el array de indices de los vertices que definen la geometria de la malla de sprites
	 */
	protected void setupIndices() {
		int numIndices = BATCH_CAPACITY * 6;
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
	 * Inicializa el array de vertices que definen la geometria de la malla del batch
	 */
	protected abstract void setupVerticesData();
	
	/**
	 * Transforma la matriz situada en el indice especificado con los datos especificados
	 * 
	 * @param mvpIndex Indice de la matrix MVP a actualizar
	 * @param position Posicion
	 * @param dimensions Dimensiones
	 * @param center Centro de rotacion (debe ser un valor entre 0.0 y 1.0)
	 * @param rotation Angulo de rotacion sobre el centro
	 * @param rotationPoint Punto externo de rotacion
	 * @param rotationAroundPoint Angulo de rotacion sobre el punto externo
	 * @param camera Camara
	 */
	protected void updateMVPMatrix(int mvpIndex, Vector2 position, Dimensions2D dimensions, Vector2 center, float rotation, Vector2 rotationPoint, float rotationAroundPoint, Camera camera) {
		geometry.updateMVPMatrix(mvpIndex, position, dimensions, center, rotation, rotationPoint, rotationAroundPoint, camera);
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
	protected void prepareDrawBatch(int batchSize) {
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
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexBuffer.limit(), GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Agrega el Sprite al batch.<br>
	 * En caso de que el batch estuviera lleno, se renderiza en 1 sola llamada y se vacia para agregar el nuevo sprite.
	 * 
	 * @param position Posicion
	 * @param dimensions Dimensiones
	 * @param center Centro de rotacion (debe ser un valor entre 0.0 y 1.0)
	 * @param rotation Angulo de rotacion sobre el centro
	 * @param rotationPoint Punto externo de rotacion
	 * @param rotationAroundPoint Angulo de rotacion sobre el punto externo
	 * @param camera Camara
	 */
	public abstract void draw(Vector2 position, Dimensions2D dimensions, Vector2 center, float rotation, Vector2 rotationPoint, float rotationAroundPoint, Camera camera);
	
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
