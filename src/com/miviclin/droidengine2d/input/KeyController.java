package com.miviclin.droidengine2d.input;

import android.app.Activity;
import android.view.KeyEvent;

import com.miviclin.droidengine2d.Game;

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
	private boolean defaultOnBackPressedEnabled;
	private Activity activity;

	/**
	 * Creates a new KeyController.
	 */
	public KeyController(Activity activity) {
		this.keyDetected = false;
		this.keyCode = -1;
		this.keyEvent = null;
		this.keyListener = null;
		this.defaultOnBackPressedEnabled = true;
		this.activity = activity;
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

	/**
	 * Enables the default behavior of {@link #onBackPressed()}.
	 */
	public void enableDefaultOnBackPressed() {
		this.defaultOnBackPressedEnabled = true;
	}

	/**
	 * Disables the default behavior of {@link #onBackPressed()}.
	 */
	public void disableDefaultOnBackPressed() {
		this.defaultOnBackPressedEnabled = false;
	}

	/**
	 * This method is called from {@link Game#onBackPressed()}.<br>
	 * The default implementation simply finishes the current activity.
	 */
	public void onBackPressed() {
		if (defaultOnBackPressedEnabled) {
			activity.finish();
		}
	}

}
