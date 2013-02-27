package com.miviclin.droidengine2d.ui;

import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

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
	
	/**
	 * Asigna un OnClickListener al GameView
	 * 
	 * @param onClickListener OnClickListener a asignar. Si es null, se eliminara el OnClickListener que hubiera.
	 */
	public void setOnClickListener(OnClickListener onClickListener);
	
	/**
	 * Asigna un OnLongClickListener al GameView
	 * 
	 * @param onLongClickListener OnLongClickListener a asignar. Si es null, se eliminara el OnClickListener que hubiera.
	 */
	public void setOnLongClickListener(OnLongClickListener onLongClickListener);
	
	/**
	 * Asigna un OnKeyListener al GameView
	 * 
	 * @param onKeyListener OnKeyListener a asignar. Si es null, se eliminara el OnClickListener que hubiera.
	 */
	public void setOnKeyListener(OnKeyListener onKeyListener);
	
	/**
	 * Asigna un OnTouchListener al GameView
	 * 
	 * @param onTouchListener OnTouchListener a asignar. Si es null, se eliminara el OnClickListener que hubiera.
	 */
	public void setOnTouchListener(OnTouchListener onTouchListener);
	
}
