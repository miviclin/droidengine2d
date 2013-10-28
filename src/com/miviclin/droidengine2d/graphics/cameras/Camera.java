package com.miviclin.droidengine2d.graphics.cameras;

import com.miviclin.droidengine2d.util.math.Matrix4;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * Contiene las matrices VIEW Y PROJECTION y es la clase base para los distintos tipos de camara.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Camera {

	private final Vector2 viewportDimensions = new Vector2(1.0f, 1.0f);
	private final Matrix4 viewMatrix = new Matrix4();
	private final Matrix4 projectionMatrix = new Matrix4();
	private final Vector3 eye = new Vector3(0.0f, 0.0f, 5.0f);
	private final Vector3 center = new Vector3(0.0f, 0.0f, 0.0f);
	private final Vector3 up = new Vector3(0.0f, 1.0f, 0.0f);

	private float near = 1.0f;
	private float far = 10.0f;

	/**
	 * Constructor
	 */
	public Camera() {
		super();
	}

	/**
	 * Translada la posicion del ojo.<br>
	 * Los cambios no seran visibles hasta que no se llame a {@link #update()}
	 * 
	 * @param x valor en pixeles que se le suma en el eje X a la posicion actual
	 * @param y valor en pixeles que se le suma en el eje Y a la posicion actual
	 * @param z valor en pixeles que se le suma en el eje Z a la posicion actual
	 */
	public void translate(float x, float y, float z) {
		eye.add(x, y, z);
	}

	/**
	 * Devuelve el ancho del viewport en pixeles
	 * 
	 * @return ancho del viewport
	 */
	public final float getViewportWidth() {
		return viewportDimensions.getX();
	}

	/**
	 * Asigna el ancho del viewport en pixeles<br>
	 * Los cambios no seran visibles hasta que no se llame a {@link #update()}
	 * 
	 * @param viewportWidth Nuevo valor para el ancho del viewport
	 */
	public final void setViewportWidth(float viewportWidth) {
		if (viewportWidth <= 0) {
			throw new IllegalArgumentException("The viewport width must be greater than 0");
		}
		this.viewportDimensions.setX(viewportWidth);
	}

	/**
	 * Devuelve el alto del viewport en pixeles
	 * 
	 * @return alto del viewport
	 */
	public final float getViewportHeight() {
		return viewportDimensions.getY();
	}

	/**
	 * Asigna el alto del viewport en pixeles<br>
	 * Los cambios no seran visibles hasta que no se llame a {@link #update()}
	 * 
	 * @param viewportHeight Nuevo valor para el alto del viewport
	 */
	public final void setViewportHeight(float viewportHeight) {
		if (viewportHeight <= 0) {
			throw new IllegalArgumentException("The viewport height must be greater than 0");
		}
		this.viewportDimensions.setY(viewportHeight);
	}

	/**
	 * Asigna las dimensiones del viewport en pixeles<br>
	 * Los cambios no seran visibles hasta que no se llame a {@link #update()}
	 * 
	 * @param viewportWidth Nuevo valor para el alto del viewport
	 * @param viewportHeight Nuevo valor para el alto del viewport
	 */
	public final void setViewportDimensions(float viewportWidth, float viewportHeight) {
		if (viewportWidth <= 0) {
			throw new IllegalArgumentException("The viewport width must be greater than 0");
		}
		if (viewportHeight <= 0) {
			throw new IllegalArgumentException("The viewport height must be greater than 0");
		}
		this.viewportDimensions.setX(viewportWidth);
		this.viewportDimensions.setY(viewportHeight);
	}

	/**
	 * Devuelve la matriz de la vista de la camara
	 * 
	 * @return Matrix4
	 */
	public Matrix4 getViewMatrix() {
		return viewMatrix;
	}

	/**
	 * Devuelve la matriz de proyeccion de la camara
	 * 
	 * @return Matrix4
	 */
	public Matrix4 getProjectionMatrix() {
		return projectionMatrix;
	}

	/**
	 * Devuelve el vector eye (posicion de la camara)
	 * 
	 * @return Vector3
	 */
	public Vector3 getEye() {
		return eye;
	}

	/**
	 * Devuelve el vector center (punto al que mira la camara)
	 * 
	 * @return Vector3
	 */
	public Vector3 getCenter() {
		return center;
	}

	/**
	 * Devuelve el vector up (direccion de la camara)
	 * 
	 * @return Vector3
	 */
	public Vector3 getUp() {
		return up;
	}

	/**
	 * Devuelve el valor de near
	 * 
	 * @return near
	 */
	public final float getNear() {
		return near;
	}

	/**
	 * Asigna el valor de near<br>
	 * Los cambios no seran visibles hasta que no se llame a {@link #update()}
	 * 
	 * @param near Nuevo valor
	 */
	public final void setNear(float near) {
		this.near = near;
	}

	/**
	 * Devuelve el valor de far
	 * 
	 * @return far
	 */
	public final float getFar() {
		return far;
	}

	/**
	 * Asigna el valor de far<br>
	 * Los cambios no seran visibles hasta que no se llame a {@link #update()}
	 * 
	 * @param far Nuevo valor de far
	 */
	public final void setFar(float far) {
		this.far = far;
	}

	/**
	 * Actualiza las matrices VIEW y PROJECTION con la configuracion actual de la camara.
	 */
	public abstract void update();

}
