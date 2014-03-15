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
package com.miviclin.droidengine2d.input.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Accelerometer.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Accelerometer {

	private SensorManager sensorManager;
	private AccelerometerValuesListener valuesListener;
	private SensorEventListener customListener;
	private boolean listening;

	/**
	 * Creates an Accelerometer with an AccelerometerValuesListener.
	 * 
	 * @param activity Activity.
	 */
	public Accelerometer(Activity activity) {
		this(activity, null);
	}

	/**
	 * Creates an Accelerometer with an AccelerometerValuesListener and the specified listener.
	 * 
	 * @param activity Activity.
	 * @param customListener SensorEventListener.
	 */
	public Accelerometer(Activity activity, SensorEventListener customListener) {
		this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		this.valuesListener = new AccelerometerValuesListener(activity, 0.2f);
		this.customListener = customListener;
		this.listening = false;
	}

	/**
	 * Starts listening the accelerometer.
	 */
	public void startListening() {
		Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(valuesListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
		if (customListener != null) {
			sensorManager.registerListener(customListener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
		listening = true;
	}

	/**
	 * Stops listening the accelerometer.
	 */
	public void stopListening() {
		sensorManager.unregisterListener(valuesListener);
		if (customListener != null) {
			sensorManager.unregisterListener(customListener);
		}
		listening = false;
	}

	/**
	 * Returns true if this object is listening the accelerometer.
	 * 
	 * @return true if this object is listening the accelerometer, false otherwise
	 */
	public boolean isListening() {
		return listening;
	}

	/**
	 * Returns the AccelerometerValuesListener.
	 * 
	 * @return AccelerometerValuesListener
	 */
	public AccelerometerValuesListener getValuesListener() {
		return valuesListener;
	}

	/**
	 * Returns the custom listener.
	 * 
	 * @return SensorEventListener
	 */
	public SensorEventListener getCustomListener() {
		return customListener;
	}

	/**
	 * Sets the custom listener.
	 * 
	 * @param listener SensorEventListener.
	 */
	public void setCustomListener(SensorEventListener listener) {
		this.customListener = listener;
	}

}
