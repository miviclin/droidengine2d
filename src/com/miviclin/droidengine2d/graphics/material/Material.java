package com.miviclin.droidengine2d.graphics.material;

/**
 * Clase base de la que deben heredar todos los materiales
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Material {
	
	private final BlendingOptions blendingOptions;
	
	/**
	 * Constructor
	 */
	public Material() {
		super();
		this.blendingOptions = new BlendingOptions();
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
