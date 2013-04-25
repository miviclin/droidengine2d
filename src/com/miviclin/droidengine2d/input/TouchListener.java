package com.miviclin.droidengine2d.input;

import android.view.MotionEvent;

/**
 * Interfaz que se utiliza para los eventos Touch
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface TouchListener {
	
	/**
	 * Metodo que contiene la logica que debe procesarse al recibir un evento Touch.<br>
	 * Se llama cuando se hace una llamada a {@link TouchController#processTouch()}.<br>
	 * Este metodo no deberia se llamado de forma manual, sino a traves de {@link TouchController#processTouch()}
	 * 
	 * @param motionEvent MotionEvent que contiene toda la informacion
	 */
	public void onTouch(MotionEvent motionEvent);
	
}
