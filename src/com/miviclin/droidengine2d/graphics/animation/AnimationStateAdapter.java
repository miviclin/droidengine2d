package com.miviclin.droidengine2d.graphics.animation;

/**
 * Clase abstracta que implementa AnimationStateListener. Todos los metodos estan vacios. Esta clase existe por
 * conveniencia para crear AnimationStateListeners, para no tener que sobreescribir todos los metodos de
 * AnimationStateListener, solo los que sea necesario.
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
