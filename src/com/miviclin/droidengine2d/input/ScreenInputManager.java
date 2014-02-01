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
public class ScreenInputManager {

	private TouchController touchController;
	private KeyController keyController;
	private AccelerometerController accelerometerController;

	/**
	 * Creates a new InputManager.
	 * 
	 * @param activity Activity.
	 */
	public ScreenInputManager(Activity activity) {
		this(activity, new TouchController(), new KeyController(activity));
	}

	/**
	 * Creates a new InputManager.
	 * 
	 * @param activity Activity.
	 * @param touchController TouchController.
	 * @param keyController KeyController.
	 */
	public ScreenInputManager(Activity activity, TouchController touchController, KeyController keyController) {
		this.touchController = touchController;
		this.keyController = keyController;
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
