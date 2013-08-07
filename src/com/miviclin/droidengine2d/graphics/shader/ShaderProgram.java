package com.miviclin.droidengine2d.graphics.shader;

import android.opengl.GLES20;

/**
 * Shader program formado por el VERTEX SHADER y el FRAGMENT SHADER.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class ShaderProgram {
	
	private ShaderDefinitions shaderDefinitions;
	private int program;
	private boolean linked;
	
	/**
	 * Crea un ShaderProgram
	 * 
	 * @param shaderDefinitions Objeto que define los shaders
	 */
	public ShaderProgram(ShaderDefinitions shaderDefinitions) {
		this.shaderDefinitions = shaderDefinitions;
		this.linked = false;
	}
	
	/**
	 * Devuelve el objeto que define los shaders
	 * 
	 * @return {@link ShaderDefinitions}
	 */
	public ShaderDefinitions getShaderDefinitions() {
		return shaderDefinitions;
	}
	
	/**
	 * Asigna este program al contexto de OpenGL para que sea usado para renderizar los siguientes poligonos.<br>
	 * Es equivalente a llamar a {@code GLES20.glUseProgram(getProgram());}
	 */
	public void use() {
		GLES20.glUseProgram(program);
	}
	
	/**
	 * Devuelve el ID asignado por OpenGL al program
	 * 
	 * @return ID del program
	 */
	public int getProgram() {
		return program;
	}
	
	/**
	 * Asigna un ID al program.<br>
	 * Este metodo solo deberia ser llamado para asignar el ID que devuelve OpenGL tras la compilacion de los shaders.
	 * 
	 * @param program Nuevo ID
	 */
	protected void setProgram(int program) {
		this.program = program;
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
	 * {@link #link()}
	 */
	protected void setLinked() {
		this.linked = true;
	}
	
	/**
	 * 1) Compila los shaders creando el ID del program.<br>
	 * 2) Asigna dicho ID con setProgram(programID);<br>
	 * 3) Enlaza los atributos de los shaders con el program<br>
	 * 4) Llama a {@link #setLinked()} para indicar que el program ya ha sido enlazado.
	 */
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
	 * Este metodo se llama desde {@link #link()}. No deberia llamarse manualmente
	 * 
	 * @param programID ID que asigna OpenGL al program compilado
	 */
	protected abstract void link(int programID);
	
}
