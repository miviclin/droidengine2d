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
		INITIALIZED,
		RUNNING,
		PAUSED,
		FINISHED;
	}
	
	private ArrayList<AnimationStateListener> listeners;
	private ArrayList<AnimationFrame> frames;
	private int currentFrameIndex;
	private float elapsedTime;
	private boolean loopModeEnabled;
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
	 * @param loop Indica si se repetira el ciclo de la animacion o no
	 */
	public Animation(boolean loop) {
		this(10, loop);
	}
	
	/**
	 * Crea una animacion
	 * 
	 * @param initialCapacity Numero minimo de frames que se pueden agregar antes de que se redimensione la estructura de datos
	 * @param repeat Indica si se repetira el ciclo de la animacion o no
	 */
	public Animation(int initialCapacity, boolean repeat) {
		this.listeners = new ArrayList<AnimationStateListener>();
		this.frames = new ArrayList<AnimationFrame>(initialCapacity);
		this.currentFrameIndex = 0;
		this.elapsedTime = 0;
		this.loopModeEnabled = repeat;
		this.state = State.INITIALIZED;
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
	 * Devuelve todos los frames de la animacion
	 * 
	 * @return Frames de la animacion
	 */
	public ArrayList<AnimationFrame> getFrames() {
		return frames;
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
		if (state == State.INITIALIZED) {
			state = State.RUNNING;
			notifyAnimationStarted();
		}
		if ((state == State.RUNNING) && (frames.size() > 0)) {
			elapsedTime += delta;
			if (elapsedTime > frames.get(currentFrameIndex).getDelay()) {
				elapsedTime = 0;
				currentFrameIndex++;
				notifyAnimationFrameChanged();
				if (currentFrameIndex >= frames.size()) {
					currentFrameIndex = 0;
					notifyAnimationLoopFinished();
					if (!loopModeEnabled) {
						state = State.FINISHED;
						currentFrameIndex = frames.size() - 1;
						notifyAnimationFinished();
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
		state = State.INITIALIZED;
	}
	
	/**
	 * Vacia la lista de frames y reinicia la configuracion
	 */
	public void clear() {
		currentFrameIndex = 0;
		elapsedTime = 0;
		state = State.INITIALIZED;
		frames.clear();
	}
	
	/**
	 * Devuelve si la repeticion esta activada
	 * 
	 * @return true si se repetite el ciclo de la animacion, false en caso contrario
	 */
	public boolean isLoopModeEnabled() {
		return loopModeEnabled;
	}
	
	/**
	 * Asigna si se repite el ciclo de la animacion o no
	 * 
	 * @param loopMode Indica si se repetira el ciclo de la animacion o no
	 */
	public void setLoopModeEnabled(boolean loopMode) {
		this.loopModeEnabled = loopMode;
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
			notifyAnimationPaused();
		}
	}
	
	/**
	 * Si la animacion esta pausada, la reanuda. Se puede reanudar con {@link #pause()}
	 */
	public void resume() {
		if (state == State.PAUSED) {
			state = State.RUNNING;
			notifyAnimationResumed();
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
	
	/**
	 * Agrega un {@link AnimationStateListener} a la lista de listeners
	 * 
	 * @param listener listener a agregar
	 */
	public void addAnimationStateListener(AnimationStateListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Elimina un {@link AnimationStateListener} de la lista de listeners
	 * 
	 * @param listener listener a eliminar
	 * @return true si se ha eliminado de la lista, false en caso de que no estuviera en la lista
	 */
	public boolean removeAnimationStateListener(AnimationStateListener listener) {
		return listeners.remove(listener);
	}
	
	/**
	 * Notifica a todos los listeners que la animacion ha comenzado.<br>
	 * Llama a {@link AnimationStateListener#onAnimationStarted(Animation)}
	 */
	protected void notifyAnimationStarted() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationStarted(this);
		}
	}
	
	/**
	 * Notifica a todos los listeners que la animacion ha sido pausada.<br>
	 * Llama a {@link AnimationStateListener#onAnimationStarted(Animation)}
	 */
	protected void notifyAnimationPaused() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationPaused(this);
		}
	}
	
	/**
	 * Notifica a todos los listeners que la animacion ha sido reanudada tras haber sido pausada previamente.<br>
	 * Llama a {@link AnimationStateListener#onAnimationStarted(Animation)}
	 */
	protected void notifyAnimationResumed() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationResumed(this);
		}
	}
	
	/**
	 * Notifica a todos los listeners que la animacion ha pasado de un frame a otro.<br>
	 * Llama a {@link AnimationStateListener#onAnimationFrameChanged(Animation)}
	 */
	protected void notifyAnimationFrameChanged() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationFrameChanged(this);
		}
	}
	
	/**
	 * Notifica a todos los listeners que la animacion ha completado un loop.<br>
	 * Llama a {@link AnimationStateListener#onAnimationLoopFinished(Animation)}
	 */
	protected void notifyAnimationLoopFinished() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationLoopFinished(this);
		}
	}
	
	/**
	 * Notifica a todos los listeners que la animacion ha terminado. Si el modo loop esta activado, la animacion no termina nunca, por lo
	 * que este metodo no deberia ser llamado en dicho caso.<br>
	 * Llama a {@link AnimationStateListener#onAnimationFinished(Animation)}
	 */
	protected void notifyAnimationFinished() {
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).onAnimationFinished(this);
		}
	}
	
}
