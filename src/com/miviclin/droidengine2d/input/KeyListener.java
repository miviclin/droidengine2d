package com.miviclin.droidengine2d.input;

import com.miviclin.droidengine2d.KeyController;
import com.miviclin.droidengine2d.TouchController;

import android.view.KeyEvent;

/**
 * Interfaz que se utiliza para los eventos Key
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface KeyListener {
	
	/**
	 * Metodo que contiene la logica que debe procesarse al recibir un evento Key.<br>
	 * Se llama cuando se hace una llamada a {@link KeyController#processKeyInput()}.<br>
	 * Este metodo no deberia se llamado de forma manual, sino a traves de {@link TouchController#processKeyInput()}
	 * 
	 * @param keyCode Codigo que identifica la tecla fisica pulsada
	 * @param event KeyEvent que contiene la informacion del evento
	 */
	public void onKey(int keyCode, KeyEvent event);
	
}