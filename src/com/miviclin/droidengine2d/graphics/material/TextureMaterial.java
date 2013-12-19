package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * This Material has texture.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureMaterial extends Material {

	private TextureRegion textureRegion;

	/**
	 * Creates a TextureMaterial.
	 * 
	 * @param textureRegion TextureRegion.
	 */
	public TextureMaterial(TextureRegion textureRegion) {
		super();
		this.textureRegion = textureRegion;
	}

	/**
	 * Returns the TextureRegion of this material.
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	/**
	 * Sets the TextureRegion of this material.
	 * 
	 * @param textureRegion TextureRegion.
	 */
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

}
