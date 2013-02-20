package com.miviclin.droidengine2d.engine;

import com.miviclin.droidengine2d.util.MutexLock;

/**
 * EngineLock se utiliza para controlar la sincronizacion entre el hilo de actualizacion del juego y el hilo de renderizado.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class EngineLock {
	
	private final MutexLock updateLock;
	private final MutexLock renderLock;
	
	public EngineLock() {
		this.updateLock = new MutexLock();
		this.renderLock = new MutexLock();
	}
	
	public void waitUntilCanUpdate() {
		updateLock.lock();
	}
	
	public void notifyCanUpdate() {
		updateLock.unlock();
	}
	
	public void waitUntilCanRender() {
		renderLock.lock();
	}
	
	public void notifyCanRender() {
		renderLock.unlock();
	}
	
}
