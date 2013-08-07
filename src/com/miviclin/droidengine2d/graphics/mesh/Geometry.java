package com.miviclin.droidengine2d.graphics.mesh;

import com.miviclin.droidengine2d.graphics.Color;
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
	private Array<Short> indices;
	private Array<Color> colors;
	private Array<Vector2> texturesUV;
	private boolean updateVertices;
	private boolean updateIndices;
	private boolean updateColors;
	private boolean updateTexturesUV;
	
	/**
	 * Crea un objeto Geometry
	 * 
	 * @param numVertices Numero de vertices para el que se reservara memoria
	 * @param numIndices Numero de indices (representan el orden en que se pintaran los vertices) para el que se reservara memoria
	 * @param usesColors Indica si la geometria contiene informacion del color de los vertices
	 * @param usesTexturesUV Indica si la geometria contiene informacion de coordenadas de texturas de los vertices
	 */
	public Geometry(int numVertices, int numIndices, boolean usesColors, boolean usesTexturesUV) {
		this.vertices = new Array<Vector3>(numVertices);
		this.indices = new Array<Short>(numIndices);
		this.colors = (usesColors) ? new Array<Color>(numVertices) : null;
		this.texturesUV = (usesTexturesUV) ? new Array<Vector2>(numVertices) : null;
		this.updateVertices = false;
		this.updateIndices = false;
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
	 * Devuelve el numero de vertices definidos en esta geometria
	 * 
	 * @return Numero de vertices
	 */
	public int getNumVertices() {
		return vertices.size();
	}
	
	/**
	 * Agrega un indice a la geometria
	 * 
	 * @param index Indice a agregar
	 */
	public void addIndex(short index) {
		indices.add(index);
	}
	
	/**
	 * Devuelve el indice situado en el indice especificado del Array de indices
	 * 
	 * @param index Indice (posicion en el array) del indice que se quiere obtener
	 * @return Indice
	 */
	public short getIndex(int index) {
		return indices.get(index);
	}
	
	/**
	 * Devuelve el numero de indices definidos en esta geometria
	 * 
	 * @return Numero de indices
	 */
	public int getNumIndices() {
		return indices.size();
	}
	
	/**
	 * Agrega un color a la geometria. El color ira asociado al vertice que se encuentre en el mismo indice.
	 * 
	 * @param color Color
	 */
	public void addColor(Color color) {
		colors.add(color);
	}
	
	/**
	 * Devuelve el color situado en el indice especificado del Array de colores
	 * 
	 * @param index Indice del vertice al que va asociado el color
	 * @return Coloro null si esta {@link Geometry} no contiene informacion de color
	 */
	public Color getColor(int index) {
		if (colors == null) {
			return null;
		}
		return colors.get(index);
	}
	
	/**
	 * Devuelve el numero de colores definidos en esta geometria (si la geometria utiliza colores, deberia haber 1 color por cada vertice)
	 * 
	 * @return Numero de colores, o -1 si no usa colores
	 */
	public int getNumColors() {
		if (colors == null) {
			return -1;
		}
		return colors.size();
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
	 * Devuelve el numero de coordenadas UV definidas en esta geometria (si la geometria utiliza texturas, deberia haber 1 coordenadas UV
	 * por cada vertice)
	 * 
	 * @return Numero de coordenadas UV, o -1 si no usa texturas
	 */
	public int getNumTexturesUV() {
		if (texturesUV == null) {
			return -1;
		}
		return texturesUV.size();
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
	 * Devuelve si se ha solicitado actualizar los indices
	 * 
	 * @return true si se ha solicitado actualizar, false en caso contrario
	 */
	public boolean isUpdateIndicesSet() {
		return updateIndices;
	}
	
	/**
	 * Asigna si se necesita actualizar los indices o no
	 * 
	 * @param updateIndices true para actualizar, false en caso contrario
	 */
	protected void setUpdateIndices(boolean updateIndices) {
		this.updateIndices = updateIndices;
	}
	
	/**
	 * Solicita actualizar los indices
	 */
	public void updateIndices() {
		updateIndices = true;
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
