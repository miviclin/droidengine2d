package com.miviclin.droidengine2d.input.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Clase que toma datos del acelerometro
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Accelerometer implements SensorEventListener {
	
	private volatile float[] accelerometerValues;
	private SensorManager sensorManager;
	
	/**
	 * Crea el objeto y empieza a registrar valores.
	 * 
	 * @param activity Activity
	 */
	public Accelerometer(Activity activity) {
		accelerometerValues = new float[3];
		sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			SensorUtilities.lowPassFilter(event.values, accelerometerValues, 3, 0.2f);
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	
	/**
	 * Comienza a registrar valores del sensor
	 */
	public void startListening() {
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	/**
	 * Para de registrar valores del sensor
	 */
	public void stopListening() {
		sensorManager.unregisterListener(this);
	}
	
	/**
	 * Devuelve la componente X del acelerometro
	 * 
	 * @return X
	 */
	public float getX() {
		return accelerometerValues[0];
	}
	
	/**
	 * Devuelve la componente Y del acelerometro
	 * 
	 * @return Y
	 */
	public float getY() {
		return accelerometerValues[1];
	}
	
	/**
	 * Devuelve la componente Z del acelerometro
	 * 
	 * @return Z
	 */
	public float getZ() {
		return accelerometerValues[2];
	}
}
