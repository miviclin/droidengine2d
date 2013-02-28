package com.miviclin.droidengine2d.engine;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.cameras.OrthographicCamera;
import com.miviclin.droidengine2d.graphics.sprites.SpriteBatch;
import com.miviclin.droidengine2d.graphics.textures.GLTextureManager;

/**
 * Game.<br>
 * Clase base que representa un juego.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Game {
	
	private final Activity activity;
	private final GLView glView;
	private final GLTextureManager textureManager;
	private Camera camera;
	
	/**
	 * Constructor
	 * 
	 * @param activity Activity en la que se ejecuta el juego
	 */
	public Game(Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("The Activity can not be null");
		}
		this.activity = activity;
		this.glView = new GLView(activity);
		this.textureManager = new GLTextureManager(activity);
		this.camera = new OrthographicCamera();
	}
	
	/**
	 * Devuelve la Activity en la que se ejecuta el juego
	 * 
	 * @return Activity en la que se ejecuta el juego
	 */
	public Activity getActivity() {
		return activity;
	}
	
	/**
	 * Devuelve el ancho del View en el que se representa el juego
	 * 
	 * @return ancho del View en el que se representa el juego
	 */
	public int getGameViewWidth() {
		return glView.getWidth();
	}
	
	/**
	 * Devuelve el alto del View en el que se representa el juego
	 * 
	 * @return alto del View en el que se representa el juego
	 */
	public int getGameViewHeight() {
		return glView.getHeight();
	}
	
	/**
	 * Devuelve el GLView en el que se representa el juego.<br>
	 * Este metodo se utiliza internamente en el engine para configurar el GLView.
	 * 
	 * @return GLView en el que se representa el juego
	 */
	GLView getGLView() {
		return glView;
	}
	
	/**
	 * Devuelve el GLTextureManager.
	 * 
	 * @return GLTextureManager
	 */
	public GLTextureManager getTextureManager() {
		return textureManager;
	}
	
	/**
	 * Devuelve la camara.
	 * 
	 * @return Camera
	 */
	public Camera getCamera() {
		return camera;
	}
	
	/**
	 * Asigna una nueva camara al juego.
	 * 
	 * @param camera Nueva camara. No puede ser null
	 */
	public void setCamera(Camera camera) {
		if (camera == null) {
			throw new IllegalArgumentException("The camera can not be null");
		}
		this.camera = camera;
	}
	
	/**
	 * Registra un listener que permitira recibir eventos Click sobre el View en el que se desarrolla el juego.
	 * 
	 * @param listener OnClickListener, o null para eliminar un listener existente
	 */
	public void setClickListener(OnClickListener listener) {
		glView.setOnClickListener(listener);
	}
	
	/**
	 * Registra un listener que permitira recibir eventos LongClick sobre el View en el que se desarrolla el juego.
	 * 
	 * @param listener OnLongClickListener, o null para eliminar un listener existente
	 */
	public void setLongClickListener(OnLongClickListener listener) {
		glView.setOnLongClickListener(listener);
	}
	
	/**
	 * Registra un listener que permitira recibir eventos de pulsacion de tecla sobre el View en el que se desarrolla el juego.
	 * 
	 * @param listener OnKeyListener, o null para eliminar un listener existente
	 */
	public void setKeyListener(OnKeyListener listener) {
		glView.setOnKeyListener(listener);
	}
	
	/**
	 * Registra un listener que permitira recibir eventos Touch sobre el View en el que se desarrolla el juego.
	 * 
	 * @param listener OnTouchListener, o null para eliminar un listener existente
	 */
	public void setTouchListener(OnTouchListener listener) {
		glView.setOnTouchListener(listener);
	}
	
	/**
	 * Se llama cuando se pausa el GameThread, normalmente debido a que la Activity recibe una llamada a onPause()
	 */
	public abstract void onEnginePaused();
	
	/**
	 * Se llama cuando se reanuda el GameThread tras haber sido pausado, normalmente debido a que la Activity recibe una llamada a
	 * onResume()
	 */
	public abstract void onEngineResumed();
	
	/**
	 * Se llama cuando se para el GameThread, normalmente debido a que la Activity ha sido destruida.
	 */
	public abstract void onEngineDisposed();
	
	/**
	 * Actualiza la logica del juego.<br>
	 * Este metodo es llamado periodicamente por GameThread.
	 * 
	 * @param delta Tiempo transcurrido, en milisegundos, desde la ultima actualizacion.
	 */
	public abstract void update(float delta);
	
	/**
	 * Renderiza los elementos del juego de forma que puedan verse en pantalla.<br>
	 * Este metodo se ejecuta en el hilo del GLRenderer tras ejecutar {@link #update(float)} en el GameThread
	 */
	public abstract void draw(SpriteBatch spriteBatch);
	
}
