package com.miviclin.droidengine2d.input;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.miviclin.droidengine2d.EngineActivity;
import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.screen.OnScreenChangeListener;
import com.miviclin.droidengine2d.screen.Screen;

/**
 * Manages game input delegating event handling to the current {@link ScreenInputManager}.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GameInputManager implements OnScreenChangeListener, OnTouchListener {

	private GLView glView;
	private ScreenInputManager currentScreenInputManager;

	/**
	 * Creates a new GameInputManager.
	 * 
	 * @param glView GLView.
	 */
	public GameInputManager(GLView glView) {
		this.glView = glView;
		this.currentScreenInputManager = null;
		this.glView.setOnTouchListener(this);
	}

	@Override
	public void onScreenChange(Screen previousScreen, Screen currentScreen) {
		this.currentScreenInputManager = currentScreen.getInputManager();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (currentScreenInputManager != null) {
			currentScreenInputManager.getTouchProcessor().queueCopyOfMotionEvent(event);
		}
		return true;
	}

	/**
	 * This method is called from {@link EngineActivity#onKeyDown(int, KeyEvent)}.<br>
	 * Queues a copy of the KeyEvent in the {@link KeyProcessor} of the current {@link ScreenInputManager} for later
	 * processing.
	 * 
	 * @param keyCode Key code.
	 * @param event KeyEvent.
	 * @return Always returns true.
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (currentScreenInputManager != null) {
			currentScreenInputManager.getKeyProcessor().queueCopyOfKeyEvent(event);
		}
		return true;
	}

	/**
	 * This method is called from {@link EngineActivity#onKeyUp(int, KeyEvent)}.<br>
	 * Queues a copy of the KeyEvent in the {@link KeyProcessor} of the current {@link ScreenInputManager} for later
	 * processing.
	 * 
	 * @param keyCode Key code.
	 * @param event KeyEvent.
	 * @return Always returns true.
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (currentScreenInputManager != null) {
			currentScreenInputManager.getKeyProcessor().queueCopyOfKeyEvent(event);
		}
		return true;
	}

	/**
	 * Returns the GLView of this GameInputManager.
	 * 
	 * @return GLView
	 */
	public GLView getGLView() {
		return glView;
	}

	/**
	 * Sets the GLView of this GameInputManager.
	 * 
	 * @param glView GLView.
	 */
	public void setGLView(GLView glView) {
		this.glView = glView;
		glView.setOnTouchListener(this);
	}

}
