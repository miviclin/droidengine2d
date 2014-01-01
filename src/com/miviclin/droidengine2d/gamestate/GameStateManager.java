package com.miviclin.droidengine2d.gamestate;

import android.util.SparseArray;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.Graphics;

/**
 * GameStateManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GameStateManager {

	private SparseArray<GameState> gameStates;
	private GameState activeGameState;

	/**
	 * Constructor.
	 */
	public GameStateManager() {
		this(16);
	}

	/**
	 * Constructor.
	 * 
	 * @param initialCapacity Initial capacity for GameStates. If this capacity is reached, the data structure that
	 *            holds the GameStates will be resized automatically.
	 */
	public GameStateManager(int initialCapacity) {
		this.gameStates = new SparseArray<GameState>(initialCapacity);
		this.activeGameState = null;
	}

	/**
	 * Registers an GameState in this GameStateManager using the specified gameStateId.<br>
	 * If an GameState with the specified gameStateId was previously registered in this GameStateManager, it will be
	 * replaced by the new one.<br>
	 * The active GameState will not change.
	 * 
	 * @param gameStateId Identifier of the GameState. It can be used to get the GameState from this GameStateManager
	 *            later.
	 * @param gameState GameState (can not be null).
	 */
	public void registerGameState(int gameStateId, GameState gameState) {
		registerGameState(gameStateId, gameState, false);
	}

	/**
	 * Registers an GameState in this GameStateManager using the specified gameStateId.<br>
	 * If an GameState with the specified gameStateId was previously registered in this GameStateManager, it will be
	 * replaced by the new one.
	 * 
	 * @param gameStateId Identifier of the GameState. It can be used to get the GameState from this GameStateManager
	 *            later.
	 * @param gameState GameState (can not be null).
	 * @param activate true to make the GameState the active GameState of this GameStateManager.
	 */
	public void registerGameState(int gameStateId, GameState gameState, boolean activate) {
		if (gameState == null) {
			throw new IllegalArgumentException("The GameState can not be null");
		}
		gameStates.put(gameStateId, gameState);
		gameState.onRegister();
		if (activate) {
			setActiveGameState(gameStateId);
		}
	}

	/**
	 * Unregisters the specified GameState from this GameStateManager.<br>
	 * If an GameState was registered with the specified gameStateId, {@link GameState#onDispose()} is called on the
	 * GameState before it is removed from this GameStateManager.
	 * 
	 * @param gameStateId Identifier of the GameState.
	 * @return Removed GameState or null
	 */
	public GameState unregisterGameState(int gameStateId) {
		GameState gameState = gameStates.get(gameStateId);
		if (gameState == activeGameState) {
			gameState.onDeactivation();
			activeGameState = null;
		}
		if (gameState != null) {
			gameState.dispose();
			gameStates.remove(gameStateId);
		}
		return gameState;
	}

	/**
	 * Returns the GameState associated with the specified gameStateId.
	 * 
	 * @param gameStateId Identifier of the GameState.
	 * @return GameState or null
	 */
	public GameState getGameState(int gameStateId) {
		return gameStates.get(gameStateId);
	}

	/**
	 * Returns the active GameState of this GameStateManager.
	 * 
	 * @return GameState or null
	 */
	public GameState getActiveGameState() {
		return activeGameState;
	}

	/**
	 * Sets the active GameState of this GameStateManager.<br>
	 * The GameState must have been previously registered with the specified gameStateId.
	 * 
	 * @param gameStateId Identifier of the GameState we want to set as the active GameState.
	 */
	public void setActiveGameState(int gameStateId) {
		if (activeGameState != null) {
			activeGameState.onDeactivation();
		}
		this.activeGameState = gameStates.get(gameStateId);
		if (activeGameState != null) {
			activeGameState.onActivation();
		}
	}

	/**
	 * This method is called when the engine is paused, usually when the activity goes to background.<br>
	 * Calls {@link GameState#onPause()} on the active GameState.
	 */
	public void pause() {
		if (activeGameState != null) {
			activeGameState.onPause();
		}
	}

	/**
	 * This method is called when the engine is resumed, usually when the activity comes to foreground.<br>
	 * Calls {@link GameState#onResume()} on the active GameState.
	 */
	public void resume() {
		if (activeGameState != null) {
			activeGameState.onResume();
		}
	}

	/**
	 * Calls {@link GameState#onDispose()} on all GameStates registered in this GameStateManager and removes them from
	 * the GameStateManager.<br>
	 * This GameStateManager will be left empty.
	 */
	public void dispose() {
		int numGameStates = gameStates.size();
		for (int i = 0; i < numGameStates; i++) {
			GameState gameState = gameStates.valueAt(i);
			if (gameState != null) {
				gameState.dispose();
			}
		}
		gameStates.clear();
		activeGameState = null;
	}

	/**
	 * Calls {@link GameState#update(float)} on the active GameState .<br>
	 * This method is called from {@link Game#update(float)}.
	 * 
	 * @param delta Elapsed time, in milliseconds, since the last update.
	 */
	public void update(float delta) {
		if (activeGameState != null) {
			activeGameState.update(delta);
		}
	}

	/**
	 * Calls {@link GameState#draw(Graphics)} on the active GameState.<br>
	 * This method is called from {@link Game#draw(Graphics)}.<br>
	 * This method is called from the redering thread after {@link GameStateManager#update(float)} has been executed in
	 * the game thread.
	 */
	public void draw(Graphics graphics) {
		if (activeGameState != null) {
			activeGameState.draw(graphics);
		}
	}

}
