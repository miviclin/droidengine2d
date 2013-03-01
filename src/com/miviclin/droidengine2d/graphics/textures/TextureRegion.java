package com.miviclin.droidengine2d.graphics.textures;

/**
 * TextureRegion representa una region dentro de una textura.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureRegion { // TODO: revisar el calculo de U y V (restar medio texel?)

	private final GLTexture texture;
	private float u1;
	private float v1;
	private float u2;
	private float v2;
	private float x;
	private float y;
	private float width;
	private float height;
	
	/**
	 * Crea un nuevo TextureRegion
	 * 
	 * @param texture Textura a la que pertenece la region
	 * @param x Posicion de la region en el eje X, relativo a la esquina superior izquierda de la textura (en pixeles)
	 * @param y Posicion de la region en el eje Y, relativo a la esquina superior izquierda de la textura (en pixeles)
	 * @param width Ancho de la region
	 * @param height Alto de la region
	 */
	public TextureRegion(GLTexture texture, float x, float y, float width, float height) {
		if (texture == null) {
			throw new IllegalArgumentException("texture can not be null");
		}
		this.texture = texture;
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);
	}
	
	/**
	 * Invierte la coordenada U de la region
	 * 
	 * @return Devuelve este TextureRegion para poder encadenar llamadas a metodos
	 */
	public TextureRegion flipX() {
		float u = u1;
		u1 = u2;
		u2 = u;
		return this;
	}
	
	/**
	 * Invierte la coordenada V de la region
	 * 
	 * @return Devuelve este TextureRegion para poder encadenar llamadas a metodos
	 */
	public TextureRegion flipY() {
		float v = v1;
		v1 = v2;
		v2 = v;
		return this;
	}
	
	/**
	 * Devuelve la textura a la que pertenece esta region
	 * 
	 * @return GLTexture
	 */
	public final GLTexture getTexture() {
		return texture;
	}
	
	/**
	 * Devuelve la coordenada U1 de la region. [0..1]
	 * 
	 * @return U1
	 */
	public float getU1() {
		return u1;
	}
	
	/**
	 * Devuelve la coordenada V1 de la region. [0..1]
	 * 
	 * @return V1
	 */
	public float getV1() {
		return v1;
	}
	
	/**
	 * Devuelve la coordenada U2 de la region. [0..1]
	 * 
	 * @return U2
	 */
	public float getU2() {
		return u2;
	}
	
	/**
	 * Devuelve la coordenada V2 de la region. [0..1]
	 * 
	 * @return V2
	 */
	public float getV2() {
		return v2;
	}
	
	/**
	 * Devuelve la posicion en el eje X de la region con respecto a la esquina superior izquierda de la textura, en pixeles
	 * 
	 * @return posicion en el eje X
	 */
	public final float getX() {
		return x;
	}
	
	/**
	 * Asigna la posicion en el eje X de la region con respecto a la esquina superior izquierda de la textura, en pixeles
	 * 
	 * @param x Nueva posicion en el eje X
	 */
	public final void setX(float x) {
		if (x < 0) {
			throw new IllegalArgumentException("offsetX must be equal or greater than 0");
		}
		if ((x + width) > texture.getWidth()) {
			throw new IllegalArgumentException("The TextureRegion must be fully contained inside the texture");
		}
		this.u1 = x / texture.getWidth();
		this.u2 = (x + width) / texture.getWidth();
		this.x = x;
	}
	
	/**
	 * Devuelve la posicion en el eje Y de la region con respecto a la esquina superior izquierda de la textura, en pixeles
	 * 
	 * @return posicion en el eje Y
	 */
	public final float getY() {
		return y;
	}
	
	/**
	 * Asigna la posicion en el eje Y de la region con respecto a la esquina superior izquierda de la textura, en pixeles
	 * 
	 * @param y Nueva posicion en el eje Y
	 */
	public final void setY(float y) {
		if (y < 0) {
			throw new IllegalArgumentException("offsetY must be equal or greater than 0");
		}
		if ((y + height) > texture.getHeight()) {
			throw new IllegalArgumentException("The TextureRegion must be fully contained inside the texture");
		}
		this.v1 = y / texture.getHeight();
		this.v2 = (y + height) / texture.getHeight();
		this.y = y;
	}
	
	/**
	 * Devuelve el ancho de la region, en pixeles
	 * 
	 * @return ancho de la region
	 */
	public final float getWidth() {
		return width;
	}
	
	/**
	 * Asigna el ancho de la region, en pixeles
	 * 
	 * @param width Nuevo ancho
	 */
	public final void setWidth(float width) {
		if (width <= 0) {
			throw new IllegalArgumentException("width must be greater than 0");
		}
		this.width = width;
	}
	
	/**
	 * Devuelve el alto de la region, en pixeles
	 * 
	 * @return alto de la region
	 */
	public final float getHeight() {
		return height;
	}
	
	/**
	 * Asigna el alto de la region, en pixeles
	 * 
	 * @param height Nuevo alto
	 */
	public final void setHeight(float height) {
		if (height <= 0) {
			throw new IllegalArgumentException("height must be greater than 0");
		}
		this.height = height;
	}
	
}
