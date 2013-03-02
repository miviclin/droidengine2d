package com.miviclin.droidengine2d.graphics.textures;

import android.content.Context;

/**
 * TextureAtlas
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface TextureAtlas {
	
	/**
	 * Carga el TextureAtlas desde un archivo XML
	 * 
	 * @param context Context
	 * @param path Ruta del archivo XML
	 */
	public void loadFromXML(Context context, String path);
	
	/**
	 * Devuelve la textura que define este TextureAtlas
	 * 
	 * @return GLTexture
	 */
	public GLTexture getSourceTexture();
	
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
	
}
