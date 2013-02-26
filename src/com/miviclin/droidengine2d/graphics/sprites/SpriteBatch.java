package com.miviclin.droidengine2d.graphics.sprites;

import com.miviclin.droidengine2d.graphics.camera.Camera;

/**
 * SpriteBatch permite pintar varios sprites en una sola llamada.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface SpriteBatch {
	
	/**
	 * Prepara el SpriteBatch para pintar.<br>
	 * Este metodo debe llamarse antes de {@link #draw(Sprite, Camera)}
	 */
	public void begin();
	
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
	
	/**
	 * Renderiza todos los elementos que contenga el batch.<br>
	 * Este metodo debe llamarse siempre al terminar de agregar todos los elementos al batch. Es necesario llamar antes a {@link #begin()}
	 */
	public void end();
	
}
