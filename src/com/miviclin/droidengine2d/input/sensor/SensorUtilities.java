package com.miviclin.droidengine2d.input.sensor;

/**
 * SensorUtilities.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class SensorUtilities {

	/**
	 * Applies a low pass filter to the specified vector in order to attenuate the input values using the output values
	 * and alpha.
	 * 
	 * @param input Input values (can not be null).
	 * @param output Output values (can not be null). These values will be modified by the filter.
	 * @param length Number of values to attenuate.
	 * @param alpha Attenuation coefficient (value between 0.0f and 1.0f).
	 */
	public static void lowPassFilter(float[] input, float[] output, int length, float alpha) {
		if (input == null) {
			throw new IllegalArgumentException("input can not be null");
		}
		if (output == null) {
			throw new IllegalArgumentException("output can not be null");
		}
		if (length > Math.min(input.length, output.length)) {
			throw new IllegalArgumentException("The specified length is too big");
		}
		if (alpha < 0 || alpha > 1) {
			throw new IllegalArgumentException("alpha must be a value between 0 and 1");
		}
		for (int i = 0; i < length; i++) {
			output[i] += (alpha * (input[i] - output[i]));
		}
	}

}
