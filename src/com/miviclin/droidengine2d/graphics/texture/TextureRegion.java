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
	}

	/**
	 * Flips the U coordinate of this TextureRegion.
	 * 
	 * @return Returns this TextureRegion
	 */
	public TextureRegion flipX() {
		float u = u1;
		u1 = u2;
		u2 = u;
		return this;
	}

	/**
	 * Flips the V coordinate of this TextureRegion.
	 * 
	 * @return Returns this TextureRegion
	 */
	public TextureRegion flipY() {
		float v = v1;
		v1 = v2;
		v2 = v;
		return this;
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
	public float getU1() {
		return u1;
	}

	/**
	 * Returns the V1 coordinate of this TextureRegion.
	 * 
	 * @return V1 (value between 0.0f and 1.0f).
	 */
	public float getV1() {
		return v1;
	}

	/**
	 * Returns the U2 coordinate of this TextureRegion.
	 * 
	 * @return U2 (value between 0.0f and 1.0f).
	 */
	public float getU2() {
		return u2;
	}

	/**
	 * Returns the V2 coordinate of this TextureRegion.
	 * 
	 * @return V2 (value between 0.0f and 1.0f).
	 */
	public float getV2() {
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
}
