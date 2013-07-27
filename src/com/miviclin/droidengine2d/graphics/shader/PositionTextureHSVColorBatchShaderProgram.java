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
					"varying vec3 vColor;\n" +
					"uniform sampler2D sTexture;\n" +
					"vec3 convertRGBtoHSV(vec3 rgbColor) {\n" +
					"    float r = rgbColor[0];\n" +
					"    float g = rgbColor[1];\n" +
					"    float b = rgbColor[2];\n" +
					"    float colorMax = max(max(r,g), b);\n" +
					"    float colorMin = min(min(r,g), b);\n" +
					"    float delta = max - min;\n" +
					"    float h = 0.0;\n" +
					"    float s = 0.0;\n" +
					"    float v = colorMax;\n" +
					"    vec3 hsv = vec3(0.0);\n" +
					"    if (colorMax != 0.0) {\n" +
					"        s = (colorMax - colorMin ) / colorMax;\n" +
					"    }\n" +
					"    if (delta != 0.0) {\n" +
					"        if (r == colorMax) {\n" +
					"            h = (g - b) / delta;\n" +
					"        } else if (g == colorMax) {\n" +
					"            h = 2.0 + (b - r) / delta;\n" +
					"        } else {\n" +
					"            h = 4.0 + (r - g) / delta;\n" +
					"        }\n" +
					"        h *= 60.0;\n" +
					"        if (h < 0.0) {\n" +
					"            h += 360.0;\n" +
					"        }\n" +
					"    }\n" +
					"    hsv[0] = h;\n" +
					"    hsv[1] = s;\n" +
					"    hsv[2] = v;\n" +
					"    return hsv;\n" +
					"}\n" +
					"vec3 convertHSVtoRGB(vec3 hsvColor) {\n" +
					"    float h = hsvColor.x;\n" +
					"    float s = hsvColor.y;\n" +
					"    float v = hsvColor.z;\n" +
					"    if (s == 0.0) {\n" +
					"        return vec3(v, v, v);\n" +
					"    }\n" +
					"    if (h == 360.0) {\n" +
					"        h = 0.0;\n" +
					"    }\n" +
					"    int hi = int(h);\n" +
					"    float f = h - float(hi);\n" +
					"    float p = v * (1.0 - s);\n" +
					"    float q = v * (1.0 - (s * f));\n" +
					"    float t = v * (1.0 - (s * (1.0 - f)));\n" +
					"    vec3 rgb;\n" +
					"    if (hi == 0) {\n" +
					"        rgb = vec3(v, t, p);\n" +
					"    } else if (hi == 1) {\n" +
					"        rgb = vec3(q, v, p);\n" +
					"    } else if (hi == 2) {\n" +
					"        rgb = vec3(p, v, t);\n" +
					"    } if(hi == 3) {\n" +
					"        rgb = vec3(p, q, v);\n" +
					"    } else if (hi == 4) {\n" +
					"        rgb = vec3(t, p, v);\n" +
					"    } else {\n" +
					"        rgb = vec3(v, p, q);\n" +
					"    }\n" +
					"    return rgb;\n" +
					"}\n" +
					"void main() {\n" +
					"    vec3 fragRGB = texture2D(sTexture, vTextureCoord).rgb;\n" +
					"    vec3 fragHSV = convertRGBtoHSV(fragRGB);\n" +
					"    fragHSV += vHSV;\n" +
					"    fragHSV.x = mod(fragHSV.x, 360.0);\n" +
					"    fragHSV.y = mod(fragHSV.y, 1.0);\n" +
					"    fragHSV.z = mod(fragHSV.z, 1.0);\n" +
					"    fragRGB = convertHSVtoRGB(fragHSV);\n" +
					"    gl_FragColor = vec4(convertHSVtoRGB(fragHSV), 1.0);\n" +
					"}";
		}
		
	}
	
}
