package com.miviclin.droidengine2d.graphics;

import com.miviclin.droidengine2d.util.Array;
import com.miviclin.droidengine2d.util.math.Vector2;
import com.miviclin.droidengine2d.util.math.Vector3;

/**
 * Clase que define la geometria de un objeto grafico: vertices e informacion de los vertices
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Geometry {
	
	private Array<Vector3> vertices;
	private Array<Vector3> faces;
	private Array<Vector3> colors;
	private Array<Vector2> texturesUV;
	private boolean updateVertices;
	private boolean updateColors;
	private boolean updateTexturesUV;
	private boolean updateFaces;
	
	/**
	 * Crea un objeto Geometry
	 * 
	 * @param numVertices Numero de vertices de la geometria
	 * @param numFaces Numero de faces de la geometria
	 * @param usesColors Indica si la geometria contiene informacion del color de los vertices
	 * @param usesTexturesUV Indica si la geometria contiene informacion de coordenadas de texturas de los vertices
	 */
	public Geometry(int numVertices, int numFaces, boolean usesColors, boolean usesTexturesUV) {
		this.vertices = new Array<Vector3>(numVertices);
		this.faces = new Array<Vector3>(numFaces);
		this.colors = (usesColors) ? new Array<Vector3>(numVertices) : null;
		this.texturesUV = (usesTexturesUV) ? new Array<Vector2>(numVertices) : null;
		this.updateVertices = false;
		this.updateFaces = false;
		this.updateColors = false;
		this.updateTexturesUV = false;
	}
	
	/**
	 * Agrega un vertice
	 * 
	 * @param position Posicion del vertice
	 */
	public void addVertex(Vector3 position) {
		vertices.add(position);
	}
	
	/**
	 * Devuelve el vertice situado en el indice especificado del Array de vertices
	 * 
	 * @param index Indice del vertice
	 * @return Coordenadas del vertice
	 */
	public Vector3 getVertex(int index) {
		return vertices.get(index);
	}
	
	/**
	 * Agrega una face a la geometria (indices de los vertices que definen la face)
	 * 
	 * @param face Indices de los vertices que definen la face
	 */
	public void addFace(Vector3 face) {
		faces.add(face);
	}
	
	/**
	 * Devuelve la face situada en el indice especificado del Array de faces
	 * 
	 * @param index Indice de la face
	 * @return Indices que definen la face
	 */
	public Vector3 getFace(int index) {
		return faces.get(index);
	}
	
	/**
	 * Agrega un color a la geometria. El color ira asociado al vertice que se encuentre en el mismo indice.
	 * 
	 * @param color Color RGB
	 */
	public void addColor(Vector3 color) {
		colors.add(color);
	}
	
	/**
	 * Devuelve el color situado en el indice especificado del Array de colores
	 * 
	 * @param index Indice del vertice al que va asociado el color
	 * @return Color RGB o null si esta {@link Geometry} no contiene informacion de color
	 */
	public Vector3 getColor(int index) {
		if (colors == null) {
			return null;
		}
		return colors.get(index);
	}
	
	/**
	 * Agrega unas coordenadas de textura a la geometria. Las coordenadas UV iran asociadas al vertice que se encuentre en el mismo indice.
	 * 
	 * @param textureUV Coordenadas UV de la textura asociada al vertice
	 */
	public void addTextureUV(Vector2 textureUV) {
		texturesUV.add(textureUV);
	}
	
	/**
	 * Devuelve las coordenadas de textura situadas en el indice especificado del Array de coordenadas UV
	 * 
	 * @param index Indice del vertice al que van asociadas las coordenadas UV
	 * @return Color RGB o null si esta {@link Geometry} no contiene informacion de color
	 */
	public Vector2 getTextureUV(int index) {
		if (texturesUV == null) {
			return null;
		}
		return texturesUV.get(index);
	}
	
	/**
	 * Devuelve si se ha solicitado actualizar los vertices
	 * 
	 * @return true si se ha solicitado actualizar, false en caso contrario
	 */
	public boolean isUpdateVerticesSet() {
		return updateVertices;
	}
	
	/**
	 * Asigna si se necesita actualizar los vertices o no
	 * 
	 * @param updateVertices true para actualizar, false en caso contrario
	 */
	protected void setUpdateVertices(boolean updateVertices) {
		this.updateVertices = updateVertices;
	}
	
	/**
	 * Solicita actualizar los vertices
	 */
	public void updateVertices() {
		updateVertices = true;
	}
	
	/**
	 * Devuelve si se ha solicitado actualizar las faces
	 * 
	 * @return true si se ha solicitado actualizar, false en caso contrario
	 */
	public boolean isUpdateFacesSet() {
		return updateFaces;
	}
	
	/**
	 * Asigna si se necesita actualizar las faces o no
	 * 
	 * @param updateFaces true para actualizar, false en caso contrario
	 */
	protected void setUpdateFaces(boolean updateFaces) {
		this.updateFaces = updateFaces;
	}
	
	/**
	 * Solicita actualizar las faces
	 */
	public void updateFaces() {
		updateFaces = true;
	}
	
	/**
	 * Devuelve si se ha solicitado actualizar los colores
	 * 
	 * @return true si se ha solicitado actualizar, false en caso contrario
	 */
	public boolean isUpdateColorsSet() {
		return updateColors;
	}
	
	/**
	 * Asigna si se necesita actualizar los colores o no
	 * 
	 * @param updateColors true para actualizar, false en caso contrario
	 */
	protected void setUpdateColors(boolean updateColors) {
		this.updateColors = updateColors;
	}
	
	/**
	 * Solicita actualizar los colores
	 */
	public void updateColors() {
		updateColors = true;
	}
	
	/**
	 * Devuelve si se ha solicitado actualizar las coordenadas UV de las texturas
	 * 
	 * @return true si se ha solicitado actualizar, false en caso contrario
	 */
	public boolean isUpdateTexturesUVSet() {
		return updateTexturesUV;
	}
	
	/**
	 * Asigna si se necesita actualizar las coordenadas UV de las texturas o no
	 * 
	 * @param updateTexturesUV true para actualizar, false en caso contrario
	 */
	protected void setUpdateTexturesUV(boolean updateTexturesUV) {
		this.updateTexturesUV = updateTexturesUV;
	}
	
	/**
	 * Solicita actualizar las coordenadas UV de las texturas
	 */
	public void updateTexturesUV() {
		updateTexturesUV = true;
	}
	
}
