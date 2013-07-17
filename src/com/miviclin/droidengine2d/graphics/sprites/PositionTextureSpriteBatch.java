package com.miviclin.droidengine2d.graphics.sprites;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.miviclin.droidengine2d.graphics.GLDebugger;
import com.miviclin.droidengine2d.graphics.Geometry;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shaders.PositionTextureBatchShaderProgram;
import com.miviclin.droidengine2d.graphics.shaders.ShaderProgram;
import com.miviclin.droidengine2d.graphics.textures.Texture;
import com.miviclin.droidengine2d.util.TransformUtilities;
import com.miviclin.droidengine2d.util.math.Matrix4;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * SpriteBatch que permite renderizar en una llamada hasta 32 sprites con transformaciones (traslacion, rotacion y escala) distintas.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class PositionTextureSpriteBatch implements SpriteBatch {
	
	protected static final int BATCH_CAPACITY = 32;
	
	protected static final int FLOAT_SIZE_BYTES = 4;
	protected static final int SHORT_SIZE_BYTES = 4;
	
	protected static final int VERTICES_DATA_STRIDE = 5;
	protected static final int VERTICES_DATA_STRIDE_BYTES = VERTICES_DATA_STRIDE * FLOAT_SIZE_BYTES;
	protected static final int VERTICES_DATA_POS_OFFSET = 0;
	protected static final int VERTICES_DATA_UV_OFFSET = 3;
	
	protected final float[] temp = new float[16];
	
	private float[] mvpIndices;
	private float[] mvpMatrices;
	
	private ShortBuffer indexBuffer;
	private FloatBuffer vertexBuffer;
	private FloatBuffer mvpIndexBuffer;
	
	private int batchSize;
	
	private Matrix4 modelMatrix;
	private Texture texture;
	private Context context;
	private PositionTextureBatchShaderProgram shaderProgram;
	private boolean inBeginEndPair;
	private boolean requestTextureBind;
	
	private Geometry geometry;
	
	/**
	 * Crea un PositionTextureSpriteBatch
	 * 
	 * @param context Context en el que se ejecuta el juego
	 */
	public PositionTextureSpriteBatch(Context context) {
		this(context, new PositionTextureBatchShaderProgram());
	}
	
	/**
	 * Crea un PositionTextureSpriteBatch
	 * 
	 * @param context Context en el que se ejecuta el juego
	 */
	protected PositionTextureSpriteBatch(Context context, PositionTextureBatchShaderProgram shaderProgram) {
		this.context = context;
		this.shaderProgram = shaderProgram;
		this.batchSize = 0;
		this.mvpIndices = new float[BATCH_CAPACITY * 4];
		this.mvpMatrices = new float[BATCH_CAPACITY * 16];
		this.modelMatrix = new Matrix4();
		this.texture = null;
		this.inBeginEndPair = false;
		this.geometry = new Geometry(BATCH_CAPACITY * 4, BATCH_CAPACITY * 6, false, true);
		
		setupIndices();
		setupMVPIndices();
		setupVerticesData();
		
		indexBuffer = ByteBuffer.allocateDirect(BATCH_CAPACITY * 6 * SHORT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
				.asShortBuffer();
		int numIndices = BATCH_CAPACITY * 6;
		for (int i = 0; i < numIndices; i++) {
			indexBuffer.put(geometry.getIndex(i));
		}
		indexBuffer.flip();
		
		vertexBuffer = ByteBuffer.allocateDirect(BATCH_CAPACITY * 4 * VERTICES_DATA_STRIDE * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		copyGeometryToVertexBuffer();
		
		mvpIndexBuffer = ByteBuffer.allocateDirect(mvpIndices.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		mvpIndexBuffer.put(mvpIndices).flip();
	}
	
	/**
	 * Copia los datos de la geometria al buffer
	 */
	private void copyGeometryToVertexBuffer() {
		vertexBuffer.clear();
		int nVertices = BATCH_CAPACITY * 4;
		Vector3 position;
		Vector2 textureUV;
		for (int i = 0; i < nVertices; i++) {
			position = geometry.getVertex(i);
			vertexBuffer.put(position.getX());
			vertexBuffer.put(position.getY());
			vertexBuffer.put(position.getZ());
			
			textureUV = geometry.getTextureUV(i);
			vertexBuffer.put(textureUV.getX());
			vertexBuffer.put(textureUV.getY());
		}
		vertexBuffer.position(batchSize * VERTICES_DATA_STRIDE * 4).flip();
	}
	
	/**
	 * Inicializa el array de indices de los vertices que definen la geometria de la malla de sprites
	 */
	private void setupIndices() {
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
	 * Inicializa el array de indices que permiten acceder a las matrices MVP en el vertex shader.
	 */
	private void setupMVPIndices() {
		float value = 0.0f;
		for (int i = 0; i < mvpIndices.length; i++) {
			if ((i != 0) && (i % 4 == 0)) {
				value += 1.0f;
			}
			mvpIndices[i] = value;
		}
	}
	
	/**
	 * Inicializa el array de vertices que definen la geometria de la malla del batch
	 */
	private void setupVerticesData() {
		int nVertices = BATCH_CAPACITY * 4;
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
	public void begin() {
		if (inBeginEndPair) {
			throw new RuntimeException("begin() can not be called more than once before calling end()");
		}
		if (!shaderProgram.isLinked()) {
			shaderProgram.link();
		}
		shaderProgram.use();
		inBeginEndPair = true;
		requestTextureBind = true;
	}
	
	@Override
	public void draw(Sprite sprite, Camera camera) {
		if (!inBeginEndPair) {
			throw new RuntimeException("begin() must be called once before calling draw(Sprite, Camera)");
		}
		drawSprite(sprite, camera);
		batchSize++;
	}
	
	@Override
	public void end() {
		if (!inBeginEndPair) {
			throw new RuntimeException("begin() must be called once before calling end()");
		}
		if (batchSize > 0) {
			drawBatch();
		}
		inBeginEndPair = false;
	}
	
	@Override
	public ShaderProgram getShaderProgram() {
		return shaderProgram;
	}
	
	/**
	 * Prepara los datos de la geometria para ser enviados a los shaders
	 */
	protected void setupVertexShaderVariables() {
		shaderProgram.specifyMVPMatrices(mvpMatrices, 0, batchSize);
		shaderProgram.specifyVerticesPosition(vertexBuffer, VERTICES_DATA_POS_OFFSET, 3, VERTICES_DATA_STRIDE_BYTES);
		shaderProgram.specifyVerticesTextureCoords(vertexBuffer, VERTICES_DATA_UV_OFFSET, 2, VERTICES_DATA_STRIDE_BYTES);
		shaderProgram.specifyVerticesMVPIndices(mvpIndexBuffer, 0, FLOAT_SIZE_BYTES);
	}
	
	/**
	 * Agrega el Sprite al batch.<br>
	 * En caso de que el batch estuviera lleno, se renderiza en 1 sola llamada y se vacia para agregar el nuevo sprite.
	 * 
	 * @param sprite Sprite a agregar
	 * @param camera Camara
	 */
	protected void drawSprite(Sprite sprite, Camera camera) {
		boolean textureChanged = checkTextureChanged(sprite);
		if ((batchSize > 0) && ((batchSize == BATCH_CAPACITY) || textureChanged)) {
			drawBatch();
		}
		setupTexture(sprite, textureChanged);
		setSpriteVerticesData(sprite);
		updateSpriteMVPMatrix(sprite, camera);
	}
	
	/**
	 * Renderizatodos los sprites del batch en 1 sola llamada
	 */
	protected void drawBatch() {
		copyGeometryToVertexBuffer();
		
		mvpIndexBuffer.limit(batchSize * 4).position(0);
		
		indexBuffer.limit(batchSize * 6).position(0);
		
		setupVertexShaderVariables();
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexBuffer.limit(), GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		GLDebugger.getInstance().passiveCheckGLError();
		
		batchSize = 0;
	}
	
	/**
	 * Comprueba si la textura es distinta a la textura utilizada en el anterior sprite que se agrego
	 * 
	 * @param sprite Sprite cuya textura se va a comprobar
	 * @return true si la textura ha cambiado, false en caso contrario
	 */
	protected boolean checkTextureChanged(Sprite sprite) {
		boolean textureChanged = false;
		if (texture == null) {
			textureChanged = true;
		} else if (!texture.equals(sprite.getTextureRegion().getTexture())) {
			textureChanged = true;
		}
		return textureChanged;
	}
	
	/**
	 * Prepara la textura. La recarga y enlaza si es necesario.
	 * 
	 * @param sprite Proximo sprite que se pretende renderizar
	 * @param textureChanged Indica si es necesario reenlazar la textura
	 */
	protected void setupTexture(Sprite sprite, boolean textureChanged) {
		if (textureChanged || requestTextureBind) {
			if (textureChanged) {
				texture = sprite.getTextureRegion().getTexture();
			}
			if (!texture.isLoaded()) {
				texture.loadTexture(context);
			}
			texture.bind();
			requestTextureBind = false;
		}
	}
	
	/**
	 * Actualiza las coordenadas UV del sprite en la geometria de la malla del batch.
	 * 
	 * @param sprite Sprite que se va a renderizar
	 */
	protected void setSpriteVerticesData(Sprite sprite) {
		int i = batchSize * 4;
		// Bottom-Left
		geometry.getTextureUV(i + 0).set(sprite.getTextureRegion().getU1(), sprite.getTextureRegion().getV2());
		// Bottom-Right
		geometry.getTextureUV(i + 1).set(sprite.getTextureRegion().getU2(), sprite.getTextureRegion().getV2());
		// Top-Right
		geometry.getTextureUV(i + 2).set(sprite.getTextureRegion().getU2(), sprite.getTextureRegion().getV1());
		// Top-Left
		geometry.getTextureUV(i + 3).set(sprite.getTextureRegion().getU1(), sprite.getTextureRegion().getV1());
	}
	
	/**
	 * Transforma los vertices asociados al sprite especificado
	 * 
	 * @param sprite Sprite que contiene los datos necesarios para la transformacion
	 * @param camera Camara
	 */
	protected void updateSpriteMVPMatrix(Sprite sprite, Camera camera) {
		int mvpOffset;
		float tx = sprite.getPosition().getX() + sprite.getCenter().getX();
		float ty = sprite.getPosition().getY() + sprite.getCenter().getY();
		
		if (sprite.getRotation() != 0 && sprite.getRotationAroundPoint() != 0) {
			TransformUtilities.transform2D(modelMatrix, tx, ty,
					sprite.getRotationPoint().getX(), sprite.getRotationPoint().getY(),
					sprite.getRotationAroundPoint(), sprite.getRotation(),
					sprite.getDimensions().getWidth(), sprite.getDimensions().getHeight());
			
		} else if (sprite.getRotationAroundPoint() != 0) {
			TransformUtilities.transform2D(modelMatrix, tx, ty,
					sprite.getRotationPoint().getX(), sprite.getRotationPoint().getY(),
					sprite.getRotationAroundPoint(),
					sprite.getDimensions().getWidth(), sprite.getDimensions().getHeight());
			
		} else if (sprite.getRotation() != 0) {
			TransformUtilities.transform2D(modelMatrix, tx, ty, sprite.getRotation(),
					sprite.getDimensions().getWidth(), sprite.getDimensions().getHeight());
			
		} else {
			TransformUtilities.transform2D(modelMatrix, tx, ty,
					sprite.getDimensions().getWidth(), sprite.getDimensions().getHeight());
			
		}
		mvpOffset = batchSize * 16;
		Matrix.multiplyMM(temp, 0, camera.viewMatrix.getValues(), 0, modelMatrix.getValues(), 0);
		Matrix.multiplyMM(mvpMatrices, mvpOffset, camera.projectionMatrix.getValues(), 0, temp, 0);
	}
	
	/**
	 * Devuelve el numero de sprites que hay en el batch
	 * 
	 * @return Numero de sprites en el batch
	 */
	public int getBatchSize() {
		return batchSize;
	}
}
