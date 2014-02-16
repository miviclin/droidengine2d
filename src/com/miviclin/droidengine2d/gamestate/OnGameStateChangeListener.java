package com.miviclin.droidengine2d.gamestate;

/**
 * OnGameStateChangeListener.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface OnGameStateChangeListener {

	/**
	 * This method is called whenever the GameState at the top of the stack of active GameStates of the GameStateManager
	 * changes.
	 * 
	 * @param previousGameState Previous GameState at the top of the stack or null if the stack was empty.
	 * @param currentGameState Current GameState at the top of the stack or null if the stack is empty.
	 * 
	 * @see GameStateManager#switchActiveGameState(int)
	 * @see GameStateManager#pushActiveGameState(int)
	 * @see GameStateManager#popActiveGameState()
	 * @see GameStateManager#popActiveGameStates(int)
	 * @see GameStateManager#popAllActiveGameStates()
	 */
	public void onGameStateChange(GameState previousGameState, GameState currentGameState);

}
