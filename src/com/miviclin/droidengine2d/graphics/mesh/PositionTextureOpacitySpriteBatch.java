package com.miviclin.droidengine2d.graphics.mesh;

import java.nio.FloatBuffer;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.TransparentTextureMaterial;
import com.miviclin.droidengine2d.graphics.shader.PositionTextureOpacityBatchShaderProgram;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * PositionTextureSpriteBatch que tiene en cuenta la opacidad de los sprites
 * 
 * @see PositionTextureSpriteBatch
 * @author Miguel Vicente Linares
 * 
 */
public class PositionTextureOpacitySpriteBatch<M extends TransparentTextureMaterial> extends PositionTextureSpriteBatchBase<M> {
	
	private int vertexOpacityOffset;
	
	/**
	 * Constructor
	 * 
	 * @param context Context en el que se ejecuta el juego
	 */
	public PositionTextureOpacitySpriteBatch(Context context) {
		super(6, context, new PositionTextureOpacityBatchShaderProgram());
		this.vertexOpacityOffset = 5;
		setGeometry(new RectangleBatchGeometry(BATCH_CAPACITY, true, true));
	}
	
	@Override
	public PositionTextureOpacityBatchShaderProgram getShaderProgram() {
		return (PositionTextureOpacityBatchShaderProgram) super.getShaderProgram();
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
	public void draw(Vector2 position, Dimensions2D dimensions, Vector2 center, float rotation, Vector2 rotationPoint, float rotationAroundPoint, Camera camera) {
		checkInBeginEndPair();
		TransparentTextureMaterial material = getCurrentMaterial();
		setupSprite(material.getTextureRegion(), position, dimensions, center, rotation, rotationPoint, rotationAroundPoint, camera);
		setupOpacity(material.getOpacity());
		incrementBatchSize();
	}
	
	/**
	 * Define la opacidad del siguiente sprite en el batch
	 * 
	 * @param opacity Opacidad
	 */
	private void setupOpacity(float opacity) {
		int spriteOffset = getBatchSize() * 4;
		int limit = spriteOffset + 4;
		for (int i = spriteOffset; i < limit; i++) {
			getGeometry().getColor(i).setA(opacity);
		}
	}
	
}
