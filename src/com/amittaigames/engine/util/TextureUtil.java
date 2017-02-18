package com.amittaigames.engine.util;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;

public class TextureUtil {

	public static int[] loadImageForOpenGL(BufferedImage img) {
		int[] pixels = new int[img.getWidth() * img.getHeight()];
		img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());

		int[] data = new int[img.getWidth() * img.getHeight()];
		for (int i = 0; i < img.getWidth() * img.getHeight(); i++) {
			int a = (pixels[i] & 0xFF000000) >> 24;
			int r = (pixels[i] & 0xFF0000) >> 16;
			int g = (pixels[i] & 0xFF00) >> 8;
			int b = (pixels[i] & 0xFF) >> 0;

			data[i] = a << 24 | b << 16 | g << 8 | r;
		}
		
		return data;
	}
	
	public static void setupOpenGLTexture(int tex, int[] data, BufferedImage img) {
		glBindTexture(GL_TEXTURE_2D, tex);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, img.getWidth(), img.getHeight(), 0,
				GL_RGBA, GL_UNSIGNED_BYTE, Buffers.createIntBuffer(data));
		glBindTexture(GL_TEXTURE_2D, 0);
	}

}