package com.miviclin.droidengine2d.graphics.mesh;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.util.Array;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * This class defines the Geometry of a mesh of vertices.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Geometry {

	private Array<Vector3> vertices;
	private Array<Short> indices;
	private Array<Color> colors;
	private Array<Vector2> texturesUV;

	/**
	 * Creates a new Geometry.
	 * 
	 * @param numVertices Number of vertices.
	 * @param numIndices Number of indices (the indices are used to render the vertices in the specified order).
	 * @param usesColors true if the geometry stores colors, false otherwise.
	 * @param usesTexturesUV true if the geometry stores texture coordinates, false otherwise.
	 */
	public Geometry(int numVertices, int numIndices, boolean usesColors, boolean usesTexturesUV) {
		this.vertices = new Array<Vector3>(numVertices);
		this.indices = new Array<Short>(numIndices);
		this.colors = (usesColors) ? new Array<Color>(numVertices) : null;
		this.texturesUV = (usesTexturesUV) ? new Array<Vector2>(numVertices) : null;
	}

	/**
	 * Adds a vertex position to this Geometry.
	 * 
	 * @param position Vertex position.
	 */
	public void addVertex(Vector3 position) {
		vertices.add(position);
	}

	/**
	 * Returns the vertex position located at the specified index of the array of vertices.
	 * 
	 * @param index Index.
	 * @return Vertex position
	 */
	public Vector3 getVertex(int index) {
		return vertices.get(index);
	}

	/**
	 * Returns the number of vertices of this Geometry.
	 * 
	 * @return Number of vertices
	 */
	public int getNumVertices() {
		return vertices.size();
	}

	/**
	 * Adds an index to this Geometry.
	 * 
	 * @param index Index.
	 */
	public void addIndex(short index) {
		indices.add(index);
	}

	/**
	 * Returns the index located at the specified index of the array of indices.
	 * 
	 * @param index Index (of the array of indices) where the index we want to get is located.
	 * @return Index
	 */
	public short getIndex(int index) {
		return indices.get(index);
	}

	/**
	 * Returns the number of indices of this Geometry.
	 * 
	 * @return Number of indices
	 */
	public int getNumIndices() {
		return indices.size();
	}

	/**
	 * Adds a Color to this Geometry. The color will be associated to the vertex position located at the same index.
	 * 
	 * @param color Color.
	 */
	public void addColor(Color color) {
		colors.add(color);
	}

	/**
	 * Returns the Color located at the specified index of the array of colors.
	 * 
	 * @param index Index.
	 * @return Color or null if this Geometry does not contain color information.
	 */
	public Color getColor(int index) {
		if (colors == null) {
			return null;
		}
		return colors.get(index);
	}

	/**
	 * Returns the number of colors of this Geometry. If this Geometry uses colors, there should be one color per
	 * defined vertex.
	 * 
	 * @return Number of colors, or -1 if this Geometry does not use colors.
	 */
	public int getNumColors() {
		if (colors == null) {
			return -1;
		}
		return colors.size();
	}

	/**
	 * Adds a pair of texture UV coordinates to this Geometry. The pair of texture UV coordinates will be associated to
	 * the vertex position located at the same index.
	 * 
	 * @param textureUV Texture UV coordinates.
	 */
	public void addTextureUV(Vector2 textureUV) {
		texturesUV.add(textureUV);
	}

	/**
	 * Returns the texture UV coordinates located at the specified index of the array of texture UV coordinates.
	 * 
	 * @param index Index.
	 * @return Texture UV coordinates or null if this Geometry does not contain texture information.
	 */
	public Vector2 getTextureUV(int index) {
		if (texturesUV == null) {
			return null;
		}
		return texturesUV.get(index);
	}

	/**
	 * Returns the number of texture UV coordinates of this Geometry. If this Geometry uses texture UV coordinates,
	 * there should be one texture UV coordinate pair per defined vertex.
	 * 
	 * @return Number of texture UV coordinates, or -1 if this Geometry does not use texture UV coordinates.
	 */
	public int getNumTexturesUV() {
		if (texturesUV == null) {
			return -1;
		}
		return texturesUV.size();
	}

}
