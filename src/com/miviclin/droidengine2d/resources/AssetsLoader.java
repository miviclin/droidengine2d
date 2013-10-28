package com.miviclin.droidengine2d.resources;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.miviclin.droidengine2d.util.math.Vector2;

/**
 * Cargador de recursos. Clase de utilidad, no instanciable
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class AssetsLoader {

	/**
	 * Constructor privado porque no tiene sentido instanciar un objeto de esta clase al ser una clase de utilidad cuyos
	 * metodos son todos estaticos. Tambien impide crear subclases de esta clase.
	 */
	private AssetsLoader() {

	}

	/**
	 * Devuelve un InputStream para poder leer un asset
	 * 
	 * @param context Context
	 * @param path Ruta del asset, relativa a la carpeta de assets
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream getAsset(Context context, String path) throws IOException {
		return context.getAssets().open(path);
	}

	/**
	 * Devuelve un InputStream para poder leer un asset
	 * 
	 * @param assetManager AssetManager de la aplicacion
	 * @param path Ruta del asset, relativa a la carpeta de assets
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream getAsset(AssetManager assetManager, String path) throws IOException {
		return assetManager.open(path);
	}

	/**
	 * Carga un Bitmap de la carpeta de assets y lo devuelve
	 * 
	 * @param context Context
	 * @param path Ruta del bitmap, relativa a la carpeta de assets
	 * @return Bitmap
	 */
	public static Bitmap loadBitmap(Context context, String path) {
		Bitmap bitmap = null;
		InputStream in = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		try {
			in = getAsset(context, path);
			bitmap = BitmapFactory.decodeStream(in, null, options);
		} catch (IOException e) {
			throw new RuntimeException("Unable to load '" + path + "'", e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		return bitmap;
	}

	/**
	 * Carga un Bitmap de la carpeta de assets y lo devuelve
	 * 
	 * @param assetManager AssetManager de la aplicacion
	 * @param path Ruta del bitmap, relativa a la carpeta de assets
	 * @return Bitmap
	 */
	public static Bitmap loadBitmap(AssetManager assetManager, String path) {
		Bitmap bitmap = null;
		InputStream in = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		try {
			in = getAsset(assetManager, path);
			bitmap = BitmapFactory.decodeStream(in, null, options);
		} catch (IOException e) {
			throw new RuntimeException("Unable to load '" + path + "'", e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		return bitmap;
	}

	/**
	 * Devuelve las dimensiones de un bitmap que se encuentre en la carpeta de assets.<br>
	 * No carga el bitmap en memoria.
	 * 
	 * @param context Context
	 * @param path Ruta del bitmap, relativa a la carpeta de assets
	 * @return Dimensiones del bitmap
	 */
	public static Vector2 getBitmapBounds(Context context, String path) {
		InputStream in = null;
		int width;
		int height;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		try {
			in = getAsset(context, path);
			BitmapFactory.decodeStream(in, null, options);
			width = options.outWidth;
			height = options.outHeight;
		} catch (IOException e) {
			throw new RuntimeException("Unable to load '" + path + "'", e);
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		return new Vector2(width, height);
	}

	/**
	 * Devuelve un AssetFileDescriptor que se puede utilizar para leer el asset especificado
	 * 
	 * @param context Context
	 * @param path Ruta del asset, relativa a la carpeta de assets
	 * @return AssetFileDescriptor
	 */
	public static AssetFileDescriptor getAssetFileDescriptor(Context context, String path) {
		AssetFileDescriptor descriptor = null;
		try {
			descriptor = context.getAssets().openFd(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return descriptor;
	}

}
