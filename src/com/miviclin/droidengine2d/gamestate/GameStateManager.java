package com.miviclin.droidengine2d.gamestate;

import java.util.ArrayList;
import java.util.Stack;

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

	private SparseArray<GameState> gameStatesRegistry;
	private Stack<GameState> activeGameStates;
	private ArrayList<OnGameStateChangeListener> onGameStateChangeListeners;

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
		this.gameStatesRegistry = new SparseArray<GameState>(initialCapacity);
		this.activeGameStates = new Stack<GameState>();
		this.onGameStateChangeListeners = new ArrayList<OnGameStateChangeListener>();
	}

	/**
	 * Registers a GameState in this GameStateManager using the specified gameStateId.<br>
	 * If a GameState with the specified gameStateId was previously registered in this GameStateManager, it will be
	 * replaced by the new one.
	 * 
	 * @param gameStateId Identifier of the GameState. It can be used to get the GameState from this GameStateManager
	 *            later.
	 * @param gameState GameState (can not be null).
	 */
	public void registerGameState(int gameStateId, GameState gameState) {
		registerGameState(gameStateId, gameState, false);
	}

	/**
	 * Registers a GameState in this GameStateManager using the specified gameStateId.<br>
	 * If a GameState with the specified gameStateId was previously registered in this GameStateManager, it will be
	 * replaced by the new one.
	 * 
	 * @param gameStateId Identifier of the GameState. It can be used to get the GameState from this GameStateManager
	 *            later.
	 * @param gameState GameState (can not be null).
	 * @param push true to push the GameState onto the stack of active GameStates of this GameStateManager.
	 */
	public void registerGameState(int gameStateId, GameState gameState, boolean push) {
		if (gameState == null) {
			throw new IllegalArgumentException("The GameState can not be null");
		}
		gameStatesRegistry.put(gameStateId, gameState);
		gameState.onRegister();
		if (push) {
			pushActiveGameState(gameStateId);
		}
	}

	/**
	 * Unregisters the specified GameState from this GameStateManager.<br>
	 * If a GameState was registered with the specified gameStateId, {@link GameState#dispose()} is called on the
	 * GameState before it is removed from this GameStateManager.<br>
	 * If the specified GameState is in the stack of active GameStates it should be removed from the stack first,
	 * otherwise an exception will be thrown.
	 * 
	 * @param gameStateId Identifier of the GameState.
	 * @return Removed GameState or null
	 */
	public GameState unregisterGameState(int gameStateId) {
		GameState gameState = gameStatesRegistry.get(gameStateId);
		if (gameState == null) {
			return null;
		}
		int indexInStack = activeGameStates.search(gameState);
		if (indexInStack >= 0) {
			throw new IllegalStateException("The GameState registered with the gameStateId " + gameStateId + " is " +
					"currently in the stack of active GameStates. It should be removed from the stack of active " +
					"GameStates before unregistering it from this GameStateManager.");
		}
		gameState.dispose();
		gameStatesRegistry.remove(gameStateId);
		return gameState;
	}

	/**
	 * Returns the GameState registered with the specified gameStateId.
	 * 
	 * @param gameStateId Identifier of the GameState.
	 * @return GameState or null
	 */
	public GameState getGameState(int gameStateId) {
		return gameStatesRegistry.get(gameStateId);
	}

	/**
	 * Replaces the GameState at the top of the stack of active GameStates of this GameStateManager with the specified
	 * GameState.<br>
	 * The GameState must have been previously registered with the specified gameStateId.<br>
	 * This method notifies every {@link OnGameStateChangeListener} registered in this GameStateManager that the
	 * GameState at the top of the stack of active GameStates has changed.
	 * 
	 * @param gameStateId Identifier of the GameState we want to set as the active GameState.
	 */
	public void switchActiveGameState(int gameStateId) {
		GameState previousTopGameState = popActiveGameStateOffStack();
		GameState currentTopGameState = pushActiveGameStateOntoStack(gameStateId);
		dispatchOnGameStateChangeEvent(previousTopGameState, currentTopGameState);
	}

	/**
	 * Returns the GameState at the top of the stack of active GameStates of this GameStateManager.
	 * 
	 * @return GameState or null
	 */
	public GameState peekActiveGameState() {
		if (activeGameStates.empty()) {
			return null;
		}
		return activeGameStates.peek();
	}

	/**
	 * Pushes the GameState onto the stack of active GameStates of this GameStateManager.<br>
	 * The GameState must have been previously registered with the specified gameStateId.<br>
	 * This method notifies every {@link OnGameStateChangeListener} registered in this GameStateManager that the
	 * GameState at the top of the stack of active GameStates has changed.
	 * 
	 * @param gameStateId Identifier of the GameState we want to set as the active GameState.
	 */
	public void pushActiveGameState(int gameStateId) {
		GameState previousTopGameState = peekActiveGameState();
		GameState currentTopGameState = pushActiveGameStateOntoStack(gameStateId);
		dispatchOnGameStateChangeEvent(previousTopGameState, currentTopGameState);
	}

	/**
	 * Pushes the GameState onto the stack of active GameStates of this GameStateManager.<br>
	 * The GameState must have been previously registered with the specified gameStateId.<br>
	 * This method does not call {@link #dispatchOnGameStateChangeEvent(GameState, GameState)}.
	 * 
	 * @param gameStateId Identifier of the GameState we want to set as the active GameState.
	 * @return The GameState pushed onto the stack of active GameStates
	 */
	private GameState pushActiveGameStateOntoStack(int gameStateId) {
		GameState gameState = gameStatesRegistry.get(gameStateId);
		if (gameState == null) {
			throw new GameStateNotRegisteredException(gameStateId);
		}
		activeGameStates.push(gameState);
		gameState.onActivation();
		return gameState;
	}

	/**
	 * Pops 1 GameState off the stack of active GameStates of this GameStateManager. Also calls
	 * {@link GameState#onDeactivation()} on the GameState popped off the stack.<br>
	 * This method notifies every {@link OnGameStateChangeListener} registered in this GameStateManager that the
	 * GameState at the top of the stack of active GameStates has changed.
	 */
	public void popActiveGameState() {
		popActiveGameStates(1);
	}

	/**
	 * Pops the specified number of GameStates off the stack of active GameStates of this GameStateManager. Also calls
	 * {@link GameState#onDeactivation()} on all the GameStates popped off the stack.<br>
	 * This method notifies every {@link OnGameStateChangeListener} registered in this GameStateManager that the
	 * GameState at the top of the stack of active GameStates has changed.
	 * 
	 * @param numGameStatesToPop Number of GameStates that will be popped off the stack of active GameStates.
	 */
	public void popActiveGameStates(int numGameStatesToPop) {
		for (int i = 0; ((i < numGameStatesToPop) && !activeGameStates.empty()); i++) {
			GameState previousGameState = popActiveGameStateOffStack();
			GameState currentGameState = activeGameStates.peek();
			dispatchOnGameStateChangeEvent(previousGameState, currentGameState);
		}
	}

	/**
	 * Pops all GameStates off the stack of active GameStates of this GameStateManager.<br>
	 * This method notifies every {@link OnGameStateChangeListener} registered in this GameStateManager that the
	 * GameState at the top of the stack of active GameStates has changed.
	 * 
	 * @see #popActiveGameStates(int)
	 */
	public void popAllActiveGameStates() {
		int numGameStatesToPop = activeGameStates.size();
		popActiveGameStates(numGameStatesToPop);
	}

	/**
	 * Pops 1 GameState off the stack of active GameStates of this GameStateManager. Also calls
	 * {@link GameState#onDeactivation()} on the GameState popped off the stack.<br>
	 * This method does not call {@link #dispatchOnGameStateChangeEvent(GameState, GameState)}.
	 * 
	 * @return The removed GameState or null
	 */
	private GameState popActiveGameStateOffStack() {
		GameState gameState = null;
		if (!activeGameStates.empty()) {
			gameState = activeGameStates.pop();
			gameState.onDeactivation();
		}
		return gameState;
	}

	/**
	 * Notifies all listeners that the GameState at the top of the stack of active GameStates has changed.
	 * 
	 * @param previousGameState Previous GameState at the top of the stack or null if the stack was empty.
	 * @param currentGameState Current GameState at the top of the stack or null if the stack is empty.
	 */
	private void dispatchOnGameStateChangeEvent(GameState previousTopGameState, GameState currentTopGameState) {
		for (int i = 0; i < onGameStateChangeListeners.size(); i++) {
			onGameStateChangeListeners.get(i).onGameStateChange(previousTopGameState, currentTopGameState);
		}
	}

	/**
	 * Adds an OnGameStateChangeListener that will be notified when the active GameState of this GameStateManager
	 * changes.
	 * 
	 * @param listener Listener to be added.
	 */
	public void addOnGameStateChangeListener(OnGameStateChangeListener listener) {
		onGameStateChangeListeners.add(listener);
	}

	/**
	 * Removes an OnGameStateChangeListener from the list of listeners. The removed listener will not be notified
	 * anymore when the active GameState of this GameStateManager changes.
	 * 
	 * @param listener Listener to be removed.
	 */
	public void removeOnGameStateChangeListener(OnGameStateChangeListener listener) {
		onGameStateChangeListeners.remove(listener);
	}

	/**
	 * This method is called when the engine is paused, usually when the activity goes to background.<br>
	 * Calls {@link GameState#onPause()} on all the active GameStates.
	 */
	public void pause() {
		for (int i = 0; i < activeGameStates.size(); i++) {
			activeGameStates.get(i).onPause();
		}
	}

	/**
	 * This method is called when the engine is resumed, usually when the activity comes to foreground.<br>
	 * Calls {@link GameState#onResume()} on the active GameState.
	 */
	public void resume() {
		for (int i = 0; i < activeGameStates.size(); i++) {
			activeGameStates.get(i).onResume();
		}
	}

	/**
	 * Clears the stack of active GameStates calling {@link #popAllActiveGameStates()} and calls
	 * {@link GameState#dispose()} on all GameStates registered in this GameStateManager and removes them from the
	 * GameStateManager.<br>
	 * This GameStateManager will be left empty.
	 */
	public void dispose() {
		popAllActiveGameStates();
		int numGameStates = gameStatesRegistry.size();
		for (int i = 0; i < numGameStates; i++) {
			GameState gameState = gameStatesRegistry.valueAt(i);
			if (gameState != null) {
				gameState.dispose();
			}
		}
		gameStatesRegistry.clear();
	}

	/**
	 * Calls {@link GameState#update(float)} on the GameState at the top of the stack of active GameStates.<br>
	 * This method is called from {@link Game#update(float)}.
	 * 
	 * @param delta Elapsed time, in milliseconds, since the last update.
	 */
	public void update(float delta) {
		GameState gameState = peekActiveGameState();
		if (gameState != null) {
			gameState.getInputManager().processInput();
			gameState.update(delta);
		}
	}

	/**
	 * Calls {@link GameState#draw(Graphics)} on all the GameStates in the stack of active GameStates.<br>
	 * This method is called from {@link Game#draw(Graphics)}.<br>
	 * This method is called from the redering thread after {@link GameStateManager#update(float)} has been executed in
	 * the game thread.
	 */
	public void draw(Graphics graphics) {
		for (int i = 0; i < activeGameStates.size(); i++) {
			GameState gameState = activeGameStates.get(i);
			gameState.draw(graphics);
		}
	}

}
