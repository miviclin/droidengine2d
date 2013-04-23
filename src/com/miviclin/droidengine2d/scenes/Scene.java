package com.miviclin.droidengine2d.scenes;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.sprites.SpriteBatch;

/**
 * Representa una pantalla del juego (menu principal, nivel 1, nivel 2, pantalla de creditos, etc).
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Scene {
	
	/**
	 * Actualiza los elementos de la Scene.<br>
	 * Este metodo se llama desde {@link Game#update(float)}
	 * 
	 * @param delta Tiempo transcurrido, en milisegundos, desde la ultima actualizacion.
	 */
	public abstract void onUpdate(float delta);
	
	/**
	 * Renderiza los elementos de la Scene de forma que puedan verse en pantalla.<br>
	 * Este metodo se ejecuta en el hilo del GLRenderer tras ejecutar {@link Scene#update(float)} en el GameThread
	 */
	public abstract void onDraw(SpriteBatch spriteBatch);
	
	/**
	 * Llamado cuando esta Scene se registra en el {@link SceneManager}
	 */
	public abstract void onRegister();
	
	/**
	 * Llamado cuando esta Scene se hace visible
	 */
	public abstract void onShow();
	
	/**
	 * Llamado cuando el juego se va a background si esta Scene es la Scene activa del juego
	 */
	public abstract void onPause();
	
	/**
	 * Llamado cuando el juego vuelve de background si esta Scene es la Scene activa del juego
	 */
	public abstract void onResume();
	
	/**
	 * Libera los recursos de la Scene. Este metodo no se llama automaticamente
	 */
	public abstract void dispose();
}
