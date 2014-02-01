package com.miviclin.droidengine2d.input;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.miviclin.droidengine2d.Engine;
import com.miviclin.droidengine2d.graphics.GLView;
import com.miviclin.droidengine2d.screen.OnScreenChangeListener;
import com.miviclin.droidengine2d.screen.Screen;

public class GameInputManager implements OnScreenChangeListener, OnTouchListener {

	private GLView glView;
	private ScreenInputManager currentScreenInputManager;

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
			currentScreenInputManager.getTouchController().setMotionEvent(event);
			currentScreenInputManager.getTouchController().processTouchInput();
			return true;
		}
		return false;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (currentScreenInputManager != null) {
			currentScreenInputManager.getKeyController().setKeyEvent(keyCode, event);
		}
		return true;
	}

	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (currentScreenInputManager != null) {
			currentScreenInputManager.getKeyController().setKeyEvent(keyCode, event);
		}
		return true;
	}

	/**
	 * This method is called from {@link Engine#onBackPressed()}.<br>
	 * Calls {@link KeyController#onBackPressed()} if the active Screen is not null. Finishes the current Activity
	 * otherwise.
	 * 
	 * @param activity Activity where the Game is running at.
	 */
	public void onBackPressed(Activity activity) {
		if (currentScreenInputManager != null) {
			currentScreenInputManager.getKeyController().onBackPressed();
		} else {
			activity.finish();
		}
	}

	public GLView getGLView() {
		return glView;
	}

	public void setGLView(GLView glView) {
		this.glView = glView;
		glView.setOnTouchListener(this);
	}

}
