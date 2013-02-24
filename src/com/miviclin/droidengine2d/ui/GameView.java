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
	
	public int getWidth();
	
	public int getHeight();
	
	public void setOnClickListener(OnClickListener onClickListener);
	
	public void setOnLongClickListener(OnLongClickListener onLongClickListener);
	
	public void setOnKeyListener(OnKeyListener onKeyListener);
	
	public void setOnTouchListener(OnTouchListener onTouchListener);
	
}
