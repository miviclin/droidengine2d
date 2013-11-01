package com.miviclin.droidengine2d.graphics.mesh;

import com.miviclin.droidengine2d.graphics.material.BlendingOptions;
import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;

/**
 * Clase base para batches
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class GraphicsBatch<M extends Material> {

	private final BlendingOptions currentBatchBlendingOptions;
	private final BlendingOptions nextBatchBlendingOptions;

	private boolean inBeginEndPair;
	private ShaderProgram shaderProgram;
	private M currentMaterial;
	private boolean forceDraw;
	private int batchSize;
	private int batchCapacity;

	/**
	 * Constructor
	 * 
	 * @param shaderProgram ShaderProgram
	 */
	public GraphicsBatch(int maxBatchSize) {
		this.currentBatchBlendingOptions = new BlendingOptions();
		this.nextBatchBlendingOptions = new BlendingOptions();
		this.inBeginEndPair = false;
		this.shaderProgram = new ShaderProgram();
		this.forceDraw = false;
		this.batchSize = 0;
		this.batchCapacity = maxBatchSize;
	}

	/**
	 * Inicializa el shader program.
	 */
	public abstract void setupShaderProgram();

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
	 * Este metodo debe llamarse siempre al terminar de agregar todos los elementos al batch. Es necesario llamar antes
	 * a {@link #begin()}
	 */
	public final void end() {
		if (!inBeginEndPair) {
			throw new RuntimeException("begin() must be called once before calling end()");
		}
		inBeginEndPair = false;
		endDraw();
	}

	/**
	 * Renderiza todos los elementos que contenga el batch.<br>
	 * Este metodo se llama desde {@link #end()}
	 */
	protected abstract void endDraw();

	/**
	 * Devuelve si se ha llamado a {@link #begin()} pero aun no se ha llamado a {@link #end()}
	 * 
	 * @return true si se ha llamado a {@link #begin()} pero aun no se ha llamado a {@link #end()}, false en caso
	 *         contrario
	 */
	public boolean isInBeginEndPair() {
		return inBeginEndPair;
	}

	/**
	 * Si se ha llamado a {@link #begin()} pero aun no se ha llamado a {@link #end()} no hace nada. En caso contrario
	 * lanza una excepcion.
	 */
	public void checkInBeginEndPair() {
		if (!inBeginEndPair) {
			throw new RuntimeException("begin() must be called once before calling draw(...)");
		}
	}

	/**
	 * Devuelve las opciones de blending que se deben usar para renderizar el batch actual
	 * 
	 * @return BlendingOptions
	 */
	public BlendingOptions getCurrentBatchBlendingOptions() {
		return currentBatchBlendingOptions;
	}

	/**
	 * Devuelve las opciones de blending que se deben usar para renderizar el siguiente batch
	 * 
	 * @return BlendingOptions
	 */
	public BlendingOptions getNextBatchBlendingOptions() {
		return nextBatchBlendingOptions;
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
	 * @param material Material
	 */
	public void setCurrentMaterial(M material) {
		BlendingOptions nextBatchBlendingOptions = material.getBlendingOptions();
		if (!this.currentBatchBlendingOptions.equals(nextBatchBlendingOptions)) {
			if (batchSize != 0) {
				forceDraw = true;
			}
		}
		if (batchSize == 0) {
			this.currentBatchBlendingOptions.copy(nextBatchBlendingOptions);
		}
		this.nextBatchBlendingOptions.copy(nextBatchBlendingOptions);
		this.currentMaterial = material;
	}

	/**
	 * Devuelve true si hay que forzar el renderizado de los elementos del batch
	 * 
	 * @return true si hay que renderizar, false en caso contrario
	 */
	public boolean isForceDraw() {
		return forceDraw;
	}

	/**
	 * Asigna si hay que forzar el renderizado de los elementos del batch
	 * 
	 * @param forceDraw true para forzar renderizado, false en caso contrario
	 */
	protected void setForceDraw(boolean forceDraw) {
		this.forceDraw = forceDraw;
	}

	/**
	 * Devuelve el numero de sprites que hay en el batch
	 * 
	 * @return Numero de sprites en el batch
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * Incrementa en 1 el numero de sprites que hay en el batch
	 */
	protected void incrementBatchSize() {
		batchSize++;
	}

	/**
	 * Reinicia a 0 el numero de sprites que hay en el batch
	 */
	protected void resetBatchSize() {
		batchSize = 0;
	}

	/**
	 * Devuelve el maximo numero de elementos que puede almacenar el batch antes de renderizar
	 * 
	 * @return Maximo numero de elementos que puede almacenar el batch antes de renderizar
	 */
	public int getBatchCapacity() {
		return batchCapacity;
	}
}
