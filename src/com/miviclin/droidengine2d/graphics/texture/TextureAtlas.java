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
package com.miviclin.droidengine2d.graphics.texture;

import java.util.Map;

import android.content.Context;

/**
 * TextureAtlas.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public interface TextureAtlas {

	/**
	 * Loads the textureatlas from the specified file.
	 * 
	 * @param path File path.
	 * @param context Context.
	 */
	public void loadFromFile(String path, Context context);

	/**
	 * Returns the source texture of this TextureAtlas.
	 * 
	 * @return Texture
	 */
	public Texture getSourceTexture();

	/**
	 * Returns the specified TextureRegion of this TextureAtlas.
	 * 
	 * @param name Name of the TextureRegion.
	 * @return TextureRegion or null if this TextureAtlas does not contain any TextureRegion with the specified name.
	 */
	public TextureRegion getTextureRegion(String name);

	/**
	 * Removes all TextureRegions from this TextureAtlas.<br>
	 * This method should be called when this TextureAtlas is not needed anymore, to release resources.
	 */
	public void clearAtlas();

	/**
	 * Returns the map of TextureRegions of this TextureAtlas.<br>
	 * The TextureRegions are indexed by name.
	 * 
	 * @return {@code Map<String, TextureRegion}
	 */
	public Map<String, TextureRegion> getTextureRegions();
}
