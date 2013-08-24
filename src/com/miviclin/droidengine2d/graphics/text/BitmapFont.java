package com.miviclin.droidengine2d.graphics.text;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.SparseArray;

import com.miviclin.droidengine2d.graphics.texture.Texture;
import com.miviclin.droidengine2d.graphics.texture.TextureRegion;
import com.miviclin.droidengine2d.resources.AssetsLoader;

/**
 * Clase que representa una fuente cargada de un bitmap.<br>
 * Esta clase utiliza la misma estructura que BMFont (http://www.angelcode.com/products/bmfont/), de forma que se puede utilizar para
 * representar fuentes procesadas por dicho programa.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class BitmapFont implements Font {
	
	// info
	private String face;
	private int size;
	private boolean bold;
	private boolean italic;
	private String charset;
	private boolean unicode;
	private int stretchH;
	private boolean smooth;
	private int antialiasing;
	private int paddingLeft;
	private int paddingRight;
	private int paddingTop;
	private int paddingBottom;
	private int spacingHorizontal;
	private int spacingVertical;
	private int outline;
	
	// common
	private int lineHeight;
	private int baseFromTop;
	private int scaleW;
	private int scaleH;
	private boolean packed;
	private int alphaChannel;
	private int redChannel;
	private int greenChannel;
	private int blueChannel;
	
	// letters
	private SparseArray<FontChar> letters;
	
	public BitmapFont() {
		
		this.letters = new SparseArray<FontChar>();
	}
	
	@Override
	public void loadFromXML(String path, Context context) {
		XmlPullParserFactory factory;
		XmlPullParser xpp;
		int eventType, index, pageId, kerningFirst, kerningSecond, kerningAmount;
		int charId, charX, charY, charWidth, charHeight, charXOffset, charYOffset, charXAdvance, charPage, charChannel;
		TextureRegion textureRegion;
		String texturePath;
		SparseArray<Texture> pages = new SparseArray<Texture>();
		
		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			xpp = factory.newPullParser();
			xpp.setInput(AssetsLoader.getAsset(context, path), null);
			eventType = xpp.getEventType();
			
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					if (xpp.getName().equals("char")) {
						charId = Integer.parseInt(xpp.getAttributeValue(null, "id"));
						charX = Integer.parseInt(xpp.getAttributeValue(null, "x"));
						charY = Integer.parseInt(xpp.getAttributeValue(null, "y"));
						charWidth = Integer.parseInt(xpp.getAttributeValue(null, "width"));
						charHeight = Integer.parseInt(xpp.getAttributeValue(null, "height"));
						charXOffset = Integer.parseInt(xpp.getAttributeValue(null, "xoffset"));
						charYOffset = Integer.parseInt(xpp.getAttributeValue(null, "yoffset"));
						charXAdvance = Integer.parseInt(xpp.getAttributeValue(null, "xadvance"));
						charPage = Integer.parseInt(xpp.getAttributeValue(null, "page"));
						charChannel = Integer.parseInt(xpp.getAttributeValue(null, "chnl"));
						textureRegion = new TextureRegion(pages.get(charPage), charX, charY, charWidth, charHeight);
						this.letters.append(charId, new FontChar(charId, textureRegion, charXOffset, charYOffset, charXAdvance, charChannel));
						
					} else if (xpp.getName().equals("kerning")) {
						kerningFirst = Integer.parseInt(xpp.getAttributeValue(null, "first"));
						kerningSecond = Integer.parseInt(xpp.getAttributeValue(null, "second"));
						kerningAmount = Integer.parseInt(xpp.getAttributeValue(null, "amount"));
						this.letters.get(kerningFirst).getKernings().append(kerningSecond, kerningAmount);
						
					} else if (xpp.getName().equals("page")) {
						pageId = Integer.parseInt(xpp.getAttributeValue(null, "id"));
						index = path.lastIndexOf('/');
						if ((index != -1) && (index + 1 <= path.length())) {
							texturePath = path.substring(0, index + 1) + xpp.getAttributeValue(null, "file");
						} else {
							texturePath = xpp.getAttributeValue(null, "file");
						}
						pages.put(pageId, new Texture(context, texturePath));
						
					} else if (xpp.getName().equals("chars")) {
						int charCount = Integer.parseInt(xpp.getAttributeValue(null, "count"));
						this.letters = new SparseArray<FontChar>(charCount);
						
					} else if (xpp.getName().equals("info")) {
						this.face = xpp.getAttributeValue(null, "face");
						this.size = Math.abs(Integer.parseInt(xpp.getAttributeValue(null, "chnl")));
						this.bold = Integer.parseInt(xpp.getAttributeValue(null, "bold")) == 1;
						this.italic = Integer.parseInt(xpp.getAttributeValue(null, "italic")) == 1;
						this.charset = xpp.getAttributeValue(null, "charset");
						this.unicode = Integer.parseInt(xpp.getAttributeValue(null, "unicode")) == 1;
						this.stretchH = Integer.parseInt(xpp.getAttributeValue(null, "unicode"));
						this.smooth = Integer.parseInt(xpp.getAttributeValue(null, "smooth")) == 1;
						this.antialiasing = Integer.parseInt(xpp.getAttributeValue(null, "aa"));
						
						String[] padding = xpp.getAttributeValue(null, "padding").split(",");
						this.paddingTop = Integer.parseInt(padding[0]);
						this.paddingRight = Integer.parseInt(padding[1]);
						this.paddingBottom = Integer.parseInt(padding[2]);
						this.paddingLeft = Integer.parseInt(padding[3]);
						
						String[] spacing = xpp.getAttributeValue(null, "spacing").split(",");
						this.spacingHorizontal = Integer.parseInt(spacing[0]);
						this.spacingVertical = Integer.parseInt(spacing[1]);
						
						this.outline = Integer.parseInt(xpp.getAttributeValue(null, "outline"));
						
					} else if (xpp.getName().equals("common")) {
						this.lineHeight = Integer.parseInt(xpp.getAttributeValue(null, "lineheight"));
						this.baseFromTop = Integer.parseInt(xpp.getAttributeValue(null, "base"));
						this.scaleW = Integer.parseInt(xpp.getAttributeValue(null, "scaleW"));
						this.scaleH = Integer.parseInt(xpp.getAttributeValue(null, "scaleH"));
						this.packed = Integer.parseInt(xpp.getAttributeValue(null, "packed")) == 1;
						this.alphaChannel = Integer.parseInt(xpp.getAttributeValue(null, "alphaChnl"));
						this.redChannel = Integer.parseInt(xpp.getAttributeValue(null, "redChnl"));
						this.greenChannel = Integer.parseInt(xpp.getAttributeValue(null, "greenChnl"));
						this.blueChannel = Integer.parseInt(xpp.getAttributeValue(null, "blueChnl"));
						
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
	public FontChar getLetter(int id) {
		return letters.get(id);
	}
	
	@Override
	public void clear() {
		letters.clear();
	}
	
	public String getFace() {
		return face;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean isBold() {
		return bold;
	}
	
	public boolean isItalic() {
		return italic;
	}
	
	public String getCharset() {
		return charset;
	}
	
	public boolean isUnicode() {
		return unicode;
	}
	
	public int getStretchH() {
		return stretchH;
	}
	
	public boolean isSmooth() {
		return smooth;
	}
	
	public int getAntialiasing() {
		return antialiasing;
	}
	
	public int getPaddingLeft() {
		return paddingLeft;
	}
	
	public int getPaddingRight() {
		return paddingRight;
	}
	
	public int getPaddingTop() {
		return paddingTop;
	}
	
	public int getPaddingBottom() {
		return paddingBottom;
	}
	
	public int getSpacingHorizontal() {
		return spacingHorizontal;
	}
	
	public int getSpacingVertical() {
		return spacingVertical;
	}
	
	public int getOutline() {
		return outline;
	}
	
	public int getLineHeight() {
		return lineHeight;
	}
	
	public int getBaseFromTop() {
		return baseFromTop;
	}
	
	public int getScaleW() {
		return scaleW;
	}
	
	public int getScaleH() {
		return scaleH;
	}
	
	public boolean isPacked() {
		return packed;
	}
	
	public int getAlphaChannel() {
		return alphaChannel;
	}
	
	public int getRedChannel() {
		return redChannel;
	}
	
	public int getGreenChannel() {
		return greenChannel;
	}
	
	public int getBlueChannel() {
		return blueChannel;
	}
	
	public SparseArray<FontChar> getLetters() {
		return letters;
	}
	
}
