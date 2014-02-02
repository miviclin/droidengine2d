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

	private TouchInputController touchController;
	private KeyInputController keyController;
	private AccelerometerController accelerometerController;

	/**
	 * Creates a new InputManager.
	 * 
	 * @param activity Activity.
	 */
	public ScreenInputManager(Activity activity) {
		this(activity, new TouchInputController(), new KeyInputController(activity));
	}

	/**
	 * Creates a new InputManager.
	 * 
	 * @param activity Activity.
	 * @param touchController TouchInputController.
	 * @param keyController KeyInputController.
	 */
	public ScreenInputManager(Activity activity, TouchInputController touchController, KeyInputController keyController) {
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
	 * Returns the TouchInputController.
	 * 
	 * @return TouchInputController
	 */
	public TouchInputController getTouchInputController() {
		return touchController;
	}

	/**
	 * Returns the KeyInputController.
	 * 
	 * @return KeyInputController
	 */
	public KeyInputController getKeyInputController() {
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
