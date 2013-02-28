package com.miviclin.droidengine2d.util.math;

/**
 * Vector tridimensional.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Vector3 {
	
	private float x;
	private float y;
	private float z;
	
	/**
	 * Constructor
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Asigna los valores especificados al vector
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	/**
	 * Asigna los valores del vector especificado a este vector
	 * 
	 * @param vector Vector3 cuyos valores sobreescribiran a los valores de x, y, z de este vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 set(Vector3 vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
		return this;
	}
	
	/**
	 * Suma a todas las coordenadas del vector el escalar especificado
	 * 
	 * @param scalar Valor que se sumara a las 3 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 add(float scalar) {
		this.x += scalar;
		this.y += scalar;
		this.z += scalar;
		return this;
	}
	
	/**
	 * Suma los valores especificados a las 3 coordenadas del vector
	 * 
	 * @param x Valor que se sumara a la coordenada X
	 * @param y Valor que se sumara a la coordenada Y
	 * @param z Valor que se sumara a la coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	/**
	 * Suma el vector especificado a este vector
	 * 
	 * @param vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 add(Vector3 vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		return this;
	}
	
	/**
	 * Resta a todas las coordenadas del vector el escalar especificado
	 * 
	 * @param scalar Valor que se restara a las 3 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 subtract(float scalar) {
		this.x -= scalar;
		this.y -= scalar;
		this.z -= scalar;
		return this;
	}
	
	/**
	 * Resta los valores especificados a las 3 coordenadas del vector
	 * 
	 * @param x Valor que se restara a la coordenada X
	 * @param y Valor que se restara a la coordenada Y
	 * @param z Valor que se restara a la coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}
	
	/**
	 * Resta el vector especificado a este vector
	 * 
	 * @param vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 subtract(Vector3 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		return this;
	}
	
	/**
	 * Multiplica las 3 coordenadas del vector por el escalar especificado
	 * 
	 * @param scalar Valor por el cual se multiplicaran las 3 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		return this;
	}
	
	/**
	 * Multiplica las 3 coordenadas del vector por los valores especificados
	 * 
	 * @param x Valor por el cual se multiplicara la coordenada X
	 * @param y Valor por el cual se multiplicara la coordenada Y
	 * @param z Valor por el cual se multiplicara la coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 multiply(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}
	
	/**
	 * Multiplica el vector especificado por este vector (coordenada a coordenada)
	 * 
	 * @param vector Las coordenadas del vector seran multiplicadas por la coordenadas de el vector especificado
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 multiply(Vector3 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		this.z *= vector.z;
		return this;
	}
	
	/**
	 * Divide las 3 coordenadas del vector por el escalar especificado
	 * 
	 * @param scalar Valor por el cual se dividiran las 3 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 divide(float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		this.z /= scalar;
		return this;
	}
	
	/**
	 * Divide las 3 coordenadas del vector por los valores especificados
	 * 
	 * @param x Valor por el cual se dividira la coordenada X
	 * @param y Valor por el cual se dividira la coordenada Y
	 * @param z Valor por el cual se dividira la coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 divide(float x, float y, float z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}
	
	/**
	 * Divide este vector por el vector especificado (coordenada a coordenada)
	 * 
	 * @param vector Las coordenadas del vector seran divididas por la coordenadas de el vector especificado
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 divide(Vector3 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
		this.z /= vector.z;
		return this;
	}
	
	/**
	 * Realiza el producto en cruz de este vector por el vector definido por las 3 coordenadas especificadas.
	 * 
	 * @param x Coordenada X
	 * @param y Coordenada Y
	 * @param z Coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 crossProduct(float x, float y, float z) {
		this.x = this.y * z - this.z * y;
		this.y = this.z * x - this.x * z;
		this.z = this.x * y - this.y * x;
		return this;
	}
	
	/**
	 * Realiza el producto en cruz de este vector por el vector especificado
	 * 
	 * @param vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 crossProduct(Vector3 vector) {
		this.x = y * vector.z - z * vector.y;
		this.y = z * vector.x - x * vector.z;
		this.z = x * vector.y - y * vector.x;
		return this;
	}
	
	/**
	 * Realiza el producto escalar de este vector por el vector definido por las 3 coordenadas especificadas
	 * 
	 * @param x Coordenada X
	 * @param y Coordenada Y
	 * @param z Coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public float dotProduct(float x, float y, float z) {
		return this.x * x + this.y * y + this.z * z;
	}
	
	/**
	 * Realiza el producto escalar de este vector por el vector especificado
	 * 
	 * @param vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public float dotProduct(Vector3 vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}
	
	/**
	 * Calcula el modulo del vector
	 * 
	 * @return Modulo de este vector
	 */
	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Normaliza este vector
	 * 
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public Vector3 normalize() {
		float length = (float) Math.sqrt(x * x + y * y + z * z);
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
	
	/**
	 * Devuelve la coordenada Z de este vector
	 * 
	 * @return Z
	 */
	public float getZ() {
		return z;
	}
	
	/**
	 * Asigna el valor de la coordenada Z de este vector
	 * 
	 * @param z Nuevo valor
	 */
	public void setZ(float z) {
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
}
