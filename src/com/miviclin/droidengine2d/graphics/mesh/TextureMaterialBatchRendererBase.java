package com.miviclin.droidengine2d.graphics.mesh;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.texture.Texture;
import com.miviclin.droidengine2d.graphics.texture.TextureRegion;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Base class for TextureMaterial batch renderers.
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> Material
 */
public abstract class TextureMaterialBatchRendererBase<M extends Material> extends RectangleBatchRenderer<M> {

	private int vertexPositionOffset;
	private int vertexUVOffset;
	private Texture texture;
	private Context context;
	private boolean requestTextureBind;

	/**
	 * Constructor.
	 * 
	 * @param verticesDataStride Data stride of the vertices.
	 * @param context Context.
	 */
	public TextureMaterialBatchRendererBase(int verticesDataStride, Context context) {
		super(verticesDataStride, 32);
		this.vertexPositionOffset = 0;
		this.vertexUVOffset = 3;
		this.context = context;
		this.texture = null;
	}

	@Override
	protected void beginDraw() {
		super.beginDraw();
		requestTextureBind = true;
	}

	/**
	 * Adds a rectangle with texture to this batch.<br>
	 * If the batch was full, it will be rendered in one draw call and it will be left empty. Then, the specified
	 * rectangle will be added to this batch.
	 * 
	 * @param textureRegion TextureRegion.
	 * @param position Position.
	 * @param scale Scale.
	 * @param origin Origin of the rectangle (value between 0.0f and 1.0f).
	 * @param rotation Rotation angle around the origin.
	 * @param camera Camera.
	 */
	protected void setupTexturedRectangle(TextureRegion textureRegion, Vector2 position, Vector2 scale, Vector2 origin,
			float rotation, Camera camera) {

		boolean textureChanged = checkTextureChanged(textureRegion);
		if ((getBatchSize() > 0) && ((getBatchSize() == getBatchCapacity()) || textureChanged || isForceDraw())) {
			drawBatch();
		}
		updateTransform(getBatchSize(), position, scale, origin, rotation, camera);
		setupTexture(textureRegion.getTexture(), textureChanged);
		setupUVCoords(textureRegion);
	}

	/**
	 * Checks if the specified TextureRegion's Texture is different from the currently selected Texture.
	 * 
	 * @param textureRegion TextureRegion.
	 * @return true if the Texture is different, false otherwise.
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
	 * Sets up the texture.<br>
	 * The texture is reloaded and bound if needed.
	 * 
	 * @param newTexture Texture.
	 * @param textureChanged If true, the current texture will be replaced by the new one, otherwise it might be
	 *            replaced but it is not guaranteed.
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
	 * Sets the UV coordinates of the vertices of the last rectangle added to this batch.
	 * 
	 * @param textureRegion TextureRegion that will be used for the last rectangle added.
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
	 * Vertex position offset in the vertex buffer.
	 * 
	 * @return Offset
	 */
	public int getVertexPositionOffset() {
		return vertexPositionOffset;
	}

	/**
	 * Offset of the UV coordinates in the vertex buffer.
	 * 
	 * @return Offset
	 */
	public int getVertexUVOffset() {
		return vertexUVOffset;
	}

}
