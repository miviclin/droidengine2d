package com.miviclin.droidengine2d.util.math;

import android.opengl.Matrix;

/**
 * Clase de utilidad que mejora algunos de los metodos de la clase Matrix
 * 
 * @author Miguel Vicente Linares
 * @see Matrix
 */
public class MatrixFix {
	
	private static float[] temp = new float[32];
	
	/**
	 * Este metodo tiene la misma funcionalidad que android.opengl.Matrix.rotateM(...). La diferencia es que Matrix.rotateM(...) en la API
	 * 10 y anteriores asigna memoria en el heap.<br>
	 * <br>
	 * 
	 * Rotates matrix m by angle a (in degrees) around the axis (x, y, z)
	 * 
	 * @param m source matrix
	 * @param mOffset index into m where the matrix starts
	 * @param a angle to rotate in degrees
	 * @param x scale factor x
	 * @param y scale factor y
	 * @param z scale factor z
	 * @see Matrix#rotateM(float[], int, float[], int, float, float, float, float)
	 */
	public static void rotateM(float[] m, int mOffset, float a, float x, float y, float z) {
		synchronized (temp) {
			Matrix.setRotateM(temp, 0, a, x, y, z);
			Matrix.multiplyMM(temp, 16, m, mOffset, temp, 0);
			System.arraycopy(temp, 16, m, mOffset, 16);
		}
	}
	
}
