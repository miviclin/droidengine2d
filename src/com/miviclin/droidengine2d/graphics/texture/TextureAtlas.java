package com.miviclin.droidengine2d.graphics.texture;

import java.util.Map;

import android.content.Context;

/**
 * TextureAtlas
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface TextureAtlas {

	/**
	 * Carga el TextureAtlas desde un archivo
	 * 
	 * @param path Ruta del archivo
	 * @param context Context
	 */
	public void loadFromFile(String path, Context context);

	/**
	 * Devuelve la textura que define este TextureAtlas
	 * 
	 * @return Texture
	 */
	public Texture getSourceTexture();

	/**
	 * Devuelve la region especificada de este TextureAtlas
	 * 
	 * @param name Nombre que identifica a la region
	 * @return TextureRegion o null en caso de que no exista una TextureRegion con el nombre especificado
	 */
	public TextureRegion getTextureRegion(String name);

	/**
	 * Elimina todas las referencias a las TextureRegions del TextureAtlas y la referencia a la textura principal.<br>
	 * Este metodo se puede llamar cuando este TextureAtlas deje de ser necesario, para liberar recursos.
	 */
	public void clearAtlas();

	/**
	 * Devuelve el mapa de TextureRegions que contiene todos los TextureRegion del TextureAtlas indexados por su
	 * correspondiente clave
	 * 
	 * @return {@code Map<String, TextureRegion}
	 */
	public Map<String, TextureRegion> getTextureRegions();
}
