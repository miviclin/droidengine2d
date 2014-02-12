package com.miviclin.droidengine2d.input;

import android.view.MotionEvent;

/**
 * Processes motion events.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface MotionEventProcessor {

	/**
	 * This method is called from {@link TouchProcessor#processTouchInput()}.<br>
	 * It should not be called manually.
	 * 
	 * @param motionEvent MotionEvent.
	 */
	public void processMotionEvent(MotionEvent motionEvent);

}
