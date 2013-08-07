package com.miviclin.droidengine2d.graphics;

import com.miviclin.droidengine2d.graphics.shader.ShaderProgram;

/**
 * Clase base para otros batches
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface ShapeBatch {
	
	/**
	 * Prepara el ShapeBatch para pintar.
	 */
	public void begin();
	
	/**
	 * Renderiza todos los elementos que contenga el batch.<br>
	 * Este metodo debe llamarse siempre al terminar de agregar todos los elementos al batch. Es necesario llamar antes a {@link #begin()}
	 */
	public void end();
	
	/**
	 * Devuelve el ShaderProgram que utiliza este ShapeBatch
	 * 
	 * @return ShaderProgram
	 */
	public ShaderProgram getShaderProgram();
	
}
