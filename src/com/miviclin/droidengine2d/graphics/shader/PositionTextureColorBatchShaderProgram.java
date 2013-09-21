package com.miviclin.droidengine2d.graphics.shader;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.miviclin.droidengine2d.graphics.GLDebugger;

/**
 * ShaderProgram configurado para renderizar batches de poligonos con posicion, textura y color
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class PositionTextureColorBatchShaderProgram extends PositionTextureBatchShaderProgram {
	
	private int aColorHandle;
	
	/**
	 * Crea un PositionTextureOpacityBatchShaderProgram
	 */
	public PositionTextureColorBatchShaderProgram() {
		super(new PositionTextureColorBatchShaderDefinitions());
	}
	
	/**
	 * Crea un PositionTextureOpacityBatchShaderProgram
	 * 
	 * @param shaderDefinitions Objeto que define los shaders
	 */
	protected PositionTextureColorBatchShaderProgram(ShaderDefinitions shaderDefinitions) {
		super(shaderDefinitions);
	}
	
	@Override
	protected void link(int programId) {
		super.link(programId);
		
		aColorHandle = GLES20.glGetAttribLocation(programId, "aColor");
		GLDebugger.getInstance().passiveCheckGLError();
		if (aColorHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aColor");
		}
	}
	
	/**
	 * Especifica los valores de color asociados a cada vertice que se van a enviar al vertex shader
	 * 
	 * @param buffer Buffer que contiene los valores asociados a cada vertice
	 * @param offset Posicion del buffer en la que se encuentra el primer valor
	 * @param size Numero de coordenadas que se especifican por vertice (RGB=3; RGBA=4)
	 * @param stride Numero de bytes que hay en el buffer entre la primera componente de un color y la primera del siguiente
	 */
	public void specifyVerticesColors(FloatBuffer buffer, int offset, int size, int stride) {
		GLES20.glEnableVertexAttribArray(aColorHandle);
		GLDebugger.getInstance().passiveCheckGLError();
		
		buffer.position(offset);
		GLES20.glVertexAttribPointer(aColorHandle, size, GLES20.GL_FLOAT, false, stride, buffer);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Definiciones de los shaders
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	protected static class PositionTextureColorBatchShaderDefinitions extends ShaderDefinitions {
		
		@Override
		public String getVertexShaderDefinition() {
			return "" +
					"uniform mat4 uMVPMatrix[32];\n" +
					"attribute float aMVPMatrixIndex;\n" +
					"attribute vec4 aPosition;\n" +
					"attribute vec2 aTextureCoord;\n" +
					"attribute vec4 aColor;\n" +
					"varying vec2 vTextureCoord;\n" +
					"varying vec4 vColor;\n" +
					"void main() {\n" +
					"    gl_Position = uMVPMatrix[int(aMVPMatrixIndex)] * aPosition;\n" +
					"    vTextureCoord = aTextureCoord;\n" +
					"    vColor = aColor;\n" +
					"}";
		}
		
		@Override
		public String getFragmentShaderDefinition() {
			return "" +
					"precision mediump float;\n" +
					"varying vec2 vTextureCoord;\n" +
					"varying vec4 vColor;\n" +
					"uniform sampler2D sTexture;\n" +
					"void main() {\n" +
					"    gl_FragColor = texture2D(sTexture, vTextureCoord) * vColor;\n" +
					"}";
		}
		
	}
	
}
