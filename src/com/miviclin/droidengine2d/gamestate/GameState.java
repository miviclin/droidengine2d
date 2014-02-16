package com.miviclin.droidengine2d.gamestate;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.Graphics;
import com.miviclin.droidengine2d.input.GameStateInputManager;

/**
 * GameState represents a level, menu, screen... of the game.<br>
 * GameStates should be initialized in {@link Game#initialize()} or in another GameState.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class GameState {

	private final Game game;
	private GameStateInputManager inputManager;

	/**
	 * Creates a new GameState.
	 * 
	 * @param width Width.
	 * @param height Height.
	 * @param game Game.
	 */
	public GameState(Game game) {
		this(new GameStateInputManager(game.getActivity()), game);
	}

	/**
	 * Creates a new GameState.
	 * 
	 * @param inputManager GameStateInputManager.
	 * @param game Game.
	 */
	public GameState(GameStateInputManager inputManager, Game game) {
		this.inputManager = inputManager;
		this.game = game;
	}

	/**
	 * Returns the width of this GameState.
	 * 
	 * @return width
	 */
	public float getWidth() {
		return game.getViewWidth();
	}

	/**
	 * Returns the height of this GameState.
	 * 
	 * @return height
	 */
	public float getHeight() {
		return game.getViewHeight();
	}

	/**
	 * Returns the Game this GameState belongs to.
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
	public GameStateInputManager getInputManager() {
		return inputManager;
	}

	/**
	 * Calls {@link #onDispose()} to dispose this GameState.<br>
	 * This method is called automatically when this GameState is unregistered from the GameStateManager calling
	 * {@link GameStateManager#unregisterGameState(int)} or when {@link GameStateManager#dispose()} is called.
	 */
	public final void dispose() {
		onDispose();
	}

	/**
	 * Updates this GameState.<br>
	 * This method is called from {@link GameStateManager#update(float)} if this GameState is registered in the
	 * GameStateManager.
	 * 
	 * @param delta Elapsed time, in milliseconds, since the last update.
	 */
	public abstract void update(float delta);

	/**
	 * Renders the game objects of this GameState.<br>
	 * This method is called from {@link GameStateManager#draw(Graphics)} if this GameState is registered in the
	 * GameStateManager.<br>
	 * This method is called from the redering thread after {@link GameState#update(float)} has been executed in the
	 * game thread.
	 */
	public abstract void draw(Graphics g);

	/**
	 * This method is called when this GameState is registered in the {@link GameStateManager}.<br>
	 * Any initialization should be performed here.
	 */
	public abstract void onRegister();

	/**
	 * This method is called when this GameState is pushed onto the stack of active GameStates of the
	 * {@link GameStateManager}.
	 */
	public abstract void onActivation();

	/**
	 * This method is called when this GameState is popped off the stack of active GameStates of the
	 * {@link GameStateManager}.
	 */
	public abstract void onDeactivation();

	/**
	 * This method is called when {@link GameStateManager#pause()} is called, if this GameState is in the stack of
	 * active GameStates of the {@link GameStateManager}.
	 */
	public abstract void onPause();

	/**
	 * This method is called when {@link GameStateManager#resume()} is called, if this GameState is the stack of active
	 * GameStates of the {@link GameStateManager}.
	 */
	public abstract void onResume();

	/**
	 * All resources should be released in this method.<br>
	 * This method is called from {@link #dispose()}.
	 */
	public abstract void onDispose();
}
