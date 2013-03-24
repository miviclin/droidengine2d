package com.miviclin.droidengine2d.graphics.textures;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;

import com.miviclin.droidengine2d.resources.AssetsLoader;

public class TexturePackerAtlas implements TextureAtlas {
	
	private Texture sourceTexture;
	private HashMap<String, TextureRegion> regions;
	
	public TexturePackerAtlas() {
		this.sourceTexture = null;
		this.regions = new HashMap<String, TextureRegion>();
	}
	
	@Override
	public void loadFromXML(String path, Context context) {
		XmlPullParserFactory factory;
		XmlPullParser xpp;
		int eventType, x, y, width, height, index;
		boolean rotated;
		TextureRegion region;
		String regionName, originalX, originalY, originalWidth, originalHeight;
		String texturePath = null;
		
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
			xpp.setInput(AssetsLoader.getAsset(context, path), null);
			eventType = xpp.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("sprite")) {
						regionName = xpp.getAttributeValue(null, "n");
						
						x = Integer.parseInt(xpp.getAttributeValue(null, "x"));
						y = Integer.parseInt(xpp.getAttributeValue(null, "y"));
						width = Integer.parseInt(xpp.getAttributeValue(null, "w"));
						height = Integer.parseInt(xpp.getAttributeValue(null, "h"));
						
						originalX = xpp.getAttributeValue(null, "oX");
						originalY = xpp.getAttributeValue(null, "oY");
						originalWidth = xpp.getAttributeValue(null, "oW");
						originalHeight = xpp.getAttributeValue(null, "oH");
						
						rotated = xpp.getAttributeValue(null, "r") != null;
						if (rotated) {
							throw new IllegalArgumentException("TexturePacker's rotated textures are not supported");
						}
						if (texturePath == null) {
							throw new NullPointerException("Can not find the texture path. Check TextureAtlas imagePath in the XML " +
									"file and also check that the provided path for tha XML file is correct.");
						}
						if ((originalX != null) || (originalY != null) || (originalWidth != null) || (originalHeight != null)) {
							// Trimmed
							throw new IllegalArgumentException("TexturePacker's trimmed textures are not supported");
						} else {
							// Not trimmed
							if (sourceTexture == null) {
								sourceTexture = new Texture(context, texturePath);
							}
							region = new TextureRegion(sourceTexture, x, y, width, height);
							regions.put(regionName, region);
						}
					} else if (xpp.getName().equals("TextureAtlas")) {
						index = path.lastIndexOf('/');
						if ((index != -1) && (index + 1 <= path.length())) {
							texturePath = path.substring(0, index + 1) + xpp.getAttributeValue(null, "imagePath");
						} else {
							texturePath = xpp.getAttributeValue(null, "imagePath");
						}
					}
				}
				eventType = xpp.next();
			}
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Texture getSourceTexture() {
		return sourceTexture;
	}
	
	@Override
	public TextureRegion getTextureRegion(String name) {
		return regions.get(name);
	}
	
	@Override
	public void clearAtlas() {
		regions.clear();
	}

	@Override
	public Map<String, TextureRegion> getTextureRegions() {
		return regions;
	}
	
}
