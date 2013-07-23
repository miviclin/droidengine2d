package com.miviclin.droidengine2d.graphics;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureColorSpriteBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureOpacitySpriteBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureSpriteBatch;
import com.miviclin.droidengine2d.graphics.shape.Sprite;
import com.miviclin.droidengine2d.graphics.texture.TextureRegion;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.math.Vector2;

public class Graphics {
	
	private Camera camera;
	private SpriteBatch currentSpriteBatch;
	private PositionTextureSpriteBatch positionTextureSB;
	private PositionTextureOpacitySpriteBatch positionTextureOpacitySB;
	private PositionTextureColorSpriteBatch positionTextureColorSB;
	private Sprite tempSprite;
	private boolean inBeginEndPair;
	
	public Graphics(Camera camera, Context context) {
		this.camera = camera;
		this.currentSpriteBatch = null;
		this.positionTextureSB = new PositionTextureSpriteBatch(context);
		this.positionTextureOpacitySB = new PositionTextureOpacitySpriteBatch(context);
		this.positionTextureColorSB = new PositionTextureColorSpriteBatch(context);
		this.tempSprite = null;
		this.inBeginEndPair = false;
	}
	
	public void initialize() {
		positionTextureSB.getShaderProgram().link();
		positionTextureSB.initialize();
		
		positionTextureOpacitySB.getShaderProgram().link();
		positionTextureOpacitySB.initialize();
		
		positionTextureColorSB.getShaderProgram().link();
		positionTextureColorSB.initialize();
	}
	
	public void drawSprite(TextureRegion textureRegion, Vector2 position, Dimensions2D dimensions) {
		drawSprite(textureRegion, position, dimensions, 0.0f, null, 0.0f);
	}
	
	public void drawSprite(TextureRegion textureRegion, Vector2 position, Dimensions2D dimensions, float rotation) {
		drawSprite(textureRegion, position, dimensions, rotation, null, 0.0f);
	}
	
	public void drawSprite(TextureRegion textureRegion, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		selectCurrentSpriteBatch(positionTextureSB);
		setupTempSprite(textureRegion, null, position, dimensions, rotation, externalCenter, externalRotation);
		positionTextureSB.draw(tempSprite, camera);
		flush();
	}
	
	public void drawSprite(TextureRegion textureRegion, float opacity, Vector2 position, Dimensions2D dimensions) {
		drawSprite(textureRegion, opacity, position, dimensions, 0.0f, null, 0.0f);
	}
	
	public void drawSprite(TextureRegion textureRegion, float opacity, Vector2 position, Dimensions2D dimensions, float rotation) {
		drawSprite(textureRegion, opacity, position, dimensions, rotation, null, 0.0f);
	}
	
	public void drawSprite(TextureRegion textureRegion, float opacity, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		selectCurrentSpriteBatch(positionTextureOpacitySB);
		setupTempSprite(textureRegion, null, position, dimensions, rotation, externalCenter, externalRotation);
		tempSprite.getColor().setA(opacity);
		positionTextureOpacitySB.draw(tempSprite, camera);
	}
	
	public void drawSprite(TextureRegion textureRegion, Color color, Vector2 position, Dimensions2D dimensions) {
		drawSprite(textureRegion, color, position, dimensions, 0.0f, null, 0.0f);
	}
	
	public void drawSprite(TextureRegion textureRegion, Color color, Vector2 position, Dimensions2D dimensions, float rotation) {
		drawSprite(textureRegion, color, position, dimensions, rotation, null, 0.0f);
	}
	
	public void drawSprite(TextureRegion textureRegion, Color color, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		selectCurrentSpriteBatch(positionTextureColorSB);
		setupTempSprite(textureRegion, color, position, dimensions, rotation, externalCenter, externalRotation);
		positionTextureColorSB.draw(tempSprite, camera);
	}
	
	public void flush() {
		if (inBeginEndPair && currentSpriteBatch != null) {
			currentSpriteBatch.end();
			inBeginEndPair = false;
		}
	}
	
	private void selectCurrentSpriteBatch(SpriteBatch spriteBatch) {
		if (!inBeginEndPair || currentSpriteBatch != spriteBatch) {
			if (inBeginEndPair) {
				currentSpriteBatch.end();
				inBeginEndPair = false;
			}
			currentSpriteBatch = spriteBatch;
			currentSpriteBatch.begin();
			inBeginEndPair = true;
		}
	}
	
	private void setupTempSprite(TextureRegion textureRegion, Color color, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		if (tempSprite == null) {
			tempSprite = new Sprite(position.getX(), position.getY(), dimensions.getWidth(), dimensions.getHeight(), textureRegion);
		} else {
			tempSprite.getPosition().set(position);
			tempSprite.getDimensions().set(dimensions);
		}
		tempSprite.setRotation(rotation);
		if (externalCenter == null) {
			tempSprite.setRotationAroundExternalPoint(0.0f, 0.0f, 0.0f);
		} else {
			tempSprite.setRotationAroundExternalPoint(externalRotation, externalCenter);
		}
		if (color != null) {
			tempSprite.getColor().setR(color.getR());
			tempSprite.getColor().setG(color.getG());
			tempSprite.getColor().setB(color.getB());
			tempSprite.getColor().setA(color.getA());
		}
	}
	
}
