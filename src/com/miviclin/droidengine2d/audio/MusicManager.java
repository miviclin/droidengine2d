package com.miviclin.droidengine2d.audio;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
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

	private MediaPlayer player;
	private boolean prepared;
	private boolean loaded;
	private float volume;

	/**
	 * Constructor
	 */
	public MusicManager() {
		this.player = null;
		this.prepared = false;
		this.loaded = false;
		this.volume = -1.0f;
	}

	/**
	 * Carga una cancion a partir de una ruta y la prepara para reproducir. Por defecto se activa la opcion de 'loop'
	 * 
	 * @param context
	 * @param nombreArchivo Ruta relativa a la carpeta de assets
	 */
	public void loadMusic(Context context, String path) {
		loadMusic(context, path, AudioManager.STREAM_MUSIC);
	}

	/**
	 * Carga una cancion a partir de una ruta y la prepara para reproducir. Por defecto se activa la opcion de 'loop'
	 * 
	 * @param context
	 * @param nombreArchivo Ruta relativa a la carpeta de assets
	 * @param streamType Tipo de stream de audio. Utilizar {@link AudioManager#STREAM_ALARM},
	 *            {@link AudioManager#STREAM_DTMF}, {@link AudioManager#STREAM_MUSIC},
	 *            {@link AudioManager#STREAM_NOTIFICATION}, {@link AudioManager#STREAM_RING},
	 *            {@link AudioManager#STREAM_SYSTEM}, o {@link AudioManager#STREAM_VOICE_CALL}
	 */
	public void loadMusic(Context context, String path, int streamType) {
		if (path.matches("[a-z]+://.+")) {
			loadExternalMusic(path, streamType);
		} else {
			loadMusicFromAssets(context, path, streamType);
		}
	}

	/**
	 * Carga una cancion a partir de una ruta y la prepara para reproducir. Por defecto se activa la opcion de 'loop'
	 * 
	 * @param nombreArchivo Ruta relativa a la carpeta de assets
	 */
	public void loadExternalMusic(String path) {
		loadExternalMusic(path, AudioManager.STREAM_MUSIC);
	}

	/**
	 * Carga una cancion a partir de una ruta y la prepara para reproducir. Por defecto se activa la opcion de 'loop'
	 * 
	 * @param nombreArchivo Ruta relativa a la carpeta de assets
	 * @param streamType Tipo de stream de audio. Utilizar {@link AudioManager#STREAM_ALARM},
	 *            {@link AudioManager#STREAM_DTMF}, {@link AudioManager#STREAM_MUSIC},
	 *            {@link AudioManager#STREAM_NOTIFICATION}, {@link AudioManager#STREAM_RING},
	 *            {@link AudioManager#STREAM_SYSTEM}, o {@link AudioManager#STREAM_VOICE_CALL}
	 */
	public void loadExternalMusic(String path, int streamType) {
		player = new MediaPlayer();
		try {
			player.setDataSource(path);
			player.setAudioStreamType(streamType);
			player.prepare();
			prepared = true;
			player.setLooping(true);
			loaded = true;
			setVolume(1.0f);
		} catch (Exception e) {
			player = null;
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
		loadMusicFromAssets(context, path, AudioManager.STREAM_MUSIC);
	}

	/**
	 * Carga una cancion a partir de un asset y la prepara para reproducir. Por defecto se activa la opcion de 'loop'
	 * 
	 * @param context Context
	 * @param path Ruta relativa a la carpeta de assets
	 * @param streamType Tipo de stream de audio. Utilizar {@link AudioManager#STREAM_ALARM},
	 *            {@link AudioManager#STREAM_DTMF}, {@link AudioManager#STREAM_MUSIC},
	 *            {@link AudioManager#STREAM_NOTIFICATION}, {@link AudioManager#STREAM_RING},
	 *            {@link AudioManager#STREAM_SYSTEM}, o {@link AudioManager#STREAM_VOICE_CALL}
	 */
	public void loadMusicFromAssets(Context context, String path, int streamType) {
		AssetFileDescriptor descriptor = AssetsLoader.getAssetFileDescriptor(context, path);
		player = new MediaPlayer();
		try {
			player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			descriptor.close();
			player.setAudioStreamType(streamType);
			player.prepare();
			prepared = true;
			player.setLooping(true);
			loaded = true;
			setVolume(1.0f);
		} catch (Exception e) {
			player = null;
			prepared = false;
			throw new RuntimeException(e.getMessage() + "");
		}
	}

	/**
	 * Reproduce la cancion cargada. Si no habia ninguna cancion cargada, no hace nada
	 */
	public void play() {
		if (loaded) {
			if (player.isPlaying()) {
				return;
			}
			try {
				synchronized (this) {
					if (!prepared) {
						player.prepare();
					}
					player.start();
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
			if (player.isPlaying()) {
				player.pause();
			}
		}
	}

	/**
	 * Para la reproduccion
	 */
	public void stop() {
		if (loaded) {
			player.stop();
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
		return player.isPlaying();
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
	 * Para el reproductor y libera los recursos que estuviera usando. Esta funcion deberia ser llamada cuando no se
	 * vaya a utilizar mas el reproductor.
	 */
	public void release() {
		if (loaded) {
			if (player.isPlaying()) {
				player.stop();
			}
			player.release();
		}
	}

	/**
	 * Comprueba si la reproduccion esta en modo 'loop'
	 * 
	 * @return true/false
	 */
	public boolean isLooping() {
		return player.isLooping();
	}

	/**
	 * Asigna el modo de reproduccion 'loop'
	 * 
	 * @param loop true/false
	 */
	public void setLooping(boolean loop) {
		player.setLooping(loop);
	}

	/**
	 * Devuelve el volumen de reproduccion
	 * 
	 * @return volume Volumen. Valor entre 0.0f y 1.0f
	 */
	public float getVolume() {
		if (player != null) {
			return volume;
		}
		return -1;
	}

	/**
	 * Asigna el volumen de reproduccion
	 * 
	 * @param volume Volumen. Valor entre 0.0f y 1.0f
	 */
	public void setVolume(float volume) {
		if (player != null) {
			this.volume = volume;
			player.setVolume(volume, volume);
		}
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
