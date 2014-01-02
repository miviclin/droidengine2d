package com.miviclin.droidengine2d.input.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * AccelerometerController.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class AccelerometerController {

	private SensorManager sensorManager;
	private SensorEventListener listener;

	/**
	 * Creates an AccelerometerController.
	 * 
	 * @param activity Activity.
	 */
	public AccelerometerController(Activity activity) {
		this(activity, null);
	}

	/**
	 * Creates an AccelerometerController with the specified listener.
	 * 
	 * @param activity Activity.
	 * @param listener SensorEventListener.
	 */
	public AccelerometerController(Activity activity, SensorEventListener listener) {
		this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		this.listener = listener;
	}

	/**
	 * Starts listening the accelerometer.
	 */
	public void startListening() {
		if (listener != null) {
			Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			sensorManager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	/**
	 * Stops listening the accelerometer.
	 */
	public void stopListening() {
		if (listener != null) {
			sensorManager.unregisterListener(listener);
		}
	}

	/**
	 * Returns the listener.
	 * 
	 * @return SensorEventListener
	 */
	public SensorEventListener getListener() {
		return listener;
	}

	/**
	 * Sets the listener.
	 * 
	 * @param listener SensorEventListener.
	 */
	public void setListener(SensorEventListener listener) {
		this.listener = listener;
	}

}
