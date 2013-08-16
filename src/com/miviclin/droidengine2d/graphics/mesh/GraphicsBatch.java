package com.miviclin.droidengine2d.graphics.mesh;

import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;

/**
 * Clase base para batches
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class GraphicsBatch<M extends Material> {
	
	private boolean inBeginEndPair;
	private ShaderProgram shaderProgram;
	private M currentMaterial;
	
	/**
	 * Constructor
	 * 
	 * @param shaderProgram ShaderProgram
	 */
	public GraphicsBatch(ShaderProgram shaderProgram) {
		this.inBeginEndPair = false;
		this.shaderProgram = shaderProgram;
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
	
	/**
	 * Si se ha llamado a {@link #begin()} pero aun no se ha llamado a {@link #end()} no hace nada. En caso contrario lanza una excepcion.
	 */
	public void checkInBeginEndPair() {
		if (!inBeginEndPair) {
			throw new RuntimeException("begin() must be called once before calling draw(...)");
		}
	}
	
	/**
	 * Devuelve el ShaderProgram
	 * 
	 * @return ShaderProgram
	 */
	public ShaderProgram getShaderProgram() {
		return shaderProgram;
	}
	
	/**
	 * Devuelve el Material enlazado actualmente al GraphicsBatch
	 * 
	 * @return Material
	 */
	public M getCurrentMaterial() {
		return currentMaterial;
	}
	
	/**
	 * Asigna el Material enlazado actualmente al GraphicsBatch
	 * 
	 * @param currentMaterial Material
	 */
	public void setCurrentMaterial(M currentMaterial) {
		this.currentMaterial = currentMaterial;
	}
}
