package com.miviclin.droidengine2d.util;

/**
 * Representa las dimensiones de un objeto en un espacio 3D
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Dimensions3D {
	
	private float width;
	private float height;
	private float depth;
	
	/**
	 * Constructor
	 * 
	 * @param width Ancho
	 * @param height Alto
	 * @param depth Profundidad
	 */
	public Dimensions3D(float width, float height, float depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	/**
	 * Asigna nuevos valores a las dimensiones
	 * 
	 * @param width Ancho
	 * @param height Alto
	 * @param depth Profundidad
	 */
	public void set(float width, float height, float depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	/**
	 * Asigna nuevos valores a las dimensiones
	 * 
	 * @param dimensions Dimensions3D cuyos valores sobreescribiran a los valores actuales
	 */
	public void set(Dimensions3D dimensions) {
		this.width = dimensions.width;
		this.height = dimensions.height;
		this.depth = dimensions.depth;
	}
	
	/**
	 * Devuelve el ancho
	 * 
	 * @return ancho
	 */
	public float getWidth() {
		return width;
	}
	
	/**
	 * Asigna un nuevo valor al ancho
	 * 
	 * @param width Nuevo valor
	 */
	public void setWidth(float width) {
		this.width = width;
	}
	
	/**
	 * Devuelve el alto
	 * 
	 * @return alto
	 */
	public float getHeight() {
		return height;
	}
	
	/**
	 * Asigna un nuevo valor al alto
	 * 
	 * @param height Nuevo valor
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	/**
	 * Devuelve la profundidad
	 * 
	 * @return profundidad
	 */
	public float getDepth() {
		return depth;
	}
	
	/**
	 * Asigna un nuevo valor a la profundidad
	 * 
	 * @param depth Nuevo valor
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}
	
	@Override
	public String toString() {
		return "(" + width + ", " + height + ", " + depth + ")";
	}
	
}
