package com.miviclin.droidengine2d.graphics;

import com.miviclin.droidengine2d.math.Matrix4;
import com.miviclin.droidengine2d.math.Vector3;

public abstract class Camera {
	
	public final Matrix4 viewMatrix = new Matrix4();
	public final Matrix4 projectionMatrix = new Matrix4();
	
	protected final Vector3 eye = new Vector3(0.0f, 0.0f, 5.0f);
	protected final Vector3 center = new Vector3(0.0f, 0.0f, 0.0f);
	protected final Vector3 up = new Vector3(0.0f, 1.0f, 0.0f);
	
	private final Dimensions2D viewportDimensions = new Dimensions2D(1.0f, 1.0f);
	
	private float near = 1.0f;
	private float far = 10.0f;
	
	public abstract void update();
	
	public void translate(float x, float y, float z) {
		eye.add(x, y, z);
	}
	
	public final float getViewportWidth() {
		return viewportDimensions.getWidth();
	}
	
	public final void setViewportWidth(float viewportWidth) {
		if (viewportWidth <= 0) {
			throw new IllegalArgumentException("The viewport width must be greater than 0");
		}
		this.viewportDimensions.setWidth(viewportWidth);
	}
	
	public final float getViewportHeight() {
		return viewportDimensions.getHeight();
	}
	
	public final void setViewportHeight(float viewportHeight) {
		if (viewportHeight <= 0) {
			throw new IllegalArgumentException("The viewport height must be greater than 0");
		}
		this.viewportDimensions.setHeight(viewportHeight);
	}
	
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
	
	public final float getNear() {
		return near;
	}
	
	public final void setNear(float near) {
		this.near = near;
	}
	
	public final float getFar() {
		return far;
	}
	
	public final void setFar(float far) {
		this.far = far;
	}
	
}
