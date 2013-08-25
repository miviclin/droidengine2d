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
	 * Crea un {@link BitmapFont}.<br>
	 * Es necesario inicializarlo manualmente mediante el metodo {@link #loadFromXML(String, Context)}
	 */
	public BitmapFont() {
		super();
	}
	
	@Override
	public void loadFromXML(String path, Context context) {
		XmlPullParserFactory factory;
		XmlPullParser xpp;
		int eventType, index, pageId, kerningFirst, kerningSecond, kerningAmount;
		int charId, charX, charY, charWidth, charHeight, charXOffset, charYOffset, charXAdvance, charPage, charChannel;
		TextureRegion textureRegion;
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
						charWidth = Integer.parseInt(xpp.getAttributeValue(null, "width"));
						charHeight = Integer.parseInt(xpp.getAttributeValue(null, "height"));
						charXOffset = Integer.parseInt(xpp.getAttributeValue(null, "xoffset"));
						charYOffset = Integer.parseInt(xpp.getAttributeValue(null, "yoffset"));
						charXAdvance = Integer.parseInt(xpp.getAttributeValue(null, "xadvance"));
						charPage = Integer.parseInt(xpp.getAttributeValue(null, "page"));
						charChannel = Integer.parseInt(xpp.getAttributeValue(null, "chnl"));
						textureRegion = new TextureRegion(this.texturePages.get(charPage), charX, charY, charWidth, charHeight);
						fontChar = new FontChar(charId, textureRegion, charXOffset, charYOffset, charXAdvance, charChannel);
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
						
						if (this.alphaChannel != BitmapFont.CHANNEL_HOLDS_GLYPH || this.redChannel != BitmapFont.CHANNEL_HOLDS_GLYPH ||
								this.greenChannel != BitmapFont.CHANNEL_HOLDS_GLYPH || this.blueChannel != BitmapFont.CHANNEL_HOLDS_GLYPH) {
							
							throw new IllegalArgumentException("All channels must be set to glyph in BMFont in order to be compatible");
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
	public void clear() {
		characters.clear();
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
				textWidth += lastChar.getKernings().get(currentChar.getID()) * scaleRatio;
			}
			textWidth += (currentChar.getxOffset() + currentChar.getxAdvance()) * scaleRatio;
			lastChar = currentChar;
		}
		return textWidth;
	}
	
	/**
	 * Devuelve el nombre de la fuente
	 * 
	 * @return Nombre de la fuente
	 */
	public String getFace() {
		return face;
	}
	
	/**
	 * Devuelve el size de la fuente
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * Devuelve true si la fuente es negrita, false en caso contrario
	 * 
	 * @return true si la fuente es negrita, false en caso contrario
	 */
	public boolean isBold() {
		return bold;
	}
	
	/**
	 * Devuelve true si la fuente es cursiva, false en caso contrario
	 * 
	 * @return true si la fuente es cursiva, false en caso contrario
	 */
	public boolean isItalic() {
		return italic;
	}
	
	/**
	 * Devuelve el charset de la fuente
	 * 
	 * @return Charset
	 */
	public String getCharset() {
		return charset;
	}
	
	/**
	 * Devuelve true si la fuente es unicode, false en caso contrario
	 * 
	 * @return true si la fuente es unicode, false en caso contrario
	 */
	public boolean isUnicode() {
		return unicode;
	}
	
	/**
	 * El stretch aplicado a la altura de la fuente, en porcentaje. 100 significa que no hay stretch.
	 * 
	 * @return Porcentaje de stretch en el eje Y
	 */
	public int getStretchH() {
		return stretchH;
	}
	
	/**
	 * Devuelve true si la fuente esta suavizada, false en caso contrario
	 * 
	 * @return true si la fuente esta suavizada, false en caso contrario
	 */
	public boolean isSmooth() {
		return smooth;
	}
	
	/**
	 * Devuelve el antialiasing de la fuente (valor de supersampling). 1 significa que no se ha usado supersampling.
	 * 
	 * @return Nivel de supersampling
	 */
	public int getAntialiasing() {
		return antialiasing;
	}
	
	/**
	 * Devuelve el padding izquierdo de los caracteres de la fuente
	 * 
	 * @return Padding izquierdo
	 */
	public int getPaddingLeft() {
		return paddingLeft;
	}
	
	/**
	 * Devuelve el padding derecho de los caracteres de la fuente
	 * 
	 * @return Padding derecho
	 */
	public int getPaddingRight() {
		return paddingRight;
	}
	
	/**
	 * Devuelve el padding por arriba de los caracteres de la fuente
	 * 
	 * @return Padding por arriba
	 */
	public int getPaddingTop() {
		return paddingTop;
	}
	
	/**
	 * Devuelve el padding por abajo de los caracteres de la fuente
	 * 
	 * @return Padding por abajo
	 */
	public int getPaddingBottom() {
		return paddingBottom;
	}
	
	/**
	 * Devuelve el espaciado horizontal de los caracteres de la fuente
	 * 
	 * @return Espaciado horizontal
	 */
	public int getSpacingHorizontal() {
		return spacingHorizontal;
	}
	
	/**
	 * Devuelve el espaciado vertical de los caracteres de la fuente
	 * 
	 * @return Espaciado vertical
	 */
	public int getSpacingVertical() {
		return spacingVertical;
	}
	
	/**
	 * Devuelve el grosor del contorno de los caracteres de la fuente
	 * 
	 * @return Grosor del contorno de los caracteres de la fuente
	 */
	public int getOutline() {
		return outline;
	}
	
	/**
	 * Devuelve la distancia en pixels entre cada linea de texto
	 * 
	 * @return Alto de linea
	 */
	public int getLineHeight() {
		return lineHeight;
	}
	
	/**
	 * Devuelve el numero de pixels desde el alto absoluto de la linea a la base de los caracteres
	 * 
	 * @return Distancia de la base de los caracteres al alto de linea
	 */
	public int getBaseFromTop() {
		return baseFromTop;
	}
	
	/**
	 * Devuelve el ancho de las texturas (atlas) de la fuente. Utilizado normalmente para escalar los caracteres
	 * 
	 * @return Ancho de las texturas
	 */
	public int getScaleW() {
		return scaleW;
	}
	
	/**
	 * Devuelve el alto de las texturas (atlas) de la fuente. Utilizado normalmente para escalar los caracteres
	 * 
	 * @return Alto de las texturas
	 */
	public int getScaleH() {
		return scaleH;
	}
	
	/**
	 * Devuelve true si los caracteres monocromaticos han sido definidos en cada uno de los canales de color, en ese caso
	 * {@link #getAlphaChannel()} describe lo que se almacena en cada canal, false en caso contrario
	 * 
	 * @return true si los caracteres monocromaticos han sido definidos en cada uno de los canales de color, en ese caso
	 *         {@link #getAlphaChannel()} describe lo que se almacena en cada canal, false en caso contrario
	 */
	public boolean isPacked() {
		return packed;
	}
	
	/**
	 * Devuelve el valor del canal alpha.
	 * 
	 * @return Posibles valores: {@link BitmapFont#CHANNEL_HOLDS_GLYPH}, {@link BitmapFont#CHANNEL_HOLDS_OUTLINE},
	 *         {@link BitmapFont#CHANNEL_HOLDS_GLYPH_AND_OUTLINE}, {@link BitmapFont#CHANNEL_VALUE_SET_TO_ZERO},
	 *         {@link BitmapFont#CHANNEL_VALUE_SET_TO_ONE}
	 */
	public int getAlphaChannel() {
		return alphaChannel;
	}
	
	/**
	 * Devuelve el valor del canal R.
	 * 
	 * @return Posibles valores: {@link BitmapFont#CHANNEL_HOLDS_GLYPH}, {@link BitmapFont#CHANNEL_HOLDS_OUTLINE},
	 *         {@link BitmapFont#CHANNEL_HOLDS_GLYPH_AND_OUTLINE}, {@link BitmapFont#CHANNEL_VALUE_SET_TO_ZERO},
	 *         {@link BitmapFont#CHANNEL_VALUE_SET_TO_ONE}
	 */
	public int getRedChannel() {
		return redChannel;
	}
	
	/**
	 * Devuelve el valor del canal G.
	 * 
	 * @return Posibles valores: {@link BitmapFont#CHANNEL_HOLDS_GLYPH}, {@link BitmapFont#CHANNEL_HOLDS_OUTLINE},
	 *         {@link BitmapFont#CHANNEL_HOLDS_GLYPH_AND_OUTLINE}, {@link BitmapFont#CHANNEL_VALUE_SET_TO_ZERO},
	 *         {@link BitmapFont#CHANNEL_VALUE_SET_TO_ONE}
	 */
	public int getGreenChannel() {
		return greenChannel;
	}
	
	/**
	 * Devuelve el valor del canal B.
	 * 
	 * @return Posibles valores: {@link BitmapFont#CHANNEL_HOLDS_GLYPH}, {@link BitmapFont#CHANNEL_HOLDS_OUTLINE},
	 *         {@link BitmapFont#CHANNEL_HOLDS_GLYPH_AND_OUTLINE}, {@link BitmapFont#CHANNEL_VALUE_SET_TO_ZERO},
	 *         {@link BitmapFont#CHANNEL_VALUE_SET_TO_ONE}
	 */
	public int getBlueChannel() {
		return blueChannel;
	}
	
	/**
	 * Devuelve el mapa de caracteres de esta fuente, indexados por ID
	 * 
	 * @return Mapa de caracteres
	 */
	public SparseArray<FontChar> getCharacters() {
		return characters;
	}
	
}
