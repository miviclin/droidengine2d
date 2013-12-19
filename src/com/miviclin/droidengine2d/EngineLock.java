package com.miviclin.droidengine2d;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * EngineLock is used to synchronize the game thread and the rendering thread.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class EngineLock {

	public final AtomicBoolean allowUpdate;
	public final Object lock;

	/**
	 * Creates an EngineLock.
	 */
	public EngineLock() {
		this.allowUpdate = new AtomicBoolean();
		this.lock = new Object();
	}

}
