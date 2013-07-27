package com.miviclin.droidengine2d.graphics;

import android.content.Context;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.mesh.PositionColorRectangularShapeBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureHSVColorSpriteBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureOpacitySpriteBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureSpriteBatch;
import com.miviclin.droidengine2d.graphics.shape.RectangularShape;
import com.miviclin.droidengine2d.graphics.shape.Sprite;
import com.miviclin.droidengine2d.graphics.texture.TextureRegion;
import com.miviclin.droidengine2d.util.Dimensions2D;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Esta clase se encarga de renderizar graficos en pantalla
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Graphics {
	
	private Camera camera;
	private ShapeBatch currentShapeBatch;
	private PositionColorRectangularShapeBatch positionColorRB;
	private PositionTextureSpriteBatch positionTextureSB;
	private PositionTextureOpacitySpriteBatch positionTextureOpacitySB;
	private PositionTextureHSVColorSpriteBatch positionTextureColorSB;
	private Sprite tempSprite;
	private RectangularShape tempRectangularShape;
	private Color tempColor;
	private boolean inBeginEndPair;
	
	/**
	 * Constructor
	 * 
	 * @param camera Camara
	 * @param context Context
	 */
	public Graphics(Camera camera, Context context) {
		this.camera = camera;
		this.currentShapeBatch = null;
		this.positionColorRB = new PositionColorRectangularShapeBatch();
		this.positionTextureSB = new PositionTextureSpriteBatch(context);
		this.positionTextureOpacitySB = new PositionTextureOpacitySpriteBatch(context);
		this.positionTextureColorSB = new PositionTextureHSVColorSpriteBatch(context);
		this.tempSprite = null;
		this.tempRectangularShape = null;
		this.tempColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		this.inBeginEndPair = false;
	}
	
	/**
	 * Metodo que inicializa el objeto graphics. Debe llamarse desde el hilo del renderer, que es el que tiene el contexto de OpenGL
	 */
	public void initialize() {
		positionColorRB.getShaderProgram().link();
		positionColorRB.initialize();
		
		positionTextureSB.getShaderProgram().link();
		positionTextureSB.initialize();
		
		positionTextureOpacitySB.getShaderProgram().link();
		positionTextureOpacitySB.initialize();
		
		positionTextureColorSB.getShaderProgram().link();
		positionTextureColorSB.initialize();
	}
	
	/**
	 * Renderiza un rectangulo en pantalla
	 * 
	 * @param color Color del rectangulo
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 */
	public void drawRect(Color color, Vector2 position, Dimensions2D dimensions) {
		drawRect(color, position, dimensions, 0.0f, null, 0.0f);
	}
	
	/**
	 * Renderiza un rectangulo en pantalla
	 * 
	 * @param color Color del rectangulo
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 * @param rotation Angulo de rotacion del sprite con respecto a su centro
	 */
	public void drawRect(Color color, Vector2 position, Dimensions2D dimensions, float rotation) {
		drawRect(color, position, dimensions, rotation, null, 0.0f);
	}
	
	/**
	 * Renderiza un rectangulo en pantalla
	 * 
	 * @param color Color del rectangulo
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 * @param rotation Angulo de rotacion del sprite con respecto a su centro
	 * @param externalCenter Centro externo de rotacion
	 * @param externalRotation Angulo de rotacion sobre el centro externo de rotacion
	 */
	public void drawRect(Color color, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		selectCurrentShapeBatch(positionColorRB);
		setupTempRectangularShape(color, position, dimensions, rotation, externalCenter, externalRotation);
		positionColorRB.draw(tempRectangularShape, camera);
	}
	
	/**
	 * Renderiza un sprite en pantalla
	 * 
	 * @param textureRegion TextureRegion a renderizar
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 */
	public void drawSprite(TextureRegion textureRegion, Vector2 position, Dimensions2D dimensions) {
		drawSprite(textureRegion, position, dimensions, 0.0f, null, 0.0f);
	}
	
	/**
	 * Renderiza un sprite en pantalla
	 * 
	 * @param textureRegion TextureRegion a renderizar
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 * @param rotation Angulo de rotacion del sprite con respecto a su centro
	 */
	public void drawSprite(TextureRegion textureRegion, Vector2 position, Dimensions2D dimensions, float rotation) {
		drawSprite(textureRegion, position, dimensions, rotation, null, 0.0f);
	}
	
	/**
	 * Renderiza un sprite en pantalla
	 * 
	 * @param textureRegion TextureRegion a renderizar
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 * @param rotation Angulo de rotacion del sprite con respecto a su centro
	 * @param externalCenter Centro externo de rotacion
	 * @param externalRotation Angulo de rotacion sobre el centro externo de rotacion
	 */
	public void drawSprite(TextureRegion textureRegion, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		selectCurrentShapeBatch(positionTextureSB);
		setupTempSprite(textureRegion, null, position, dimensions, rotation, externalCenter, externalRotation);
		positionTextureSB.draw(tempSprite, camera);
	}
	
	/**
	 * Renderiza un sprite en pantalla
	 * 
	 * @param textureRegion TextureRegion a renderizar
	 * @param opacity Opacidad del sprite
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 */
	public void drawSprite(TextureRegion textureRegion, float opacity, Vector2 position, Dimensions2D dimensions) {
		drawSprite(textureRegion, opacity, position, dimensions, 0.0f, null, 0.0f);
	}
	
	/**
	 * Renderiza un sprite en pantalla
	 * 
	 * @param textureRegion TextureRegion a renderizar
	 * @param opacity Opacidad del sprite
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 * @param rotation Angulo de rotacion del sprite con respecto a su centro
	 */
	public void drawSprite(TextureRegion textureRegion, float opacity, Vector2 position, Dimensions2D dimensions, float rotation) {
		drawSprite(textureRegion, opacity, position, dimensions, rotation, null, 0.0f);
	}
	
	/**
	 * Renderiza un sprite en pantalla
	 * 
	 * @param textureRegion TextureRegion a renderizar
	 * @param opacity Opacidad del sprite
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 * @param rotation Angulo de rotacion del sprite con respecto a su centro
	 * @param externalCenter Centro externo de rotacion
	 * @param externalRotation Angulo de rotacion sobre el centro externo de rotacion
	 */
	public void drawSprite(TextureRegion textureRegion, float opacity, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		selectCurrentShapeBatch(positionTextureOpacitySB);
		setupTempSprite(textureRegion, null, position, dimensions, rotation, externalCenter, externalRotation);
		tempSprite.getColor().setA(opacity);
		positionTextureOpacitySB.draw(tempSprite, camera);
	}
	
	/**
	 * Renderiza un sprite en pantalla
	 * 
	 * @param textureRegion TextureRegion a renderizar
	 * @param color Color del sprite
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 */
	public void drawSprite(TextureRegion textureRegion, Color color, Vector2 position, Dimensions2D dimensions) {
		drawSprite(textureRegion, color, position, dimensions, 0.0f, null, 0.0f);
	}
	
	/**
	 * Renderiza un sprite en pantalla
	 * 
	 * @param textureRegion TextureRegion a renderizar
	 * @param color Color del sprite
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 * @param rotation Angulo de rotacion del sprite con respecto a su centro
	 */
	public void drawSprite(TextureRegion textureRegion, Color color, Vector2 position, Dimensions2D dimensions, float rotation) {
		drawSprite(textureRegion, color, position, dimensions, rotation, null, 0.0f);
	}
	
	/**
	 * Renderiza un sprite en pantalla
	 * 
	 * @param textureRegion TextureRegion a renderizar
	 * @param color Color del sprite
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 * @param rotation Angulo de rotacion del sprite con respecto a su centro
	 * @param externalCenter Centro externo de rotacion
	 * @param externalRotation Angulo de rotacion sobre el centro externo de rotacion
	 */
	public void drawSprite(TextureRegion textureRegion, Color color, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		selectCurrentShapeBatch(positionTextureColorSB);
		setupTempSprite(textureRegion, color, position, dimensions, rotation, externalCenter, externalRotation);
		positionTextureColorSB.draw(tempSprite, camera);
	}
	
	/**
	 * Renderiza en pantalla los elementos que aun estan en el batch sin renderizar.<br>
	 * Este metodo debe llamarse siempre al final de cada frame para asegurarse de que no queden elementos sin renderizar.
	 */
	public void flush() {
		if (inBeginEndPair && currentShapeBatch != null) {
			currentShapeBatch.end();
			inBeginEndPair = false;
		}
	}
	
	/**
	 * Comprueba si el shapebatch especificado es el seleccionado actualmente, y si no lo es, lo selecciona y lo prepara para su uso.
	 * 
	 * @param shapeBatch ShapeBatch
	 */
	private void selectCurrentShapeBatch(ShapeBatch shapeBatch) {
		if (!inBeginEndPair || currentShapeBatch != shapeBatch) {
			if (inBeginEndPair) {
				currentShapeBatch.end();
				inBeginEndPair = false;
			}
			currentShapeBatch = shapeBatch;
			currentShapeBatch.begin();
			inBeginEndPair = true;
		}
	}
	
	/**
	 * Configura el Sprite temporal, que se pasara al SpriteBatch actualmente seleccionado, con los datos especificados
	 * 
	 * @param textureRegion TextureRegion
	 * @param color Color
	 * @param position Posicion
	 * @param dimensions Dimensiones
	 * @param rotation Angulo de rotacion sobre el centro del sprite
	 * @param externalCenter Centro externo de rotacion
	 * @param externalRotation Angulo de rotacion sobre el centro externo de rotacion
	 */
	private void setupTempSprite(TextureRegion textureRegion, Color color, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		if (tempSprite == null) {
			tempSprite = new Sprite(position.getX(), position.getY(), dimensions.getWidth(), dimensions.getHeight(), textureRegion);
		}
		tempSprite.setTextureRegion(textureRegion);
		tempSprite.getPosition().set(position);
		tempSprite.getDimensions().set(dimensions);
		tempSprite.getCenter().set(dimensions.getWidth() / 2, dimensions.getHeight() / 2);
		tempSprite.setRotation(rotation);
		if (externalCenter == null) {
			tempSprite.setRotationAroundExternalPoint(0.0f, 0.0f, 0.0f);
		} else {
			tempSprite.setRotationAroundExternalPoint(externalRotation, externalCenter);
		}
		if (color != null) {
			tempSprite.getColor().set(color);
		} else {
			tempSprite.getColor().set(tempColor);
		}
	}
	
	/**
	 * Configura el RectangularShape temporal, que se pasara al RectangularShapeBatch actualmente seleccionado, con los datos especificados
	 * 
	 * @param color Color
	 * @param position Posicion
	 * @param dimensions Dimensiones
	 * @param rotation Angulo de rotacion sobre el centro del RectangularShape
	 * @param externalCenter Centro externo de rotacion
	 * @param externalRotation Angulo de rotacion sobre el centro externo de rotacion
	 */
	private void setupTempRectangularShape(Color color, Vector2 position, Dimensions2D dimensions, float rotation, Vector2 externalCenter, float externalRotation) {
		if (tempRectangularShape == null) {
			tempRectangularShape = new RectangularShape(position.getX(), position.getY(), dimensions.getWidth(), dimensions.getHeight());
		}
		tempRectangularShape.getPosition().set(position);
		tempRectangularShape.getDimensions().set(dimensions);
		tempRectangularShape.getCenter().set(dimensions.getWidth() / 2, dimensions.getHeight() / 2);
		tempRectangularShape.setRotation(rotation);
		if (externalCenter == null) {
			tempRectangularShape.setRotationAroundExternalPoint(0.0f, 0.0f, 0.0f);
		} else {
			tempRectangularShape.setRotationAroundExternalPoint(externalRotation, externalCenter);
		}
		if (color != null) {
			tempRectangularShape.getColor().set(color);
		} else {
			tempRectangularShape.getColor().set(tempColor);
		}
	}
	
}
