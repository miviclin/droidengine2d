package com.miviclin.droidengine2d.graphics.shaders;

import android.opengl.GLES20;

import com.miviclin.droidengine2d.graphics.GLDebug;

/**
 * ShaderProgram configurado para renderizar DynamicSpriteBatch
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class DynamicSpriteBatchShaderProgram extends ShaderProgram {
	
	public final static String VERTEX_SHADER = "" +
			"uniform mat4 uMVPMatrix[32];\n" +
			"attribute float aMVPMatrixIndex;\n" +
			"attribute vec4 aPosition;\n" +
			"attribute vec2 aTextureCoord;\n" +
			"varying vec2 vTextureCoord;\n" +
			"void main() {\n" +
			"    gl_Position = uMVPMatrix[int(aMVPMatrixIndex)] * aPosition;\n" +
			"    vTextureCoord = aTextureCoord;\n" +
			"}";
	
	public final static String FRAGMENT_SHADER = "" +
			"precision mediump float;\n" +
			"varying vec2 vTextureCoord;\n" +
			"uniform sampler2D sTexture;\n" +
			"void main() {\n" +
			"    gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
			"}";
	
	private int aPositionHandle;
	private int aTextureHandle;
	private int uMVPMatrixHandle;
	private int aMVPMatrixIndexHandle;
	
	/**
	 * Crea un DynamicSpriteBatchShaderProgram
	 */
	public DynamicSpriteBatchShaderProgram() {
		super();
	}
	
	@Override
	public void link() {
		int programID = ShaderUtilities.createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
		if (programID == 0) {
			return;
		}
		setProgram(programID);
		link(programID);
		setLinked();
	}
	
	/**
	 * Enlaza los atributos de los shaders con el program.<br>
	 * La forma comun de llamar a este metodo es: {@code link(getProgram())}
	 * 
	 * @param program ID del program (el ID asignado por OpenGL al compilar)
	 */
	private void link(int program) {
		aPositionHandle = GLES20.glGetAttribLocation(program, "aPosition");
		GLDebug.checkGLError();
		if (aPositionHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aPosition");
		}
		aTextureHandle = GLES20.glGetAttribLocation(program, "aTextureCoord");
		GLDebug.checkGLError();
		if (aTextureHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aTextureCoord");
		}
		aMVPMatrixIndexHandle = GLES20.glGetAttribLocation(program, "aMVPMatrixIndex");
		GLDebug.checkGLError();
		if (aMVPMatrixIndexHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aMVPMatrixIndex");
		}
		uMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
		GLDebug.checkGLError();
		if (uMVPMatrixHandle == -1) {
			throw new RuntimeException("Could not get attrib location for uMVPMatrix");
		}
	}
	
	/**
	 * Devuelve el handle del attribute "aPosition"
	 * 
	 * @return aPositionHandle
	 */
	public int getPositionAttributeHandle() {
		return aPositionHandle;
	}
	
	/**
	 * Devuelve el handle del attribute "aTextureCoord"
	 * 
	 * @return aTextureHandle
	 */
	public int getTextureCoordAttributeHandle() {
		return aTextureHandle;
	}
	
	/**
	 * Devuelve el handle del attribute "aMVPMatrixIndex"
	 * 
	 * @return aMVPMatrixIndexHandle
	 */
	public int getMVPMatrixIndexAttributeHandle() {
		return aMVPMatrixIndexHandle;
	}
	
	/**
	 * Devuelve el handle del uniform "uMVPMatrix"
	 * 
	 * @return uMVPMatrixHandle
	 */
	public int getMVPMatrixUniformHandle() {
		return uMVPMatrixHandle;
	}
	
}
