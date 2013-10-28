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
 * Clase de utilidad que contiene metodos que simplifican tareas relacionadas con las Activities.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ActivityUtilities {

	/**
	 * Detecta si el dispositivo soporta OpenGL ES 2.0.<br>
	 * NOTA: Si el dispositivo es un emulador de android, se asume que soporta OpenGL ES 2.0 por lo que devolvera
	 * siempre true.
	 * 
	 * @param activity Activity sobre la que opera este metodo
	 * @return true si lo soporta, false en caso contrario
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
	 * Cambia la orientacion de la Activity a PORTRAIT. Es posible que la Activity se reinicie al llamar a este metodo.
	 * 
	 * @param activity Activity sobre la que opera este metodo
	 */
	public static void setOrientationToPortrait(Activity activity) {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/**
	 * Cambia la orientacion de la Activity a LANDSCAPE. Es posible que la Activity se reinicie al llamar a este metodo.
	 * 
	 * @param activity Activity sobre la que opera este metodo
	 */
	public static void setOrientationToLandscape(Activity activity) {
		activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	}

	/**
	 * Oculta el titulo de la Activity
	 * 
	 * @param activity Activity sobre la que opera este metodo
	 */
	public static void hideTitle(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	/**
	 * Configura la Activity para que ocupe toda la pantalla, ocultando la barra de notificaciones, etc
	 * 
	 * @param activity Activity sobre la que opera este metodo
	 */
	public static void setToFullscreen(Activity activity) {
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}

	/**
	 * Configura la Activity para que mientras sea visible, la pantalla se mantenga encendida
	 * 
	 * @param activity Activity sobre la que opera este metodo
	 */
	public static void keepScreenOn(Activity activity) {
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}

	/**
	 * Sugiere que mientras la Activity sea visible, los botones hardware de control de volumen del dispositivo puedan
	 * controlar el volumen del stream de musica
	 * 
	 * @param activity Activity sobre la que opera este metodo
	 */
	public static void controlMusicPlayBackStream(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
	}

}
