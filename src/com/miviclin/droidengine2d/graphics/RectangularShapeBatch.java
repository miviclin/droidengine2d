package com.miviclin.droidengine2d.graphics;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.shape.RectangularShape;

/**
 * SpriteBatch permite pintar varios sprites en una sola llamada.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface RectangularShapeBatch extends ShapeBatch {
	
	/**
	 * Agrega un RectangularShape al batch.<br>
	 * Si el batch esta lleno, se renderiza el batch y se vacia, posteriormente se agrega el elemento.<br>
	 * Este metodo se puede llamar varias veces seguidas, entre una llamada a {@link #begin()} y una llamada a {@link #end()} con distintos
	 * RectangularShape.
	 * 
	 * @param shape RectangularShape a agregar
	 * @param camera Camara
	 */
	public void draw(RectangularShape shape, Camera camera);
	
}