package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * Material que solo tiene una textura
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureMaterial extends Material {
	
	private TextureRegion textureRegion;
	
	/**
	 * Constructor
	 * 
	 * @param textureRegion TextureRegion (no puede ser null)
	 */
	public TextureMaterial(TextureRegion textureRegion) {
		super();
		this.textureRegion = textureRegion;
	}
	
	/**
	 * Devuelve el TextureRegion
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	/**
	 * Asigna un TextureRegion. No puede ser null.
	 * 
	 * @param textureRegion Nuevo TextureRegion
	 */
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
}
