package com.miviclin.droidengine2d.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.miviclin.droidengine2d.engine.EngineLock;
import com.miviclin.droidengine2d.engine.Game;
import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shaders.DynamicSpriteBatchShaderProgram;
import com.miviclin.droidengine2d.graphics.sprites.DynamicSpriteBatch;
import com.miviclin.droidengine2d.graphics.sprites.SpriteBatch;

/**
 * Responsable de relizar las llamadas a OpenGL para renderizar los elementos del juego.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GLRenderer implements GLSurfaceView.Renderer {
	
	private Game game;
	private Camera camera;
	private Context context;
	private EngineLock engineLock;
	
	private SpriteBatch spriteBatch;
	
	/**
	 * Crea un GLRenderer
	 * 
	 * @param game Juego
	 * @param engineLock Utilizado para sincronizar correctamente los hilos
	 */
	public GLRenderer(Game game, EngineLock engineLock) {
		this.game = game;
		this.camera = game.getCamera();
		this.context = game.getActivity();
		this.engineLock = engineLock;
	}
	
	@Override
	public final void onDrawFrame(GL10 glUnused) {
		if (!engineLock.allowUpdate.get()) {
			synchronized (engineLock.lock) {
				game.draw(spriteBatch);
				engineLock.allowUpdate.set(true);
			}
		}
	}
	
	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) {
		camera.setViewportDimensions(width, height);
		camera.update();
	}
	
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		DynamicSpriteBatchShaderProgram shaderProgram = new DynamicSpriteBatchShaderProgram();
		shaderProgram.link();
		spriteBatch = new DynamicSpriteBatch(context, shaderProgram);
		
		game.getTextureManager().loadAllTextures();
		
		GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	}
	
}
