package com.miviclin.droidengine2d.input;

import android.view.MotionEvent;

import com.miviclin.collections.PooledLinkedQueue;

/**
 * Motion events can be queued in a TouchProcessor to be processed later.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TouchProcessor {

	private final Object motionEventQueueLock = new Object();

	private MotionEventProcessor motionEventProcessor;
	private PooledLinkedQueue<MotionEvent> motionEventQueue;

	/**
	 * Creates a new TouchProcessor.
	 */
	public TouchProcessor() {
		this.motionEventProcessor = null;
		this.motionEventQueue = new PooledLinkedQueue<MotionEvent>(60);
	}

	/**
	 * Queues a copy of the specified MotionEvent for later processing. The event will be recycled when
	 * {@link TouchProcessor#processTouchInput()} is called.
	 * 
	 * @param motionEvent MotionEvent.
	 */
	public void queueCopyOfMotionEvent(MotionEvent motionEvent) {
		synchronized (motionEventQueueLock) {
			MotionEvent motionEventToQueue = MotionEvent.obtain(motionEvent);
			motionEventQueue.add(motionEventToQueue);
		}
	}

	/**
	 * Processes touch input.<br>
	 * This method should be called when the game updates, before the update is processed.<br>
	 * {@link MotionEventProcessor#processMotionEvent(MotionEvent)} will be called once per MotionEvent queued in this
	 * TouchProcessor. After that call, {@link MotionEvent#recycle()} will be called on the MotionEvent.
	 */
	public void processTouchInput() {
		if (motionEventProcessor != null) {
			synchronized (motionEventQueueLock) {
				while (!motionEventQueue.isEmpty()) {
					MotionEvent motionEvent = motionEventQueue.poll();
					motionEventProcessor.processMotionEvent(motionEvent);
					motionEvent.recycle();
				}
			}
		}
	}

	/**
	 * Sets the {@link MotionEventProcessor} of this TouchProcessor.
	 * 
	 * @param motionEventProcessor TouchListener.
	 */
	public void setMotionEventProcessor(MotionEventProcessor motionEventProcessor) {
		this.motionEventProcessor = motionEventProcessor;
	}

}
