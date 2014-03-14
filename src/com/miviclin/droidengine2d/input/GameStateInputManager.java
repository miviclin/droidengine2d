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
package com.miviclin.droidengine2d.input;

import android.app.Activity;

import com.miviclin.droidengine2d.input.sensor.Accelerometer;

/**
 * GameStateInputManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GameStateInputManager {

	private TouchProcessor touchProcessor;
	private KeyProcessor keyProcessor;
	private Accelerometer accelerometer;

	/**
	 * Creates a new GameStateInputManager.
	 * 
	 * @param activity Activity.
	 */
	public GameStateInputManager(Activity activity) {
		this(activity, new TouchProcessor(), new KeyProcessor(activity));
	}

	/**
	 * Creates a new GameStateInputManager.
	 * 
	 * @param activity Activity.
	 * @param touchProcessor TouchProcessor.
	 * @param keyProcessor KeyProcessor.
	 */
	public GameStateInputManager(Activity activity, TouchProcessor touchProcessor, KeyProcessor keyProcessor) {
		this.touchProcessor = touchProcessor;
		this.keyProcessor = keyProcessor;
		this.accelerometer = new Accelerometer(activity);
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
