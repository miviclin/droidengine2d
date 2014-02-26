package com.miviclin.droidengine2d.gamestate;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.Graphics;
import com.miviclin.droidengine2d.input.GameStateInputManager;

/**
 * This class provides an empty default implementation of {@link GameState}.<br>
 * All abstract methods in {@link GameState} are implemented in this class and their implementations are empty.<br>
 * This class is useful if overwriting all callback methods in {@link GameState} is not needed.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class GameStateAdapter extends GameState {

	/**
	 * Creates a new GameStateAdapter.
	 * 
	 * @param game Game.
	 */
	public GameStateAdapter(Game game) {
		super(game);
	}

	/**
	 * Creates a new GameStateAdapter.
	 * 
	 * @param gameStateInputManager GameStateInputManager.
	 * @param game Game.
	 */
	public GameStateAdapter(GameStateInputManager gameStateInputManager, Game game) {
		super(gameStateInputManager, game);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void draw(Graphics g) {
	}

	@Override
	public void onRegister() {
	}

	@Override
	public void onActivation() {
	}

	@Override
	public void onDeactivation() {
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onResume() {
	}

	@Override
	public void onDispose() {
	}

}
