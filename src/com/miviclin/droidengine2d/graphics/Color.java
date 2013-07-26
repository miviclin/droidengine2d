package com.miviclin.droidengine2d.graphics;

/**
 * Color
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Color {
	
	public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
	public static final Color RED = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f, 1.0f);
	public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f, 1.0f);
	public static final Color YELLOW = new Color(1.0f, 1.0f, 0.0f, 1.0f);
	public static final Color CYAN = new Color(0.0f, 1.0f, 0.0f, 1.0f);
	public static final Color MAGENTA = new Color(1.0f, 0.0f, 0.0f, 1.0f);
	public static final Color GRAY = new Color(0.5f, 0.5f, 0.5f, 1.0f);
	public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	
	private float r;
	private float g;
	private float b;
	private float a;
	
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
			throw new IllegalArgumentException("All color components must be values between 0 and 1");
		}
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	/**
	 * Copia los valores RGBA del color especificado en este Color
	 * 
	 * @param color Color a copiar
	 */
	public void set(Color color) {
		this.r = color.r;
		this.g = color.g;
		this.b = color.b;
		this.a = color.a;
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
	 * Asigna la componente R del color
	 * 
	 * @param r Valor entre 0.0f y 1.0f
	 */
	public void setR(float r) {
		if (r < 0 || r > 1) {
			throw new IllegalArgumentException("The red component must be a value between 0 and 1");
		}
		this.r = r;
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
	 * Asigna la componente G del color
	 * 
	 * @param g Valor entre 0.0f y 1.0f
	 */
	public void setG(float g) {
		if (g < 0 || g > 1) {
			throw new IllegalArgumentException("The green component must be a value between 0 and 1");
		}
		this.g = g;
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
	 * Asigna la componente B del color
	 * 
	 * @param b Valor entre 0.0f y 1.0f
	 */
	public void setB(float b) {
		if (b < 0 || b > 1) {
			throw new IllegalArgumentException("The blue component must be a value between 0 and 1");
		}
		this.b = b;
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
