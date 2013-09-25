package com.miviclin.droidengine2d.graphics.shader;

/**
 * Excepcion utilizada en ShaderProgram
 * 
 * @author Miguel Vicente Linares
 * 
 */
@SuppressWarnings("serial")
public class ShaderProgramException extends RuntimeException {
	
	/**
	 * Crea una ShaderProgramException
	 * 
	 * @param message Mensaje de la excepcion
	 */
	public ShaderProgramException(String message) {
		super(message);
	}
	
}
