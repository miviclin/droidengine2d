package com.miviclin.droidengine2d.util;

import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Clase que contiene informacion referente a la transformacion de un objeto: posicion, rotacion, escala...
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Transform {
	
	private Vector2 position;
	private Vector2 scale;
	private Vector2 origin;
	private float rotation;
	
	/**
	 * Crea un objeto Transform con la posicion y escala especificadas.<br>
	 * El origen es por defecto el punto (scale.getX() / 2, scale.getY() / 2).
	 * 
	 * @param position Posicion
	 * @param scale Escala
	 */
	public Transform(Vector2 position, Vector2 scale) {
		this(position, scale, new Vector2(scale.getX() / 2, scale.getY() / 2), 0.0f);
	}
	
	/**
	 * Crea un objeto Transform con la posicion, escala, origen y rotacion especificada.
	 * 
	 * @param position Posicion
	 * @param scale Escala
	 * @param origin Origen (Deberia ser un punto entre (0, 0) y (scale.getX(), scale.getY()))
	 * @param rotation Rotacion
	 */
	public Transform(Vector2 position, Vector2 scale, Vector2 origin, float rotation) {
		super();
		checkNotNull(position, "position");
		checkNotNull(scale, "scale");
		checkNotNull(origin, "origin");
		this.position = position;
		this.scale = scale;
		this.origin = origin;
		this.rotation = rotation;
	}
	
	/**
	 * Devuelve el angulo de rotacion sobre el origen
	 * 
	 * @return Angulo de rotacion
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * Asigna el angulo de rotacion sobre el origen
	 * 
	 * @param rotation Angulo de rotacion
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * Devuelve la posicion
	 * 
	 * @return Posicion
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Devuelve la escala
	 * 
	 * @return Escala
	 */
	public Vector2 getScale() {
		return scale;
	}
	
	/**
	 * Devuelve el origen
	 * 
	 * @return Origen
	 */
	public Vector2 getOrigin() {
		return origin;
	}
	
	/**
	 * Comprueba si el vector especificado es null y si lo es lanza una excepcion
	 * 
	 * @param vector Vector2
	 * @param variableName Nombre de la variable para mostrarlo en el mensaje de la excepcion
	 */
	private void checkNotNull(Vector2 vector, String variableName) {
		if (vector == null) {
			throw new IllegalArgumentException(variableName + " can not be null");
		}
	}
	
}
