package com.miviclin.droidengine2d.input;

import android.view.MotionEvent;

import com.miviclin.collections.PooledLinkedQueue;

/**
 * TouchController.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TouchController {

	private final Object motionEventQueueLock = new Object();

	private TouchInputProcessor touchInputProcessor;
	private PooledLinkedQueue<MotionEvent> motionEventQueue;

	/**
	 * Creates a new TouchController.
	 */
	public TouchController() {
		this.touchInputProcessor = null;
		this.motionEventQueue = new PooledLinkedQueue<MotionEvent>(60);
	}

	/**
	 * Queues a copy of the specified MotionEvent for later processing. The event will be recycled when
	 * {@link TouchController#processTouchInput()} is called.
	 * 
	 * @param motionEvent MotionEvent.
	 */
	public void queueCopyOfMotionEvent(MotionEvent motionEvent) {
		if (motionEvent != null) {
			synchronized (motionEventQueueLock) {
				MotionEvent motionEventToQueue = MotionEvent.obtain(motionEvent);
				motionEventQueue.add(motionEventToQueue);
			}
		}
	}

	/**
	 * Processes touch input.<br>
	 * This method should be called when the game updates, before the update is processed.<br>
	 * {@link TouchInputProcessor#processMotionEvent(MotionEvent)} will be called once per MotionEvent queued in this
	 * TouchController. After that call, {@link MotionEvent#recycle()} will be called on the MotionEvent.
	 */
	public void processTouchInput() {
		if (touchInputProcessor != null) {
			MotionEvent motionEvent;
			synchronized (motionEventQueueLock) {
				while ((motionEvent = motionEventQueue.poll()) != null) {
					touchInputProcessor.processMotionEvent(motionEvent);
					motionEvent.recycle();
				}
			}
		}
	}

	/**
	 * Sets the {@link TouchInputProcessor} of this TouchController.
	 * 
	 * @param touchInputProcessor TouchListener.
	 */
	public void setTouchInputProcessor(TouchInputProcessor touchInputProcessor) {
		this.touchInputProcessor = touchInputProcessor;
	}

}
