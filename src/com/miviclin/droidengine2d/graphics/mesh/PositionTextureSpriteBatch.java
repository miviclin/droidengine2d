package com.miviclin.droidengine2d.graphics.mesh;

import java.nio.FloatBuffer;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.TextureMaterial;
import com.miviclin.droidengine2d.graphics.shader.PositionTextureBatchShaderProgram;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * SpriteBatch que permite renderizar en una llamada hasta 32 sprites con transformaciones (traslacion, rotacion y escala) distintas.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class PositionTextureSpriteBatch<M extends TextureMaterial> extends PositionTextureSpriteBatchBase<M> {
	
	/**
	 * Constructor
	 * 
	 * @param context Context en el que se ejecuta el juego
	 */
	public PositionTextureSpriteBatch(Context context) {
		super(5, context, new PositionTextureBatchShaderProgram());
	}
	
	@Override
	public PositionTextureBatchShaderProgram getShaderProgram() {
		return (PositionTextureBatchShaderProgram) super.getShaderProgram();
	}
	
	@Override
	protected void setupVerticesData() {
		RectangleBatchGeometry geometry = getGeometry();
		int nVertices = BATCH_CAPACITY * 4;
		for (int i = 0; i < nVertices; i++) {
			// Bottom-Left
			geometry.addVertex(new Vector3(-0.5f, -0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(0.0f, 1.0f));
			// Bottom-Right
			geometry.addVertex(new Vector3(0.5f, -0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(1.0f, 1.0f));
			// Top-Right
			geometry.addVertex(new Vector3(0.5f, 0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(1.0f, 0.0f));
			// Top-Left
			geometry.addVertex(new Vector3(-0.5f, 0.5f, 0.0f));
			geometry.addTextureUV(new Vector2(0.0f, 0.0f));
		}
	}
	
	@Override
	protected void copyGeometryToVertexBuffer(int batchSize) {
		FloatBuffer vertexBuffer = getVertexBuffer();
		vertexBuffer.clear();
		int nVertices = BATCH_CAPACITY * 4;
		Vector3 position;
		Vector2 textureUV;
		for (int i = 0; i < nVertices; i++) {
			position = getGeometry().getVertex(i);
			vertexBuffer.put(position.getX());
			vertexBuffer.put(position.getY());
			vertexBuffer.put(position.getZ());
			
			textureUV = getGeometry().getTextureUV(i);
			vertexBuffer.put(textureUV.getX());
			vertexBuffer.put(textureUV.getY());
		}
	}
	
	@Override
	public void draw(Vector2 position, Dimensions2D dimensions, Vector2 center, float rotation, Camera camera) {
		checkInBeginEndPair();
		TextureMaterial material = getCurrentMaterial();
		setupSprite(material.getTextureRegion(), position, dimensions, center, rotation, camera);
		incrementBatchSize();
	}
	
}
