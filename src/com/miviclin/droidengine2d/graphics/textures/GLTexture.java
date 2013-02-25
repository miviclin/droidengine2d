package com.miviclin.droidengine2d.graphics.textures;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.miviclin.droidengine2d.resources.AssetsLoader;
import com.miviclin.droidengine2d.util.Dimensions2D;

public class GLTexture implements Comparable<GLTexture> {
	
	private String path;
	private int textureID;
	private int minFilter;
	private int magFilter;
	private int wrapS;
	private int wrapT;
	private int width;
	private int height;
	private boolean loaded;
	
	public GLTexture(Context context, String path) {
		Dimensions2D bitmapBounds = AssetsLoader.getBitmapBounds(context, path);
		this.path = path;
		this.textureID = 0;
		this.minFilter = GLES20.GL_LINEAR;
		this.magFilter = GLES20.GL_LINEAR;
		this.wrapS = GLES20.GL_REPEAT;
		this.wrapT = GLES20.GL_REPEAT;
		this.width = (int) bitmapBounds.getWidth();
		this.height = (int) bitmapBounds.getHeight();
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("An error occurred while loading the texture");
		}
		this.loaded = false;
	}
	
	public void loadTexture(Context context) {
		Bitmap bitmap;
		allocateTextureID();
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
	
	protected void allocateTextureID() {
		int[] textures = new int[1];
		if (textureID != 0) {
			delete();
		}
		GLES20.glGenTextures(1, textures, 0);
		textureID = textures[0];
	}
	
	public void bind() {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID);
	}
	
	public void unBind() {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
	}
	
	public void setFilters(int minFilter, int magFilter) {
		this.minFilter = minFilter;
		this.magFilter = magFilter;
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, minFilter);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, magFilter);
	}
	
	public void setWrapMode(int wrapS, int wrapT) {
		this.wrapS = wrapS;
		this.wrapT = wrapT;
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, wrapS);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, wrapT);
	}
	
	public void delete() {
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID);
		int[] textures = new int[1];
		textures[0] = textureID;
		GLES20.glDeleteTextures(1, textures, 0);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	@Override
	public int compareTo(GLTexture texture) {
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
		result = prime * result + textureID;
		result = prime * result + width;
		result = prime * result + wrapS;
		result = prime * result + wrapT;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GLTexture other = (GLTexture) obj;
		if (height != other.height)
			return false;
		if (loaded != other.loaded)
			return false;
		if (magFilter != other.magFilter)
			return false;
		if (minFilter != other.minFilter)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (textureID != other.textureID)
			return false;
		if (width != other.width)
			return false;
		if (wrapS != other.wrapS)
			return false;
		if (wrapT != other.wrapT)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "" + path;
	}
	
}
