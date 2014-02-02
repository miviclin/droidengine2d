package com.miviclin.droidengine2d.input;

import android.view.KeyEvent;

/**
 * KeyEventInfo holds information about a KeyEvent.<br>
 * KeyEvents are inmutable, so DroidEngine2D uses KeyEventInfo to create mutable copies of KeyEvents so they can be
 * pooled and reused. KeyEventInfo objects are not mutable from app code.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class KeyEventInfo {

	private int deviceId;
	private int source;
	private int metaState;
	private int action;
	private int keyCode;
	private int scanCode;
	private int repeatCount;
	private int flags;
	private long downTime;
	private long eventTime;
	private String characters;

	/**
	 * Creates an uninitialized KeyEventInfo.<br>
	 * {@link KeyEventInfo#copyKeyEventInfo(KeyEvent)} should be called after creating a KeyEventInfo with this
	 * constructor.
	 */
	KeyEventInfo() {
	}

	/**
	 * Creates a new KeyEventInfo with the information about the specified KeyEvent.
	 * 
	 * @param keyEvent KeyEvent.
	 */
	public KeyEventInfo(KeyEvent keyEvent) {
		this.downTime = keyEvent.getDownTime();
		this.eventTime = keyEvent.getEventTime();
		this.action = keyEvent.getAction();
		this.keyCode = keyEvent.getKeyCode();
		this.repeatCount = keyEvent.getRepeatCount();
		this.metaState = keyEvent.getMetaState();
		this.deviceId = keyEvent.getDeviceId();
		this.scanCode = keyEvent.getScanCode();
		this.flags = keyEvent.getFlags();
		this.source = keyEvent.getSource();
		this.characters = keyEvent.getCharacters();
	}

	/**
	 * Copies the information of the specified KeyEvent into this object.
	 * 
	 * @param keyEvent KeyEvent.
	 */
	void copyKeyEventInfo(KeyEvent keyEvent) {
		this.downTime = keyEvent.getDownTime();
		this.eventTime = keyEvent.getEventTime();
		this.action = keyEvent.getAction();
		this.keyCode = keyEvent.getKeyCode();
		this.repeatCount = keyEvent.getRepeatCount();
		this.metaState = keyEvent.getMetaState();
		this.deviceId = keyEvent.getDeviceId();
		this.scanCode = keyEvent.getScanCode();
		this.flags = keyEvent.getFlags();
		this.source = keyEvent.getSource();
		this.characters = keyEvent.getCharacters();
	}

	/**
	 * See: {@link KeyEvent#getDeviceId()}
	 */
	public int getDeviceId() {
		return deviceId;
	}

	/**
	 * See: {@link KeyEvent#getSource()}
	 */
	public int getSource() {
		return source;
	}

	/**
	 * See: {@link KeyEvent#getMetaState()}
	 */
	public int getMetaState() {
		return metaState;
	}

	/**
	 * See: {@link KeyEvent#getAction()}
	 */
	public int getAction() {
		return action;
	}

	/**
	 * See: {@link KeyEvent#getKeyCode()}
	 */
	public int getKeyCode() {
		return keyCode;
	}

	/**
	 * See: {@link KeyEvent#getScanCode()}
	 */
	public int getScanCode() {
		return scanCode;
	}

	/**
	 * See: {@link KeyEvent#getRepeatCount()}
	 */
	public int getRepeatCount() {
		return repeatCount;
	}

	/**
	 * See: {@link KeyEvent#getFlags()}
	 */
	public int getFlags() {
		return flags;
	}

	/**
	 * See: {@link KeyEvent#getDownTime()}
	 */
	public long getDownTime() {
		return downTime;
	}

	/**
	 * See: {@link KeyEvent#getEventTime()}
	 */
	public long getEventTime() {
		return eventTime;
	}

	/**
	 * See: {@link KeyEvent#getCharacters()}
	 */
	public String getCharacters() {
		return characters;
	}

}
