package com.miviclin.droidengine2d.graphics.material;

import android.opengl.GLES20;

/**
 * Objeto que define las opciones de blending
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class BlendingOptions {
	
	private int sourceFactor;
	private int destinationFactor;
	private int blendEquationMode;
	
	/**
	 * Constructor.<br>
	 * El valor por defecto para sourceFactor es {@link GLES20#GL_SRC_ALPHA}<br>
	 * El valor por defecto para destinationFactor es {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}<br>
	 * La ecuacion utilizada por defecto es {@link GLES20#GL_FUNC_ADD}
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 * @see GLES20#glBlendEquation(int)
	 */
	public BlendingOptions() {
		this(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA, GLES20.GL_FUNC_ADD);
	}
	
	/**
	 * Constructor.<br>
	 * Valores aceptados para sourceFactor y destinationFactor: <br>
	 * {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR}, {@link GLES20#GL_ONE_MINUS_SRC_COLOR},
	 * {@link GLES20#GL_DST_COLOR}, {@link GLES20#GL_ONE_MINUS_DST_COLOR}, {@link GLES20#GL_SRC_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA}, {@link GLES20#GL_ONE_MINUS_DST_ALPHA},
	 * {@link GLES20#GL_CONSTANT_COLOR}, {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GLES20#GL_CONSTANT_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}<br>
	 * <br>
	 * 
	 * La ecuacion utilizada por defecto es {@link GLES20#GL_FUNC_ADD}
	 * 
	 * @param sourceFactor
	 * @param destinationFactor
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 * @see GLES20#glBlendEquation(int)
	 */
	public BlendingOptions(int sourceFactor, int destinationFactor) {
		this(sourceFactor, destinationFactor, GLES20.GL_FUNC_ADD);
	}
	
	/**
	 * Constructor.<br>
	 * Valores aceptados para sourceFactor y destinationFactor: <br>
	 * {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR}, {@link GLES20#GL_ONE_MINUS_SRC_COLOR},
	 * {@link GLES20#GL_DST_COLOR}, {@link GLES20#GL_ONE_MINUS_DST_COLOR}, {@link GLES20#GL_SRC_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA}, {@link GLES20#GL_ONE_MINUS_DST_ALPHA},
	 * {@link GLES20#GL_CONSTANT_COLOR}, {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GLES20#GL_CONSTANT_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}<br>
	 * <br>
	 * 
	 * Valores aceptados para blendEquationMode:<br>
	 * {@link GLES20#GL_FUNC_ADD}, {@link GLES20#GL_FUNC_SUBTRACT}, {@link GLES20#GL_FUNC_REVERSE_SUBTRACT}.
	 * 
	 * @param sourceFactor
	 * @param destinationFactor
	 * @param blendEquationMode
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 * @see GLES20#glBlendEquation(int)
	 */
	public BlendingOptions(int sourceFactor, int destinationFactor, int blendEquationMode) {
		checkValidFactor(sourceFactor);
		checkValidFactor(destinationFactor);
		checkValidBlendEquationMode(blendEquationMode);
		this.sourceFactor = sourceFactor;
		this.destinationFactor = destinationFactor;
		this.blendEquationMode = blendEquationMode;
	}
	
	/**
	 * Devuelve el sFactor de la funcion de blending
	 * 
	 * @return source factor
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 */
	public int getSourceFactor() {
		return sourceFactor;
	}
	
	/**
	 * Asigna el sFactor de la funcion de blending.<br>
	 * Valores aceptados para sourceFactor: <br>
	 * {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR}, {@link GLES20#GL_ONE_MINUS_SRC_COLOR},
	 * {@link GLES20#GL_DST_COLOR}, {@link GLES20#GL_ONE_MINUS_DST_COLOR}, {@link GLES20#GL_SRC_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA}, {@link GLES20#GL_ONE_MINUS_DST_ALPHA},
	 * {@link GLES20#GL_CONSTANT_COLOR}, {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GLES20#GL_CONSTANT_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}
	 * 
	 * @param sourceFactor
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 */
	public void setSourceFactor(int sourceFactor) {
		checkValidFactor(sourceFactor);
		this.sourceFactor = sourceFactor;
	}
	
	/**
	 * Devuelve el dFactor de la funcion de blending
	 * 
	 * @return destination factor
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 */
	public int getDestinationFactor() {
		return destinationFactor;
	}
	
	/**
	 * Asigna el sFactor de la funcion de blending.<br>
	 * Valores aceptados para destinationFactor: <br>
	 * {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR}, {@link GLES20#GL_ONE_MINUS_SRC_COLOR},
	 * {@link GLES20#GL_DST_COLOR}, {@link GLES20#GL_ONE_MINUS_DST_COLOR}, {@link GLES20#GL_SRC_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA}, {@link GLES20#GL_ONE_MINUS_DST_ALPHA},
	 * {@link GLES20#GL_CONSTANT_COLOR}, {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GLES20#GL_CONSTANT_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}
	 * 
	 * @param destinationFactor
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 */
	public void setDestinationFactor(int destinationFactor) {
		checkValidFactor(destinationFactor);
		this.destinationFactor = destinationFactor;
	}
	
	/**
	 * Devuelve el modo de blending (ecuacion utilizada)
	 * 
	 * @return {@link GLES20#GL_FUNC_ADD}, {@link GLES20#GL_FUNC_SUBTRACT} o {@link GLES20#GL_FUNC_REVERSE_SUBTRACT}
	 * 
	 * @see GLES20#glBlendEquation(int)
	 */
	public int getBlendEquationMode() {
		return blendEquationMode;
	}
	
	/**
	 * Asigna la ecuacion de blending utilizada
	 * 
	 * @param blendEquationMode {@link GLES20#GL_FUNC_ADD}, {@link GLES20#GL_FUNC_SUBTRACT} o {@link GLES20#GL_FUNC_REVERSE_SUBTRACT}
	 * 
	 * @see GLES20#glBlendEquation(int)
	 */
	public void setBlendEquationMode(int blendEquationMode) {
		checkValidBlendEquationMode(blendEquationMode);
		this.blendEquationMode = blendEquationMode;
	}
	
	/**
	 * Copia el BlendingOptions especificado en este BlendingOptions
	 * 
	 * @param other BlendingOptions
	 */
	public void copy(BlendingOptions other) {
		this.sourceFactor = other.sourceFactor;
		this.destinationFactor = other.destinationFactor;
		this.blendEquationMode = other.blendEquationMode;
	}
	
	/**
	 * Comprueba si el factor especificado es un parametro valido para {@link GLES20#glBlendFunc(int, int)}
	 * 
	 * @param factor Valores aceptados: {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR},
	 *            {@link GLES20#GL_ONE_MINUS_SRC_COLOR}, {@link GLES20#GL_DST_COLOR}, {@link GLES20#GL_ONE_MINUS_DST_COLOR},
	 *            {@link GLES20#GL_SRC_ALPHA}, {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA},
	 *            {@link GLES20#GL_ONE_MINUS_DST_ALPHA}, {@link GLES20#GL_CONSTANT_COLOR}, {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR},
	 *            {@link GLES20#GL_CONSTANT_ALPHA}, {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}
	 */
	private void checkValidFactor(int factor) {
		switch (factor) {
		case GLES20.GL_ZERO:
			break;
		case GLES20.GL_ONE:
			break;
		case GLES20.GL_SRC_COLOR:
			break;
		case GLES20.GL_ONE_MINUS_SRC_COLOR:
			break;
		case GLES20.GL_DST_COLOR:
			break;
		case GLES20.GL_ONE_MINUS_DST_COLOR:
			break;
		case GLES20.GL_SRC_ALPHA:
			break;
		case GLES20.GL_ONE_MINUS_SRC_ALPHA:
			break;
		case GLES20.GL_DST_ALPHA:
			break;
		case GLES20.GL_ONE_MINUS_DST_ALPHA:
			break;
		case GLES20.GL_CONSTANT_COLOR:
			break;
		case GLES20.GL_ONE_MINUS_CONSTANT_COLOR:
			break;
		case GLES20.GL_CONSTANT_ALPHA:
			break;
		case GLES20.GL_ONE_MINUS_CONSTANT_ALPHA:
			break;
		case GLES20.GL_SRC_ALPHA_SATURATE:
			break;
		default:
			throw new IllegalArgumentException("The specified factor is not a valid glBlendFunc(sFactor, dfactor) parameter");
		}
	}
	
	/**
	 * Comprueba si el mode especificado es un parametro valido para {@link GLES20#glBlendEquation(int)}
	 * 
	 * @param mode Valores aceptados: {@link GLES20#GL_FUNC_ADD}, {@link GLES20#GL_FUNC_SUBTRACT}, {@link GLES20#GL_FUNC_REVERSE_SUBTRACT}.
	 */
	private void checkValidBlendEquationMode(int mode) {
		switch (mode) {
		case GLES20.GL_FUNC_ADD:
			break;
		case GLES20.GL_FUNC_SUBTRACT:
			break;
		case GLES20.GL_FUNC_REVERSE_SUBTRACT:
			break;
		default:
			throw new IllegalArgumentException("The specified mode is not a valid glBlendEquation(mode) parameter");
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blendEquationMode;
		result = prime * result + destinationFactor;
		result = prime * result + sourceFactor;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		BlendingOptions other = (BlendingOptions) obj;
		if (blendEquationMode != other.blendEquationMode) {
			return false;
		}
		if (destinationFactor != other.destinationFactor) {
			return false;
		}
		if (sourceFactor != other.sourceFactor) {
			return false;
		}
		return true;
	}
	
}
