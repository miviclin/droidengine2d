package com.miviclin.droidengine2d.graphics;

import android.opengl.GLES20;
import android.opengl.GLException;

/**
 * Contiene metodos utiles para depurar el funcionamiento de OpenGL
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GLDebug {
	
	/**
	 * Comprueba si se ha producido un error en alguna funcion de OpenGL llamada previamente.<br>
	 * Si se detecta un error se lanza una excepcion con el mensaje especificado.
	 * 
	 * @param message Mensaje que se muestra en caso de error
	 * @throws GLException
	 */
	public static void checkGLError(String message) {
		int errorCode;
		if ((errorCode = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			throw new GLException(errorCode, "GLError " + errorCode + " : " + message);
		}
	}
	
}
