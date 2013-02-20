package com.miviclin.droidengine2d.engine;

/**
 * Game
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Game {
	
	public Game() {
		
	}
	
	public abstract void onEnginePaused();
	
	public abstract void onEngineResumed();
	
	public abstract void onEngineDisposed();
	
}
