package com.miviclin.droidengine2d;

import android.view.KeyEvent;

import com.miviclin.droidengine2d.input.KeyListener;
import com.miviclin.droidengine2d.scenes.Scene;

/**
 * Controlador de eventos Key
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class KeyController {
	
	private volatile boolean keyDetected;
	private volatile int keyCode;
	private volatile KeyEvent keyEvent;
	private KeyListener keyListener;
	
	/**
	 * Constructor
	 */
	public KeyController() {
		this.keyDetected = false;
		this.keyCode = -1;
		this.keyEvent = null;
		this.keyListener = null;
	}
	
	/**
	 * Asigna un KeyEvent al KeyController. Este KeyEvent sera el que se le pase a {@link KeyListener#onKey(int, KeyEvent)}.
	 * 
	 * @param keyCode Codigo de tecla
	 * @param keyEvent KeyEvent
	 */
	void setKeyEvent(int keyCode, KeyEvent keyEvent) {
		this.keyCode = keyCode;
		this.keyEvent = keyEvent;
		if (keyEvent != null) {
			keyDetected = true;
		}
	}
	
	/**
	 * Asigna un {@link KeyListener} al KeyController
	 * 
	 * @param keyListener KeyListener
	 */
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}
	
	/**
	 * Procesa el evento de tecla.<br>
	 * Este metodo deberia ser llamado desde {@link Scene#update(float)} para que en caso de que haya un evento de tecla, ejecute
	 * {@link KeyListener#onKey(int, KeyEvent)}
	 */
	public void processKeyInput() {
		if ((keyListener != null) && keyDetected) {
			keyListener.onKey(keyCode, keyEvent);
		}
		keyDetected = false;
	}
	
}