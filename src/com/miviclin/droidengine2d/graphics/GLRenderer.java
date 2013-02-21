package com.miviclin.droidengine2d.graphics;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.miviclin.droidengine2d.engine.EngineLock;

public class GLRenderer implements GLSurfaceView.Renderer {
	
	private long tInicio = 0;
	private long tTotal = 0;
	private int nFrames = 9;
	
	private DynamicSpriteBatch spriteBatch;
	private Sprite s1, s2;
	
	private Camera camera;
	
	private Context context;
	private EngineLock engineLock;
	private boolean justCreated;
	
	float p = 0.0f;
	
	public GLRenderer(Context context, EngineLock engineLock) {
		this.context = context;
		this.engineLock = engineLock;
		this.justCreated = true;
	}
	
	@Override
	public void onDrawFrame(GL10 glUnused) {
		if (justCreated) {
			justCreated = false;
			engineLock.notifyCanUpdate();
		}
		engineLock.waitUntilCanRender();
		// Contador de FPS improvisado, mejorar mas adelante
		if (nFrames++ == 9) {
			if (tInicio != 0) {
				tTotal = System.currentTimeMillis() - tInicio;
				tTotal = 1000 * nFrames / tTotal;
				Log.d("FPS", tTotal + "");
			}
			tInicio = System.currentTimeMillis();
			tTotal = 0;
			nFrames = 0;
		}
		
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		
		long time = System.currentTimeMillis() % 4000L;
		float angle = 0.090f * ((int) time);
		
		s1.setRotation(angle);
		s2.setRotation(-angle);
		
		spriteBatch.begin();
		s1.setPosition(0.0f, 0.0f);
		spriteBatch.draw(s1, camera);
		s1.setPosition(150.0f, 0.0f);
		spriteBatch.draw(s1, camera);
		s1.setPosition(150.0f, 500.0f);
		spriteBatch.draw(s1, camera);
		spriteBatch.draw(s2, camera);
		s1.setPosition(0.0f, 300.0f);
		spriteBatch.draw(s1, camera);
		spriteBatch.end();
		engineLock.notifyCanUpdate();
	}
	
	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) {
		camera.setViewportDimensions(width, height);
		camera.update();
	}
	
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		GLTexture glTexturebatch = new GLTexture(context, "img/star.png");
		GLTexture glTexturebatch2 = new GLTexture(context, "img/atlas_star2.png");
		s1 = new Sprite(0.0f, 0.0f, 128.0f, 128.0f, new TextureRegion(glTexturebatch, 0.0f, 0.0f, 128.0f, 128.0f));
		s2 = new Sprite(200.0f, 400.0f, 200.0f, 200.0f, new TextureRegion(glTexturebatch2, 0.0f, 0.0f, 128.0f, 128.0f));
		glTexturebatch.loadTexture(context);
		glTexturebatch2.loadTexture(context);
		DynamicSpriteBatchShaderProgram shaderProgram = new DynamicSpriteBatchShaderProgram();
		shaderProgram.link();
		spriteBatch = new DynamicSpriteBatch(context, shaderProgram);
		
		camera = new OrthographicCamera();
		
		GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		
		justCreated = true;
	}
	
}
