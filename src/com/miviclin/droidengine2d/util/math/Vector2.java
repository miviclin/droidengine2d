package com.miviclin.droidengine2d.util.math;

public class Vector2 {
	
	private float x;
	private float y;
	
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2 set(Vector2 vector) {
		this.x = vector.x;
		this.y = vector.y;
		return this;
	}
	
	public Vector2 add(float scalar) {
		this.x += scalar;
		this.y += scalar;
		return this;
	}
	
	public Vector2 add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2 add(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}
	
	public Vector2 subtract(float scalar) {
		this.x -= scalar;
		this.y -= scalar;
		return this;
	}
	
	public Vector2 subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector2 subtract(Vector2 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}
	
	public Vector2 multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	
	public Vector2 multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Vector2 multiply(Vector2 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		return this;
	}
	
	public Vector2 divide(float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}
	
	public Vector2 divide(float x, float y) {
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public Vector2 divide(Vector2 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
		return this;
	}
	
	public float dotProduct(float x, float y) {
		return this.x * x + this.y * y;
	}
	
	public float dotProduct(Vector2 vector) {
		return x * vector.x + y * vector.y;
	}
	
	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}
	
	public Vector2 normalize() {
		float length = (float) Math.sqrt(x * x + y * y);
		if (length == 0) {
			return this;
		}
		return this.divide(length);
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
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
