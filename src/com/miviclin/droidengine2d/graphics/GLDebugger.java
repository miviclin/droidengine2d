package com.miviclin.droidengine2d.graphics;

import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLU;

/**
 * Clase que proporciona una forma de obtener mensajes de error de OpenGL en caso de que se produzca un error.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public final class GLDebugger {
	
	private static GLDebugger instance = null;
	
	private boolean debugModeEnabled;
	
	/**
	 * Constructor
	 */
	private GLDebugger() {
		this.debugModeEnabled = false;
	}
	
	/**
	 * Devuelve la instancia de GLDebugger
	 * 
	 * @return GLDebugger
	 */
	public static GLDebugger getInstance() {
		if (GLDebugger.instance == null) {
			GLDebugger.instance = new GLDebugger();
		}
		return GLDebugger.instance;
	}
	
	/**
	 * Comprueba si se ha producido un error en alguna funcion de OpenGL llamada previamente.<br>
	 * Si el modo debug esta desactivado, este metodo no hace nada a menos que se detecte un fallo al llamar a
	 * {@link GLDebugger#checkGLError()}
	 * 
	 * @throws GLException Si se detecta un error
	 */
	public void passiveCheckGLError() {
		int errorCode;
		if (debugModeEnabled) {
			if ((errorCode = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
				throwGLException(errorCode);
			}
		}
	}
	
	/**
	 * Comprueba si se ha producido un error en alguna funcion de OpenGL llamada previamente.<br>
	 * Si se detecta un error, se activara el modo debug, de forma que las llamadas a {@link #passiveCheckGLError()} comprobaran tambien si
	 * se ha producido error y podran lanzar
	 * 
	 * @throws GLException Si se detecta un error
	 */
	public void checkGLError() {
		int errorCode;
		if ((errorCode = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			if (debugModeEnabled) {
				throwGLException(errorCode);
			} else {
				debugModeEnabled = true;
				// Para limpiar los flags de error de OpenGL
				while (true) {
					if (GLES20.glGetError() == GLES20.GL_NO_ERROR) {
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Lanza una excepcion con el codigo de error especificado y un mensaje de descripcion de dicho error
	 * 
	 * @param errorCode Codigo de error
	 * @throws GLException
	 */
	private static void throwGLException(int errorCode) {
		throw new GLException(errorCode, "GLError " + errorCode + ", " + GLDebugger.getErrorString(errorCode));
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
