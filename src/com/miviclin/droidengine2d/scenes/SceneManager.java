package com.miviclin.droidengine2d.scenes;

import java.util.HashMap;

/**
 * Gestor de Scenes
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class SceneManager {
	
	private HashMap<String, Scene> scenes;
	private Scene activeScene;
	
	/**
	 * Constructor
	 */
	public SceneManager() {
		this(16);
	}
	
	/**
	 * Constructor
	 * 
	 * @param initialCapacity Capacidad minima inicial antes de redimensionar la estructura de datos
	 */
	public SceneManager(int initialCapacity) {
		this.scenes = new HashMap<String, Scene>((int) ((initialCapacity / 0.75f) + 1));
		this.activeScene = null;
	}
	
	/**
	 * Registra una Scene en el SceneManager identificada por el sceneID especificado.<br>
	 * Si ya habia una Scene registrada con el mismo sceneID, se sustituira la que habia antes por la nueva.
	 * 
	 * @param sceneID Identificador que permite obtener la Scene
	 * @param scene Scene a agregar
	 */
	public void registerScene(String sceneID, Scene scene) {
		scenes.put(sceneID, scene);
	}
	
	/**
	 * Elimina la Scene asociada al identificador especificado. Si no habia ninguna Scene registrada con este identificador no hace nada.
	 * 
	 * @param sceneID Identificador de la Scene a eliminar
	 */
	public void unregisterScene(String sceneID) {
		scenes.remove(sceneID);
	}
	
	/**
	 * Devuelve la Scene asociada al identificador especificado. Si no habia ninguna Scene registrada con este identificador devuelve null.
	 * 
	 * @param sceneID Identificador de la Scene a devolver
	 * @return Scene asociada al ID especificado o null
	 */
	public Scene getScene(String sceneID) {
		return scenes.get(sceneID);
	}
	
	/**
	 * Devuelve la Scene activa del juego.<br>
	 * Si no se ha asignado previamente una Scene devolvera null
	 * 
	 * @return Scene actual o null
	 */
	public Scene getActiveScene() {
		return activeScene;
	}
	
	/**
	 * Asigna la Scene que estara activa en el juego a partir del ID especificado.<br>
	 * Si no habia ninguna Scene registrada con este identificador se asignara null.
	 * 
	 * @param sceneID Identificador de la Scene que queremos asignar como Scene activa
	 */
	public void setActiveScene(String sceneID) {
		this.activeScene = scenes.get(sceneID);
	}
	
}
