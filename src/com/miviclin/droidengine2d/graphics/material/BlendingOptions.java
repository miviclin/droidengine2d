package com.miviclin.droidengine2d.graphics.material;

import android.opengl.GLES20;

/**
 * Blending options.
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
	 * The default source factor is {@link GLES20#GL_SRC_ALPHA}.<br>
	 * The default destination factor is {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}.<br>
	 * The default blending equation is {@link GLES20#GL_FUNC_ADD}.
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 * @see GLES20#glBlendEquation(int)
	 */
	public BlendingOptions() {
		this(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA, GLES20.GL_FUNC_ADD);
	}

	/**
	 * Constructor.<br>
	 * Supported values for source factor and destination factor:<br>
	 * {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR}, {@link GLES20#GL_ONE_MINUS_SRC_COLOR}
	 * , {@link GLES20#GL_DST_COLOR}, {@link GLES20#GL_ONE_MINUS_DST_COLOR}, {@link GLES20#GL_SRC_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA}, {@link GLES20#GL_ONE_MINUS_DST_ALPHA},
	 * {@link GLES20#GL_CONSTANT_COLOR}, {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GLES20#GL_CONSTANT_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}<br>
	 * <br>
	 * 
	 * The default blending equation is {@link GLES20#GL_FUNC_ADD}
	 * 
	 * @param sourceFactor Source factor.
	 * @param destinationFactor Destination factor.
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 * @see GLES20#glBlendEquation(int)
	 */
	public BlendingOptions(int sourceFactor, int destinationFactor) {
		this(sourceFactor, destinationFactor, GLES20.GL_FUNC_ADD);
	}

	/**
	 * Constructor.<br>
	 * Supported values for source factor and destination factor:<br>
	 * {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR}, {@link GLES20#GL_ONE_MINUS_SRC_COLOR}
	 * , {@link GLES20#GL_DST_COLOR}, {@link GLES20#GL_ONE_MINUS_DST_COLOR}, {@link GLES20#GL_SRC_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA}, {@link GLES20#GL_ONE_MINUS_DST_ALPHA},
	 * {@link GLES20#GL_CONSTANT_COLOR}, {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GLES20#GL_CONSTANT_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}<br>
	 * <br>
	 * 
	 * Supported values for the blending equation:<br>
	 * {@link GLES20#GL_FUNC_ADD}, {@link GLES20#GL_FUNC_SUBTRACT}, {@link GLES20#GL_FUNC_REVERSE_SUBTRACT}.
	 * 
	 * @param sourceFactor Source factor.
	 * @param destinationFactor Destination factor.
	 * @param blendEquationMode Blending equation.
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
	 * Returns the source factor of the blending function.
	 * 
	 * @return source factor
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 */
	public int getSourceFactor() {
		return sourceFactor;
	}

	/**
	 * Sets the source factor of the blending function.<br>
	 * Supported values for source factor:<br>
	 * {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR}, {@link GLES20#GL_ONE_MINUS_SRC_COLOR}
	 * , {@link GLES20#GL_DST_COLOR}, {@link GLES20#GL_ONE_MINUS_DST_COLOR}, {@link GLES20#GL_SRC_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA}, {@link GLES20#GL_ONE_MINUS_DST_ALPHA},
	 * {@link GLES20#GL_CONSTANT_COLOR}, {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GLES20#GL_CONSTANT_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}
	 * 
	 * @param sourceFactor Source factor.
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 */
	public void setSourceFactor(int sourceFactor) {
		checkValidFactor(sourceFactor);
		this.sourceFactor = sourceFactor;
	}

	/**
	 * Returns the destination factor of the blending function.
	 * 
	 * @return destination factor
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 */
	public int getDestinationFactor() {
		return destinationFactor;
	}

	/**
	 * Sets the destination factor of the blending function.<br>
	 * Supported values for source factor:<br>
	 * {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR}, {@link GLES20#GL_ONE_MINUS_SRC_COLOR}
	 * , {@link GLES20#GL_DST_COLOR}, {@link GLES20#GL_ONE_MINUS_DST_COLOR}, {@link GLES20#GL_SRC_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA}, {@link GLES20#GL_ONE_MINUS_DST_ALPHA},
	 * {@link GLES20#GL_CONSTANT_COLOR}, {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GLES20#GL_CONSTANT_ALPHA},
	 * {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}
	 * 
	 * @param destinationFactor Destination factor.
	 * 
	 * @see GLES20#glBlendFunc(int, int)
	 */
	public void setDestinationFactor(int destinationFactor) {
		checkValidFactor(destinationFactor);
		this.destinationFactor = destinationFactor;
	}

	/**
	 * Returns the blending equation.
	 * 
	 * @return {@link GLES20#GL_FUNC_ADD}, {@link GLES20#GL_FUNC_SUBTRACT} o {@link GLES20#GL_FUNC_REVERSE_SUBTRACT}
	 * 
	 * @see GLES20#glBlendEquation(int)
	 */
	public int getBlendEquationMode() {
		return blendEquationMode;
	}

	/**
	 * Sets the blending equation.
	 * 
	 * @param blendEquationMode {@link GLES20#GL_FUNC_ADD}, {@link GLES20#GL_FUNC_SUBTRACT} or
	 *            {@link GLES20#GL_FUNC_REVERSE_SUBTRACT}
	 * 
	 * @see GLES20#glBlendEquation(int)
	 */
	public void setBlendEquationMode(int blendEquationMode) {
		checkValidBlendEquationMode(blendEquationMode);
		this.blendEquationMode = blendEquationMode;
	}

	/**
	 * Copies the specified BlendingOptions into this BlendingOptions.
	 * 
	 * @param other BlendingOptions
	 */
	public void copy(BlendingOptions other) {
		this.sourceFactor = other.sourceFactor;
		this.destinationFactor = other.destinationFactor;
		this.blendEquationMode = other.blendEquationMode;
	}

	/**
	 * Checks if the specified factor is a valid param for {@link GLES20#glBlendFunc(int, int)}.
	 * 
	 * @param factor Supported factors: {@link GLES20#GL_ZERO}, {@link GLES20#GL_ONE}, {@link GLES20#GL_SRC_COLOR},
	 *            {@link GLES20#GL_ONE_MINUS_SRC_COLOR}, {@link GLES20#GL_DST_COLOR},
	 *            {@link GLES20#GL_ONE_MINUS_DST_COLOR}, {@link GLES20#GL_SRC_ALPHA},
	 *            {@link GLES20#GL_ONE_MINUS_SRC_ALPHA}, {@link GLES20#GL_DST_ALPHA},
	 *            {@link GLES20#GL_ONE_MINUS_DST_ALPHA}, {@link GLES20#GL_CONSTANT_COLOR},
	 *            {@link GLES20#GL_ONE_MINUS_CONSTANT_COLOR}, {@link GLES20#GL_CONSTANT_ALPHA},
	 *            {@link GLES20#GL_ONE_MINUS_CONSTANT_ALPHA}, {@link GLES20#GL_SRC_ALPHA_SATURATE}
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
			throw new IllegalArgumentException("" +
					"The specified factor is not a valid glBlendFunc(sFactor, dfactor) parameter");
		}
	}

	/**
	 * Checks if the specified blending equation is a valid param for {@link GLES20#glBlendEquation(int)}
	 * 
	 * @param mode Supported blending equations: {@link GLES20#GL_FUNC_ADD}, {@link GLES20#GL_FUNC_SUBTRACT},
	 *            {@link GLES20#GL_FUNC_REVERSE_SUBTRACT}.
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
