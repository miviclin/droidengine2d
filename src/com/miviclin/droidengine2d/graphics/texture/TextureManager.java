package com.miviclin.droidengine2d.graphics.texture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.SparseArray;

import com.miviclin.droidengine2d.graphics.text.Font;
import com.miviclin.droidengine2d.util.Pool;

/**
 * TextureManager.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public final class TextureManager {

	private Context context;
	private HashMap<String, TextureRegion> textureRegions;
	private ArrayList<Texture> activeTextures;
	private Pool<Texture> removedTextures;
	private int texturesToLoad;

	/**
	 * Creates a new TextureManager with an initial capacity of 16.
	 * 
	 * @param context Context.
	 */
	public TextureManager(Context context) {
		this(16, 16, context);
	}

	/**
	 * Creates a new TextureManager.
	 * 
	 * @param initialCapacityForTextures Initial capacity for textures.
	 * @param initialCapacityForTextureRegions Initial capacity for texture regions.
	 * @param context Context.
	 */
	public TextureManager(int initialCapacityForTextures, int initialCapacityForTextureRegions, Context context) {
		int mapCapacity = (int) ((initialCapacityForTextureRegions / 0.75f) + 1);
		this.textureRegions = new HashMap<String, TextureRegion>(mapCapacity);
		this.context = context;
		this.activeTextures = new ArrayList<Texture>(initialCapacityForTextures);
		this.removedTextures = new Pool<Texture>(initialCapacityForTextures);
		this.texturesToLoad = 0;
	}

	/**
	 * Adds a TextureRegion to this TextureManager.<br>
	 * The specified key can be used to retrieve that TextureRegion later. If there is already a TextureRegion
	 * registered with the specified key in this TextureManager, it will be replaced.
	 * 
	 * @param key Identifies the TextureRegion.
	 * @param textureRegion TextureRegion.
	 * @return Returns the TextureRegion previously registered with this key, or null if there wasn't any.
	 */
	public TextureRegion addTextureRegion(String key, TextureRegion textureRegion) {
		addTexture(textureRegion.getTexture());
		return textureRegions.put(key, textureRegion);
	}

	/**
	 * Removes the TextureRegion associated to the specified key.
	 * 
	 * @param key Key.
	 * @return Removed TextureRegion or null
	 */
	public TextureRegion removeTextureRegion(String key) {
		return textureRegions.remove(key);
	}

	/**
	 * Returns the TextureRegion associated to the specified key.
	 * 
	 * @param key Key.
	 * @return TextureRegion or null
	 */
	public TextureRegion getTextureRegion(String key) {
		return textureRegions.get(key);
	}

	/**
	 * Adds all TextureRegions of the specified TextureAtlas to this TextureManager. The source texture of the specified
	 * TextureAtlas is also added to this TextureManager.
	 * 
	 * @param textureAtlas TextureAtlas.
	 */
	public void addTextureAtlas(TextureAtlas textureAtlas) {
		Map<String, TextureRegion> atlasContent = textureAtlas.getTextureRegions();
		for (Map.Entry<String, TextureRegion> entry : atlasContent.entrySet()) {
			textureRegions.put(entry.getKey(), entry.getValue());
		}
		addTexture(textureAtlas.getSourceTexture());
	}

	/**
	 * Removes all TextureRegions of the specified TextureAtlas from this TextureManager.<br>
	 * The source texture of the specified TextureAtlas will not be removed from this TextureManager.
	 * 
	 * @param textureAtlas TextureAtlas.
	 */
	public void removeTextureAtlas(TextureAtlas textureAtlas) {
		Map<String, TextureRegion> atlasContent = textureAtlas.getTextureRegions();
		for (Map.Entry<String, TextureRegion> entry : atlasContent.entrySet()) {
			textureRegions.remove(entry.getKey());
		}
	}

	/**
	 * Adds the specified Texture to this TextureManager.
	 * 
	 * @param texture Texture.
	 * @return true if it has been added, false if a Texture with the same path (relative to the assets folder) was
	 *         previously registered in this TextureManager
	 */
	public boolean addTexture(Texture texture) {
		int j;
		Texture aux;
		if (activeTextures.contains(texture)) {
			return false;
		}
		activeTextures.add(texture);
		j = activeTextures.size() - 1;
		for (int i = j - 1; i >= 0; i--) {
			if (activeTextures.get(i).compareTo(activeTextures.get(j)) == 0) {
				break;
			}
			if (activeTextures.get(i).compareTo(activeTextures.get(j)) > 0) {
				aux = activeTextures.set(i, activeTextures.get(j));
				activeTextures.set(j, aux);
				j--;
			} else {
				break;
			}
		}
		if (!texture.isLoaded()) {
			texturesToLoad++;
		}
		return true;
	}

	/**
	 * Adds all textures of a Font to this TextureManager.
	 * 
	 * @param font Font.
	 */
	public void addFontTextures(Font font) {
		SparseArray<Texture> texturePages = font.getTexturePages();
		for (int i = 0; i < texturePages.size(); i++) {
			addTexture(texturePages.valueAt(i));
		}
	}

	/**
	 * Removes the specified Texture from the collection of active textures.<br>
	 * The texture is moved to a pool so it won't be reloaded but the object will not be collected by the GC until
	 * {@link #clearRemovedTextures()} is called.
	 * 
	 * @param texture Texture to be removed from the collection of active textures.
	 */
	public void removeTexture(Texture texture) {
		Texture removedTexture;
		int mid;
		int low = 0;
		int high = activeTextures.size() - 1;
		while (low <= high) {
			mid = (low + high) / 2;
			if (activeTextures.get(mid).compareTo(texture) < 0) {
				low = mid + 1;
			} else if (activeTextures.get(mid).compareTo(texture) > 0) {
				high = mid - 1;
			} else {
				removedTexture = activeTextures.remove(mid);
				removedTextures.put(removedTexture);
				if (!removedTexture.isLoaded()) {
					texturesToLoad--;
				}
			}
		}
	}

	/**
	 * Removes all Textures from the collection of active textures.<br>
	 * The textures are moved to a pool so they won't be reloaded but the objects will not be collected by the GC until
	 * {@link #clearRemovedTextures()} is called.
	 */
	public void removeAllTextures() {
		for (int i = activeTextures.size() - 1; i >= 0; i--) {
			removedTextures.put(activeTextures.remove(i));
		}
		texturesToLoad = 0;
	}

	/**
	 * Removes all textures from the pool of removed textures. Active textures won't be removed.<br>
	 * This method must be called from the rendering thread.
	 * 
	 * @see #removeTexture(Texture)
	 * @see #removeAllTextures()
	 */
	public void clearRemovedTextures() {
		for (int i = removedTextures.size() - 1; i >= 0; i--) {
			removedTextures.get().delete();
		}
	}

	/**
	 * Removes all textures stored in this TextureManager.<br>
	 * This method must be called from the rendering thread.
	 */
	public void clearAll() {
		int activeTexturesSize = activeTextures.size();
		int removedTexturesSize = removedTextures.size();
		int maxSize = Math.max(activeTexturesSize, removedTexturesSize);
		for (int i = maxSize - 1; i >= 0; i--) {
			if (i < activeTexturesSize) {
				activeTextures.remove(i).delete();
			}
			if (i < removedTexturesSize) {
				removedTextures.get().delete();
			}
		}
		texturesToLoad = 0;
		textureRegions.clear();
	}

	/**
	 * Loads all textures that have not been previously loaded.<br>
	 * This method must be called from the rendering thread.
	 */
	public void loadTextures() {
		if (texturesToLoad > 0) {
			for (int i = 0; i < activeTextures.size(); i++) {
				if (!activeTextures.get(i).isLoaded()) {
					activeTextures.get(i).loadTexture(context);
					texturesToLoad--;
					if (texturesToLoad == 0) {
						break;
					}
				}
			}
			texturesToLoad = 0;
		}
	}

	/**
	 * Loads all textures.<br>
	 * This method must be called from the rendering thread.
	 */
	public void loadAllTextures() {
		for (int i = 0; i < activeTextures.size(); i++) {
			activeTextures.get(i).loadTexture(context);
		}
		texturesToLoad = 0;
	}

}
