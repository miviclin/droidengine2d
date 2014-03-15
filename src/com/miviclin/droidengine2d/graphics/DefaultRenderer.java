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

import android.app.Activity;
import android.opengl.GLES20;

import com.miviclin.droidengine2d.AbstractGame;
import com.miviclin.droidengine2d.BuildConfig;
import com.miviclin.droidengine2d.graphics.cameras.Camera;

/**
 * Default renderer.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class DefaultRenderer implements EngineRenderer {

	private AbstractGame game;
	private Graphics graphics;

	/**
	 * Creates a new DefaultRenderer.
	 * 
	 * @param game Game.
	 */
	public DefaultRenderer(AbstractGame game) {
		this.game = game;
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		Camera camera = game.getCamera();
		Activity activity = game.getActivity();
		graphics = new Graphics(camera, activity);
		graphics.initialize();

		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
	}

	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) {
		Camera camera = game.getCamera();
		camera.setViewportDimensions(width, height);
		camera.update();
		game.getTextureManager().loadAllTextures();
	}

	@Override
	public void onDrawFrame(GL10 glUnused) {
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

		if (BuildConfig.DEBUG) {
			GLDebugger.getInstance().logNumDrawCallsInPreviousFrame();
		}

		game.draw(graphics);
		graphics.flush();

		if (BuildConfig.DEBUG) {
			GLDebugger.getInstance().resetNumDrawCallsInCurrentFrame();
		}
	}

}
