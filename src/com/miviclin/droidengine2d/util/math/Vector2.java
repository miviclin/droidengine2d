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
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Asigna los valores del vector especificado a este vector
	 * 
	 * @param vector Vector2 cuyos valores sobreescribiran a los valores de x, y de este vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 set(Vector2 vector) {
		this.x = vector.x;
		this.y = vector.y;
		return this;
	}
	
	/**
	 * Suma a todas las coordenadas del vector el escalar especificado
	 * 
	 * @param scalar Valor que se sumara a las 2 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 add(float scalar) {
		this.x += scalar;
		this.y += scalar;
		return this;
	}
	
	/**
	 * Suma los valores especificados a las 2 coordenadas del vector
	 * 
	 * @param x Valor que se sumara a la coordenada X
	 * @param y Valor que se sumara a la coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 add(float x, float y) {
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
	public Vector2 add(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	
	/**
	 * Resta a todas las coordenadas del vector el escalar especificado
	 * 
	 * @param scalar Valor que se restara a las 2 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 subtract(float scalar) {
		this.x -= scalar;
		this.y -= scalar;
		return this;
	}
	
	/**
	 * Resta los valores especificados a las 2 coordenadas del vector
	 * 
	 * @param x Valor que se restara a la coordenada X
	 * @param y Valor que se restara a la coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 subtract(float x, float y) {
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
	public Vector2 subtract(Vector2 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}
	
	/**
	 * Multiplica las 2 coordenadas del vector por el escalar especificado
	 * 
	 * @param scalar Valor por el cual se multiplicaran las 2 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	
	/**
	 * Multiplica las 2 coordenadas del vector por los valores especificados
	 * 
	 * @param x Valor por el cual se multiplicara la coordenada X
	 * @param y Valor por el cual se multiplicara la coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 multiply(float x, float y) {
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
	public Vector2 multiply(Vector2 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		return this;
	}
	
	/**
	 * Divide las 2 coordenadas del vector por el escalar especificado
	 * 
	 * @param scalar Valor por el cual se dividiran las 2 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 divide(float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}
	
	/**
	 * Divide las 2 coordenadas del vector por los valores especificados
	 * 
	 * @param x Valor por el cual se dividira la coordenada X
	 * @param y Valor por el cual se dividira la coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 divide(float x, float y) {
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
	public Vector2 divide(Vector2 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
		return this;
	}
	
	/**
	 * Realiza el producto escalar de este vector por el vector definido por las 2 coordenadas especificadas
	 * 
	 * @param x Coordenada X
	 * @param y Coordenada Y
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public float dotProduct(float x, float y) {
		return this.x * x + this.y * y;
	}
	
	/**
	 * Realiza el producto escalar de este vector por el vector especificado
	 * 
	 * @param vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public float dotProduct(Vector2 vector) {
		return x * vector.x + y * vector.y;
	}
	
	/**
	 * Calcula el modulo del vector
	 * 
	 * @return Modulo de este vector
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Normaliza este vector
	 * 
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector2 normalize() {
		float length = (float) Math.sqrt(x * x + y * y);
		if (length == 0) {
			return this;
		}
		return this.divide(length);
	}
	
	/**
	 * Devuelve la coordenada X de este vector
	 * 
	 * @return X
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Asigna el valor de la coordenada X de este vector
	 * 
	 * @param x Nuevo valor
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Devuelve la coordenada Y de este vector
	 * 
	 * @return Y
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Asigna el valor de la coordenada Y de este vector
	 * 
	 * @param y Nuevo valor
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
