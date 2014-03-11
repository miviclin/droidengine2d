package com.miviclin.droidengine2d.graphics.material;

/**
 * This exception should be thrown if the Graphics object does not support a material that is tried to render.
 * 
 * @author Miguel Vicente Linares
 * 
 */
@SuppressWarnings("serial")
public class UnsupportedMaterialException extends RuntimeException {

	/**
	 * Constructs a new UnsupportedMaterialException with the current stack trace and the specified detail message.
	 * 
	 * @param detailMessage The detail message for this exception.
	 */
	public UnsupportedMaterialException(String detailMessage) {
		super(detailMessage);
	}

	/**
	 * Constructs a new UnsupportedMaterialException with the current stack trace and the following detail message:<br>
	 * 
	 * <pre>
	 * The following material is not supported: {@code <name of the specified material>}
	 * </pre>
	 */
	public UnsupportedMaterialException(Class<? extends Material> unsupportedMaterialClass) {
		super("The following material is not supported: " + unsupportedMaterialClass.getName());
	}
}
