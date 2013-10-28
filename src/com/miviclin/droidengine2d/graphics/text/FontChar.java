package com.miviclin.droidengine2d.graphics.text;

import android.util.SparseIntArray;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * Representa un caracter de una fuente
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class FontChar {

	public static final int CHANNEL_B = 1;
	public static final int CHANNEL_G = 2;
	public static final int CHANNEL_R = 4;
	public static final int CHANNEL_A = 8;
	public static final int CHANNEL_RGBA = 15;

	private int id;
	private TextureRegion textureRegion;
	private int xOffset;
	private int yOffset;
	private int xAdvance;
	private int channel;
	private SparseIntArray kernings;

	/**
	 * Constructor
	 * 
	 * @param id ID del caracter
	 * @param textureRegion TextureRegion del caracter en el atlas de la fuente
	 * @param xOffset Offset en el eje X que deberia aplicarse al renderizar el caracter
	 * @param yOffset Offset en el eje Y que deberia aplicarse al renderizar el caracter
	 * @param xAdvance Offset en el eje X que deberia aplicarse para renderizar el siguiente caracter
	 * @param channel Canal de color de la textura en el que se encuentra la letra. Utilizar {@link FontChar#CHANNEL_R},
	 *            {@link FontChar#CHANNEL_G}, {@link FontChar#CHANNEL_B}, {@link FontChar#CHANNEL_A},
	 *            {@link FontChar#CHANNEL_RGBA}
	 */
	public FontChar(int id, TextureRegion textureRegion, int xOffset, int yOffset, int xAdvance, int channel) {
		super();
		this.id = id;
		this.textureRegion = textureRegion;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
		this.channel = channel;
		this.kernings = new SparseIntArray();
	}

	/**
	 * Devuelve el ID del caracter
	 * 
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Devuelve el TextureRegion del caracter
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	/**
	 * Devuelve el offset en el eje X que deberia aplicarse al renderizar el caracter, en pixels
	 * 
	 * @return Offset en el eje X
	 */
	public int getxOffset() {
		return xOffset;
	}

	/**
	 * Devuelve el offset en el eje Y que deberia aplicarse al renderizar el caracter, en pixels
	 * 
	 * @return Offset en el eje Y
	 */
	public int getyOffset() {
		return yOffset;
	}

	/**
	 * Devuelve el offset en el eje X que deberia aplicarse para renderizar el siguiente caracter, en pixels
	 * 
	 * @return Offset hasta el siguiente caracter
	 */
	public int getxAdvance() {
		return xAdvance;
	}

	/**
	 * Devuelve el canal de color de la textura en el que se encuentra el caracter: {@link FontChar#CHANNEL_R},
	 * {@link FontChar#CHANNEL_G}, {@link FontChar#CHANNEL_B}, {@link FontChar#CHANNEL_A}, {@link FontChar#CHANNEL_RGBA}
	 * 
	 * @return Canal de color de la textura en el que se encuentra representado el caracter
	 */
	public int getChannel() {
		return channel;
	}

	/**
	 * Devuelve el mapa cuya clave es el ID de otro caracter y el valor mapeado es el offset que debe haber desde dicho
	 * caracter a este
	 * 
	 * @return Mapa de kernings
	 */
	public SparseIntArray getKernings() {
		return kernings;
	}

}
