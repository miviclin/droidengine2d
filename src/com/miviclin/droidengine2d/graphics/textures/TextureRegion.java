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
	private float offsetX;
	private float offsetY;
	private float width;
	private float height;
	
	/**
	 * Crea un nuevo TextureRegion
	 * 
	 * @param texture Textura a la que pertenece la region
	 * @param offsetX Offset de la region en el eje X, relativo a la esquina superior izquierda de la textura
	 * @param offsetY Offset de la region en el eje Y, relativo a la esquina superior izquierda de la textura
	 * @param width Ancho de la region
	 * @param height Alto de la region
	 */
	public TextureRegion(GLTexture texture, float offsetX, float offsetY, float width, float height) {
		if (texture == null) {
			throw new IllegalArgumentException("texture can not be null");
		}
		this.texture = texture;
		setWidth(width);
		setHeight(height);
		setOffsetX(offsetX);
		setOffsetY(offsetY);
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
	 * Devuelve el offset en el eje X de la region con respecto a la esquina superior izquierda de la textura, en pixeles
	 * 
	 * @return offset en el eje X
	 */
	public final float getOffsetX() {
		return offsetX;
	}
	
	/**
	 * Asigna el offset en el eje X de la region con respecto a la esquina superior izquierda de la textura, en pixeles
	 * 
	 * @param offsetX Nuevo offset en el eje X
	 */
	public final void setOffsetX(float offsetX) {
		if (offsetX < 0) {
			throw new IllegalArgumentException("offsetX must be equal or greater than 0");
		}
		if ((offsetX + width) > texture.getWidth()) {
			throw new IllegalArgumentException("The TextureRegion must be fully contained inside the texture");
		}
		this.u1 = offsetX / texture.getWidth();
		this.u2 = (offsetX + width) / texture.getWidth();
		this.offsetX = offsetX;
	}
	
	/**
	 * Devuelve el offset en el eje Y de la region con respecto a la esquina superior izquierda de la textura, en pixeles
	 * 
	 * @return offset en el eje Y
	 */
	public final float getOffsetY() {
		return offsetY;
	}
	
	/**
	 * Asigna el offset en el eje Y de la region con respecto a la esquina superior izquierda de la textura, en pixeles
	 * 
	 * @param offsetY Nuevo offset en el eje Y
	 */
	public final void setOffsetY(float offsetY) {
		if (offsetY < 0) {
			throw new IllegalArgumentException("offsetY must be equal or greater than 0");
		}
		if ((offsetY + height) > texture.getHeight()) {
			throw new IllegalArgumentException("The TextureRegion must be fully contained inside the texture");
		}
		this.v1 = offsetY / texture.getHeight();
		this.v2 = (offsetY + height) / texture.getHeight();
		this.offsetY = offsetY;
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
