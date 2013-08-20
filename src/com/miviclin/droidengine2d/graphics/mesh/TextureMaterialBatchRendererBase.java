package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;
import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.shader.PositionTextureBatchShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.graphics.texture.Texture;
import com.miviclin.droidengine2d.graphics.texture.TextureRegion;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Clase base de la que deben heredar los renderers de mallas que representen batches de figuras rectangulares cuyo material sea
 * TextureMaterial
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> Material
 */
public abstract class TextureMaterialBatchRendererBase<M extends Material> extends RectangleBatchRenderer<M> {
	
	protected static final int BATCH_CAPACITY = 32;
	
	private int vertexPositionOffset;
	private int vertexUVOffset;
	private int batchSize;
	private Texture texture;
	private Context context;
	private boolean requestTextureBind;
	
	/**
	 * Constructor
	 * 
	 * @param verticesDataStride Stride de los datos de los vertices
	 * @param context Context en el que se ejecuta el juego
	 * @param shaderProgram Shader Program
	 */
	public TextureMaterialBatchRendererBase(int verticesDataStride, Context context, PositionTextureBatchShaderProgram shaderProgram) {
		super(verticesDataStride, shaderProgram);
		this.vertexPositionOffset = 0;
		this.vertexUVOffset = 3;
		this.context = context;
		this.batchSize = 0;
		this.texture = null;
	}
	
	@Override
	protected void beginDraw() {
		ShaderProgram shaderProgram = getShaderProgram();
		if (!shaderProgram.isLinked()) {
			shaderProgram.link();
		}
		shaderProgram.use();
		requestTextureBind = true;
	}
	
	@Override
	protected void endDraw() {
		if (batchSize > 0) {
			prepareDrawBatch(batchSize);
			drawBatch();
			batchSize = 0;
		}
	}
	
	@Override
	protected void setupVertexShaderVariables(int batchSize) {
		PositionTextureBatchShaderProgram shaderProgram = (PositionTextureBatchShaderProgram) getShaderProgram();
		shaderProgram.specifyMVPMatrices(getGeometry().getMvpMatrices(), 0, batchSize);
		shaderProgram.specifyVerticesPosition(getVertexBuffer(), getVertexPositionOffset(), 3, getVerticesDataStrideBytes());
		shaderProgram.specifyVerticesTextureCoords(getVertexBuffer(), getVertexUVOffset(), 2, getVerticesDataStrideBytes());
		shaderProgram.specifyVerticesMVPIndices(getMvpIndexBuffer(), 0, SIZE_OF_FLOAT);
	}
	
	/**
	 * Agrega el Sprite al batch.<br>
	 * En caso de que el batch estuviera lleno, se renderiza en 1 sola llamada y se vacia para agregar el nuevo sprite.
	 * 
	 * @param textureRegion TextureRegion
	 * @param position Posicion
	 * @param dimensions Dimensiones
	 * @param origin Origen de la figura (debe ser un valor entre 0.0 y 1.0)
	 * @param rotation Angulo de rotacion sobre el centro
	 * @param camera Camara
	 */
	protected void setupSprite(TextureRegion textureRegion, Vector2 position, Dimensions2D dimensions, Vector2 origin, float rotation, Camera camera) {
		boolean textureChanged = checkTextureChanged(textureRegion);
		if ((batchSize > 0) && ((batchSize == BATCH_CAPACITY) || textureChanged)) {
			prepareDrawBatch(batchSize);
			drawBatch();
			batchSize = 0;
		}
		updateTransform(batchSize, position, dimensions, origin, rotation, camera);
		setupTexture(textureRegion.getTexture(), textureChanged);
		setupUVCoords(textureRegion);
	}
	
	/**
	 * Comprueba si la textura es distinta a la textura seleccionada actualmente
	 * 
	 * @param textureRegion TextureRegion cuya textura se va a comprobar
	 * @return true si la textura ha cambiado, false en caso contrario
	 */
	protected boolean checkTextureChanged(TextureRegion textureRegion) {
		boolean textureChanged = false;
		if (texture == null) {
			textureChanged = true;
		} else if (!texture.equals(textureRegion.getTexture())) {
			textureChanged = true;
		}
		return textureChanged;
	}
	
	/**
	 * Prepara la textura. La recarga y enlaza si es necesario.
	 * 
	 * @param newTexture Proxima textura que se pretende utilizar
	 * @param textureChanged Indica si es necesario reenlazar la textura
	 */
	protected void setupTexture(Texture newTexture, boolean textureChanged) {
		if (textureChanged || requestTextureBind) {
			if (textureChanged) {
				texture = newTexture;
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
	 * @param textureRegion TextureRegion que se va a renderizar
	 */
	protected void setupUVCoords(TextureRegion textureRegion) {
		int i = getBatchSize() * 4;
		RectangleBatchGeometry geometry = getGeometry();
		// Bottom-Left
		geometry.getTextureUV(i + 0).set(textureRegion.getU1(), textureRegion.getV2());
		// Bottom-Right
		geometry.getTextureUV(i + 1).set(textureRegion.getU2(), textureRegion.getV2());
		// Top-Right
		geometry.getTextureUV(i + 2).set(textureRegion.getU2(), textureRegion.getV1());
		// Top-Left
		geometry.getTextureUV(i + 3).set(textureRegion.getU1(), textureRegion.getV1());
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
	 * Incrementa en 1 el numero de sprites que hay en el batch
	 */
	protected void incrementBatchSize() {
		batchSize++;
	}
	
	/**
	 * Reinicia a 0 el numero de sprites que hay en el batch
	 */
	protected void resetBatchSize() {
		batchSize = 0;
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
	 * Offset de las coordenadas UV en los datos de un vertice
	 * 
	 * @return Offset
	 */
	public int getVertexUVOffset() {
		return vertexUVOffset;
	}
	
}
