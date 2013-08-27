package com.miviclin.droidengine2d.graphics.mesh;

import java.nio.FloatBuffer;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.TextureMaterial;
import com.miviclin.droidengine2d.graphics.shader.PositionTextureBatchShaderProgram;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * Clase base de la que deben heredar los renderers de mallas que representen batches de figuras rectangulares cuyo material sea
 * TextureMaterial
 * 
 * @author Miguel Vicente Linares
 * 
 * @param <M> TextureMaterial
 */
public class TextureMaterialBatchRenderer<M extends TextureMaterial> extends TextureMaterialBatchRendererBase<M> {
	
	/**
	 * Constructor
	 * 
	 * @param context Context en el que se ejecuta el juego
	 */
	public TextureMaterialBatchRenderer(Context context) {
		super(5, context, new PositionTextureBatchShaderProgram());
	}
	
	@Override
	public PositionTextureBatchShaderProgram getShaderProgram() {
		return (PositionTextureBatchShaderProgram) super.getShaderProgram();
	}
	
	@Override
	protected void setupVerticesData() {
		RectangleBatchGeometry geometry = getGeometry();
		int nVertices = getBatchCapacity() * 4;
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
		int nVertices = getBatchCapacity() * 4;
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
	public void draw(Vector2 position, Vector2 scale, Vector2 origin, float rotation, Camera camera) {
		checkInBeginEndPair();
		TextureMaterial material = getCurrentMaterial();
		setupSprite(material.getTextureRegion(), position, scale, origin, rotation, camera);
		incrementBatchSize();
	}
	
}
