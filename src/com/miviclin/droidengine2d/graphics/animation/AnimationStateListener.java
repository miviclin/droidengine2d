package com.miviclin.droidengine2d.graphics.animation;

/**
 * Interfaz que define algunos metodos que se llaman cuando el estado de la Animation cambia
 * 
 * @author Miguel Vicente Linares
 * @see Animation
 */
public interface AnimationStateListener {

	/**
	 * Callback que se llama cuando empieza la animacion
	 * 
	 * @param animation Animation que llama a este metodo
	 */
	public void onAnimationStarted(Animation animation);

	/**
	 * Callback que se llama cuando se pausa la animacion
	 * 
	 * @param animation Animation que llama a este metodo
	 */
	public void onAnimationPaused(Animation animation);

	/**
	 * Callback que se llama cuando se reanuda la la animacion tras haber sido pausada previamente
	 * 
	 * @param animation Animation que llama a este metodo
	 */
	public void onAnimationResumed(Animation animation);

	/**
	 * Callback que se llama cada vez que la Animation pasa de un AnimationFrame a otro
	 * 
	 * @param animation Animation que llama a este metodo
	 */
	public void onAnimationFrameChanged(Animation animation);

	/**
	 * Callback que se llama cada vez que la Animation termina un loop
	 * 
	 * @param animation Animation que llama a este metodo
	 */
	public void onAnimationLoopFinished(Animation animation);

	/**
	 * Callback que se llama cuando la Animation termina (solo se llamara en caso de que la Animation tenga el modo loop
	 * desactivado)
	 * 
	 * @param animation Animation que llama a este metodo
	 */
	public void onAnimationFinished(Animation animation);

}
