package com.miviclin.droidengine2d.input;

import android.view.KeyEvent;

/**
 * KeyController.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class KeyController {

	private volatile boolean keyDetected;
	private volatile int keyCode;
	private volatile KeyEvent keyEvent;
	private KeyListener keyListener;

	/**
	 * Creates a new KeyController.
	 */
	public KeyController() {
		this.keyDetected = false;
		this.keyCode = -1;
		this.keyEvent = null;
		this.keyListener = null;
	}

	/**
	 * Sets the KeyEvent of this KeyController. The KeyEvent will be later used when this KeyController calls
	 * {@link KeyListener#onKey(int, KeyEvent)}.
	 * 
	 * @param keyCode Key code.
	 * @param keyEvent KeyEvent.
	 */
	public void setKeyEvent(int keyCode, KeyEvent keyEvent) {
		this.keyCode = keyCode;
		this.keyEvent = keyEvent;
		if (keyEvent != null) {
			keyDetected = true;
		}
	}

	/**
	 * Sets the {@link KeyListener} of this KeyController.
	 * 
	 * @param keyListener KeyListener.
	 */
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}

	/**
	 * Processes key input.<br>
	 * This method should be called when the game updates, before the update is processed. If a key event has happened,
	 * this method will call {@link KeyListener#onKey(int, KeyEvent)}.
	 */
	public void processKeyInput() {
		if ((keyListener != null) && keyDetected) {
			keyListener.onKey(keyCode, keyEvent);
		}
		keyDetected = false;
	}

}
