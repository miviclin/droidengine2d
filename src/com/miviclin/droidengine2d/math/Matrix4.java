package com.miviclin.droidengine2d.math;

import android.opengl.Matrix;

public class Matrix4 {
	
	public final static int M00 = 0;
	public final static int M01 = 4;
	public final static int M02 = 8;
	public final static int M03 = 12;
	public final static int M10 = 1;
	public final static int M11 = 5;
	public final static int M12 = 9;
	public final static int M13 = 13;
	public final static int M20 = 2;
	public final static int M21 = 6;
	public final static int M22 = 10;
	public final static int M23 = 14;
	public final static int M30 = 3;
	public final static int M31 = 7;
	public final static int M32 = 11;
	public final static int M33 = 15;
	
	private float[] values = new float[16];
	private float[] tmpMatrix = new float[16];
	private float[] tmpVector = new float[4];
	
	public Matrix4() {
		this.values[M00] = 1.0f;
		this.values[M11] = 1.0f;
		this.values[M22] = 1.0f;
		this.values[M33] = 1.0f;
	}
	
	public float[] getValues() {
		return values;
	}
	
	public Matrix4 set(float[] values) {
		this.values = values;
		return this;
	}
	
	public Matrix4 setValues(float[] values) {
		System.arraycopy(values, 0, this.values, 0, this.values.length);
		return this;
	}
	
	public Matrix4 setValues(float[] values, int offset) {
		System.arraycopy(values, offset, this.values, 0, this.values.length);
		return this;
	}
	
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
	
	public boolean invert() {
		return Matrix.invertM(values, 0, values, 0);
	}
	
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
	
	public Matrix4 multiplyM(float[] matrix, int offset) {
		float[] aux;
		Matrix.multiplyMM(tmpMatrix, 0, values, 0, matrix, offset);
		aux = values;
		values = tmpMatrix;
		tmpMatrix = aux;
		return this;
	}
	
	public Matrix4 multiplyM(Matrix4 matrix) {
		float[] aux;
		Matrix.multiplyMM(tmpMatrix, 0, values, 0, matrix.values, 0);
		aux = values;
		values = tmpMatrix;
		tmpMatrix = aux;
		return this;
	}
	
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
	
	public Matrix4 multiplyV(float[] vector, int offset) {
		float[] aux;
		Matrix.multiplyMV(tmpMatrix, 0, values, 0, vector, offset);
		aux = values;
		values = tmpMatrix;
		tmpMatrix = aux;
		return this;
	}
	
	public Matrix4 translate(float x, float y, float z) {
		Matrix.translateM(values, 0, x, y, z);
		return this;
	}
	
	public Matrix4 translate(Vector3 position) {
		Matrix.translateM(values, 0, position.getX(), position.getY(), position.getZ());
		return this;
	}
	
	public Matrix4 scale(float x, float y, float z) {
		Matrix.scaleM(values, 0, x, y, z);
		return this;
	}
	
	public Matrix4 scale(Vector3 scaleFactor) {
		Matrix.translateM(values, 0, scaleFactor.getX(), scaleFactor.getY(), scaleFactor.getZ());
		return this;
	}
	
	public Matrix4 rotate(float angle, float x, float y, float z) {
		MatrixFix.rotateM(values, 0, angle, x, y, z);
		return this;
	}
	
	public Matrix4 rotate(float angle, Vector3 scaleFactor) {
		MatrixFix.rotateM(values, 0, angle, scaleFactor.getX(), scaleFactor.getY(), scaleFactor.getZ());
		return this;
	}
	
	public Matrix4 setLookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
		Matrix.setLookAtM(values, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
		return this;
	}
	
	public Matrix4 setLookAt(Vector3 eye, Vector3 center, Vector3 up) {
		Matrix.setLookAtM(values, 0,
				eye.getX(), eye.getY(), eye.getZ(),
				center.getX(), center.getY(), center.getZ(),
				up.getX(), up.getY(), up.getZ());
		
		return this;
	}
	
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
