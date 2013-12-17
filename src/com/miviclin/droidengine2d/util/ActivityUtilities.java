package com.miviclin.droidengine2d.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ConfigurationInfo;
import android.media.AudioManager;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.miviclin.droidengine2d.BuildConfig;

/**
 * ActivityUtilities.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ActivityUtilities {

	/**
	 * Checks if the device supports OpenGL ES 2.0.<br>
	 * NOTE: If the app is running on the Android emulator, this method returns always true.
	 * 
	 * @param activity Activity.
	 * @return true if OpenGL ES 2.0 is supported, false otherwise
	 */
	public static boolean detectOpenGLES20(Activity activity) {
		ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		if (BuildConfig.DEBUG) {
			if (Build.FINGERPRINT.startsWith("generic")) {
				return true;
			}
		}
		return (info.reqGlEsVersion >= 0x20000);
	}

	/**
	 * Changes the orientation of the Activity to PORTRAIT.
	 * 
	 * @param activity Activity.
	 */
	public static void setOrientationToPortrait(Activity activity) {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/**
	 * Changes the orientation of the Activity to LANDSCAPE.
	 * 
	 * @param activity Activity.
	 */
	public static void setOrientationToLandscape(Activity activity) {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * Hides the title of the Activity.
	 * 
	 * @param activity Activity.
	 */
	public static void hideTitle(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * Sets the Activity to full screen.
	 * 
	 * @param activity Activity.
	 */
	public static void setToFullscreen(Activity activity) {
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}

	/**
	 * Sets the Activity to keep the screen on as long as it is visible to the user.
	 * 
	 * @param activity Activity.
	 */
	public static void keepScreenOn(Activity activity) {
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/**
	 * Sets the Activity to allow the hardware buttons to control the volume of music playback.
	 * 
	 * @param activity Activity.
	 */
	public static void controlMusicPlayBackStream(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}

}
