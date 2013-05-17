package com.miviclin.droidengine2d.graphics.shaders;

import java.util.HashMap;

/**
 * Almacena ShaderPrograms
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ShaderProgramsManager {
	
	private HashMap<Class<? extends ShaderProgram>, ShaderProgram> shaderPrograms;
	
	/**
	 * Constructor
	 */
	public ShaderProgramsManager() {
		this.shaderPrograms = new HashMap<Class<? extends ShaderProgram>, ShaderProgram>();
	}
	
	/**
	 * Devuelve un ShaderProgram indexado por clase
	 * 
	 * @param spClass Clase a la que pertenece el ShaderProgram que se quiere obtener
	 * @return ShaderProgram asociado a la clase especificada o null si no habia ningun ShaderProgram registrado con esa clase
	 */
	public ShaderProgram getShaderProgram(Class<? extends ShaderProgram> spClass) {
		return shaderPrograms.get(spClass);
	}
	
	/**
	 * Agrega un ShaderProgram al ShaderProgramsManager
	 * 
	 * @param spClass Clase a la que pertenece el ShaderProgram que se quiere registrar
	 * @param shaderProgram ShaderProgram a agregar
	 */
	public void addShaderProgram(Class<? extends ShaderProgram> spClass, ShaderProgram shaderProgram) {
		shaderPrograms.put(spClass, shaderProgram);
	}
	
	/**
	 * Elimina un ShaderProgram del ShaderProgramManager
	 * 
	 * @param spClass Clase a la que pertenece el ShaderProgram que se quiere eliminar
	 * @return Devuelve el ShaderProgram eliminado o null si no habia ninguno registrado con el ID especificado
	 */
	public ShaderProgram removeShaderProgram(Class<? extends ShaderProgram> spClass) {
		return shaderPrograms.remove(spClass);
	}
	
	/**
	 * Elimina todos los ShaderPrograms previamente registrados
	 */
	public void clear() {
		shaderPrograms.clear();
	}
	
}
