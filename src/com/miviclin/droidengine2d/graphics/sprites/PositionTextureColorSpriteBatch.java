package com.miviclin.droidengine2d.graphics.sprites;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shaders.PositionTextureColorBatchShaderProgram;

public class PositionTextureColorSpriteBatch extends PositionTextureSpriteBatch {
	
	protected static final int COLOR_DATA_STRIDE = 4;
	protected static final int COLOR_DATA_STRIDE_BYTES = COLOR_DATA_STRIDE * FLOAT_SIZE_BYTES;
	
	private FloatBuffer colorBuffer;
	private float[] colorData;
	
	public PositionTextureColorSpriteBatch(Context context) {
		super(context, new PositionTextureColorBatchShaderProgram());
		
		colorData = new float[BATCH_CAPACITY * 4 * COLOR_DATA_STRIDE];
		
		colorBuffer = ByteBuffer.allocateDirect(colorData.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		colorBuffer.put(colorData).flip();
	}
	
	protected PositionTextureColorSpriteBatch(Context context, PositionTextureColorBatchShaderProgram shaderProgram) {
		super(context, shaderProgram);
	}
	
	@Override
	protected void setupVertexShaderVariables() {
		super.setupVertexShaderVariables();
		PositionTextureColorBatchShaderProgram shaderProgram = (PositionTextureColorBatchShaderProgram) getShaderProgram();
		shaderProgram.specifyVerticesColors(colorBuffer, 0, 4, COLOR_DATA_STRIDE_BYTES);
	}
	
	@Override
	protected void drawSprite(Sprite sprite, Camera camera) {
		super.drawSprite(sprite, camera);
		setupColor(sprite);
	}
	
	@Override
	protected void drawBatch() {
		colorBuffer.clear();
		colorBuffer.put(colorData).limit(getBatchSize() * 4 * COLOR_DATA_STRIDE).position(0);
		super.drawBatch();
	}
	
	/**
	 * Define el color del siguiente sprite en el batch
	 * 
	 * @param sprite Aprite que se esta agregando al batch
	 */
	protected void setupColor(Sprite sprite) {
		int spriteOffset = getBatchSize() * 4 * COLOR_DATA_STRIDE;
		int limit = spriteOffset + 4 * COLOR_DATA_STRIDE;
		for (int i = spriteOffset; i < limit; i += 4) {
			colorData[i + 0] = sprite.getColor().getR();
			colorData[i + 1] = sprite.getColor().getG();
			colorData[i + 2] = sprite.getColor().getB();
			colorData[i + 3] = sprite.getColor().getA();
		}
	}
	
}
