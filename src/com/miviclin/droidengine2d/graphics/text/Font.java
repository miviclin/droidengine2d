package com.miviclin.droidengine2d.graphics.text;

import android.content.Context;

public interface Font {
	
	/**
	 * Carga la fuente desde un archivo XML
	 * 
	 * @param path Ruta del archivo XML
	 * @param context Context
	 */
	public void loadFromXML(String path, Context context);
	
	/**
	 * Devuelve la letra especificada
	 * 
	 * @param id ID de la letra
	 * @return Letter o null en caso de que no exista una letra con el ID especificado
	 */
	public Letter getLetter(int id);
	
	/**
	 * Elimina todas las referencias y libera recursos.<br>
	 * Este metodo se puede llamar cuando esta Font deje de ser necesario, para liberar recursos.
	 */
	public void clear();
}
