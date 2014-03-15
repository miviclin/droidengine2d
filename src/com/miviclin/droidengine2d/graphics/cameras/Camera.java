/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.miviclin.droidengine2d.graphics.cameras;

import com.miviclin.droidengine2d.util.math.Matrix4;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * Base class for cameras.<br>
 * Contains the view and projection matrices.
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
	 * Constructor.
	 */
	public Camera() {
		super();
	}

	/**
	 * Translates de eye vector.<br>
	 * Changes will not be visible untill {@link #update()} is called.
	 * 
	 * @param x Value, in pixels, that will be added to the current X.
	 * @param y Value, in pixels, that will be added to the current Y.
	 * @param z Value, in pixels, that will be added to the current Z.
	 */
	public void translate(float x, float y, float z) {
		eye.add(x, y, z);
	}

	/**
	 * Returns the width of the viewport in pixels.
	 * 
	 * @return the width of the viewport in pixels
	 */
	public final float getViewportWidth() {
		return viewportDimensions.getX();
	}

	/**
	 * Sets the width of the viewport.<br>
	 * Changes will not be visible untill {@link #update()} is called.
	 * 
	 * @param viewportWidth New width, in pixels.
	 */
	public final void setViewportWidth(float viewportWidth) {
		if (viewportWidth <= 0) {
			throw new IllegalArgumentException("The viewport width must be greater than 0");
		}
		this.viewportDimensions.setX(viewportWidth);
	}

	/**
	 * Returns the height of the viewport in pixels.
	 * 
	 * @return the height of the viewport in pixels
	 */
	public final float getViewportHeight() {
		return viewportDimensions.getY();
	}

	/**
	 * Sets the height of the viewport.<br>
	 * Changes will not be visible untill {@link #update()} is called.
	 * 
	 * @param viewportHeight New height, in pixels.
	 */
	public final void setViewportHeight(float viewportHeight) {
		if (viewportHeight <= 0) {
			throw new IllegalArgumentException("The viewport height must be greater than 0");
		}
		this.viewportDimensions.setY(viewportHeight);
	}

	/**
	 * Sets the dimensions of the viewport.<br>
	 * Changes will not be visible untill {@link #update()} is called.
	 * 
	 * @param viewportWidth New width, in pixels.
	 * @param viewportHeight New height, in pixels.
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
	 * Returns the view matrix.
	 * 
	 * @return View matrix.
	 */
	public Matrix4 getViewMatrix() {
		return viewMatrix;
	}

	/**
	 * Returns the projection matrix.
	 * 
	 * @return Projection matrix.
	 */
	public Matrix4 getProjectionMatrix() {
		return projectionMatrix;
	}

	/**
	 * Returns the eye vector (camera position).
	 * 
	 * @return Vector3
	 */
	public Vector3 getEye() {
		return eye;
	}

	/**
	 * Returns the center vector (where the camera is looking at).
	 * 
	 * @return Vector3
	 */
	public Vector3 getCenter() {
		return center;
	}

	/**
	 * Returns the up vector (the up vector of the camera).
	 * 
	 * @return Vector3
	 */
	public Vector3 getUp() {
		return up;
	}

	/**
	 * Returns the distance from the camera to the front of the viewing volume.
	 * 
	 * @return near
	 */
	public final float getNear() {
		return near;
	}

	/**
	 * Sets the distance from the camera to the front of the viewing volume.<br>
	 * Changes will not be visible untill {@link #update()} is called.
	 * 
	 * @param near New near value.
	 */
	public final void setNear(float near) {
		this.near = near;
	}

	/**
	 * Returns the distance from the camera to the back of the viewing volume.
	 * 
	 * @return far
	 */
	public final float getFar() {
		return far;
	}

	/**
	 * Sets the distance from the camera to the back of the viewing volume.<br>
	 * Changes will not be visible untill {@link #update()} is called.
	 * 
	 * @param far New far value.
	 */
	public final void setFar(float far) {
		this.far = far;
	}

	/**
	 * Updates the view and projection matrices with the current camera configuration.
	 */
	public abstract void update();

}
