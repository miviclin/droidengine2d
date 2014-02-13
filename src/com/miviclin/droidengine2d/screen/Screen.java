package com.miviclin.droidengine2d.screen;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.Graphics;
import com.miviclin.droidengine2d.input.ScreenInputManager;

/**
 * Screen represents a level, menu, screen... of the game.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Screen {

	private final Game game;
	private float width;
	private float height;
	private ScreenInputManager inputManager;

	/**
	 * Creates a new Screen.
	 * 
	 * @param width Width.
	 * @param height Height.
	 * @param game Game.
	 */
	public Screen(float width, float height, Game game) {
		this(width, height, new ScreenInputManager(game.getActivity()), game);
	}

	/**
	 * Creates a new Screen.
	 * 
	 * @param width Width.
	 * @param height Height.
	 * @param inputManager InputManager.
	 * @param game Game.
	 */
	public Screen(float width, float height, ScreenInputManager inputManager, Game game) {
		this.game = game;
		this.width = width;
		this.height = height;
		this.inputManager = inputManager;
	}

	/**
	 * Returns the width of this Screen.
	 * 
	 * @return width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Returns the height of this Screen.
	 * 
	 * @return height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Returns the Game this Screen belongs to.
	 * 
	 * @return Game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Returns the InputManager.
	 * 
	 * @return InputManager
	 */
	public ScreenInputManager getInputManager() {
		return inputManager;
	}

	/**
	 * Calls {@link #onDispose()} to dispose this Screen.<br>
	 * This method is called automatically when this Screen is unregistered from the ScreenManager calling
	 * {@link ScreenManager#unregisterScreen(int)} or when {@link ScreenManager#dispose()} is called.
	 */
	public final void dispose() {
		onDispose();
	}

	/**
	 * Updates this Screen.<br>
	 * This method is called from {@link ScreenManager#update(float)} if this Screen is registered in the ScreenManager.
	 * 
	 * @param delta Elapsed time, in milliseconds, since the last update.
	 */
	public abstract void update(float delta);

	/**
	 * Renders the game objects of this Screen.<br>
	 * This method is called from {@link ScreenManager#draw(Graphics)} if this Screen is registered in the
	 * ScreenManager.<br>
	 * This method is called from the redering thread after {@link Screen#update(float)} has been executed in the game
	 * thread.
	 */
	public abstract void draw(Graphics g);

	/**
	 * This method is called when this Screen is registered in the {@link ScreenManager}.<br>
	 * Any initialization should be performed here.
	 */
	public abstract void onRegister();

	/**
	 * This method is called when this Screen becomes the active Screen in the {@link ScreenManager}.
	 */
	public abstract void onActivation();

	/**
	 * This method is called when this Screen stops being the active Screen in the {@link ScreenManager}.
	 */
	public abstract void onDeactivation();

	/**
	 * This method is called when {@link ScreenManager#pause()} is called, if this Screen is the active Screen.
	 */
	public abstract void onPause();

	/**
	 * This method is called when {@link ScreenManager#resume()} is called, if this Screen is the active Screen.
	 */
	public abstract void onResume();

	/**
	 * All resources should be released in this method.<br>
	 * This method is called from {@link #dispose()}.
	 */
	public abstract void onDispose();
}
