package com.miviclin.droidengine2d.graphics.mesh;

import com.miviclin.droidengine2d.graphics.material.BlendingOptions;
import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;

/**
 * Base class for graphics batches.
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
	 * Constructor.
	 * 
	 * @param maxBatchSize Maximum size of this GraphicsBatch.
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
	 * Sets up the ShaderProgram.
	 */
	public abstract void setupShaderProgram();

	/**
	 * Prepares this object for rendering.<br>
	 * This method must be called before {@link #end()}.
	 * 
	 * @see #beginDraw()
	 */
	public final void begin() {
		if (inBeginEndPair) {
			throw new RuntimeException("begin() can not be called more than once before calling end()");
		}
		inBeginEndPair = true;
		beginDraw();
	}

	/**
	 * Prepares this object for rendering.<br>
	 * This method is called from {@link #begin()}.
	 */
	protected abstract void beginDraw();

	/**
	 * Renders all the remaining elements in this batch.<br>
	 * This method must be called after {@link #begin()}.
	 */
	public final void end() {
		if (!inBeginEndPair) {
			throw new RuntimeException("begin() must be called once before calling end()");
		}
		inBeginEndPair = false;
		endDraw();
	}

	/**
	 * Renders all the remaining elements in this batch.<br>
	 * This method is called from {@link #end()}.
	 */
	protected abstract void endDraw();

	/**
	 * Returns true if {@link #begin()} was called but {@link #end()} has not been called yet.
	 * 
	 * @return true if {@link #begin()} was called but {@link #end()} has not been called yet, false otherwise.
	 */
	public boolean isInBeginEndPair() {
		return inBeginEndPair;
	}

	/**
	 * Checks if {@link #begin()} was called but {@link #end()} has not been called yet. If the result is false, throws
	 * an exception.
	 */
	public void checkInBeginEndPair() {
		if (!inBeginEndPair) {
			throw new RuntimeException("begin() must be called once before calling draw(...)");
		}
	}

	/**
	 * Returns the BlendingOptions that should be used to render the current batch.
	 * 
	 * @return BlendingOptions
	 */
	public BlendingOptions getCurrentBatchBlendingOptions() {
		return currentBatchBlendingOptions;
	}

	/**
	 * Returns the BlendingOptions that should be used to render the next batch.
	 * 
	 * @return BlendingOptions
	 */
	public BlendingOptions getNextBatchBlendingOptions() {
		return nextBatchBlendingOptions;
	}

	/**
	 * Returns the ShaderProgram.
	 * 
	 * @return ShaderProgram
	 */
	public ShaderProgram getShaderProgram() {
		return shaderProgram;
	}

	/**
	 * Returns the current material of this batch.
	 * 
	 * @return Material
	 */
	public M getCurrentMaterial() {
		return currentMaterial;
	}

	/**
	 * Sets the current material of this batch and updates the current and next BlendingOptions if needed.
	 * 
	 * @param material Material.
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
	 * Returns if the forceDraw flag is set to true or false.
	 * 
	 * @return true or false
	 */
	public boolean isForceDraw() {
		return forceDraw;
	}

	/**
	 * Sets the forceDraw flag. If set to true, it means that the current batch will be rendered even if there is still
	 * room for more elements.
	 * 
	 * @param forceDraw true to force rendering, false otherwise.
	 */
	protected void setForceDraw(boolean forceDraw) {
		this.forceDraw = forceDraw;
	}

	/**
	 * Returns the number of elements in this batch.
	 * 
	 * @return Number of elements in this batch.
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * Increments in 1 the number of elements of this batch.
	 */
	protected void incrementBatchSize() {
		batchSize++;
	}

	/**
	 * Resets to 0 the number of elements of this batch.
	 */
	protected void resetBatchSize() {
		batchSize = 0;
	}

	/**
	 * Returns the capacity of this batch.
	 * 
	 * @return Max number of elements thar this batch is able to render in one draw call.
	 */
	public int getBatchCapacity() {
		return batchCapacity;
	}
}
