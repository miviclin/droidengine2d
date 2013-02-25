package com.miviclin.droidengine2d.graphics.sprites;

import com.miviclin.droidengine2d.graphics.textures.TextureRegion;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.Point2D;

public class Sprite {
	
	private TextureRegion textureRegion;
	private Point2D position = new Point2D(0.0f, 0.0f);
	private Point2D center = new Point2D(0.0f, 0.0f);
	private Dimensions2D dimensions = new Dimensions2D(1.0f, 1.0f);
	private Point2D rotationPoint = new Point2D(0.0f, 0.0f);
	private float rotationAroundPoint;
	private float rotationAroundCenter;
	
	public Sprite(float x, float y, float width, float height, TextureRegion textureRegion) {
		checkDimensions(width, height);
		checkTextureRegion(textureRegion);
		this.position.set(x, y);
		this.dimensions.set(width, height);
		this.rotationAroundPoint = 0.0f;
		this.rotationAroundCenter = 0.0f;
		this.textureRegion = textureRegion;
		this.center.set(width / 2, height / 2);
	}
	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(float x, float y) {
		position.set(x, y);
	}
	
	public void setPosition(Point2D position) {
		position.set(position);
	}
	
	public Dimensions2D getDimensions() {
		return dimensions;
	}
	
	public void setDimensions(float width, float height) {
		dimensions.set(width, height);
		center.set(width / 2, height / 2);
	}
	
	public float getRotation() {
		return rotationAroundCenter;
	}
	
	public void setRotation(float angle) {
		rotationAroundCenter = angle;
	}
	
	public float getRotationAroundPoint() {
		return rotationAroundPoint;
	}
	
	public Point2D getRotationPoint() {
		return rotationPoint;
	}
	
	public void setRotationAroundExternalPoint(float angle, float pointX, float pointY) {
		rotationAroundPoint = angle;
		rotationPoint.set(pointX, pointY);
	}
	
	public void setRotationAroundExternalPoint(float angle, Point2D point) {
		rotationAroundPoint = angle;
		rotationPoint.set(point);
	}
	
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
	public Point2D getCenter() {
		return center;
	}
	
	private void checkDimensions(float width, float height) {
		if (width <= 0) {
			throw new IllegalArgumentException("width must be greater than 0");
		}
		if (height <= 0) {
			throw new IllegalArgumentException("height must be greater than 0");
		}
	}
	
	private void checkTextureRegion(TextureRegion textureRegion) {
		if (textureRegion == null) {
			throw new IllegalArgumentException("The TextureRegion can not be null");
		}
	}
	
}
