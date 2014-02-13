package com.miviclin.droidengine2d;

import android.app.Activity;

import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.graphics.Graphics;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.cameras.OrthographicCamera;
import com.miviclin.droidengine2d.graphics.texture.TextureManager;
import com.miviclin.droidengine2d.input.GameInputManager;
import com.miviclin.droidengine2d.screen.ScreenManager;

/**
 * Game.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class Game {

	private final Activity activity;
	private final TextureManager textureManager;
	private final ScreenManager screenManager;
	private final GameInputManager gameInputManager;

	private volatile boolean initialized;

	private GLView glView;
	private Camera camera;
	private boolean prepared;

	/**
	 * Creates a Game with the specified name.
	 * 
	 * @param activity Activity where the Game runs
	 */
	public Game(Activity activity) {
		this(new GLView(activity), activity);
	}

	/**
	 * Creates a Game with the specified name and GLView.
	 * 
	 * @param glView GLView where the game is rendered
	 * @param activity Activity where the Game runs
	 */
	public Game(GLView glView, Activity activity) {
		this(new GameInputManager(glView), activity);
	}

	/**
	 * Creates a Game with the specified name and GLView.
	 * 
	 * @param gameInputManager GameInputManager. The Game will be redered in the GLView of this GameInputManager.
	 * @param activity Activity where the Game runs
	 */
	public Game(GameInputManager gameInputManager, Activity activity) {
		if (activity == null) {
			throw new IllegalArgumentException("The Activity can not be null");
		}
		this.activity = activity;
		this.textureManager = new TextureManager(activity);
		this.screenManager = new ScreenManager();
		this.screenManager.addOnScreenChangeListener(gameInputManager);
		this.gameInputManager = gameInputManager;
		this.initialized = false;
		this.glView = gameInputManager.getGLView();
		this.camera = new OrthographicCamera();
		this.prepared = false;
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
	 * Returns the width of the view where this Game is rendered.
	 * 
	 * @return Width
	 */
	public float getViewWidth() {
		return glView.getWidth();
	}

	/**
	 * Returns the height of the view where this Game is rendered.
	 * 
	 * @return Height
	 */
	public float getViewHeight() {
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
	 * @param glView GLView.
	 */
	void setGLView(GLView glView) {
		this.glView = glView;
		gameInputManager.setGLView(glView);
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
	 * Returns the ScreenManager.
	 * 
	 * @return ScreenManager
	 */
	public ScreenManager getScreenManager() {
		return screenManager;
	}

	/**
	 * Returns the GameInputManager.
	 * 
	 * @return GameInputManager
	 */
	public GameInputManager getGameInputManager() {
		return gameInputManager;
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
	 * Returns true if the Game object has been initialized and is ready to initialize the Screens. This method will
	 * return true after {@link #prepare()} has been called.
	 * 
	 * @return true if it is safe to initialize the Screens
	 */
	public boolean isPrepared() {
		return prepared;
	}

	/**
	 * This method should be called when the Game object has been initialized and is ready to initialize the Screens.
	 * 
	 * @see Game#isPrepared()
	 */
	public void prepare() {
		this.prepared = true;
	}

	/**
	 * This method is called when the GameThread is paused, usually when {@link Activity#onPause()} is called.
	 */
	public void onEnginePaused() {
		if (initialized) {
			screenManager.pause();
		}
	}

	/**
	 * This method is called when the GameThread is resumed, usually when {@link Activity#onResume()} is called.
	 */
	public void onEngineResumed() {
		if (initialized) {
			screenManager.resume();
		}
	}

	/**
	 * This method is called when the GameThread is destroyed, usually when {@link Activity#onDestroy()} is called.
	 */
	public void onEngineDisposed() {
		if (initialized) {
			screenManager.dispose();
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
			screenManager.update(delta);
		}
		if (!initialized && prepared) {
			initialize();
			initialized = true;
			System.gc();
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
			screenManager.draw(graphics);
		}
	}

	/**
	 * Initializes at least the initial Screen of this Game.
	 */
	public abstract void initialize();

}
