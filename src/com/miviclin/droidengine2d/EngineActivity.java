package com.miviclin.droidengine2d;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.miviclin.droidengine2d.graphics.GLView;

/**
 * Activity que gestiona el engine.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public abstract class EngineActivity extends FragmentActivity {
	
	private Engine engine;
	private Game game;
	private boolean prepared;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setWindowFlags();
		
		setContentView(getContentViewID());
		final GLView glView = (GLView) findViewById(getGLViewID());
		
		// Creamos el juego y lo lanzamos
		engine = createEngine(glView);
		game = engine.getGame();
		engine.startGame();
		
		// GLView.getWidth() y GLView.getHeight() devuelven 0 hasta que la View no se muestra en pantalla, por lo que tenemos que esperar a
		// que la View se renderice para poder inicializar el juego y acceder al ancho y alto de la pantalla
		glView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			
			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				if (getResources().getConfiguration().orientation != getOrientation()) {
					return;
				}
				// Eliminamos este listener para asegurar que solo se ejecuta 1 vez
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					glView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				} else {
					glView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				}
				if (!prepared) {
					prepared = true;
					game.prepare();
				}
			}
		});
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (engine != null) {
			engine.onPause();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (engine != null) {
			engine.onResume();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (engine != null) {
			engine.onDestroy();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (engine != null) {
			engine.onBackPressed();
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (engine != null) {
			onConfigurationChanged();
		}
	}
	
	/**
	 * Se llama desde onConfigurationChanged(Configuration) si el engine ya ha sido inicializado.
	 */
	public void onConfigurationChanged() {
		setContentView(getContentViewID());
		engine.setGLView((GLView) findViewById(getGLViewID()));
	}
	
	/**
	 * Asigna los flags al objeto Window.<br>
	 * Este metodo se llama en {@link EngineActivity#onCreate(Bundle)}
	 */
	public void setWindowFlags() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
	
	/**
	 * Devuelve el Engine
	 * 
	 * @return Engine
	 */
	protected Engine getEngine() {
		return engine;
	}
	
	/**
	 * Devuelve el juego
	 * 
	 * @return Game
	 */
	protected Game getGame() {
		return game;
	}
	
	/**
	 * Crea un Engine con el Game que se vaya a representar en esta Activity y lo devuelve.<br>
	 * Este metodo se llama en {@link EngineActivity#onCreate(Bundle)}
	 * 
	 * @param glView GLView en la que se renderiza el juego
	 * @return Engine que se va a utilizar en esta Activity
	 */
	public abstract Engine createEngine(GLView glView);
	
	/**
	 * Devuelve el ID del layout que se utiliza en setContentView(int)
	 * 
	 * @return ID del layout
	 */
	public abstract int getContentViewID();
	
	/**
	 * Devuelve el ID del GLView que se utiliza para representar el juego
	 * 
	 * @return ID del GLView
	 */
	public abstract int getGLViewID();
	
	/**
	 * Devuelve la orientacion que debe tener la activity. Esta orientacion debe corresponderse con la orientacion definida en
	 * AndroidManifest.xml
	 * 
	 * @return {@link Configuration#ORIENTATION_LANDSCAPE} o {@link Configuration#ORIENTATION_PORTRAIT}
	 */
	public abstract int getOrientation();
}