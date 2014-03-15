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

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * This Material has texture.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class TextureMaterial extends Material {

	private TextureRegion textureRegion;

	/**
	 * Creates a TextureMaterial.
	 * 
	 * @param textureRegion TextureRegion.
	 */
	public TextureMaterial(TextureRegion textureRegion) {
		super();
		this.textureRegion = textureRegion;
	}

	/**
	 * Returns the TextureRegion of this material.
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	/**
	 * Sets the TextureRegion of this material.
	 * 
	 * @param textureRegion TextureRegion.
	 */
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

}
