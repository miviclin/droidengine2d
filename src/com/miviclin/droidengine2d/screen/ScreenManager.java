package com.miviclin.droidengine2d.screen;

import java.util.ArrayList;

import android.util.SparseArray;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.Graphics;

/**
 * ScreenManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ScreenManager {

	private SparseArray<Screen> screens;
	private Screen activeScreen;
	private ArrayList<OnScreenChangeListener> onScreenChangeListeners;

	/**
	 * Constructor.
	 */
	public ScreenManager() {
		this(16);
	}

	/**
	 * Constructor.
	 * 
	 * @param initialCapacity Initial capacity for Screens. If this capacity is reached, the data structure that holds
	 *            the Screens will be resized automatically.
	 */
	public ScreenManager(int initialCapacity) {
		this.screens = new SparseArray<Screen>(initialCapacity);
		this.activeScreen = null;
		this.onScreenChangeListeners = new ArrayList<OnScreenChangeListener>();
	}

	/**
	 * Registers a Screen in this ScreenManager using the specified screenId.<br>
	 * If a Screen with the specified screenId was previously registered in this ScreenManager, it will be replaced by
	 * the new one.<br>
	 * The active Screen will not change.
	 * 
	 * @param screenId Identifier of the Screen. It can be used to get the Screen from this ScreenManager later.
	 * @param screen Screen (can not be null).
	 */
	public void registerScreen(int screenId, Screen screen) {
		registerScreen(screenId, screen, false);
	}

	/**
	 * Registers a Screen in this ScreenManager using the specified screenId.<br>
	 * If a Screen with the specified screenId was previously registered in this ScreenManager, it will be replaced by
	 * the new one.
	 * 
	 * @param screenId Identifier of the Screen. It can be used to get the Screen from this ScreenManager later.
	 * @param screen Screen (can not be null).
	 * @param activate true to make the Screen the active Screen of this ScreenManager.
	 */
	public void registerScreen(int screenId, Screen screen, boolean activate) {
		if (screen == null) {
			throw new IllegalArgumentException("The Screen can not be null");
		}
		screens.put(screenId, screen);
		screen.onRegister();
		if (activate) {
			setActiveScreen(screenId);
		}
	}

	/**
	 * Unregisters the specified Screen from this ScreenManager.<br>
	 * If a Screen was registered with the specified screenId, {@link Screen#onDispose()} is called on the Screen before
	 * it is removed from this ScreenManager.
	 * 
	 * @param screenId Identifier of the Screen.
	 * @return Removed Screen or null
	 */
	public Screen unregisterScreen(int screenId) {
		Screen screen = screens.get(screenId);
		if (screen == activeScreen) {
			screen.onDeactivation();
			activeScreen = null;
		}
		if (screen != null) {
			screen.dispose();
			screens.remove(screenId);
		}
		return screen;
	}

	/**
	 * Returns the Screen associated with the specified screenId.
	 * 
	 * @param screenId Identifier of the Screen.
	 * @return Screen or null
	 */
	public Screen getScreen(int screenId) {
		return screens.get(screenId);
	}

	/**
	 * Returns the active Screen of this ScreenManager.
	 * 
	 * @return Screen or null
	 */
	public Screen getActiveScreen() {
		return activeScreen;
	}

	/**
	 * Sets the active Screen of this ScreenManager.<br>
	 * The Screen must have been previously registered with the specified screenId.
	 * 
	 * @param screenId Identifier of the Screen we want to set as the active Screen.
	 */
	public void setActiveScreen(int screenId) {
		if (activeScreen != null) {
			activeScreen.onDeactivation();
		}
		Screen screen = screens.get(screenId);
		if (activeScreen != screen) {
			dispatchOnScreenChangeEvent(activeScreen, screen);
		}
		this.activeScreen = screen;
		if (activeScreen != null) {
			activeScreen.onActivation();
		}
	}

	/**
	 * Notifies all listeners that the Screen has changed.
	 * 
	 * @param previousScreen Previous Screen.
	 * @param currentScreen Current Screen.
	 */
	private void dispatchOnScreenChangeEvent(Screen previousScreen, Screen currentScreen) {
		for (int i = 0; i < onScreenChangeListeners.size(); i++) {
			onScreenChangeListeners.get(i).onScreenChange(previousScreen, currentScreen);
		}
	}

	/**
	 * Adds an OnScreenChangeListener that will be notified when the active Screen of this ScreenManager changes.
	 * 
	 * @param listener Listener to be added.
	 */
	public void addOnScreenChangeListener(OnScreenChangeListener listener) {
		onScreenChangeListeners.add(listener);
	}

	/**
	 * Removes an OnScreenChangeListener from the list of listeners. The removed listener will not be notified anymore
	 * when the active Screen of this ScreenManager changes.
	 * 
	 * @param listener Listener to be removed.
	 */
	public void removeOnScreenChangeListener(OnScreenChangeListener listener) {
		onScreenChangeListeners.remove(listener);
	}

	/**
	 * This method is called when the engine is paused, usually when the activity goes to background.<br>
	 * Calls {@link Screen#onPause()} on the active Screen.
	 */
	public void pause() {
		if (activeScreen != null) {
			activeScreen.onPause();
		}
	}

	/**
	 * This method is called when the engine is resumed, usually when the activity comes to foreground.<br>
	 * Calls {@link Screen#onResume()} on the active Screen.
	 */
	public void resume() {
		if (activeScreen != null) {
			activeScreen.onResume();
		}
	}

	/**
	 * Calls {@link Screen#onDispose()} on all Screens registered in this ScreenManager and removes them from the
	 * ScreenManager.<br>
	 * This ScreenManager will be left empty.
	 */
	public void dispose() {
		int numScreens = screens.size();
		for (int i = 0; i < numScreens; i++) {
			Screen screen = screens.valueAt(i);
			if (screen != null) {
				screen.dispose();
			}
		}
		screens.clear();
		activeScreen = null;
	}

	/**
	 * Calls {@link Screen#update(float)} on the active Screen .<br>
	 * This method is called from {@link Game#update(float)}.
	 * 
	 * @param delta Elapsed time, in milliseconds, since the last update.
	 */
	public void update(float delta) {
		if (activeScreen != null) {
			activeScreen.update(delta);
		}
	}

	/**
	 * Calls {@link Screen#draw(Graphics)} on the active Screen.<br>
	 * This method is called from {@link Game#draw(Graphics)}.<br>
	 * This method is called from the redering thread after {@link ScreenManager#update(float)} has been executed in the
	 * game thread.
	 */
	public void draw(Graphics graphics) {
		if (activeScreen != null) {
			activeScreen.draw(graphics);
		}
	}

}
