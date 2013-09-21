package com.miviclin.droidengine2d.scene;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.Graphics;
import com.miviclin.droidengine2d.input.KeyController;
import com.miviclin.droidengine2d.input.TouchController;

/**
 * Representa una pantalla del juego (menu principal, nivel 1, nivel 2, pantalla de creditos, etc).
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Scene {
	
	private final Game game;
	private TouchController touchController;
	private KeyController keyController;
	
	/**
	 * Crea una Scene del juego especificado
	 * 
	 * @param game Game al que pertenece la Scene
	 */
	public Scene(Game game) {
		this.game = game;
		this.touchController = new TouchController();
		this.keyController = new KeyController();
	}
	
	/**
	 * Devuelve el juego al que pertenece esta Scene
	 * 
	 * @return Game
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Devuelve el TouchController
	 * 
	 * @return TouchController
	 */
	public TouchController getTouchController() {
		return touchController;
	}
	
	/**
	 * Devuelve el KeyController
	 * 
	 * @return KeyController
	 */
	public KeyController getKeyController() {
		return keyController;
	}
	
	/**
	 * Actualiza los elementos de la Scene.<br>
	 * Este metodo se llama desde {@link SceneManager#update(float)}
	 * 
	 * @param delta Tiempo transcurrido, en milisegundos, desde la ultima actualizacion.
	 */
	public abstract void update(float delta);
	
	/**
	 * Renderiza los elementos de la Scene de forma que puedan verse en pantalla.<br>
	 * Este metodo se llama desde {@link SceneManager#draw(Graphics)} Este metodo se ejecuta en el hilo del GLRenderer tras ejecutar
	 * {@link Scene#update(float)} en el GameThread
	 */
	public abstract void draw(Graphics g);
	
	/**
	 * Llamado cuando esta Scene se registra en el {@link SceneManager}
	 */
	public abstract void onRegister();
	
	/**
	 * Llamado cuando esta Scene pasa a ser la Scene activa mediante el {@link SceneManager}
	 */
	public abstract void onActivation();
	
	/**
	 * Llamado cuando esta Scene deja de ser la Scene activa mediante el {@link SceneManager}
	 */
	public abstract void onDeactivation();
	
	/**
	 * Llamado cuando el juego se va a background desde {@link SceneManager#pause()}
	 */
	public abstract void onPause();
	
	/**
	 * Llamado cuando el juego vuelve de background desde {@link SceneManager#resume()}
	 */
	public abstract void onResume();
	
	/**
	 * Libera los recursos de la Scene.
	 */
	public abstract void dispose();
}
