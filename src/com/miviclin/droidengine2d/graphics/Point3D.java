package com.miviclin.droidengine2d.graphics;

public class Point3D {
	
	private float x;
	private float y;
	private float z;
	
	public Point3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void translate(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(Point3D point) {
		this.x = point.x;
		this.y = point.y;
		this.z = point.z;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getZ() {
		return z;
	}
	
	public void setZ(float z) {
		this.z = z;
	}
	
}
