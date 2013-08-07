package com.miviclin.droidengine2d.graphics;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shape.Sprite;

/**
 * SpriteBatch permite pintar varios sprites en una sola llamada.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface SpriteBatch extends ShapeBatch {
	
	/**
	 * Agrega un sprite al batch.<br>
	 * Si el batch esta lleno, se renderiza el batch y se vacia, posteriormente se agrega el elemento.<br>
	 * Este metodo se puede llamar varias veces seguidas, entre una llamada a {@link #begin()} y una llamada a {@link #end()} con distintos
	 * sprites.
	 * 
	 * @param sprite Sprite a agregar
	 * @param camera Camara
	 */
	public void draw(Sprite sprite, Camera camera);
	
}
