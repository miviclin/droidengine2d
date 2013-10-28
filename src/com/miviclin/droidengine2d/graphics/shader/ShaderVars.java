package com.miviclin.droidengine2d.graphics.shader;

/**
 * Esta clase contiene constantes que se utilizan para definir las variables de los shaders.
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
