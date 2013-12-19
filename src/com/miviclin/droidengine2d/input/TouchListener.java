package com.miviclin.droidengine2d.input;

import android.view.MotionEvent;

/**
 * Touch event listener.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface TouchListener {

	/**
	 * This method is called from {@link TouchController#processTouchInput()} when a touch event happens.<br>
	 * It should not be called manually.
	 * 
	 * @param motionEvent MotionEvent.
	 * @return true if the event was consumed, false otherwise
	 */
	public boolean onTouch(MotionEvent motionEvent);

}
