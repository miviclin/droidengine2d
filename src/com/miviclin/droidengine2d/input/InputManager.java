package com.miviclin.droidengine2d.input;

import android.app.Activity;

import com.miviclin.droidengine2d.input.sensor.Accelerometer;

/**
 * InputManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class InputManager {

	private TouchController touchController;
	private KeyController keyController;
	private Accelerometer accelerometer;

	public InputManager(Activity activity) {
		this.touchController = new TouchController();
		this.keyController = new KeyController();
		this.accelerometer = new Accelerometer(0.2f, activity, false);
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
	 * Returns the Accelerometer.
	 * 
	 * @return Accelerometer
	 */
	public Accelerometer getAccelerometer() {
		return accelerometer;
	}
}
