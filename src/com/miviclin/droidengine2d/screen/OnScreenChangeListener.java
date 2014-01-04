package com.miviclin.droidengine2d.screen;

/**
 * OnScreenChangeListener.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface OnScreenChangeListener {

	/**
	 * This method is called when the active Screen of the ScreenManager changes. This allows listeners to know when the
	 * Screen has changed.
	 * 
	 * @param previousScreen Previous Screen.
	 * @param currentScreen Current Screen.
	 */
	public void onScreenChange(Screen previousScreen, Screen currentScreen);

}
