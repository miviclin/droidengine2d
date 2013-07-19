package com.miviclin.droidengine2d;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;

import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.cameras.OrthographicCamera;
import com.miviclin.droidengine2d.graphics.meshes.SpriteBatch;
import com.miviclin.droidengine2d.graphics.textures.TextureManager;
import com.miviclin.droidengine2d.scenes.Scene;
import com.miviclin.droidengine2d.scenes.SceneManager;

/**
 * Game.<br>
 * Clase base que representa un juego.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Game implements OnTouchListener, OnKeyListener {
	
	private final String name;
	private final Activity activity;
	private final TextureManager textureManager;
	private final SceneManager sceneManager;
	private GLView glView;
	private Camera camera;
	private boolean prepared;
	private volatile boolean initialized;
	private boolean onTouchListener;
	private boolean onKeyListener;
	
	/**
	 * Constructor
	 * 
	 * @param name Nombre del juego
	 * @param activity Activity en la que se ejecuta el juego
	 */
	public Game(String name, Activity activity) {
		this(name, new GLView(activity), activity);
	}
	
	/**
	 * Constructor
	 * 
	 * @param name Nombre del juego
	 * @param glView GLView en la que se representara el juego
	 * @param activity Activity en la que se ejecuta el juego
	 */
	public Game(String name, GLView glView, Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("The Activity can not be null");
		}
		this.name = name;
		this.activity = activity;
		this.glView = glView;
		this.textureManager = new TextureManager(activity);
		this.sceneManager = new SceneManager();
		this.camera = new OrthographicCamera();
		this.prepared = false;
		this.initialized = false;
		this.onTouchListener = false;
		this.onKeyListener = false;
	}
	
	/**
	 * Devuelve el nombre del juego
	 * 
	 * @return
	 */
	public String getName() {
		return name;
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
	 * Asigna un GLView para representar el juego. Translada los listeners del GLView antiguo al nuevo.<br>
	 * Este metodo se utiliza internamente en el engine para configurar el GLView.
	 * 
	 * @param nuevo GLView
	 */
	void setGLView(GLView glView) {
		boolean onTouchListener = this.onTouchListener;
		boolean onKeyListener = this.onKeyListener;
		if (this.glView != null) {
			disableTouchListener();
			disableKeyListener();
		}
		this.glView = glView;
		if (onTouchListener) {
			enableTouchListener();
		}
		if (onKeyListener) {
			enableKeyListener();
		}
	}
	
	/**
	 * Devuelve el TextureManager.
	 * 
	 * @return TextureManager
	 */
	public TextureManager getTextureManager() {
		return textureManager;
	}
	
	/**
	 * Devuelve el SceneManager.
	 * 
	 * @return SceneManager
	 */
	public SceneManager getSceneManager() {
		return sceneManager;
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
	 * Indica si el core del juego ha sido inicializado y esta preparado para inicializar las Scenes
	 * 
	 * @return true si se puede comenzar a inicializar los elementos del juego (Scenes, jugador, enemigos, etc)
	 */
	public boolean isPrepared() {
		return prepared;
	}
	
	/**
	 * Indica que el core del juego ha sido inicializado y esta preparado para inicializar las Scenes
	 */
	public void prepare() {
		this.prepared = true;
	}
	
	/**
	 * Registra este juego para que sea notificado cuando se produzcan eventos Touch sobre la View en el que se desarrolla el juego.
	 */
	public void enableTouchListener() {
		glView.setOnTouchListener(this);
		onTouchListener = true;
	}
	
	/**
	 * Si este juego estaba registrado para ser notificado de los eventos Touch que se produjeran en la View en la que se desarrolla el
	 * juego, dejara de estarlo tras llamar a este metodo.
	 */
	public void disableTouchListener() {
		glView.setOnTouchListener(null);
		onTouchListener = false;
	}
	
	/**
	 * Registra este juego para que sea notificado cuando se produzcan eventos Key sobre la View en el que se desarrolla el juego.
	 */
	public void enableKeyListener() {
		glView.setOnKeyListener(this);
		onKeyListener = true;
	}
	
	/**
	 * Si este juego estaba registrado para ser notificado de los eventos Key que se produjeran en la View en la que se desarrolla el juego,
	 * dejara de estarlo tras llamar a este metodo.
	 */
	public void disableKeyListener() {
		glView.setOnKeyListener(null);
		onKeyListener = false;
	}
	
	/**
	 * Se llama cuando en la View en la que se produce un evento Touch.<br>
	 * Para que este metodo sea llamado, se debe haber registrado este juego para que reciba eventos Key mediante una llamada a
	 * {@link Game#enableTouchListener()}<br>
	 * Por defecto este metodo no realiza ninguna accion. Sobreescribir si es necesario.
	 * 
	 * @param v La View en la que se ha hecho click
	 * @param event MotionEvent que contiene la informacion del evento
	 * @return true si el listener consume el evento, false en caso contrario
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Scene activeScene = getSceneManager().getActiveScene();
		if (activeScene != null) {
			activeScene.getTouchController().setMotionEvent(event);
		}
		return true;
	}
	
	/**
	 * Se llama cuando en la View en la que se produce un evento Key.<br>
	 * Para que este metodo sea llamado, se debe haber registrado este juego para que reciba eventos Key mediante una llamada a
	 * {@link Game#enableKeyListener()}<br>
	 * Por defecto este metodo no realiza ninguna accion. Sobreescribir si es necesario.
	 * 
	 * @param v La View en la que se ha hecho click
	 * @param keyCode Codigo que identifica la tecla fisica pulsada
	 * @param event KeyEvent que contiene la informacion del evento
	 * @return true si el listener consume el evento, false en caso contrario
	 */
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		Scene activeScene = getSceneManager().getActiveScene();
		if (activeScene != null) {
			activeScene.getKeyController().setKeyEvent(keyCode, event);
		}
		return true;
	}
	
	/**
	 * Llamado por {@link Engine#onBackPressed()}<br>
	 * Destruye la Activity en la que se ejecuta el juego.<br>
	 * Sobreescribir este metodo para implementar acciones que deban ejecutarse al pulsar el boton BACK del dispositivo
	 */
	public void onBackPressed() {
		getActivity().finish();
	}
	
	/**
	 * Se llama cuando se pausa el GameThread, normalmente debido a que la Activity recibe una llamada a onPause()
	 */
	public void onEnginePaused() {
		if (initialized) {
			sceneManager.pause();
		}
	}
	
	/**
	 * Se llama cuando se reanuda el GameThread tras haber sido pausado, normalmente debido a que la Activity recibe una llamada a
	 * onResume()
	 */
	public void onEngineResumed() {
		if (initialized) {
			sceneManager.resume();
		}
	}
	
	/**
	 * Se llama cuando se para el GameThread, normalmente debido a que la Activity ha sido destruida.
	 */
	public void onEngineDisposed() {
		if (initialized) {
			sceneManager.dispose();
		}
	}
	
	/**
	 * Actualiza la logica del juego.<br>
	 * Este metodo es llamado periodicamente por GameThread.
	 * 
	 * @param delta Tiempo transcurrido, en milisegundos, desde la ultima actualizacion.
	 */
	public void update(float delta) {
		if (initialized) {
			sceneManager.update(delta);
		}
		if (!initialized && prepared) {
			initialize();
			initialized = true;
		}
	}
	
	/**
	 * Renderiza los elementos del juego de forma que puedan verse en pantalla.<br>
	 * Este metodo se ejecuta en el hilo del GLRenderer tras ejecutar {@link #update(float)} en el GameThread
	 */
	public void draw(SpriteBatch spriteBatch) {
		if (initialized) {
			sceneManager.draw(spriteBatch);
		}
	}
	
	/**
	 * Inicializa el juego
	 */
	public abstract void initialize();
	
}
