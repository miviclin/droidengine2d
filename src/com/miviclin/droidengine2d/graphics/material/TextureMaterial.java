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
		checkTextureRegion(textureRegion);
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
		checkTextureRegion(textureRegion);
		this.textureRegion = textureRegion;
	}
	
	/**
	 * Comprueba que el TextureRegion especificado no sea null y lanza una excepcion en caso contrario
	 * 
	 * @param textureRegion TextureRegion
	 */
	private void checkTextureRegion(TextureRegion textureRegion) {
		if (textureRegion == null) {
			throw new IllegalArgumentException("The TextureRegion can not be null");
		}
	}
	
}
