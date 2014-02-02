package com.miviclin.droidengine2d;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.Display;
import android.view.WindowManager;

import com.miviclin.droidengine2d.graphics.DefaultRenderer;
import com.miviclin.droidengine2d.graphics.EngineRenderer;
import com.miviclin.droidengine2d.graphics.GLRenderer;
import com.miviclin.droidengine2d.graphics.GLView;

/**
 * Engine manages the game thread and the rendering thread.<br>
 * <br>
 * WARNING: {@link Engine#onPause()}, {@link Engine#onResume()} and {@link Engine#onDestroy()} should be called from
 * {@link Activity#onPause()}, {@link Activity#onResume()} and {@link Activity#onDestroy()} respectively.<br>
 * It is also possible to intercept the back button calling {@link Engine#onBackPressed()} from
 * {@link Activity#onBackPressed()}<br>
 * DroidEngine2D uses OpenGL ES 2.0, so a compatibility check should be performed. For example:
 * 
 * <pre>
 * {@code Engine engine;
 * if (ActivityUtilities.detectOpenGLES20(activity)) {
 *     engine = new Engine(...);
 * } else {
 *     // Tell the user that his/her device does not support OpenGL ES 2.0
 * }
 * </pre>
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Engine {

	private Game game;
	private GameThread gameThread;
	private GLRenderer renderer;
	private GLView glView;

	/**
	 * Creates an Engine.<br>
	 * READ: {@link Engine}
	 * 
	 * @param game Game
	 * @param renderer EngineRenderer
	 * @throws IllegalArgumentException if engineBuilder is null
	 */
	private Engine(EngineBuilder engineBuilder) {
		if (engineBuilder == null) {
			throw new IllegalArgumentException("The EngineBuilder can not be null");
		}
		this.game = engineBuilder.game;
		this.glView = engineBuilder.glView;
		this.gameThread = engineBuilder.gameThread;
		this.renderer = engineBuilder.glRenderer;
	}

	/**
	 * Returns the Game.
	 * 
	 * @return Game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Returns the GLView.
	 * 
	 * @return GLView
	 */
	public GLView getGLView() {
		return glView;
	}

	/**
	 * Sets a new GLView. Also sets the GLRenderer to the new GLView and starts the GL thread.<br>
	 * Calling this method manually when the Engine is first created is not needed.
	 * 
	 * @param GLView New GLView
	 */
	public void setGLView(GLView glView) {
		this.glView = glView;
		this.glView.setEGLContextClientVersion(2);
		this.game.setGLView(glView);
		this.gameThread.setGLView(glView);
		glView.setRenderer(renderer);
		glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	/**
	 * Starts the game thread and the rendering thread.
	 */
	public void startGame() {
		gameThread.start();
		glView.setRenderer(renderer);
		glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	/**
	 * Pauses the game thread and the rendering thread.<br>
	 * This method should be called from {@link Activity.onPause()}
	 */
	public void onPause() {
		glView.onPause();
		gameThread.pause();
	}

	/**
	 * Resumes the game thread and the rendering thread.<br>
	 * This method should be called from {@link Activity.onResume()}
	 */
	public void onResume() {
		glView.onResume();
		gameThread.resume();
	}

	/**
	 * Destroys the game thread and releases resources.<br>
	 * This method should be called from {@link Activity#onDestroy()}
	 */
	public void onDestroy() {
		gameThread.terminate();
	}

	/**
	 * EngineBuilder is used to build the {@link Engine}.
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	public static final class EngineBuilder {

		private final Game game;
		private final GLView glView;
		private EngineRenderer renderer;
		private GLRenderer glRenderer;
		private GameThread gameThread;
		private int maxFPS;
		private int maxSkippedFrames;

		/**
		 * Creates an EngineBuilder.<br>
		 * READ: {@link Engine}
		 * 
		 * @param game Game
		 * @throws IllegalArgumentException if game is null
		 */
		public EngineBuilder(Game game) {
			if (game == null) {
				throw new IllegalArgumentException("The Game can not be null");
			}
			this.game = game;
			this.glView = game.getGLView();
			this.glView.setEGLContextClientVersion(2);
			this.renderer = new DefaultRenderer(game);
			this.glRenderer = null;
			this.gameThread = null;
			this.maxFPS = 30;
			this.maxSkippedFrames = 5;
		}

		/**
		 * Sets the EngineRenderer.<br>
		 * The default renderer is {@link DefaultRenderer}
		 * 
		 * @param renderer EngineRenderer
		 * @return this EngineBuilder
		 */
		public EngineBuilder setRenderer(EngineRenderer renderer) {
			if (renderer == null) {
				throw new IllegalArgumentException("The Renderer can not be null");
			}
			this.renderer = renderer;
			return this;
		}

		/**
		 * Set the frame rate cap (maximum number of FPS the game will run at). The default value is 30.<br>
		 * The frame rate cap can not be greater than the display refresh rate. If the specified value is greater than
		 * the display refresh rate, the frame rate cap will be set to the display refresh rate.
		 * 
		 * @param maxFPS Frame rate cap
		 * @return this EngineBuilder
		 */
		public EngineBuilder setMaxFPS(int maxFPS) {
			WindowManager windowManager = (WindowManager) game.getActivity().getSystemService(Context.WINDOW_SERVICE);
			Display display = windowManager.getDefaultDisplay();
			int refreshRate = (int) display.getRefreshRate();
			this.maxFPS = (maxFPS > refreshRate) ? ((refreshRate > 30) ? refreshRate : 30) : maxFPS;
			return this;
		}

		/**
		 * Sets the maximum number of frames the game will be updated before rendering in case performance is bad in the
		 * specified device.
		 * 
		 * @param maxSkippedFrames Maximum number of skipped frames in case performance is bad
		 * @return this EngineBuilder
		 */
		public EngineBuilder setMaxSkippedFrames(int maxSkippedFrames) {
			this.maxSkippedFrames = maxSkippedFrames;
			return this;
		}

		/**
		 * Builds an {@link Engine} using this EngineBuilder
		 * 
		 * @return Engine
		 */
		public Engine build() {
			EngineLock engineLock = new EngineLock();
			this.glRenderer = new GLRenderer(renderer, engineLock);
			this.gameThread = new GameThread(maxFPS, maxSkippedFrames, game, glView, engineLock);
			return new Engine(this);
		}

	}

}
