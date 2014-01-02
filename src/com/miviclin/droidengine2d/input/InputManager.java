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
	 * Processes all input.<br>
	 * This method should be called when the game updates, before the update is processed.
	 */
	public void processInput() {
		touchController.processTouchInput();
		keyController.processKeyInput();
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
