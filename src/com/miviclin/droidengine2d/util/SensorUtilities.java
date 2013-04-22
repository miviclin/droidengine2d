package com.miviclin.droidengine2d.util;

/**
 * Utilidades para trabajar con los sensores
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class SensorUtilities {
	
	/**
	 * Aplica un filtro a paso bajo para atenuar los valores de input en funcion a los valores de output y alpha
	 * 
	 * @param input Valores que queremos atenuar. No puede ser null
	 * @param output Valores que se utilizaran para atenuar los valores de input. No puede ser null
	 * @param length Numero de valores a atenuar
	 * @param alpha Coeficiente de atenuacion. Valor entre 0 y 1
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
