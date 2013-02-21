package com.miviclin.droidengine2d.graphics;

import android.opengl.GLES20;
import android.opengl.GLException;

public class GLDebug {
	
	public static void checkGLError(String message) {
		int errorCode;
		if ((errorCode = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			throw new GLException(errorCode, "GLError " + errorCode + " : " + message);
		}
	}
	
}
