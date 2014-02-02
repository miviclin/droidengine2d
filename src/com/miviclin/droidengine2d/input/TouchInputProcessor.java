package com.miviclin.droidengine2d.input;

import android.view.MotionEvent;

/**
 * Touch event listener.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface TouchInputProcessor {

	/**
	 * This method is called from {@link TouchInputController#processTouchInput()}.<br>
	 * It should not be called manually.
	 * 
	 * @param motionEvent MotionEvent.
	 */
	public void processMotionEvent(MotionEvent motionEvent);

}
