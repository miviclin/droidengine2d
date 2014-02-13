package com.miviclin.droidengine2d;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.miviclin.droidengine2d.gamestate.GameState;
import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.input.GameStateInputManager;

/**
 * EngineActivity manages the life cycle of the Engine.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class EngineActivity extends FragmentActivity {

	private Engine engine;
	private boolean prepared;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setWindowFlags();

		setContentView(getContentViewId());
		final GLView glView = (GLView) findViewById(getGLViewId());

		engine = createEngine(glView);

		final Game game = engine.getGame();
		engine.startGame();

		// GLView.getWidth() and GLView.getHeight() return 0 before the view is rendered on screen for the first
		// time,
		// so we have to wait until the view is rendered for the first time before initializing the Engine
		glView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				if (getResources().getConfiguration().orientation != getOrientation()) {
					return;
				}
				// Ensure that game initialization is run only once
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					glView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				} else {
					glView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
				if (!prepared) {
					prepared = true;
					game.prepare();
				}
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		if (engine != null) {
			engine.onPause();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (engine != null) {
			engine.onResume();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (engine != null) {
			engine.onDestroy();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (engine != null) {
			return engine.getGame().getGameInputManager().onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (engine != null) {
			return engine.getGame().getGameInputManager().onKeyUp(keyCode, event);
		}
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * The default implementation of this method does nothing.<br>
	 * The back button presses are handled by the {@link GameStateInputManager} of the current {@link GameState}.
	 */
	@Override
	public void onBackPressed() {
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (engine != null) {
			onConfigurationChanged();
		}
	}

	/**
	 * This method is called from {@link #onConfigurationChanged(Configuration)} if the Engine has been initialized.
	 */
	public void onConfigurationChanged() {
		setContentView(getContentViewId());
		engine.setGLView((GLView) findViewById(getGLViewId()));
	}

	/**
	 * Sets Window flags.<br>
	 * This method is called from {@link EngineActivity#onCreate(Bundle)}
	 */
	public void setWindowFlags() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/**
	 * Returns the Engine.
	 * 
	 * @return Engine
	 */
	protected Engine getEngine() {
		return engine;
	}

	/**
	 * Creates an Engine and returns it.<br>
	 * The Engine object returned by this method is the Engine that will be managed by this Activity.<br>
	 * This method is called from {@link EngineActivity#onCreate(Bundle)}
	 * 
	 * @param glView GLView used to render the game
	 * @return Engine
	 */
	public abstract Engine createEngine(GLView glView);

	/**
	 * Returns the content view ID.<br>
	 * This method must return the ID of the layout used for this activity.
	 * 
	 * @return Content View ID
	 */
	public abstract int getContentViewId();

	/**
	 * Returns the ID of the GLView (defined in the layout xml file) where the game will be rendered.
	 * 
	 * @return GLView ID
	 */
	public abstract int getGLViewId();

	/**
	 * Returns the orientation of the activity.<br>
	 * This method must return the same orientation defined in AndroidManifest.xml for this activity.
	 * 
	 * @return {@link Configuration#ORIENTATION_LANDSCAPE} or {@link Configuration#ORIENTATION_PORTRAIT}
	 */
	public abstract int getOrientation();
}
