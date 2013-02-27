package com.miviclin.droidengine2d.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.miviclin.droidengine2d.ui.GameView;

/**
 * View sobre la que se representan los elementos del juego.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GLView extends GLSurfaceView implements GameView {
	
	/**
	 * Constructor
	 * 
	 * @param context Context en el que se ejecuta el juego
	 */
	public GLView(Context context) {
		super(context);
	}
	
	/**
	 * Constructor
	 * 
	 * @param context Context en el que se ejecuta el juego
	 * @param attrs AttributeSet
	 */
	public GLView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
}
