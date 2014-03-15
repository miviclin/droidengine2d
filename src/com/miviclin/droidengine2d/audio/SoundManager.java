/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
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
	private HashMap<String, Integer> soundMap;

	/**
	 * Creates a SoundManager.
	 * 
	 * @param maxStreams Max number of sounds that can be played at the same time.
	 * @param initialCapacity Initial capacity of the sound pool. It will be resized if more sounds are loaded after the
	 *            specified capacity is reached.
	 */
	public SoundManager(int maxStreams, int initialCapacity) {
		initialize(maxStreams, initialCapacity);
	}

	/**
	 * Releases all resources (calls {@link #release()}) and resets this SoundManager with the specified parameters.
	 * 
	 * @param maxStreams Max number of sounds that can be played at the same time.
	 * @param initialCapacity Initial capacity of the sound pool. It will be resized if more sounds are loaded after the
	 *            specified capacity is reached.
	 */
	public void reset(int maxStreams, int initialCapacity) {
		release();
		initialize(maxStreams, initialCapacity);
	}

	/**
	 * Initializes this SoundManager with the specified parameters.
	 * 
	 * @param maxStreams Max number of sounds that can be played at the same time.
	 * @param initialCapacity Initial capacity of the sound pool. It will be resized if more sounds are loaded after the
	 *            specified capacity is reached.
	 */
	private void initialize(int maxStreams, int capacity) {
		this.soundPool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
		this.soundMap = new HashMap<String, Integer>((int) ((capacity / 0.75) + 1));
	}

	/**
	 * Loads a sound from the specified file. The sound will be stored in memory.
	 * 
	 * @param context Context.
	 * @param path File path. Relative to the assets folder.
	 */
	public void loadSound(Context context, String path) {
		AssetFileDescriptor descriptor = AssetsLoader.getAssetFileDescriptor(context, path);
		if (soundPool != null) {
			int soundId = soundPool.load(descriptor, 1);
			soundMap.put(path, soundId);
		} else {
			throw new IllegalStateException("reset(...) must be called before loadSound(...)");
		}
	}

	/**
	 * Releases a sound that is not going to be used anymore.
	 * 
	 * @param path File path. Relative to the assets folder. This is the path that was specified when the sound was
	 *            loaded.
	 */
	public void releaseSound(String path) {
		Integer soundId = soundMap.get(path);
		if (soundId != null) {
			soundPool.unload(soundId);
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
	 * Plays the specified sound.<br>
	 * The spefied sound must have been previously loaded with {@link #loadSound(Context, String)}.
	 * 
	 * @param path File path. Relative to the assets folder. This is the path that was specified when the sound was
	 *            loaded.
	 */
	public void playSound(String path) {
		Integer soundId = soundMap.get(path);
		if (soundId != null) {
			soundPool.play(soundId, 1, 1, 0, 0, 1);
		}
	}

}
