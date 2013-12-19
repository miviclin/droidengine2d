package com.miviclin.droidengine2d.util;

import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Transform. It has position, scale, rotation...
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Transform {

	private Vector2 position;
	private Vector2 scale;
	private Vector2 origin;
	private float rotation;

	/**
	 * Creates a new Transform with the specified position and scale.<br>
	 * The default origin is (scale.getX() / 2, scale.getY() / 2).
	 * 
	 * @param position Position.
	 * @param scale Scale.
	 */
	public Transform(Vector2 position, Vector2 scale) {
		this(position, scale, new Vector2(scale.getX() / 2, scale.getY() / 2), 0.0f);
	}

	/**
	 * Creates a new Transform with the specified position, scale, origin and rotation.
	 * 
	 * @param position Position.
	 * @param scale Scale.
	 * @param origin Origin (must be a point between (0, 0) and (scale.getX(), scale.getY())).
	 * @param rotation Rotation angle.
	 */
	public Transform(Vector2 position, Vector2 scale, Vector2 origin, float rotation) {
		super();
		checkNotNull(position, "position");
		checkNotNull(scale, "scale");
		checkNotNull(origin, "origin");
		this.position = position;
		this.scale = scale;
		this.origin = origin;
		this.rotation = rotation;
	}

	/**
	 * Returns the rotation angle around the origin.
	 * 
	 * @return rotation angle
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * Sets the rotation angle around the origin.
	 * 
	 * @param rotation Rotation angle.
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * Returns the position.
	 * 
	 * @return position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Returns the scale.
	 * 
	 * @return scale
	 */
	public Vector2 getScale() {
		return scale;
	}

	/**
	 * Returns the origin.
	 * 
	 * @return origin
	 */
	public Vector2 getOrigin() {
		return origin;
	}

	/**
	 * Checks if the specified Vector2 is not null. If it is null, throws an exception.
	 * 
	 * @param vector Vector2.
	 * @param variableName Name of the Vector2 variable. This name will be used in the message of the exception.
	 */
	private void checkNotNull(Vector2 vector, String variableName) {
		if (vector == null) {
			throw new IllegalArgumentException(variableName + " can not be null");
		}
	}

}
