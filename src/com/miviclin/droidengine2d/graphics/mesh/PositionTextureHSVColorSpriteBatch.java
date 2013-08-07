package com.miviclin.droidengine2d.graphics.mesh;

import java.nio.FloatBuffer;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.shader.PositionTextureHSVColorBatchShaderProgram;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;


public class PositionTextureHSVColorSpriteBatch extends PositionTextureColorSpriteBatch {

	public PositionTextureHSVColorSpriteBatch(Context context) {
		super(context, new PositionTextureHSVColorBatchShaderProgram());
	}
	
	protected PositionTextureHSVColorSpriteBatch(Context context, PositionTextureHSVColorBatchShaderProgram shaderProgram) {
		super(context, shaderProgram);
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
			vertexBuffer.put(color.getH());
			vertexBuffer.put(color.getS());
			vertexBuffer.put(color.getV());
			vertexBuffer.put(color.getA());
		}
	}
	
}
