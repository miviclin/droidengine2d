package com.miviclin.droidengine2d.engine;

import android.app.Activity;

import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.ui.GameView;

/**
 * Game.<br>
 * Clase base que representa un juego.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Game {
	
	private Activity activity;
	private GLView glView;
	
	/**
	 * Constructor
	 * 
	 * @param activity Activity en la que se ejecuta el juego
	 */
	public Game(Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("The Activity can not be null");
		}
		this.activity = activity;
		this.glView = new GLView(activity);
	}
	
	/**
	 * Devuelve la Activity en la que se ejecuta el juego
	 * 
	 * @return Activity en la que se ejecuta el juego
	 */
	public Activity getActivity() {
		return activity;
	}
	
	/**
	 * Devuelve el GameView en el que se representa el juego
	 * 
	 * @return GameView en el que se representa el juego
	 */
	public GameView getGameView() {
		return glView;
	}
	
	/**
	 * Devuelve el GLView en el que se representa el juego.<br>
	 * Este metodo se utiliza internamente en el engine para configurar el GLView.
	 * 
	 * @return GLView en el que se representa el juego
	 */
	GLView getGLView() {
		return glView;
	}
	
	/**
	 * Se llama cuando se pausa el GameThread, normalmente debido a que la Activity recibe una llamada a onPause()
	 */
	public abstract void onEnginePaused();
	
	/**
	 * Se llama cuando se reanuda el GameThread tras haber sido pausado, normalmente debido a que la Activity recibe una llamada a
	 * onResume()
	 */
	public abstract void onEngineResumed();
	
	/**
	 * Se llama cuando se para el GameThread, normalmente debido a que la Activity ha sido destruida.
	 */
	public abstract void onEngineDisposed();
	
	/**
	 * Actualiza la logica del juego.<br>
	 * Este metodo es llamado periodicamente por GameThread.
	 * 
	 * @param delta Tiempo transcurrido, en milisegundos, desde la ultima actualizacion.
	 */
	public abstract void update(float delta);
	
	/**
	 * Renderiza los elementos del juego de forma que puedan verse en pantalla.<br>
	 * Este metodo se ejecuta en el hilo del GLRenderer tras ejecutar {@link #update(float)} en el GameThread
	 */
	public abstract void draw(); // TODO: Pasarle un SpriteBatch o algo que se encargue de pintar en pantalla
	
}
