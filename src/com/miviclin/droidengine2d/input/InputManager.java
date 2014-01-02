package com.miviclin.droidengine2d.input;

import android.app.Activity;

import com.miviclin.droidengine2d.input.sensor.AccelerometerController;
import com.miviclin.droidengine2d.input.sensor.AccelerometerValuesListener;

/**
 * InputManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class InputManager {

	private TouchController touchController;
	private KeyController keyController;
	private AccelerometerController accelerometerController;

	/**
	 * Creates a new InputManager.
	 * 
	 * @param activity Activity.
	 */
	public InputManager(Activity activity) {
		this.touchController = new TouchController();
		this.keyController = new KeyController(activity);
		this.accelerometerController = new AccelerometerController(activity, new AccelerometerValuesListener(0.2f));
	}

	/**
	 * Processes touch and key input.<br>
	 * This method should be called when the game updates, before the update is processed.
	 */
	public void processInput() {
		touchController.processTouchInput();
		keyController.processKeyInput();
	}

	/**
	 * Returns the TouchController.
	 * 
	 * @return TouchController
	 */
	public TouchController getTouchController() {
		return touchController;
	}

	/**
	 * Returns the KeyController.
	 * 
	 * @return KeyController
	 */
	public KeyController getKeyController() {
		return keyController;
	}

	/**
	 * Returns the AccelerometerController.
	 * 
	 * @return AccelerometerController
	 */
	public AccelerometerController getAccelerometerController() {
		return accelerometerController;
	}
}
