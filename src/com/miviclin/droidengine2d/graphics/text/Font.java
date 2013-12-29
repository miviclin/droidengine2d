package com.miviclin.droidengine2d.graphics.text;

import android.content.Context;
import android.util.SparseArray;

import com.miviclin.droidengine2d.graphics.texture.Texture;

/**
 * Font interface.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface Font {

	/**
	 * Loads this font's data from the specified file.
	 * 
	 * @param path File path.
	 * @param context Context.
	 */
	public void loadFromFile(String path, Context context);

	/**
	 * Returns the specified character of this font.
	 * 
	 * @param id ID of the character.
	 * @return FontChar
	 * @throws UndefinedCharacterException If the specified character is not defined.
	 */
	public FontChar getCharacter(int id);

	/**
	 * Returns the textures that contain the characters of this font, indexed by page ID.
	 * 
	 * @return Texturas
	 */
	public SparseArray<Texture> getTexturePages();

	/**
	 * Measures the width in pixels of the specified line of text if it was rendered at the specified size in pixels
	 * using this font.
	 * 
	 * @param line Line of text.
	 * @param fontSizePx Font size in pixels.
	 * @return measured width
	 */
	public float measureLineWidth(String line, float fontSizePx);

	/**
	 * Measures the height of a line of text if it was rendered at the specified size in pixels using this font.
	 * 
	 * @param fontSizePx Font size in pixels.
	 * @return measured height
	 */
	public float measureLineHeight(float fontSizePx);
}
