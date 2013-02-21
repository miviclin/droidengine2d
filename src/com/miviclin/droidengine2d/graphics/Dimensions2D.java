package com.miviclin.droidengine2d.graphics;

public class Dimensions2D {
	
	private float width;
	private float height;
	
	public Dimensions2D(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void set(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public void set(Dimensions2D dimensions) {
		this.width = dimensions.width;
		this.height = dimensions.height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
}
