package com.miviclin.droidengine2d.graphics;

import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLU;
import android.util.Log;

/**
 * Clase que proporciona una forma de obtener mensajes de error de OpenGL en caso de que se produzca un error.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public final class GLDebugger {
	
	public static final int FLAG_NO_LOGGING = 0;
	public static final int FLAG_LOG_NUM_DRAW_CALLS_FRAME = 1;
	
	private static GLDebugger instance = null;
	
	private boolean debugModeEnabled;
	private int numDrawCallsFrame;
	private int logFlags;
	
	/**
	 * Constructor
	 */
	private GLDebugger() {
		this.debugModeEnabled = false;
		this.numDrawCallsFrame = 0;
		this.logFlags = GLDebugger.FLAG_NO_LOGGING;
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
	 * Asigna los flags de logging. Utilizar {@link GLDebugger#FLAG_NO_LOGGING} para desactivar el logging
	 * 
	 * @param flags Flags
	 */
	public void setLoggingFlags(int flags) {
		this.logFlags = flags;
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
	
	/**
	 * Devuelve el numero de llamadas a draw realizadas.<br>
	 * Para que el numero devuelto sea correcto, es necesario haber llamado a {@link #incrementNumDrawCallsFrame()} tras cada llamada
	 * realizada y {@link #resetNumDrawCallsFrame()} al final de cada frame
	 * 
	 * @return Numero de llamadas a draw
	 */
	public int getNumDrawCallsFrame() {
		return numDrawCallsFrame;
	}
	
	/**
	 * Incrementa el contador de llamadas a draw
	 */
	public void incrementNumDrawCallsFrame() {
		numDrawCallsFrame++;
	}
	
	/**
	 * Reinicia el contador de llamadas a draw
	 */
	public void resetNumDrawCallsFrame() {
		numDrawCallsFrame = 0;
	}
	
	/**
	 * Muestra un mensaje en el log con el numero de llamadas a draw realizadas hasta el momento. Solo se mostrara si el flag
	 * {@link GLDebugger#FLAG_LOG_NUM_DRAW_CALLS_FRAME} esta activo
	 * 
	 * @see #getNumDrawCallsFrame()
	 */
	public void logNumDrawCallsFrame() {
		if ((logFlags & GLDebugger.FLAG_LOG_NUM_DRAW_CALLS_FRAME) == GLDebugger.FLAG_LOG_NUM_DRAW_CALLS_FRAME) {
			Log.d(DefaultRenderer.class.getSimpleName(), "draw calls: " + GLDebugger.getInstance().getNumDrawCallsFrame());
		}
	}
	
}
