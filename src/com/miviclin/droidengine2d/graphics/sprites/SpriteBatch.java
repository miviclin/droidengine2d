package com.miviclin.droidengine2d.graphics.sprites;

import com.miviclin.droidengine2d.graphics.camera.Camera;

public interface SpriteBatch {
	
	public void begin();
	
	public void draw(Sprite sprite, Camera camera);
	
	public void end();
	
}
