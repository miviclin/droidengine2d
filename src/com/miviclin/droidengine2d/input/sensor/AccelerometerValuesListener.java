package com.miviclin.droidengine2d.input.sensor;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * AccelerometerValuesListener.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class AccelerometerValuesListener implements SensorEventListener {

	private volatile float lowPassFilterAttenuation;
	private volatile float[] accelerometerValues;

	/**
	 * Creates an AccelerometerValuesListener.
	 * 
	 * @param lowPassFilterAttenuation Low pass filter attenuation value.
	 */
	public AccelerometerValuesListener(float lowPassFilterAttenuation) {
		this.lowPassFilterAttenuation = lowPassFilterAttenuation;
		accelerometerValues = new float[3];
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

}
