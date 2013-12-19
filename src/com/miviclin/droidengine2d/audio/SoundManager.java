package com.miviclin.droidengine2d.audio;

import java.util.HashMap;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

import com.miviclin.droidengine2d.resources.AssetsLoader;

/**
 * Manages sounds and allows playing them.<br>
 * All sounds will be loaded in memory.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class SoundManager {

	private SoundPool soundPool;
	private HashMap<String, IdWrapper> soundMap;

	/**
	 * Creates a SoundManager.
	 * 
	 * @param maxStreams Max number of sounds that can be played at the same time.
	 * @param initialCapacity Initial capacity of the sound pool. It will be resized if more sounds are loaded after the
	 *            specified capacity is reached.
	 */
	public SoundManager(int maxStreams, int initialCapacity) {
		this.soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
		this.soundMap = new HashMap<String, IdWrapper>((int) ((initialCapacity / 0.75) + 1));
	}

	/**
	 * Loads a sound from the specified file. The sound will be stored in memory.
	 * 
	 * @param context Context.
	 * @param path File path. Relative to the assets folder.
	 */
	public void loadSound(Context context, String path) {
		int soundId;
		AssetFileDescriptor descriptor = AssetsLoader.getAssetFileDescriptor(context, path);
		if (soundPool != null) {
			soundId = soundPool.load(descriptor, 1);
		} else {
			throw new IllegalStateException("reset(...) must be called before loadSound(...)");
		}
		soundMap.put(path, new IdWrapper(soundId));
	}

	/**
	 * Releases a sound that is not going to be used anymore.
	 * 
	 * @param path File path. Relative to the assets folder. This is the path that was specified when the sound was
	 *            loaded.
	 */
	public void releaseSound(String path) {
		IdWrapper soundId = soundMap.get(path);
		if (soundId != null) {
			soundPool.unload(soundId.getId());
			soundMap.remove(path);
		}
	}

	/**
	 * Releases all sounds and resources. This method should be called when the SoundManager is not needed anymore.
	 */
	public void release() {
		soundPool.release();
		soundPool = null;
		soundMap.clear();
	}

	/**
	 * Releases all resources (calls {@link #release()}) and resets this SoundManager with the specified parameters.
	 * 
	 * @param maxStreams Max number of sounds that can be played at the same time.
	 * @param initialCapacity Initial capacity of the sound pool. It will be resized if more sounds are loaded after the
	 *            specified capacity is reached.
	 */
	public void reset(int maxStreams, int capacity) {
		release();
		this.soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
		this.soundMap = new HashMap<String, IdWrapper>((int) ((capacity / 0.75) + 1));
	}

	/**
	 * Plays the specified sound.<br>
	 * The spefied sound must have been previously loaded with {@link #loadSound(Context, String)}.
	 * 
	 * @param path File path. Relative to the assets folder. This is the path that was specified when the sound was
	 *            loaded.
	 */
	public void playSound(String path) {
		IdWrapper soundId = soundMap.get(path);
		if (soundId != null) {
			soundPool.play(soundId.getId(), 1, 1, 0, 0, 1);
		}
	}

	/**
	 * IDWrapper.
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	private static class IdWrapper {

		private int id;

		public IdWrapper(int id) {
			this.id = id;
		}

		public int getId() {
			return id;
		}
	}

}
