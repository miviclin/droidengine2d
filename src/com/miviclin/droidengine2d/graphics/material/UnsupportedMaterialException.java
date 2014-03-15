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
