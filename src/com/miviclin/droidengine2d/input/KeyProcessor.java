package com.miviclin.droidengine2d.input;

import android.app.Activity;
import android.view.KeyEvent;

import com.miviclin.collections.PooledLinkedQueue;

/**
 * Key events can be queued in a KeyProcessor to be processed later.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class KeyProcessor {

	private final Object keyEventQueueLock = new Object();

	private KeyEventProcessor keyEventProcessor;
	private PooledLinkedQueue<KeyEventInfo> keyEventInfoQueue;
	private Activity activity;

	/**
	 * Creates a new KeyProcessor.
	 * 
	 * @param activity Activity;
	 */
	public KeyProcessor(Activity activity) {
		this.keyEventProcessor = null;
		this.keyEventInfoQueue = new PooledLinkedQueue<KeyEventInfo>(60);

		this.activity = activity;
	}

	/**
	 * Queues a KeyEventInfo, wich is a copy of the specified KeyEvent, for later processing.<br>
	 * The event will be recycled when {@link KeyProcessor#processKeyInput()} is called.
	 * 
	 * @param keyEvent KeyEvent.
	 */
	public void queueCopyOfKeyEvent(KeyEvent keyEvent) {
		synchronized (keyEventQueueLock) {
			KeyEventInfo keyEventInfo = KeyEventInfo.obtain(keyEvent);
			keyEventInfoQueue.add(keyEventInfo);
		}
	}

	/**
	 * Processes key input.<br>
	 * This method should be called when the game updates, before the update is processed. If a key event has happened,
	 * this method will call {@link KeyEventProcessor#onKey(int, KeyEvent)}.
	 */
	public void processKeyInput() {
		synchronized (keyEventQueueLock) {
			while (!keyEventInfoQueue.isEmpty()) {
				KeyEventInfo keyEventInfo = keyEventInfoQueue.poll();
				if (keyEventProcessor != null) {
					keyEventProcessor.processKeyEvent(keyEventInfo);
				} else if (keyEventInfo.getKeyCode() == KeyEvent.KEYCODE_BACK) {
					onBackPressed();
				}
				keyEventInfo.recycle();
			}
		}
	}

	/**
	 * This method is called when the back button is pressed and there is no KeyEventProcessor set in this KeyProcessor.<br>
	 * Finishes the Activity referenced by this KeyProcessor.
	 */
	public void onBackPressed() {
		activity.finish();
	}

	/**
	 * Sets the {@link KeyEventProcessor} of this KeyProcessor.
	 * 
	 * @param keyEventProcessor KeyEventProcessor.
	 */
	public void setKeyEventProcessor(KeyEventProcessor keyEventProcessor) {
		this.keyEventProcessor = keyEventProcessor;
	}

}
