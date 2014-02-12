package com.miviclin.droidengine2d.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.view.Display;
import android.view.Surface;

import com.miviclin.droidengine2d.BuildConfig;

/**
 * ActivityUtilities.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public final class ActivityUtilities {

	/**
	 * Private constructor to prevent the instantiation of this utility class.
	 */
	private ActivityUtilities() {
		super();
	}

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
	 * Calculates the default orientation of the device.<br>
	 * The default orientation of most phones is portrait, and the default orientation of most tablets is landscape.
	 * 
	 * @param activity Activity.
	 * @return {@link Configuration#ORIENTATION_PORTRAIT} or {@link Configuration#ORIENTATION_LANDSCAPE}
	 */
	public static int getDefaultOrientationOfDevice(Activity activity) {
		Configuration configuration = activity.getResources().getConfiguration();
		boolean orientationLandscape = (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE);

		Display display = activity.getWindowManager().getDefaultDisplay();
		int rotation = display.getRotation();
		boolean parallelToDefaultVerticalAxis = (rotation == Surface.ROTATION_0) || (rotation == Surface.ROTATION_180);

		boolean defaultOrientationLandscape = (parallelToDefaultVerticalAxis && orientationLandscape) ||
				(!parallelToDefaultVerticalAxis && !orientationLandscape);

		if (defaultOrientationLandscape) {
			return Configuration.ORIENTATION_LANDSCAPE;
		}
		return Configuration.ORIENTATION_PORTRAIT;
	}

}
