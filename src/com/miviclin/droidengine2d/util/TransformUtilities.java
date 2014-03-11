package com.miviclin.droidengine2d.util;

import android.opengl.Matrix;

import com.miviclin.droidengine2d.util.math.Matrix4;
import com.miviclin.droidengine2d.util.math.MatrixFix;

/**
 * Utility methods to transform matrices.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TransformUtilities {

	/**
	 * Applies the following transformations to the specified matrix:<br>
	 * - Translation.<br>
	 * - Scale.
	 * 
	 * @param matrix 4x4 matrix.
	 * @param offset Index of the array where the first element of the matrix is located.
	 * @param tx Translation in the X axis.
	 * @param ty Translation in the Y axis.
	 * @param sx Scale in the X axis.
	 * @param sy Scale in the Y axis.
	 */
	public static void transform2D(float[] matrix, int offset, float tx, float ty, float sx, float sy) {
		float x = tx + sx / 2;
		float y = ty + sy / 2;
		Matrix.setIdentityM(matrix, offset);
		Matrix.translateM(matrix, offset, x, y, 0.0f);
		Matrix.scaleM(matrix, offset, sx, sy, 0.0f);
	}

	/**
	 * Applies the following transformations to the specified matrix:<br>
	 * - Translation.<br>
	 * - Rotation around the origin.<br>
	 * - Scale.
	 * 
	 * @param matrix 4x4 matrix.
	 * @param offset Index of the array where the first element of the matrix is located.
	 * @param tx Translation in the X axis.
	 * @param ty Translation in the Y axis.
	 * @param rotationAroundCenter Rotation angle around the origin (scale / 2).
	 * @param sx Scale in the X axis.
	 * @param sy Scale in the Y axis.
	 */
	public static void transform2D(float[] matrix, int offset, float tx, float ty, float rotationAroundCenter,
			float sx, float sy) {

		float x = tx + sx / 2;
		float y = ty + sy / 2;
		Matrix.setIdentityM(matrix, offset);
		Matrix.translateM(matrix, offset, x, y, 0.0f);
		MatrixFix.rotateM(matrix, offset, rotationAroundCenter, 0.0f, 0.0f, 1.0f);
		Matrix.scaleM(matrix, offset, sx, sy, 0.0f);
	}

	/**
	 * Applies the following transformations to the specified matrix:<br>
	 * - Translation.<br>
	 * - Rotation around the origin.<br>
	 * - Scale.
	 * 
	 * @param matrix 4x4 matrix.
	 * @param offset Index of the array where the first element of the matrix is located.
	 * @param tx Translation in the X axis.
	 * @param ty Translation in the Y axis.
	 * @param ox X coordinate of the rotation point (origin).
	 * @param oy Y coordinate of the rotation point (origin).
	 * @param rotationAroundPoint Rotation angle around the origin (scale / 2).
	 * @param sx Scale in the X axis.
	 * @param sy Scale in the Y axis.
	 */
	public static void transform2D(float[] matrix, int offset, float tx, float ty, float ox, float oy,
			float rotationAroundPoint, float sx, float sy) {

		float x = tx + sx / 2;
		float y = ty + sy / 2;
		float originX = ox - x;
		float originY = oy - y;
		Matrix.setIdentityM(matrix, offset);
		Matrix.translateM(matrix, offset, x, y, 0.0f);
		Matrix.translateM(matrix, offset, originX, originY, 0.0f);
		MatrixFix.rotateM(matrix, offset, rotationAroundPoint, 0.0f, 0.0f, 1.0f);
		Matrix.translateM(matrix, offset, -originX, -originY, 0.0f);
		Matrix.scaleM(matrix, offset, sx, sy, 0.0f);
	}

	/**
	 * Applies the following transformations to the specified matrix:<br>
	 * - Translation.<br>
	 * - Rotation around an external point.<br>
	 * - Rotation around the origin.<br>
	 * - Scale.
	 * 
	 * @param matrix 4x4 matrix.
	 * @param offset Index of the array where the first element of the matrix is located.
	 * @param tx Translation in the X axis.
	 * @param ty Translation in the Y axis.
	 * @param ox X coordinate of the rotation point.
	 * @param oy Y coordinate of the rotation point.
	 * @param rotationAroundPoint Rotation angle around the specified point.
	 * @param rotationAroundCenter Rotation angle around the center (scale / 2)
	 * @param sx Scale in the X axis.
	 * @param sy Scale in the Y axis.
	 */
	public static void transform2D(float[] matrix, int offset, float tx, float ty, float ox, float oy,
			float rotationAroundPoint, float rotationAroundCenter, float sx, float sy) {

		float x = tx + sx / 2;
		float y = ty + sy / 2;
		float originX = ox - x;
		float originY = oy - y;
		Matrix.setIdentityM(matrix, offset);
		Matrix.translateM(matrix, offset, x, y, 0.0f);
		Matrix.translateM(matrix, offset, originX, originY, 0.0f);
		MatrixFix.rotateM(matrix, offset, rotationAroundPoint, 0.0f, 0.0f, 1.0f);
		Matrix.translateM(matrix, offset, -originX, -originY, 0.0f);
		MatrixFix.rotateM(matrix, offset, rotationAroundCenter, 0.0f, 0.0f, 1.0f);
		Matrix.scaleM(matrix, offset, sx, sy, 0.0f);
	}

	/**
	 * Applies the following transformations to the specified matrix:<br>
	 * - Translation.<br>
	 * - Scale.
	 * 
	 * @param matrix 4x4 matrix.
	 * @param tx Translation in the X axis.
	 * @param ty Translation in the Y axis.
	 * @param sx Scale in the X axis.
	 * @param sy Scale in the Y axis.
	 */
	public static void transform2D(Matrix4 matrix, float tx, float ty, float sx, float sy) {
		matrix.setIdentity();
		matrix.translate(tx, ty, 0.0f);
		matrix.scale(sx, sy, 0.0f);
	}

	/**
	 * Applies the following transformations to the specified matrix:<br>
	 * - Translation.<br>
	 * - Rotation around the origin.<br>
	 * - Scale.
	 * 
	 * @param matrix 4x4 matrix.
	 * @param tx Translation in the X axis.
	 * @param ty Translation in the Y axis.
	 * @param rotationAroundCenter Rotation angle around the origin (scale / 2).
	 * @param sx Scale in the X axis.
	 * @param sy Scale in the Y axis.
	 */
	public static void transform2D(Matrix4 matrix, float tx, float ty, float rotationAroundCenter, float sx, float sy) {
		matrix.setIdentity();
		matrix.translate(tx, ty, 0.0f);
		matrix.rotate(rotationAroundCenter, 0.0f, 0.0f, 1.0f);
		matrix.scale(sx, sy, 0.0f);
	}

	/**
	 * Applies the following transformations to the specified matrix:<br>
	 * - Translation.<br>
	 * - Rotation around the origin.<br>
	 * - Scale.
	 * 
	 * @param matrix 4x4 matrix.
	 * @param tx Translation in the X axis.
	 * @param ty Translation in the Y axis.
	 * @param ox X coordinate of the rotation point (origin).
	 * @param oy Y coordinate of the rotation point (origin).
	 * @param rotationAroundPoint Rotation angle around the origin (scale / 2).
	 * @param sx Scale in the X axis.
	 * @param sy Scale in the Y axis.
	 */
	public static void transform2D(Matrix4 matrix, float tx, float ty, float ox, float oy, float rotationAroundPoint,
			float sx, float sy) {

		float originX = ox - tx;
		float originY = oy - ty;
		matrix.setIdentity();
		matrix.translate(tx, tx, 0.0f);
		matrix.translate(originX, originY, 0.0f);
		matrix.rotate(rotationAroundPoint, 0.0f, 0.0f, 1.0f);
		matrix.translate(-originX, -originY, 0.0f);
		matrix.scale(sx, sy, 0.0f);
	}

	/**
	 * Applies the following transformations to the specified matrix:<br>
	 * - Translation.<br>
	 * - Rotation around an external point.<br>
	 * - Rotation around the origin.<br>
	 * - Scale.
	 * 
	 * @param matrix 4x4 matrix.
	 * @param tx Translation in the X axis.
	 * @param ty Translation in the Y axis.
	 * @param ox X coordinate of the rotation point.
	 * @param oy Y coordinate of the rotation point.
	 * @param rotationAroundPoint Rotation angle around the specified point.
	 * @param rotationAroundCenter Rotation angle around the center (scale / 2)
	 * @param sx Scale in the X axis.
	 * @param sy Scale in the Y axis.
	 */
	public static void transform2D(Matrix4 matrix, float tx, float ty, float ox, float oy, float rotationAroundPoint,
			float rotationAroundCenter, float sx, float sy) {

		float originX = ox - tx;
		float originY = oy - ty;
		matrix.setIdentity();
		matrix.translate(tx, tx, 0.0f);
		matrix.translate(originX, originY, 0.0f);
		matrix.rotate(rotationAroundPoint, 0.0f, 0.0f, 1.0f);
		matrix.translate(-originX, -originY, 0.0f);
		matrix.rotate(rotationAroundCenter, 0.0f, 0.0f, 1.0f);
		matrix.scale(sx, sy, 0.0f);
	}

}
