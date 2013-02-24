package com.miviclin.droidengine2d.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.miviclin.droidengine2d.ui.GameView;

public class GLView extends GLSurfaceView implements GameView {
	
	public GLView(Context context) {
		super(context);
	}
	
	public GLView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
}
