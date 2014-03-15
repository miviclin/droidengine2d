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
package com.miviclin.droidengine2d.graphics;

/**
 * Color.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Color {

	private float r;
	private float g;
	private float b;
	private float a;

	private float h;
	private float s;
	private float v;

	/**
	 * Creates a new Color with the specified RGB values (values between 0.0f and 1.0f). The A (alpha) component will
	 * have tha value 1.0f by default.
	 * 
	 * @param r Red.
	 * @param g Green.
	 * @param b Blue.
	 */
	public Color(float r, float g, float b) {
		this(r, g, b, 1.0f);
	}

	/**
	 * Creates a new Color with the specified RGBA values (values between 0.0f and 1.0f).
	 * 
	 * @param r Red.
	 * @param g Green.
	 * @param b Blue.
	 * @param a Alpha.
	 */
	public Color(float r, float g, float b, float a) {
		if (r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1 || a < 0 || a > 1) {
			throw new IllegalArgumentException("The RGB components must be values between 0.0 and 1.0");
		}
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		convertRGBtoHSV();
	}

	/**
	 * Creates a new Color, copying from the specified color.
	 * 
	 * @param color Color.
	 */
	public Color(Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
		this.h = color.h;
		this.s = color.s;
		this.v = color.v;
	}

	/**
	 * Overwrites this Color with the specified one.<br>
	 * This Color will be a copy of the specified Color.
	 * 
	 * @param color Color to copy.
	 */
	public void set(Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
		this.h = color.h;
		this.s = color.s;
		this.v = color.v;
	}

	/**
	 * Sets the specified RGB values of this Color.
	 * 
	 * @param r Value between 0.0f and 1.0f.
	 * @param g Value between 0.0f and 1.0f.
	 * @param b Value between 0.0f and 1.0f.
	 */
	public void setRGB(float r, float g, float b) {
		if (r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1) {
			throw new IllegalArgumentException("The RGB components must be values between 0.0 and 1.0");
		}
		this.r = r;
		this.g = g;
		this.b = b;
		convertRGBtoHSV();
	}

	/**
	 * Sets the specified RGBA values of this Color.
	 * 
	 * @param r Value between 0.0f and 1.0f.
	 * @param g Value between 0.0f and 1.0f.
	 * @param b Value between 0.0f and 1.0f.
	 * @param a Value between 0.0f and 1.0f.
	 */
	public void setRGBA(float r, float g, float b, float a) {
		setRGB(r, g, b);
		setA(a);
	}

	/**
	 * Updates the HSV values of this Color so they represent the same color that the RGB values represent.
	 */
	private void convertRGBtoHSV() {
		float max = Math.max(Math.max(r, g), b);
		float min = Math.min(Math.min(r, g), b);
		float delta = max - min;
		float h = 0;
		float s = max == 0 ? 0 : (max - min) / max;
		float v = max;
		if (delta != 0) {
			if (r == max) {
				h = (g - b) / delta;
			} else if (g == max) {
				h = 2 + (b - r) / delta;
			} else {
				h = 4 + (r - g) / delta;
			}
			h *= 60;
			if (h < 0) {
				h += 360;
			}
		}
		this.h = h;
		this.s = s;
		this.v = v;
	}

	/**
	 * Sets the specified HSV values of this Color.
	 * 
	 * @param h Value between 0.0f and 360.0f.
	 * @param s Value between 0.0f and 1.0f.
	 * @param v Value between 0.0f and 1.0f.
	 */
	public void setHSV(float h, float s, float v) {
		if (h < 0 || h > 360) {
			throw new IllegalArgumentException("The H component must be a value between 0.0 and 360.0");
		}
		if (s < 0 || s > 1 || v < 0 || v > 1) {
			throw new IllegalArgumentException("The S and V components must be values between 0.0 and 1.0");
		}
		this.h = h;
		this.s = s;
		this.v = v;
		convertHSVtoRGB();
	}

	/**
	 * Updates the RGB values of this Color so they represent the same color that the HSV values represent.
	 */
	private void convertHSVtoRGB() {
		if (s == 0.0) {
			this.r = v;
			this.g = v;
			this.g = v;
			return;
		}
		float h = this.h;
		if (h == 360.0f) {
			h = 0.0f;
		}
		int hi = (int) (Math.floor(h / 60.0));
		float f = (h / 60.0f) - hi;
		float p = v * (1.0f - s);
		float q = v * (1.0f - (s * f));
		float t = v * (1.0f - (s * (1.0f - f)));
		switch (hi) {
		case 0:
			r = v;
			g = t;
			b = p;
			break;
		case 1:
			r = q;
			g = v;
			b = p;
			break;
		case 2:
			r = p;
			g = v;
			b = t;
			break;
		case 3:
			r = p;
			g = q;
			b = v;
			break;
		case 4:
			r = t;
			g = p;
			b = v;
			break;
		case 5:
			r = v;
			g = p;
			b = q;
			break;
		default:
			break;
		}

	}

	/**
	 * Returns the R component (red) of this Color.
	 * 
	 * @return Value between 0.0f and 1.0f
	 */
	public float getR() {
		return r;
	}

	/**
	 * Returns the G component (green) of this Color.
	 * 
	 * @return Value between 0.0f and 1.0f
	 */
	public float getG() {
		return g;
	}

	/**
	 * Returns the B component (blue) of this Color.
	 * 
	 * @return Value between 0.0f and 1.0f
	 */
	public float getB() {
		return b;
	}

	/**
	 * Returns the A component (alpha) of this Color.
	 * 
	 * @return Value between 0.0f and 1.0f
	 */
	public float getA() {
		return a;
	}

	/**
	 * Sets the A component (alpha) of this Color to the specified value.
	 * 
	 * @param a Value between 0.0f and 1.0f
	 */
	public void setA(float a) {
		if (a < 0 || a > 1) {
			throw new IllegalArgumentException("The alpha component must be a value between 0 and 1");
		}
		this.a = a;
	}

	/**
	 * Returns the H component (hue) of this Color.
	 * 
	 * @return Value between 0.0f and 360.0f
	 */
	public float getH() {
		return h;
	}

	/**
	 * Returns the S component (saturation) of this Color.
	 * 
	 * @return Value between 0.0f and 1.0f
	 */
	public float getS() {
		return s;
	}

	/**
	 * Returns the V component (brightness) of this Color.
	 * 
	 * @return Value between 0.0f and 1.0f
	 */
	public float getV() {
		return v;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(a);
		result = prime * result + Float.floatToIntBits(b);
		result = prime * result + Float.floatToIntBits(g);
		result = prime * result + Float.floatToIntBits(r);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Color other = (Color) obj;
		if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a)) {
			return false;
		}
		if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b)) {
			return false;
		}
		if (Float.floatToIntBits(g) != Float.floatToIntBits(other.g)) {
			return false;
		}
		if (Float.floatToIntBits(r) != Float.floatToIntBits(other.r)) {
			return false;
		}
		return true;
	}

}
