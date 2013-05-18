package com.miviclin.droidengine2d.graphics.shaders;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.miviclin.droidengine2d.graphics.GLDebugger;

/**
 * ShaderProgram configurado para renderizar batches de poligonos con posicion, textura y opacidad
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class PositionTextureOpacityBatchShaderProgram extends PositionTextureBatchShaderProgram {
	
	private int aOpacityHandle;
	
	/**
	 * Crea un PositionTextureOpacityBatchShaderProgram
	 */
	public PositionTextureOpacityBatchShaderProgram() {
		super(new PositionTextureOpacityBatchShaderDefinitions());
	}
	
	/**
	 * Crea un PositionTextureOpacityBatchShaderProgram
	 * 
	 * @param shaderDefinitions Objeto que define los shaders
	 */
	protected PositionTextureOpacityBatchShaderProgram(ShaderDefinitions shaderDefinitions) {
		super(shaderDefinitions);
	}
	
	@Override
	protected void link(int programID) {
		super.link(programID);
		
		aOpacityHandle = GLES20.glGetAttribLocation(programID, "aOpacity");
		GLDebugger.getInstance().passiveCheckGLError();
		if (aOpacityHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aOpacity");
		}
	}
	
	/**
	 * Especifica los valores de la opacidad de la textura que se van a enviar al vertex shader
	 * 
	 * @param buffer Buffer que contiene los valores asociados a cada vertice
	 * @param offset Posicion del buffer en la que se encuentra el primer valor
	 * @param stride Numero de bytes que hay en el buffer entre un valor y el siguiente
	 */
	public void specifyVerticesOpacity(FloatBuffer buffer, int offset, int stride) {
		GLES20.glEnableVertexAttribArray(aOpacityHandle);
		GLDebugger.getInstance().passiveCheckGLError();
		
		buffer.position(offset);
		GLES20.glVertexAttribPointer(aOpacityHandle, 1, GLES20.GL_FLOAT, false, stride, buffer);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Definiciones de los shaders
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	protected static class PositionTextureOpacityBatchShaderDefinitions extends ShaderDefinitions {
		
		@Override
		public String getVertexShaderDefinition() {
			return "" +
					"uniform mat4 uMVPMatrix[32];\n" +
					"attribute float aMVPMatrixIndex;\n" +
					"attribute vec4 aPosition;\n" +
					"attribute vec2 aTextureCoord;\n" +
					"attribute float aOpacity;\n" +
					"varying vec2 vTextureCoord;\n" +
					"varying float vOpacity;\n" +
					"void main() {\n" +
					"    gl_Position = uMVPMatrix[int(aMVPMatrixIndex)] * aPosition;\n" +
					"    vTextureCoord = aTextureCoord;\n" +
					"    vOpacity = aOpacity;\n" +
					"}";
		}
		
		@Override
		public String getFragmentShaderDefinition() {
			return "" +
					"precision mediump float;\n" +
					"varying vec2 vTextureCoord;\n" +
					"varying float vOpacity;\n" +
					"uniform sampler2D sTexture;\n" +
					"void main() {\n" +
					"    gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
					"    gl_FragColor.w *= vOpacity;\n" +
					"}";
		}
		
	}
	
}
