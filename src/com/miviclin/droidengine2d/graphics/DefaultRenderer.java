package com.miviclin.droidengine2d.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;

import com.miviclin.droidengine2d.Game;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shaders.DynamicSpriteBatchShaderProgram;
import com.miviclin.droidengine2d.graphics.sprites.DynamicSpriteBatch;
import com.miviclin.droidengine2d.graphics.sprites.SpriteBatch;

/**
 * DefaultRenderer
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class DefaultRenderer implements EngineRenderer {
	
	private Game game;
	private Camera camera;
	private Context context;
	
	private SpriteBatch spriteBatch;
	
	/**
	 * Crea un DefaultRenderer
	 * 
	 * @param game Juego
	 */
	public DefaultRenderer(Game game) {
		this.game = game;
		this.camera = game.getCamera();
		this.context = game.getActivity();
	}
	
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		DynamicSpriteBatchShaderProgram shaderProgram = new DynamicSpriteBatchShaderProgram();
		shaderProgram.link();
		spriteBatch = new DynamicSpriteBatch(context, shaderProgram);
		
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		
		game.getTextureManager().loadAllTextures();
	}
	
	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) {
		camera.setViewportDimensions(width, height);
		camera.update();
	}
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		game.draw(spriteBatch);
	}
	
}
