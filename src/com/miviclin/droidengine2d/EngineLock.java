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
package com.miviclin.droidengine2d;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * EngineLock is used to synchronize the game thread and the rendering thread.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class EngineLock {

	private final AtomicBoolean allowUpdateFlag;
	private final Object lock;

	/**
	 * Creates an EngineLock.
	 */
	public EngineLock() {
		this.allowUpdateFlag = new AtomicBoolean();
		this.lock = new Object();
	}

	/**
	 * Returns the allow update flag.<br>
	 * If this flag is set, the game can be updated.
	 * 
	 * @return The allow update flag
	 */
	public AtomicBoolean getAllowUpdateFlag() {
		return allowUpdateFlag;
	}

	/**
	 * Returns the object used to lock the GameThread while the rendering thread is working and vice-versa.
	 * 
	 * @return Lock object
	 */
	public Object getLock() {
		return lock;
	}

}
