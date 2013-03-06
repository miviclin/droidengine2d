package com.miviclin.droidengine2d.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Define el comportamiento del renderer.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface EngineRenderer {
	
	/**
	 * Se llama desde {@link GLRenderer#onSurfaceCreated(GL10, EGLConfig)}
	 */
	public abstract void onSurfaceCreated(GL10 glUnused, EGLConfig config);
	
	/**
	 * Se llama desde {@link GLRenderer#onSurfaceChanged(GL10, int, int)}
	 */
	public abstract void onSurfaceChanged(GL10 glUnused, int width, int height);
	
	/**
	 * Se llama desde {@link GLRenderer##onDrawFrame(GL10)}
	 */
	public abstract void onDrawFrame(GL10 glUnused);
	
}
