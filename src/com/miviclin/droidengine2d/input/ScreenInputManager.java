package com.miviclin.droidengine2d.input;

import android.app.Activity;

import com.miviclin.droidengine2d.input.sensor.Accelerometer;
import com.miviclin.droidengine2d.input.sensor.AccelerometerValuesListener;

/**
 * InputManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ScreenInputManager {

	private TouchProcessor touchProcessor;
	private KeyProcessor keyProcessor;
	private Accelerometer accelerometer;

	/**
	 * Creates a new InputManager.
	 * 
	 * @param activity Activity.
	 */
	public ScreenInputManager(Activity activity) {
		this(activity, new TouchProcessor(), new KeyProcessor(activity));
	}

	/**
	 * Creates a new InputManager.
	 * 
	 * @param activity Activity.
	 * @param touchProcessor TouchProcessor.
	 * @param keyProcessor KeyProcessor.
	 */
	public ScreenInputManager(Activity activity, TouchProcessor touchProcessor, KeyProcessor keyProcessor) {
		this.touchProcessor = touchProcessor;
		this.keyProcessor = keyProcessor;
		this.accelerometer = new Accelerometer(activity, new AccelerometerValuesListener(0.2f));
	}

	/**
	 * Processes touch and key input.<br>
	 * This method should be called when the game updates, before the update is processed.
	 */
	public void processInput() {
		touchProcessor.processTouchInput();
		keyProcessor.processKeyInput();
	}

	/**
	 * Returns the TouchProcessor.
	 * 
	 * @return TouchProcessor
	 */
	public TouchProcessor getTouchProcessor() {
		return touchProcessor;
	}

	/**
	 * Returns the KeyProcessor.
	 * 
	 * @return KeyProcessor
	 */
	public KeyProcessor getKeyProcessor() {
		return keyProcessor;
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
