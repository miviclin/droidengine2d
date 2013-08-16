package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * Material con textura y color
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureColorMaterial extends TextureMaterial {
	
	private Color color;
	
	/**
	 * Constructor
	 * 
	 * @param color Color (no puede ser null)
	 */
	public TextureColorMaterial(TextureRegion textureRegion, Color color) {
		super(textureRegion);
		if (color == null) {
			throw new IllegalArgumentException("The TextureRegion can not be null");
		}
		this.color = color;
	}
	
	/**
	 * Devuelve el color
	 * 
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}
}
