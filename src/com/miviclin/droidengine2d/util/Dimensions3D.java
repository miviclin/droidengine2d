package com.miviclin.droidengine2d.util;

public class Dimensions3D {
	
	private float width;
	private float height;
	private float depth;
	
	public Dimensions3D(float width, float height, float depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public void set(float width, float height, float depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public void set(Dimensions3D dimensions) {
		this.width = dimensions.width;
		this.height = dimensions.height;
		this.depth = dimensions.depth;
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
	
	public float getDepth() {
		return depth;
	}
	
	public void setDepth(float depth) {
		this.depth = depth;
	}
	
}
