package com.miviclin.droidengine2d.util.math;

import android.opengl.Matrix;

/**
 * Representa una matriz de 4x4.
 * 
 * @author Miguel Vicente Linares
 * 
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
	 * Constructor
	 */
	public Matrix4() {
		this.values[M00] = 1.0f;
		this.values[M11] = 1.0f;
		this.values[M22] = 1.0f;
		this.values[M33] = 1.0f;
	}
	
	/**
	 * Devuelve el array de valores que componen la matriz. Se puede acceder a ellos individualmente mediante los indices:<br>
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
	 * Asigna el array especificado como representacion interna de la matriz.<br>
	 * El array debe ser de 16 elementos.
	 * 
	 * @param values Nuevos valores
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 set(float[] values) {
		if (values.length != 16) {
			throw new IllegalArgumentException("values.length must be 16");
		}
		this.values = values;
		return this;
	}
	
	/**
	 * Sustituye los elementos de la matriz por los 16 primeros elementos del array especificado<br>
	 * El array debe ser de 16 elementos.<br>
	 * Este metodo es equivalente a llamar a {@code setValues(values, 0)}
	 * 
	 * @param values Nuevos valores
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 setValues(float[] values) {
		return setValues(values, 0);
	}
	
	/**
	 * Sustituye los elementos de la matriz por 16 elementos del array especificado, siendo el primer elemento el situado en el indice
	 * "offset"<br>
	 * El array debe ser de 16 elementos.
	 * 
	 * @param values Nuevos valores
	 * @param offset Posicion del primer elemento a copiar
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 setValues(float[] values, int offset) {
		if ((values.length - offset) < 16) {
			throw new IllegalArgumentException("There should be at least 16 values");
		}
		System.arraycopy(values, offset, this.values, 0, this.values.length);
		return this;
	}
	
	/**
	 * Convierte la matriz en la matriz identidad.
	 * 
	 * @return Referencia a esta matriz, para poder encadenar operaciones
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
	 * Invierte la matriz
	 * 
	 * @return true si la matriz se puede invertir, false en caso contrario
	 */
	public boolean invert() {
		return Matrix.invertM(values, 0, values, 0);
	}
	
	/**
	 * Transpone la matriz
	 * 
	 * @return Referencia a esta matriz, para poder encadenar operaciones
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
	 * Multiplica la matriz por la matriz especificada (16 valores del array comenzando por la posicion offset) y sobreescribe esta matriz
	 * con el resultado de la multiplicacion.
	 * 
	 * @param matrix Matriz representada en formato array
	 * @param offset Indice del array en el que se encuentra el primer valor
	 * @return Referencia a esta matriz, para poder encadenar operaciones
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
	 * Multiplica la matriz por la matriz especificada y sobreescribe esta matriz con el resultado de la multiplicacion.
	 * 
	 * @param matrix Matriz
	 * @return Referencia a esta matriz, para poder encadenar operaciones
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
	 * Multiplica la matriz por el vector especificado y sobreescribe esta matriz con el resultado de la multiplicacion.
	 * 
	 * @param x Coordenada x del vector
	 * @param y Coordenada y del vector
	 * @param z Coordenada z del vector
	 * @param w Coordenada w del vector
	 * @return Referencia a esta matriz, para poder encadenar operaciones
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
	 * Multiplica la matriz por el vector especificado y sobreescribe esta matriz con el resultado de la multiplicacion.
	 * 
	 * @param vector Vector en formato array
	 * @param offset Indice del primer elemento del vector en el array
	 * @return Referencia a esta matriz, para poder encadenar operaciones
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
	 * Translada la matriz
	 * 
	 * @param x Translacion en el eje X
	 * @param y Translacion en el eje Y
	 * @param z Translacion en el eje Z
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 translate(float x, float y, float z) {
		Matrix.translateM(values, 0, x, y, z);
		return this;
	}
	
	/**
	 * Translada la matriz
	 * 
	 * @param translation Translacion a aplicar
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 translate(Vector3 translation) {
		Matrix.translateM(values, 0, translation.getX(), translation.getY(), translation.getZ());
		return this;
	}
	
	/**
	 * Escala la matriz
	 * 
	 * @param x Escala en el eje X
	 * @param y Escala en el eje Y
	 * @param z Escala en el eje Z
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 scale(float x, float y, float z) {
		Matrix.scaleM(values, 0, x, y, z);
		return this;
	}
	
	/**
	 * Escala la matriz
	 * 
	 * @param scaleFactor Escala a aplicar
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 scale(Vector3 scaleFactor) {
		Matrix.translateM(values, 0, scaleFactor.getX(), scaleFactor.getY(), scaleFactor.getZ());
		return this;
	}
	
	/**
	 * Rota la matriz
	 * 
	 * @param angle Angulo de rotacion, en grados
	 * @param x Escala de rotacion en el eje X
	 * @param y Escala de rotacion en el eje y
	 * @param z Escala de rotacion en el eje Z
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 rotate(float angle, float x, float y, float z) {
		MatrixFix.rotateM(values, 0, angle, x, y, z);
		return this;
	}
	
	/**
	 * Rota la matriz
	 * 
	 * @param angle Angulo de rotacion, en grados
	 * @param scaleFactor Escala de rotacion alrededor de los 3 ejes
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 rotate(float angle, Vector3 scaleFactor) {
		MatrixFix.rotateM(values, 0, angle, scaleFactor.getX(), scaleFactor.getY(), scaleFactor.getZ());
		return this;
	}
	
	/**
	 * Define una transformacion en terminos de punto en el que se encuentra el ojo, centro de vista (punto hacia el que mira el ojo) y up
	 * vector (vector que define la direccion del sistema de coordenadas).
	 * 
	 * @param eyeX Coordenada X del ojo
	 * @param eyeY Coordenada Y del ojo
	 * @param eyeZ Coordenada Z del ojo
	 * @param centerX Coordenada X del centro de vista
	 * @param centerY Coordenada Y del centro de vista
	 * @param centerZ Coordenada Z del centro de vista
	 * @param upX Coordenada X del up vector
	 * @param upY Coordenada Y del up vector
	 * @param upZ Coordenada Z del up vector
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 setLookAt(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
		Matrix.setLookAtM(values, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
		return this;
	}
	
	/**
	 * Define una transformacion en terminos de punto en el que se encuentra el ojo, centro de vista (punto hacia el que mira el ojo) y up
	 * vector (vector que define la direccion del sistema de coordenadas).
	 * 
	 * @param eye ojo
	 * @param center centro de vista
	 * @param up up vector
	 * @return Referencia a esta matriz, para poder encadenar operaciones
	 */
	public Matrix4 setLookAt(Vector3 eye, Vector3 center, Vector3 up) {
		Matrix.setLookAtM(values, 0,
				eye.getX(), eye.getY(), eye.getZ(),
				center.getX(), center.getY(), center.getZ(),
				up.getX(), up.getY(), up.getZ());
		
		return this;
	}
	
	/**
	 * Convierte la matriz en una matriz de proyeccion ortografica.
	 * 
	 * @param left Limite izquierdo
	 * @param right Limite derecho
	 * @param bottom Limite inferior
	 * @param top Limite superior
	 * @param near Limite mas cercano al ojo
	 * @param far Limite mas lejano al ojo
	 * @return Referencia a esta matriz, para poder encadenar operaciones
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
	 * Convierte la matriz en una matriz de proyeccion con perspectiva.
	 * 
	 * @param left Limite izquierdo
	 * @param right Limite derecho
	 * @param bottom Limite inferior
	 * @param top Limite superior
	 * @param near Limite mas cercano al ojo
	 * @param far Limite mas lejano al ojo
	 * @return Referencia a esta matriz, para poder encadenar operaciones
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
