package com.miviclin.droidengine2d.graphics;

import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Context;
import android.opengl.GLES20;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.ColorMaterial;
import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.material.TextureColorMaterial;
import com.miviclin.droidengine2d.graphics.material.TextureHsvMaterial;
import com.miviclin.droidengine2d.graphics.material.TextureMaterial;
import com.miviclin.droidengine2d.graphics.material.TransparentTextureMaterial;
import com.miviclin.droidengine2d.graphics.material.UnsupportedMaterialException;
import com.miviclin.droidengine2d.graphics.mesh.ColorMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.GraphicsBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.RectangleBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.TextureColorMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.TextureHsvMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.TextureMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.TransparentTextureMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.text.BitmapFont;
import com.miviclin.droidengine2d.graphics.text.FontChar;
import com.miviclin.droidengine2d.util.Transform;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * The Graphics class allows rendering things on screen without needing to know how rendering in implemented internally.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class Graphics {

	private final Vector2 tmpOrigin;
	private final Vector2 tmpScale;
	private final Vector2 tmpPosition;
	private final TextureColorMaterial tmpTextureColorMaterial;

	private Camera camera;
	private Context context;
	private RectangleBatchRenderer<? extends Material> currentRenderer;
	private HashMap<Class<? extends Material>, RectangleBatchRenderer<? extends Material>> renderers;
	private boolean inBeginEndPair;

	/**
	 * Constructor.
	 * 
	 * @param camera Camera.
	 * @param context Context.
	 */
	public Graphics(Camera camera, Context context) {
		this.tmpOrigin = new Vector2(0, 0);
		this.tmpScale = new Vector2(1, 1);
		this.tmpPosition = new Vector2(0, 0);
		this.tmpTextureColorMaterial = new TextureColorMaterial(null, new Color(0, 0, 0));
		this.camera = camera;
		this.context = context;
		this.currentRenderer = null;
		this.renderers = new HashMap<Class<? extends Material>, RectangleBatchRenderer<? extends Material>>();
		this.inBeginEndPair = false;
	}

	/**
	 * Initializes the Graphics object. Must be called from the renderer thread.
	 * 
	 * @see Graphics#loadMaterialRenderers()
	 */
	public void initialize() {
		loadMaterialRenderers();
		for (Entry<Class<? extends Material>, RectangleBatchRenderer<? extends Material>> entry : renderers.entrySet()) {
			entry.getValue().setupShaderProgram();
			entry.getValue().getShaderProgram().compileAndLink();
			entry.getValue().initialize();
		}
	}

	/**
	 * Loads all supported material renderers.<br>
	 * This method is called from {@link Graphics#initialize()}.<br>
	 * More supported renderers could be added overriding this method. For example:
	 * 
	 * <pre>
	 * <code>{@literal @}Override
	 * protected void loadMaterialRenderers() {
	 *     super.loadMaterialRenderers();
	 *     getMaterialRenderers().put(TextureColorMaterial.class, new TextureColorMaterialBatchRenderer{@code<TextureColorMaterial>}(getContext()));
	 * }
	 * </code>
	 * </pre>
	 */
	protected void loadMaterialRenderers() {
		renderers.put(ColorMaterial.class, new ColorMaterialBatchRenderer<ColorMaterial>());
		renderers.put(TextureMaterial.class, new TextureMaterialBatchRenderer<TextureMaterial>(context));
		renderers.put(TextureColorMaterial.class, new TextureColorMaterialBatchRenderer<TextureColorMaterial>(context));
		renderers.put(TextureHsvMaterial.class, new TextureHsvMaterialBatchRenderer<TextureHsvMaterial>(context));
		renderers.put(TransparentTextureMaterial.class,
				new TransparentTextureMaterialBatchRenderer<TransparentTextureMaterial>(context));
	}

	/**
	 * Renders a rectangular shape with the specified Material and Transform.
	 * 
	 * @param material Material.
	 * @param transform Transform.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <M extends Material> void drawRect(M material, Transform transform) {
		RectangleBatchRenderer batchRenderer = renderers.get(material.getClass());
		if (batchRenderer == null) {
			throw new UnsupportedMaterialException(material.getClass());
		}

		Vector2 scale = transform.getScale();
		if (scale.getX() < 1 || scale.getY() < 1) {
			throw new IllegalArgumentException("The scale of the transform has to be at least (1, 1)");
		}

		Vector2 origin = transform.getOrigin();
		if (origin.getX() < 0 || origin.getX() > scale.getX() || origin.getY() < 0 || origin.getY() > scale.getY()) {
			throw new IllegalArgumentException("" +
					"The origin of the transform must be between (0, 0) and (scale.getX(), scale.getY()");
		}
		Vector2.divide(tmpOrigin, origin, scale);
		selectCurrentRenderer(batchRenderer);
		batchRenderer.setCurrentMaterial(material);
		batchRenderer.draw(transform.getPosition(), scale, tmpOrigin, transform.getRotation(), camera);
	}

	/**
	 * Renders text.
	 * 
	 * @param text Text to render.
	 * @param font Font to be used.
	 * @param position Position of the top-left corner of the first letter of the text.
	 * @param fontSizePx Text scale in pixels.
	 * @param color Text color.
	 */
	public void drawText(CharSequence text, BitmapFont font, Vector2 position, float fontSizePx, Color color) {
		drawText(text, font, position, fontSizePx, null, 0.0f, color);
	}

	/**
	 * Renders text.
	 * 
	 * @param text Text to render.
	 * @param font Font to be used.
	 * @param position Position of the top-left corner of the first letter of the text.
	 * @param fontSizePx Text scale in pixels.
	 * @param rotationPoint Rotation point (anchor).
	 * @param rotation Rotation angle.
	 * @param color Text color.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void drawText(CharSequence text, BitmapFont font, Vector2 position, float fontSizePx, Vector2 rotationPoint,
			float rotation, Color color) {

		RectangleBatchRenderer batchRenderer = renderers.get(TextureColorMaterial.class);
		if (batchRenderer == null) {
			throw new UnsupportedMaterialException(TextureColorMaterial.class);
		}
		if (fontSizePx < 1 || fontSizePx < 1) {
			throw new IllegalArgumentException("fontSizePx has to be at least 1");
		}
		selectCurrentRenderer(batchRenderer);
		batchRenderer.setCurrentMaterial(tmpTextureColorMaterial);
		tmpTextureColorMaterial.getColor().set(color);
		tmpOrigin.set(0, 1);

		int textLength = text.length();
		float scaleRatio = fontSizePx / font.getSize();
		float posX = position.getX();
		float posY = position.getY();

		FontChar currentChar;
		FontChar lastChar = null;
		float cosR, sinR;
		for (int i = 0; i < textLength; i++) {
			currentChar = font.getCharacter((int) text.charAt(i));
			if (lastChar != null) {
				posX += lastChar.getKernings().get(currentChar.getId()) * scaleRatio;
			}
			posX += currentChar.getxOffset() * scaleRatio;
			posY = position.getY() - currentChar.getyOffset() * scaleRatio;
			tmpScale.setX(currentChar.getTextureRegion().getWidth() * scaleRatio);
			tmpScale.setY(currentChar.getTextureRegion().getHeight() * scaleRatio);

			if (rotation != 0 && rotationPoint != null) {
				cosR = (float) Math.cos(Math.toRadians(rotation));
				sinR = (float) Math.sin(Math.toRadians(rotation));

				tmpPosition.setX(((posX - rotationPoint.getX()) * cosR - (posY - rotationPoint.getY()) * sinR)
						+ rotationPoint.getX());

				tmpPosition.setY(((posY - rotationPoint.getY()) * cosR + (posX - rotationPoint.getX()) * sinR)
						+ rotationPoint.getY());

			} else {
				tmpPosition.set(posX, posY);
			}

			tmpTextureColorMaterial.setTextureRegion(currentChar.getTextureRegion());
			batchRenderer.draw(tmpPosition, tmpScale, tmpOrigin, (rotationPoint != null) ? rotation : 0.0f, camera);
			posX += currentChar.getxAdvance() * scaleRatio;
			lastChar = currentChar;
		}
	}

	/**
	 * Sets the background color of the GLView to the specified Color.
	 * 
	 * @param color Color.
	 */
	public void setBackgroundColor(Color color) {
		GLES20.glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
	}

	/**
	 * Flushes the current material renderer, rendering the remaining elements.<br>
	 * This method should be called once at the end of each frame to ensure that all elements are rendered.
	 */
	public void flush() {
		if (inBeginEndPair && currentRenderer != null) {
			inBeginEndPair = false;
			currentRenderer.end();
		}
	}

	/**
	 * Checks if the specified renderer is the currently selected one. If it isn't, the specified rendered is now the
	 * currently selected renderer and prepares it to be used. If the previously selected renderer has elements batched
	 * for rendering, they are rendered before swapping renderers.
	 * 
	 * @param renderer RectangleBatchMesh.
	 */
	private void selectCurrentRenderer(RectangleBatchRenderer<?> renderer) {
		if (!inBeginEndPair || currentRenderer != renderer) {
			if (inBeginEndPair) {
				currentRenderer.end();
				inBeginEndPair = false;
			}
			currentRenderer = renderer;
			currentRenderer.begin();
			inBeginEndPair = true;
		}
	}

	/**
	 * Returns the camera.
	 * 
	 * @return Camera
	 */
	protected Camera getCamera() {
		return camera;
	}

	/**
	 * Returns the Context where the game runs.
	 * 
	 * @return Context
	 */
	protected Context getContext() {
		return context;
	}

	/**
	 * Returns true if there is one or more elements pending to be rendered in the currently selected renderer.
	 * 
	 * @return true if {@link GraphicsBatchRenderer#begin()} has been called, but {@link GraphicsBatchRenderer#end()}
	 *         has not been called yet on the currently selected renderer, false otherwise
	 */
	protected boolean isInBeginEndPair() {
		return inBeginEndPair;
	}

	/**
	 * Returns the map that stores the supported material renderers.<br>
	 * In order for Graphics to be able to render a material that is not supported by default, a material renderer able
	 * to render such material must be registered in this map.<br>
	 * In order to add a new material renderer to the map returned by this method, the recommended way is overriding
	 * {@link Graphics#loadMaterialRenderers()}.
	 * 
	 * @return Map containing all material renderers supported by this Graphics object.
	 */
	protected HashMap<Class<? extends Material>, RectangleBatchRenderer<? extends Material>> getMaterialRenderers() {
		return renderers;
	}

}
