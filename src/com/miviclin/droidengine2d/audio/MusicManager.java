package com.miviclin.droidengine2d.audio;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.miviclin.droidengine2d.resources.AssetsLoader;

/**
 * Manages background music streaming.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class MusicManager implements OnCompletionListener {

	public static final float MIN_VOLUME = 0.0f;
	public static final float MAX_VOLUME = 1.0f;

	private MediaPlayer player;
	private boolean prepared;
	private boolean loaded;
	private float volume;

	/**
	 * Creates a new MusicManager.
	 */
	public MusicManager() {
		this.player = null;
		this.prepared = false;
		this.loaded = false;
		this.volume = -1.0f;
	}

	/**
	 * Loads music from the specified file and prepares the MediaPlayer for playing it. Looping is enabled by default.
	 * 
	 * @param context Context.
	 * @param path File path.
	 */
	public void loadMusic(Context context, String path) {
		loadMusic(context, path, AudioManager.STREAM_MUSIC);
	}

	/**
	 * Loads music from the specified file and prepares the MediaPlayer for playing it. Looping is enabled by default.
	 * 
	 * @param context Context.
	 * @param path File path.
	 * @param streamType The type of the audio stream. Use {@link AudioManager#STREAM_ALARM},
	 *            {@link AudioManager#STREAM_DTMF}, {@link AudioManager#STREAM_MUSIC},
	 *            {@link AudioManager#STREAM_NOTIFICATION}, {@link AudioManager#STREAM_RING},
	 *            {@link AudioManager#STREAM_SYSTEM}, or {@link AudioManager#STREAM_VOICE_CALL}.
	 */
	public void loadMusic(Context context, String path, int streamType) {
		if (path.matches("[a-z]+://.+")) {
			loadExternalMusic(path, streamType);
		} else {
			loadMusicFromAssets(context, path, streamType);
		}
	}

	/**
	 * Loads music from the specified external file and prepares the MediaPlayer for playing it. Looping is enabled by
	 * default.
	 * 
	 * @param path External file path.
	 */
	public void loadExternalMusic(String path) {
		loadExternalMusic(path, AudioManager.STREAM_MUSIC);
	}

	/**
	 * Loads music from the specified external file and prepares the MediaPlayer for playing it. Looping is enabled by
	 * default.
	 * 
	 * @param path External file path.
	 * @param streamType The type of the audio stream. Use {@link AudioManager#STREAM_ALARM},
	 *            {@link AudioManager#STREAM_DTMF}, {@link AudioManager#STREAM_MUSIC},
	 *            {@link AudioManager#STREAM_NOTIFICATION}, {@link AudioManager#STREAM_RING},
	 *            {@link AudioManager#STREAM_SYSTEM}, or {@link AudioManager#STREAM_VOICE_CALL}
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
	 * Loads music from the specified asset file and prepares the MediaPlayer for playing it. Looping is enabled by
	 * default.
	 * 
	 * @param context Context.
	 * @param path File path. Relative to the assets folder.
	 */
	public void loadMusicFromAssets(Context context, String path) {
		loadMusicFromAssets(context, path, AudioManager.STREAM_MUSIC);
	}

	/**
	 * Loads music from the specified asset file and prepares the MediaPlayer for playing it. Looping is enabled by
	 * default.
	 * 
	 * @param context Context.
	 * @param path path File path. Relative to the assets folder.
	 * @param streamType The type of the audio stream. Use {@link AudioManager#STREAM_ALARM},
	 *            {@link AudioManager#STREAM_DTMF}, {@link AudioManager#STREAM_MUSIC},
	 *            {@link AudioManager#STREAM_NOTIFICATION}, {@link AudioManager#STREAM_RING},
	 *            {@link AudioManager#STREAM_SYSTEM}, or {@link AudioManager#STREAM_VOICE_CALL}
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
	 * Plays the previously loaded song.<br>
	 * If there is not any song loaded, this method does nothing.
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
	 * Pauses the music playback.
	 */
	public void pause() {
		if (loaded) {
			if (player.isPlaying()) {
				player.pause();
			}
		}
	}

	/**
	 * Stops the music playback.
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
	 * Returns true if there is a song being played.
	 * 
	 * @return true if a song is being played, false otherwise
	 */
	public boolean isPlaying() {
		return player.isPlaying();
	}

	/**
	 * Returns true if the playback is stopped.
	 * 
	 * @return true if the playback is stopped, false otherwise
	 */
	public boolean isStopped() {
		return !prepared;
	}

	/**
	 * Releases resources.
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
	 * Returns true if looping is enabled.
	 * 
	 * @return true if looping is enabled, false otherwise
	 */
	public boolean isLooping() {
		return player.isLooping();
	}

	/**
	 * Enables or disables looping.
	 * 
	 * @param looping true to enable looping, false to disable it
	 */
	public void setLooping(boolean looping) {
		player.setLooping(looping);
	}

	/**
	 * Returns the volume of the song being played. If there is no song loaded, this method will return -1.
	 * 
	 * @return Value between 0.0f and 1.0f. Or -1 if there is no song loaded.
	 */
	public float getVolume() {
		if (player != null) {
			return volume;
		}
		return -1;
	}

	/**
	 * Sets the volume of the loaded song.
	 * 
	 * @param volume Volume. Value between 0.0f and 1.0f.
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
	 * Checks if MusicManager supports playing the specified file without loading it.
	 * 
	 * @param context Context.
	 * @param ruta File path.
	 * @return true if the file can be played with MusicManager, false otherwise. If the specified path is not a valid
	 *         path, this method returns false.
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
