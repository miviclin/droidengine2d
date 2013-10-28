package com.miviclin.droidengine2d.util;

import android.opengl.Matrix;

import com.miviclin.droidengine2d.util.math.Matrix4;
import com.miviclin.droidengine2d.util.math.MatrixFix;

/**
 * Clase que contiene varios metodos para aplicar transformaciones sobre matrices
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TransformUtilities {

	/**
	 * Aplica las siguientes transformaciones sobre una matriz:<br>
	 * - Translacion<br>
	 * - Escala
	 * 
	 * @param matrix matriz de 4x4 en formato array
	 * @param offset indice del array en el que esta el primer elemento de la matriz
	 * @param tx Translacion en el eje X
	 * @param ty Translacion en el eje Y
	 * @param sx Escala en el eje X
	 * @param sy Escala en el eje Y
	 */
	public static void transform2D(float[] matrix, int offset, float tx, float ty, float sx, float sy) {
		float x = tx + sx / 2;
		float y = ty + sy / 2;
		Matrix.setIdentityM(matrix, offset);
		Matrix.translateM(matrix, offset, x, y, 0.0f);
		Matrix.scaleM(matrix, offset, sx, sy, 0.0f);
	}

	/**
	 * Aplica las siguientes transformaciones sobre una matriz:<br>
	 * - Translacion<br>
	 * - Rotacion alrededor del centro<br>
	 * - Escala
	 * 
	 * @param matrix matriz de 4x4 en formato array
	 * @param offset indice del array en el que esta el primer elemento de la matriz
	 * @param tx Translacion en el eje X
	 * @param ty Translacion en el eje Y
	 * @param rotationAroundCenter Angulo de rotacion alrededor del centro (calculado a partir de la escala / 2)
	 * @param sx Escala en el eje X
	 * @param sy Escala en el eje Y
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
	 * Aplica las siguientes transformaciones sobre una matriz:<br>
	 * - Translacion<br>
	 * - Rotacion alrededor de un punto externo<br>
	 * - Escala
	 * 
	 * @param matrix matriz de 4x4 en formato array
	 * @param offset indice del array en el que esta el primer elemento de la matriz
	 * @param tx Translacion en el eje X
	 * @param ty Translacion en el eje Y
	 * @param ox Coordenada X del punto externo de rotacion
	 * @param oy Coordenada Y del punto externo de rotacion
	 * @param rotationAroundPoint Angulo de rotacion alrededor del punto especificado
	 * @param sx Escala en el eje X
	 * @param sy Escala en el eje Y
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
	 * Aplica las siguientes transformaciones sobre una matriz:<br>
	 * - Translacion<br>
	 * - Rotacion alrededor de un punto externo<br>
	 * - Rotacion alrededor del centro<br>
	 * - Escala
	 * 
	 * @param matrix matriz de 4x4 en formato array
	 * @param offset indice del array en el que esta el primer elemento de la matriz
	 * @param tx Translacion en el eje X
	 * @param ty Translacion en el eje Y
	 * @param ox Coordenada X del punto externo de rotacion
	 * @param oy Coordenada Y del punto externo de rotacion
	 * @param rotationAroundPoint Angulo de rotacion alrededor del punto especificado
	 * @param rotationAroundCenter Angulo de rotacion alrededor del centro (calculado a partir de la escala / 2)
	 * @param sx Escala en el eje X
	 * @param sy Escala en el eje Y
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
	 * Aplica las siguientes transformaciones sobre una matriz:<br>
	 * - Translacion<br>
	 * - Escala
	 * 
	 * @param matrix matriz de 4x4
	 * @param tx Translacion en el eje X
	 * @param ty Translacion en el eje Y
	 * @param sx Escala en el eje X
	 * @param sy Escala en el eje Y
	 */
	public static void transform2D(Matrix4 matrix, float tx, float ty, float sx, float sy) {
		matrix.setIdentity();
		matrix.translate(tx, ty, 0.0f);
		matrix.scale(sx, sy, 0.0f);
	}

	/**
	 * Aplica las siguientes transformaciones sobre una matriz:<br>
	 * - Translacion<br>
	 * - Rotacion alrededor del centro<br>
	 * - Escala
	 * 
	 * @param matrix matriz de 4x4
	 * @param tx Translacion en el eje X
	 * @param ty Translacion en el eje Y
	 * @param rotationAroundCenter Angulo de rotacion alrededor del centro (calculado a partir de la escala / 2)
	 * @param sx Escala en el eje X
	 * @param sy Escala en el eje Y
	 */
	public static void transform2D(Matrix4 matrix, float tx, float ty, float rotationAroundCenter, float sx, float sy) {
		matrix.setIdentity();
		matrix.translate(tx, ty, 0.0f);
		matrix.rotate(rotationAroundCenter, 0.0f, 0.0f, 1.0f);
		matrix.scale(sx, sy, 0.0f);
	}

	/**
	 * Aplica las siguientes transformaciones sobre una matriz:<br>
	 * - Translacion<br>
	 * - Rotacion alrededor de un punto externo<br>
	 * - Escala
	 * 
	 * @param matrix matriz de 4x4
	 * @param tx Translacion en el eje X
	 * @param ty Translacion en el eje Y
	 * @param ox Coordenada X del punto externo de rotacion
	 * @param oy Coordenada Y del punto externo de rotacion
	 * @param rotationAroundPoint Angulo de rotacion alrededor del punto especificado
	 * @param sx Escala en el eje X
	 * @param sy Escala en el eje Y
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
	 * Aplica las siguientes transformaciones sobre una matriz:<br>
	 * - Translacion<br>
	 * - Rotacion alrededor de un punto externo<br>
	 * - Rotacion alrededor del centro<br>
	 * - Escala
	 * 
	 * @param matrix matriz de 4x4
	 * @param tx Translacion en el eje X
	 * @param ty Translacion en el eje Y
	 * @param ox Coordenada X del punto externo de rotacion
	 * @param oy Coordenada Y del punto externo de rotacion
	 * @param rotationAroundPoint Angulo de rotacion alrededor del punto especificado
	 * @param rotationAroundCenter Angulo de rotacion alrededor del centro (calculado a partir de la escala / 2)
	 * @param sx Escala en el eje X
	 * @param sy Escala en el eje Y
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
