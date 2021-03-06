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
package com.miviclin.droidengine2d.gamestate;

import android.app.Activity;

import com.miviclin.droidengine2d.AbstractGame;
import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.Graphics;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.texture.TextureManager;
import com.miviclin.droidengine2d.input.GameStateInputManager;

/**
 * GameState represents a level, menu, screen... of the game.<br>
 * GameStates should be initialized in {@link AbstractGame#initialize()} or in another GameState.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class GameState {

	private final Game game;
	private GameStateInputManager gameStateInputManager;

	/**
	 * Creates a new GameState.
	 * 
	 * @param game Game.
	 */
	public GameState(Game game) {
		this(new GameStateInputManager(game.getActivity()), game);
	}

	/**
	 * Creates a new GameState.
	 * 
	 * @param gameStateInputManager GameStateInputManager.
	 * @param game Game.
	 */
	public GameState(GameStateInputManager gameStateInputManager, Game game) {
		this.gameStateInputManager = gameStateInputManager;
		this.game = game;
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
	 * Returns the Activity where the game runs.<br>
	 * This method is a shorthand for {@code getGame().getActivity()}.
	 * 
	 * @return Activity where the game runs
	 */
	public Activity getActivity() {
		return game.getActivity();
	}

	/**
	 * Returns the Camera.<br>
	 * This method is a shorthand for {@code getGame().getCamera()}.
	 * 
	 * @return Camera
	 */
	public Camera getCamera() {
		return game.getCamera();
	}

	/**
	 * Returns the TextureManager.<br>
	 * This method is a shorthand for {@code getGame().getTextureManager()}.
	 * 
	 * @return TextureManager
	 */
	public TextureManager getTextureManager() {
		return game.getTextureManager();
	}

	/**
	 * Returns the GameStateManager.<br>
	 * This method is a shorthand for {@code getGame().getGameStateManager()}.
	 * 
	 * @return GameStateManager
	 */
	public GameStateManager getGameStateManager() {
		return game.getGameStateManager();
	}

	/**
	 * Returns the GameInputManager.
	 * 
	 * @return GameInputManager
	 */
	public GameStateInputManager getGameStateInputManager() {
		return gameStateInputManager;
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
