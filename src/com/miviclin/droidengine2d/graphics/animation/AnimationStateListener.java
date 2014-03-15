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
package com.miviclin.droidengine2d.graphics.animation;

/**
 * Animation state listener.
 * 
 * @author Miguel Vicente Linares
 * @see Animation
 */
public interface AnimationStateListener {

	/**
	 * This callback is called when the Animation starts.
	 * 
	 * @param animation Animation that calls this method.
	 */
	public void onAnimationStarted(Animation animation);

	/**
	 * This callback is called when the Animation is paused.
	 * 
	 * @param animation Animation that calls this method.
	 */
	public void onAnimationPaused(Animation animation);

	/**
	 * This callback is called when the Animation is resumed.
	 * 
	 * @param animation Animation that calls this method.
	 */
	public void onAnimationResumed(Animation animation);

	/**
	 * This callback is called when the current frame of the Animation changes.
	 * 
	 * @param animation Animation that calls this method.
	 */
	public void onAnimationFrameChanged(Animation animation);

	/**
	 * This callback is called when the Animation loops.
	 * 
	 * @param animation Animation that calls this method.
	 */
	public void onAnimationLoopFinished(Animation animation);

	/**
	 * This callback is called when the Animation finishes.<br>
	 * If the loop mode is enabled, the Animation never finishes.
	 * 
	 * @param animation Animation that calls this method.
	 */
	public void onAnimationFinished(Animation animation);

}
