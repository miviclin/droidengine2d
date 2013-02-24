package com.miviclin.droidengine2d.engine;

import android.app.Activity;

/**
 * Game
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Game {
	
	private Activity activity;
	
	public Game(Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("The Activity can not be null");
		}
		this.activity = activity;
	}
	
	public Activity getActivity() {
		return activity;
	}
	
	public abstract void onEnginePaused();
	
	public abstract void onEngineResumed();
	
	public abstract void onEngineDisposed();
	
	public abstract void update(float delta);
	
	public abstract void draw(); // TODO: Pasarle un SpriteBatch o algo que se encargue de pintar en pantalla
	
}
