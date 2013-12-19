package com.miviclin.droidengine2d.scene;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.Graphics;
import com.miviclin.droidengine2d.input.KeyController;
import com.miviclin.droidengine2d.input.TouchController;

/**
 * Scene represents a level, menu, screen... of the game.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Scene {

	private final Game game;
	private TouchController touchController;
	private KeyController keyController;

	/**
	 * Creates a new Scene.
	 * 
	 * @param game Game.
	 */
	public Scene(Game game) {
		this.game = game;
		this.touchController = new TouchController();
		this.keyController = new KeyController();
	}

	/**
	 * Returns the Game this Scene belongs to.
	 * 
	 * @return Game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Returns the TouchController of this Scene.
	 * 
	 * @return TouchController
	 */
	public TouchController getTouchController() {
		return touchController;
	}

	/**
	 * Returns the KeyController of this Scene.
	 * 
	 * @return KeyController
	 */
	public KeyController getKeyController() {
		return keyController;
	}

	/**
	 * Updates this Scene.<br>
	 * This method is called from {@link SceneManager#update(float)} if this Scene is registered in the SceneManager.
	 * 
	 * @param delta Elapsed time, in milliseconds, since the last update.
	 */
	public abstract void update(float delta);

	/**
	 * Renders the game objects of this Scene.<br>
	 * This method is called from {@link SceneManager#draw(Graphics)} if this Scene is registered in the SceneManager.<br>
	 * This method is called from the redering thread after {@link Scene#update(float)} has been executed in the game
	 * thread.
	 */
	public abstract void draw(Graphics g);

	/**
	 * This method is called when this Scene is registered in the {@link SceneManager}.
	 */
	public abstract void onRegister();

	/**
	 * This method is called when this Scene becomes the active Scene in the {@link SceneManager}.
	 */
	public abstract void onActivation();

	/**
	 * This method is called when this Scene stops being the active Scene in the {@link SceneManager}.
	 */
	public abstract void onDeactivation();

	/**
	 * This method is called when {@link SceneManager#pause()} is called, if this Scene is the active Scene.
	 */
	public abstract void onPause();

	/**
	 * This method is called when {@link SceneManager#resume()} is called, if this Scene is the active Scene.
	 */
	public abstract void onResume();

	/**
	 * Disposes this Scene. All resources should be released in this method.<br>
	 * This method is called automatically when this Scene is unregistered from the SceneManager calling
	 * {@link SceneManager#unregisterScene(String)} or when {@link SceneManager#dispose()} is called.
	 */
	public abstract void dispose();
}
