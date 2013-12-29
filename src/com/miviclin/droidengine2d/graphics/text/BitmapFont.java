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
 * BitmapFont.<br>
 * This class is able to load a the font format generated by BMFont.<br>
 * BMFont can be downloaded from: <a href="http://www.angelcode.com/products/bmfont/">
 * http://www.angelcode.com/products/bmfont/</a>.
 * 
 * @author Miguel Vicente Linares
 * 
 */
public class BitmapFont implements Font {

	public static final int CHANNEL_HOLDS_GLYPH = 0;
	public static final int CHANNEL_HOLDS_OUTLINE = 1;
	public static final int CHANNEL_HOLDS_GLYPH_AND_OUTLINE = 2;
	public static final int CHANNEL_VALUE_SET_TO_ZERO = 3;
	public static final int CHANNEL_VALUE_SET_TO_ONE = 4;

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

	// font characters
	private SparseArray<FontChar> characters;

	// font texture atlases
	private SparseArray<Texture> texturePages;

	/**
	 * Creates a new {@link BitmapFont}.<br>
	 * The font's data is not loaded after creation. It can be loaded with {@link #loadFromFile(String, Context)}.
	 */
	public BitmapFont() {
		super();
	}

	@Override
	public void loadFromFile(String path, Context context) {
		XmlPullParserFactory factory;
		XmlPullParser xpp;
		int eventType, index, pageId, kerningFirst, kerningSecond, kerningAmount;
		int charId, charX, charY, charW, charH, charXOffset, charYOffset, charXAdvance, charPage, charChnl;
		TextureRegion texRegion;
		Texture texture;
		String texturePath;
		FontChar fontChar;
		this.texturePages = new SparseArray<Texture>();

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
						charW = Integer.parseInt(xpp.getAttributeValue(null, "width"));
						charH = Integer.parseInt(xpp.getAttributeValue(null, "height"));
						charXOffset = Integer.parseInt(xpp.getAttributeValue(null, "xoffset"));
						charYOffset = Integer.parseInt(xpp.getAttributeValue(null, "yoffset"));
						charXAdvance = Integer.parseInt(xpp.getAttributeValue(null, "xadvance"));
						charPage = Integer.parseInt(xpp.getAttributeValue(null, "page"));
						charChnl = Integer.parseInt(xpp.getAttributeValue(null, "chnl"));
						texRegion = new TextureRegion(this.texturePages.get(charPage), charX, charY, charW, charH);
						fontChar = new FontChar(charId, texRegion, charXOffset, charYOffset, charXAdvance, charChnl);
						this.characters.append(charId, fontChar);

					} else if (xpp.getName().equals("kerning")) {
						kerningFirst = Integer.parseInt(xpp.getAttributeValue(null, "first"));
						kerningSecond = Integer.parseInt(xpp.getAttributeValue(null, "second"));
						kerningAmount = Integer.parseInt(xpp.getAttributeValue(null, "amount"));
						this.characters.get(kerningFirst).getKernings().append(kerningSecond, kerningAmount);

					} else if (xpp.getName().equals("page")) {
						pageId = Integer.parseInt(xpp.getAttributeValue(null, "id"));
						index = path.lastIndexOf('/');
						if ((index != -1) && (index + 1 <= path.length())) {
							texturePath = path.substring(0, index + 1) + xpp.getAttributeValue(null, "file");
						} else {
							texturePath = xpp.getAttributeValue(null, "file");
						}
						texture = new Texture(context, texturePath);
						this.texturePages.put(pageId, texture);

					} else if (xpp.getName().equals("chars")) {
						int charCount = Integer.parseInt(xpp.getAttributeValue(null, "count"));
						this.characters = new SparseArray<FontChar>(charCount);

					} else if (xpp.getName().equals("info")) {
						this.face = xpp.getAttributeValue(null, "face");
						this.size = Math.abs(Integer.parseInt(xpp.getAttributeValue(null, "size")));
						this.bold = Integer.parseInt(xpp.getAttributeValue(null, "bold")) == 1;
						this.italic = Integer.parseInt(xpp.getAttributeValue(null, "italic")) == 1;
						this.charset = xpp.getAttributeValue(null, "charset");
						this.unicode = Integer.parseInt(xpp.getAttributeValue(null, "unicode")) == 1;
						this.stretchH = Integer.parseInt(xpp.getAttributeValue(null, "stretchH"));
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
						this.lineHeight = Integer.parseInt(xpp.getAttributeValue(null, "lineHeight"));
						this.baseFromTop = Integer.parseInt(xpp.getAttributeValue(null, "base"));
						this.scaleW = Integer.parseInt(xpp.getAttributeValue(null, "scaleW"));
						this.scaleH = Integer.parseInt(xpp.getAttributeValue(null, "scaleH"));
						this.packed = Integer.parseInt(xpp.getAttributeValue(null, "packed")) == 1;
						this.alphaChannel = Integer.parseInt(xpp.getAttributeValue(null, "alphaChnl"));
						this.redChannel = Integer.parseInt(xpp.getAttributeValue(null, "redChnl"));
						this.greenChannel = Integer.parseInt(xpp.getAttributeValue(null, "greenChnl"));
						this.blueChannel = Integer.parseInt(xpp.getAttributeValue(null, "blueChnl"));

						if (this.alphaChannel != BitmapFont.CHANNEL_HOLDS_GLYPH
								|| this.redChannel != BitmapFont.CHANNEL_HOLDS_GLYPH
								|| this.greenChannel != BitmapFont.CHANNEL_HOLDS_GLYPH
								|| this.blueChannel != BitmapFont.CHANNEL_HOLDS_GLYPH) {

							throw new IllegalArgumentException("" +
									"All channels must be set to glyph in BMFont in order to be compatible");
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
	public FontChar getCharacter(int id) {
		FontChar character = characters.get(id);
		if (character == null) {
			throw new UndefinedCharacterException();
		}
		return character;
	}

	@Override
	public SparseArray<Texture> getTexturePages() {
		return texturePages;
	}

	@Override
	public float measureLineWidth(String line, float fontSizePx) {
		FontChar lastChar = null;
		FontChar currentChar = null;
		int textLength = line.length();
		float scaleRatio = fontSizePx / getSize();
		float textWidth = 0.0f;

		for (int i = 0; i < textLength; i++) {
			currentChar = getCharacter((int) line.charAt(i));
			if (lastChar != null) {
				textWidth += lastChar.getKernings().get(currentChar.getId()) * scaleRatio;
			}
			textWidth += (currentChar.getxOffset() + currentChar.getxAdvance()) * scaleRatio;
			lastChar = currentChar;
		}
		return textWidth;
	}

	@Override
	public float measureLineHeight(float fontSizePx) {
		float scaleRatio = fontSizePx / getSize();
		return getLineHeight() * scaleRatio;
	}

	/**
	 * Returns the name of this font.
	 * 
	 * @return name of the font
	 */
	public String getFace() {
		return face;
	}

	/**
	 * Returns the size of this font.
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns true if this font is bold.
	 * 
	 * @return true if this font is bold, false otherwise.
	 */
	public boolean isBold() {
		return bold;
	}

	/**
	 * Returns true if this font is italic.
	 * 
	 * @return true if this font is italic, false otherwise.
	 */
	public boolean isItalic() {
		return italic;
	}

	/**
	 * Returns the charset of this font.
	 * 
	 * @return Charset of this font
	 */
	public String getCharset() {
		return charset;
	}

	/**
	 * Returns true if this font is unicode.
	 * 
	 * @return true if this font is unicode, false otherwise.
	 */
	public boolean isUnicode() {
		return unicode;
	}

	/**
	 * Returns the stretch percentage applied to this font's height. If this method returns 100, it means there is no
	 * stretch.
	 * 
	 * @return Stretch applied to this font's height
	 */
	public int getStretchH() {
		return stretchH;
	}

	/**
	 * Returns true if this font is smooth.
	 * 
	 * @return true if this font is smooth, false otherwise
	 */
	public boolean isSmooth() {
		return smooth;
	}

	/**
	 * Returns this font's antialiasing (supersampling value). If this method returns 1, there is no supersampling.
	 * 
	 * @return Supersampling level
	 */
	public int getAntialiasing() {
		return antialiasing;
	}

	/**
	 * Returns the left padding of the characters of this font.
	 * 
	 * @return left padding
	 */
	public int getPaddingLeft() {
		return paddingLeft;
	}

	/**
	 * Returns the right padding of the characters of this font.
	 * 
	 * @return right padding
	 */
	public int getPaddingRight() {
		return paddingRight;
	}

	/**
	 * Returns the top padding of the characters of this font.
	 * 
	 * @return top padding
	 */
	public int getPaddingTop() {
		return paddingTop;
	}

	/**
	 * Returns the bottom padding of the characters of this font.
	 * 
	 * @return bottom padding
	 */
	public int getPaddingBottom() {
		return paddingBottom;
	}

	/**
	 * Returns the horizontal spacing of the characters of this font.
	 * 
	 * @return horizontal spacing
	 */
	public int getSpacingHorizontal() {
		return spacingHorizontal;
	}

	/**
	 * Returns the vertical spacing of the characters of this font.
	 * 
	 * @return vertical spacing
	 */
	public int getSpacingVertical() {
		return spacingVertical;
	}

	/**
	 * Returns the width of the outline of the characters of this font.
	 * 
	 * @return width of the outline of the characters of this font
	 */
	public int getOutline() {
		return outline;
	}

	/**
	 * Returns the height in pixels of a line of text with this font.
	 * 
	 * @return line height
	 */
	public int getLineHeight() {
		return lineHeight;
	}

	/**
	 * Returns the distance in pixels from the top of the line to the base of the characters.
	 * 
	 * @return distance
	 */
	public int getBaseFromTop() {
		return baseFromTop;
	}

	/**
	 * Returns the scale of the width of this font's texture atlases. This value can be used to scale characters when
	 * rendering.
	 * 
	 * @return scale of the width of this font's texture atlases
	 */
	public int getScaleW() {
		return scaleW;
	}

	/**
	 * Returns the scale of the height of this font's texture atlases. This value can be used to scale characters when
	 * rendering.
	 * 
	 * @return scale of the height of this font's texture atlases
	 */
	public int getScaleH() {
		return scaleH;
	}

	/**
	 * Returns true if monochromatic colors has been defined using all color channels. In that case,
	 * {@link #getAlphaChannel()} describes what is stored in each channel.
	 * 
	 * @return true if monochromatic colors has been defined using all color channels. In that case,
	 *         {@link #getAlphaChannel()} describes what is stored in each channel, false otherwise
	 */
	public boolean isPacked() {
		return packed;
	}

	/**
	 * Returns the value of the alpha channel.<br>
	 * Supported values: {@link BitmapFont#CHANNEL_HOLDS_GLYPH}, {@link BitmapFont#CHANNEL_HOLDS_OUTLINE},
	 * {@link BitmapFont#CHANNEL_HOLDS_GLYPH_AND_OUTLINE}, {@link BitmapFont#CHANNEL_VALUE_SET_TO_ZERO},
	 * {@link BitmapFont#CHANNEL_VALUE_SET_TO_ONE}.
	 * 
	 * @return the value of the alpha channel
	 */
	public int getAlphaChannel() {
		return alphaChannel;
	}

	/**
	 * Returns the value of the red channel.<br>
	 * Supported values: {@link BitmapFont#CHANNEL_HOLDS_GLYPH}, {@link BitmapFont#CHANNEL_HOLDS_OUTLINE},
	 * {@link BitmapFont#CHANNEL_HOLDS_GLYPH_AND_OUTLINE}, {@link BitmapFont#CHANNEL_VALUE_SET_TO_ZERO},
	 * {@link BitmapFont#CHANNEL_VALUE_SET_TO_ONE}.
	 * 
	 * @return the value of the red channel
	 */
	public int getRedChannel() {
		return redChannel;
	}

	/**
	 * Returns the value of the green channel.<br>
	 * Supported values: {@link BitmapFont#CHANNEL_HOLDS_GLYPH}, {@link BitmapFont#CHANNEL_HOLDS_OUTLINE},
	 * {@link BitmapFont#CHANNEL_HOLDS_GLYPH_AND_OUTLINE}, {@link BitmapFont#CHANNEL_VALUE_SET_TO_ZERO},
	 * {@link BitmapFont#CHANNEL_VALUE_SET_TO_ONE}.
	 * 
	 * @return the value of the green channel
	 */
	public int getGreenChannel() {
		return greenChannel;
	}

	/**
	 * Returns the value of the blue channel.<br>
	 * Supported values: {@link BitmapFont#CHANNEL_HOLDS_GLYPH}, {@link BitmapFont#CHANNEL_HOLDS_OUTLINE},
	 * {@link BitmapFont#CHANNEL_HOLDS_GLYPH_AND_OUTLINE}, {@link BitmapFont#CHANNEL_VALUE_SET_TO_ZERO},
	 * {@link BitmapFont#CHANNEL_VALUE_SET_TO_ONE}.
	 * 
	 * @return the value of the blue channel
	 */
	public int getBlueChannel() {
		return blueChannel;
	}

	/**
	 * Returns the map of characters of this font (indexed by character ID).
	 * 
	 * @return map of characters of this font
	 */
	public SparseArray<FontChar> getCharacters() {
		return characters;
	}

}
