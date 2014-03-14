/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.miviclin.droidengine2d.input;

import android.view.KeyEvent;

import com.miviclin.collections.Pool;

/**
 * KeyEventInfo holds information about a KeyEvent.<br>
 * KeyEvents are inmutable, so DroidEngine2D uses KeyEventInfo to create mutable copies of KeyEvents so they can be
 * pooled and reused. KeyEventInfo objects are not mutable from app code.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class KeyEventInfo {

	private static final KeyEventInfoPool keyEventInfoPool = new KeyEventInfoPool();

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
	private KeyEventInfo() {
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
	 * Creates a new KeyEventInfo, copying from an existing KeyEvent.<br>
	 * The KeyEventInfo is obtained from a pool. If the pool is empty, a new KeyEventInfo will be created.<br>
	 * When the returned KeyEventInfo is not needed anymore, {@link KeyEventInfo#recycle()} should be called.
	 * 
	 * @param keyEvent KeyEvent.
	 * @return KeyEventInfo.
	 */
	public static KeyEventInfo obtain(KeyEvent keyEvent) {
		KeyEventInfo keyEventInfo = keyEventInfoPool.obtain();
		keyEventInfo.copyKeyEventInfo(keyEvent);
		return keyEventInfo;
	}

	/**
	 * Copies the information of the specified KeyEvent into this object.
	 * 
	 * @param keyEvent KeyEvent.
	 */
	protected void copyKeyEventInfo(KeyEvent keyEvent) {
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
	 * Recycles the KeyEventInfo, to be re-used by a later caller. After calling this function you must not ever touch
	 * the event again.
	 */
	public void recycle() {
		keyEventInfoPool.recycle(this);
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

	/**
	 * Pool of KeyEventInfo objects.
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	private static class KeyEventInfoPool extends Pool<KeyEventInfo> {

		private static final int DEFAULT_INITIAL_CAPACITY = 60;

		/**
		 * Creates a KeyEventInfoPool containing 60 KeyEventInfo objects.
		 */
		public KeyEventInfoPool() {
			super(DEFAULT_INITIAL_CAPACITY);
			for (int i = 0; i < DEFAULT_INITIAL_CAPACITY; i++) {
				this.recycle(new KeyEventInfo());
			}
		}

		@Override
		public KeyEventInfo createObject() {
			return new KeyEventInfo();
		}

		@Override
		public final void recycle(KeyEventInfo object) {
			super.recycle(object);
		}

	}

}
