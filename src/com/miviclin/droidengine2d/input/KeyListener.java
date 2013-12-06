package com.miviclin.droidengine2d.input;

import android.view.KeyEvent;

/**
 * Key event listener.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface KeyListener {

	/**
	 * This method is called from {@link KeyController#processKeyInput()} when a key event happens.<br>
	 * It should not be called manually.
	 * 
	 * @param keyCode Key code.
	 * @param event KeyEvent.
	 * @return true if the event was consumed, false otherwise
	 */
	public boolean onKey(int keyCode, KeyEvent event);

}
