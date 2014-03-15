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
 * Abstract class that implements AnimationStateListener. All methods are empty. This class exists for convinience. It
 * can be used instead of AnimationStateListener in case we don't need to implement all callbacks.
 * 
 * @author Miguel Vicente Linares
 * @see AnimationStateListener
 */
public abstract class AnimationStateAdapter implements AnimationStateListener {

	@Override
	public void onAnimationStarted(Animation animation) {

	}

	@Override
	public void onAnimationPaused(Animation animation) {

	}

	@Override
	public void onAnimationResumed(Animation animation) {

	}

	@Override
	public void onAnimationFrameChanged(Animation animation) {

	}

	@Override
	public void onAnimationLoopFinished(Animation animation) {

	}

	@Override
	public void onAnimationFinished(Animation animation) {

	}

}
