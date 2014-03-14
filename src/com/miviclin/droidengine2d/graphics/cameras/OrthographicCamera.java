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
