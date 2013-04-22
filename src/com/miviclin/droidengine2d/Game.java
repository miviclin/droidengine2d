package com.miviclin.droidengine2d;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;

import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.cameras.OrthographicCamera;
import com.miviclin.droidengine2d.graphics.sprites.SpriteBatch;
import com.miviclin.droidengine2d.graphics.textures.TextureManager;

/**
 * Game.<br>
 * Clase base que representa un juego.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Game implements OnClickListener, OnLongClickListener, OnKeyListener, OnTouchListener {
	
	private final String name;
	private final Activity activity;
	private final TextureManager textureManager;
	private GLView glView;
	private Camera camera;
	private boolean onClickListener;
	private boolean onLongClickListener;
	private boolean onKeyListener;
	private boolean onTouchListener;
	
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
		this.camera = new OrthographicCamera();
		this.onClickListener = false;
		this.onLongClickListener = false;
		this.onKeyListener = false;
		this.onTouchListener = false;
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
		boolean onClickListener = this.onClickListener;
		boolean onLongClickListener = this.onLongClickListener;
		boolean onKeyListener = this.onKeyListener;
		boolean onTouchListener = this.onTouchListener;
		if (this.glView != null) {
			disableClickListener();
			disableLongClickListener();
			disableKeyListener();
			disableTouchListener();
		}
		this.glView = glView;
		if (onClickListener) {
			enableClickListener();
		}
		if (onLongClickListener) {
			enableLongClickListener();
		}
		if (onKeyListener) {
			enableKeyListener();
		}
		if (onTouchListener) {
			enableTouchListener();
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
	 * Registra este juego para que sea notificado cuando se produzcan eventos Click sobre la View en el que se desarrolla el juego.
	 */
	public void enableClickListener() {
		glView.setOnClickListener(this);
		onClickListener = true;
	}
	
	/**
	 * Si este juego estaba registrado para ser notificado de los eventos Click que se produjeran en la View en la que se desarrolla el
	 * juego, dejara de estarlo tras llamar a este metodo.
	 */
	public void disableClickListener() {
		glView.setOnClickListener(null);
		onClickListener = false;
	}
	
	/**
	 * Registra este juego para que sea notificado cuando se produzcan eventos LongClick sobre la View en el que se desarrolla el juego.
	 */
	public void enableLongClickListener() {
		glView.setOnLongClickListener(this);
		onLongClickListener = true;
	}
	
	/**
	 * Si este juego estaba registrado para ser notificado de los eventos LongClick que se produjeran en la View en la que se desarrolla el
	 * juego, dejara de estarlo tras llamar a este metodo.
	 */
	public void disableLongClickListener() {
		glView.setOnLongClickListener(null);
		onLongClickListener = false;
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
	 * Se llama cuando en la View en la que se produce un evento Click.<br>
	 * Para que este metodo sea llamado, se debe haber registrado este juego para que reciba eventos Click mediante una llamada a
	 * {@link Game#enableClickListener()}<br>
	 * Por defecto este metodo no realiza ninguna accion. Sobreescribir si es necesario.
	 * 
	 * @param v La View en la que se ha hecho click
	 */
	@Override
	public void onClick(View v) {
		
	}
	
	/**
	 * Se llama cuando en la View en la que se produce un evento LongClick.<br>
	 * Para que este metodo sea llamado, se debe haber registrado este juego para que reciba eventos LongClick mediante una llamada a
	 * {@link Game#enableLongClickListener()}<br>
	 * Por defecto este metodo no realiza ninguna accion. Sobreescribir si es necesario.
	 * 
	 * @param v La View en la que se ha hecho click
	 * @return true si el callback consume el evento, false en caso contrario
	 */
	@Override
	public boolean onLongClick(View v) {
		return false;
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
		return false;
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
		return false;
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
	 * Inicializa el juego
	 */
	public abstract void initialize();
	
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
