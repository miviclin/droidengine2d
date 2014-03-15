/*   Copyright 2013-2014 Miguel Vicente Linares
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.miviclin.droidengine2d.graphics.text;

import android.util.SparseIntArray;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * Character of a {@link Font}.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class FontChar {

	public static final int CHANNEL_B = 1;
	public static final int CHANNEL_G = 2;
	public static final int CHANNEL_R = 4;
	public static final int CHANNEL_A = 8;
	public static final int CHANNEL_RGBA = 15;

	private int id;
	private TextureRegion textureRegion;
	private int xOffset;
	private int yOffset;
	private int xAdvance;
	private int channel;
	private SparseIntArray kernings;

	/**
	 * Constructor.
	 * 
	 * @param id ID.
	 * @param textureRegion TextureRegion.
	 * @param xOffset Offset in the X axis in pixels that should be applied when rendering this character.
	 * @param yOffset Offset in the Y axis in pixels that should be applied when rendering this character.
	 * @param xAdvance Offset in the X axis in pixels that should be applied when rendering the next character after
	 *            this one.
	 * @param channel Color channel of the letter in the texture. Use {@link FontChar#CHANNEL_R},
	 *            {@link FontChar#CHANNEL_G}, {@link FontChar#CHANNEL_B}, {@link FontChar#CHANNEL_A}, or
	 *            {@link FontChar#CHANNEL_RGBA}.
	 */
	public FontChar(int id, TextureRegion textureRegion, int xOffset, int yOffset, int xAdvance, int channel) {
		super();
		this.id = id;
		this.textureRegion = textureRegion;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.xAdvance = xAdvance;
		this.channel = channel;
		this.kernings = new SparseIntArray();
	}

	/**
	 * Returns the ID of this character.
	 * 
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the TextureRegion of this character.
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	/**
	 * Returns the offset in the X axis in pixels that should be applied when rendering this character.
	 * 
	 * @return offset
	 */
	public int getxOffset() {
		return xOffset;
	}

	/**
	 * Returns the offset in the Y axis in pixels that should be applied when rendering this character.
	 * 
	 * @return offset
	 */
	public int getyOffset() {
		return yOffset;
	}

	/**
	 * Returns the offset in the X axis in pixels that should be applied when rendering the next character after this
	 * one.
	 * 
	 * @return offset
	 */
	public int getxAdvance() {
		return xAdvance;
	}

	/**
	 * Returns the color channel of the letter in the texture: {@link FontChar#CHANNEL_R}, {@link FontChar#CHANNEL_G},
	 * {@link FontChar#CHANNEL_B}, {@link FontChar#CHANNEL_A}, or {@link FontChar#CHANNEL_RGBA}.
	 * 
	 * @return color channel of the letter
	 */
	public int getChannel() {
		return channel;
	}

	/**
	 * Returns the map of kernings of this character.<br>
	 * The key of this map is the ID of other character, and the mapped value is the offset that should be applied
	 * between that character and this one.
	 * 
	 * @return map of kernings of this character
	 */
	public SparseIntArray getKernings() {
		return kernings;
	}

}
