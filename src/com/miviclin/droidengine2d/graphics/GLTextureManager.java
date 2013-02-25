package com.miviclin.droidengine2d.graphics;

import java.util.ArrayList;

import android.content.Context;

import com.miviclin.droidengine2d.util.Pool;

/**
 * Se encarga de gestionar las texturas.<br>
 * Al eliminar una textura se almacena en un Pool para poder eliminarlas por completo en algun momento en el que el recolector de basura no
 * comprometa el rendimiento del juego.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public final class GLTextureManager {
	
	private Context context;
	private ArrayList<GLTexture> activeTextures;
	private Pool<GLTexture> removedTextures;
	private int texturesToLoad;
	
	/**
	 * Crea un GLTextureManager con una capacidad inicial de 16
	 * 
	 * @param context Context en el que se ejecuta el juego, necesario para poder cargar las texturas
	 */
	public GLTextureManager(Context context) {
		this(16, context);
	}
	
	/**
	 * Crea un GLTextureManager
	 * 
	 * @param initialCapacity Capacidad inicial del GLTextureManager
	 * @param context Context en el que se ejecuta el juego, necesario para poder cargar las texturas
	 */
	public GLTextureManager(int initialCapacity, Context context) {
		this.context = context;
		this.activeTextures = new ArrayList<GLTexture>(initialCapacity);
		this.removedTextures = new Pool<GLTexture>(initialCapacity);
		this.texturesToLoad = 0;
	}
	
	/**
	 * Agrega la textura al GLTextureManager.
	 * 
	 * @param texture GLTexture que se va a agregar
	 * @return true si se ha agregado, false si ya se encontraba insertada otra textura con el mismo identificador (ruta del asset)
	 */
	public boolean addTexture(GLTexture texture) {
		int j;
		GLTexture aux;
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
	 * Elimina una textura de la colleccion de texturas activas.<br>
	 * La textura se traslada a un Pool, por tanto, se mantiene una referencia a ella para evitar que sea reciclada por el GC.<br>
	 * Para eliminar por completo todas las texturas, llamar a {@link #clearAll()}, o, en caso de querer eliminar solo las texturas
	 * almacenadas en el Pool de texturas eliminadas, llamar a {@link #clearRemovedTextures()}
	 * 
	 * @param texture Textura que se va a eliminar
	 */
	public void removeTexture(GLTexture texture) {
		GLTexture removedTexture;
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
	 * Elimina por completo las texturas del Pool de texturas previamente eliminadas con {@link #removeTexture(GLTexture)} o
	 * {@link #removeAllTextures()}<br>
	 * NOTA: Este metodo debe ejecutarse en el hilo del GLRenderer
	 */
	public void clearRemovedTextures() {
		for (int i = removedTextures.size() - 1; i >= 0; i--) {
			removedTextures.get().delete();
		}
	}
	
	/**
	 * Elimina por completo todas las texturas almacenadas en el GLTextureManager.<br>
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
	
}
