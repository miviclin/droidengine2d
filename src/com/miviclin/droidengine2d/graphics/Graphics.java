package com.miviclin.droidengine2d.graphics;

import java.util.HashMap;
import java.util.Map.Entry;

import android.content.Context;

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
		this.camera = camera;
		this.context = context;
		this.currentRenderer = null;
		this.renderers = new HashMap<Class<? extends Material>, RectangleBatchRenderer<? extends Material>>();
		this.inBeginEndPair = false;
	}
	
	/**
	 * Metodo que inicializa el objeto graphics. Debe llamarse desde el hilo del renderer, que es el que tiene el contexto de OpenGL.
	 * 
	 * @see Graphics#loadMaterialRenderers()
	 */
	public void initialize() {
		loadMaterialRenderers();
		for (Entry<Class<? extends Material>, RectangleBatchRenderer<? extends Material>> entry : renderers.entrySet()) {
			entry.getValue().getShaderProgram().link();
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
		renderers.put(TransparentTextureMaterial.class, new TransparentTextureMaterialBatchRenderer<TransparentTextureMaterial>(context));
		renderers.put(TextureColorMaterial.class, new TextureColorMaterialBatchRenderer<TextureColorMaterial>(context));
		renderers.put(TextureHSVMaterial.class, new TextureHSVMaterialBatchRenderer<TextureHSVMaterial>(context));
	}
	
	/**
	 * Renderiza una figura rectangular
	 * 
	 * @param material Material
	 * @param transform Transform de la figura a representar
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <M extends Material> void draw(M material, Transform transform) {
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
			throw new IllegalArgumentException("The origin of the transform must be between (0, 0) and (scale.getX(), scale.getY()");
		}
		Vector2.divide(tmpOrigin, origin, scale);
		selectCurrentRenderer(batchRenderer);
		batchRenderer.setCurrentMaterial(material);
		batchRenderer.draw(transform.getPosition(), scale, tmpOrigin, transform.getRotation(), camera);
	}
	
	/**
	 * Renderiza en pantalla los elementos que aun estan en el batch sin renderizar.<br>
	 * Este metodo debe llamarse siempre al final de cada frame para asegurarse de que no queden elementos sin renderizar.
	 */
	public void flush() {
		if (inBeginEndPair && currentRenderer != null) {
			currentRenderer.end();
			inBeginEndPair = false;
		}
	}
	
	/**
	 * Comprueba si el renderer especificado es el seleccionado actualmente, y si no lo es, lo selecciona y lo prepara para su uso.<br>
	 * Si habia elementos sin renderizar en el renderer previamente seleccionado, los renderiza antes de seleccionar el nuevo.
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
	 * @return true si se ha llamado a {@link GraphicsBatch#begin()} , pero aun no se ha llamado a {@link GraphicsBatch#end()} en el batch
	 *         seleccionado actualmente
	 */
	protected boolean isInBeginEndPair() {
		return inBeginEndPair;
	}
	
	/**
	 * Devuelve el mapa que almacena los renderers soportados actualmente.<br>
	 * Para poder renderizar materials no soportados por defecto, hay que agregar una entrada a este mapa.<br>
	 * La forma recomendada de agregar entradas al mapa es sobreescribiendo el metodo {@link Graphics#loadMaterialRenderers()}.
	 * 
	 * @return Mapa que contiene los renderers soportados por este objeto Graphics indexados por Material
	 */
	protected HashMap<Class<? extends Material>, RectangleBatchRenderer<? extends Material>> getMaterialRenderers() {
		return renderers;
	}
	
}
