package com.miviclin.droidengine2d.input.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Accelerometer.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Accelerometer implements SensorEventListener {

	private volatile float lowPassFilterAttenuation;
	private volatile float[] accelerometerValues;
	private SensorManager sensorManager;

	/**
	 * Creates an Accelerometer and starts registering values.
	 * 
	 * @param activity Activity.
	 */
	public Accelerometer(Activity activity) {
		this(0.2f, activity);
	}

	/**
	 * Creates an Accelerometer and starts registering values.
	 * 
	 * @param lowPassFilterAttenuation Low pass filter attenuation value.
	 * @param activity Activity.
	 */
	public Accelerometer(float lowPassFilterAttenuation, Activity activity) {
		this.lowPassFilterAttenuation = lowPassFilterAttenuation;
		accelerometerValues = new float[3];
		sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

		startListeningAccelerometer();
	}

	/**
	 * Creates an Accelerometer.
	 * 
	 * @param lowPassFilterAttenuation Low pass filter attenuation value.
	 * @param activity Activity.
	 * @param startListening True to start listening sensor changes.
	 */
	public Accelerometer(float lowPassFilterAttenuation, Activity activity, boolean startListening) {
		this.lowPassFilterAttenuation = lowPassFilterAttenuation;
		accelerometerValues = new float[3];
		sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);

		if (startListening) {
			startListeningAccelerometer();
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			SensorUtilities.lowPassFilter(event.values, accelerometerValues, 3, lowPassFilterAttenuation);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	/**
	 * Starts listening the accelerometer.
	 */
	public void startListening() {
		startListeningAccelerometer();
	}

	/**
	 * Starts listening the accelerometer. Internal implementation.
	 */
	private void startListeningAccelerometer() {
		Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	/**
	 * Stops listening the accelerometer.
	 */
	public void stopListening() {
		sensorManager.unregisterListener(this);
	}

	/**
	 * Returns the attenuation value of the low pass filter.
	 * 
	 * @return the attenuation value of the low pass filter
	 */
	public float getLowPassFilterAttenuation() {
		return lowPassFilterAttenuation;
	}

	/**
	 * Sets the attenuation value of the low pass filter
	 * 
	 * @param lowPassFilterAttenuation New value.
	 */
	public void setLowPassFilterAttenuation(float lowPassFilterAttenuation) {
		this.lowPassFilterAttenuation = lowPassFilterAttenuation;
	}

	/**
	 * Returns the X component of the vector read from the accelerometer.
	 * 
	 * @return X
	 */
	public float getX() {
		return accelerometerValues[0];
	}

	/**
	 * Returns the Y component of the vector read from the accelerometer.
	 * 
	 * @return Y
	 */
	public float getY() {
		return accelerometerValues[1];
	}

	/**
	 * Returns the Z component of the vector read from the accelerometer.
	 * 
	 * @return Z
	 */
	public float getZ() {
		return accelerometerValues[2];
	}
}
