package com.miviclin.droidengine2d.scene;

import android.util.SparseArray;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.Graphics;

/**
 * SceneManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class SceneManager {

	private SparseArray<Scene> scenes;
	private Scene activeScene;

	/**
	 * Constructor.
	 */
	public SceneManager() {
		this(16);
	}

	/**
	 * Constructor.
	 * 
	 * @param initialCapacity Initial capacity for Scenes. If this capacity is reached, the data structure that holds
	 *            the Scenes will be resized automatically.
	 */
	public SceneManager(int initialCapacity) {
		this.scenes = new SparseArray<Scene>(initialCapacity);
		this.activeScene = null;
	}

	/**
	 * Registers an Scene in this SceneManager using the specified sceneId.<br>
	 * If an Scene with the specified sceneId was previously registered in this SceneManager, it will be replaced by the
	 * new one.<br>
	 * The active Scene will not change.
	 * 
	 * @param sceneId Identifier of the Scene. It can be used to get the Scene from this SceneManager later.
	 * @param scene Scene (can not be null).
	 */
	public void registerScene(int sceneId, Scene scene) {
		registerScene(sceneId, scene, false);
	}

	/**
	 * Registers an Scene in this SceneManager using the specified sceneId.<br>
	 * If an Scene with the specified sceneId was previously registered in this SceneManager, it will be replaced by the
	 * new one.
	 * 
	 * @param sceneId Identifier of the Scene. It can be used to get the Scene from this SceneManager later.
	 * @param scene Scene (can not be null).
	 * @param activate true to make the Scene the active Scene of this SceneManager.
	 */
	public void registerScene(int sceneId, Scene scene, boolean activate) {
		if (scene == null) {
			throw new IllegalArgumentException("The Scene can not be null");
		}
		scenes.put(sceneId, scene);
		scene.onRegister();
		if (activate) {
			setActiveScene(sceneId);
		}
	}

	/**
	 * Unregisters the specified Scene from this SceneManager.<br>
	 * If an Scene was registered with the specified sceneId, {@link Scene#onDispose()} is called on the Scene before it
	 * is removed from this SceneManager.
	 * 
	 * @param sceneId Identifier of the Scene.
	 * @return Removed Scene or null
	 */
	public Scene unregisterScene(int sceneId) {
		Scene scene = scenes.get(sceneId);
		if (scene == activeScene) {
			scene.onDeactivation();
			activeScene = null;
		}
		if (scene != null) {
			scene.dispose();
			scenes.remove(sceneId);
		}
		return scene;
	}

	/**
	 * Returns the Scene associated with the specified sceneId.
	 * 
	 * @param sceneId Identifier of the Scene.
	 * @return Scene or null
	 */
	public Scene getScene(int sceneId) {
		return scenes.get(sceneId);
	}

	/**
	 * Returns the active Scene of this SceneManager.
	 * 
	 * @return Scene or null
	 */
	public Scene getActiveScene() {
		return activeScene;
	}

	/**
	 * Sets the active Scene of this SceneManager.<br>
	 * The Scene must have been previously registered with the specified sceneId.
	 * 
	 * @param sceneId Identifier of the Scene we want to set as the active Scene.
	 */
	public void setActiveScene(int sceneId) {
		if (activeScene != null) {
			activeScene.onDeactivation();
		}
		this.activeScene = scenes.get(sceneId);
		if (activeScene != null) {
			activeScene.onActivation();
		}
	}

	/**
	 * This method is called when the engine is paused, usually when the activity goes to background.<br>
	 * Calls {@link Scene#onPause()} on the active Scene.
	 */
	public void pause() {
		if (activeScene != null) {
			activeScene.onPause();
		}
	}

	/**
	 * This method is called when the engine is resumed, usually when the activity comes to foreground.<br>
	 * Calls {@link Scene#onResume()} on the active Scene.
	 */
	public void resume() {
		if (activeScene != null) {
			activeScene.onResume();
		}
	}

	/**
	 * Calls {@link Scene#onDispose()} on all Scenes registered in this SceneManager and removes them from the
	 * SceneManager.<br>
	 * This SceneManager will be left empty.
	 */
	public void dispose() {
		int numScenes = scenes.size();
		for (int i = 0; i < numScenes; i++) {
			Scene scene = scenes.valueAt(i);
			if (scene != null) {
				scene.dispose();
			}
		}
		scenes.clear();
		activeScene = null;
	}

	/**
	 * Calls {@link Scene#update(float)} on the active Scene .<br>
	 * This method is called from {@link Game#update(float)}.
	 * 
	 * @param delta Elapsed time, in milliseconds, since the last update.
	 */
	public void update(float delta) {
		if (activeScene != null) {
			activeScene.update(delta);
		}
	}

	/**
	 * Calls {@link Scene#draw(Graphics)} on the active Scene.<br>
	 * This method is called from {@link Game#draw(Graphics)}.<br>
	 * This method is called from the redering thread after {@link SceneManager#update(float)} has been executed in the
	 * game thread.
	 */
	public void draw(Graphics graphics) {
		if (activeScene != null) {
			activeScene.draw(graphics);
		}
	}

}
