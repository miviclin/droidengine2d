package com.miviclin.droidengine2d.graphics.shape;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * Sprite
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Sprite extends RectangularShape {
	
	private TextureRegion textureRegion;
	
	/**
	 * Crea un Sprite
	 * 
	 * @param x Coordenada X en pixeles de la esquina superior izquierda del sprite
	 * @param y Coordenada Y en pixeles de la esquina superior izquierda del sprite
	 * @param width Ancho del sprite
	 * @param height Alto del sprite
	 * @param textureRegion TextureRegion (region de una textura que se toma como imagen del sprite)
	 */
	public Sprite(float x, float y, float width, float height, TextureRegion textureRegion) {
		super(x, y, width, height);
		checkTextureRegion(textureRegion);
		this.textureRegion = textureRegion;
	}
	
	/**
	 * Devuelve la region de la textura correspondiente al sprite
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	/**
	 * Asigna un TextureRegion al sprite
	 * 
	 * @param textureRegion Nuevo TextureRegion
	 * @throws IllegalArgumentException Si el TextureRegion especificado es null
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
