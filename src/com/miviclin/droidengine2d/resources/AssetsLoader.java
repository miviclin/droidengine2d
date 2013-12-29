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
 * Utility class that can be used to load resources.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class AssetsLoader {

	/**
	 * Private constructor. This class should not be instantiable.
	 */
	private AssetsLoader() {

	}

	/**
	 * Returns an opened InputStream to read the specified asset.
	 * 
	 * @param context Context.
	 * @param path File path. Relative to the assets folder.
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream getAsset(Context context, String path) throws IOException {
		return context.getAssets().open(path);
	}

	/**
	 * Returns an opened InputStream to read the specified asset.
	 * 
	 * @param assetManager AssetManager.
	 * @param path File path. Relative to the assets folder.
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream getAsset(AssetManager assetManager, String path) throws IOException {
		return assetManager.open(path);
	}

	/**
	 * Loads a Bitmap from the assets folder.
	 * 
	 * @param context Context.
	 * @param path File path. Relative to the assets folder.
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
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return bitmap;
	}

	/**
	 * Loads a Bitmap from the assets folder.
	 * 
	 * @param assetManager AssetManager.
	 * @param path File path. Relative to the assets folder.
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
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return bitmap;
	}

	/**
	 * Returns the bounds of the specified Bitmap. This method does not load the Bitmap into memory.
	 * 
	 * @param context Context.
	 * @param path File path. Relative to the assets folder.
	 * @return Vector2 that contains the width and height of the specified Bitmap
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
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return new Vector2(width, height);
	}

	/**
	 * Returns an AssetFileDescriptor that can be used to read the specified asset.
	 * 
	 * @param context Context.
	 * @param path File path. Relative to the assets folder.
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
