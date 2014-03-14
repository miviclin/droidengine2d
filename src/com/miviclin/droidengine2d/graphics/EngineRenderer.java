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
package com.miviclin.droidengine2d.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * EngineRenderer interface.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface EngineRenderer {

	/**
	 * This method is called from {@link GLRenderer#onSurfaceCreated(GL10, EGLConfig)}
	 */
	public abstract void onSurfaceCreated(GL10 glUnused, EGLConfig config);

	/**
	 * This method is called from {@link GLRenderer#onSurfaceChanged(GL10, int, int)}
	 */
	public abstract void onSurfaceChanged(GL10 glUnused, int width, int height);

	/**
	 * This method is called from {@link GLRenderer#onDrawFrame(GL10)}
	 */
	public abstract void onDrawFrame(GL10 glUnused);

}
