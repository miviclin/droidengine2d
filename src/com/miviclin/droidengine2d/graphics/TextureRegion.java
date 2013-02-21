package com.miviclin.droidengine2d.graphics;

public class TextureRegion { // TODO: revisar el calculo de U y V (restar medio texel?)

	private final GLTexture texture;
	private float u1;
	private float v1;
	private float u2;
	private float v2;
	private float offsetX;
	private float offsetY;
	private float width;
	private float height;
	
	public TextureRegion(GLTexture texture, float offsetX, float offsetY, float width, float height) {
		if (texture == null) {
			throw new IllegalArgumentException("texture can not be null");
		}
		this.texture = texture;
		setWidth(width);
		setHeight(height);
		setOffsetX(offsetX);
		setOffsetY(offsetY);
	}
	
	public TextureRegion flipX() {
		float u = u1;
		u1 = u2;
		u2 = u;
		return this;
	}
	
	public TextureRegion flipY() {
		float v = v1;
		v1 = v2;
		v2 = v;
		return this;
	}
	
	public final GLTexture getTexture() {
		return texture;
	}
	
	public float getU1() {
		return u1;
	}
	
	public float getV1() {
		return v1;
	}
	
	public float getU2() {
		return u2;
	}
	
	public float getV2() {
		return v2;
	}
	
	public final float getOffsetX() {
		return offsetX;
	}
	
	public final void setOffsetX(float offsetX) {
		if (offsetX < 0) {
			throw new IllegalArgumentException("offsetX must be equal or greater than 0");
		}
		if ((offsetX + width) > texture.getWidth()) {
			throw new IllegalArgumentException("The TextureRegion must be fully contained inside the texture");
		}
		this.u1 = offsetX / texture.getWidth();
		this.u2 = (offsetX + width) / texture.getWidth();
		this.offsetX = offsetX;
	}
	
	public final float getOffsetY() {
		return offsetY;
	}
	
	public final void setOffsetY(float offsetY) {
		if (offsetY < 0) {
			throw new IllegalArgumentException("offsetY must be equal or greater than 0");
		}
		if ((offsetY + height) > texture.getHeight()) {
			throw new IllegalArgumentException("The TextureRegion must be fully contained inside the texture");
		}
		this.v1 = offsetY / texture.getHeight();
		this.v2 = (offsetY + height) / texture.getHeight();
		this.offsetY = offsetY;
	}
	
	public final float getWidth() {
		return width;
	}
	
	public final void setWidth(float width) {
		if (width <= 0) {
			throw new IllegalArgumentException("width must be greater than 0");
		}
		this.width = width;
	}
	
	public final float getHeight() {
		return height;
	}
	
	public final void setHeight(float height) {
		if (height <= 0) {
			throw new IllegalArgumentException("height must be greater than 0");
		}
		this.height = height;
	}
	
}
