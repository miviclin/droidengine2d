/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.miviclin.droidengine2d.graphics.texture;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.miviclin.droidengine2d.resources.AssetsLoader;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Texture.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Texture implements Comparable<Texture> {

	private String path;
	private int textureId;
	private int minFilter;
	private int magFilter;
	private int wrapS;
	private int wrapT;
	private int width;
	private int height;
	private boolean loaded;

	/**
	 * Creates a new Texture.
	 * 
	 * @param context Context.
	 * @param path File path. Relative to the assets folder.
	 */
	public Texture(Context context, String path) {
		Vector2 bitmapBounds = AssetsLoader.getBitmapBounds(context, path);
		this.path = path;
		this.textureId = -1;
		this.minFilter = GLES20.GL_LINEAR;
		this.magFilter = GLES20.GL_LINEAR;
		this.wrapS = GLES20.GL_REPEAT;
		this.wrapT = GLES20.GL_REPEAT;
		this.width = (int) bitmapBounds.getX();
		this.height = (int) bitmapBounds.getY();
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("An error occurred while loading the texture");
		}
		this.loaded = false;
	}

	/**
	 * Loads the texture and sets filters and wrap mode.
	 * 
	 * @param context Context.
	 */
	public void loadTexture(Context context) {
		Bitmap bitmap;
		allocateTextureId();
		bind();
		setFilters(minFilter, magFilter);
		setWrapMode(wrapS, wrapT);
		bitmap = AssetsLoader.loadBitmap(context, path);
		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
		unBind();
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		bitmap.recycle();
		loaded = true;
	}

	/**
	 * When this method is called, OpenGL will allocate memory for this texture and will generate a texture ID.<br>
	 * If this texture had a previously generated ID, {@link #delete()} should be called on this texture before
	 * generating a new one.
	 */
	protected void allocateTextureId() {
		int[] textures = new int[1];
		GLES20.glGenTextures(1, textures, 0);
		textureId = textures[0];
	}

	/**
	 * Binds this texture to the OpenGL context.
	 */
	public void bind() {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
	}

	/**
	 * Unbinds this texture to the OpenGL context.
	 */
	public void unBind() {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}

	/**
	 * Sets the min and mag filters of this texture.
	 * 
	 * @param minFilter Min. filter.
	 * @param magFilter Mag. filter.
	 */
	public void setFilters(int minFilter, int magFilter) {
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, minFilter);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, magFilter);
	}

	/**
	 * Sets the wrapmode of this texture.
	 * 
	 * @param wrapS Wrap S.
	 * @param wrapT Wrap T.
	 */
	public void setWrapMode(int wrapS, int wrapT) {
		this.wrapS = wrapS;
		this.wrapT = wrapT;
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, wrapS);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, wrapT);
	}

	/**
	 * Removes this texture from the OpenGL context.<br>
	 * This method should be called when the texture is not needed anymore, to release resources.
	 */
	public void delete() {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		int[] textures = new int[1];
		textures[0] = textureId;
		GLES20.glDeleteTextures(1, textures, 0);
	}

	/**
	 * Returns the width of this texture.
	 * 
	 * @return the width of this texture
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns the height of this texture.
	 * 
	 * @return the height of this texture
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns true if the texture has been loaded.
	 * 
	 * @return true if the texture has been loaded, false otherwise
	 * @see #loadTexture(Context)
	 */
	public boolean isLoaded() {
		return loaded;
	}

	@Override
	public int compareTo(Texture texture) {
		return path.compareTo(texture.path);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + height;
		result = prime * result + (loaded ? 1231 : 1237);
		result = prime * result + magFilter;
		result = prime * result + minFilter;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + textureId;
		result = prime * result + width;
		result = prime * result + wrapS;
		result = prime * result + wrapT;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Texture other = (Texture) obj;
		if (height != other.height) {
			return false;
		}
		if (loaded != other.loaded) {
			return false;
		}
		if (magFilter != other.magFilter) {
			return false;
		}
		if (minFilter != other.minFilter) {
			return false;
		}
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		if (textureId != other.textureId) {
			return false;
		}
		if (width != other.width) {
			return false;
		}
		if (wrapS != other.wrapS) {
			return false;
		}
		if (wrapT != other.wrapT) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "" + path;
	}

}
