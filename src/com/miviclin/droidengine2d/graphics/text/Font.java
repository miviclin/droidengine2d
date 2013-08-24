package com.miviclin.droidengine2d.graphics.text;

import android.content.Context;

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
	 */
	public FontChar getCharacter(int id);
	
	/**
	 * Elimina todas las referencias y libera recursos.<br>
	 * Este metodo se puede llamar cuando esta Font deje de ser necesario, para liberar recursos.
	 */
	public void clear();
}
