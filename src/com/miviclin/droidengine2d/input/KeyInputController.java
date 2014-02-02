package com.miviclin.droidengine2d.input;

import android.app.Activity;
import android.view.KeyEvent;

import com.miviclin.collections.Pool;
import com.miviclin.collections.PooledLinkedQueue;

/**
 * KeyInputController.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class KeyInputController {

	private final Object keyEventQueueLock = new Object();

	private KeyInputProcessor keyInputProcessor;
	private PooledLinkedQueue<KeyEventInfo> keyEventInfoQueue;
	private KeyEventInfoPool keyEventInfoPool;
	private Activity activity;

	/**
	 * Creates a new KeyInputController.
	 * 
	 * @param activity Activity;
	 */
	public KeyInputController(Activity activity) {
		this.keyInputProcessor = null;
		this.keyEventInfoQueue = new PooledLinkedQueue<KeyEventInfo>(60);

		int initialCapacityOfKeyEventInfoPool = 60;
		this.keyEventInfoPool = new KeyEventInfoPool(initialCapacityOfKeyEventInfoPool);
		for (int i = 0; i < initialCapacityOfKeyEventInfoPool; i++) {
			keyEventInfoPool.recycle(new KeyEventInfo());
		}

		this.activity = activity;
	}

	/**
	 * Queues a KeyEventInfo, wich is a copy of the specified KeyEvent, for later processing.<br>
	 * The event will be recycled when {@link KeyInputController#processKeyInput()} is called.
	 * 
	 * @param keyEvent KeyEvent.
	 */
	public void queueCopyOfKeyEvent(KeyEvent keyEvent) {
		if (keyEvent != null) {
			synchronized (keyEventQueueLock) {
				KeyEventInfo keyEventInfo = keyEventInfoPool.obtain();
				keyEventInfo.copyKeyEventInfo(keyEvent);
				keyEventInfoQueue.add(keyEventInfo);
			}
		}
	}

	/**
	 * Processes key input.<br>
	 * This method should be called when the game updates, before the update is processed. If a key event has happened,
	 * this method will call {@link KeyInputProcessor#onKey(int, KeyEvent)}.
	 */
	public void processKeyInput() {
		KeyEventInfo keyEventInfo;
		synchronized (keyEventQueueLock) {
			while ((keyEventInfo = keyEventInfoQueue.poll()) != null) {
				if (keyInputProcessor != null) {
					keyInputProcessor.processKeyEvent(keyEventInfo);
				} else if (keyEventInfo.getKeyCode() == KeyEvent.KEYCODE_BACK) {
					onBackPressed();
				}
				keyEventInfoPool.recycle(keyEventInfo);
			}
		}
	}

	/**
	 * This method is called when the back button is pressed and there is no KeyInputProcessor set in this
	 * KeyInputController.<br>
	 * Finishes the Activity referenced by this KeyInputController.
	 */
	public void onBackPressed() {
		activity.finish();
	}

	/**
	 * Sets the {@link KeyInputProcessor} of this KeyInputController.
	 * 
	 * @param keyInputProcessor KeyInputProcessor.
	 */
	public void setKeyInputProcessor(KeyInputProcessor keyInputProcessor) {
		this.keyInputProcessor = keyInputProcessor;
	}

	/**
	 * Pool of KeyEventInfo objects.
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	private static class KeyEventInfoPool extends Pool<KeyEventInfo> {

		/**
		 * Creates an empty KeyEventInfoPool with the specified initial capacity.
		 * 
		 * @param initialCapacity Initial capacity.
		 */
		public KeyEventInfoPool(int initialCapacity) {
			super(initialCapacity);
		}

		@Override
		public KeyEventInfo createObject() {
			return new KeyEventInfo();
		}

	}

}
