package com.miviclin.droidengine2d.graphics.sprites;

import com.miviclin.droidengine2d.graphics.textures.TextureRegion;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.Point2D;

/**
 * Sprite
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Sprite {
	
	private TextureRegion textureRegion;
	private Point2D position = new Point2D(0.0f, 0.0f);
	private Point2D center = new Point2D(0.0f, 0.0f);
	private Dimensions2D dimensions = new Dimensions2D(1.0f, 1.0f);
	private Point2D rotationPoint = new Point2D(0.0f, 0.0f);
	private float rotationAroundPoint;
	private float rotationAroundCenter;
	
	/**
	 * Crea un Sprite
	 * 
	 * @param x Coordenada X en pixeles de la esquina superior izquierda del sprite
	 * @param y Coordenada Y en pixeles de la esquina superior izquierda del sprite
	 * @param width Ancho del sprite
	 * @param height Alto del sprite
	 * @param textureRegion TextureRegion (region de una textura que se toma como imagen del sprite)
	 */
	public Sprite(float x, float y, float width, float height, TextureRegion textureRegion) {
		checkDimensions(width, height);
		checkTextureRegion(textureRegion);
		this.position.set(x, y);
		this.dimensions.set(width, height);
		this.rotationAroundPoint = 0.0f;
		this.rotationAroundCenter = 0.0f;
		this.textureRegion = textureRegion;
		this.center.set(width / 2, height / 2);
	}
	
	/**
	 * Devuelve la posicion del sprite en la pantalla
	 * 
	 * @return punto en el que esta situada la esquina superior izquierda del sprite
	 */
	public Point2D getPosition() {
		return position;
	}
	
	/**
	 * Asigna la posicion del sprite
	 * 
	 * @param x coordenada X, en pixeles
	 * @param y coordenada Y, en pixeles
	 */
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
	
	/**
	 * Asigna la posicion del sprite
	 * 
	 * @param position posicion en la pantalla
	 */
	public void setPosition(Point2D position) {
		this.position.set(position);
	}
	
	/**
	 * Devuelve las dimensiones del sprite
	 * 
	 * @return dimensiones del sprite
	 */
	public Dimensions2D getDimensions() {
		return dimensions;
	}
	
	/**
	 * Asigna las dimensiones del sprite
	 * 
	 * @param width Ancho
	 * @param height Alto
	 */
	public void setDimensions(float width, float height) {
		dimensions.set(width, height);
		center.set(width / 2, height / 2);
	}
	
	/**
	 * Devuelve el angulo de rotacion del sprite sobre su centro
	 * 
	 * @return angulo
	 */
	public float getRotation() {
		return rotationAroundCenter;
	}
	
	/**
	 * Asigna el angulo de rotacion del sprite sobre su centro
	 * 
	 * @param angle angulo
	 */
	public void setRotation(float angle) {
		rotationAroundCenter = angle;
	}
	
	/**
	 * Devuelve el angulo de rotacion del sprite sobre un punto externo
	 * 
	 * @return angulo
	 */
	public float getRotationAroundPoint() {
		return rotationAroundPoint;
	}
	
	/**
	 * Devuelve el punto externo de rotacion del sprite
	 * 
	 * @return Punto externo al sprite
	 */
	public Point2D getRotationPoint() {
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
	public void setRotationAroundExternalPoint(float angle, Point2D point) {
		rotationAroundPoint = angle;
		rotationPoint.set(point);
	}
	
	/**
	 * Devuelve la region de la textura correspondiente al sprite
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	/**
	 * Asigna un TextureRegion al sprite
	 * 
	 * @param textureRegion Nuevo TextureRegion
	 * @throws IllegalArgumentException Si el TextureRegion especificado es null
	 */
	public void setTextureRegion(TextureRegion textureRegion) {
		checkTextureRegion(textureRegion);
		this.textureRegion = textureRegion;
	}
	
	/**
	 * Devuelve el punto que esta en el centro del sprite
	 * 
	 * @return Point2D
	 */
	public Point2D getCenter() {
		return center;
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
	
	/**
	 * Comprueba que el TextureRegion especificado no sea null y lanza una excepcion en caso contrario
	 * 
	 * @param textureRegion TextureRegion
	 */
	private void checkTextureRegion(TextureRegion textureRegion) {
		if (textureRegion == null) {
			throw new IllegalArgumentException("The TextureRegion can not be null");
		}
	}
	
}
