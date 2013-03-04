package com.miviclin.droidengine2d.graphics.sprites;

import com.miviclin.droidengine2d.graphics.textures.TextureRegion;

/**
 * Representa un frame de animacion
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class AnimationFrame {
	
	private int delay;
	private TextureRegion textureRegion;
	
	/**
	 * Crea un AnimationFrame
	 * 
	 * @param delay Tiempo que se mostrara este frame antes de actualizar al siguiente
	 * @param textureRegion TextureRegion que se mostrara en este frame
	 */
	public AnimationFrame(int delay, TextureRegion textureRegion) {
		super();
		this.delay = delay;
		this.textureRegion = textureRegion;
	}
	
	/**
	 * Devuelve el tiempo que se mostrara este frame antes de actualizar al siguiente
	 * 
	 * @return tiempo que se mostrara este frame antes de actualizar al siguiente
	 */
	public int getDelay() {
		return delay;
	}
	
	/**
	 * Asigna el tiempo que se mostrara este frame antes de actualizar al siguiente
	 * 
	 * @param delay Nuevo valor
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	/**
	 * Devuelve el TextureRegion que se mostrara en este frame
	 * 
	 * @return TextureRegion que se mostrara en este frame
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	/**
	 * Asigna el TextureRegion que se mostrara en este frame
	 * 
	 * @param textureRegion Nuevo TextureRegion
	 */
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
}
