/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.miviclin.droidengine2d.graphics.mesh;

import android.opengl.Matrix;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.util.TransformUtilities;
import com.miviclin.droidengine2d.util.math.Matrix4;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * This class defines the geometry of the mesh used to render a batch of rectangles.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class RectangleBatchGeometry extends Geometry {

	protected final float[] temp = new float[16];

	private float[] mvpIndices;
	private float[] mvpMatrices;
	private Matrix4 modelMatrix;

	/**
	 * Constructor.
	 * 
	 * @param batchCapacity Max number of elements of the batch.
	 * @param usesColors true if the geometry stores colors, false otherwise.
	 * @param usesTexturesUV true if the geometry stores texture coordinates, false otherwise.
	 */
	public RectangleBatchGeometry(int batchCapacity, boolean usesColors, boolean usesTexturesUV) {
		super(batchCapacity * 4, batchCapacity * 6, usesColors, usesTexturesUV);
		this.mvpIndices = new float[batchCapacity * 4];
		this.mvpMatrices = new float[batchCapacity * 16];
		this.modelMatrix = new Matrix4();

		setupMVPIndices();
	}

	/**
	 * Initializes the array of indices of the array of MVP matrices. This array is used in the vertex shader.
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
	 * Transforms the MVP matrix located at the specified index with the especified position, scale and rotation.
	 * 
	 * @param batchIndex Index of the array of MVP matrices where the matrix is located.
	 * @param position Position.
	 * @param scale Scale.
	 * @param rotation Rotation angle around the origin.
	 * @param camera Camera.
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
	 * Returns the array of indices of the array of MVP matrices.
	 * 
	 * @return Array of indices of the array of MVP matrices.
	 */
	public float[] getMvpIndices() {
		return mvpIndices;
	}

	/**
	 * Sets the array of indices of the array of MVP matrices.
	 * 
	 * @param mvpIndices New array of indices.
	 */
	public void setMvpIndices(float[] mvpIndices) {
		this.mvpIndices = mvpIndices;
	}

	/**
	 * Returns the array of MVP matrices. All matrices are stored in the same array. Each block of 16 numbers is a 4x4
	 * matrix.
	 * 
	 * @return Array of MVP matrices
	 */
	public float[] getMvpMatrices() {
		return mvpMatrices;
	}
}
