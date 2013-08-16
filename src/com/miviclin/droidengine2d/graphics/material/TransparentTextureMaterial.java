package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * Material que tiene una textura y permite variar la opacidad
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TransparentTextureMaterial extends TextureMaterial {
	
	private float opacity;
	
	/**
	 * Constructor
	 * 
	 * @param textureRegion TextureRegion (no puede ser null)
	 */
	public TransparentTextureMaterial(TextureRegion textureRegion) {
		this(textureRegion, 1.0f);
	}
	
	/**
	 * Constructor
	 * 
	 * @param textureRegion TextureRegion (no puede ser null)
	 * @param opacity Opacidad (valor entre 0.0f y 1.0f)
	 */
	public TransparentTextureMaterial(TextureRegion textureRegion, float opacity) {
		super(textureRegion);
		checkOpacity(opacity);
		this.opacity = opacity;
	}
	
	/**
	 * Devuelve la opacidad
	 * 
	 * @return Opacidad (valor entre 0.0f y 1.0f)
	 */
	public float getOpacity() {
		return opacity;
	}
	
	/**
	 * Asigna la opacidad
	 * 
	 * @param opacity Valor entre 0.0f y 1.0f
	 */
	public void setOpacity(float opacity) {
		checkOpacity(opacity);
		this.opacity = opacity;
	}
	
	/**
	 * Comprueba que la opacidad sea un valor entre 0.0f y 1.0f y lanza una excepcion en caso contrario
	 * 
	 * @param opacity Opacidad
	 */
	private void checkOpacity(float opacity) {
		if (opacity < 0 || opacity > 1) {
			throw new IllegalArgumentException("The opacity value must be a value between 0.0f and 1.0f");
		}
	}
	
}
