package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.Color;

/**
 * Material con color
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ColorMaterial extends Material {
	
	private Color color;
	
	/**
	 * Constructor
	 * 
	 * @param color Color (no puede ser null)
	 */
	public ColorMaterial(Color color) {
		super();
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