package com.miviclin.droidengine2d;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * EngineLock se utiliza para controlar la sincronizacion entre el hilo de actualizacion del juego y el hilo de renderizado.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class EngineLock {
	
	public final AtomicBoolean allowUpdate;
	public final Object lock;
	
	public EngineLock() {
		this.allowUpdate = new AtomicBoolean();
		this.lock = new Object();
	}
	
}
