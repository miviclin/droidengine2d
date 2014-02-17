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
