package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * This material has texture and variable opacity.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TransparentTextureMaterial extends TextureMaterial {

	private float opacity;

	/**
	 * Creates a TransparentTextureMaterial.
	 * 
	 * @param textureRegion TextureRegion.
	 */
	public TransparentTextureMaterial(TextureRegion textureRegion) {
		this(textureRegion, 1.0f);
	}

	/**
	 * Creates a TransparentTextureMaterial.
	 * 
	 * @param textureRegion TextureRegion.
	 * @param opacity Opacity (value between 0.0f and 1.0f).
	 */
	public TransparentTextureMaterial(TextureRegion textureRegion, float opacity) {
		super(textureRegion);
		checkOpacity(opacity);
		this.opacity = opacity;
	}

	/**
	 * Returns the opacity of this material.
	 * 
	 * @return Opacity (value between 0.0f and 1.0f)
	 */
	public float getOpacity() {
		return opacity;
	}

	/**
	 * Sets the opacity of this material.
	 * 
	 * @param opacity (value between 0.0f and 1.0f).
	 */
	public void setOpacity(float opacity) {
		checkOpacity(opacity);
		this.opacity = opacity;
	}

	/**
	 * Checks that the specified opacity is a value between 0.0f and 1.0f and throws an exception in case it isn't.
	 * 
	 * @param opacity (value between 0.0f and 1.0f)
	 */
	private void checkOpacity(float opacity) {
		if (opacity < 0 || opacity > 1) {
			throw new IllegalArgumentException("The opacity value must be a value between 0.0f and 1.0f");
		}
	}

}
