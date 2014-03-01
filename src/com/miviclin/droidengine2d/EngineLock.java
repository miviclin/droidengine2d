package com.miviclin.droidengine2d;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * EngineLock is used to synchronize the game thread and the rendering thread.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class EngineLock {

	private final AtomicBoolean allowUpdateFlag;
	private final Object lock;

	/**
	 * Creates an EngineLock.
	 */
	public EngineLock() {
		this.allowUpdateFlag = new AtomicBoolean();
		this.lock = new Object();
	}

	/**
	 * Returns the allow update flag.<br>
	 * If this flag is set, the game can be updated.
	 * 
	 * @return The allow update flag
	 */
	public AtomicBoolean getAllowUpdateFlag() {
		return allowUpdateFlag;
	}

	/**
	 * Returns the object used to lock the GameThread while the rendering thread is working and vice-versa.
	 * 
	 * @return Lock object
	 */
	public Object getLock() {
		return lock;
	}

}
