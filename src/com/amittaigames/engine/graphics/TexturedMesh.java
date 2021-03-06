package com.amittaigames.engine.graphics;

import com.amittaigames.engine.util.Buffers;
import com.amittaigames.engine.util.TextureUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class TexturedMesh extends Renderable {

	private float[] pos;
	private float[] color;
	private float[] coords;
	private int[] list;
	
	private int vPos;
	private int vColor;
	private int vCoords;
	private int vTexture;
	private int vList;
	private int vCount;
	
	private int imgWidth;
	private int imgHeight;
	
	private boolean external;

	/**
	 * @param path Path to texture
	 * @param pos Position vertices
	 * @param color Color vertices
	 * @param coords UV coordinate vertices
	 * @param list Element array vertices
	 * @param external Is the texture external to the JAR file or not?
	 */
	public TexturedMesh(String path, float[] pos, float[] color, float[] coords, int[] list, boolean external) {
		this.pos = pos;
		this.color = color;
		this.coords = coords;
		this.list = list;
		this.external = external;
		this.vCount = list.length;
		
		init(path, 0);
	}

	/**
	 * @param path Path to texture
	 * @param pos Position vertices
	 * @param color Color vertices
	 * @param coords UV coordinate vertices
	 * @param list Element array vertices
	 * @param external Is the texture external to the JAR file or not?
	 */
	public TexturedMesh(String path, float[] pos, float[] color, float[] coords, int[] list, boolean external, int tex) {
		this.pos = pos;
		this.color = color;
		this.coords = coords;
		this.list = list;
		this.external = external;
		this.vCount = list.length;
		this.vTexture = tex;

		init(path, tex);
	}

	/**
	 * Initialize a TexturedMesh with given information
	 * @param path Path to texture
	 */
	private void init(String path, int tex) {
		vPos = glGenBuffers();
		updatePosition(pos);
		
		vColor = glGenBuffers();
		updateColor(color);
		
		vCoords = glGenBuffers();
		updateCoords(coords);
		
		vList = glGenBuffers();
		IntBuffer listBuf = Buffers.createIntBuffer(list);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vList);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, listBuf, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		if (tex == 0) {
			try {
				BufferedImage img;
				if (external)
					img = ImageIO.read(new BufferedInputStream(new FileInputStream(path)));
				else
					img = ImageIO.read(this.getClass().getResourceAsStream(path));


				this.imgWidth = img.getWidth();
				this.imgHeight = img.getHeight();
				
				int[] data = TextureUtil.loadImageForOpenGL(img);

				vTexture = glGenTextures();
				TextureUtil.setupOpenGLTexture(vTexture, data, img);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Update position data
	 * @param data Position data
	 */
	public void updatePosition(float[] data) {
		FloatBuffer buf = Buffers.createFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vPos);
		glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Update color data
	 * @param data Color data
	 */
	public void updateColor(float[] data) {
		FloatBuffer buf = Buffers.createFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vColor);
		glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Update UV mapping data
	 * @param data UV mapping data
	 */
	public void updateCoords(float[] data) {
		FloatBuffer buf = Buffers.createFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vCoords);
		glBufferData(GL_ARRAY_BUFFER, buf, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	/**
	 * Delete OpenGL buffers
	 */
	public void delete() {
		glDeleteBuffers(vPos);
		glDeleteBuffers(vColor);
		glDeleteBuffers(vCoords);
		glDeleteBuffers(vList);
		glDeleteTextures(vTexture);
	}

	public int getPos() {
		return vPos;
	}

	public int getColor() {
		return vColor;
	}

	public int getCoords() {
		return vCoords;
	}

	public int getTexture() {
		return vTexture;
	}

	public int getList() {
		return vList;
	}

	public int getCount() {
		return vCount;
	}
	
	public int getImageWidth() {
		return imgWidth;
	}
	
	public int getImageHeight() {
		return imgHeight;
	}
	
}
