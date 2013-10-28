package com.miviclin.droidengine2d.util.math;

/**
 * Vector bidimensional.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Vector2 {

	private float x;
	private float y;

	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Asigna los valores especificados al vector
	 * 
	 * @param x
	 * @param y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Asigna los valores del vector especificado a este vector
	 * 
	 * @param vector Vector2 cuyos valores sobreescribiran a los valores de x, y de este vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 set(Vector2 vector) {
		this.x = vector.x;
		this.y = vector.y;
		return this;
	}

	/**
	 * Copia el vector especificado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector cuyas coordenadas se quieren copiar en output
	 */
	public static final void copy(Vector2 output, Vector2 vector) {
		output.x = vector.x;
		output.y = vector.y;
	}

	/**
	 * Suma a todas las coordenadas del vector el escalar especificado
	 * 
	 * @param scalar Valor que se sumara a las 2 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 add(float scalar) {
		this.x += scalar;
		this.y += scalar;
		return this;
	}

	/**
	 * Suma vector + scalar y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param escalar Valor que se sumara a ambas coordenadas
	 */
	public static final void add(Vector2 output, Vector2 vector, float scalar) {
		output.x = vector.x + scalar;
		output.y = vector.y + scalar;
	}

	/**
	 * Suma los valores especificados a las 2 coordenadas del vector
	 * 
	 * @param x Valor que se sumara a la coordenada X
	 * @param y Valor que se sumara a la coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Suma el vector especificado a este vector
	 * 
	 * @param vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 add(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	/**
	 * Suma v1 + v2 y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 */
	public static final void add(Vector2 output, Vector2 v1, Vector2 v2) {
		output.x = v1.x + v2.x;
		output.y = v1.y + v2.y;
	}

	/**
	 * Resta a todas las coordenadas del vector el escalar especificado
	 * 
	 * @param scalar Valor que se restara a las 2 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 subtract(float scalar) {
		this.x -= scalar;
		this.y -= scalar;
		return this;
	}

	/**
	 * Resta vector - scalar y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param escalar Valor que se restara a ambas coordenadas
	 */
	public static final void subtract(Vector2 output, Vector2 vector, float scalar) {
		output.x = vector.x - scalar;
		output.y = vector.y - scalar;
	}

	/**
	 * Resta los valores especificados a las 2 coordenadas del vector
	 * 
	 * @param x Valor que se restara a la coordenada X
	 * @param y Valor que se restara a la coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * Resta el vector especificado a este vector
	 * 
	 * @param vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 subtract(Vector2 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	/**
	 * Resta v1 - v2 y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 */
	public static final void subtract(Vector2 output, Vector2 v1, Vector2 v2) {
		output.x = v1.x - v2.x;
		output.y = v1.y - v2.y;
	}

	/**
	 * Multiplica las 2 coordenadas del vector por el escalar especificado
	 * 
	 * @param scalar Valor por el cual se multiplicaran las 2 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}

	/**
	 * Multiplica vector * scalar y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param escalar Valor por el que se multiplicaran ambas coordenadas
	 */
	public static final void multiply(Vector2 output, Vector2 vector, float scalar) {
		output.x = vector.x * scalar;
		output.y = vector.y * scalar;
	}

	/**
	 * Multiplica las 2 coordenadas del vector por los valores especificados
	 * 
	 * @param x Valor por el cual se multiplicara la coordenada X
	 * @param y Valor por el cual se multiplicara la coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}

	/**
	 * Multiplica el vector especificado por este vector (coordenada a coordenada)
	 * 
	 * @param vector Las coordenadas del vector seran multiplicadas por la coordenadas de el vector especificado
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 multiply(Vector2 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		return this;
	}

	/**
	 * Multiplica v1 * v2 y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 */
	public static final void multiply(Vector2 output, Vector2 v1, Vector2 v2) {
		output.x = v1.x * v2.x;
		output.y = v1.y * v2.y;
	}

	/**
	 * Divide las 2 coordenadas del vector por el escalar especificado
	 * 
	 * @param scalar Valor por el cual se dividiran las 2 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 divide(float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}

	/**
	 * Divide vector / scalar y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param escalar Valor por el que se dividiran ambas coordenadas
	 */
	public static final void divide(Vector2 output, Vector2 vector, float scalar) {
		output.x = vector.x / scalar;
		output.y = vector.y / scalar;
	}

	/**
	 * Divide las 2 coordenadas del vector por los valores especificados
	 * 
	 * @param x Valor por el cual se dividira la coordenada X
	 * @param y Valor por el cual se dividira la coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 divide(float x, float y) {
		this.x /= x;
		this.y /= y;
		return this;
	}

	/**
	 * Divide este vector por el vector especificado (coordenada a coordenada)
	 * 
	 * @param vector Las coordenadas del vector seran divididas por la coordenadas de el vector especificado
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 divide(Vector2 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
		return this;
	}

	/**
	 * Divide v1 / v2 y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 */
	public static final void divide(Vector2 output, Vector2 v1, Vector2 v2) {
		output.x = v1.x / v2.x;
		output.y = v1.y / v2.y;
	}

	/**
	 * Realiza el producto escalar de este vector por el vector definido por las 2 coordenadas especificadas
	 * 
	 * @param x Coordenada X
	 * @param y Coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final float dotProduct(float x, float y) {
		return this.x * x + this.y * y;
	}

	/**
	 * Realiza el producto escalar de este vector por el vector especificado
	 * 
	 * @param vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final float dotProduct(Vector2 vector) {
		return x * vector.x + y * vector.y;
	}

	/**
	 * Realiza el producto escalar de v1 y v2
	 * 
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 * @return Producto escalar de v1 y v2
	 */
	public static final float dotProduct(Vector2 v1, Vector2 v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}

	/**
	 * Calcula el modulo del vector
	 * 
	 * @return Modulo de este vector
	 */
	public final float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Calcula el modulo del vector especificado
	 * 
	 * @param vector
	 * @return Modulo del vector
	 */
	public static final float length(Vector2 vector) {
		return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y);
	}

	/**
	 * Normaliza este vector
	 * 
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 normalize() {
		float length = (float) Math.sqrt(x * x + y * y);
		if (length == 0) {
			return this;
		}
		return this.divide(length);
	}

	/**
	 * Normaliza el vector especificado y almacena las coordenadas del vector normalizado en output
	 * 
	 * @param output Vector normalizado
	 * @param vector Vector sin normalizar
	 */
	public static final void normalize(Vector2 output, Vector2 vector) {
		float length = Vector2.length(vector);
		if (length == 0) {
			Vector2.copy(output, vector);
			return;
		}
		Vector2.copy(output, vector.divide(length));
	}

	/**
	 * Convierte las coordenadas de este vector a valor absoluto
	 * 
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector2 abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		return this;
	}

	/**
	 * Convierte las coordenadas de vector a valor absoluto y las almacena en output
	 * 
	 * @param output Vector con las coordenadas de vector en valor absoluto
	 * @param vector Vector especificado
	 */
	public static final void abs(Vector2 output, Vector2 vector) {
		output.x = Math.abs(vector.x);
		output.y = Math.abs(vector.y);
	}

	/**
	 * Devuelve la coordenada X de este vector
	 * 
	 * @return X
	 */
	public final float getX() {
		return x;
	}

	/**
	 * Asigna el valor de la coordenada X de este vector
	 * 
	 * @param x Nuevo valor
	 */
	public final void setX(float x) {
		this.x = x;
	}

	/**
	 * Devuelve la coordenada Y de este vector
	 * 
	 * @return Y
	 */
	public final float getY() {
		return y;
	}

	/**
	 * Asigna el valor de la coordenada Y de este vector
	 * 
	 * @param y Nuevo valor
	 */
	public final void setY(float y) {
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
