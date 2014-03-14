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
package com.miviclin.droidengine2d.graphics.shader;

/**
 * Constants for the names of the most used variables in the GLSL code of the default shaders.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ShaderVars {

	// Uniforms

	public static final String U_MVP_MATRIX = "uMVPMatrix";

	// Attributes

	public static final String A_MVP_MATRIX_INDEX = "aMVPMatrixIndex";
	public static final String A_POSITION = "aPosition";
	public static final String A_COLOR = "aColor";
	public static final String A_TEXTURE_COORD = "aTextureCoord";
	public static final String A_OPACITY = "aOpacity";

	// Varyings

	public static final String V_COLOR = "vColor";
	public static final String V_TEXTURE_COORD = "vTextureCoord";
	public static final String V_OPACITY = "vOpacity";

}
