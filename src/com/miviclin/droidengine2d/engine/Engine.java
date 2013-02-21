package com.miviclin.droidengine2d.engine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;

import com.miviclin.droidengine2d.graphics.GLRenderer;
import com.miviclin.droidengine2d.graphics.GLView;

/**
 * Gestor principal del engine. Maneja el hilo del juego y el hilo del renderer.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Engine {
	
	private GameThread gameThread;
	private GLRenderer renderer;
	private GLView glView;
	private Context context;
	
	/**
	 * Crea un Engine
	 * 
	 * @param context
	 */
	public Engine(Context context) {
		EngineLock engineLock = new EngineLock();
		this.renderer = new GLRenderer(context, engineLock);
		this.gameThread = new GameThread(null, engineLock); // TODO: Implementar Game
		
		this.glView = new GLView(context);
		//if (detectOpenGLES20()) {
			glView.setEGLContextClientVersion(2);
		//}
		// TODO: En caso de que no sea compatible, buscar solucion
		
		this.context = context;
	}
	
	/**
	 * Detecta si el dispositivo soporta OpenGL ES 2.0
	 * 
	 * @return true si lo soporta, false en caso contrario
	 */
	private boolean detectOpenGLES20() {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		return (info.reqGlEsVersion >= 0x20000);
	}
	
	/**
	 * Lanza los hilos del Juego y GLView e inicia el juego
	 */
	public void startGame() {
		gameThread.iniciar();
		glView.setRenderer(renderer);
	}
	
	/**
	 * Pausa los hilos del juego. Llamar en Activity.onPause()
	 */
	public void onPause() {
		glView.onPause();
		gameThread.pausar();
	}
	
	/**
	 * Reanuda los hilos del juego. Llamar en Activity.onResume()
	 */
	public void onResume() {
		glView.onResume();
		gameThread.reanudar();
	}
	
	/**
	 * Para el juego. Llamar cuando se termine la aplicacion.
	 */
	public void onFinish() {
		gameThread.parar();
	}
	
	/**
	 * Devuelve el GLView
	 * 
	 * @return GLView
	 */
	public GLView getGlView() {
		return glView;
	}
	
}
