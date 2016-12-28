package com.amittaigames.engine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Font {
	
	//
	//	FONT DATA
	//
	
	static class FontData {
		
		public float x;
		public float y;
		public float width;
		public float height;
		public float xoff;
		public float yoff;
		public float xadv;
		
		public FontData(float x, float y, float width, float height,
						float xoff, float yoff, float xadv) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.xoff = xoff;
			this.yoff = yoff;
			this.xadv = xadv;
		}
		
	}
	
	//
	//	FONT
	//
	
	private FontData[] data;
	private int id;
	private int imgw;
	private float scale;
	private float base;

	private int vColor;
	private int vTexture;
	
	private static Map<String, Font> loaded = new HashMap<>();
	
	public Font(FontData[] data, int vTexture, int id, int imgw, float scale, float base) {
		this.data = data;
		this.vTexture = vTexture;
		this.id = id;
		this.imgw = imgw;
		this.scale = scale;
		this.base = base;
	}

	/**
	 * Load an internal font from a path
	 * @param path Path to font and image sans file extension
	 * @param id A specified ID number to use when getting the font
	 * @param scale A scale at which to render the font
	 */
	public static void load(String path, int id, float scale) {
		String fnt = Util.readInternalFile(path + ".fnt");
		String[] fntLines = fnt.split("\n");
		FontData[] fdata = new FontData[128];
		int vTexture = 0;

		int imgw = 0;
		try {
			BufferedImage img = ImageIO.read(Font.class.getResourceAsStream(path + ".png"));
			imgw = img.getWidth();

			int[] pixels = new int[img.getWidth() * img.getHeight()];
			img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

			int[] data = new int[img.getWidth() * img.getHeight()];
			for (int i = 0; i < img.getWidth() * img.getHeight(); i++) {
				int a = (pixels[i] & 0xFF000000) >> 24;
				int r = (pixels[i] & 0xFF0000) >> 16;
				int g = (pixels[i] & 0xFF00) >> 8;
				int b = (pixels[i] & 0xFF);

				data[i] = a << 24 | b << 16 | g << 8 | r;
			}

			vTexture = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, vTexture);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, img.getWidth(), img.getHeight(), 0,
					GL_RGBA, GL_UNSIGNED_BYTE, Buffers.createIntBuffer(data));
			glBindTexture(GL_TEXTURE_2D, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int base = 0;
		for (int i = 0; i < fntLines.length; i++) {
			if (fntLines[i].contains("base=")) {
				String[] args = fntLines[i].split(" ");
				
				baseLoop:
				for (int j = 0; j < args.length; j++) {
					if (args[j].startsWith("base=")) {
						base = Integer.parseInt(args[j].split("=")[1]);
						break baseLoop;
					}
				}
			}
			if (fntLines[i].startsWith("char ")) {
				parseCharLine(fntLines[i], fdata, imgw, scale);
			}
		}
		base *= scale;
		
		String[] temp = path.split("/");
		String name = temp[temp.length - 1];
		if (vTexture != 0) {
			Font font = new Font(fdata, vTexture, id, imgw, scale, base);
			font.vColor = glGenBuffers();
			font.setColor(255, 255, 255);
			loaded.put(name + " " + id, font);
		} else {
			System.err.println("Could not load font " + name + " " + id);
		}
	}

	/**
	 * Sets the font color
	 * @param r Red component
	 * @param g Green component
	 * @param b Blue component
	 */
	public void setColor(int r, int g, int b) {
		float[] color = new float[12];
		for (int i = 0; i < 12; i += 3) {
			color[i] = (float)r/255.0f;
			color[i + 1] = (float)g/255.0f;
			color[i + 2] = (float)b/255.0f;
		}
		FloatBuffer buf = Buffers.createFloatBuffer(color);
		glBindBuffer(GL_ARRAY_BUFFER, vColor);
		glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Gets character data
	 * @param c Character
	 * @return Data for specified character
	 */
	public FontData getDataForChar(char c) {
		return this.data[c];
	}

	/**
	 * Return loaded font
	 * @param name "Name ID"
	 * @return Loaded font
	 */
	public static Font get(String name) {
		return loaded.get(name);
	}

	/**
	 * Parses character line in *.fnt file
	 * @param line Line of text to parse
	 * @param data Font data array to modify
	 * @param imgw Image width (and height)
	 * @param scale Scale at which to render the font
	 */
	private static void parseCharLine(String line, FontData[] data, int imgw, float scale) {
		int index_id = 			line.indexOf("id");
		int index_x = 			line.indexOf("x");
		int index_y = 			line.indexOf("y");
		int index_width = 		line.indexOf("width");
		int index_height = 		line.indexOf("height");
		int index_xoff = 		line.indexOf("xoffset");
		int index_yoff = 		line.indexOf("yoffset");
		int index_xadv = 		line.indexOf("xadvance");
		
		String parse_id = 		line.substring(index_id + 3, index_x - 1).trim();
		String parse_x = 		line.substring(index_x + 2, index_y - 1).trim();
		String parse_y = 		line.substring(index_y + 2, index_width - 1).trim();
		String parse_width =	line.substring(index_width + 6, index_height - 1).trim();
		String parse_height = 	line.substring(index_height + 7, index_xoff - 1).trim();
		String parse_xoff = 	line.substring(index_xoff + 8, index_yoff - 1).trim();
		String parse_yoff = 	line.substring(index_yoff + 8, index_xadv - 1).trim();
		String parse_xadv = 	line.substring(index_xadv + 9, line.indexOf("page") - 1).trim();
		
		int id = Integer.parseInt(parse_id);
		int x = Integer.parseInt(parse_x);
		int y = Integer.parseInt(parse_y);
		int width = Integer.parseInt(parse_width);
		int height = Integer.parseInt(parse_height);
		float xoff = Integer.parseInt(parse_xoff) * scale;
		float yoff = Integer.parseInt(parse_yoff) * scale;
		float xadv = Integer.parseInt(parse_xadv) * scale;
		
		data[id] = new FontData((float)x/imgw, (float)y/imgw, 
				(float)width/imgw, (float)height/imgw, xoff, yoff, xadv);
	}

	/**
	 * Return texture VBO
	 * @return texture VBO
	 */
	public int getTexture() {
		return vTexture;
	}

	/**
	 * Return color VBO
	 * @return color VBO
	 */
	public int getColor() {
		return vColor;
	}

	/**
	 * Return image size
	 * @return image size
	 */
	public int getImageWidth() {
		return imgw;
	}


	/**
	 * Return scale at which to render the font
	 * @return scale at which to render the font
	 */
	public float getScale() {
		return scale;
	}

	/**
	 * Return the line base of the font
	 * @return line base of the font
	 */
	public float getBase() {
		return base;
	}

}