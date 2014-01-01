package com.miviclin.droidengine2d;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;

import com.miviclin.droidengine2d.gamestate.GameState;
import com.miviclin.droidengine2d.gamestate.GameStateManager;
import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.graphics.Graphics;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.cameras.OrthographicCamera;
import com.miviclin.droidengine2d.graphics.texture.TextureManager;

/**
 * Game.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Game implements OnTouchListener, OnKeyListener {

	private final String name;
	private final Activity activity;
	private final TextureManager textureManager;
	private final GameStateManager gameStateManager;
	private GLView glView;
	private Camera camera;
	private boolean prepared;
	private volatile boolean initialized;
	private boolean onTouchListener;
	private boolean onKeyListener;

	/**
	 * Creates a Game with the specified name.
	 * 
	 * @param name Name of the Game
	 * @param activity Activity where the Game runs
	 */
	public Game(String name, Activity activity) {
		this(name, new GLView(activity), activity);
	}

	/**
	 * Creates a Game with the specified name and GLView.
	 * 
	 * @param name Name of the Game
	 * @param glView GLView where the game is rendered
	 * @param activity Activity where the Game runs
	 */
	public Game(String name, GLView glView, Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("The Activity can not be null");
		}
		this.name = name;
		this.activity = activity;
		this.glView = glView;
		this.textureManager = new TextureManager(activity);
		this.gameStateManager = new GameStateManager();
		this.camera = new OrthographicCamera();
		this.prepared = false;
		this.initialized = false;
		this.onTouchListener = false;
		this.onKeyListener = false;
	}

	/**
	 * Returns the name of the Game.
	 * 
	 * @return name of the Game
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the Activity where the game runs.
	 * 
	 * @return Activity where the game runs
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Returns the width of the View where the game is rendered.
	 * 
	 * @return width of the View where the game is rendered
	 */
	public int getGameViewWidth() {
		return glView.getWidth();
	}

	/**
	 * Returns the height of the View where the game is rendered.
	 * 
	 * @return height of the View where the game is rendered
	 */
	public int getGameViewHeight() {
		return glView.getHeight();
	}

	/**
	 * Returns the GLView where the game is rendered.<br>
	 * This method is only visible to the famework because it is only needed to set up the GLView.
	 * 
	 * @return GLView where the game is rendered
	 */
	GLView getGLView() {
		return glView;
	}

	/**
	 * Sets a new GLView. All listeners in the old GLView will be linked to the new one.<br>
	 * This method is only visible to the famework because it is only needed to set up the GLView.
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
	 * Returns the TextureManager.
	 * 
	 * @return TextureManager
	 */
	public TextureManager getTextureManager() {
		return textureManager;
	}

	/**
	 * Returns the GameStateManager.
	 * 
	 * @return GameStateManager
	 */
	public GameStateManager getGameStateManager() {
		return gameStateManager;
	}

	/**
	 * Returns the Camera.
	 * 
	 * @return Camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Sets a new Camera to the Game.
	 * 
	 * @param camera Camera. Can not be null.
	 */
	public void setCamera(Camera camera) {
		if (camera == null) {
			throw new IllegalArgumentException("The camera can not be null");
		}
		this.camera = camera;
	}

	/**
	 * Returns true if the Game object has been initialized and is ready to initialize the GameStates. This method will
	 * return true after {@link #prepare()} has been called.
	 * 
	 * @return true if it is safe to initialize the GameStates
	 */
	public boolean isPrepared() {
		return prepared;
	}

	/**
	 * This method should be called when the Game object has been initialized and is ready to initialize the GameStates.
	 * 
	 * @see Game#isPrepared()
	 */
	public void prepare() {
		this.prepared = true;
	}

	/**
	 * Registers a touch listener.<br>
	 * This Game will listen touch events dispatched to its associated GLView.
	 */
	public void enableTouchListener() {
		glView.setOnTouchListener(this);
		onTouchListener = true;
	}

	/**
	 * If this Game has an enabled touch listener, it will be disabled.<br>
	 * This Game will no longer listen touch events.
	 */
	public void disableTouchListener() {
		glView.setOnTouchListener(null);
		onTouchListener = false;
	}

	/**
	 * Registers a key listener.<br>
	 * This Game will listen key events dispatched to its associated GLView.
	 */
	public void enableKeyListener() {
		glView.setOnKeyListener(this);
		onKeyListener = true;
	}

	/**
	 * If this Game has an enabled key listener, it will be disabled.<br>
	 * This Game will no longer listen key events.
	 */
	public void disableKeyListener() {
		glView.setOnKeyListener(null);
		onKeyListener = false;
	}

	/**
	 * This method is called when a touch event is dispatched to the GLView if the touch listener is enabled.<br>
	 * In order to enable the touch listener, {@link Game#enableTouchListener()} should be called.<br>
	 * Sets the current GameState's touch controller MotionEvent.
	 * 
	 * @param v View.
	 * @param event MotionEvent.
	 * @return true by default (the Game will consume all events).
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		GameState activeGameState = getGameStateManager().getActiveGameState();
		if (activeGameState != null) {
			activeGameState.getTouchController().setMotionEvent(event);
		}
		return true;
	}

	/**
	 * This method is called when a key event is dispatched to the GLView if the key listener is enabled.<br>
	 * In order to enable the touch listener, {@link Game#enableKeyListener()} should be called.<br>
	 * Sets the current GameState's key controller MotionEvent.
	 * 
	 * @param v View.
	 * @param keyCode KeyCode.
	 * @param event KeyEvent.
	 * @return true by default (the Game will consume all events).
	 */
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		GameState activeGameState = getGameStateManager().getActiveGameState();
		if (activeGameState != null) {
			activeGameState.getKeyController().setKeyEvent(keyCode, event);
		}
		return true;
	}

	/**
	 * This method is called from {@link Engine#onBackPressed()}.<br>
	 * Destroys the Activity where this Game is running.
	 */
	public void onBackPressed() {
		getActivity().finish();
	}

	/**
	 * This method is called when the GameThread is paused, usually when {@link Activity#onPause()} is called.
	 */
	public void onEnginePaused() {
		if (initialized) {
			gameStateManager.pause();
		}
	}

	/**
	 * This method is called when the GameThread is resumed, usually when {@link Activity#onResume()} is called.
	 */
	public void onEngineResumed() {
		if (initialized) {
			gameStateManager.resume();
		}
	}

	/**
	 * This method is called when the GameThread is destroyed, usually when {@link Activity#onDestroy()} is called.
	 */
	public void onEngineDisposed() {
		if (initialized) {
			gameStateManager.dispose();
		}
	}

	/**
	 * Updates the game logic.<br>
	 * This method is called from the game thread.
	 * 
	 * @param delta Elapsed time, in milliseconds, since the last update.
	 */
	public void update(float delta) {
		if (initialized) {
			gameStateManager.update(delta);
		}
		if (!initialized && prepared) {
			initialize();
			initialized = true;
		}
	}

	/**
	 * Renders the game objects.<br>
	 * This method is called from the redering thread after {@link #update(float)} has been executed in the game thread.
	 * 
	 * @param graphics Graphics.
	 */
	public void draw(Graphics graphics) {
		if (initialized) {
			gameStateManager.draw(graphics);
		}
	}

	/**
	 * Initializes the Game objects.
	 */
	public abstract void initialize();

}
