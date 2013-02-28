package com.miviclin.droidengine2d.util;

/**
 * Representa las dimensiones de un objeto en un espacio 2D
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Dimensions2D {
	
	private float width;
	private float height;
	
	/**
	 * Constructor
	 * 
	 * @param width Ancho
	 * @param height Alto
	 */
	public Dimensions2D(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Asigna nuevos valores a las dimensiones
	 * 
	 * @param width Ancho
	 * @param height Alto
	 */
	public void set(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Asigna nuevos valores a las dimensiones
	 * 
	 * @param dimensions Dimensions2D cuyos valores sobreescribiran a los valores actuales
	 */
	public void set(Dimensions2D dimensions) {
		this.width = dimensions.width;
		this.height = dimensions.height;
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
	
	@Override
	public String toString() {
		return "(" + width + ", " + height + ")";
	}
	
}
