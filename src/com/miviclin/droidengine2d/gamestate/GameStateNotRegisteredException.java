package com.miviclin.droidengine2d.gamestate;

/**
 * This exception should be thrown if there is no GameState registered with the specified GameState ID in the
 * GameStateManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
@SuppressWarnings("serial")
public class GameStateNotRegisteredException extends RuntimeException {

	/**
	 * Creates a new GameStateNotRegisteredException.
	 */
	public GameStateNotRegisteredException() {
		super("A GameState with the specified gameStateId is not registered in the GameStateManager");
	}

	/**
	 * Creates a new GameStateNotRegisteredException.
	 * 
	 * @param gameStateId Identifier of the GameState.
	 */
	public GameStateNotRegisteredException(int gameStateId) {
		super("A GameState with the gameStateId " + gameStateId + " is not registered in the GameStateManager");
	}

}
