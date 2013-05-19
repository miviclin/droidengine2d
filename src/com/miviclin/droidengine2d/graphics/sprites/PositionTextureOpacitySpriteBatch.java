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
	private float lastOpacity;
	
	public PositionTextureOpacitySpriteBatch(Context context) {
		super(context, new PositionTextureOpacityBatchShaderProgram());
		
		opacityData = new float[BATCH_CAPACITY * 4];
		
		opacityBuffer = ByteBuffer.allocateDirect(opacityData.length * FLOAT_SIZE_BYTES)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		opacityBuffer.put(opacityData).flip();
		
		lastOpacity = 1.0f;
	}
	
	protected PositionTextureOpacitySpriteBatch(Context context, PositionTextureOpacityBatchShaderProgram shaderProgram) {
		super(context, shaderProgram);
	}
	
	@Override
	protected void setupVertexShaderVariables() {
		super.setupVertexShaderVariables();
		PositionTextureOpacityBatchShaderProgram shaderProgram = (PositionTextureOpacityBatchShaderProgram) getShaderProgram();
		shaderProgram.specifyVerticesOpacity(opacityBuffer, 0, FLOAT_SIZE_BYTES);
	}
	
	@Override
	protected void drawSprite(Sprite sprite, Camera camera) {
		boolean textureChanged = checkTextureChanged(sprite);
		if ((getBatchSize() > 0) && ((getBatchSize() == BATCH_CAPACITY) || textureChanged || sprite.getOpacity() != lastOpacity)) {
			drawBatch();
		}
		setupTexture(sprite, textureChanged);
		setSpriteVerticesData(sprite);
		updateSpriteMVPMatrix(sprite, camera);
		setupOpacity(sprite);
	}
	
	@Override
	protected void drawBatch() {
		opacityBuffer.clear();
		opacityBuffer.put(opacityData).limit(getBatchSize() * 4).position(0);
		super.drawBatch();
	}
	
	/**
	 * Define la opacidad del siguiente sprite en el batch
	 * 
	 * @param opacity Opacidad. Valor entre 0 y 1
	 */
	protected void setupOpacity(Sprite sprite) {
		int spriteOffset = getBatchSize() * 6;
		int limit = spriteOffset + 6;
		for (int i = spriteOffset; i < limit; i++) {
			opacityData[i] = sprite.getOpacity();
		}
		lastOpacity = sprite.getOpacity();
	}
	
}
