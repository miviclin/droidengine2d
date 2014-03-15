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
package com.miviclin.droidengine2d.graphics;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * View where the game is renderer with OpenGL.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class GLView extends GLSurfaceView {

	/**
	 * Standard View constructor. In order to render something, you must call {@link #setRenderer} to register a
	 * renderer.
	 * 
	 * @param context Context.
	 */
	public GLView(Context context) {
		super(context);
	}

	/**
	 * Standard View constructor. In order to render something, you must call {@link #setRenderer} to register a
	 * renderer.
	 * 
	 * @param context Context.
	 * @param attrs AttributeSet
	 */
	public GLView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
}
