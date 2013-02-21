package com.miviclin.droidengine2d.graphics;

public class OrthographicCamera extends Camera {
	
	public OrthographicCamera() {
		eye.set(0.0f, 0.0f, 5.0f);
		center.set(0.0f, 0.0f, 0.0f);
		up.set(0.0f, 1.0f, 0.0f);
		setNear(1.0f);
		setFar(10.0f);
	}
	
	@Override
	public void update() {
		viewMatrix.setLookAt(eye, center.set(eye.getX(), eye.getY(), center.getZ()), up);
		projectionMatrix.setOrtho(0, getViewportWidth(), 0, getViewportHeight(), getNear(), getFar());
	}
	
}
