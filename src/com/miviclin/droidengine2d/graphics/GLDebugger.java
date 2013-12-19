package com.miviclin.droidengine2d.graphics;

import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLU;
import android.util.Log;

/**
 * OpenGL debugger.<br>
 * Allows getting error messages from OpenGL errors, logging the number of draw calls, etc.
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
	 * Constructor.
	 */
	private GLDebugger() {
		this.debugModeEnabled = false;
		this.numDrawCallsFrame = 0;
		this.logFlags = GLDebugger.FLAG_NO_LOGGING;
	}

	/**
	 * Returns the instance of GLDebugger.
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
	 * Sets logging flags. Use {@link GLDebugger#FLAG_NO_LOGGING} to disable logging.
	 * 
	 * @param flags Logging flags.
	 */
	public void setLoggingFlags(int flags) {
		this.logFlags = flags;
	}

	/**
	 * Checks if any OpenGL erros occurred before calling this method.<br>
	 * If the debug mode is disabled, this method will not do anything.
	 * 
	 * @throws GLException If an error is detected.
	 * @see GLDebugger#checkGLError()
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
	 * Checks if any OpenGL erros occurred before calling this method.<br>
	 * If an error is detected, the debug mode will be enabled, so {@link #passiveCheckGLError()} calls will also check
	 * for errors, making it easier to find the error source.
	 * 
	 * @throws GLException If an error is detected.
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
	 * Throws a GLException with the specified error code. The exception will also contain the error message associated
	 * with the specified error code.
	 * 
	 * @param errorCode Error code.
	 * @throws GLException
	 */
	private static void throwGLException(int errorCode) {
		throw new GLException(errorCode, "GLError " + errorCode + ", " + GLDebugger.getErrorString(errorCode));
	}

	/**
	 * Returns the error message associated with the specified error code.
	 * 
	 * @param error Error code.
	 * @return Error message.
	 */
	private static String getErrorString(int error) {
		String errorString = GLU.gluErrorString(error);
		if (errorString == null) {
			errorString = "Unknown error 0x" + Integer.toHexString(error);
		}
		return errorString;
	}

	/**
	 * Returns the number of registered draw calls.<br>
	 * In order to register a draw call, {@link #incrementNumDrawCallsFrame()} should be called after the draw call is
	 * executed.<br>
	 * Also {@link #resetNumDrawCallsFrame()} should be called at the end of each frame.
	 * 
	 * @return Number of registered draw calls per frame.
	 */
	public int getNumDrawCallsFrame() {
		return numDrawCallsFrame;
	}

	/**
	 * Increments the number of registered draw calls.<br>
	 * This method should be called after each draw call in order to register it.
	 */
	public void incrementNumDrawCallsFrame() {
		numDrawCallsFrame++;
	}

	/**
	 * Resets the number of registered draw calls.<br>
	 * This method should be called at the end of each frame.
	 */
	public void resetNumDrawCallsFrame() {
		numDrawCallsFrame = 0;
	}

	/**
	 * Logs the number of registered draw calls. If the flag {@link GLDebugger#FLAG_LOG_NUM_DRAW_CALLS_FRAME} is
	 * disabled, this method does not do anything.
	 * 
	 * @see #getNumDrawCallsFrame()
	 */
	public void logNumDrawCallsFrame() {
		if ((logFlags & GLDebugger.FLAG_LOG_NUM_DRAW_CALLS_FRAME) == GLDebugger.FLAG_LOG_NUM_DRAW_CALLS_FRAME) {
			Log.d(DefaultRenderer.class.getSimpleName(), "draw calls: "
					+ GLDebugger.getInstance().getNumDrawCallsFrame());
		}
	}

}
