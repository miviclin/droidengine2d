package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * This Material has texture and color.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureColorMaterial extends TextureMaterial {

	private Color color;

	/**
	 * Creates a TextureColorMaterial.
	 * 
	 * @param color Color (can not be null).
	 */
	public TextureColorMaterial(TextureRegion textureRegion, Color color) {
		super(textureRegion);
		if (color == null) {
			throw new IllegalArgumentException("The Color can not be null");
		}
		this.color = color;
	}

	/**
	 * Returns the Color of this material.
	 * 
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}
}
