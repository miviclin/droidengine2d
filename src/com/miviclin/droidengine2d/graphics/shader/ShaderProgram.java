package com.miviclin.droidengine2d.graphics.shader;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.opengl.GLES20;
import android.util.Log;

import com.miviclin.droidengine2d.graphics.GLDebugger;

/**
 * Shader program formado por el VERTEX SHADER y el FRAGMENT SHADER.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ShaderProgram {
	
	private String vertexShaderSource;
	private String fragmentShaderSource;
	private HashMap<String, Integer> attributesLocations;
	private HashMap<String, Integer> uniformsLocations;
	private int programId;
	private boolean linked;
	
	/**
	 * Crea un ShaderProgram
	 */
	public ShaderProgram() {
		this.vertexShaderSource = "";
		this.fragmentShaderSource = "";
		this.attributesLocations = new HashMap<String, Integer>();
		this.uniformsLocations = new HashMap<String, Integer>();
		this.linked = false;
	}
	
	/**
	 * Devuelve el codigo GLSL del vertex shader
	 * 
	 * @return Codigo GLSL del vertex shader
	 */
	public String getVertexShaderSource() {
		return vertexShaderSource;
	}
	
	/**
	 * Devuelve el codigo GLSL del fragment shader
	 * 
	 * @return Codigo GLSL del fragment shader
	 */
	public String getFragmentShaderSource() {
		return fragmentShaderSource;
	}
	
	/**
	 * Devuelve la posicion del attribute especificado en el shader
	 * 
	 * @param attributeName Nombre del attribute
	 * @return Posicion del attribute
	 */
	public int getAttributeLocation(String attributeName) {
		int attributeLocation = attributesLocations.get(attributeName).intValue();
		if (attributeLocation == -1) {
			throw new IllegalArgumentException("The specified attribute is not linked: " + attributeName);
		}
		return attributeLocation;
	}
	
	/**
	 * Devuelve la posicion del uniform especificado en el shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @return Posicion del uniform
	 */
	public int getUniformLocation(String uniformName) {
		int uniformLocation = uniformsLocations.get(uniformName).intValue();
		if (uniformLocation == -1) {
			throw new IllegalArgumentException("The specified uniform is not linked: " + uniformName);
		}
		return uniformLocation;
	}
	
	/**
	 * Asigna este program al contexto de OpenGL para que sea usado para renderizar los siguientes poligonos.<br>
	 * Es equivalente a llamar a {@code GLES20.glUseProgram(getProgramId());}
	 */
	public void use() {
		GLES20.glUseProgram(programId);
	}
	
	/**
	 * Devuelve el ID asignado por OpenGL al program
	 * 
	 * @return ID del program
	 */
	public int getProgramId() {
		return programId;
	}
	
	/**
	 * Asigna un ID al program.<br>
	 * Este metodo solo deberia ser llamado para asignar el ID que devuelve OpenGL tras la compilacion de los shaders.
	 * 
	 * @param programId Nuevo ID
	 */
	protected void setProgramId(int programId) {
		this.programId = programId;
	}
	
	/**
	 * Devuelve true si el program tiene enlazados los atributos de los shaders
	 * 
	 * @return true si el program tiene enlazados los atributos de los shaders, false en caso contrario
	 */
	public boolean isLinked() {
		return linked;
	}
	
	/**
	 * Indica que el program ya ha sido enlazado con los shaders.<br>
	 * Este metodo debe llamarse unicamente tras enlazar correctamente los atributos de los shaders con el program, normalmente en
	 * {@link #compileAndLink()}
	 */
	protected void setLinked() {
		this.linked = true;
	}
	
	/**
	 * Asigna los shaders que formaran este shader program. Llamar a este metodo antes de {@link #compileAndLink()}
	 * 
	 * @param vertexShaderSource Codigo GLSL del vertex shader
	 * @param fragmentShaderSource Codigo GLSL del fragment shader
	 * @param attributes Lista de atributes del shader program
	 * @param uniforms Lista de uniforms del shader program
	 */
	public void setShaders(String vertexShaderSource, String fragmentShaderSource, ArrayList<String> attributes, ArrayList<String> uniforms) {
		this.vertexShaderSource = vertexShaderSource;
		this.fragmentShaderSource = fragmentShaderSource;
		
		for (int i = 0; i < attributes.size(); i++) {
			attributesLocations.put(attributes.get(i), -1);
		}
		
		for (int i = 0; i < uniforms.size(); i++) {
			uniformsLocations.put(uniforms.get(i), -1);
		}
	}
	
	/**
	 * 1) Compila los shaders creando el ID del program.<br>
	 * 2) Asigna dicho ID con setProgram(programId);<br>
	 * 3) Enlaza los atributos de los shaders con el program<br>
	 * 4) Llama a {@link #setLinked()} para indicar que el program ya ha sido enlazado.
	 */
	public void compileAndLink() {
		if (vertexShaderSource.equals("") || fragmentShaderSource.equals("")) {
			throw new ShaderProgramException("The source code of the shaders is not loaded.\n" +
					"Ensure setShaders(...) has been called before trying to compile the shader program.");
		}
		int programId = ShaderProgram.createProgram(vertexShaderSource, fragmentShaderSource);
		if (programId == 0) {
			return;
		}
		setProgramId(programId);
		link(programId);
		setLinked();
	}
	
	/**
	 * Enlaza los atributos de los shaders con el program.<br>
	 * Este metodo se llama desde {@link #compileAndLink()}. No deberia llamarse manualmente
	 * 
	 * @param programId ID que asigna OpenGL al program compilado
	 */
	protected void link(int programId) {
		String variableName;
		int location;
		for (Map.Entry<String, Integer> entry : attributesLocations.entrySet()) {
			variableName = entry.getKey();
			location = GLES20.glGetAttribLocation(programId, variableName);
			GLDebugger.getInstance().passiveCheckGLError();
			if (location == -1) {
				throw new RuntimeException("Could not get attribute location for " + variableName);
			}
			entry.setValue(location);
		}
		for (Map.Entry<String, Integer> entry : uniformsLocations.entrySet()) {
			variableName = entry.getKey();
			location = GLES20.glGetUniformLocation(programId, variableName);
			GLDebugger.getInstance().passiveCheckGLError();
			if (location == -1) {
				throw new RuntimeException("Could not get uniform location for " + variableName);
			}
			entry.setValue(location);
		}
	}
	
	/**
	 * Especifica los valores del attribute especificado asociados a cada vertice que se van a enviar al vertex shader
	 * 
	 * @param attributeName Nombre del attribute
	 * @param size Numero de valores que se especifican por vertice (Ejemplo: posicion (x, y, z) => size=3)
	 * @param strideBytes Numero de bytes que hay en el buffer entre la primera componente de un attribute y la primera del siguiente
	 * @param dataBuffer Buffer que contiene los valores asociados a cada vertice
	 * @param dataOffset Posicion del buffer en la que se encuentra el primer valor del attribute
	 */
	public void setAttribute(String attributeName, int size, int strideBytes, FloatBuffer dataBuffer, int dataOffset) {
		int attributeLocation = getAttributeLocation(attributeName);
		
		GLES20.glEnableVertexAttribArray(attributeLocation);
		GLDebugger.getInstance().passiveCheckGLError();
		
		dataBuffer.position(dataOffset);
		GLES20.glVertexAttribPointer(attributeLocation, size, GLES20.GL_FLOAT, false, strideBytes, dataBuffer);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica el uniform compuesto por 1 float que se va a enviar al vertex shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @param x Valor del uniform
	 */
	public void setUniform1f(String uniformName, float x) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniform1f(uniformLocation, x);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica el array de uniforms compuestos por 1 float que se va a enviar al vertex shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @param count Numero de elementos en el array
	 * @param data Datos a enviar
	 * @param dataOffset Posicion del array en la que esta el primer dato
	 */
	public void setUniform1fv(String uniformName, int count, float[] data, int dataOffset) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniform1fv(uniformLocation, count, data, dataOffset);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica el uniform compuesto por 2 float que se va a enviar al vertex shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @param x Valor del uniform
	 */
	public void setUniform1f(String uniformName, float x, float y) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniform2f(uniformLocation, x, y);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica el array de uniforms compuestos por 2 float que se va a enviar al vertex shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @param count Numero de elementos en el array
	 * @param data Datos a enviar
	 * @param dataOffset Posicion del array en la que esta el primer dato
	 */
	public void setUniform2fv(String uniformName, int count, float[] data, int dataOffset) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniform2fv(uniformLocation, count, data, dataOffset);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica el uniform compuesto por 3 float que se va a enviar al vertex shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @param x Valor del uniform
	 */
	public void setUniform3f(String uniformName, float x, float y, float z) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniform3f(uniformLocation, x, y, z);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica el array de uniforms compuestos por 2 float que se va a enviar al vertex shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @param count Numero de elementos en el array
	 * @param data Datos a enviar
	 * @param dataOffset Posicion del array en la que esta el primer dato
	 */
	public void setUniform3fv(String uniformName, int count, float[] data, int dataOffset) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniform3fv(uniformLocation, count, data, dataOffset);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica el uniform compuesto por 4 float que se va a enviar al vertex shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @param x Valor del uniform
	 */
	public void setUniform4f(String uniformName, float x, float y, float z, float w) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniform4f(uniformLocation, x, y, z, w);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica el array de uniforms compuestos por 4 float que se va a enviar al vertex shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @param count Numero de elementos en el array
	 * @param data Datos a enviar
	 * @param dataOffset Posicion del array en la que esta el primer dato
	 */
	public void setUniform4fv(String uniformName, int count, float[] data, int dataOffset) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniform4fv(uniformLocation, count, data, dataOffset);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica las matrices de 2x2 floats que se van a enviar al vertex shader
	 * 
	 * @param numMatrices Numero de matrices que se enviaran
	 * @param data Todas las matrices concatenadas en un unico array
	 * @param dataOffset Posicion del array en la que esta el primer elemento de la primera matriz
	 */
	public void setUniformMatrix2fv(String uniformName, int numMatrices, float[] data, int dataOffset) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniformMatrix2fv(uniformLocation, numMatrices, false, data, dataOffset);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica las matrices de 3x3 floats que se van a enviar al vertex shader
	 * 
	 * @param numMatrices Numero de matrices que se enviaran
	 * @param data Todas las matrices concatenadas en un unico array
	 * @param dataOffset Posicion del array en la que esta el primer elemento de la primera matriz
	 */
	public void setUniformMatrix3fv(String uniformName, int numMatrices, float[] data, int dataOffset) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniformMatrix3fv(uniformLocation, numMatrices, false, data, dataOffset);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Especifica las matrices de 4x4 floats que se van a enviar al vertex shader
	 * 
	 * @param numMatrices Numero de matrices que se enviaran
	 * @param data Todas las matrices concatenadas en un unico array
	 * @param dataOffset Posicion del array en la que esta el primer elemento de la primera matriz
	 */
	public void setUniformMatrix4fv(String uniformName, int numMatrices, float[] data, int dataOffset) {
		int uniformLocation = getUniformLocation(uniformName);
		GLES20.glUniformMatrix4fv(uniformLocation, numMatrices, false, data, dataOffset);
		GLDebugger.getInstance().passiveCheckGLError();
	}
	
	/**
	 * Crea un programa GLSL a partir de los 2 shaders especificados
	 * 
	 * @param vertexSource Codigo GLSL del vertex shader
	 * @param fragmentSource Codigo GLSL del fragment shader
	 * @return Devuelve el identificador que asigna OpenGL ES 2.0 al programa compilado. Si devuelve 0 es que se ha producido algun error.
	 */
	protected static int createProgram(String vertexSource, String fragmentSource) {
		int vertexShader, fragmentShader, program;
		
		vertexShader = compileShader(GLES20.GL_VERTEX_SHADER, vertexSource);
		if (vertexShader == 0) {
			return 0;
		}
		fragmentShader = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
		if (fragmentShader == 0) {
			return 0;
		}
		program = GLES20.glCreateProgram();
		if (program != 0) {
			GLES20.glAttachShader(program, vertexShader);
			GLDebugger.getInstance().passiveCheckGLError();
			GLES20.glAttachShader(program, fragmentShader);
			GLDebugger.getInstance().passiveCheckGLError();
			GLES20.glLinkProgram(program);
			int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] != GLES20.GL_TRUE) {
				Log.e(ShaderProgram.class.getSimpleName(), "Could not link program: ");
				Log.e(ShaderProgram.class.getSimpleName(), GLES20.glGetProgramInfoLog(program));
				GLES20.glDeleteProgram(program);
				program = 0;
			}
		}
		return program;
	}
	
	/**
	 * Compila el shader.
	 * 
	 * @param shaderType Tipo de shader. Utilizar {@link GLES20#GL_VERTEX_SHADER } o {@link GLES20#GL_FRAGMENT_SHADER }
	 * @param source Codigo GLSL del shader a compilar
	 * @return Devuelve el identificador que asigna OpenGL ES 2.0 al shader compilado
	 */
	private static int compileShader(int shaderType, String source) {
		int shader = GLES20.glCreateShader(shaderType);
		if (shader != 0) {
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			int[] compiled = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
			if (compiled[0] == 0) {
				Log.e(ShaderProgram.class.getSimpleName(), "Could not compile shader " + shaderType + ":");
				Log.e(ShaderProgram.class.getSimpleName(), GLES20.glGetShaderInfoLog(shader));
				GLES20.glDeleteShader(shader);
				shader = 0;
			}
		}
		return shader;
	}
	
	@Override
	public String toString() {
		return vertexShaderSource + "\n\n" + fragmentShaderSource;
	}
	
}
