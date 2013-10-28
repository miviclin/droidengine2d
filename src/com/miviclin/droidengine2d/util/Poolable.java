package com.miviclin.droidengine2d.util;

/**
 * Interfaz que utiliza Pool para asignar los valores por defecto a las propiedades de los objetos que almacena.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface Poolable {

	/**
	 * Reinicia las propiedades del objeto a los valores por defecto
	 */
	public void reset();

}
