package com.miviclin.droidengine2d.graphics;

import android.opengl.GLES20;

public class DynamicSpriteBatchShaderProgram {
	
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
	
	private int program;
	private int aPositionHandle;
	private int aTextureHandle;
	private int uMVPMatrixHandle;
	private int aMVPMatrixIndexHandle;
	
	private boolean linked;
	
	public DynamicSpriteBatchShaderProgram() {
		this.linked = false;
	}
	
	public void link() {
		int programID = ShaderUtilities.createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
		link(programID);
	}
	
	private void link(int program) {
		if (program == 0) {
			return;
		}
		setProgram(program);
		
		aPositionHandle = GLES20.glGetAttribLocation(program, "aPosition");
		GLDebug.checkGLError("glGetAttribLocation aPosition");
		if (aPositionHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aPosition");
		}
		aTextureHandle = GLES20.glGetAttribLocation(program, "aTextureCoord");
		GLDebug.checkGLError("glGetAttribLocation aTextureCoord");
		if (aTextureHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aTextureCoord");
		}
		aMVPMatrixIndexHandle = GLES20.glGetAttribLocation(program, "aMVPMatrixIndex");
		GLDebug.checkGLError("glGetAttribLocation aMVPMatrixIndex");
		if (aMVPMatrixIndexHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aMVPMatrixIndex");
		}
		uMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
		GLDebug.checkGLError("glGetUniformLocation uMVPMatrix");
		if (uMVPMatrixHandle == -1) {
			throw new RuntimeException("Could not get attrib location for uMVPMatrix");
		}
		
		linked = true;
	}
	
	public void use() {
		GLES20.glUseProgram(program);
	}
	
	public int getProgram() {
		return program;
	}
	
	private void setProgram(int program) {
		this.program = program;
	}
	
	public int getPositionAttributeHandle() {
		return aPositionHandle;
	}
	
	public int getTextureCoordAttributeHandle() {
		return aTextureHandle;
	}
	
	public int getMVPMatrixUniformHandle() {
		return uMVPMatrixHandle;
	}
	
	public int getMVPMatrixIndexAttributeHandle() {
		return aMVPMatrixIndexHandle;
	}
	
	public boolean isLinked() {
		return linked;
	}
	
}
