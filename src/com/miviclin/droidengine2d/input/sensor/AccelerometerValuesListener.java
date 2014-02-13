package com.miviclin.droidengine2d.input.sensor;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.miviclin.droidengine2d.util.ActivityUtilities;

/**
 * AccelerometerValuesListener.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class AccelerometerValuesListener implements SensorEventListener {

	private volatile float lowPassFilterAttenuation;
	private volatile float[] accelerometerValues;

	private boolean useCoordinateSystemOfDisplay;
	private Activity activity;
	private int defaultOrientationOfDevice;

	/**
	 * Creates an AccelerometerValuesListener that uses the default coordinate system of the device.
	 * 
	 * @param activity Activity.
	 * @param lowPassFilterAttenuation Low pass filter attenuation value.
	 */
	public AccelerometerValuesListener(Activity activity, float lowPassFilterAttenuation) {
		this.lowPassFilterAttenuation = lowPassFilterAttenuation;
		this.accelerometerValues = new float[3];
		this.useCoordinateSystemOfDisplay = false;
		this.activity = activity;
		this.defaultOrientationOfDevice = ActivityUtilities.getDefaultOrientationOfDevice(activity);
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
	 * After this method is called, the values returned by {@link #getX()}, {@link #getY()} and {@link #getZ()} will be
	 * in the default coordinate system of the device. The coordinate system is defined relative to the screen of the
	 * device when the device is held in its default orientation. When a device is held in its default orientation, the
	 * X axis is horizontal and points to the right, the Y axis is vertical and points up, and the Z axis points toward
	 * the outside of the screen face. In this system, coordinates behind the screen have negative Z values.
	 */
	public void useDefaultCoordinateSystemOfDevice() {
		this.useCoordinateSystemOfDisplay = false;
	}

	/**
	 * After this method is called, the values returned by {@link #getX()}, {@link #getY()} and {@link #getZ()} will be
	 * in the current coordinate system of the display. If the current orientation is
	 * {@link Configuration#ORIENTATION_PORTRAIT} and the device is held in portrait orientation, or if the current
	 * orientation is {@link Configuration#ORIENTATION_LANDSCAPE} and the device is held in landscape orientation, the X
	 * axis is horizontal and points to the right, the Y axis is vertical and points up, and the Z axis points toward
	 * the outside of the screen face. In this system, coordinates behind the screen have negative Z values.
	 */
	public void useCoordinateSystemOfDisplay() {
		this.useCoordinateSystemOfDisplay = true;
	}

	/**
	 * Returns the X component of the vector read from the accelerometer.<br>
	 * The vector will be in the current coordinate system of this AccelerometerValuesListener. Call
	 * {@link #useDefaultCoordinateSystemOfDevice()} or {@link #useCoordinateSystemOfDisplay()} to set the coordinate
	 * system to be used.
	 * 
	 * @return X
	 */
	public float getX() {
		if (useCoordinateSystemOfDisplay) {
			int currentOrientationOfDisplay = activity.getResources().getConfiguration().orientation;
			if (currentOrientationOfDisplay != defaultOrientationOfDevice) {
				return -accelerometerValues[1];
			}
		}
		return accelerometerValues[0];
	}

	/**
	 * Returns the Y component of the vector read from the accelerometer.<br>
	 * The vector will be in the current coordinate system of this AccelerometerValuesListener. Call
	 * {@link #useDefaultCoordinateSystemOfDevice()} or {@link #useCoordinateSystemOfDisplay()} to set the coordinate
	 * system to be used.
	 * 
	 * @return Y
	 */
	public float getY() {
		if (useCoordinateSystemOfDisplay) {
			int currentOrientationOfDisplay = activity.getResources().getConfiguration().orientation;
			if (currentOrientationOfDisplay != defaultOrientationOfDevice) {
				return accelerometerValues[0];
			}
		}
		return accelerometerValues[1];
	}

	/**
	 * Returns the Z component of the vector read from the accelerometer.<br>
	 * The vector will be in the current coordinate system of this AccelerometerValuesListener. Call
	 * {@link #useDefaultCoordinateSystemOfDevice()} or {@link #useCoordinateSystemOfDisplay()} to set the coordinate
	 * system to be used.
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
