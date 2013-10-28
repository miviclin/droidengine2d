package com.miviclin.droidengine2d;

import android.util.Log;

import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.util.MutexLock;

/**
 * GameThread
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GameThread implements Runnable {

	private enum State {
		INITIALIZING,
		RUNNING,
		PAUSED,
		TERMINATED;
	}

	private final int maxSkippedFrames;
	private final float idealTimePerFrame;
	private final Game game;
	private final EngineLock engineLock;
	private final MutexLock pauseLock;
	private final MutexLock terminateLock;
	private GLView glView;

	private State currentState;
	private boolean started;

	/**
	 * Constructor. Crea un nuevo HiloJuego. Por defecto, el maximo de FPS al que funcionara el juego es 30 y el numero
	 * de frames que puede actualizar sin renderizar es 5.
	 * 
	 * @param game Juego que va a gestionar este hilo
	 * @param glView GLView en la que se representa el juego
	 * @param engineLock Utilizado para sincronizar correctamente los hilos
	 */
	public GameThread(Game game, GLView glView, EngineLock engineLock) {
		this(30, 5, game, glView, engineLock);

	}

	/**
	 * Constructor. Crea un nuevo HiloJuego. Por defecto, el numero de frames que puede actualizar sin renderizar es 5.
	 * 
	 * @param maxFPS Maximos FPS a los que va a actualizarse el juego. En caso de que el dispositivo no sea capaz de
	 *            mantener los FPS especificados, el juego podria experimentar ralentizacionas.
	 * @param game Juego que va a gestionar este hilo
	 * @param glView GLView en la que se representa el juego
	 * @param engineLock Utilizado para sincronizar correctamente los hilos
	 */
	public GameThread(int maxFPS, Game game, GLView glView, EngineLock engineLock) {
		this(maxFPS, 5, game, glView, engineLock);
	}

	/**
	 * Constructor
	 * 
	 * @param maxFPS Maximos FPS a los que va a actualizarse el juego
	 * @param maxSkippedFrames Maximos frames seguidos que se puede saltar sin renderizar en caso de que una vuelta del
	 *            bucle principal del juego tarde mas de lo estipulado.
	 * @param game Juego que va a gestionar este hilo
	 * @param glView GLView en la que se representa el juego
	 * @param engineLock Utilizado para sincronizar correctamente los hilos
	 */
	public GameThread(int maxFPS, int maxSkippedFrames, Game game, GLView glView, EngineLock engineLock) {
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
		this.glView = glView;
		this.engineLock = engineLock;
		this.pauseLock = new MutexLock();
		this.terminateLock = new MutexLock();
		this.started = false;
	}

	@Override
	public void run() {
		long startingTime;
		long frameTime;
		long waitingTime;
		int skippedFrames;

		while (currentState != State.TERMINATED) {
			if (currentState == State.PAUSED) {
				game.onEnginePaused();
				pauseLock.lock();
			}
			if (currentState == State.RUNNING) {
				startingTime = System.currentTimeMillis();
				skippedFrames = 0;
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
					sleep(waitingTime, 0.333f);
				}
				while ((waitingTime < 0) && (skippedFrames < maxSkippedFrames) && (currentState == State.RUNNING)) {
					game.update((float) idealTimePerFrame);
					waitingTime += idealTimePerFrame;
					skippedFrames++;
				}
				if (BuildConfig.DEBUG) {
					if (skippedFrames > 0) {
						Log.d("SkippedFrames", skippedFrames + "");
					}
				}
			}
		}
		game.onEngineDisposed();
		terminateLock.unlock();
	}

	/**
	 * Tiene un comportamiento similar a {@code Thread#sleep(long)}. Pone el hilo en espera durante el tiempo
	 * especificado.<br>
	 * Para llevar esto a cabo, se hace uso de {@code Thread#sleep(long)} hasta que se sobrepase el porcentaje de tiempo
	 * especificado, a partir de ahi, se llama a {@code Thread#yield()} hasta completar el tiempo total.<br>
	 * Este metodo pretende ser mas preciso que {@code Thread#sleep(long)}. Cuanto mayor sea el porcentaje especificado,
	 * mas CPU consumira la espera (si el porcentaje es 0, la espera no consume CPU), sin embargo,
	 * {@code Thread#sleep(long)} puede ser bastante impreciso, por lo que puede ser conveniente cargar un poco mas la
	 * CPU si se necesita mas precision en el tiempo de espera.<br>
	 * Especificando como porcentaje 0.333f parece ser lo suficientemente preciso y no carga la CPU al 100%
	 * 
	 * @param sleepTimeMillis Tiempo total de espera
	 * @param sleepTimePercentage Porcentaje del tiempo total que se hara uso de {@code Thread#sleep(long)} (Valor entre
	 *            0 y 1)
	 * @see Thread#sleep(long)
	 * @see Thread#yield()
	 */
	private void sleep(long sleepTimeMillis, float sleepTimePercentage) {
		long diff;
		long prev = System.nanoTime();
		long sleep = sleepTimeMillis * 1000000;
		long limit = (long) (sleep * sleepTimePercentage);
		while ((diff = System.nanoTime() - prev) < sleep) {
			if (diff < limit) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					Log.w(GameThread.class.getSimpleName(), e.getMessage());
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
		Thread thread;
		if (started) {
			throw new IllegalThreadStateException("Thread already started.");
		}
		thread = new Thread(this);
		thread.setDaemon(true);
		started = true;
		currentState = State.RUNNING;
		thread.start();
	}

	/**
	 * Para el hilo del juego. Llamar solo cuando no se vaya a utilizar nunca mas, por ejemplo al salir de la
	 * aplicacion.
	 */
	public void terminate() {
		currentState = State.TERMINATED;
		pauseLock.unlock();
		engineLock.allowUpdate.set(true);
		terminateLock.lock();
	}

	/**
	 * Pausa el hilo del juego
	 */
	public void pause() {
		currentState = State.PAUSED;
		engineLock.allowUpdate.set(true);
	}

	/**
	 * Reanuda el hilo del juego
	 */
	public void resume() {
		if (currentState == State.PAUSED) {
			currentState = State.RUNNING;
			game.onEngineResumed();
			engineLock.allowUpdate.set(true);
			pauseLock.unlock();
		}
	}

	/**
	 * Asigna un GLView para representar el juego. Translada los listeners del GLView antiguo al nuevo.<br>
	 * Este metodo se utiliza internamente en el engine para configurar el GLView.
	 * 
	 * @param nuevo GLView
	 */
	void setGLView(GLView glView) {
		this.glView = glView;
	}

}
