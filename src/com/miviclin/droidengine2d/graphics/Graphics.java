package com.miviclin.droidengine2d.graphics;

import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Context;
import android.opengl.GLES20;

import com.miviclin.droidengine2d.graphics.cameras.Camera;
import com.miviclin.droidengine2d.graphics.material.ColorMaterial;
import com.miviclin.droidengine2d.graphics.material.Material;
import com.miviclin.droidengine2d.graphics.material.TextureColorMaterial;
import com.miviclin.droidengine2d.graphics.material.TextureHSVMaterial;
import com.miviclin.droidengine2d.graphics.material.TextureMaterial;
import com.miviclin.droidengine2d.graphics.material.TransparentTextureMaterial;
import com.miviclin.droidengine2d.graphics.mesh.ColorMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.GraphicsBatch;
import com.miviclin.droidengine2d.graphics.mesh.RectangleBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.TextureColorMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.TextureHSVMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.TextureMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.mesh.TransparentTextureMaterialBatchRenderer;
import com.miviclin.droidengine2d.graphics.text.BitmapFont;
import com.miviclin.droidengine2d.graphics.text.FontChar;
import com.miviclin.droidengine2d.util.Transform;
import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Esta clase se encarga de renderizar graficos en pantalla
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
	 * Constructor
	 * 
	 * @param camera Camara
	 * @param context Context
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
	 * Metodo que inicializa el objeto graphics. Debe llamarse desde el hilo del renderer, que es el que tiene el
	 * contexto de OpenGL.
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
	 * Carga los renderers de materiales en el mapa de renderers soportados.<br>
	 * Este metodo se llama desde {@link Graphics#initialize()}.<br>
	 * Se pueden agregar mas renderers soportados sobreescribiendo este metodo de la siguiente forma:
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
		renderers.put(TextureHSVMaterial.class, new TextureHSVMaterialBatchRenderer<TextureHSVMaterial>(context));
		renderers.put(TransparentTextureMaterial.class,
				new TransparentTextureMaterialBatchRenderer<TransparentTextureMaterial>(context));
	}

	/**
	 * Renderiza una figura rectangular
	 * 
	 * @param material Material
	 * @param transform Transform de la figura a representar
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <M extends Material> void drawRect(M material, Transform transform) {
		RectangleBatchRenderer batchRenderer = renderers.get(material.getClass());
		if (batchRenderer == null) {
			throw new UnsupportedMaterialException();
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
	 * Renderiza texto en pantalla
	 * 
	 * @param text Texto a mostrar
	 * @param font Fuente
	 * @param position Posicion de la esquina superior izquierda de la primera letra
	 * @param fontSizePx Escala del texto en pixeles
	 * @param color Color del texto
	 */
	public void drawText(CharSequence text, BitmapFont font, Vector2 position, float fontSizePx, Color color) {
		drawText(text, font, position, fontSizePx, null, 0.0f, color);
	}

	/**
	 * Renderiza texto en pantalla
	 * 
	 * @param text Texto a mostrar
	 * @param font Fuente
	 * @param position Posicion de la esquina superior izquierda de la primera letra
	 * @param fontSizePx Escala del texto en pixeles
	 * @param rotationPoint Punto de rotacion (puede ser un punto externo al texto)
	 * @param rotation Angulo de rotacion
	 * @param color Color del texto
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void drawText(CharSequence text, BitmapFont font, Vector2 position, float fontSizePx, Vector2 rotationPoint,
			float rotation, Color color) {

		RectangleBatchRenderer batchRenderer = renderers.get(TextureColorMaterial.class);
		if (batchRenderer == null) {
			throw new UnsupportedMaterialException();
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
	 * Asigna el color especificado al color de fondo del GLView
	 * 
	 * @param color Color
	 */
	public void setBackgroundColor(Color color) {
		GLES20.glClearColor(color.getR(), color.getG(), color.getB(), color.getA());
	}

	/**
	 * Renderiza en pantalla los elementos que aun estan en el batch sin renderizar.<br>
	 * Este metodo debe llamarse siempre al final de cada frame para asegurarse de que no queden elementos sin
	 * renderizar.
	 */
	public void flush() {
		if (inBeginEndPair && currentRenderer != null) {
			inBeginEndPair = false;
			currentRenderer.end();
		}
	}

	/**
	 * Comprueba si el renderer especificado es el seleccionado actualmente, y si no lo es, lo selecciona y lo prepara
	 * para su uso.<br>
	 * Si habia elementos sin renderizar en el renderer previamente seleccionado, los renderiza antes de seleccionar el
	 * nuevo.
	 * 
	 * @param renderer RectangleBatchMesh
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
	 * Devuelve la camara
	 * 
	 * @return Camera
	 */
	protected Camera getCamera() {
		return camera;
	}

	/**
	 * Devuelve el Context en el que se ejecuta el juego
	 * 
	 * @return Context
	 */
	protected Context getContext() {
		return context;
	}

	/**
	 * Devuelve si aun hay elementos pendientes de ser renderizados en el batch que esta actualmente en uso
	 * 
	 * @return true si se ha llamado a {@link GraphicsBatch#begin()} , pero aun no se ha llamado a
	 *         {@link GraphicsBatch#end()} en el batch seleccionado actualmente
	 */
	protected boolean isInBeginEndPair() {
		return inBeginEndPair;
	}

	/**
	 * Devuelve el mapa que almacena los renderers soportados actualmente.<br>
	 * Para poder renderizar materials no soportados por defecto, hay que agregar una entrada a este mapa.<br>
	 * La forma recomendada de agregar entradas al mapa es sobreescribiendo el metodo
	 * {@link Graphics#loadMaterialRenderers()}.
	 * 
	 * @return Mapa que contiene los renderers soportados por este objeto Graphics indexados por Material
	 */
	protected HashMap<Class<? extends Material>, RectangleBatchRenderer<? extends Material>> getMaterialRenderers() {
		return renderers;
	}

}
