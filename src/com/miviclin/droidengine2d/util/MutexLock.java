package com.miviclin.droidengine2d.util;

/**
 * MutexLock actua como un semaforo binario.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class MutexLock {
	
	private Object lock;
	private boolean notified;
	
	/**
	 * Constructor
	 */
	public MutexLock() {
		this.lock = new Object();
		this.notified = false;
	}
	
	/**
	 * Bloquea el lock
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
	 * Desbloquea el lock
	 */
	public void unlock() {
		synchronized (lock) {
			notified = true;
			lock.notify();
		}
	}
	
}
