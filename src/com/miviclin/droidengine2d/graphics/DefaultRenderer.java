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
		game.draw(graphics);
		graphics.flush();

		if (BuildConfig.DEBUG) {
			GLDebugger.getInstance().logNumDrawCallsFrame();
			GLDebugger.getInstance().resetNumDrawCallsFrame();
		}
	}

}
