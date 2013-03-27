package com.miviclin.droidengine2d;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.Display;
import android.view.WindowManager;

import com.miviclin.droidengine2d.graphics.DefaultRenderer;
import com.miviclin.droidengine2d.graphics.EngineRenderer;
import com.miviclin.droidengine2d.graphics.GLRenderer;
import com.miviclin.droidengine2d.graphics.GLView;

/**
 * Gestor principal del engine. Maneja el hilo del juego y el hilo del renderer.<br>
 * <br>
 * ATENCION: Para que el Engine actue de acuerdo al ciclo de vide de la Activity en la que se utiliza, es necesario llamar a
 * {@link Engine#onPause()}, {@link Engine#onResume()} y {@link Engine#onDestroy()} en los metodos correspondientes de la Activity.<br>
 * Tambien se puede interceptar el boton back llamando a {@link Engine#onBackPressed()} desde {@link Activity#onBackPressed()}<br>
 * AndroidEngine utiliza OpenGL ES 2.0, por tanto, es recomendable comprobar primero si el dispositivo soporta OpenGL ES 2.0. Ejemplo:
 * 
 * <pre>
 * {@code Engine engine;
 * if (ActivityUtilities.detectOpenGLES20(activity)) {
 *     engine = new Engine(...);
 * } else {
 *     // Indicar al usuario que su dispositivo no soporta OpenGL ES 2.0 y cerrar la app
 * }
 * </pre>
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Engine {
	
	private Game game;
	private GameThread gameThread;
	private GLRenderer renderer;
	private GLView glView;
	
	/**
	 * Crea un Engine.<br>
	 * LEER: {@link Engine}
	 * 
	 * @param game Juego
	 * @param renderer EngineRenderer
	 * @throws IllegalArgumentException Si el juego es null
	 */
	private Engine(EngineBuilder engineBuilder) {
		if (engineBuilder == null) {
			throw new IllegalArgumentException("The EngineBuilder can not be null");
		}
		this.game = engineBuilder.game;
		this.glView = engineBuilder.glView;
		this.gameThread = engineBuilder.gameThread;
		this.renderer = engineBuilder.glRenderer;
	}
	
	/**
	 * Devuelve el GLView
	 * 
	 * @return GLView
	 */
	public GLView getGLView() {
		return glView;
	}
	
	/**
	 * Lanza los hilos del Juego y GLView e inicia el juego
	 */
	public void startGame() {
		gameThread.start();
		glView.setRenderer(renderer);
		glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}
	
	/**
	 * Pausa los hilos del juego. Llamar en {@link Activity.onPause()}<br>
	 * Si se ha llamado a onPause() porque la Activity se esta cerrando, este metodo llama a {@link Engine#onFinish()}
	 */
	public void onPause() {
		glView.onPause();
		gameThread.pause();
	}
	
	/**
	 * Reanuda los hilos del juego. Llamar en {@link Activity.onResume()}
	 */
	public void onResume() {
		glView.onResume();
		gameThread.resume();
	}
	
	/**
	 * Destruye el hilo del juego y libera recursos. Llamar en {@link Activity#onDestroy()}
	 */
	public void onDestroy() {
		gameThread.terminate();
	}
	
	/**
	 * Llamar desde {@link Activity#onBackPressed()} para que el juego pueda interceptar las pulsaciones del boton BACK del dispositivo y
	 * pueda realizar acciones antes de que la activity sea destruida.
	 */
	public void onBackPressed() {
		game.onBackPressed();
	}
	
	/**
	 * Builder que se utiliza para construir un {@link Engine}.
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	public final static class EngineBuilder {
		
		private final Game game;
		private final GLView glView;
		private EngineRenderer renderer;
		private GLRenderer glRenderer;
		private GameThread gameThread;
		private int maxFPS;
		private int maxSkippedFrames;
		
		/**
		 * Crea un EngineBuilder.<br>
		 * LEER: {@link Engine}
		 * 
		 * @param game Juego
		 * @throws IllegalArgumentException Si el juego es null
		 */
		public EngineBuilder(Game game) {
			if (game == null) {
				throw new IllegalArgumentException("The Game can not be null");
			}
			this.game = game;
			this.glView = game.getGLView();
			this.glView.setEGLContextClientVersion(2);
			this.renderer = new DefaultRenderer(game);
			this.glRenderer = null;
			this.gameThread = null;
			this.maxFPS = 30;
			this.maxSkippedFrames = 5;
		}
		
		/**
		 * Asigna el renderer que utilizara el Engine.<br>
		 * El renderer por defecto es {@link DefaultRenderer}
		 * 
		 * @param renderer Renderer que se utilizara
		 * @return El propio EngineBuilder, para poder encadenar llamadas a metodos
		 */
		public EngineBuilder setRenderer(EngineRenderer renderer) {
			if (renderer == null) {
				throw new IllegalArgumentException("The Renderer can not be null");
			}
			this.renderer = renderer;
			return this;
		}
		
		/**
		 * Asigna el maximo numero de FPS al que se actualizara y se repintara el juego.<br>
		 * La frecuencia de actualizacion del juego no superara la frecuencia de refresco de la pantalla, por tanto, si el valor
		 * especificado es mayor que la frecuencia de refresco de la pantalla, la frecuencia de actualizacion del juego coincidira con la
		 * frecuencia de refresco de la pantalla, independientemente del valor especificado.<br>
		 * Si se asigna un valor alto es posible que el rendimiento decrezca en los dispositivos menos potentes.<br>
		 * El valor por defecto es 30.
		 * 
		 * @param maxFPS Nuevo valor
		 * @return El propio EngineBuilder, para poder encadenar llamadas a metodos
		 */
		public EngineBuilder setMaxFPS(int maxFPS) {
			Display display = ((WindowManager) game.getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			int refreshRate = (int) display.getRefreshRate();
			this.maxFPS = (maxFPS > refreshRate) ? refreshRate : maxFPS;
			return this;
		}
		
		/**
		 * Asigna el maximo numero de frames seguidos que se puede actualizar sin renderizar en caso de que una vuelta del bucle principal
		 * del juego tarde mas de lo estipulado.
		 * 
		 * @param maxSkippedFrames Nuevo valor
		 * @return El propio EngineBuilder, para poder encadenar llamadas a metodos
		 */
		public EngineBuilder setMaxSkippedFrames(int maxSkippedFrames) {
			this.maxSkippedFrames = maxSkippedFrames;
			return this;
		}
		
		/**
		 * Construye un {@link Engine} a partir de la configuracion del EngineBuilder
		 * 
		 * @return Engine
		 */
		public Engine build() {
			EngineLock engineLock = new EngineLock();
			this.glRenderer = new GLRenderer(renderer, engineLock);
			this.gameThread = new GameThread(maxFPS, maxSkippedFrames, game, glView, engineLock);
			return new Engine(this);
		}
		
	}
	
}
