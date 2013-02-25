package com.miviclin.droidengine2d.graphics;

public interface SpriteBatch {
	
	public void begin();
	
	public void draw(Sprite sprite, Camera camera);
	
	public void end();
	
}
