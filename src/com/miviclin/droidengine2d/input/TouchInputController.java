package com.miviclin.droidengine2d.input;

import android.view.MotionEvent;

import com.miviclin.collections.PooledLinkedQueue;

/**
 * TouchInputController.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TouchInputController {

	private final Object motionEventQueueLock = new Object();

	private TouchInputProcessor touchInputProcessor;
	private PooledLinkedQueue<MotionEvent> motionEventQueue;

	/**
	 * Creates a new TouchInputController.
	 */
	public TouchInputController() {
		this.touchInputProcessor = null;
		this.motionEventQueue = new PooledLinkedQueue<MotionEvent>(60);
	}

	/**
	 * Queues a copy of the specified MotionEvent for later processing. The event will be recycled when
	 * {@link TouchInputController#processTouchInput()} is called.
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
	 * TouchInputController. After that call, {@link MotionEvent#recycle()} will be called on the MotionEvent.
	 */
	public void processTouchInput() {
		if (touchInputProcessor != null) {
			synchronized (motionEventQueueLock) {
				while (!motionEventQueue.isEmpty()) {
					MotionEvent motionEvent = motionEventQueue.poll();
					touchInputProcessor.processMotionEvent(motionEvent);
					motionEvent.recycle();
				}
			}
		}
	}

	/**
	 * Sets the {@link TouchInputProcessor} of this TouchInputController.
	 * 
	 * @param touchInputProcessor TouchListener.
	 */
	public void setTouchInputProcessor(TouchInputProcessor touchInputProcessor) {
		this.touchInputProcessor = touchInputProcessor;
	}

}
