package com.miviclin.droidengine2d.audio;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.miviclin.droidengine2d.resources.AssetsLoader;

/**
 * Permite reproducir musica de fondo.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class MusicManager implements OnCompletionListener {
	
	private MediaPlayer mediaPlayer;
	private boolean prepared;
	private boolean loaded;
	
	/**
	 * Constructor
	 */
	public MusicManager() {
		this.mediaPlayer = null;
		this.prepared = false;
		this.loaded = false;
	}
	
	/**
	 * Carga una cancion a partir de una ruta y la prepara para reproducir. Por defecto se activa la opcion de 'loop'
	 * 
	 * @param context
	 * @param nombreArchivo Ruta relativa a la carpeta de assets
	 */
	public void loadMusic(Context context, String path) {
		if (path.matches("[a-z]+://.+")) {
			loadExternalMusic(path);
		} else {
			loadMusicFromAssets(context, path);
		}
	}
	
	/**
	 * Carga una cancion a partir de una ruta y la prepara para reproducir. Por defecto se activa la opcion de 'loop'
	 * 
	 * @param nombreArchivo Ruta relativa a la carpeta de assets
	 */
	public void loadExternalMusic(String path) {
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(path);
			mediaPlayer.prepare();
			prepared = true;
			mediaPlayer.setLooping(true);
			loaded = true;
		} catch (Exception e) {
			mediaPlayer = null;
			prepared = false;
			throw new RuntimeException(e.getMessage() + "");
		}
	}
	
	/**
	 * Carga una cancion a partir de un asset y la prepara para reproducir. Por defecto se activa la opcion de 'loop'
	 * 
	 * @param context Context
	 * @param path Ruta relativa a la carpeta de assets
	 */
	public void loadMusicFromAssets(Context context, String path) {
		AssetFileDescriptor descriptor = AssetsLoader.getAssetFileDescriptor(context, path);
		mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			descriptor.close();
			mediaPlayer.prepare();
			prepared = true;
			mediaPlayer.setLooping(true);
			loaded = true;
		} catch (Exception e) {
			mediaPlayer = null;
			prepared = false;
			throw new RuntimeException(e.getMessage() + "");
		}
	}
	
	/**
	 * Reproduce la cancion cargada. Si no habia ninguna cancion cargada, no hace nada
	 */
	public void play() {
		if (loaded) {
			if (mediaPlayer.isPlaying()) {
				return;
			}
			try {
				synchronized (this) {
					if (!prepared) {
						mediaPlayer.prepare();
					}
					mediaPlayer.start();
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Pausa la reproduccion
	 */
	public void pause() {
		if (loaded) {
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			}
		}
	}
	
	/**
	 * Para la reproduccion
	 */
	public void stop() {
		if (loaded) {
			mediaPlayer.stop();
			synchronized (this) {
				prepared = false;
			}
		}
	}
	
	/**
	 * Comprueba si se esta reproduciendo una cancion
	 * 
	 * @return true/false
	 */
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}
	
	/**
	 * Comprueba si el reproductor esta parado
	 * 
	 * @return true/false
	 */
	public boolean isStopped() {
		return !prepared;
	}
	
	/**
	 * Para el reproductor y libera los recursos que estuviera usando. Esta funcion deberia ser llamada cuando no se vaya a utilizar mas el
	 * reproductor.
	 */
	public void release() {
		if (loaded) {
			if (mediaPlayer.isPlaying())
				mediaPlayer.stop();
			mediaPlayer.release();
		}
	}
	
	/**
	 * Comprueba si la reproduccion esta en modo 'loop'
	 * 
	 * @return true/false
	 */
	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}
	
	/**
	 * Asigna el modo de reproduccion 'loop'
	 * 
	 * @param loop true/false
	 */
	public void setLooping(boolean loop) {
		mediaPlayer.setLooping(loop);
	}
	
	/**
	 * Asigna el volumen de reproduccion
	 * 
	 * @param volume Volumen. Valor entre 0.0f y 1.0f
	 */
	public void setVolume(float volume) {
		mediaPlayer.setVolume(volume, volume);
	}
	
	@Override
	public void onCompletion(MediaPlayer player) {
		synchronized (this) {
			prepared = false;
		}
	}
	
	/**
	 * Comprueba si MusicManager es capaz de reproducir el archivo especificado
	 * 
	 * @param context Context
	 * @param ruta Ruta del archivo
	 * @return true si lo soporta, false en caso contrario (o si la ruta especificada no es valida)
	 */
	public static boolean checkFileCompatibility(Context context, String ruta) {
		AssetFileDescriptor descriptor;
		MediaPlayer mp = new MediaPlayer();
		if (ruta.matches("[a-z]+://.+")) {
			try {
				mp.setDataSource(ruta);
				mp.prepare();
			} catch (Exception e) {
				return false;
			}
		} else {
			descriptor = AssetsLoader.getAssetFileDescriptor(context, ruta);
			try {
				mp.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
				descriptor.close();
				mp.prepare();
			} catch (Exception e) {
				return false;
			}
		}
		mp.release();
		return true;
	}
	
}
