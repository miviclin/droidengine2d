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
	
	public abstract void update(float delta);
	
	public abstract void draw(); // TODO: Pasarle un SpriteBatch o algo que se encargue de pintar en pantalla
	
}
