package com.miviclin.droidengine2d.graphics.text;

import android.util.SparseIntArray;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

public class Letter {
	
	private int id;
	private TextureRegion textureRegion;
	private int xOffset; // Offset en el eje X que deberia aplicarse al renderizar la letra
	private int yOffset; // Offset en el eje Y que deberia aplicarse al renderizar la letra
	private int xAdvance; // Offset en el eje X que deberia aplicarse para renderizar la siguiente letra
	private int channel; // El canal de color de la textura en el que se encuentra la letra (1=B, 2=G, 4=R, 8=A, 15=RGBA)
	
	// Mapa cuya clave es el ID de otra letra y el valor mapeado es el offset que debe haber desde esa letra a esta
	private SparseIntArray kernings;
	
	public Letter(int id, TextureRegion textureRegion, int xOffset, int yOffset, int xAdvance, int channel) {
		super();
		this.id = id;
		this.textureRegion = textureRegion;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
		this.channel = channel;
		this.kernings = new SparseIntArray();
	}
	
	public int getId() {
		return id;
	}
	
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	public int getxOffset() {
		return xOffset;
	}
	
	public int getyOffset() {
		return yOffset;
	}
	
	public int getxAdvance() {
		return xAdvance;
	}
	
	public int getChannel() {
		return channel;
	}
	
	public SparseIntArray getKernings() {
		return kernings;
	}
	
}
