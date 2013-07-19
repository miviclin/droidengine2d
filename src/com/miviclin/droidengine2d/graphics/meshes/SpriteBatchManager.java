package com.miviclin.droidengine2d.graphics.meshes;

import java.util.HashMap;


/**
 * Almacena SpriteBatches y se encarga de renderizar elementos en pantalla usando los SpriteBatches de los que dispone
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class SpriteBatchManager {
	
	private HashMap<Class<? extends SpriteBatch>, SpriteBatch> spriteBatches;
	
	/**
	 * Constructor
	 */
	public SpriteBatchManager() {
		this.spriteBatches = new HashMap<Class<? extends SpriteBatch>, SpriteBatch>();
	}
	
	/**
	 * Devuelve un SpriteBatch indexado por clase
	 * 
	 * @param sbClass Clase a la que pertenece el SpriteBatch que se quiere obtener
	 * @return SpriteBatch asociado a la clase especificada o null si no habia ningun SpriteBatch registrado con esa clase
	 */
	public SpriteBatch getSpriteBatch(Class<? extends SpriteBatch> sbClass) {
		return spriteBatches.get(sbClass);
	}
	
	/**
	 * Agrega un SpriteBatch al SpriteBatchManager
	 * 
	 * @param sbClass Clase a la que pertenece el SpriteBatch que se quiere registrar
	 * @param spriteBatch SpriteBatch a agregar
	 */
	public void addSpriteBatch(Class<? extends SpriteBatch> sbClass, SpriteBatch spriteBatch) {
		spriteBatches.put(sbClass, spriteBatch);
	}
	
	/**
	 * Elimina un SpriteBatch del SpriteBatchManager
	 * 
	 * @param sbClass Clase a la que pertenece el SpriteBatch que se quiere eliminar
	 * @return Devuelve el SpriteBatch eliminado o null si no habia ninguno registrado con el ID especificado
	 */
	public SpriteBatch removeSpriteBatch(Class<? extends SpriteBatch> sbClass) {
		return spriteBatches.remove(sbClass);
	}
	
	/**
	 * Elimina todos los SpriteBatches previamente registrados
	 */
	public void clear() {
		spriteBatches.clear();
	}
	
}
