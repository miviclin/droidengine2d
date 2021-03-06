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
package com.miviclin.droidengine2d;

import android.app.Activity;

import com.miviclin.droidengine2d.gamestate.GameStateManager;
import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.graphics.Graphics;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.cameras.OrthographicCamera;
import com.miviclin.droidengine2d.graphics.texture.TextureManager;
import com.miviclin.droidengine2d.input.GameInputManager;

/**
 * Abstract Game. All games should inherit from this class.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class AbstractGame implements Game {

	private final Activity activity;
	private final Camera camera;
	private final TextureManager textureManager;
	private final GameStateManager gameStateManager;
	private final GameInputManager gameInputManager;

	private volatile boolean initialized;

	private GLView glView;
	private boolean prepared;

	/**
	 * Creates a Game.
	 * 
	 * @param activity Activity where the Game runs.
	 */
	public AbstractGame(Activity activity) {
		this(new GLView(activity), activity);
	}

	/**
	 * Creates a Game with the specified GLView.
	 * 
	 * @param glView GLView where the game is rendered.
	 * @param activity Activity where the Game runs.
	 */
	public AbstractGame(GLView glView, Activity activity) {
		this(new GameInputManager(glView), activity);
	}

	/**
	 * Creates a Game with the specified name and GLView.
	 * 
	 * @param gameInputManager GameInputManager. The Game will be redered in the GLView of this GameInputManager.
	 * @param activity Activity where the Game runs.
	 */
	public AbstractGame(GameInputManager gameInputManager, Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("The Activity can not be null");
		}
		this.activity = activity;
		this.camera = new OrthographicCamera();
		this.textureManager = new TextureManager(activity);
		this.gameStateManager = new GameStateManager();
		this.gameStateManager.addOnGameStateChangeListener(gameInputManager);
		this.gameInputManager = gameInputManager;
		this.initialized = false;
		this.glView = gameInputManager.getGLView();
		this.prepared = false;
	}

	@Override
	public Activity getActivity() {
		return activity;
	}

	@Override
	public Camera getCamera() {
		return camera;
	}

	@Override
	public TextureManager getTextureManager() {
		return textureManager;
	}

	@Override
	public GameStateManager getGameStateManager() {
		return gameStateManager;
	}

	/**
	 * Returns the GameInputManager.
	 * 
	 * @return GameInputManager
	 */
	public GameInputManager getGameInputManager() {
		return gameInputManager;
	}

	/**
	 * Returns the GLView where the game is rendered.<br>
	 * This method is only visible to the famework because it is only needed to set up the GLView.
	 * 
	 * @return GLView where the game is rendered
	 */
	GLView getGLView() {
		return glView;
	}

	/**
	 * Sets a new GLView. All listeners in the old GLView will be linked to the new one.<br>
	 * This method is only visible to the famework because it is only needed to set up the GLView.
	 * 
	 * @param glView GLView.
	 */
	void setGLView(GLView glView) {
		this.glView = glView;
		gameInputManager.setGLView(glView);
	}

	/**
	 * Returns true if the Game object has been initialized and is ready to initialize the GameStates. This method will
	 * return true after {@link #prepare()} has been called.
	 * 
	 * @return true if it is safe to initialize the GameStates
	 */
	public boolean isPrepared() {
		return prepared;
	}

	/**
	 * This method should be called when the Game object has been initialized and is ready to initialize the GameStates.
	 * 
	 * @see AbstractGame#isPrepared()
	 */
	public void prepare() {
		this.prepared = true;
	}

	/**
	 * This method is called when the GameThread is paused, usually when {@link Activity#onPause()} is called.
	 */
	public void onEnginePaused() {
		if (initialized) {
			gameStateManager.pause();
		}
	}

	/**
	 * This method is called when the GameThread is resumed, usually when {@link Activity#onResume()} is called.
	 */
	public void onEngineResumed() {
		if (initialized) {
			gameStateManager.resume();
		}
	}

	/**
	 * This method is called when the GameThread is destroyed, usually when {@link Activity#onDestroy()} is called.
	 */
	public void onEngineDisposed() {
		if (initialized) {
			gameStateManager.dispose();
		}
	}

	/**
	 * Updates the game logic.<br>
	 * This method is called from the game thread.
	 * 
	 * @param delta Elapsed time, in milliseconds, since the last update.
	 */
	public void update(float delta) {
		if (initialized) {
			gameStateManager.update(delta);
		} else if (prepared) {
			initialize();
			initialized = true;
			System.gc();
		}
	}

	/**
	 * Renders the game objects.<br>
	 * This method is called from the redering thread after {@link #update(float)} has been executed in the game thread.
	 * 
	 * @param graphics Graphics.
	 */
	public void draw(Graphics graphics) {
		if (initialized) {
			gameStateManager.draw(graphics);
		}
	}

	/**
	 * Initializes at least the initial GameState of this Game.
	 */
	public abstract void initialize();

}
