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
