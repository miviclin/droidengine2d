package com.miviclin.droidengine2d.graphics.sprites;

import java.nio.FloatBuffer;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.RectangleBatchGeometry;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shaders.PositionTextureOpacityBatchShaderProgram;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * PositionTextureSpriteBatch que tiene en cuenta la opacidad de los sprites
 * 
 * @see PositionTextureSpriteBatch
 * @author Miguel Vicente Linares
 * 
 */
public class PositionTextureOpacitySpriteBatch extends PositionTextureSpriteBatch {
	
	private int vertexOpacityOffset;
	
	public PositionTextureOpacitySpriteBatch(Context context) {
		this(context, new PositionTextureOpacityBatchShaderProgram());
	}
	
	protected PositionTextureOpacitySpriteBatch(Context context, PositionTextureOpacityBatchShaderProgram shaderProgram) {
		super(context, shaderProgram);
		setVerticesDataStride(6);
		this.vertexOpacityOffset = 5;
		setGeometry(new RectangleBatchGeometry(BATCH_CAPACITY, true, true));
	}
	
	@Override
	protected void setupVerticesData() {
		RectangleBatchGeometry geometry = getGeometry();
		int nVertices = BATCH_CAPACITY * 4;
		for (int i = 0; i < nVertices; i++) {
			// Bottom-Left
			geometry.addVertex(new Vector3(-0.5f, -0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(0.0f, 1.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			// Bottom-Right
			geometry.addVertex(new Vector3(0.5f, -0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(1.0f, 1.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			// Top-Right
			geometry.addVertex(new Vector3(0.5f, 0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(1.0f, 0.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
			// Top-Left
			geometry.addVertex(new Vector3(-0.5f, 0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(0.0f, 0.0f));
			geometry.addColor(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		}
	}
	
	@Override
	protected void copyGeometryToVertexBuffer(int batchSize) {
		FloatBuffer vertexBuffer = getVertexBuffer();
		vertexBuffer.clear();
		int nVertices = BATCH_CAPACITY * 4;
		Vector3 position;
		Vector2 textureUV;
		Color color;
		for (int i = 0; i < nVertices; i++) {
			position = getGeometry().getVertex(i);
			vertexBuffer.put(position.getX());
			vertexBuffer.put(position.getY());
			vertexBuffer.put(position.getZ());
			
			textureUV = getGeometry().getTextureUV(i);
			vertexBuffer.put(textureUV.getX());
			vertexBuffer.put(textureUV.getY());
			
			color = getGeometry().getColor(i);
			vertexBuffer.put(color.getA());
		}
	}
	
	@Override
	protected void setupVertexShaderVariables(int batchSize) {
		super.setupVertexShaderVariables(batchSize);
		PositionTextureOpacityBatchShaderProgram shaderProgram = getShaderProgram();
		shaderProgram.specifyVerticesOpacity(getVertexBuffer(), vertexOpacityOffset, getVerticesDataStrideBytes());
	}
	
	@Override
	public PositionTextureOpacityBatchShaderProgram getShaderProgram() {
		return (PositionTextureOpacityBatchShaderProgram) super.getShaderProgram();
	}
	
	@Override
	protected void drawSprite(Sprite sprite, Camera camera) {
		super.drawSprite(sprite, camera);
		setupOpacity(sprite);
	}
	
	/**
	 * Define la opacidad del siguiente sprite en el batch
	 * 
	 * @param sprite Aprite que se esta agregando al batch
	 */
	private void setupOpacity(Sprite sprite) {
		int spriteOffset = getBatchSize() * 4;
		int limit = spriteOffset + 4;
		for (int i = spriteOffset; i < limit; i++) {
			getGeometry().getColor(i).setA(sprite.getColor().getA());
		}
	}
	
}
