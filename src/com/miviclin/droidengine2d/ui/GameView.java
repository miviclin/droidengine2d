package com.miviclin.droidengine2d.ui;

/**
 * Interfaz que define los metodos de una View a los que debe tener acceso el juego.<br>
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface GameView {
	
	/**
	 * Devuelve el ancho del GameView
	 * 
	 * @return ancho
	 */
	public int getWidth();
	
	/**
	 * Devuelve el alto del GameView
	 * 
	 * @return alto
	 */
	public int getHeight();
	
}
