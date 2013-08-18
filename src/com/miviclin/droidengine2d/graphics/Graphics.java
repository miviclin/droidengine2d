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
import com.miviclin.droidengine2d.graphics.mesh.GraphicsBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionColorRectangularShapeBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureColorSpriteBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureHSVColorSpriteBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureOpacitySpriteBatch;
import com.miviclin.droidengine2d.graphics.mesh.PositionTextureSpriteBatch;
import com.miviclin.droidengine2d.graphics.mesh.RectangleBatchMesh;
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
	private Context context;
	private RectangleBatchMesh<? extends Material> currentBatch;
	private HashMap<Class<? extends Material>, RectangleBatchMesh<? extends Material>> batches;
	private Vector2 tempCenter;
	private boolean inBeginEndPair;
	
	/**
	 * Constructor
	 * 
	 * @param camera Camara
	 * @param context Context
	 */
	public Graphics(Camera camera, Context context) {
		this.camera = camera;
		this.context = context;
		this.currentBatch = null;
		this.batches = new HashMap<Class<? extends Material>, RectangleBatchMesh<? extends Material>>();
		this.tempCenter = new Vector2(0, 0);
		this.inBeginEndPair = false;
	}
	
	/**
	 * Metodo que inicializa el objeto graphics. Debe llamarse desde el hilo del renderer, que es el que tiene el contexto de OpenGL.
	 */
	public void initialize() {
		loadBatches();
		for (Entry<Class<? extends Material>, RectangleBatchMesh<? extends Material>> entry : batches.entrySet()) {
			entry.getValue().getShaderProgram().link();
			entry.getValue().initialize();
		}
	}
	
	/**
	 * Carga los batches en el mapa de batches soportados.<br>
	 * Este metodo se llama desde {@link Graphics#initialize()}.<br>
	 * Se pueden agregar mas batches soportados sobreescribiendo este metodo de la siguiente forma:
	 * 
	 * <pre>
	 * 
	 * 
	 * 
	 * 
	 * protected void loadBatches() {
	 * 	super.loadBatches();
	 * 	getBatches().put(TextureColorMaterial.class, new PositionTextureColorSpriteBatch&lt;TextureColorMaterial&gt;(getContext()));
	 * }
	 * </pre>
	 */
	protected void loadBatches() {
		batches.put(ColorMaterial.class, new PositionColorRectangularShapeBatch<ColorMaterial>());
		batches.put(TextureMaterial.class, new PositionTextureSpriteBatch<TextureMaterial>(context));
		batches.put(TransparentTextureMaterial.class, new PositionTextureOpacitySpriteBatch<TransparentTextureMaterial>(context));
		batches.put(TextureColorMaterial.class, new PositionTextureColorSpriteBatch<TextureColorMaterial>(context));
		batches.put(TextureHSVMaterial.class, new PositionTextureHSVColorSpriteBatch<TextureHSVMaterial>(context));
	}
	
	/**
	 * Renderiza una figura rectangular
	 * 
	 * @param material Material
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 */
	public <M extends Material> void draw(M material, Vector2 position, Dimensions2D dimensions) {
		draw(material, position, dimensions, null, 0.0f);
	}
	
	/**
	 * Renderiza una figura rectangular
	 * 
	 * @param material Material
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 */
	public <M extends Material> void draw(M material, Vector2 position, Dimensions2D dimensions, float rotation) {
		draw(material, position, dimensions, null, rotation);
	}
	
	/**
	 * Renderiza una figura rectangular
	 * 
	 * @param material Material
	 * @param position Posicion en la que se renderizara
	 * @param dimensions Dimensiones del sprite
	 * @param center Centro de rotacion
	 * @param rotation Angulo de rotacion del sprite con respecto a su centro
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <M extends Material> void draw(M material, Vector2 position, Dimensions2D dimensions, Vector2 center, float rotation) {
		RectangleBatchMesh batch = batches.get(material.getClass());
		if (batch == null) {
			throw new UnsupportedMaterialException();
		}
		tempCenter.set(dimensions.getWidth() / 2, dimensions.getHeight() / 2);
		selectCurrentBatch(batch);
		batch.setCurrentMaterial(material);
		batch.draw(position, dimensions, (center == null) ? tempCenter : center, rotation, camera);
	}
	
	/**
	 * Renderiza en pantalla los elementos que aun estan en el batch sin renderizar.<br>
	 * Este metodo debe llamarse siempre al final de cada frame para asegurarse de que no queden elementos sin renderizar.
	 */
	public void flush() {
		if (inBeginEndPair && currentBatch != null) {
			currentBatch.end();
			inBeginEndPair = false;
		}
	}
	
	/**
	 * Comprueba si el batch especificado es el seleccionado actualmente, y si no lo es, lo selecciona y lo prepara para su uso.<br>
	 * Si habia elementos sin renderizar en el batch previamente seleccionado, los renderiza antes de seleccionar el nuevo.
	 * 
	 * @param batch RectangleBatchMesh
	 */
	private void selectCurrentBatch(RectangleBatchMesh<?> batch) {
		if (!inBeginEndPair || currentBatch != batch) {
			if (inBeginEndPair) {
				currentBatch.end();
				inBeginEndPair = false;
			}
			currentBatch = batch;
			currentBatch.begin();
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
	 * Devuelve el mapa que almacena los batches soportados actualmente.<br>
	 * Para poder renderizar materials no soportados por defecto, hay que agregar una entrada a este mapa.<br>
	 * La forma recomendada de agregar entradas al mapa es sobreescribiendo el metodo {@link Graphics#loadBatches()}.
	 * 
	 * @return Mapa que contiene los batches soportados por este objeto Graphics indexados por Material
	 */
	protected HashMap<Class<? extends Material>, RectangleBatchMesh<? extends Material>> getBatches() {
		return batches;
	}
	
}
