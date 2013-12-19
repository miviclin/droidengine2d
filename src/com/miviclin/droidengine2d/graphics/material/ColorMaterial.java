package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.Color;

/**
 * This Material has Color.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ColorMaterial extends Material {

	private Color color;

	/**
	 * Creates a ColorMaterial.<br>
	 * The default color is white.
	 */
	public ColorMaterial() {
		this(new Color(1, 1, 1));
	}

	/**
	 * Creates a ColorMaterial.
	 * 
	 * @param color Color (can not be null).
	 */
	public ColorMaterial(Color color) {
		super();
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
