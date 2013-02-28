package com.miviclin.droidengine2d.util;

/**
 * Representa un punto en un espacio 2D
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Point2D {
	
	private float x;
	private float y;
	
	/**
	 * Crea un punto a partir de sus coordenadas
	 * 
	 * @param x
	 * @param y
	 */
	public Point2D(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Translada el punto
	 * 
	 * @param x Valor que se sumara a la coordenada X
	 * @param y Valor que se sumara a la coordenada Y
	 */
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	/**
	 * Asigna las coordenadas del punto
	 * 
	 * @param x Nueva coordenada X
	 * @param y Nueva coordenada Y
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Asigna las coordenadas del punto
	 * 
	 * @param point Punto cuyas coordenadas sobreescribiran a las coordenadas actuales de este punto
	 */
	public void set(Point2D point) {
		this.x = point.x;
		this.y = point.y;
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
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
