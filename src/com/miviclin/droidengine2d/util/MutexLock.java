package com.miviclin.droidengine2d.util;

/**
 * MutexLock acts as a binary semaphore.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class MutexLock {

	private Object lock;
	private boolean notified;

	/**
	 * Constructor.
	 */
	public MutexLock() {
		this.lock = new Object();
		this.notified = false;
	}

	/**
	 * Locks the lock.
	 */
	public void lock() {
		synchronized (lock) {
			if (!notified) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
				}
			}
			notified = false;
		}
	}

	/**
	 * Unlocks the lock.
	 */
	public void unlock() {
		synchronized (lock) {
			notified = true;
			lock.notify();
		}
	}

}
