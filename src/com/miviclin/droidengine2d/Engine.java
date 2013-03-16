package com.miviclin.droidengine2d;

import android.app.Activity;
import android.opengl.GLSurfaceView;

import com.miviclin.droidengine2d.graphics.EngineRenderer;
import com.miviclin.droidengine2d.graphics.GLRenderer;
import com.miviclin.droidengine2d.graphics.GLView;

/**
 * Gestor principal del engine. Maneja el hilo del juego y el hilo del renderer.<br>
 * <br>
 * ATENCION: Para que el Engine actue de acuerdo al ciclo de vide de la Activity en la que se utiliza, es necesario llamar a
 * {@link Engine#onPause()}, {@link Engine#onResume()} y {@link Engine#onDestroy()} en los metodos correspondientes de la Activity.<br>
 * Tambien se puede interceptar el boton back llamando a {@link Engine#onBackPressed()} desde {@link Activity#onBackPressed()}<br>
 * AndroidEngine utiliza OpenGL ES 2.0, por tanto, es recomendable comprobar primero si el dispositivo soporta OpenGL ES 2.0. Ejemplo:
 * 
 * <pre>
 * {@code Engine engine;
 * if (ActivityUtilities.detectOpenGLES20(activity)) {
 *     engine = new Engine(...);
 * } else {
 *     // Indicar al usuario que su dispositivo no soporta OpenGL ES 2.0 y cerrar la app
 * }
 * </pre>
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Engine {
	
	private Game game;
	private GameThread gameThread;
	private GLRenderer renderer;
	private GLView glView;
	private Activity activity;
	private boolean destroyed;
	
	/**
	 * Crea un Engine.<br>
	 * LEER: {@link Engine}
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
		this.game = game;
		this.glView = game.getGLView();
		this.glView.setEGLContextClientVersion(2);
		this.gameThread = new GameThread(game, glView, engineLock);
		this.renderer = new GLRenderer(renderer, engineLock);
		this.activity = game.getActivity();
		this.destroyed = false;
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
		destroyed = false;
	}
	
	/**
	 * Pausa los hilos del juego. Llamar en {@link Activity.onPause()}<br>
	 * Si se ha llamado a onPause() porque la Activity se esta cerrando, este metodo llama a {@link Engine#onFinish()}
	 */
	public void onPause() {
		glView.onPause();
		gameThread.pause();
		if (activity.isFinishing() && !destroyed) {
			onDestroy();
			destroyed = true;
		}
	}
	
	/**
	 * Reanuda los hilos del juego. Llamar en {@link Activity.onResume()}
	 */
	public void onResume() {
		glView.onResume();
		gameThread.resume();
	}
	
	/**
	 * Destruye el hilo del juego y libera recursos. Llamar en {@link Activity#onDestroy()}
	 */
	public void onDestroy() {
		if (!destroyed) {
			gameThread.terminate();
			destroyed = true;
		}
	}
	
	/**
	 * Llamar desde {@link Activity#onBackPressed()} para que el juego pueda interceptar las pulsaciones del boton BACK del dispositivo y
	 * pueda realizar acciones antes de que la activity sea destruida.
	 */
	public void onBackPressed() {
		game.onBackPressed();
	}
	
}
