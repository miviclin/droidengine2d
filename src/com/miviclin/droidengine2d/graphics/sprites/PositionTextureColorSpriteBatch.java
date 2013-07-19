package com.miviclin.droidengine2d.graphics.sprites;

import java.nio.FloatBuffer;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.RectangleBatchGeometry;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shaders.PositionTextureColorBatchShaderProgram;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

public class PositionTextureColorSpriteBatch extends PositionTextureSpriteBatch {
	
	private int vertexColorOffset;
	
	public PositionTextureColorSpriteBatch(Context context) {
		this(context, new PositionTextureColorBatchShaderProgram());
	}
	
	protected PositionTextureColorSpriteBatch(Context context, PositionTextureColorBatchShaderProgram shaderProgram) {
		super(context, shaderProgram);
		setVerticesDataStride(9);
		this.vertexColorOffset = 5;
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
			vertexBuffer.put(color.getR());
			vertexBuffer.put(color.getG());
			vertexBuffer.put(color.getB());
			vertexBuffer.put(color.getA());
		}
	}
	
	@Override
	protected void setupVertexShaderVariables(int batchSize) {
		super.setupVertexShaderVariables(batchSize);
		PositionTextureColorBatchShaderProgram shaderProgram = getShaderProgram();
		shaderProgram.specifyVerticesColors(getVertexBuffer(), vertexColorOffset, 4, getVerticesDataStrideBytes());
	}
	
	@Override
	public PositionTextureColorBatchShaderProgram getShaderProgram() {
		return (PositionTextureColorBatchShaderProgram) super.getShaderProgram();
	}
	
	@Override
	protected void drawSprite(Sprite sprite, Camera camera) {
		super.drawSprite(sprite, camera);
		setupColor(sprite);
	}
	
	/**
	 * Define el color del siguiente sprite en el batch
	 * 
	 * @param sprite Aprite que se esta agregando al batch
	 */
	private void setupColor(Sprite sprite) {
		int spriteOffset = getBatchSize() * 4;
		int limit = spriteOffset + 4;
		for (int i = spriteOffset; i < limit; i++) {
			getGeometry().getColor(i).setR(sprite.getColor().getR());
			getGeometry().getColor(i).setG(sprite.getColor().getG());
			getGeometry().getColor(i).setB(sprite.getColor().getB());
			getGeometry().getColor(i).setA(sprite.getColor().getA());
		}
	}
	
}
