package com.miviclin.droidengine2d.scenes;

import java.util.HashMap;
import java.util.Map;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.sprites.SpriteBatch;

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
	 * @param scene Scene a agregar. No puede ser null
	 */
	public void registerScene(String sceneID, Scene scene) {
		if (scene == null) {
			throw new IllegalArgumentException("The Scene can not be null");
		}
		scenes.put(sceneID, scene);
		scene.onRegister();
	}
	
	/**
	 * Elimina la Scene asociada al identificador especificado. Si no habia ninguna Scene registrada con este identificador no hace nada.
	 * 
	 * @param sceneID Identificador de la Scene a eliminar
	 * @return Scene eliminada o null
	 */
	public Scene unregisterScene(String sceneID) {
		return scenes.remove(sceneID);
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
		if (activeScene != null) {
			activeScene.onDeactivation();
		}
		this.activeScene = scenes.get(sceneID);
		if (activeScene != null) {
			activeScene.onActivation();
		}
	}
	
	/**
	 * Llamado cuando el juego se va a background. Llama a {@link Scene#onPause()} en la Scene activa
	 */
	public void pause() {
		if (activeScene != null) {
			activeScene.onPause();
		}
	}
	
	/**
	 * Llamado cuando el juego vuelve de background. Llama a {@link Scene#onResume()} en la Scene activa
	 */
	public void resume() {
		if (activeScene != null) {
			activeScene.onResume();
		}
	}
	
	/**
	 * Libera los recursos de todas las Scenes registradas en el SceneManager. Llama a {@link Scene#dispose()} en todas las Scenes.<br>
	 * El SceneManager quedara vacio.
	 */
	public void dispose() {
		for (Map.Entry<String, Scene> entry : scenes.entrySet()) {
			entry.getValue().dispose();
			entry.setValue(null);
		}
		scenes.clear();
	}
	
	/**
	 * Actualiza los elementos de la Scene activa.<br>
	 * Este metodo se llama desde {@link Game#update(float)}
	 * 
	 * @param delta Tiempo transcurrido, en milisegundos, desde la ultima actualizacion.
	 */
	public void update(float delta) {
		if (activeScene != null) {
			activeScene.update(delta);
		}
	}
	
	/**
	 * Renderiza los elementos de la Scene activa de forma que puedan verse en pantalla.<br>
	 * Este metodo se llama desde {@link Game#draw(SpriteBatch)}<br>
	 * Este metodo se ejecuta en el hilo del GLRenderer tras ejecutar {@link SceneManager#update(float)} en el GameThread
	 */
	public void draw(SpriteBatch spriteBatch) {
		if (activeScene != null) {
			activeScene.draw(spriteBatch);
		}
	}
	
}
