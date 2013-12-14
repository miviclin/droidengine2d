package com.miviclin.droidengine2d.util.math;

/**
 * 3D Vector.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Vector3 {

	private float x;
	private float y;
	private float z;

	/**
	 * Creates a new Vector3.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Sets the specified values to this Vector3.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return this Vector3
	 */
	public final Vector3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	/**
	 * Sets the specified values to this Vector3.
	 * 
	 * @param vector Vector3 whose values will be copied to this Vector3.
	 * @return this Vector3
	 */
	public final Vector3 set(Vector3 vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
		return this;
	}

	/**
	 * Copies the specified vector into the output vector.
	 * 
	 * @param output Output vector.
	 * @param vector Vector whose coordinates will be copied into the output vector.
	 */
	public static final void copy(Vector3 output, Vector3 vector) {
		output.x = vector.x;
		output.y = vector.y;
		output.z = vector.z;
	}

	/**
	 * Adds the specified scalar to both coordinates of this vector.
	 * 
	 * @param scalar Scalar.
	 * @return this Vector3
	 */
	public final Vector3 add(float scalar) {
		this.x += scalar;
		this.y += scalar;
		this.z += scalar;
		return this;
	}

	/**
	 * Adds the specified scalar to both coordinates of the specified vector and stores the result in the output vector.
	 * 
	 * @param output Output vector.
	 * @param vector Vector.
	 * @param escalar Scalar.
	 */
	public static final void add(Vector3 output, Vector3 vector, float scalar) {
		output.x = vector.x + scalar;
		output.y = vector.y + scalar;
		output.z = vector.z + scalar;
	}

	/**
	 * Adds the specified vector to this vector.
	 * 
	 * @param x Value to be added to the X coordinate of this vector.
	 * @param y Value to be added to the Y coordinate of this vector.
	 * @param z Value to be added to the Z coordinate of this vector.
	 * @return this Vector3
	 */
	public final Vector3 add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	/**
	 * Adds the specified vector to this vector.
	 * 
	 * @param vector Vector to be added to this vector.
	 * @return this Vector3
	 */
	public final Vector3 add(Vector3 vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		return this;
	}

	/**
	 * Adds v2 to v1 and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 */
	public static final void add(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.x + v2.x;
		output.y = v1.y + v2.y;
		output.z = v1.z + v2.z;
	}

	/**
	 * Substracts the specified scalar from all coordinates of this vector.
	 * 
	 * @param scalar Scalar.
	 * @return this Vector3
	 */
	public final Vector3 subtract(float scalar) {
		this.x -= scalar;
		this.y -= scalar;
		this.z -= scalar;
		return this;
	}

	/**
	 * Substracts the specified scalar from all coordinates of the specified vector and stores the result in the output
	 * vector.
	 * 
	 * @param output Result.
	 * @param vector Vector.
	 * @param scalar Scalar.
	 */
	public static final void subtract(Vector3 output, Vector3 vector, float scalar) {
		output.x = vector.x - scalar;
		output.y = vector.y - scalar;
		output.z = vector.z - scalar;
	}

	/**
	 * Substracts the specified vector from all coordinates of this vector.
	 * 
	 * @param x Value to be substracted from the X coordinate of this vector.
	 * @param y Value to be substracted from the Y coordinate of this vector.
	 * @param z Value to be substracted from the Z coordinate of this vector.
	 * @return this Vector3
	 */
	public final Vector3 subtract(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	/**
	 * Substracts the specified vector from this vector.
	 * 
	 * @param vector Vector to be substracted from this vector.
	 * @return this Vector3
	 */
	public final Vector3 subtract(Vector3 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		return this;
	}

	/**
	 * Substracts v2 from v1 and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 */
	public static final void subtract(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.x - v2.x;
		output.y = v1.y - v2.y;
		output.z = v1.z - v2.z;
	}

	/**
	 * Multiplies this vector by the specified scalar.
	 * 
	 * @param scalar Scalar.
	 * @return this Vector3
	 */
	public final Vector3 multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		return this;
	}

	/**
	 * Multiplies the specified vector by the specified scalar and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param vector Vector.
	 * @param scalar Scalar.
	 */
	public static final void multiply(Vector3 output, Vector3 vector, float scalar) {
		output.x = vector.x * scalar;
		output.y = vector.y * scalar;
		output.z = vector.z * scalar;
	}

	/**
	 * Multiplies this vector by the specified vector.
	 * 
	 * @param x The X coordinate of this vector will be multiplied by this value.
	 * @param y The Y coordinate of this vector will be multiplied by this value.
	 * @param z The Z coordinate of this vector will be multiplied by this value.
	 * @return this Vector2
	 */
	public final Vector3 multiply(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
		return this;
	}

	/**
	 * Multiplies this vector by the specified vector.
	 * 
	 * @param vector Vector.
	 * @return this Vector3
	 */
	public final Vector3 multiply(Vector3 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		this.z *= vector.z;
		return this;
	}

	/**
	 * Multiplies v1 by v2 and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 */
	public static final void multiply(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.x * v2.x;
		output.y = v1.y * v2.y;
		output.z = v1.z * v2.z;
	}

	/**
	 * Divides this vector by the specified scalar.
	 * 
	 * @param scalar Scalar.
	 * @return this Vector3
	 */
	public final Vector3 divide(float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		this.z /= scalar;
		return this;
	}

	/**
	 * Divides the specified vector by the specified scalar and stores the result in the output vector.
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param scalar Scalar.
	 */
	public static final void divide(Vector3 output, Vector3 vector, float scalar) {
		output.x = vector.x / scalar;
		output.y = vector.y / scalar;
		output.z = vector.z / scalar;
	}

	/**
	 * Divides this vector by the specified vector.
	 * 
	 * @param x The X coordinate of this vector will be divided by this value.
	 * @param y The Y coordinate of this vector will be multiplied by this value.
	 * @param z The Z coordinate of this vector will be multiplied by this value.
	 * @return this Vector3
	 */
	public final Vector3 divide(float x, float y, float z) {
		this.x /= x;
		this.y /= y;
		this.z /= z;
		return this;
	}

	/**
	 * Divides this vector by the specified vector.
	 * 
	 * @param vector Vector.
	 * @return this Vector3
	 */
	public final Vector3 divide(Vector3 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
		this.z /= vector.z;
		return this;
	}

	/**
	 * Divides v1 by the v2 and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 */
	public static final void divide(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.x / v2.x;
		output.y = v1.y / v2.y;
		output.z = v1.z / v2.z;
	}

	/**
	 * Performs the cross product of this vector and the specified vector and stores the result in this vector.
	 * 
	 * @param x X coordinate of the specified vector.
	 * @param y Y coordinate of the specified vector.
	 * @param z Z coordinate of the specified vector.
	 * @return this Vector3
	 */
	public final Vector3 crossProduct(float x, float y, float z) {
		this.x = this.y * z - this.z * y;
		this.y = this.z * x - this.x * z;
		this.z = this.x * y - this.y * x;
		return this;
	}

	/**
	 * Performs the cross product of this vector and the specified vector and stores the result in this vector.
	 * 
	 * @param vector Vector
	 * @return this Vector3
	 */
	public final Vector3 crossProduct(Vector3 vector) {
		this.x = y * vector.z - z * vector.y;
		this.y = z * vector.x - x * vector.z;
		this.z = x * vector.y - y * vector.x;
		return this;
	}

	/**
	 * Performs the cross product of this vector and the specified vector and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 */
	public static final void crossProduct(Vector3 output, Vector3 v1, Vector3 v2) {
		output.x = v1.y * v2.z - v1.z * v2.y;
		output.y = v1.z * v2.x - v1.x * v2.z;
		output.z = v1.x * v2.y - v1.y * v2.x;
	}

	/**
	 * Performs the dot product of this vector and the specified vector and stores the result in this vector.
	 * 
	 * @param x X coordinate of the specified vector.
	 * @param y Y coordinate of the specified vector.
	 * @param z Z coordinate of the specified vector.
	 * @return dot product of v1 and v2
	 */
	public final float dotProduct(float x, float y, float z) {
		return this.x * x + this.y * y + this.z * z;
	}

	/**
	 * Performs the dot product of this vector and the specified vector and stores the result in this vector.
	 * 
	 * @param vector Vector.
	 * @return dot product of v1 and v2
	 */
	public final float dotProduct(Vector3 vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	/**
	 * Performs the dot product of v1 and v2.
	 * 
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 * @return dot product of v1 and v2
	 */
	public static final float dotProduct(Vector3 v1, Vector3 v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}

	/**
	 * Calculates the length of this vector.
	 * 
	 * @return length of this vector
	 */
	public final float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Calculates the length of the specified vector.
	 * 
	 * @param vector Vector.
	 * @return length of the specified vector
	 */
	public static final float length(Vector3 vector) {
		return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
	}

	/**
	 * Normalizes this vector.
	 * 
	 * @return this Vector3
	 */
	public final Vector3 normalize() {
		float length = (float) Math.sqrt(x * x + y * y + z * z);
		if (length == 0) {
			return this;
		}
		return this.divide(length);
	}

	/**
	 * Normalizes the specified vector and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param vector Vector.
	 */
	public static final void normalize(Vector3 output, Vector3 vector) {
		float length = Vector3.length(vector);
		if (length == 0) {
			Vector3.copy(output, vector);
			return;
		}
		Vector3.copy(output, vector.divide(length));
	}

	/**
	 * Converts the coordinates of this vector to their absolute values.
	 * 
	 * @return this Vector3
	 */
	public final Vector3 abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		this.z = Math.abs(this.z);
		return this;
	}

	/**
	 * Converts the coordinates of the specified vector to their absolute values and stores the result in the output
	 * vector.
	 * 
	 * @param output Result.
	 * @param vector Vector.
	 */
	public static final void abs(Vector3 output, Vector3 vector) {
		output.x = Math.abs(vector.x);
		output.y = Math.abs(vector.y);
		output.z = Math.abs(vector.z);
	}

	/**
	 * Returns the X coordinate of this vector.
	 * 
	 * @return X
	 */
	public final float getX() {
		return x;
	}

	/**
	 * Sets the value of the X coordinate of this vector.
	 * 
	 * @param x New value.
	 */
	public final void setX(float x) {
		this.x = x;
	}

	/**
	 * Returns the Y coordinate of this vector.
	 * 
	 * @return Y
	 */
	public final float getY() {
		return y;
	}

	/**
	 * Sets the value of the Y coordinate of this vector.
	 * 
	 * @param y New value.
	 */
	public final void setY(float y) {
		this.y = y;
	}

	/**
	 * Returns the Z coordinate of this vector.
	 * 
	 * @return Z
	 */
	public final float getZ() {
		return z;
	}

	/**
	 * Sets the value of the Z coordinate of this vector.
	 * 
	 * @param z New value.
	 */
	public final void setZ(float z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}

}
