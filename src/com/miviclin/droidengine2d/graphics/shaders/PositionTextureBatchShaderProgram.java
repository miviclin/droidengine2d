package com.miviclin.droidengine2d.graphics.shaders;

import java.nio.FloatBuffer;

import android.opengl.GLES20;

import com.miviclin.droidengine2d.graphics.GLDebugger;

/**
 * ShaderProgram configurado para renderizar batches de sprites
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class PositionTextureBatchShaderProgram extends ShaderProgram {
	
	private int aPositionHandle;
	private int aTextureHandle;
	private int uMVPMatrixHandle;
	private int aMVPMatrixIndexHandle;
	
	/**
	 * Crea un PositionTextureBatchShaderProgram
	 */
	public PositionTextureBatchShaderProgram() {
		super(new PositionTextureBatchShaderDefinitions());
	}
	
	/**
	 * Crea un PositionTextureBatchShaderProgram
	 * 
	 * @param shaderDefinitions Objeto que define los shaders
	 */
	protected PositionTextureBatchShaderProgram(ShaderDefinitions shaderDefinitions) {
		super(shaderDefinitions);
	}
	
	@Override
	public void link() {
		ShaderDefinitions shaders = getShaderDefinitions();
		int programID = ShaderUtilities.createProgram(shaders.getVertexShaderDefinition(), shaders.getFragmentShaderDefinition());
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
	protected void link(int program) {
		aPositionHandle = GLES20.glGetAttribLocation(program, "aPosition");
		GLDebugger.getInstance().passiveCheckGLError();
		if (aPositionHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aPosition");
		}
		aTextureHandle = GLES20.glGetAttribLocation(program, "aTextureCoord");
		GLDebugger.getInstance().passiveCheckGLError();
		if (aTextureHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aTextureCoord");
		}
		aMVPMatrixIndexHandle = GLES20.glGetAttribLocation(program, "aMVPMatrixIndex");
		GLDebugger.getInstance().passiveCheckGLError();
		if (aMVPMatrixIndexHandle == -1) {
			throw new RuntimeException("Could not get attrib location for aMVPMatrixIndex");
		}
		uMVPMatrixHandle = GLES20.glGetUniformLocation(program, "uMVPMatrix");
		GLDebugger.getInstance().passiveCheckGLError();
		if (uMVPMatrixHandle == -1) {
			throw new RuntimeException("Could not get attrib location for uMVPMatrix");
		}
	}
	
	/**
	 * Especifica las coordenadas de los vertices que se van a enviar al vertex shader
	 * 
	 * @param buffer Buffer que contiene las coordenadas de los vertices
	 * @param offset Posicion del buffer en la que se encuentra la primera coordenada
	 * @param size Numero de coordenadas que se especifican por vertice
	 * @param stride Numero de bytes que hay en el buffer entre la primera coordenada de un vertice y la primera coordenada del siguiente
	 */
	public void specifyVerticesPosition(FloatBuffer buffer, int offset, int size, int stride) {
		GLES20.glEnableVertexAttribArray(aPositionHandle);
		GLDebugger.getInstance().passiveCheckGLError();
		
		buffer.position(offset);
		GLES20.glVertexAttribPointer(aPositionHandle, size, GLES20.GL_FLOAT, false, stride, buffer);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica las coordenadas UV de las texturas para los vertices que se van a enviar al vertex shader
	 * 
	 * @param buffer Buffer que contiene las coordenadas de las texturas para cada vertice
	 * @param offset Posicion del buffer en la que se encuentra la primera coordenada
	 * @param size Numero de coordenadas que se especifican por vertice
	 * @param stride Numero de bytes que hay en el buffer entre la primera coordenada de un vertice y la primera coordenada del siguiente
	 */
	public void specifyVerticesTextureCoords(FloatBuffer buffer, int offset, int size, int stride) {
		GLES20.glEnableVertexAttribArray(aTextureHandle);
		GLDebugger.getInstance().passiveCheckGLError();
		
		buffer.position(offset);
		GLES20.glVertexAttribPointer(aTextureHandle, size, GLES20.GL_FLOAT, false, stride, buffer);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica los indices del array de MVP que se van a enviar al vertex shader
	 * 
	 * @param buffer Buffer que contiene los indices asociados a cada vertice
	 * @param offset Posicion del buffer en la que se encuentra el primer indice
	 * @param stride Numero de bytes que hay en el buffer entre un indice y el siguiente
	 */
	public void specifyVerticesMVPIndices(FloatBuffer buffer, int offset, int stride) {
		GLES20.glEnableVertexAttribArray(aMVPMatrixIndexHandle);
		GLDebugger.getInstance().passiveCheckGLError();
		
		buffer.position(offset);
		GLES20.glVertexAttribPointer(aMVPMatrixIndexHandle, 1, GLES20.GL_FLOAT, false, stride, buffer);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica las matrices MVP que se van a enviar al vertex shader
	 * 
	 * @param mvpMatrices Todas las matrices MVP concatenadas en un unico array
	 * @param offset Posicion del array en la que esta el primer elemento de la primera matriz
	 * @param batchSize Numero de matrices que se enviaran
	 */
	public void specifyMVPMatrices(float[] mvpMatrices, int offset, int batchSize) {
		GLES20.glUniformMatrix4fv(uMVPMatrixHandle, batchSize, false, mvpMatrices, offset);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Definiciones de los shaders
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	private static class PositionTextureBatchShaderDefinitions extends ShaderDefinitions {
		
		private final static String VERTEX_SHADER = "" +
				"uniform mat4 uMVPMatrix[32];\n" +
				"attribute float aMVPMatrixIndex;\n" +
				"attribute vec4 aPosition;\n" +
				"attribute vec2 aTextureCoord;\n" +
				"varying vec2 vTextureCoord;\n" +
				"void main() {\n" +
				"    gl_Position = uMVPMatrix[int(aMVPMatrixIndex)] * aPosition;\n" +
				"    vTextureCoord = aTextureCoord;\n" +
				"}";
		
		private final static String FRAGMENT_SHADER = "" +
				"precision mediump float;\n" +
				"varying vec2 vTextureCoord;\n" +
				"uniform sampler2D sTexture;\n" +
				"void main() {\n" +
				"    gl_FragColor = texture2D(sTexture, vTextureCoord);\n" +
				"}";
		
		@Override
		public String getVertexShaderDefinition() {
			return VERTEX_SHADER;
		}
		
		@Override
		public String getFragmentShaderDefinition() {
			return FRAGMENT_SHADER;
		}
		
	}
	
}
