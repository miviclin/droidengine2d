/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.miviclin.droidengine2d.util.math;

import android.opengl.Matrix;

/**
 * Utility class. Fixes some methods of {@link android.opengl.Matrix}.
 * 
 * @author Miguel Vicente Linares
 */
public class MatrixFix {

	private static float[] temp = new float[32];

	/**
	 * This method has the same funcionality as
	 * {@link android.opengl.Matrix#rotateM(float[], int, float, float, float, float)}. The difference is that the
	 * method of the {@link android.opengl.Matrix} class allocates heap memory in API 10 and lower.<br>
	 * 
	 * Rotates matrix m in place by angle a (in degrees) around the axis (x, y, z)
	 * 
	 * @param m 4x4 matrix.
	 * @param mOffset Index into m where the matrix starts.
	 * @param a Angle to rotate in degrees.
	 * @param x Scale factor x.
	 * @param y Scale factor y.
	 * @param z Scale factor z.
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
