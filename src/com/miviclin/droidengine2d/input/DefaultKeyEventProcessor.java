package com.miviclin.droidengine2d.input;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.view.KeyEvent;

import com.miviclin.droidengine2d.util.time.TimeCounter;

/**
 * Default KeyEventProcessor. Processes the back key and the volume keys.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class DefaultKeyEventProcessor implements KeyEventProcessor {

	private static final int VOLUME_ADJUST_DELAY_MS = 120;

	private Activity activity;
	private TimeCounter timeSinceLastVolumeAdjustment;

	/**
	 * Creates a new DefaultKeyEventProcessor.
	 * 
	 * @param activity Activity.
	 */
	public DefaultKeyEventProcessor(Activity activity) {
		this.activity = activity;
		this.timeSinceLastVolumeAdjustment = new TimeCounter();
	}

	@Override
	public void processKeyEvent(KeyEventInfo event) {
		int keyCode = event.getKeyCode();
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			activity.finish();
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
			adjustVolume(AudioManager.ADJUST_RAISE);
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			adjustVolume(AudioManager.ADJUST_LOWER);
		}
	}

	/**
	 * Returns the Activity.
	 * 
	 * @return Activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Adjusts the volume of the current audio stream of the Activity.
	 * 
	 * @param direction The direction to adjust the volume. One of {@link AudioManager#ADJUST_LOWER},
	 *            {@link AudioManager#ADJUST_RAISE}, or {@link AudioManager#ADJUST_SAME}.
	 */
	private void adjustVolume(int direction) {
		timeSinceLastVolumeAdjustment.update();
		if (timeSinceLastVolumeAdjustment.getMilliseconds() < VOLUME_ADJUST_DELAY_MS) {
			return;
		}
		AudioManager audio = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		audio.adjustVolume(direction, AudioManager.FLAG_SHOW_UI);
		timeSinceLastVolumeAdjustment.reset();
	}

}
