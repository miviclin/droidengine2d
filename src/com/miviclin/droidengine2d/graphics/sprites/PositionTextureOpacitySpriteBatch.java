package com.miviclin.droidengine2d.graphics.sprites;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shaders.PositionTextureOpacityBatchShaderProgram;

/**
 * PositionTextureSpriteBatch que tiene en cuenta la opacidad de los sprites
 * 
 * @see PositionTextureSpriteBatch
 * @author Miguel Vicente Linares
 * 
 */
public class PositionTextureOpacitySpriteBatch extends PositionTextureSpriteBatch {
	
	private FloatBuffer opacityBuffer;
	private float[] opacityData;
	
	private PositionTextureOpacityBatchShaderProgram shaderProgram;
	
	public PositionTextureOpacitySpriteBatch(Context context) {
		super(context);
		shaderProgram = new PositionTextureOpacityBatchShaderProgram();
		setShaderProgram(shaderProgram);
		
		opacityData = new float[BATCH_CAPACITY * 6];
		
		opacityBuffer = ByteBuffer.allocateDirect(opacityData.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		opacityBuffer.put(opacityData).flip();
	}
	
	@Override
	public PositionTextureOpacityBatchShaderProgram getShaderProgram() {
		return shaderProgram;
	}
	
	@Override
	protected void setupVertexShaderVariables() {
		super.setupVertexShaderVariables();
		shaderProgram.specifyVerticesOpacity(opacityBuffer, 0, FLOAT_SIZE_BYTES);
	}
	
	@Override
	protected void prepareShaderData(Sprite sprite, Camera camera, boolean textureChanged) {
		super.prepareShaderData(sprite, camera, textureChanged);
		setupOpacity(sprite);
	}
	
	/**
	 * Define la opacidad del batch
	 */
	private void setupOpacity(Sprite sprite) {
		int spriteOffset = getBatchSize() * 6;
		int limit = spriteOffset + 6;
		for (int i = spriteOffset; i < limit; i++) {
			opacityData[i] = sprite.getOpacity();
		}
	}
	
}
