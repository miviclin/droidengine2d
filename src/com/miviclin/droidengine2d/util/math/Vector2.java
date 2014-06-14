/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.miviclin.droidengine2d.util.math;

/**
 * 2D Vector.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Vector2 {

	private float x;
	private float y;

	/**
	 * Creates a new Vector2.
	 * 
	 * @param x
	 * @param y
	 */
	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the specified values to this Vector2.
	 * 
	 * @param x
	 * @param y
	 * @return this Vector2
	 */
	public final Vector2 set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	/**
	 * Sets the specified values to this Vector2.
	 * 
	 * @param vector Vector2 whose values will be copied to this Vector2.
	 */
	public final Vector2 set(Vector2 vector) {
		this.x = vector.x;
		this.y = vector.y;
		return this;
	}

	/**
	 * Copies the specified vector into the output vector.
	 * 
	 * @param output Output vector.
	 * @param vector Vector whose coordinates will be copied into the output vector.
	 */
	public static final void copy(Vector2 output, Vector2 vector) {
		output.x = vector.x;
		output.y = vector.y;
	}

	/**
	 * Adds the specified scalar to both coordinates of this vector.
	 * 
	 * @param scalar Scalar.
	 * @return this Vector2
	 */
	public final Vector2 add(float scalar) {
		this.x += scalar;
		this.y += scalar;
		return this;
	}

	/**
	 * Adds the specified scalar to both coordinates of the specified vector and stores the result in the output vector.
	 * 
	 * @param output Output vector.
	 * @param vector Vector.
	 * @param scalar Scalar.
	 */
	public static final void add(Vector2 output, Vector2 vector, float scalar) {
		output.x = vector.x + scalar;
		output.y = vector.y + scalar;
	}

	/**
	 * Adds the specified vector to this vector.
	 * 
	 * @param x Value to be added to the X coordinate of this vector.
	 * @param y Value to be added to the Y coordinate of this vector.
	 * @return this Vector2
	 */
	public final Vector2 add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}

	/**
	 * Adds the specified vector to this vector.
	 * 
	 * @param vector Vector to be added to this vector.
	 * @return this Vector2
	 */
	public final Vector2 add(Vector2 vector) {
		this.x += vector.x;
		this.y += vector.y;
		return this;
	}

	/**
	 * Adds v2 to v1 and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 */
	public static final void add(Vector2 output, Vector2 v1, Vector2 v2) {
		output.x = v1.x + v2.x;
		output.y = v1.y + v2.y;
	}

	/**
	 * Substracts the specified scalar from all coordinates of this vector.
	 * 
	 * @param scalar Scalar.
	 * @return this Vector2
	 */
	public final Vector2 subtract(float scalar) {
		this.x -= scalar;
		this.y -= scalar;
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
	public static final void subtract(Vector2 output, Vector2 vector, float scalar) {
		output.x = vector.x - scalar;
		output.y = vector.y - scalar;
	}

	/**
	 * Substracts the specified vector from all coordinates of this vector.
	 * 
	 * @param x Value to be substracted from the X coordinate of this vector.
	 * @param y Value to be substracted from the Y coordinate of this vector.
	 * @return this Vector2
	 */
	public final Vector2 subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	/**
	 * Substracts the specified vector from this vector.
	 * 
	 * @param vector Vector to be substracted from this vector.
	 * @return this Vector2
	 */
	public final Vector2 subtract(Vector2 vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		return this;
	}

	/**
	 * Substracts v2 from v1 and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 */
	public static final void subtract(Vector2 output, Vector2 v1, Vector2 v2) {
		output.x = v1.x - v2.x;
		output.y = v1.y - v2.y;
	}

	/**
	 * Multiplies this vector by the specified scalar.
	 * 
	 * @param scalar Scalar.
	 * @return this Vector2
	 */
	public final Vector2 multiply(float scalar) {
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}

	/**
	 * Multiplies the specified vector by the specified scalar and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param vector Vector.
	 * @param scalar Scalar.
	 */
	public static final void multiply(Vector2 output, Vector2 vector, float scalar) {
		output.x = vector.x * scalar;
		output.y = vector.y * scalar;
	}

	/**
	 * Multiplies this vector by the specified vector.
	 * 
	 * @param x The X coordinate of this vector will be multiplied by this value.
	 * @param y The Y coordinate of this vector will be multiplied by this value.
	 * @return this Vector2
	 */
	public final Vector2 multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}

	/**
	 * Multiplies this vector by the specified vector.
	 * 
	 * @param vector Vector.
	 * @return this Vector2
	 */
	public final Vector2 multiply(Vector2 vector) {
		this.x *= vector.x;
		this.y *= vector.y;
		return this;
	}

	/**
	 * Multiplies v1 by v2 and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 */
	public static final void multiply(Vector2 output, Vector2 v1, Vector2 v2) {
		output.x = v1.x * v2.x;
		output.y = v1.y * v2.y;
	}

	/**
	 * Divides this vector by the specified scalar.
	 * 
	 * @param scalar Scalar.
	 * @return this Vector2
	 */
	public final Vector2 divide(float scalar) {
		this.x /= scalar;
		this.y /= scalar;
		return this;
	}

	/**
	 * Divides the specified vector by the specified scalar and stores the result in the output vector.
	 * 
	 * @param output Resultado de la operacion
	 * @param vector Vector original
	 * @param scalar Scalar.
	 */
	public static final void divide(Vector2 output, Vector2 vector, float scalar) {
		output.x = vector.x / scalar;
		output.y = vector.y / scalar;
	}

	/**
	 * Divides this vector by the specified vector.
	 * 
	 * @param x The X coordinate of this vector will be divided by this value.
	 * @param y The Y coordinate of this vector will be multiplied by this value.
	 * @return this Vector2
	 */
	public final Vector2 divide(float x, float y) {
		this.x /= x;
		this.y /= y;
		return this;
	}

	/**
	 * Divides this vector by the specified vector.
	 * 
	 * @param vector Vector.
	 * @return this Vector2
	 */
	public final Vector2 divide(Vector2 vector) {
		this.x /= vector.x;
		this.y /= vector.y;
		return this;
	}

	/**
	 * Divides v1 by the v2 and stores the result in the output vector.
	 * 
	 * @param output Result.
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 */
	public static final void divide(Vector2 output, Vector2 v1, Vector2 v2) {
		output.x = v1.x / v2.x;
		output.y = v1.y / v2.y;
	}

	/**
	 * Performs the dot product of this vector and the specified vector and stores the result in this vector.
	 * 
	 * @param x X coordinate of the specified vector.
	 * @param y Y coordinate of the specified vector.
	 * @return dot product of v1 and v2
	 */
	public final float dotProduct(float x, float y) {
		return this.x * x + this.y * y;
	}

	/**
	 * Performs the dot product of this vector and the specified vector and stores the result in this vector.
	 * 
	 * @param vector Vector.
	 * @return dot product of v1 and v2
	 */
	public final float dotProduct(Vector2 vector) {
		return x * vector.x + y * vector.y;
	}

	/**
	 * Performs the dot product of v1 and v2.
	 * 
	 * @param v1 Vector 1.
	 * @param v2 Vector 2.
	 * @return dot product of v1 and v2
	 */
	public static final float dotProduct(Vector2 v1, Vector2 v2) {
		return v1.x * v2.x + v1.y * v2.y;
	}

	/**
	 * Calculates the length of this vector.
	 * 
	 * @return length of this vector
	 */
	public final float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	/**
	 * Calculates the length of the specified vector.
	 * 
	 * @param vector Vector.
	 * @return length of the specified vector
	 */
	public static final float length(Vector2 vector) {
		return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y);
	}

	/**
	 * Sets the length of this vector.<br>
	 * 
	 * @param length New length.
	 * @return this Vector2
	 */
	public final Vector2 setLength(float length) {
		if (length < 0) {
			throw new IllegalArgumentException("The specified length must be positive or zero.");
		}
		normalize();
		multiply(length);
		return this;
	}

	/**
	 * Normalizes this vector.
	 * 
	 * @return this Vector2
	 */
	public final Vector2 normalize() {
		float length = (float) Math.sqrt(x * x + y * y);
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
	public static final void normalize(Vector2 output, Vector2 vector) {
		float length = Vector2.length(vector);
		if (length == 0) {
			Vector2.copy(output, vector);
			return;
		}
		Vector2.copy(output, vector.divide(length));
	}

	/**
	 * Converts the coordinates of this vector to their absolute values.
	 * 
	 * @return this Vector2
	 */
	public final Vector2 abs() {
		this.x = Math.abs(this.x);
		this.y = Math.abs(this.y);
		return this;
	}

	/**
	 * Converts the coordinates of the specified vector to their absolute values and stores the result in the output
	 * vector.
	 * 
	 * @param output Result.
	 * @param vector Vector.
	 */
	public static final void abs(Vector2 output, Vector2 vector) {
		output.x = Math.abs(vector.x);
		output.y = Math.abs(vector.y);
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

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
