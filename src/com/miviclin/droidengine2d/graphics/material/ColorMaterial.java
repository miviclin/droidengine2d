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
	 * Constructor por defecto. Crea un ColorMaterial de color blanco.
	 */
	public ColorMaterial() {
		this(new Color(1, 1, 1));
	}

	/**
	 * Constructor
	 * 
	 * @param color Color (no puede ser null)
	 */
	public ColorMaterial(Color color) {
		super();
		if (color == null) {
			throw new IllegalArgumentException("The Color can not be null");
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
