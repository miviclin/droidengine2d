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

	private static final GLDebugger INSTANCE = new GLDebugger();

	public static final int FLAG_NO_LOGGING = 0;
	public static final int FLAG_LOG_NUM_DRAW_CALLS = 1;

	private boolean debugModeEnabled;
	private int numDrawCallsInCurrentFrame;
	private int numDrawCallsInPreviousFrame;
	private int logFlags;

	/**
	 * Constructor.
	 */
	private GLDebugger() {
		this.debugModeEnabled = false;
		this.numDrawCallsInCurrentFrame = 0;
		this.numDrawCallsInPreviousFrame = 0;
		this.logFlags = FLAG_NO_LOGGING;
	}

	/**
	 * Returns the instance of GLDebugger.
	 * 
	 * @return GLDebugger
	 */
	public static GLDebugger getInstance() {
		return INSTANCE;
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
		throw new GLException(errorCode, "GLError " + errorCode + ", " + getErrorString(errorCode));
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
	 * Returns the number of draw calls registered in the current frame.<br>
	 * In order to register a draw call, {@link #incrementNumDrawCallsInCurrentFrame()} should be called after the draw
	 * call is executed.<br>
	 * Also {@link #resetNumDrawCallsInCurrentFrame()} should be called at the end of each frame.
	 * 
	 * @return Number of draw calls registered in the current frame.
	 * 
	 * @see GLDebugger#incrementNumDrawCallsInCurrentFrame()
	 * @see GLDebugger#resetNumDrawCallsInCurrentFrame()
	 * @see GLDebugger#getNumDrawCallsInPreviousFrame()
	 */
	public int getNumDrawCallsInCurrentFrame() {
		return numDrawCallsInCurrentFrame;
	}

	/**
	 * Returns the number of draw calls registered in the previously rendered frame.
	 * 
	 * @return Number of draw calls registered in the previously rendered frame.
	 * 
	 * @see GLDebugger#getNumDrawCallsInCurrentFrame()
	 * @see GLDebugger#incrementNumDrawCallsInCurrentFrame()
	 * @see GLDebugger#resetNumDrawCallsInCurrentFrame()
	 */
	public int getNumDrawCallsInPreviousFrame() {
		return numDrawCallsInPreviousFrame;
	}

	/**
	 * Increments the number of draw calls registered in the current frame.<br>
	 * This method should be called after each draw call in order to register it.
	 * 
	 * @see GLDebugger#getNumDrawCallsInCurrentFrame()
	 * @see GLDebugger#resetNumDrawCallsInCurrentFrame()
	 * @see GLDebugger#getNumDrawCallsInPreviousFrame()
	 */
	public void incrementNumDrawCallsInCurrentFrame() {
		numDrawCallsInCurrentFrame++;
	}

	/**
	 * Sets the number of draw calls registered in the previous frame to the number of draw calls registered in the
	 * current frame and resets the number of draw calls registered in the current frame.<br>
	 * This method should be called at the end of each frame.
	 * 
	 * @see GLDebugger#getNumDrawCallsInCurrentFrame()
	 * @see GLDebugger#incrementNumDrawCallsInCurrentFrame()
	 * @see GLDebugger#getNumDrawCallsInPreviousFrame()
	 */
	public void resetNumDrawCallsInCurrentFrame() {
		numDrawCallsInPreviousFrame = numDrawCallsInCurrentFrame;
		numDrawCallsInCurrentFrame = 0;
	}

	/**
	 * Logs the number of draw calls registered in the current frame. If the flag
	 * {@link GLDebugger#FLAG_LOG_NUM_DRAW_CALLS} is disabled, this method does not do anything.
	 * 
	 * @see #getNumDrawCallsInCurrentFrame()
	 */
	public void logNumDrawCallsInCurrentFrame() {
		boolean flagLogNumDrawCallsSet = ((logFlags & FLAG_LOG_NUM_DRAW_CALLS) == FLAG_LOG_NUM_DRAW_CALLS);
		if (flagLogNumDrawCallsSet) {
			String tag = getClass().getSimpleName();
			Log.d(tag, "Number of draw calls in the current frame: " + getNumDrawCallsInCurrentFrame());
		}
	}

	/**
	 * Logs the number of draw calls registered in the previously rendered frame. If the flag
	 * {@link GLDebugger#FLAG_LOG_NUM_DRAW_CALLS} is disabled, this method does not do anything.
	 * 
	 * @see #getNumDrawCallsInPreviousFrame()
	 */
	public void logNumDrawCallsInPreviousFrame() {
		boolean flagLogNumDrawCallsSet = ((logFlags & FLAG_LOG_NUM_DRAW_CALLS) == FLAG_LOG_NUM_DRAW_CALLS);
		if (flagLogNumDrawCallsSet) {
			String tag = getClass().getSimpleName();
			Log.d(tag, "Number of draw calls in the previous frame: " + getNumDrawCallsInPreviousFrame());
		}
	}

}
