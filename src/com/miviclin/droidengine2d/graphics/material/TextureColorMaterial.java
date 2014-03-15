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
package com.miviclin.droidengine2d.graphics.material;

import com.miviclin.droidengine2d.graphics.Color;
import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * This Material has texture and color.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureColorMaterial extends TextureMaterial {

	private Color color;

	/**
	 * Creates a TextureColorMaterial.
	 * 
	 * @param color Color (can not be null).
	 */
	public TextureColorMaterial(TextureRegion textureRegion, Color color) {
		super(textureRegion);
		if (color == null) {
			throw new IllegalArgumentException("The Color can not be null");
		}
		this.color = color;
	}

	/**
	 * Returns the Color of this material.
	 * 
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}
}
