package com.miviclin.droidengine2d.graphics;

/**
 * Color
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
	 * Crea un color con los valores RGB especificados (Valores entre 0.0f y 1.0f). La componente A tendra el valor 1.0f por defecto.
	 * 
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 */
	public Color(float r, float g, float b) {
		this(r, g, b, 1.0f);
	}
	
	/**
	 * Crea un color con los valores RGBA especificados (Valores entre 0.0f y 1.0f)
	 * 
	 * @param r Red
	 * @param g Green
	 * @param b Blue
	 * @param a Alpha
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
	 * Copia los valores del color especificado en este Color
	 * 
	 * @param color Color a copiar
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
	 * Asigna los valores RGB especificados al color
	 * 
	 * @param r Valor entre 0.0f y 1.0f
	 * @param g Valor entre 0.0f y 1.0f
	 * @param b Valor entre 0.0f y 1.0f
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
	 * Actualiza los valores HSV para que representen un color equivalente al que definen los valores RGB.
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
	 * Asigna los valores HSV especificados al color
	 * 
	 * @param h Valor entre 0.0f y 360.0f
	 * @param s Valor entre 0.0f y 1.0f
	 * @param v Valor entre 0.0f y 1.0f
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
	 * Actualiza los valores RGB para que representen un color equivalente al que definen los valores HSV.
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
	 * Devuelve la componente R del color
	 * 
	 * @return Valor entre 0.0f y 1.0f
	 */
	public float getR() {
		return r;
	}
	
	/**
	 * Devuelve la componente G del color
	 * 
	 * @return Valor entre 0.0f y 1.0f
	 */
	public float getG() {
		return g;
	}
	
	/**
	 * Devuelve la componente B del color
	 * 
	 * @return Valor entre 0.0f y 1.0f
	 */
	public float getB() {
		return b;
	}
	
	/**
	 * Devuelve la componente A del color
	 * 
	 * @return Valor entre 0.0f y 1.0f
	 */
	public float getA() {
		return a;
	}
	
	/**
	 * Asigna la componente A del color
	 * 
	 * @param a Valor entre 0.0f y 1.0f
	 */
	public void setA(float a) {
		if (a < 0 || a > 1) {
			throw new IllegalArgumentException("The alpha component must be a value between 0 and 1");
		}
		this.a = a;
	}
	
	/**
	 * Devuelve el tono del color (componente H en el modelo HSV)
	 * 
	 * @return Valor entre 0.0f y 360.0f
	 */
	public float getH() {
		return h;
	}
	
	/**
	 * Devuelve la saturacion del color (componente S en el modelo HSV)
	 * 
	 * @return Valor entre 0.0f y 1.0f
	 */
	public float getS() {
		return s;
	}
	
	/**
	 * Devuelve el brillo del color (componente V en el modelo HSV)
	 * 
	 * @return Valor entre 0.0f y 1.0f
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (Float.floatToIntBits(a) != Float.floatToIntBits(other.a))
			return false;
		if (Float.floatToIntBits(b) != Float.floatToIntBits(other.b))
			return false;
		if (Float.floatToIntBits(g) != Float.floatToIntBits(other.g))
			return false;
		if (Float.floatToIntBits(r) != Float.floatToIntBits(other.r))
			return false;
		return true;
	}
	
}
