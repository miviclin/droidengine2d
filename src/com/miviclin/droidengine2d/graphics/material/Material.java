package com.miviclin.droidengine2d.graphics.material;

/**
 * Clase base de la que deben heredar todos los materiales
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Material {
	
	private final BlendingOptions blendingOptions = new BlendingOptions();
	
	/**
	 * Constructor
	 */
	public Material() {
		super();
	}
	
	/**
	 * Devuelve las opciones de blending
	 * 
	 * @return BlendingOptions
	 */
	public BlendingOptions getBlendingOptions() {
		return blendingOptions;
	}
	
}
