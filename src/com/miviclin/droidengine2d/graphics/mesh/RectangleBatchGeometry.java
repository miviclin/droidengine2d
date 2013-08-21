package com.miviclin.droidengine2d.graphics.mesh;

import android.opengl.Matrix;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.util.TransformUtilities;
import com.miviclin.droidengine2d.util.math.Matrix4;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Define la geometria basica de una malla que representa un batch de figuras rectangulares
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class RectangleBatchGeometry extends Geometry {
	
	protected final float[] temp = new float[16];
	
	private float[] mvpIndices;
	private float[] mvpMatrices;
	private Matrix4 modelMatrix;
	
	public RectangleBatchGeometry(int batchCapacity, boolean usesColors, boolean usesTexturesUV) {
		super(batchCapacity * 4, batchCapacity * 6, usesColors, usesTexturesUV);
		this.mvpIndices = new float[batchCapacity * 4];
		this.mvpMatrices = new float[batchCapacity * 16];
		this.modelMatrix = new Matrix4();
		
		setupMVPIndices();
	}
	
	/**
	 * Inicializa el array de indices que permiten acceder a las matrices MVP en el vertex shader.
	 */
	private void setupMVPIndices() {
		float value = 0.0f;
		for (int i = 0; i < mvpIndices.length; i++) {
			if ((i != 0) && (i % 4 == 0)) {
				value += 1.0f;
			}
			mvpIndices[i] = value;
		}
	}
	
	/**
	 * Transforma la matriz MVP del indice especificado
	 * 
	 * @param batchIndex Indice de la matriz sobre la que se aplicaran las transformaciones
	 * @param position Posicion
	 * @param scale Escala
	 * @param rotation Angulo de rotacion sobre el centro
	 * @param camera Camara
	 */
	public void updateMVPMatrix(int batchIndex, Vector2 position, Vector2 scale, float rotation, Camera camera) {
		int mvpOffset;
		float tx = position.getX();
		float ty = position.getY();
		
		if (rotation != 0) {
			TransformUtilities.transform2D(modelMatrix, tx, ty, rotation, scale.getX(), scale.getY());
		} else {
			TransformUtilities.transform2D(modelMatrix, tx, ty, scale.getX(), scale.getY());
		}
		mvpOffset = batchIndex * 16;
		Matrix.multiplyMM(temp, 0, camera.getViewMatrix().getValues(), 0, modelMatrix.getValues(), 0);
		Matrix.multiplyMM(mvpMatrices, mvpOffset, camera.getProjectionMatrix().getValues(), 0, temp, 0);
	}
	
	/**
	 * Devuelve el array de indices de las matrices MVP
	 * 
	 * @return Array de indices de matrices MVP
	 */
	public float[] getMvpIndices() {
		return mvpIndices;
	}
	
	/**
	 * Asigna el array de indices de las matrices MVP
	 * 
	 * @param mvpIndices Nuevo array de indices
	 */
	public void setMvpIndices(float[] mvpIndices) {
		this.mvpIndices = mvpIndices;
	}
	
	/**
	 * Devuelve las matrices MVP, todas almacenadas en el mismo array. Cada 16 elementos es una matriz.
	 * 
	 * @return Array que contiene las matrices MVP
	 */
	public float[] getMvpMatrices() {
		return mvpMatrices;
	}
}
