package com.miviclin.droidengine2d.graphics;

import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLU;

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
	 * @throws GLException Si se detecta un error
	 */
	public static void checkGLError() {
		int errorCode;
		if ((errorCode = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			throw new GLException(errorCode, "GLError " + errorCode + ", " + getErrorString(errorCode));
		}
	}
	
	/**
	 * Devuelve la descripcion de un error de OpenGL
	 * 
	 * @param error Codigo del error
	 * @return Descripcion del error
	 */
	private static String getErrorString(int error) {
		String errorString = GLU.gluErrorString(error);
		if (errorString == null) {
			errorString = "Unknown error 0x" + Integer.toHexString(error);
		}
		return errorString;
	}
	
}
