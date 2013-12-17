package com.miviclin.droidengine2d.input;

import android.view.MotionEvent;

/**
 * TouchController.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TouchController {

	private volatile boolean touchDetected;
	private volatile MotionEvent motionEvent;
	private TouchListener touchListener;

	/**
	 * Creates a new TouchController.
	 */
	public TouchController() {
		this.touchDetected = false;
		this.motionEvent = null;
		this.touchListener = null;
	}

	/**
	 * Sets the MotionEvent of this TouchController. The MotionEvent will be later used when this TouchController calls
	 * {@link TouchListener#onTouch(MotionEvent)}.
	 * 
	 * @param motionEvent MotionEvent.
	 */
	public void setMotionEvent(MotionEvent motionEvent) {
		this.motionEvent = motionEvent;
		if (motionEvent != null) {
			touchDetected = true;
		}
	}

	/**
	 * Sets the {@link TouchListener} of this TouchController.
	 * 
	 * @param touchListener TouchListener.
	 */
	public void setTouchListener(TouchListener touchListener) {
		this.touchListener = touchListener;
	}

	/**
	 * Processes touch input.<br>
	 * This method should be called when the game updates, before the update is processed. If a touch event has
	 * happened, this method will call {@link TouchListener#onTouch(MotionEvent)}.
	 */
	public void processTouchInput() {
		if ((touchListener != null) && touchDetected) {
			touchListener.onTouch(motionEvent);
		}
		touchDetected = false;
	}

}
