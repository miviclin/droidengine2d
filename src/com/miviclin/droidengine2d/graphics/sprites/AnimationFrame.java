package com.miviclin.droidengine2d.graphics.sprites;

import com.miviclin.droidengine2d.graphics.textures.TextureRegion;

public class AnimationFrame {
	
	private int time;
	private TextureRegion textureRegion;
	
	public AnimationFrame(int time, TextureRegion textureRegion) {
		super();
		this.time = time;
		this.textureRegion = textureRegion;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}
	
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}
	
}
