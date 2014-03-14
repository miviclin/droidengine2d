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
