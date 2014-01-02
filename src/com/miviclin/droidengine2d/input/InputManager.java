package com.miviclin.droidengine2d.input;

/**
 * InputManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class InputManager {

	private TouchController touchController;
	private KeyController keyController;

	public InputManager() {
		this.touchController = new TouchController();
		this.keyController = new KeyController();
	}

	/**
	 * Returns the TouchController of this GameState.
	 * 
	 * @return TouchController
	 */
	public TouchController getTouchController() {
		return touchController;
	}

	/**
	 * Returns the KeyController of this GameState.
	 * 
	 * @return KeyController
	 */
	public KeyController getKeyController() {
		return keyController;
	}

}
