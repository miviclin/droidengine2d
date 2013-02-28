package com.miviclin.droidengine2d.audio;

import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

import com.miviclin.droidengine2d.resources.AssetsLoader;

/**
 * Permite cargar sonidos en memoria y reproducirlos.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class SoundManager {
	
	private SoundPool soundPool;
	private HashMap<String, IDWrapper> soundMap;
	
	/**
	 * Constructor.
	 * 
	 * @param maxStreams Maximo de sonidos que se permite reproducir simultaneamente
	 * @param initialCapacity Numero de sonidos que se planea almacenar. Nota: si la capacidad inicial seleccionada es menor que la cantidad
	 *            de sonidos que realmente se va a almacenar, la coleccion se redimensionara en tiempo de ejecucion.
	 */
	public SoundManager(int maxStreams, int initialCapacity) {
		this.soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
		this.soundMap = new HashMap<String, IDWrapper>((int) ((initialCapacity / 0.75) + 1));
	}
	
	/**
	 * Carga un sonido en la coleccion de sonidos
	 * 
	 * @param context Context
	 * @param path Ruta del sonido, relativa a la carpeta de assets
	 */
	public void loadSound(Context context, String path) {
		int soundID;
		AssetFileDescriptor descriptor = AssetsLoader.getAssetFileDescriptor(context, path);
		if (soundPool != null) {
			soundID = soundPool.load(descriptor, 1);
		} else {
			throw new IllegalStateException("reset(...) must be called before loadSound(...)");
		}
		soundMap.put(path, new IDWrapper(soundID));
	}
	
	/**
	 * Libera un sonido que ya no se va a utilizar mas
	 * 
	 * @param path Ruta del sonido, relativa a la carpeta de assets
	 */
	public void releaseSound(String path) {
		IDWrapper soundID = soundMap.get(path);
		if (soundID != null) {
			soundPool.unload(soundID.getID());
			soundMap.remove(path);
		}
	}
	
	/**
	 * Libera todos los sonidos almacenados y demas recursos utilizados por este objeto. Esta funcion deberia llamarse cuando no vamos a
	 * reproducir mas sonidos.
	 */
	public void release() {
		soundPool.release();
		soundPool = null;
		soundMap.clear();
	}
	
	/**
	 * Libera los recursos (llama a la funcion {@link #release()}) y reinicia este GestorSonidos con los parametros especificados.
	 * 
	 * @param maxStreams Maximo de sonidos que se permite reproducir simultaneamente
	 * @param capacity Numero de sonidos que se planea almacenar. Nota: si la capacidad inicial seleccionada es menor que la cantidad de
	 *            sonidos que realmente se va a almacenar, la coleccion se redimensionara en tiempo de ejecucion.
	 */
	public void reset(int maxStreams, int capacity) {
		release();
		this.soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
		this.soundMap = new HashMap<String, IDWrapper>((int) ((capacity / 0.75) + 1));
	}
	
	/**
	 * Reproduce el sonido especificado.<br>
	 * El sonido debe haber sido cargado previamente mediante {@link #loadSound(Context, String)}
	 * 
	 * @param path Ruta del sonido, relativa a la carpeta de assets
	 */
	public void playSound(String path) {
		IDWrapper soundID = soundMap.get(path);
		if (soundID != null) {
			soundPool.play(soundID.getID(), 1, 1, 0, 0, 1);
		}
	}
	
	/**
	 * Actua como wrapper para el ID del sonido, para poder insertar IDs en el HashMap de sonidos, ya que este no permite insertar tipos
	 * primitivos. Utilizamos esta clase en lugar de Integer para ahorrar un poco de memoria.
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	private static class IDWrapper {
		
		private int id;
		
		public IDWrapper(int id) {
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
	}
	
}
