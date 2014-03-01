package com.miviclin.droidengine2d.graphics.texture;

/**
 * TextureRegion is a region of a texture.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureRegion {

	private final Texture texture;
	private float u1;
	private float v1;
	private float u2;
	private float v2;
	private float x;
	private float y;
	private float width;
	private float height;
	private boolean flippedHorizontally;
	private boolean flippedVertically;

	/**
	 * Creates a new TextureRegion.
	 * 
	 * @param texture Texture this TextureRegion belongs to.
	 * @param x Position of this region in the X axis in pixels. Relative to the top-left corner of the texture.
	 * @param y Position of this region in the Y axis in pixels. Relative to the top-left corner of the texture.
	 * @param width Width of this region.
	 * @param height Height of this region.
	 */
	public TextureRegion(Texture texture, float x, float y, float width, float height) {
		if (texture == null) {
			throw new IllegalArgumentException("texture can not be null");
		}
		this.texture = texture;
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
		this.flippedHorizontally = false;
		this.flippedVertically = false;
	}

	/**
	 * Creates a new TextureRegion, copying from the specified TextureRegion.
	 * 
	 * @param textureRegion TextureRegion.
	 */
	public TextureRegion(TextureRegion textureRegion) {
		this.texture = textureRegion.texture;
		this.u1 = textureRegion.u1;
		this.v1 = textureRegion.v1;
		this.u2 = textureRegion.u2;
		this.v2 = textureRegion.v2;
		this.x = textureRegion.x;
		this.y = textureRegion.y;
		this.width = textureRegion.width;
		this.height = textureRegion.height;
		this.flippedHorizontally = textureRegion.flippedHorizontally;
		this.flippedVertically = textureRegion.flippedVertically;
	}

	/**
	 * Returns the Texture this TextureRegion belongs to.
	 * 
	 * @return Texture
	 */
	public final Texture getTexture() {
		return texture;
	}

	/**
	 * Returns the U1 coordinate of this TextureRegion.
	 * 
	 * @return U1 (value between 0.0f and 1.0f).
	 */
	public final float getU1() {
		return u1;
	}

	/**
	 * Returns the V1 coordinate of this TextureRegion.
	 * 
	 * @return V1 (value between 0.0f and 1.0f).
	 */
	public final float getV1() {
		return v1;
	}

	/**
	 * Returns the U2 coordinate of this TextureRegion.
	 * 
	 * @return U2 (value between 0.0f and 1.0f).
	 */
	public final float getU2() {
		return u2;
	}

	/**
	 * Returns the V2 coordinate of this TextureRegion.
	 * 
	 * @return V2 (value between 0.0f and 1.0f).
	 */
	public final float getV2() {
		return v2;
	}

	/**
	 * Returns the position of this region in the X axis in pixels. Relative to the top-left corner of the texture.
	 * 
	 * @return x
	 */
	public final float getX() {
		return x;
	}

	/**
	 * Sets the position of this region in the X axis in pixels. Relative to the top-left corner of the texture.
	 * 
	 * @param x New value.
	 */
	public final void setX(float x) {
		if (x < 0) {
			throw new IllegalArgumentException("x must be equal or greater than 0");
		}
		if ((x + width) > texture.getWidth()) {
			throw new IllegalArgumentException("The TextureRegion must be fully contained inside the texture");
		}
		this.u1 = x / texture.getWidth();
		this.u2 = (x + width) / texture.getWidth();
		this.x = x;
	}

	/**
	 * Returns the position of this region in the Y axis in pixels. Relative to the top-left corner of the texture.
	 * 
	 * @return y
	 */
	public final float getY() {
		return y;
	}

	/**
	 * Sets the position of this region in the Y axis in pixels. Relative to the top-left corner of the texture.
	 * 
	 * @param y New value.
	 */
	public final void setY(float y) {
		if (y < 0) {
			throw new IllegalArgumentException("y must be equal or greater than 0");
		}
		if ((y + height) > texture.getHeight()) {
			throw new IllegalArgumentException("The TextureRegion must be fully contained inside the texture");
		}
		this.v1 = y / texture.getHeight();
		this.v2 = (y + height) / texture.getHeight();
		this.y = y;
	}

	/**
	 * Returns the width of this TextureRegion, in pixels.
	 * 
	 * @return the width of this TextureRegion
	 */
	public final float getWidth() {
		return width;
	}

	/**
	 * Sets the width of this TextureRegion, in pixels.
	 * 
	 * @param width New value.
	 */
	public final void setWidth(float width) {
		if (width <= 0) {
			throw new IllegalArgumentException("width must be greater than 0");
		}
		this.width = width;
	}

	/**
	 * Returns the height of this TextureRegion, in pixels.
	 * 
	 * @return the height of this TextureRegion
	 */
	public final float getHeight() {
		return height;
	}

	/**
	 * Sets the height of this TextureRegion, in pixels.
	 * 
	 * @param height New value.
	 */
	public final void setHeight(float height) {
		if (height <= 0) {
			throw new IllegalArgumentException("height must be greater than 0");
		}
		this.height = height;
	}

	/**
	 * Returns true if this TextureRegion is flipped horizontally.
	 * 
	 * @return true if this TextureRegion is flipped horizontally, false otherwise
	 */
	public final boolean isFlippedHorizontally() {
		return flippedHorizontally;
	}

	/**
	 * Flips this TextureRegion horizontally.
	 */
	public final void flipHorizontally() {
		float previousU1 = u1;
		u1 = u2;
		u2 = previousU1;
		flippedHorizontally = !flippedHorizontally;
	}

	/**
	 * Returns true if this TextureRegion is flipped vertically.
	 * 
	 * @return true if this TextureRegion is flipped vertically, false otherwise
	 */
	public final boolean isFlippedVertically() {
		return flippedVertically;
	}

	/**
	 * Flips this TextureRegion vertically.
	 */
	public final void flipVertically() {
		float previousV1 = v1;
		v1 = v2;
		v2 = previousV1;
		flippedVertically = !flippedVertically;
	}

}
