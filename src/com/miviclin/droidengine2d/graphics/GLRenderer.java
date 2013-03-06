package com.miviclin.droidengine2d.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;

import com.miviclin.droidengine2d.EngineLock;

/**
 * Responsable de relizar las llamadas a OpenGL para renderizar los elementos del juego.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GLRenderer implements GLSurfaceView.Renderer {
	
	private EngineRenderer engineRenderer;
	private EngineLock engineLock;
	
	/**
	 * Crea un GLRenderer
	 * 
	 * @param engineLock Utilizado para sincronizar correctamente los hilos
	 */
	public GLRenderer(EngineRenderer engineRenderer, EngineLock engineLock) {
		this.engineRenderer = engineRenderer;
		this.engineLock = engineLock;
	}
	
	@Override
	public final void onDrawFrame(GL10 glUnused) {
		if (!engineLock.allowUpdate.get()) {
			synchronized (engineLock.lock) {
				engineRenderer.onDrawFrame(glUnused);
				engineLock.allowUpdate.set(true);
			}
		}
	}
	
	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) {
		engineRenderer.onSurfaceChanged(glUnused, width, height);
	}
	
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		engineRenderer.onSurfaceCreated(glUnused, config);
	}
	
}
