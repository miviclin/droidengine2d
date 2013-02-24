package com.miviclin.droidengine2d.engine;

import android.opengl.GLSurfaceView;

import com.miviclin.droidengine2d.graphics.GLRenderer;
import com.miviclin.droidengine2d.graphics.GLView;

/**
 * Gestor principal del engine. Maneja el hilo del juego y el hilo del renderer.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Engine {
	
	private GameThread gameThread;
	private GLRenderer renderer;
	private GLView glView;
	
	/**
	 * Crea un Engine.<br>
	 * AndroidEngine utiliza OpenGL ES 2.0, por tanto, es recomendable comprobar primero si el dispositivo soporta OpenGL ES 2.0. Ejemplo:
	 * 
	 * <pre>
	 * {@code Engine engine;
	 * if (detectOpenGLES20()) {
	 *     engine = new Engine(...);
	 * } else {
	 *     // Indicar al usuario que su dispositivo no soporta OpenGL ES 2.0 y cerrar la app
	 * }
	 * </pre>
	 * 
	 * @param game Juego
	 * @throws IllegalArgumentException Si el juego es null
	 */
	public Engine(Game game) {
		EngineLock engineLock = new EngineLock();
		if (game == null) {
			throw new IllegalArgumentException("The Game can not be null");
		}
		this.glView = new GLView(game.getActivity());
		this.glView.setEGLContextClientVersion(2);
		this.gameThread = new GameThread(game, glView, engineLock);
		this.renderer = new GLRenderer(game, engineLock);
	}
	
	/**
	 * Lanza los hilos del Juego y GLView e inicia el juego
	 */
	public void startGame() {
		gameThread.start();
		glView.setRenderer(renderer);
		glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	/**
	 * Pausa los hilos del juego. Llamar en Activity.onPause()
	 */
	public void onPause() {
		glView.onPause();
		gameThread.pause();
	}
	
	/**
	 * Reanuda los hilos del juego. Llamar en Activity.onResume()
	 */
	public void onResume() {
		glView.onResume();
		gameThread.resume();
	}
	
	/**
	 * Para el juego. Llamar cuando se termine la aplicacion.
	 */
	public void onFinish() {
		gameThread.terminate();
	}
	
	/**
	 * Devuelve el GLView
	 * 
	 * @return GLView
	 */
	public GLView getGlView() {
		return glView;
	}
	
}
