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
package com.miviclin.droidengine2d;

import android.app.Activity;

import com.miviclin.droidengine2d.gamestate.GameStateManager;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.texture.TextureManager;

/**
 * Public interface for Games.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface Game {

	/**
	 * Returns the Activity where the game runs.
	 * 
	 * @return Activity where the game runs
	 */
	public Activity getActivity();

	/**
	 * Returns the Camera.
	 * 
	 * @return Camera
	 */
	public Camera getCamera();

	/**
	 * Returns the TextureManager.
	 * 
	 * @return TextureManager
	 */
	public TextureManager getTextureManager();

	/**
	 * Returns the GameStateManager.
	 * 
	 * @return GameStateManager
	 */
	public GameStateManager getGameStateManager();

}
