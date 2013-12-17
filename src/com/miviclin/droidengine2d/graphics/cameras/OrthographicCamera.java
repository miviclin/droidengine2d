package com.miviclin.droidengine2d.graphics.cameras;

/**
 * Orthographic projection camera.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class OrthographicCamera extends Camera {

	/**
	 * Creates a new OrthographicCamera.
	 */
	public OrthographicCamera() {
		getEye().set(0.0f, 0.0f, 5.0f);
		getCenter().set(0.0f, 0.0f, 0.0f);
		getUp().set(0.0f, 1.0f, 0.0f);
		setNear(1.0f);
		setFar(10.0f);
	}

	@Override
	public void update() {
		getCenter().set(getEye().getX(), getEye().getY(), getCenter().getZ());
		getViewMatrix().setLookAt(getEye(), getCenter(), getUp());
		getProjectionMatrix().setOrtho(0, getViewportWidth(), 0, getViewportHeight(), getNear(), getFar());
	}

}
