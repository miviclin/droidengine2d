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

import android.view.MotionEvent;

/**
 * Processes motion events.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface MotionEventProcessor {

	/**
	 * This method is called from {@link TouchProcessor#processTouchInput()}.<br>
	 * It should not be called manually.
	 * 
	 * @param motionEvent MotionEvent.
	 */
	public void processMotionEvent(MotionEvent motionEvent);

}
