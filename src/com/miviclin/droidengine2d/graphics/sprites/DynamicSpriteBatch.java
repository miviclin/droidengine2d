package com.miviclin.droidengine2d.graphics.sprites;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.miviclin.droidengine2d.graphics.GLDebug;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shaders.DynamicSpriteBatchShaderProgram;
import com.miviclin.droidengine2d.graphics.textures.Texture;
import com.miviclin.droidengine2d.util.TransformUtilities;
import com.miviclin.droidengine2d.util.math.Matrix4;

/**
 * SpriteBatch que permite renderizar en una llamada hasta 32 sprites con transformaciones (traslacion, rotacion y escala) distintas.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class DynamicSpriteBatch implements SpriteBatch {
	
	private static final int FLOAT_SIZE_BYTES = 4;
	private static final int SHORT_SIZE_BYTES = 4;
	
	private static final int VERTICES_DATA_STRIDE = 5;
	private static final int VERTICES_DATA_STRIDE_BYTES = VERTICES_DATA_STRIDE * FLOAT_SIZE_BYTES;
	private static final int VERTICES_DATA_POS_OFFSET = 0;
	private static final int VERTICES_DATA_UV_OFFSET = 3;
	
	private final float[] temp = new float[16];
	
	private float[] verticesData;
	private float[] mvpIndices;
	private float[] mvpMatrices;
	private short[] indices;
	
	private ShortBuffer indexBuffer;
	private FloatBuffer vertexBuffer;
	private FloatBuffer mvpIndexBuffer;
	
	private int batchCapacity;
	private int batchSize;
	
	private Matrix4 modelMatrix;
	private Texture texture;
	private Context context;
	private DynamicSpriteBatchShaderProgram shaderProgram;
	private boolean inBeginEndPair;
	private boolean requestTextureBind;
	
	/**
	 * Crea un DynamicSpriteBatch
	 * 
	 * @param context Context en el que se ejecuta el juego
	 * @param shaderProgram programa de shaders que se utilizara para renderizar los batches
	 */
	public DynamicSpriteBatch(Context context, DynamicSpriteBatchShaderProgram shaderProgram) {
		this.context = context;
		this.shaderProgram = shaderProgram;
		this.batchCapacity = 32;
		this.batchSize = 0;
		this.indices = new short[batchCapacity * 6];
		this.verticesData = new float[batchCapacity * 4 * VERTICES_DATA_STRIDE];
		this.mvpIndices = new float[batchCapacity * 4];
		this.mvpMatrices = new float[batchCapacity * 16];
		this.modelMatrix = new Matrix4();
		this.texture = null;
		this.inBeginEndPair = false;
		
		setupIndices();
		setupMVPIndices();
		setupVerticesData();
		
		indexBuffer = ByteBuffer.allocateDirect(indices.length * SHORT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
				.asShortBuffer();
		indexBuffer.put(indices).flip();
		
		vertexBuffer = ByteBuffer.allocateDirect(verticesData.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		vertexBuffer.put(verticesData).flip();
		
		mvpIndexBuffer = ByteBuffer.allocateDirect(mvpIndices.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		mvpIndexBuffer.put(mvpIndices).flip();
	}
	
	/**
	 * Inicializa el array de indices de los vertices que definen la geometria de la malla de sprites
	 */
	private void setupIndices() {
		for (int i = 0, j = 0; i < indices.length; i += 6, j += 4) {
			indices[i + 0] = (short) (j + 0);
			indices[i + 1] = (short) (j + 1);
			indices[i + 2] = (short) (j + 2);
			indices[i + 3] = (short) (j + 2);
			indices[i + 4] = (short) (j + 3);
			indices[i + 5] = (short) (j + 0);
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
		for (int i = 0; i < verticesData.length; i += 20) {
			// Bottom-Left
			verticesData[i + 0] = -0.5f;
			verticesData[i + 1] = -0.5f;
			verticesData[i + 2] = 0.0f;
			verticesData[i + 3] = 0.0f;
			verticesData[i + 4] = 1.0f;
			// Bottom-Right
			verticesData[i + 5] = 0.5f;
			verticesData[i + 6] = -0.5f;
			verticesData[i + 7] = 0.0f;
			verticesData[i + 8] = 1.0f;
			verticesData[i + 9] = 1.0f;
			// Top-Right
			verticesData[i + 10] = 0.5f;
			verticesData[i + 11] = 0.5f;
			verticesData[i + 12] = 0.0f;
			verticesData[i + 13] = 1.0f;
			verticesData[i + 14] = 0.0f;
			// Top-Left
			verticesData[i + 15] = -0.5f;
			verticesData[i + 16] = 0.5f;
			verticesData[i + 17] = 0.0f;
			verticesData[i + 18] = 0.0f;
			verticesData[i + 19] = 0.0f;
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
	
	/**
	 * Prepara los datos de la geometria para ser enviados a los shaders
	 */
	public void setupVertexShaderVariables() {
		int aPositionHandle = shaderProgram.getPositionAttributeHandle();
		int aTextureHandle = shaderProgram.getTextureCoordAttributeHandle();
		int uMVPMatrixHandle = shaderProgram.getMVPMatrixUniformHandle();
		int aMVPMatrixIndexHandle = shaderProgram.getMVPMatrixIndexAttributeHandle();
		
		GLES20.glUniformMatrix4fv(uMVPMatrixHandle, batchSize, false, mvpMatrices, 0);
		
		GLES20.glEnableVertexAttribArray(aPositionHandle);
		GLDebug.checkGLError();
		
		vertexBuffer.position(VERTICES_DATA_POS_OFFSET);
		GLES20.glVertexAttribPointer(aPositionHandle, 3, GLES20.GL_FLOAT, false, VERTICES_DATA_STRIDE_BYTES, vertexBuffer);
		GLDebug.checkGLError();
		
		GLES20.glEnableVertexAttribArray(aTextureHandle);
		GLDebug.checkGLError();
		
		vertexBuffer.position(VERTICES_DATA_UV_OFFSET);
		GLES20.glVertexAttribPointer(aTextureHandle, 2, GLES20.GL_FLOAT, false, VERTICES_DATA_STRIDE_BYTES, vertexBuffer);
		GLDebug.checkGLError();
		
		GLES20.glEnableVertexAttribArray(aMVPMatrixIndexHandle);
		GLDebug.checkGLError();
		
		mvpIndexBuffer.position(0);
		GLES20.glVertexAttribPointer(aMVPMatrixIndexHandle, 1, GLES20.GL_FLOAT, false, FLOAT_SIZE_BYTES, mvpIndexBuffer);
		GLDebug.checkGLError();
	}
	
	/**
	 * Agrega el Sprite al batch.<br>
	 * En caso de que el batch estuviera lleno, se renderiza en 1 sola llamada y se vacia para agregar el nuevo sprite.
	 * 
	 * @param sprite Sprite a agregar
	 * @param camera Camara
	 */
	private void drawSprite(Sprite sprite, Camera camera) {
		boolean textureChanged = checkTextureChanged(sprite);
		if ((batchSize > 0) && ((batchSize == batchCapacity) || textureChanged)) {
			drawBatch();
		}
		setupTexture(sprite, textureChanged);
		setSpriteVerticesData(sprite);
		updateSpriteMVPMatrix(sprite, camera);
		batchSize++;
	}
	
	/**
	 * Renderizatodos los sprites del batch en 1 sola llamada
	 */
	private void drawBatch() {
		vertexBuffer.clear();
		vertexBuffer.put(verticesData).position(batchSize * VERTICES_DATA_STRIDE * 4).flip();
		
		mvpIndexBuffer.limit(batchSize * 4).position(0);
		
		indexBuffer.limit(batchSize * 6).position(0);
		
		setupVertexShaderVariables();
		
		GLES20.glDrawElements(GLES20.GL_TRIANGLES, indexBuffer.limit(), GLES20.GL_UNSIGNED_SHORT, indexBuffer);
		GLDebug.checkGLError();
		
		batchSize = 0;
	}
	
	/**
	 * Comprueba si la textura es distinta a la textura utilizada en el anterior sprite que se agrego
	 * 
	 * @param sprite Sprite cuya textura se va a comprobar
	 * @return true si la textura ha cambiado, false en caso contrario
	 */
	private boolean checkTextureChanged(Sprite sprite) {
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
	private void setupTexture(Sprite sprite, boolean textureChanged) {
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
	private void setSpriteVerticesData(Sprite sprite) {
		int i = batchSize * VERTICES_DATA_STRIDE * 4;
		// Bottom-Left
		verticesData[i + 3] = sprite.getTextureRegion().getU1();
		verticesData[i + 4] = sprite.getTextureRegion().getV2();
		// Bottom-Right
		verticesData[i + 8] = sprite.getTextureRegion().getU2();
		verticesData[i + 9] = sprite.getTextureRegion().getV2();
		// Top-Right
		verticesData[i + 13] = sprite.getTextureRegion().getU2();
		verticesData[i + 14] = sprite.getTextureRegion().getV1();
		// Top-Left
		verticesData[i + 18] = sprite.getTextureRegion().getU1();
		verticesData[i + 19] = sprite.getTextureRegion().getV1();
	}
	
	/**
	 * Transforma los vertices asociados al sprite especificado
	 * 
	 * @param sprite Sprite que contiene los datos necesarios para la transformacion
	 * @param camera Camara
	 */
	private void updateSpriteMVPMatrix(Sprite sprite, Camera camera) {
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
	
}
