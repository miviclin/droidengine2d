package com.miviclin.droidengine2d.graphics.text;

import android.content.Context;
import android.util.SparseArray;

import com.miviclin.droidengine2d.graphics.texture.Texture;

/**
 * Interfaz que define una fuente
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface Font {
	
	/**
	 * Carga la fuente desde un archivo XML
	 * 
	 * @param path Ruta del archivo XML
	 * @param context Context
	 */
	public void loadFromXML(String path, Context context);
	
	/**
	 * Devuelve el caracter especificado
	 * 
	 * @param id ID de la letra
	 * @return FontChar o null en caso de que no exista un caracter con el ID especificado
	 * @throws UndefinedCharacterException Si el caracter especificado no esta definido para esta fuente
	 */
	public FontChar getCharacter(int id);
	
	/**
	 * Elimina todas las referencias y libera recursos.<br>
	 * Este metodo se puede llamar cuando esta Font deje de ser necesario, para liberar recursos.
	 */
	public void clear();
	
	/**
	 * Devuelve las texturas que contienen los caracteres de la fuente indexadas por id de pagina
	 * 
	 * @return Texturas
	 */
	public SparseArray<Texture> getTexturePages();
	
	/**
	 * Calcula el ancho de una linea de texto en pixels
	 * 
	 * @param line Linea de texto
	 * @param fontSizePx Tamano de la fuente en pixels
	 * @return Ancho de la linea de texto en pixels
	 */
	public float measureLineWidth(String line, float fontSizePx);
	
	/**
	 * Calcula el alto de una linea de texto en pixels
	 * 
	 * @param fontSizePx Tamano de la fuente en pixels
	 * @return Alto de la linea de texto en pixels
	 */
	public float measureLineHeight(float fontSizePx);
}
