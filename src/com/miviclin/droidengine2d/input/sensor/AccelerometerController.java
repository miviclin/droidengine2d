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
	private AccelerometerValuesListener valuesListener;
	private SensorEventListener customListener;

	/**
	 * Creates an AccelerometerController with an AccelerometerValuesListener.
	 * 
	 * @param activity Activity.
	 */
	public AccelerometerController(Activity activity) {
		this(activity, null);
	}

	/**
	 * Creates an AccelerometerController with an AccelerometerValuesListener and the specified listener.
	 * 
	 * @param activity Activity.
	 * @param customListener SensorEventListener.
	 */
	public AccelerometerController(Activity activity, SensorEventListener customListener) {
		this.sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		this.valuesListener = new AccelerometerValuesListener(0.2f);
		this.customListener = customListener;
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
	}

	/**
	 * Stops listening the accelerometer.
	 */
	public void stopListening() {
		sensorManager.unregisterListener(valuesListener);
		if (customListener != null) {
			sensorManager.unregisterListener(customListener);
		}
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
