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
package com.miviclin.droidengine2d.util;

/**
 * MutexLock acts as a binary semaphore.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class MutexLock {

	private Object lock;
	private boolean notified;

	/**
	 * Constructor.
	 */
	public MutexLock() {
		this.lock = new Object();
		this.notified = false;
	}

	/**
	 * Locks the lock.
	 */
	public void lock() {
		synchronized (lock) {
			if (!notified) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
				}
			}
			notified = false;
		}
	}

	/**
	 * Unlocks the lock.
	 */
	public void unlock() {
		synchronized (lock) {
			notified = true;
			lock.notify();
		}
	}

}
