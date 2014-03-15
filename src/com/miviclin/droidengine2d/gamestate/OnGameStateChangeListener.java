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
