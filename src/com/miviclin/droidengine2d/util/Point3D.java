package com.miviclin.droidengine2d.util;

/**
 * Representa un punto en un espacio 3D
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Point3D {
	
	private float x;
	private float y;
	private float z;
	
	/**
	 * Crea un punto a partir de sus coordenadas
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Translada el punto
	 * 
	 * @param x Valor que se sumara a la coordenada X
	 * @param y Valor que se sumara a la coordenada Y
	 * @param z Valor que se sumara a la coordenada Z
	 */
	public void translate(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	/**
	 * Asigna las coordenadas del punto
	 * 
	 * @param x Nueva coordenada X
	 * @param y Nueva coordenada Y
	 * @param z Nueva coordenada Z
	 */
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Asigna las coordenadas del punto
	 * 
	 * @param point Punto cuyas coordenadas sobreescribiran a las coordenadas actuales de este punto
	 */
	public void set(Point3D point) {
		this.x = point.x;
		this.y = point.y;
		this.z = point.z;
	}
	
	/**
	 * Devuelve la coordenada X del punto
	 * 
	 * @return X
	 */
	public float getX() {
		return x;
	}
	
	/**
	 * Asigna un nuevo valor a la coordenada X del punto
	 * 
	 * @param x Nuevo valor
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Devuelve la coordenada Y del punto
	 * 
	 * @return Y
	 */
	public float getY() {
		return y;
	}
	
	/**
	 * Asigna un nuevo valor a la coordenada Y del punto
	 * 
	 * @param y Nuevo valor
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Devuelve la coordenada Z del punto
	 * 
	 * @return Z
	 */
	public float getZ() {
		return z;
	}
	
	/**
	 * Asigna un nuevo valor a la coordenada Z del punto
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
