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
package com.miviclin.droidengine2d.graphics.animation;

import com.miviclin.droidengine2d.graphics.texture.TextureRegion;

/**
 * Animation frame.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class AnimationFrame {

	private float delay;
	private TextureRegion textureRegion;

	/**
	 * Creates a new AnimationFrame.
	 * 
	 * @param delay Minimum time this frame will be displayed.
	 * @param textureRegion TextureRegion.
	 */
	public AnimationFrame(float delay, TextureRegion textureRegion) {
		super();
		this.delay = delay;
		this.textureRegion = textureRegion;
	}

	/**
	 * Returns the minimum time this frame will be displayed.
	 * 
	 * @return minimum time this frame will be displayed
	 */
	public float getDelay() {
		return delay;
	}

	/**
	 * Sets the minimum time this frame will be displayed.
	 * 
	 * @param delay Minimum time this frame will be displayed.
	 */
	public void setDelay(float delay) {
		this.delay = delay;
	}

	/**
	 * Returns the TextureRegion associated to this AnimationFrame.
	 * 
	 * @return TextureRegion
	 */
	public TextureRegion getTextureRegion() {
		return textureRegion;
	}

	/**
	 * Sets the TextureRegion associated to this AnimationFrame.
	 * 
	 * @param textureRegion TextureRegion.
	 */
	public void setTextureRegion(TextureRegion textureRegion) {
		this.textureRegion = textureRegion;
	}

}
