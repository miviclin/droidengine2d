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
