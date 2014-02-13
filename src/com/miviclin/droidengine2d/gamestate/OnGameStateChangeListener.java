package com.miviclin.droidengine2d.gamestate;

/**
 * OnGameStateChangeListener.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface OnGameStateChangeListener {

	/**
	 * This method is called when the active GameState of the GameStateManager changes. This allows listeners to know
	 * when the GameState has changed.
	 * 
	 * @param previousGameState Previous GameState.
	 * @param currentGameState Current GameState.
	 */
	public void onGameStateChange(GameState previousGameState, GameState currentGameState);

}
