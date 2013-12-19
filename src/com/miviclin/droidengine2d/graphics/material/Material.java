package com.miviclin.droidengine2d.graphics.material;

/**
 * Base class for materials.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Material {

	private final BlendingOptions blendingOptions;

	/**
	 * Constructor.
	 */
	public Material() {
		super();
		this.blendingOptions = new BlendingOptions();
	}

	/**
	 * Returns the blending options of this material.
	 * 
	 * @return BlendingOptions
	 */
	public BlendingOptions getBlendingOptions() {
		return blendingOptions;
	}

}
