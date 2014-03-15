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

import android.opengl.GLSurfaceView;

import com.miviclin.droidengine2d.EngineLock;

/**
 * Manages the rendering of the game. GLRenderer runs on its own thread.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GLRenderer implements GLSurfaceView.Renderer {

	private EngineRenderer engineRenderer;
	private EngineLock engineLock;

	/**
	 * Creates a new GLRenderer.
	 * 
	 * @param engineRenderer EngineRenderer used to render the game.
	 * @param engineLock EngineLock used to synchronize both threads.
	 */
	public GLRenderer(EngineRenderer engineRenderer, EngineLock engineLock) {
		this.engineRenderer = engineRenderer;
		this.engineLock = engineLock;
	}

	@Override
	public final void onDrawFrame(GL10 glUnused) {
		if (!engineLock.getAllowUpdateFlag().get()) {
			synchronized (engineLock.getLock()) {
				engineRenderer.onDrawFrame(glUnused);
				GLDebugger.getInstance().checkGLError();
				engineLock.getAllowUpdateFlag().set(true);
			}
		}
	}

	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) {
		engineRenderer.onSurfaceChanged(glUnused, width, height);
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		engineRenderer.onSurfaceCreated(glUnused, config);
	}

}
