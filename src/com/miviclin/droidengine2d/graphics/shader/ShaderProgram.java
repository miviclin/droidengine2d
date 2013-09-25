package com.miviclin.droidengine2d.graphics.shader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.opengl.GLES20;

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
	 * 
	 * @param vertexShaderSource Codigo GLSL del vertex shader
	 * @param fragmentShaderSource Codigo GLSL del fragment shader
	 * @param attributes Lista de atributes del shader program
	 * @param uniforms Lista de uniforms del shader program
	 */
	public ShaderProgram(String vertexShaderSource, String fragmentShaderSource, ArrayList<String> attributes, ArrayList<String> uniforms) {
		this.vertexShaderSource = vertexShaderSource;
		this.fragmentShaderSource = fragmentShaderSource;
		
		this.attributesLocations = new HashMap<String, Integer>();
		for (int i = 0; i < attributes.size(); i++) {
			attributesLocations.put(attributes.get(i), -1);
		}
		
		this.uniformsLocations = new HashMap<String, Integer>();
		for (int i = 0; i < uniforms.size(); i++) {
			uniformsLocations.put(uniforms.get(i), -1);
		}
		
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
		return attributesLocations.get(attributeName).intValue();
	}
	
	/**
	 * Devuelve la posicion del uniform especificado en el shader
	 * 
	 * @param uniformName Nombre del uniform
	 * @return Posicion del uniform
	 */
	public int getUniformLocation(String uniformName) {
		return uniformsLocations.get(uniformName).intValue();
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
	 * 1) Compila los shaders creando el ID del program.<br>
	 * 2) Asigna dicho ID con setProgram(programId);<br>
	 * 3) Enlaza los atributos de los shaders con el program<br>
	 * 4) Llama a {@link #setLinked()} para indicar que el program ya ha sido enlazado.
	 */
	public void compileAndLink() {
		int programId = ShaderUtilities.createProgram(vertexShaderSource, fragmentShaderSource);
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
	
}
