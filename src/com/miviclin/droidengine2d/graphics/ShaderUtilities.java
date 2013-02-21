package com.miviclin.droidengine2d.graphics;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Clase que se encarga de cargar y compilar los shaders.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class ShaderUtilities {
	
	/**
	 * Crea un programa GLSL a partir de los 2 shaders especificados
	 * 
	 * @param vertexSource Codigo GLSL del vertex shader
	 * @param fragmentSource Codigo GLSL del fragment shader
	 * @return Devuelve el identificador que asigna OpenGL ES 2.0 al programa compilado. Si devuelve 0 es que se ha producido algun error.
	 */
	public static int createProgram(String vertexSource, String fragmentSource) {
		int vertexShader, fragmentShader, program;
		
		vertexShader = compileShader(GLES20.GL_VERTEX_SHADER, vertexSource);
		if (vertexShader == 0) {
			return 0;
		}
		fragmentShader = compileShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
		if (fragmentShader == 0) {
			return 0;
		}
		program = GLES20.glCreateProgram();
		if (program != 0) {
			GLES20.glAttachShader(program, vertexShader);
			GLDebug.checkGLError("glAttachShader vertexShader");
			GLES20.glAttachShader(program, fragmentShader);
			GLDebug.checkGLError("glAttachShader fragmentShader");
			GLES20.glLinkProgram(program);
			int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] != GLES20.GL_TRUE) {
				Log.e(ShaderUtilities.class.getSimpleName(), "Could not link program: ");
				Log.e(ShaderUtilities.class.getSimpleName(), GLES20.glGetProgramInfoLog(program));
				GLES20.glDeleteProgram(program);
				program = 0;
			}
		}
		return program;
	}
	
	/**
	 * Compila el shader.
	 * 
	 * @param shaderType Tipo de shader. Utilizar {@link GLES20#GL_VERTEX_SHADER } o {@link GLES20#GL_FRAGMENT_SHADER }
	 * @param source Codigo GLSL del shader a compilar
	 * @return Devuelve el identificador que asigna OpenGL ES 2.0 al shader compilado
	 */
	private static int compileShader(int shaderType, String source) {
		int shader = GLES20.glCreateShader(shaderType);
		if (shader != 0) {
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			int[] compiled = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
			if (compiled[0] == 0) {
				Log.e(ShaderUtilities.class.getSimpleName(), "Could not compile shader " + shaderType + ":");
				Log.e(ShaderUtilities.class.getSimpleName(), GLES20.glGetShaderInfoLog(shader));
				GLES20.glDeleteShader(shader);
				shader = 0;
			}
		}
		return shader;
	}
	
}
