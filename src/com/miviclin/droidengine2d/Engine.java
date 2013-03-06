package com.miviclin.droidengine2d;

import android.app.Activity;
import android.opengl.GLSurfaceView;

import com.miviclin.droidengine2d.graphics.EngineRenderer;
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
	private Activity activity;
	
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
	 * @param renderer EngineRenderer
	 * @throws IllegalArgumentException Si el juego es null
	 */
	public Engine(Game game, EngineRenderer renderer) {
		EngineLock engineLock = new EngineLock();
		if (game == null) {
			throw new IllegalArgumentException("The Game can not be null");
		}
		if (renderer == null) {
			throw new IllegalArgumentException("The Renderer can not be null");
		}
		this.glView = game.getGLView();
		this.glView.setEGLContextClientVersion(2);
		this.gameThread = new GameThread(game, glView, engineLock);
		this.renderer = new GLRenderer(renderer, engineLock);
		this.activity = game.getActivity();
	}
	
	/**
	 * Devuelve el GLView
	 * 
	 * @return GLView
	 */
	public GLView getGLView() {
		return glView;
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
	 * Pausa los hilos del juego. Llamar en Activity.onPause()<br>
	 * Si se ha llamado a onPause() porque la Activity se esta cerrando, este metodo llama a {@link Engine#onFinish()}
	 */
	public void onPause() {
		glView.onPause();
		gameThread.pause();
		if (activity.isFinishing()) {
			onFinish();
		}
	}
	
	/**
	 * Reanuda los hilos del juego. Llamar en Activity.onResume()
	 */
	public void onResume() {
		glView.onResume();
		gameThread.resume();
	}
	
	/**
	 * Para el hilo del juego. Llamar cuando se termine la aplicacion.<br>
	 * Si se sobreescribe este metodo, se debe llamar a {@code super.onFinish()} para que el hilo del juego se destruya al salir de la
	 * aplicacion, de lo contrario podria quedarse funcionando en segundo plano. Este metodo es llamado por {@link Engine#onPause()} cuando
	 * la Activity se esta cerrando.
	 */
	protected void onFinish() {
		gameThread.terminate();
	}
	
}
