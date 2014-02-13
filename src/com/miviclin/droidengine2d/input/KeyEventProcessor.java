package com.miviclin.droidengine2d.input;

/**
 * Processes key events.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface KeyEventProcessor {

	/**
	 * This method is called from {@link KeyProcessor#processKeyInput()}.<br>
	 * It should not be called manually.
	 * 
	 * @param event KeyEventInfo.
	 */
	public void processKeyEvent(KeyEventInfo event);

}
