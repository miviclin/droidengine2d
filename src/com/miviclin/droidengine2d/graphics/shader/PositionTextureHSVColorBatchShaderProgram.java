package com.miviclin.droidengine2d.graphics.shader;



public class PositionTextureHSVColorBatchShaderProgram extends PositionTextureColorBatchShaderProgram {
	
	/**
	 * Crea un PositionTextureOpacityBatchShaderProgram
	 */
	public PositionTextureHSVColorBatchShaderProgram() {
		super(new PositionTextureHSVColorBatchShaderDefinitions());
	}
	
	/**
	 * Crea un PositionTextureOpacityBatchShaderProgram
	 * 
	 * @param shaderDefinitions Objeto que define los shaders
	 */
	protected PositionTextureHSVColorBatchShaderProgram(ShaderDefinitions shaderDefinitions) {
		super(shaderDefinitions);
	}
	
	/**
	 * Definiciones de los shaders
	 * 
	 * @author Miguel Vicente Linares
	 * 
	 */
	protected static class PositionTextureHSVColorBatchShaderDefinitions extends ShaderDefinitions {
		
		@Override
		public String getVertexShaderDefinition() {
			return "" +
					"uniform mat4 uMVPMatrix[32];\n" +
					"attribute float aMVPMatrixIndex;\n" +
					"attribute vec4 aPosition;\n" +
					"attribute vec2 aTextureCoord;\n" +
					"attribute vec4 aColor;\n" +
					"varying vec2 vTextureCoord;\n" +
					"varying vec4 vColor;\n" +
					"void main() {\n" +
					"    gl_Position = uMVPMatrix[int(aMVPMatrixIndex)] * aPosition;\n" +
					"    vTextureCoord = aTextureCoord;\n" +
					"    vColor = aColor;\n" +
					"}";
		}
		
		@Override
		public String getFragmentShaderDefinition() {
			return "" +
					"precision mediump float;\n" +
					"varying vec2 vTextureCoord;\n" + 
					"varying vec4 vColor;\n" +
					"uniform sampler2D sTexture;\n" +
					/*"vec3 Hue(float H) {\n" + 
					"    float R = abs(H * 6.0 - 3.0) - 1.0;\n" + 
					"    float G = 2.0 - abs(H * 6.0 - 2.0);\n" + 
					"    float B = 2.0 - abs(H * 6.0 - 4.0);\n" + 
					"    return clamp(vec3(R,G,B), 0.0, 1.0);\n" + 
					"}\n" + 
					"\n" + 
					"vec3 HSVtoRGB(vec3 HSV) {\n" + 
					"    return vec3(((Hue(HSV.x) - 1.0) * HSV.y + 1.0) * HSV.z);\n" + 
					"}\n" + 
					"\n" + 
					"vec3 RGBtoHSV(vec3 RGB) {\n" + 
					"    vec3 HSV = vec3(0.0, 0.0, 0.0);\n" + 
					"    HSV.z = max(RGB.r, max(RGB.g, RGB.b));\n" + 
					"    float M = min(RGB.r, min(RGB.g, RGB.b));\n" + 
					"    float C = HSV.z - M;\n" + 
					"    if (C != 0.0) {\n" + 
					"        HSV.y = C / HSV.z;\n" + 
					"        vec3 Delta = (HSV.z - RGB) / C;\n" + 
					"        Delta.rgb -= Delta.brg;\n" + 
					"        Delta.rg += vec2(2.0, 4.0);\n" + 
					"        if (RGB.r >= HSV.z) {\n" + 
					"            HSV.x = Delta.b;\n" + 
					"		}\n" + 
					"        else if (RGB.g >= HSV.z) {\n" + 
					"            HSV.x = Delta.r;\n" + 
					"		}\n" + 
					"        else {\n" + 
					"            HSV.x = Delta.g;\n" + 
					"        }\n" + 
					"		HSV.x = fract(HSV.x / 6.0);\n" + 
					"    }\n" + 
					"    return HSV;\n" + 
					"}\n" +*/
					"vec3 RGBtoHSV(vec3 c) {\n" +
					"    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\n" +
					"    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\n" +
					"    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\n" +
					"    float d = q.x - min(q.w, q.y);\n" +
					"    float e = 1.0e-10;\n" +
					"    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);\n" +
					"}\n" +
					"vec3 HSVtoRGB(vec3 c) {\n" +
					"    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);\n" +
					"    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);\n" +
					"    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);\n" +
					"}\n" +
					"void main() {\n" +
					"    vec4 textureColor = texture2D(sTexture, vTextureCoord);\n" +
					"    vec3 fragRGB = textureColor.rgb;\n" +
					"    vec3 fragHSV = RGBtoHSV(fragRGB);\n" +
					"    fragHSV.x += vColor.x / 360.0;\n" +
					"    fragHSV.yz *= vColor.yz;\n" +
					"    fragHSV.xyz = mod(fragHSV.xyz, 1.0);\n" +
					"    fragRGB = HSVtoRGB(fragHSV);\n" +
					"    gl_FragColor = vec4(fragRGB, textureColor.w);\n" +
					"}";
		}
		
	}
	
}
