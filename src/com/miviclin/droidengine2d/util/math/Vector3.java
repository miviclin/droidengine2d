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
	public final Vector3 set(float x, float y, float z) {
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
	public final Vector3 set(Vector3 vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
		return this;
	}
	
	/**
	 * Copia el vector especificado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector cuyas coordenadas se quieren copiar en output
	 */
	public final static void copy(Vector3 output, Vector3 vector) {
		output.x = vector.x;
		output.y = vector.y;
		output.z = vector.z;
	}
	
	/**
	 * Suma a todas las coordenadas del vector el escalar especificado
	 * 
	 * @param scalar Valor que se sumara a las 3 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 add(float scalar) {
		this.x += scalar;
		this.y += scalar;
		this.z += scalar;
		return this;
	}
	
	/**
	 * Suma vector + scalar y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param escalar Valor que se sumara a ambas coordenadas
	 */
	public final static void add(Vector3 output, Vector3 vector, float scalar) {
		output.x = vector.x + scalar;
		output.y = vector.y + scalar;
		output.z = vector.z + scalar;
	}
	
	/**
	 * Suma los valores especificados a las 3 coordenadas del vector
	 * 
	 * @param x Valor que se sumara a la coordenada X
	 * @param y Valor que se sumara a la coordenada Y
	 * @param z Valor que se sumara a la coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 add(float x, float y, float z) {
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
	public final Vector3 add(Vector3 vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		return this;
	}
	
	/**
	 * Suma v1 + v2 y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 */
	public final static void add(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.x + v2.x;
		output.y = v1.y + v2.y;
		output.z = v1.z + v2.z;
	}
	
	/**
	 * Resta a todas las coordenadas del vector el escalar especificado
	 * 
	 * @param scalar Valor que se restara a las 3 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 subtract(float scalar) {
		this.x -= scalar;
		this.y -= scalar;
		this.z -= scalar;
		return this;
	}
	
	/**
	 * Resta vector - scalar y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param escalar Valor que se restara a ambas coordenadas
	 */
	public final static void subtract(Vector3 output, Vector3 vector, float scalar) {
		output.x = vector.x - scalar;
		output.y = vector.y - scalar;
		output.z = vector.z - scalar;
	}
	
	/**
	 * Resta los valores especificados a las 3 coordenadas del vector
	 * 
	 * @param x Valor que se restara a la coordenada X
	 * @param y Valor que se restara a la coordenada Y
	 * @param z Valor que se restara a la coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 subtract(float x, float y, float z) {
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
	public final Vector3 subtract(Vector3 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		return this;
	}
	
	/**
	 * Resta v1 - v2 y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 */
	public final static void subtract(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.x - v2.x;
		output.y = v1.y - v2.y;
		output.z = v1.z - v2.z;
	}
	
	/**
	 * Multiplica las 3 coordenadas del vector por el escalar especificado
	 * 
	 * @param scalar Valor por el cual se multiplicaran las 3 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		return this;
	}
	
	/**
	 * Multiplica vector * scalar y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param escalar Valor por el que se multiplicaran ambas coordenadas
	 */
	public final static void multiply(Vector3 output, Vector3 vector, float scalar) {
		output.x = vector.x * scalar;
		output.y = vector.y * scalar;
		output.z = vector.z * scalar;
	}
	
	/**
	 * Multiplica las 3 coordenadas del vector por los valores especificados
	 * 
	 * @param x Valor por el cual se multiplicara la coordenada X
	 * @param y Valor por el cual se multiplicara la coordenada Y
	 * @param z Valor por el cual se multiplicara la coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 multiply(float x, float y, float z) {
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
	public final Vector3 multiply(Vector3 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		this.z *= vector.z;
		return this;
	}
	
	/**
	 * Multiplica v1 * v2 y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 */
	public final static void multiply(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.x * v2.x;
		output.y = v1.y * v2.y;
		output.z = v1.z * v2.z;
	}
	
	/**
	 * Divide las 3 coordenadas del vector por el escalar especificado
	 * 
	 * @param scalar Valor por el cual se dividiran las 3 coordenadas del vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 divide(float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		this.z /= scalar;
		return this;
	}
	
	/**
	 * Divide vector / scalar y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param escalar Valor por el que se dividiran ambas coordenadas
	 */
	public final static void divide(Vector3 output, Vector3 vector, float scalar) {
		output.x = vector.x / scalar;
		output.y = vector.y / scalar;
		output.z = vector.z / scalar;
	}
	
	/**
	 * Divide las 3 coordenadas del vector por los valores especificados
	 * 
	 * @param x Valor por el cual se dividira la coordenada X
	 * @param y Valor por el cual se dividira la coordenada Y
	 * @param z Valor por el cual se dividira la coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 divide(float x, float y, float z) {
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
	public final Vector3 divide(Vector3 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
		this.z /= vector.z;
		return this;
	}
	
	/**
	 * Divide v1 / v2 y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 */
	public final static void divide(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.x / v2.x;
		output.y = v1.y / v2.y;
		output.z = v1.z / v2.z;
	}
	
	/**
	 * Realiza el producto en cruz de este vector por el vector definido por las 3 coordenadas especificadas.
	 * 
	 * @param x Coordenada X
	 * @param y Coordenada Y
	 * @param z Coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 crossProduct(float x, float y, float z) {
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
	public final Vector3 crossProduct(Vector3 vector) {
		this.x = y * vector.z - z * vector.y;
		this.y = z * vector.x - x * vector.z;
		this.z = x * vector.y - y * vector.x;
		return this;
	}
	
	/**
	 * Realiza el producto en cruz de v1 y v2 y almacena el resultado en output
	 * 
	 * @param output Resultado de la operacion
	 * @param v2 Operando 1
	 * @param v2 Operando 2
	 */
	public final static void crossProduct(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.y * v2.z - v1.z * v2.y;
		output.y = v1.z * v2.x - v1.x * v2.z;
		output.z = v1.x * v2.y - v1.y * v2.x;
	}
	
	/**
	 * Realiza el producto escalar de este vector por el vector definido por las 3 coordenadas especificadas
	 * 
	 * @param x Coordenada X
	 * @param y Coordenada Y
	 * @param z Coordenada Z
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final float dotProduct(float x, float y, float z) {
		return this.x * x + this.y * y + this.z * z;
	}
	
	/**
	 * Realiza el producto escalar de este vector por el vector especificado
	 * 
	 * @param vector
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final float dotProduct(Vector3 vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}
	
	/**
	 * Realiza el producto escalar de v1 y v2
	 * 
	 * @param v1 Operando 1
	 * @param v2 Operando 2
	 * @return Producto escalar de v1 y v2
	 */
	public final static float dotProduct(Vector3 v1, Vector3 v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}
	
	/**
	 * Calcula el modulo del vector
	 * 
	 * @return Modulo de este vector
	 */
	public final float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}
	
	/**
	 * Calcula el modulo del vector especificado
	 * 
	 * @param vector
	 * @return Modulo del vector
	 */
	public final static float length(Vector3 vector) {
		return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
	}
	
	/**
	 * Normaliza este vector
	 * 
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 normalize() {
		float length = (float) Math.sqrt(x * x + y * y + z * z);
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
	public final static void normalize(Vector3 output, Vector3 vector) {
		float length = Vector3.length(vector);
		if (length == 0) {
			Vector3.copy(output, vector);
			return;
		}
		Vector3.copy(output, vector.divide(length));
	}
	
	/**
	 * Convierte las coordenadas de este vector a valor absoluto
	 * 
	 * @return Referencia al propio vector para poder encadenar operaciones
	 */
	public final Vector3 abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
		return this;
	}
	
	/**
	 * Convierte las coordenadas de vector a valor absoluto y las almacena en output
	 * 
	 * @param output Vector con las coordenadas de vector en valor absoluto
	 * @param vector Vector especificado
	 */
	public final static void abs(Vector3 output, Vector3 vector) {
		output.x = Math.abs(vector.x);
		output.y = Math.abs(vector.y);
		output.z = Math.abs(vector.z);
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
	
	/**
	 * Devuelve la coordenada Z de este vector
	 * 
	 * @return Z
	 */
	public final float getZ() {
		return z;
	}
	
	/**
	 * Asigna el valor de la coordenada Z de este vector
	 * 
	 * @param z Nuevo valor
	 */
	public final void setZ(float z) {
		this.z = z;
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
}
