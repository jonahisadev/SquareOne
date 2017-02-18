package com.amittaigames.engine.graphics;

import com.amittaigames.engine.util.TextureUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class SpriteSheet {

	private String path;
	private int sprite_w;
	private int sprite_h;
	private int padding;
	private boolean external;
	
	private int texture;
	private int imgWidth;
	private int imgHeight;
	
	private List<Sprite> spriteList = new ArrayList<>();
	
	public SpriteSheet(String path, int sprite_w, int sprite_h, int padding, boolean external) {
		this.path = path;
		this.sprite_w = sprite_w;
		this.sprite_h = sprite_h;
		this.padding = padding;
		this.external = external;
		
		init();
	}
	
	private void init() {
		try {
			BufferedImage img;
			if (external)
				img = ImageIO.read(new BufferedInputStream(new FileInputStream(path)));
			else
				img = ImageIO.read(this.getClass().getResourceAsStream(path));


			this.imgWidth = img.getWidth();
			this.imgHeight = img.getHeight();

			int[] data = TextureUtil.loadImageForOpenGL(img);

			this.texture = glGenTextures();
			TextureUtil.setupOpenGLTexture(this.texture, data, img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int createSprite(float x, float y, float width, float height, int sheet_x, int sheet_y) {
		float[] pos = {
			x, y,
			x + width, y,
			x + width, y + height,
			x, y + height
		};
		
		float[] color = {
			1, 1, 1,
			1, 1, 1,
			1, 1, 1,
			1, 1, 1
		};
		
		float[] coords = getUVForSprite(sheet_x, sheet_y);
		
		int[] list = {
			0, 1, 2,
			0, 3, 2
		};
		
		Sprite sprite = new Sprite(pos, color, coords, list, this.texture, this);
		int listi = spriteList.size();
		spriteList.add(sprite);
		
		return listi;
	}
	
	public float[] getUVForSprite(int sheet_x, int sheet_y) {
		float img_x = (float)((sheet_x * this.sprite_w) + (sheet_x * this.padding)) / (float)imgWidth;
		float img_y = (float)((sheet_y * this.sprite_h) + (sheet_y * this.padding)) / (float)imgHeight;
		float img_w = (float)(this.sprite_w) / (float)imgWidth;
		float img_h = (float)(this.sprite_h) / (float)imgHeight;

		float[] coords = {
				img_x, img_y,
				img_x + img_w, img_y,
				img_x + img_w, img_y + img_h,
				img_x, img_y + img_h
		};
		
		return coords;
	}
	
	public Sprite getSpriteByIndex(int index) {
		return spriteList.get(index);
	}

	public int getSpriteWidth() {
		return sprite_w;
	}

	public int getSpriteHeight() {
		return sprite_h;
	}

	public int getPadding() {
		return padding;
	}

	public int getTexture() {
		return texture;
	}

	public List<Sprite> getSpriteList() {
		return spriteList;
	}
	
}
