package com.miviclin.droidengine2d.graphics;

import com.miviclin.droidengine2d.util.Array;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

public class Geometry {
	
	private Array<Vector3> vertices;
	private Array<Vector3> faces;
	private Array<Vector3> colors;
	private Array<Vector2> texturesUV;
	private boolean updateVertices;
	private boolean updateColors;
	private boolean updateTexturesUV;
	private boolean updateFaces;
	
	private Geometry(GeometryBuilder builder) {
		this.vertices = builder.vertices;
		this.faces = builder.faces;
		this.colors = builder.colors;
		this.texturesUV = builder.texturesUV;
		this.updateVertices = false;
		this.updateFaces = false;
		this.updateColors = false;
		this.updateTexturesUV = false;
	}
	
	public void addVertex(Vector3 position) {
		vertices.add(position);
	}
	
	public Vector3 getVertex(int index) {
		return vertices.get(index);
	}
	
	public void addFace(Vector3 face) {
		faces.add(face);
	}
	
	public Vector3 getFace(int index) {
		return faces.get(index);
	}
	
	public void addColor(Vector3 color) {
		colors.add(color);
	}
	
	public Vector3 getColor(int index) {
		if (colors == null) {
			return null;
		}
		return colors.get(index);
	}
	
	public void addTextureUV(Vector2 textureUV) {
		texturesUV.add(textureUV);
	}
	
	public Vector2 getTextureUV(int index) {
		if (texturesUV == null) {
			return null;
		}
		return texturesUV.get(index);
	}
	
	public boolean isUpdateVerticesSet() {
		return updateVertices;
	}
	
	protected void setUpdateVertices(boolean updateVertices) {
		this.updateVertices = updateVertices;
	}
	
	public void updateVertices() {
		updateVertices = true;
	}
	
	public boolean isUpdateFacesSet() {
		return updateFaces;
	}
	
	protected void setUpdateFaces(boolean updateFaces) {
		this.updateFaces = updateFaces;
	}
	
	public void updateFaces() {
		updateFaces = true;
	}
	
	public boolean isUpdateColorsSet() {
		return updateColors;
	}
	
	protected void setUpdateColors(boolean updateColors) {
		this.updateColors = updateColors;
	}
	
	public void updateColors() {
		updateColors = true;
	}
	
	public boolean isUpdateTexturesUVSet() {
		return updateTexturesUV;
	}
	
	protected void setUpdateTexturesUV(boolean updateTexturesUV) {
		this.updateTexturesUV = updateTexturesUV;
	}
	
	public void updateTexturesUV() {
		updateTexturesUV = true;
	}
	
	public static class GeometryBuilder {
		
		private Array<Vector3> vertices;
		private Array<Vector3> faces;
		private Array<Vector3> colors;
		private Array<Vector2> texturesUV;
		private int numVertices;
		private int numFaces;
		private boolean usesColors;
		private boolean usesTexturesUV;
		
		public GeometryBuilder(int numVertices, int numFaces) {
			this.numVertices = numVertices;
			this.numFaces = numFaces;
			this.usesColors = false;
			this.usesTexturesUV = false;
		}
		
		public void useColors() {
			this.usesColors = true;
		}
		
		public void useTexturesUV() {
			this.usesTexturesUV = true;
		}
		
		public Geometry build() {
			this.vertices = new Array<Vector3>(numVertices);
			this.faces = new Array<Vector3>(numFaces);
			this.colors = (usesColors) ? new Array<Vector3>(numVertices) : null;
			this.texturesUV = (usesTexturesUV) ? new Array<Vector2>(numVertices) : null;
			return new Geometry(this);
		}
		
	}
	
}
