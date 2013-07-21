package com.miviclin.droidengine2d.graphics.shape;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Define una forma rectangular que almacena informacion sobre su posicion, rotacion, etc.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class RectangularShape {
	
	private Vector2 position = new Vector2(0.0f, 0.0f);
	private Vector2 center = new Vector2(0.0f, 0.0f);
	private Dimensions2D dimensions = new Dimensions2D(1.0f, 1.0f);
	private Vector2 rotationPoint = new Vector2(0.0f, 0.0f);
	private float rotationAroundPoint;
	private float rotationAroundCenter;
	private Color color;
	
	/**
	 * Crea un RectangularShape
	 * 
	 * @param x Coordenada X en pixeles de la esquina superior izquierda
	 * @param y Coordenada Y en pixeles de la esquina superior izquierda
	 * @param width Ancho
	 * @param height Alto
	 */
	public RectangularShape(float x, float y, float width, float height) {
		checkDimensions(width, height);
		this.position.set(x, y);
		this.dimensions.set(width, height);
		this.rotationAroundPoint = 0.0f;
		this.rotationAroundCenter = 0.0f;
		this.center.set(width / 2, height / 2);
		this.color = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	/**
	 * Devuelve la posicion del RectangularShape en la pantalla
	 * 
	 * @return punto en el que esta situada la esquina superior izquierda
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Asigna la posicion del RectangularShape
	 * 
	 * @param x coordenada X, en pixeles
	 * @param y coordenada Y, en pixeles
	 */
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
	
	/**
	 * Asigna la posicion del RectangularShape
	 * 
	 * @param position posicion en la pantalla
	 */
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}
	
	/**
	 * Devuelve las dimensiones del RectangularShape
	 * 
	 * @return dimensiones del RectangularShape
	 */
	public Dimensions2D getDimensions() {
		return dimensions;
	}
	
	/**
	 * Asigna las dimensiones del RectangularShape
	 * 
	 * @param width Ancho
	 * @param height Alto
	 */
	public void setDimensions(float width, float height) {
		dimensions.set(width, height);
		center.set(width / 2, height / 2);
	}
	
	/**
	 * Devuelve el angulo de rotacion del RectangularShape sobre su centro
	 * 
	 * @return angulo
	 */
	public float getRotation() {
		return rotationAroundCenter;
	}
	
	/**
	 * Asigna el angulo de rotacion del RectangularShape sobre su centro
	 * 
	 * @param angle angulo
	 */
	public void setRotation(float angle) {
		rotationAroundCenter = angle;
	}
	
	/**
	 * Devuelve el angulo de rotacion del RectangularShape sobre un punto externo
	 * 
	 * @return angulo
	 */
	public float getRotationAroundPoint() {
		return rotationAroundPoint;
	}
	
	/**
	 * Devuelve el punto externo de rotacion del RectangularShape
	 * 
	 * @return Punto externo al RectangularShape
	 */
	public Vector2 getRotationPoint() {
		return rotationPoint;
	}
	
	/**
	 * Asigna un punto externo de rotacion y el angulo de rotacion alrededor de dicho punto
	 * 
	 * @param angle angulo
	 * @param pointX Coordenada X del punto externo de rotacion
	 * @param pointY Coordenada Y del punto externo de rotacion
	 */
	public void setRotationAroundExternalPoint(float angle, float pointX, float pointY) {
		rotationAroundPoint = angle;
		rotationPoint.set(pointX, pointY);
	}
	
	/**
	 * Asigna un punto externo de rotacion y el angulo de rotacion alrededor de dicho punto
	 * 
	 * @param angle angulo
	 * @param point Punto externo de rotacion
	 */
	public void setRotationAroundExternalPoint(float angle, Vector2 point) {
		rotationAroundPoint = angle;
		rotationPoint.set(point);
	}
	
	/**
	 * Devuelve el punto que esta en el centro del RectangularShape
	 * 
	 * @return Vector2
	 */
	public Vector2 getCenter() {
		return center;
	}
	
	/**
	 * Devuelve el color del RectangularShape
	 * 
	 * @return Color del RectangularShape
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * Asigna un color al RectangularShape
	 * 
	 * @param color Color
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Comprueba que las dimensiones especificadas sean validas y lanza una excepcion en caso contrario
	 * 
	 * @param width Ancho
	 * @param height Alto
	 */
	private void checkDimensions(float width, float height) {
		if (width <= 0) {
			throw new IllegalArgumentException("width must be greater than 0");
		}
		if (height <= 0) {
			throw new IllegalArgumentException("height must be greater than 0");
		}
	}
	
}
