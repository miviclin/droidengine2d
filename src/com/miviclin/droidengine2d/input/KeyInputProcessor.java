package com.miviclin.droidengine2d.input;

/**
 * Key input processor.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface KeyInputProcessor {

	/**
	 * This method is called from {@link KeyController#processKeyInput()}.<br>
	 * It should not be called manually.
	 * 
	 * @param event KeyEventInfo.
	 */
	public void processKeyEvent(KeyEventInfo event);

}
