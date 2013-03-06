package com.miviclin.droidengine2d.graphics.animation;

import java.util.ArrayList;

/**
 * Animacion
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Animation {
	
	private enum State {
		RUNNING,
		PAUSED,
		FINISHED;
	}
	
	private ArrayList<AnimationFrame> frames;
	private int currentFrameIndex;
	private float elapsedTime;
	private boolean repetitionEnabled;
	private State state;
	
	/**
	 * Crea una animacion con capacidad inicial de 10 frames y con repeticion activada, de forma que cuando se complete el primer ciclo,
	 * volvera a repetirse todo el ciclo de la animacion.
	 */
	public Animation() {
		this(10, true);
	}
	
	/**
	 * Crea una animacion con capaidad inicial para 10 frames.
	 * 
	 * @param repeat Indica si se repetira el ciclo de la animacion o no
	 */
	public Animation(boolean repeat) {
		this(10, repeat);
	}
	
	/**
	 * Crea una animacion
	 * 
	 * @param initialCapacity Numero minimo de frames que se pueden agregar antes de que se redimensione la estructura de datos
	 * @param repeat Indica si se repetira el ciclo de la animacion o no
	 */
	public Animation(int initialCapacity, boolean repeat) {
		this.frames = new ArrayList<AnimationFrame>(initialCapacity);
		this.currentFrameIndex = 0;
		this.elapsedTime = 0;
		this.repetitionEnabled = repeat;
		this.state = State.RUNNING;
	}
	
	/**
	 * Agrega un frame a la animacion
	 * 
	 * @param frame Nuevo frame, se agregara al final de la lista de frames
	 */
	public void addFrame(AnimationFrame frame) {
		frames.add(frame);
	}
	
	/**
	 * Devuelve el frame actual
	 * 
	 * @return AnimationFrame o null si la lista de frames esta vacia
	 */
	public AnimationFrame getCurrentFrame() {
		if (frames.size() > 0) {
			return frames.get(currentFrameIndex);
		}
		return null;
	}
	
	/**
	 * Actualiza la animacion y devuelve el frame actual
	 * 
	 * @param delta Tiempo transcurrido desde la ultima actualizacion
	 * @return AnimationFrame o null si la lista de frames esta vacia
	 */
	public AnimationFrame getCurrentFrame(float delta) {
		if ((state == State.RUNNING) && (frames.size() > 1)) {
			elapsedTime += delta;
			if (elapsedTime > frames.get(currentFrameIndex).getDelay()) {
				elapsedTime = 0;
				currentFrameIndex++;
				if (currentFrameIndex >= frames.size()) {
					currentFrameIndex = 0;
					if (!repetitionEnabled) {
						state = State.FINISHED;
						currentFrameIndex = frames.size() - 1;
					}
				}
			}
		}
		if (frames.size() > 0) {
			return frames.get(currentFrameIndex);
		}
		return null;
	}
	
	/**
	 * Reinicia la animacion. No vacia la lista de frames.
	 */
	public void reset() {
		currentFrameIndex = 0;
		elapsedTime = 0;
		state = State.RUNNING;
	}
	
	/**
	 * Vacia la lista de frames y reinicia la configuracion
	 */
	public void clear() {
		currentFrameIndex = 0;
		elapsedTime = 0;
		state = State.RUNNING;
		frames.clear();
	}
	
	/**
	 * Devuelve si la repeticion esta activada
	 * 
	 * @return true si se repetite el ciclo de la animacion, false en caso contrario
	 */
	public boolean isRepetitionEnabled() {
		return repetitionEnabled;
	}
	
	/**
	 * Asigna si se repite el ciclo de la animacion o no
	 * 
	 * @param repetitionEnabled Indica si se repetira el ciclo de la animacion o no
	 */
	public void setRepetitionEnabled(boolean repetitionEnabled) {
		this.repetitionEnabled = repetitionEnabled;
		if (state == State.FINISHED) {
			state = State.RUNNING;
			currentFrameIndex = 0;
		}
	}
	
	/**
	 * Si la animacion no ha terminado, la pausa. Se puede reanudar con {@link #resume()}
	 */
	public void pause() {
		if (state != State.FINISHED) {
			state = State.PAUSED;
		}
	}
	
	/**
	 * Si la animacion esta pausada, la reanuda. Se puede reanudar con {@link #pause()}
	 */
	public void resume() {
		if (state == State.PAUSED) {
			state = State.RUNNING;
		}
	}
	
	/**
	 * Devuelve si la animacion se esta ejecutando (esta pausada ni ha terminado)
	 * 
	 * @return true si la animacion esta ejecutandose, false en caso contrario
	 */
	public boolean isRunning() {
		return state == State.RUNNING;
	}
	
	/**
	 * Devuelve si la animacion esta pausada
	 * 
	 * @return true si la animacion esta pausada, false en caso contrario
	 */
	public boolean isPaused() {
		return state == State.PAUSED;
	}
	
	/**
	 * Devuelve si la animacion ha terminado.<br>
	 * Si la repeticion esta activada, devolvera siempre true, puesto que la animacion no termina nunca.
	 * 
	 * @return true si la animacion ha terminado, false en caso contrario
	 */
	public boolean isFinished() {
		return state == State.FINISHED;
	}
}
