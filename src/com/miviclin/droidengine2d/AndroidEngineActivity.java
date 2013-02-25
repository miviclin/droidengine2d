package com.miviclin.droidengine2d;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;

public class AndroidEngineActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_engine);
	}
	
	/**
	 * Detecta si el dispositivo soporta OpenGL ES 2.0
	 * 
	 * @return true si lo soporta, false en caso contrario
	 */
	public boolean detectOpenGLES20() {
		ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
		ConfigurationInfo info = am.getDeviceConfigurationInfo();
		return (info.reqGlEsVersion >= 0x20000);
	}
	
}
