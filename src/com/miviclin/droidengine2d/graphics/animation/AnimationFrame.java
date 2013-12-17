package com.miviclin.droidengine2d.graphics.animation;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * Animation frame.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class AnimationFrame {

	private float delay;
	private TextureRegion textureRegion;

	/**
	 * Creates a new AnimationFrame.
	 * 
	 * @param delay Minimum time this frame will be displayed.
	 * @param textureRegion TextureRegion.
	 */
	public AnimationFrame(float delay, TextureRegion textureRegion) {
		super();
		this.delay = delay;
		this.textureRegion = textureRegion;
	}

	/**
	 * Returns the minimum time this frame will be displayed.
	 * 
	 * @return minimum time this frame will be displayed
	 */
	public float getDelay() {
		return delay;
	}

	/**
	 * Sets the minimum time this frame will be displayed.
	 * 
	 * @param delay Minimum time this frame will be displayed.
	 */
	public void setDelay(float delay) {
		this.delay = delay;
	}

	/**
	 * Returns the TextureRegion associated to this AnimationFrame.
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	/**
	 * Sets the TextureRegion associated to this AnimationFrame.
	 * 
	 * @param textureRegion TextureRegion.
	 */
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

}
