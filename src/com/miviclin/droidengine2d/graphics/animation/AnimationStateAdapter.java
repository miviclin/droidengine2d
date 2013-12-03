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
