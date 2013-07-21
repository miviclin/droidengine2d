package com.miviclin.droidengine2d.graphics.mesh;

import static com.miviclin.droidengine2d.util.PrimitiveTypeSize.SIZE_OF_FLOAT;

import java.nio.FloatBuffer;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shader.PositionTextureBatchShaderProgram;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;
import com.miviclin.droidengine2d.graphics.shape.Sprite;
import com.miviclin.droidengine2d.graphics.texture.Texture;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * SpriteBatch que permite renderizar en una llamada hasta 32 sprites con transformaciones (traslacion, rotacion y escala) distintas.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class PositionTextureSpriteBatch extends RectangleBatchMesh implements SpriteBatch {
	
	protected static final int BATCH_CAPACITY = 32;
	
	private int vertexPositionOffset;
	private int vertexUVOffset;
	private int batchSize;
	private Texture texture;
	private Context context;
	private boolean inBeginEndPair;
	private boolean requestTextureBind;
	
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
		super(5, shaderProgram);
		setVerticesDataStride(5);
		this.vertexPositionOffset = 0;
		this.vertexUVOffset = 3;
		this.context = context;
		this.batchSize = 0;
		this.texture = null;
		this.inBeginEndPair = false;
	}
	
	@Override
	public void begin() {
		ShaderProgram shaderProgram = getShaderProgram();
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
			prepareDrawBatch(batchSize);
			drawBatch();
			batchSize = 0;
		}
		inBeginEndPair = false;
	}
	
	@Override
	public PositionTextureBatchShaderProgram getShaderProgram() {
		return (PositionTextureBatchShaderProgram) super.getShaderProgram();
	}
	
	@Override
	protected void setupVerticesData() {
		RectangleBatchGeometry geometry = getGeometry();
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
	protected void copyGeometryToVertexBuffer(int batchSize) {
		FloatBuffer vertexBuffer = getVertexBuffer();
		vertexBuffer.clear();
		int nVertices = BATCH_CAPACITY * 4;
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
	protected void setupVertexShaderVariables(int batchSize) {
		PositionTextureBatchShaderProgram shaderProgram = getShaderProgram();
		shaderProgram.specifyMVPMatrices(getGeometry().getMvpMatrices(), 0, batchSize);
		shaderProgram.specifyVerticesPosition(getVertexBuffer(), vertexPositionOffset, 3, getVerticesDataStrideBytes());
		shaderProgram.specifyVerticesTextureCoords(getVertexBuffer(), vertexUVOffset, 2, getVerticesDataStrideBytes());
		shaderProgram.specifyVerticesMVPIndices(getMvpIndexBuffer(), 0, SIZE_OF_FLOAT);
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
			prepareDrawBatch(batchSize);
			drawBatch();
			batchSize = 0;
		}
		setupTexture(sprite, textureChanged);
		setSpriteVerticesData(sprite);
		updateMVPMatrix(batchSize, sprite, camera);
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
		RectangleBatchGeometry geometry = getGeometry();
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
	 * Offset de las coordenadas UV en los datos de un vertice
	 * 
	 * @return Offset
	 */
	public int getVertexUVOffset() {
		return vertexUVOffset;
	}
	
}
