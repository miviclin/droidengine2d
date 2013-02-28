package com.miviclin.droidengine2d.graphics.cameras;

import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.math.Matrix4;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * Contiene las matrices VIEW Y PROJECTION y es la clase base para los distintos tipos de camara.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Camera {
	
	public final Matrix4 viewMatrix = new Matrix4();
	public final Matrix4 projectionMatrix = new Matrix4();
	
	protected final Vector3 eye = new Vector3(0.0f, 0.0f, 5.0f);
	protected final Vector3 center = new Vector3(0.0f, 0.0f, 0.0f);
	protected final Vector3 up = new Vector3(0.0f, 1.0f, 0.0f);
	
	private final Dimensions2D viewportDimensions = new Dimensions2D(1.0f, 1.0f);
	
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
		return viewportDimensions.getWidth();
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
		this.viewportDimensions.setWidth(viewportWidth);
	}
	
	/**
	 * Devuelve el alto del viewport en pixeles
	 * 
	 * @return alto del viewport
	 */
	public final float getViewportHeight() {
		return viewportDimensions.getHeight();
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
		this.viewportDimensions.setHeight(viewportHeight);
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
		this.viewportDimensions.setWidth(viewportWidth);
		this.viewportDimensions.setHeight(viewportHeight);
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
