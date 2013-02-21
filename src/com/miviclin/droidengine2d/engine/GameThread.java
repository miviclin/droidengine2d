package com.miviclin.droidengine2d.engine;

import android.util.Log;

import com.miviclin.droidengine2d.util.MutexLock;

/**
 * GameThread
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GameThread implements Runnable { // TODO: Traducir
	
	private enum Estado {
		INICIALIZANDO,
		CORRIENDO,
		PAUSADO,
		TERMINADO;
	}
	
	private Estado estadoActual;
	
	private final int maxFramesSaltados;
	private final float tIdealFrame; // tiempo ideal para procesar 1 frame (ms)
	
	private Game juego;
	// private DobleBufferRenderizado bufferRenderizado; // TODO: reimplementar el buffer
	private final EngineLock engineLock;
	private MutexLock sPausaHilo;
	private boolean glInicializado;
	
	/**
	 * Constructor. Crea un nuevo HiloJuego. Por defecto, el maximo de FPS al que funcionara el juego es 30 y el numero de frames que puede
	 * actualizar sin renderizar es 5.
	 * 
	 * @param maxFPS Maximos FPS a los que va a actualizarse el juego. En caso de que el dispositivo no sea capaz de mantener los FPS
	 *            especificados, el juego podria experimentar ralentizacionas.
	 * @param juego Juego que va a gestionar este hilo
	 */
	public GameThread(Game juego, EngineLock engineLock) {
		this(30, 5, juego, engineLock);
	}
	
	/**
	 * Constructor. Crea un nuevo HiloJuego. Por defecto, el numero de frames que puede actualizar sin renderizar es 5.
	 * 
	 * @param maxFPS Maximos FPS a los que va a actualizarse el juego. En caso de que el dispositivo no sea capaz de mantener los FPS
	 *            especificados, el juego podria experimentar ralentizacionas.
	 * @param juego Juego que va a gestionar este hilo
	 */
	public GameThread(int maxFPS, Game juego, EngineLock engineLock) {
		this(maxFPS, 5, juego, engineLock);
	}
	
	/**
	 * Constructor
	 * 
	 * @param maxFPS Maximos FPS a los que va a actualizarse el juego
	 * @param maxFramesSaltados Maximos frames seguidos que se puede saltar sin renderizar en caso de que una vuelta del bucle principal del
	 *            juego tarde mas de lo estipulado.
	 * @param juego Juego que va a gestionar este hilo
	 */
	public GameThread(int maxFPS, int maxFramesSaltados, Game juego, EngineLock engineLock) {
		if (juego == null) {
			//throw new RuntimeException("El Juego no puede ser null"); // TODO: Reactivar cuando se implemente el juego
		}
		if (maxFPS <= 0) {
			throw new RuntimeException("maxFPS debe ser mayor que 0");
		}
		this.estadoActual = Estado.INICIALIZANDO;
		this.maxFramesSaltados = maxFramesSaltados;
		this.tIdealFrame = 1000 / maxFPS;
		this.juego = juego;
		// this.bufferRenderizado = juego.getGLView().getBufferRenderizado();
		this.engineLock = engineLock;
		this.sPausaHilo = new MutexLock();
		
		this.glInicializado = false;
	}
	
	@Override
	public void run() {
		long tInicio;
		long tFrame;
		long tEspera;
		int framesSaltados;
		
		if (estadoActual == Estado.INICIALIZANDO) {
			Log.d("WaitNotify", "wait iniGL");
			// sInicializacionGL.doWait();
			glInicializado = true;
			estadoActual = Estado.CORRIENDO;
		}
		while (estadoActual != Estado.TERMINADO) {
			if (estadoActual == Estado.PAUSADO) {
				juego.onEnginePaused();
				Log.d("WaitNotify", "wait pausa");
				sPausaHilo.lock();
			}
			if (estadoActual == Estado.CORRIENDO) {
				tInicio = System.currentTimeMillis();
				framesSaltados = 0;
				// juego.actualizar((float) tIdealFrame);
				Log.d("WaitNotify", "wait render");
				engineLock.waitUntilCanUpdate();
				// Hacemos esta doble comprobacion para evitar enviar los elementos actualizados en caso de que
				// se haya pausado el hilo antes de que el hilo del GLRenderer haya terminado de pintar.
				if (estadoActual == Estado.CORRIENDO) {
					// bufferRenderizado.intercambiarBuffers();
					Log.d("WaitNotify", "notify actualizacion");
					engineLock.notifyCanRender();
				}
				tFrame = System.currentTimeMillis() - tInicio;
				tEspera = (long) (tIdealFrame - tFrame);
				
				if (tEspera > 0) {
					try {
						Thread.sleep(tEspera);
					} catch (InterruptedException e) {
					}
				}
				while ((tEspera < 0) && (framesSaltados < maxFramesSaltados) && (estadoActual == Estado.CORRIENDO)) {
					// juego.actualizar((float) tIdealFrame);
					tEspera += tIdealFrame;
					framesSaltados++;
				}
				Log.d("FramesSaltados", framesSaltados + "");
				Log.d("TiempoFrame", (System.currentTimeMillis() - tInicio) + "ms");
			}
		}
		juego.onEngineDisposed();
		// bufferRenderizado.vaciar();
		Log.d("Finish", "hilo terminado");
	}
	
	/**
	 * Inicia el hilo
	 */
	public void iniciar() {
		Thread hilo = new Thread(this);
		hilo.setDaemon(true);
		hilo.start();
	}
	
	/**
	 * Para el hilo del juego. Llamar solo cuando no se vaya a utilizar nunca mas, por ejemplo al salir de la aplicacion.
	 */
	public void parar() {
		estadoActual = Estado.TERMINADO;
		Log.d("WaitNotify", "notify iniGL");
		// sInicializacionGL.doNotify();
		Log.d("WaitNotify", "notify pausa");
		sPausaHilo.lock();
		Log.d("WaitNotify", "notify render");
		engineLock.notifyCanRender();
	}
	
	/**
	 * Pausa el hilo del juego
	 */
	public void pausar() {
		estadoActual = Estado.PAUSADO;
		Log.d("WaitNotify", "notify render pausar");
		engineLock.notifyCanRender();
	}
	
	/**
	 * Reanuda el hilo del juego
	 */
	public void reanudar() {
		// Este metodo se llama en el onResume() de la Activity, que se llama al crear la Activity por primera
		// vez y luego cada vez que vuelve de background. Por eso hay que diferenciar si se ha llamado antes de
		// que se complete la inicializacion, o se ha llamado porque la Activity vuelve de background.
		Estado estado = estadoActual;
		if (!glInicializado) {
			estadoActual = Estado.INICIALIZANDO;
		} else {
			estadoActual = Estado.CORRIENDO;
		}
		if (estado == Estado.PAUSADO) {
			juego.onEngineResumed();
			// Reiniciamos el semaforo sRenderizado por precaucion
			Log.d("WaitNotify", "notify render - reset");
			engineLock.notifyCanRender();
			Log.d("WaitNotify", "wait render - reset");
			engineLock.waitUntilCanRender();
			Log.d("WaitNotify", "notify pausa");
			sPausaHilo.lock();
		}
	}
	
}
