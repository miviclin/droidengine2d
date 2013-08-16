package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * Material que tiene una textura y permite variar la opacidad. Tambien se permite variar el tono de la textura y se puede reducir la
 * saturacion y el brillo.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureHSVMaterial extends TransparentTextureMaterial {
	
	private float hOffset;
	private float sMulti;
	private float vMulti;
	
	/**
	 * Constructor
	 * 
	 * @param textureRegion TextureRegion (no puede ser null)
	 */
	public TextureHSVMaterial(TextureRegion textureRegion) {
		this(textureRegion, 1.0f, 0.0f, 1.0f, 1.0f);
	}
	
	/**
	 * Constructor
	 * 
	 * @param textureRegion TextureRegion (no puede ser null)
	 * @param hOffset Offset de tono (valor entre 0.0f y 360.0f)
	 * @param sMulti Multiplicador de saturacion (valor entre 0.0f y 1.0f)
	 * @param vMulti Multiplicador de brillo (valor entre 0.0f y 1.0f)
	 */
	public TextureHSVMaterial(TextureRegion textureRegion, float hOffset, float sMulti, float vMulti) {
		this(textureRegion, 1.0f, hOffset, sMulti, vMulti);
	}
	
	/**
	 * Constructor
	 * 
	 * @param textureRegion TextureRegion (no puede ser null)
	 * @param opacity Opacidad (valor entre 0.0f y 1.0f)
	 * @param hOffset Offset de tono (valor entre 0.0f y 360.0f)
	 * @param sMulti Multiplicador de saturacion (valor entre 0.0f y 1.0f)
	 * @param vMulti Multiplicador de brillo (valor entre 0.0f y 1.0f)
	 */
	public TextureHSVMaterial(TextureRegion textureRegion, float opacity, float hOffset, float sMulti, float vMulti) {
		super(textureRegion, opacity);
		checkH(hOffset);
		checkS(sMulti);
		checkV(vMulti);
		this.hOffset = hOffset;
		this.sMulti = sMulti;
		this.vMulti = vMulti;
	}
	
	/**
	 * Devuelve el offset del valor H (tono)
	 * 
	 * @return valor entre 0.0f y 360.0f
	 */
	public float getHOffset() {
		return hOffset;
	}
	
	/**
	 * Asigna el offset del valor H (tono)
	 * 
	 * @param hOffset valor entre 0.0f y 360.0f
	 */
	public void setHOffset(float hOffset) {
		checkH(hOffset);
		this.hOffset = hOffset;
	}
	
	/**
	 * Devuelve el multiplicador del valor S (saturacion)
	 * 
	 * @return valor entre 0.0f y 1.0f
	 */
	public float getSMulti() {
		return sMulti;
	}
	
	/**
	 * Asigna el multiplicador del valor S (saturacion)
	 * 
	 * @param sMulti valor entre 0.0f y 1.0f
	 */
	public void setSMulti(float sMulti) {
		checkS(sMulti);
		this.sMulti = sMulti;
	}
	
	/**
	 * Devuelve el multiplicador del valor V (brillo)
	 * 
	 * @return valor entre 0.0f y 1.0f
	 */
	public float getVMulti() {
		return vMulti;
	}
	
	/**
	 * Asigna el multiplicador del valor V (brillo)
	 * 
	 * @param vMulti valor entre 0.0f y 1.0f
	 */
	public void setVMulti(float vMulti) {
		checkV(vMulti);
		this.vMulti = vMulti;
	}
	
	/**
	 * Comprueba que hOffset sea un valor entre 0.0f y 360.0f y lanza una excepcion en caso contrario
	 * 
	 * @param hOffset
	 */
	private void checkH(float hOffset) {
		if (hOffset < 0 || hOffset > 360) {
			throw new IllegalArgumentException("The H component must be a value between 0.0 and 360.0");
		}
	}
	
	/**
	 * Comprueba que sMulti sea un valor entre 0.0f y 1.0f y lanza una excepcion en caso contrario
	 * 
	 * @param sMulti
	 */
	private void checkS(float sMulti) {
		if (sMulti < 0 || sMulti > 1) {
			throw new IllegalArgumentException("The S component must be a value between 0.0 and 1.0");
		}
	}
	
	/**
	 * Comprueba que vMulti sea un valor entre 0.0f y 1.0f y lanza una excepcion en caso contrario
	 * 
	 * @param vMulti
	 */
	private void checkV(float vMulti) {
		if (vMulti < 0 || vMulti > 1) {
			throw new IllegalArgumentException("The V component must be a value between 0.0 and 1.0");
		}
	}
}
