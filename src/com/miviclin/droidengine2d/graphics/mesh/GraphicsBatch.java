package com.miviclin.droidengine2d.graphics.mesh;

/**
 * Clase base para batches
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class GraphicsBatch {
	
	private boolean inBeginEndPair;
	
	/**
	 * Constructor
	 */
	public GraphicsBatch() {
		this.inBeginEndPair = false;
	}
	
	/**
	 * Prepara el batch para pintar.
	 */
	public final void begin() {
		if (inBeginEndPair) {
			throw new RuntimeException("begin() can not be called more than once before calling end()");
		}
		inBeginEndPair = true;
		beginDraw();
	}
	
	/**
	 * Prepara el batch para pintar.<br>
	 * Este metodo se llama desde {@link #begin()}
	 */
	protected abstract void beginDraw();
	
	/**
	 * Renderiza todos los elementos que contenga el batch.<br>
	 * Este metodo debe llamarse siempre al terminar de agregar todos los elementos al batch. Es necesario llamar antes a {@link #begin()}
	 */
	public final void end() {
		if (!inBeginEndPair) {
			throw new RuntimeException("begin() must be called once before calling end()");
		}
		endDraw();
		inBeginEndPair = false;
	}
	
	/**
	 * Renderiza todos los elementos que contenga el batch.<br>
	 * Este metodo se llama desde {@link #end()}
	 */
	protected abstract void endDraw();
	
	/**
	 * Devuelve si se ha llamado a {@link #begin()} pero aun no se ha llamado a {@link #end()}
	 * 
	 * @return true si se ha llamado a {@link #begin()} pero aun no se ha llamado a {@link #end()}, false en caso contrario
	 */
	public boolean isInBeginEndPair() {
		return inBeginEndPair;
	}
}
