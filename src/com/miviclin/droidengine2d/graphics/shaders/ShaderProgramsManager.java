package com.miviclin.droidengine2d.graphics.shaders;

import java.util.HashMap;

/**
 * Almacena ShaderPrograms
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ShaderProgramsManager {
	
	private HashMap<String, ShaderProgram> shaderPrograms;
	
	/**
	 * Constructor
	 */
	public ShaderProgramsManager() {
		this.shaderPrograms = new HashMap<String, ShaderProgram>();
	}
	
	/**
	 * Devuelve un ShaderProgram indexado por ID
	 * 
	 * @param id ID con el que se registro el ShaderProgram
	 * @return ShaderProgram asociado al ID especificado o null si no habia ningun ShaderProgram registrado con ese ID
	 */
	public ShaderProgram getShaderProgram(String id) {
		return shaderPrograms.get(id);
	}
	
	/**
	 * Agrega un ShaderProgram al ShaderProgramsManager
	 * 
	 * @param id ID con el que se registrara el ShaderProgram. Se utilizara despues para acceder a el
	 * @param shaderProgram ShaderProgram a agregar
	 */
	public void addShaderProgram(String id, ShaderProgram shaderProgram) {
		shaderPrograms.put(id, shaderProgram);
	}
	
	/**
	 * Elimina un ShaderProgram del ShaderProgramManager
	 * 
	 * @param id ID del ShaderProgram a eliminar
	 * @return Devuelve el ShaderProgram eliminado o null si no habia ninguno registrado con el ID especificado
	 */
	public ShaderProgram removeShaderProgram(String id) {
		return shaderPrograms.remove(id);
	}
	
	/**
	 * Elimina todos los ShaderPrograms previamente registrados
	 */
	public void clear() {
		shaderPrograms.clear();
	}
	
}
