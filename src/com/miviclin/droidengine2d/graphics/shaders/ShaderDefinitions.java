package com.miviclin.droidengine2d.graphics.shaders;

/**
 * Definiciones en GLSL de los shaders
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class ShaderDefinitions {
	
	/**
	 * Devuelve un string con el codigo GLSL del Vertex Shader
	 * 
	 * @return vertex shader
	 */
	public abstract String getVertexShaderDefinition();
	
	/**
	 * Devuelve un string con el codigo GLSL del Fragment Shader
	 * 
	 * @return fragment shader
	 */
	public abstract String getFragmentShaderDefinition();
}