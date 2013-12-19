package com.miviclin.droidengine2d.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * View where the game is renderer with OpenGL.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GLView extends GLSurfaceView {

	/**
	 * Standard View constructor. In order to render something, you must call {@link #setRenderer} to register a
	 * renderer.
	 * 
	 * @param context Context.
	 */
	public GLView(Context context) {
		super(context);
	}

	/**
	 * Standard View constructor. In order to render something, you must call {@link #setRenderer} to register a
	 * renderer.
	 * 
	 * @param context Context.
	 * @param attrs AttributeSet
	 */
	public GLView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
}
