package com.miviclin.droidengine2d.engine;

import android.util.Log;

import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.util.MutexLock;

/**
 * GameThread
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GameThread implements Runnable { // TODO: Traducir

	private enum State {
		INITIALIZING,
		RUNNING,
		PAUSED,
		TERMINATED;
	}
	
	private final int maxSkippedFrames;
	private final float idealTimePerFrame; // tiempo ideal para procesar 1 frame (ms)
	
	private State currentState;
	private Game game;
	private final EngineLock engineLock;
	
	private GLView glView;
	
	private MutexLock sPausaHilo; // TODO: quitar si sobra
	private boolean glInicializado; // TODO: quitar si sobra
	
	/**
	 * Constructor. Crea un nuevo HiloJuego. Por defecto, el maximo de FPS al que funcionara el juego es 30 y el numero de frames que puede
	 * actualizar sin renderizar es 5.
	 * 
	 * @param maxFPS Maximos FPS a los que va a actualizarse el juego. En caso de que el dispositivo no sea capaz de mantener los FPS
	 *            especificados, el juego podria experimentar ralentizacionas.
	 * @param game Juego que va a gestionar este hilo
	 */
	public GameThread(Game game, GLView glView, EngineLock engineLock) {
		this(30, 5, game, engineLock);
		this.glView = glView;
	}
	
	/**
	 * Constructor. Crea un nuevo HiloJuego. Por defecto, el numero de frames que puede actualizar sin renderizar es 5.
	 * 
	 * @param maxFPS Maximos FPS a los que va a actualizarse el juego. En caso de que el dispositivo no sea capaz de mantener los FPS
	 *            especificados, el juego podria experimentar ralentizacionas.
	 * @param game Juego que va a gestionar este hilo
	 */
	public GameThread(int maxFPS, Game game, EngineLock engineLock) {
		this(maxFPS, 5, game, engineLock);
	}
	
	/**
	 * Constructor
	 * 
	 * @param maxFPS Maximos FPS a los que va a actualizarse el juego
	 * @param maxSkippedFrames Maximos frames seguidos que se puede saltar sin renderizar en caso de que una vuelta del bucle principal del
	 *            juego tarde mas de lo estipulado.
	 * @param game Juego que va a gestionar este hilo
	 */
	public GameThread(int maxFPS, int maxSkippedFrames, Game game, EngineLock engineLock) {
		if (game == null) {
			throw new IllegalArgumentException("The Game can not be null");
		}
		if (maxFPS <= 0) {
			throw new IllegalArgumentException("maxFPS must be greater than 0");
		}
		this.currentState = State.INITIALIZING;
		this.maxSkippedFrames = maxSkippedFrames;
		this.idealTimePerFrame = 1000 / maxFPS;
		this.game = game;
		this.engineLock = engineLock;
		
		
		this.sPausaHilo = new MutexLock();
		this.glInicializado = false;
	}
	
	@Override
	public void run() {
		long startingTime;
		long frameTime;
		long waitingTime;
		int skippedFrames;
		
		if (currentState == State.INITIALIZING) {
			Log.d("WaitNotify", "wait iniGL");
			// sInicializacionGL.doWait();
			glInicializado = true;
			currentState = State.RUNNING;
		}
		while (currentState != State.TERMINATED) {
			if (currentState == State.PAUSED) {
				game.onEnginePaused();
				Log.d("WaitNotify", "wait pausa");
				sPausaHilo.lock();
			}
			if (currentState == State.RUNNING) {
				startingTime = System.currentTimeMillis();
				skippedFrames = 0;
				// juego.actualizar((float) tIdealFrame);
				Log.d("WaitNotify", "wait render");
				if (engineLock.allowUpdate.get()) {
					synchronized (engineLock.lock) {
						game.update((float) idealTimePerFrame);
						engineLock.allowUpdate.set(false);
						glView.requestRender();
					}
				}
				frameTime = System.currentTimeMillis() - startingTime;
				waitingTime = (long) (idealTimePerFrame - frameTime);
				
				if (waitingTime > 0) {
					/*try {
						Thread.sleep(waitingTime);
					} catch (InterruptedException e) {
					}*/
					sleep(waitingTime, 0.333f); // TODO: Provisional, hacer pruebas.
				}
				while ((waitingTime < 0) && (skippedFrames < maxSkippedFrames) && (currentState == State.RUNNING)) {
					game.update((float) idealTimePerFrame);
					waitingTime += idealTimePerFrame;
					skippedFrames++;
				}
				Log.d("FramesSaltados", skippedFrames + "");
				Log.d("TiempoFrame", (System.currentTimeMillis() - startingTime) + "ms");
			}
		}
		game.onEngineDisposed();
		Log.d("Finish", "hilo terminado");
	}
	
	private void sleep(long sleepTimeMillis, float sleepTimePercentage) {
		long diff;
		long prev = System.nanoTime();
		long sleep = sleepTimeMillis * 1000000;
		long limit = (long) (sleep * sleepTimePercentage);
		while ((diff = System.nanoTime() - prev) < sleep) {
			if (diff < limit) {
				try {
					Thread.sleep(1);
				} catch (Exception exc) {
				}
			}
			else {
				Thread.yield();
			}
		}
	}
	
	/**
	 * Inicia el hilo
	 */
	public void start() {
		Thread hilo = new Thread(this);
		hilo.setDaemon(true);
		hilo.start();
	}
	
	/**
	 * Para el hilo del juego. Llamar solo cuando no se vaya a utilizar nunca mas, por ejemplo al salir de la aplicacion.
	 */
	public void terminate() {
		currentState = State.TERMINATED;
		Log.d("WaitNotify", "notify iniGL");
		// sInicializacionGL.doNotify();
		Log.d("WaitNotify", "notify pausa");
		sPausaHilo.unlock();
		Log.d("WaitNotify", "notify render");
		engineLock.allowUpdate.set(true);
	}
	
	/**
	 * Pausa el hilo del juego
	 */
	public void pause() {
		currentState = State.PAUSED;
		Log.d("WaitNotify", "notify render pausar");
		engineLock.allowUpdate.set(true);
	}
	
	/**
	 * Reanuda el hilo del juego
	 */
	public void resume() {
		// Este metodo se llama en el onResume() de la Activity, que se llama al crear la Activity por primera
		// vez y luego cada vez que vuelve de background. Por eso hay que diferenciar si se ha llamado antes de
		// que se complete la inicializacion, o se ha llamado porque la Activity vuelve de background.
		State estado = currentState;
		if (!glInicializado) {
			currentState = State.INITIALIZING;
		} else {
			currentState = State.RUNNING;
		}
		if (estado == State.PAUSED) {
			game.onEngineResumed();
			Log.d("WaitNotify", "wait render - reset");
			engineLock.allowUpdate.set(true);
			Log.d("WaitNotify", "notify pausa");
			sPausaHilo.unlock();
		}
	}
	
}
