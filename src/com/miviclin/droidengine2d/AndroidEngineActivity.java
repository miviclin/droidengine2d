package com.miviclin.droidengine2d;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AndroidEngineActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_engine);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.android_engine, menu);
		return true;
	}
	
}
