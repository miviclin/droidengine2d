package com.miviclin.droidengine2d.graphics.texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.SparseArray;

import com.miviclin.droidengine2d.graphics.text.Font;
import com.miviclin.droidengine2d.util.Pool;

/**
 * Se encarga de gestionar las texturas.<br>
 * Al eliminar una textura se almacena en un Pool para poder eliminarlas por completo en algun momento en el que el recolector de basura no
 * comprometa el rendimiento del juego.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public final class TextureManager {
	
	private Context context;
	private HashMap<String, TextureRegion> textureRegions;
	private ArrayList<Texture> activeTextures;
	private Pool<Texture> removedTextures;
	private int texturesToLoad;
	
	/**
	 * Crea un TextureManager con una capacidad inicial de 16
	 * 
	 * @param context Context en el que se ejecuta el juego, necesario para poder cargar las texturas
	 */
	public TextureManager(Context context) {
		this(16, 16, context);
	}
	
	/**
	 * Crea un TextureManager
	 * 
	 * @param initialCapacityForTextures Capacidad inicial del TextureManager para Textures
	 * @param initialCapacityForTextureRegions Capacidad inicial del TextureManager para TextureRegions
	 * @param context Context en el que se ejecuta el juego, necesario para poder cargar las texturas
	 */
	public TextureManager(int initialCapacityForTextures, int initialCapacityForTextureRegions, Context context) {
		this.context = context;
		this.textureRegions = new HashMap<String, TextureRegion>((int) ((initialCapacityForTextureRegions / 0.75f) + 1));
		this.activeTextures = new ArrayList<Texture>(initialCapacityForTextures);
		this.removedTextures = new Pool<Texture>(initialCapacityForTextures);
		this.texturesToLoad = 0;
	}
	
	/**
	 * Agrega un TextureRegion al TextureManager. La clave especificada permitira identificar al TextureRegion agregado. Si se inserta
	 * posteriormente otro TextureRegion con la misma clave, se reemplazara al que hubiera, no puede haber 2 TextureRegion registrados con
	 * la misma clave.
	 * 
	 * @param key Clave que identifica al TextureRegion agregado
	 * @param textureRegion TextureRegion
	 * @return Devuelve el TextureRegion que hubiera previamente registrado con la clave especificada o null si no habia ninguno
	 */
	public TextureRegion addTextureRegion(String key, TextureRegion textureRegion) {
		addTexture(textureRegion.getTexture());
		return textureRegions.put(key, textureRegion);
	}
	
	/**
	 * Elimina el TextureRegion asociado a la clave especificada. El objeto Texture asociado al TextureRegion eliminado no sera eliminado
	 * del TextureManager.
	 * 
	 * @param key Clave
	 * @return TextureRegion eliminado o null si no hay ninguno asociado a dicha clave
	 */
	public TextureRegion removeTextureRegion(String key) {
		return textureRegions.remove(key);
	}
	
	/**
	 * Devuelve el TextureRegion asociado a la clave especificada.
	 * 
	 * @param key Clave
	 * @return TextureRegion registrado con la clave especificada o null si no hay ninguno asociado a dicha clave
	 */
	public TextureRegion getTextureRegion(String key) {
		return textureRegions.get(key);
	}
	
	/**
	 * Agrega todos los TextureRegion del TextureAtlas especificado al TextureManager, de forma que se podra acceder por su clave
	 * posteriormente.
	 * 
	 * @param textureAtlas TextureAtlas especificado
	 */
	public void addTextureAtlas(TextureAtlas textureAtlas) {
		Map<String, TextureRegion> atlasContent = textureAtlas.getTextureRegions();
		for (Map.Entry<String, TextureRegion> entry : atlasContent.entrySet()) {
			textureRegions.put(entry.getKey(), entry.getValue());
		}
		addTexture(textureAtlas.getSourceTexture());
	}
	
	/**
	 * Elimina todos los TextureRegion del TextureAtlas especificado al TextureManager. El objeto Texture asociado al TextureAtlas
	 * especificado no sera eliminado del TextureManager al llamar a este metodo.
	 * 
	 * @param textureAtlas TextureAtlas especificado
	 */
	public void removeTextureAtlas(TextureAtlas textureAtlas) {
		Map<String, TextureRegion> atlasContent = textureAtlas.getTextureRegions();
		for (Map.Entry<String, TextureRegion> entry : atlasContent.entrySet()) {
			textureRegions.remove(entry.getKey());
		}
	}
	
	/**
	 * Agrega la textura al TextureManager.
	 * 
	 * @param texture Texture que se va a agregar
	 * @return true si se ha agregado, false si ya se encontraba insertada otra textura con el mismo identificador (ruta del asset)
	 */
	public boolean addTexture(Texture texture) {
		int j;
		Texture aux;
		if (activeTextures.contains(texture)) {
			return false;
		}
		activeTextures.add(texture);
		j = activeTextures.size() - 1;
		for (int i = j - 1; i >= 0; i--) {
			if (activeTextures.get(i).compareTo(activeTextures.get(j)) == 0) {
				break;
			}
			if (activeTextures.get(i).compareTo(activeTextures.get(j)) > 0) {
				aux = activeTextures.set(i, activeTextures.get(j));
				activeTextures.set(j, aux);
				j--;
			} else {
				break;
			}
		}
		if (!texture.isLoaded()) {
			texturesToLoad++;
		}
		return true;
	}
	
	/**
	 * Agrega todas las texturas de una fuente al TextureManager
	 * 
	 * @param font Font cuyas texturas se van a registrar en el TextureManager
	 */
	public void addFontTextures(Font font) {
		SparseArray<Texture> texturePages = font.getTexturePages();
		for (int i = 0; i < texturePages.size(); i++) {
			addTexture(texturePages.valueAt(i));
		}
	}
	
	/**
	 * Elimina una textura de la colleccion de texturas activas.<br>
	 * La textura se traslada a un Pool, por tanto, se mantiene una referencia a ella para evitar que sea reciclada por el GC.<br>
	 * Para eliminar por completo todas las texturas, llamar a {@link #clearAll()}, o, en caso de querer eliminar solo las texturas
	 * almacenadas en el Pool de texturas eliminadas, llamar a {@link #clearRemovedTextures()}
	 * 
	 * @param texture Textura que se va a eliminar
	 */
	public void removeTexture(Texture texture) {
		Texture removedTexture;
		int mid;
		int low = 0;
		int high = activeTextures.size() - 1;
		while (low <= high) {
			mid = (low + high) / 2;
			if (activeTextures.get(mid).compareTo(texture) < 0) {
				low = mid + 1;
			} else if (activeTextures.get(mid).compareTo(texture) > 0) {
				high = mid - 1;
			} else {
				removedTexture = activeTextures.remove(mid);
				removedTextures.put(removedTexture);
				if (!removedTexture.isLoaded()) {
					texturesToLoad--;
				}
			}
		}
	}
	
	/**
	 * Elimina todas las texturas de la colleccion de texturas activas.<br>
	 * Las texturas se trasladan a un Pool, por tanto, se mantiene una referencia a ellas para evitar que sean recicladas por el GC.<br>
	 * Para eliminar por completo todas las texturas, llamar a {@link #clearAll()}, o, en caso de querer eliminar solo las texturas
	 * almacenadas en el Pool de texturas eliminadas, llamar a {@link #clearRemovedTextures()}
	 */
	public void removeAllTextures() {
		for (int i = activeTextures.size() - 1; i >= 0; i--) {
			removedTextures.put(activeTextures.remove(i));
		}
		texturesToLoad = 0;
	}
	
	/**
	 * Elimina por completo las texturas del Pool de texturas previamente eliminadas con {@link #removeTexture(Texture)} o
	 * {@link #removeAllTextures()}<br>
	 * NOTA: Este metodo debe ejecutarse en el hilo del GLRenderer
	 */
	public void clearRemovedTextures() {
		for (int i = removedTextures.size() - 1; i >= 0; i--) {
			removedTextures.get().delete();
		}
	}
	
	/**
	 * Elimina por completo todas las texturas y TextureRegions almacenadas en el TextureManager, dejandolo vacio.<br>
	 * NOTA: Este metodo debe ejecutarse en el hilo del GLRenderer
	 */
	public void clearAll() {
		int activeTexturesSize = activeTextures.size();
		int removedTexturesSize = removedTextures.size();
		int maxSize = Math.max(activeTexturesSize, removedTexturesSize);
		for (int i = maxSize - 1; i >= 0; i--) {
			if (i < activeTexturesSize) {
				activeTextures.remove(i).delete();
			}
			if (i < removedTexturesSize) {
				removedTextures.get().delete();
			}
		}
		texturesToLoad = 0;
		textureRegions.clear();
	}
	
	/**
	 * Carga todas las texturas que no estuvieran cargadas.<br>
	 * NOTA: Este metodo debe ejecutarse en el hilo del GLRenderer
	 */
	public void loadTextures() {
		if (texturesToLoad > 0) {
			for (int i = 0; i < activeTextures.size(); i++) {
				if (!activeTextures.get(i).isLoaded()) {
					activeTextures.get(i).loadTexture(context);
					texturesToLoad--;
					if (texturesToLoad == 0) {
						break;
					}
				}
			}
			texturesToLoad = 0;
		}
	}
	
	/**
	 * Carga todas las texturas.<br>
	 * NOTA: Este metodo debe ejecutarse en el hilo del GLRenderer
	 */
	public void loadAllTextures() {
		for (int i = 0; i < activeTextures.size(); i++) {
			activeTextures.get(i).loadTexture(context);
		}
		texturesToLoad = 0;
	}
	
}
