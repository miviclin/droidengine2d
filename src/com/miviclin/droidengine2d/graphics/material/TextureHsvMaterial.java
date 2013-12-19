package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * This material has texture and HSV color.<br>
 * The hue of the texture can be changed. Saturation and brightness can be reduced. And opacity can also be adjusted.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureHsvMaterial extends TransparentTextureMaterial {

	private float hOffset;
	private float sMulti;
	private float vMulti;

	/**
	 * Creates a TextureHsvMaterial.
	 * 
	 * @param textureRegion TextureRegion.
	 */
	public TextureHsvMaterial(TextureRegion textureRegion) {
		this(textureRegion, 1.0f, 0.0f, 1.0f, 1.0f);
	}

	/**
	 * Creates a TextureHsvMaterial.
	 * 
	 * @param textureRegion TextureRegion.
	 * @param hOffset Hue offset (value between 0.0f and 360.0f).
	 * @param sMulti Saturation multiplier (value between 0.0f and 1.0f).
	 * @param vMulti Brightness multiplier (value between 0.0f and 1.0f).
	 */
	public TextureHsvMaterial(TextureRegion textureRegion, float hOffset, float sMulti, float vMulti) {
		this(textureRegion, 1.0f, hOffset, sMulti, vMulti);
	}

	/**
	 * Creates a TextureHsvMaterial.
	 * 
	 * @param textureRegion TextureRegion.
	 * @param opacity (value between 0.0f and 1.0f).
	 * @param hOffset Hue offset (value between 0.0f and 360.0f).
	 * @param sMulti Saturation multiplier (value between 0.0f and 1.0f).
	 * @param vMulti Brightness multiplier (value between 0.0f and 1.0f).
	 */
	public TextureHsvMaterial(TextureRegion textureRegion, float opacity, float hOffset, float sMulti, float vMulti) {
		super(textureRegion, opacity);
		checkH(hOffset);
		checkS(sMulti);
		checkV(vMulti);
		this.hOffset = hOffset;
		this.sMulti = sMulti;
		this.vMulti = vMulti;
	}

	/**
	 * Returns the hue offset.
	 * 
	 * @return value between 0.0f and 360.0f
	 */
	public float getHOffset() {
		return hOffset;
	}

	/**
	 * Sets the hue offset.
	 * 
	 * @param hOffset Value between 0.0f and 360.0f.
	 */
	public void setHOffset(float hOffset) {
		checkH(hOffset);
		this.hOffset = hOffset;
	}

	/**
	 * Returns the saturation multiplier.
	 * 
	 * @return value between 0.0f and 1.0f
	 */
	public float getSMulti() {
		return sMulti;
	}

	/**
	 * Sets the saturation multiplier.
	 * 
	 * @param sMulti Value between 0.0f and 1.0f.
	 */
	public void setSMulti(float sMulti) {
		checkS(sMulti);
		this.sMulti = sMulti;
	}

	/**
	 * Returns the brightness multiplier.
	 * 
	 * @return value between 0.0f and 1.0f
	 */
	public float getVMulti() {
		return vMulti;
	}

	/**
	 * Sets the brightness multiplier.
	 * 
	 * @param vMulti Value between 0.0f and 1.0f.
	 */
	public void setVMulti(float vMulti) {
		checkV(vMulti);
		this.vMulti = vMulti;
	}

	/**
	 * Checks that hOffset is a value between 0.0f and 360.0f and throws an exception in case it isn't.
	 * 
	 * @param hOffset
	 */
	private void checkH(float hOffset) {
		if (hOffset < 0 || hOffset > 360) {
			throw new IllegalArgumentException("The H component must be a value between 0.0 and 360.0");
		}
	}

	/**
	 * Checks that sMulti is a value between 0.0f and 1.0f and throws an exception in case it isn't.
	 * 
	 * @param sMulti
	 */
	private void checkS(float sMulti) {
		if (sMulti < 0 || sMulti > 1) {
			throw new IllegalArgumentException("The S component must be a value between 0.0 and 1.0");
		}
	}

	/**
	 * Checks that vMulti is a value between 0.0f and 1.0f and throws an exception in case it isn't.
	 * 
	 * @param vMulti
	 */
	private void checkV(float vMulti) {
		if (vMulti < 0 || vMulti > 1) {
			throw new IllegalArgumentException("The V component must be a value between 0.0 and 1.0");
		}
	}
}
