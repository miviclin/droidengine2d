package com.miviclin.droidengine2d.util.math;

import android.opengl.Matrix;

/**
 * This class represents a 4x4 matrix in OpenGL ES format.
 * 
 * @author Miguel Vicente Linares
 * 
 * @see android.opengl.Matrix
 */
public class Matrix4 {

	public static final int M00 = 0;
	public static final int M01 = 4;
	public static final int M02 = 8;
	public static final int M03 = 12;
	public static final int M10 = 1;
	public static final int M11 = 5;
	public static final int M12 = 9;
	public static final int M13 = 13;
	public static final int M20 = 2;
	public static final int M21 = 6;
	public static final int M22 = 10;
	public static final int M23 = 14;
	public static final int M30 = 3;
	public static final int M31 = 7;
	public static final int M32 = 11;
	public static final int M33 = 15;

	private float[] values = new float[16];
	private float[] tmpMatrix = new float[16];
	private float[] tmpVector = new float[4];

	/**
	 * Creates a new Matrix4.<br>
	 * The default value is the identity matrix.
	 */
	public Matrix4() {
		this.values[M00] = 1.0f;
		this.values[M11] = 1.0f;
		this.values[M22] = 1.0f;
		this.values[M33] = 1.0f;
	}

	/**
	 * Returns the backing array with the values of the matrix. The following indices can be used to access each value:<br>
	 * {@link #M00}, {@link #M01}, {@link #M02}, {@link #M03}<br>
	 * {@link #M10}, {@link #M11}, {@link #M12}, {@link #M13}<br>
	 * {@link #M20}, {@link #M21}, {@link #M22}, {@link #M23}<br>
	 * {@link #M30}, {@link #M31}, {@link #M32}, {@link #M33}
	 * 
	 * @return representacion interna de la matriz en un array
	 */
	public float[] getValues() {
		return values;
	}

	/**
	 * Replaces the backing array of values of this matrix with the specified array.
	 * 
	 * @param values New array of values (this array must have 16 elements).
	 * @return this Matrix4
	 */
	public Matrix4 set(float[] values) {
		if (values.length != 16) {
			throw new IllegalArgumentException("values.length must be 16");
		}
		this.values = values;
		return this;
	}

	/**
	 * Replaces the values of this matrix with the first 16 values of the specified array.<br>
	 * Calling this method has the same effect as calling {@code setValues(values, 0)}.
	 * 
	 * @param values Array where the new values are located.
	 * @return this Matrix4
	 */
	public Matrix4 setValues(float[] values) {
		return setValues(values, 0);
	}

	/**
	 * Replaces the values of this matrix with 16 values of the specified array. The specified offset is where
	 * 
	 * @param values Array where the new values are located.
	 * @param offset Position of the first element to be copied into the array of values of this Matrix4.
	 * @return this Matrix4
	 */
	public Matrix4 setValues(float[] values, int offset) {
		if ((values.length - offset) < 16) {
			throw new IllegalArgumentException("There should be at least 16 values");
		}
		System.arraycopy(values, offset, this.values, 0, this.values.length);
		return this;
	}

	/**
	 * Sets this matrix to the identity matrix.
	 * 
	 * @return this Matrix4
	 */
	public Matrix4 setIdentity() {
		values[M00] = 1.0f;
		values[M01] = 0.0f;
		values[M02] = 0.0f;
		values[M03] = 0.0f;
		values[M10] = 0.0f;
		values[M11] = 1.0f;
		values[M12] = 0.0f;
		values[M13] = 0.0f;
		values[M20] = 0.0f;
		values[M21] = 0.0f;
		values[M22] = 1.0f;
		values[M23] = 0.0f;
		values[M30] = 0.0f;
		values[M31] = 0.0f;
		values[M32] = 0.0f;
		values[M33] = 1.0f;
		return this;
	}

	/**
	 * Inverts this matrix.
	 * 
	 * @return true if the matrix could be inverted, false otherwise
	 */
	public boolean invert() {
		return Matrix.invertM(values, 0, values, 0);
	}

	/**
	 * Transposes this matrix.
	 * 
	 * @return this Matrix4
	 */
	public Matrix4 transpose() {
		float m01 = values[M10];
		float m02 = values[M20];
		float m03 = values[M30];
		float m10 = values[M01];
		float m12 = values[M21];
		float m13 = values[M31];
		float m20 = values[M02];
		float m21 = values[M12];
		float m23 = values[M32];
		float m30 = values[M03];
		float m31 = values[M13];
		float m32 = values[M23];

		values[M01] = m01;
		values[M02] = m02;
		values[M03] = m03;
		values[M10] = m10;
		values[M12] = m12;
		values[M13] = m13;
		values[M20] = m20;
		values[M21] = m21;
		values[M23] = m23;
		values[M30] = m30;
		values[M31] = m31;
		values[M32] = m32;
		return this;
	}

	/**
	 * Multiplies this matrix by the specified matrix. The result is stored in this Matrix4.
	 * 
	 * @param matrix 4x4 matrix.
	 * @param offset Offset of the first element of the matrix in the array of values.
	 * @return this Matrix4
	 */
	public Matrix4 multiplyM(float[] matrix, int offset) {
		float[] aux;
		if ((matrix.length - offset) < 16) {
			throw new IllegalArgumentException("There should be at least 16 values");
		}
		Matrix.multiplyMM(tmpMatrix, 0, values, 0, matrix, offset);
		aux = values;
		values = tmpMatrix;
		tmpMatrix = aux;
		return this;
	}

	/**
	 * Multiplies this matrix by the specified matrix. The result is stored in this Matrix4.
	 * 
	 * @param matrix Matrix4.
	 * @return this Matrix4
	 */
	public Matrix4 multiplyM(Matrix4 matrix) {
		float[] aux;
		Matrix.multiplyMM(tmpMatrix, 0, values, 0, matrix.values, 0);
		aux = values;
		values = tmpMatrix;
		tmpMatrix = aux;
		return this;
	}

	/**
	 * Multiplies this matrix by the specified vector. The result is stored in this Matrix4.
	 * 
	 * @param x X coordinate of the vector.
	 * @param y Y coordinate of the vector.
	 * @param z Z coordinate of the vector.
	 * @param w W coordinate of the vector.
	 * @return this Matrix4
	 */
	public Matrix4 multiplyV(float x, float y, float z, float w) {
		float[] aux;
		tmpVector[0] = x;
		tmpVector[1] = y;
		tmpVector[2] = z;
		tmpVector[3] = w;
		Matrix.multiplyMV(tmpMatrix, 0, values, 0, tmpVector, 0);
		aux = values;
		values = tmpMatrix;
		tmpMatrix = aux;
		return this;
	}

	/**
	 * Multiplies this matrix by the specified vector. The result is stored in this Matrix4.
	 * 
	 * @param vector This array contains the values of the vector.
	 * @param offset Offset of the first element of the vector in the array.
	 * @return this Matrix4
	 */
	public Matrix4 multiplyV(float[] vector, int offset) {
		float[] aux;
		if ((vector.length - offset) < 4) {
			throw new IllegalArgumentException("There should be at least 4 values");
		}
		Matrix.multiplyMV(tmpMatrix, 0, values, 0, vector, offset);
		aux = values;
		values = tmpMatrix;
		tmpMatrix = aux;
		return this;
	}

	/**
	 * Translates this matrix.
	 * 
	 * @param x Translation in the X axis.
	 * @param y Translation in the Y axis.
	 * @param z Translation in the Z axis.
	 * @return this Matrix4
	 */
	public Matrix4 translate(float x, float y, float z) {
		Matrix.translateM(values, 0, x, y, z);
		return this;
	}

	/**
	 * Translates this matrix.
	 * 
	 * @param translation Translation.
	 * @return this Matrix4
	 */
	public Matrix4 translate(Vector3 translation) {
		Matrix.translateM(values, 0, translation.getX(), translation.getY(), translation.getZ());
		return this;
	}

	/**
	 * Scales this matrix.
	 * 
	 * @param x Scale in the X axis.
	 * @param y Scale in the Y axis.
	 * @param z Scale in the Z axis.
	 * @return this Matrix4
	 */
	public Matrix4 scale(float x, float y, float z) {
		Matrix.scaleM(values, 0, x, y, z);
		return this;
	}

	/**
	 * Scales this matrix.
	 * 
	 * @param scaleFactor Scale.
	 * @return this Matrix4
	 */
	public Matrix4 scale(Vector3 scaleFactor) {
		Matrix.translateM(values, 0, scaleFactor.getX(), scaleFactor.getY(), scaleFactor.getZ());
		return this;
	}

	/**
	 * Rotates this matrix.
	 * 
	 * @param angle Rotation angle, in degrees.
	 * @param x Scale factor in the X axis.
	 * @param y Scale factor in the Y axis.
	 * @param z Scale factor in the Z axis.
	 * @return this Matrix4
	 */
	public Matrix4 rotate(float angle, float x, float y, float z) {
		MatrixFix.rotateM(values, 0, angle, x, y, z);
		return this;
	}

	/**
	 * Rotates this matrix.
	 * 
	 * @param angle Rotation angle, in degrees.
	 * @param scaleFactor Scale factor.
	 * @return this Matrix4
	 */
	public Matrix4 rotate(float angle, Vector3 scaleFactor) {
		MatrixFix.rotateM(values, 0, angle, scaleFactor.getX(), scaleFactor.getY(), scaleFactor.getZ());
		return this;
	}

	/**
	 * Defines a viewing transformation in terms of an eye point, a center of view, and an up vector.
	 * 
	 * @param eyeX Coordinate X of the eye vector.
	 * @param eyeY Coordinate Y of the eye vector.
	 * @param eyeZ Coordinate Z of the eye vector.
	 * @param centerX Coordinate X of the center vector.
	 * @param centerY Coordinate Y of the center vector.
	 * @param centerZ Coordinate Z of the center vector.
	 * @param upX Coordinate X of the up vector.
	 * @param upY Coordinate Y of the up vector.
	 * @param upZ Coordinate Z of the up vector.
	 * @return this Matrix4
	 */
	public Matrix4 setLookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ,
			float upX, float upY, float upZ) {

		Matrix.setLookAtM(values, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
		return this;
	}

	/**
	 * Defines a viewing transformation in terms of an eye point, a center of view, and an up vector.
	 * 
	 * @param eye Eye vector.
	 * @param center Center vector.
	 * @param up Up vector.
	 * @return this Matrix4
	 */
	public Matrix4 setLookAt(Vector3 eye, Vector3 center, Vector3 up) {
		Matrix.setLookAtM(values, 0,
				eye.getX(), eye.getY(), eye.getZ(),
				center.getX(), center.getY(), center.getZ(),
				up.getX(), up.getY(), up.getZ());

		return this;
	}

	/**
	 * Converts this matrix to an orthographic projection matrix.
	 * 
	 * @param left Left limit.
	 * @param right Right limit.
	 * @param bottom Bottom limit.
	 * @param top Top limit.
	 * @param near Near limit.
	 * @param far Far limit.
	 * @return this Matrix4
	 */
	public Matrix4 setOrtho(float left, float right, float bottom, float top, float near, float far) {
		if (left == right) {
			throw new IllegalArgumentException("left == right");
		}
		if (bottom == top) {
			throw new IllegalArgumentException("bottom == top");
		}
		if (near == far) {
			throw new IllegalArgumentException("near == far");
		}
		Matrix.orthoM(values, 0, left, right, bottom, top, near, far);
		return this;
	}

	/**
	 * Defines a projection matrix in terms of six clip planes.
	 * 
	 * @param left Left limit.
	 * @param right Right limit.
	 * @param bottom Bottom limit.
	 * @param top Top limit.
	 * @param near Near limit.
	 * @param far Far limit.
	 * @return this Matrix4
	 */
	public Matrix4 setFrustum(float left, float right, float bottom, float top, float near, float far) {
		if (left == right) {
			throw new IllegalArgumentException("left == right");
		}
		if (top == bottom) {
			throw new IllegalArgumentException("top == bottom");
		}
		if (near == far) {
			throw new IllegalArgumentException("near == far");
		}
		if (near <= 0.0f) {
			throw new IllegalArgumentException("near <= 0.0f");
		}
		if (far <= 0.0f) {
			throw new IllegalArgumentException("far <= 0.0f");
		}
		Matrix.frustumM(values, 0, left, right, bottom, top, near, far);
		return this;
	}

	@Override
	public String toString() {
		String s = "";
		s += values[M00] + " " + values[M01] + " " + values[M02] + " " + values[M03] + "\n";
		s += values[M10] + " " + values[M11] + " " + values[M12] + " " + values[M13] + "\n";
		s += values[M20] + " " + values[M21] + " " + values[M22] + " " + values[M23] + "\n";
		s += values[M30] + " " + values[M31] + " " + values[M32] + " " + values[M33] + "\n";
		return s;
	}

}
