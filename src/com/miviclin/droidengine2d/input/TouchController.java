package com.miviclin.droidengine2d.input;

import android.view.MotionEvent;

import com.miviclin.droidengine2d.scenes.Scene;

/**
 * Controlador de eventos Touch
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TouchController {
	
	private volatile boolean touchDetected;
	private volatile MotionEvent motionEvent;
	private TouchListener touchListener;
	
	/**
	 * Constructor
	 */
	public TouchController() {
		this.touchDetected = false;
		this.motionEvent = null;
		this.touchListener = null;
	}
	
	/**
	 * Asigna un MotionEvent al TouchController. Este MotionEvent sera el que se le pase a {@link TouchListener#onTouch(MotionEvent)}.<br>
	 * Este metodo no deberia llamarse manualmente, lo utiliza internamente el framework.
	 * 
	 * @param motionEvent MotionEvent
	 */
	public void setMotionEvent(MotionEvent motionEvent) {
		this.motionEvent = motionEvent;
		if (motionEvent != null) {
			touchDetected = true;
		}
	}
	
	/**
	 * Asigna un {@link TouchListener} al TouchController
	 * 
	 * @param touchListener TouchListener
	 */
	public void setTouchListener(TouchListener touchListener) {
		this.touchListener = touchListener;
	}
	
	/**
	 * Procesa el touch.<br>
	 * Este metodo deberia ser llamado desde {@link Scene#update(float)} para que en caso de que haya un evento de touch, ejecute
	 * {@link TouchListener#onTouch(MotionEvent)}
	 */
	public void processTouchInput() {
		if ((touchListener != null) && touchDetected) {
			touchListener.onTouch(motionEvent);
		}
		touchDetected = false;
	}
	
}
