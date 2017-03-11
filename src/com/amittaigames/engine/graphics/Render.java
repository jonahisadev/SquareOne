package com.amittaigames.engine.graphics;

import com.amittaigames.engine.util.Buffers;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Render {
	
	/**
	 * Clear the color buffer
	 * @param r Red component
	 * @param g Green component
	 * @param b Blue component
	 */
	public void clear(int r, int g, int b) {
		glClearColor((float)r/255.0f, (float)g/255.0f, (float)b/255.0f, 1);
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	/**
	 * Set line render mode
	 * @param lines Should render draw lines or fill?
	 */
	public void setLineMode(boolean lines) {
		if (lines)
			glPolygonMode(GL_FRONT, GL_LINE);
		else
			glPolygonMode(GL_FRONT, GL_FILL);
	}

	/**
	 * Wrapper for mesh drawing
	 * @param rect Rect object
	 * @see Rect   
	 */
	public void drawRect(Rect rect) {
		glPushMatrix();

		glScalef(rect.getScale(), rect.getScale(), 1);
		
		glTranslatef(rect.getX() + (rect.getWidth() / 2), rect.getY() + (rect.getHeight() / 2), 0);
		glRotatef(rect.getAngle(), 0, 0, 1);
		glTranslatef(-(rect.getX() + (rect.getWidth() / 2)), -(rect.getY() + (rect.getHeight() / 2)), 0);
		
		drawMesh(rect.getMesh());
		
		glPopMatrix();
	}

	/**
	 * Wrapper for textured mesh drawing
	 * @param rect TexturedRect object
	 * @see TexturedRect    
	 */
	public void drawTexturedRect(TexturedRect rect) {
		glPushMatrix();

		glScalef(rect.getScale(), rect.getScale(), 1);
		
		glTranslatef(rect.getX() + (rect.getWidth() / 2), rect.getY() + (rect.getHeight() / 2), 0);
		glRotatef(rect.getAngle(), 0, 0, 1);
		glTranslatef(-(rect.getX() + (rect.getWidth() / 2)), -(rect.getY() + (rect.getHeight() / 2)), 0);
		
		drawTexturedMesh(rect.getMesh());
		
		glPopMatrix();
	}

	/**
	 * Draws a TexturedMesh
	 * @param mesh TexturedMesh object
	 * @see TexturedMesh
	 */
	public void drawTexturedMesh(TexturedMesh mesh) {
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		glBindTexture(GL_TEXTURE_2D, mesh.getTexture());
		
		glBindBuffer(GL_ARRAY_BUFFER, mesh.getPos());
		glVertexPointer(2, GL_FLOAT, 0, 0);
		glEnableClientState(GL_VERTEX_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, mesh.getColor());
		glColorPointer(3, GL_FLOAT, 0, 0);
		glEnableClientState(GL_COLOR_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, mesh.getCoords());
		glTexCoordPointer(2, GL_FLOAT, 0, 0);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getList());
		glDrawElements(GL_TRIANGLES, mesh.getCount(), GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	}

	/**
	 * Draws a Mesh
	 * @param mesh Mesh object
	 * @see Mesh   
	 */
	public void drawMesh(Mesh mesh) {
		glBindBuffer(GL_ARRAY_BUFFER, mesh.getPos());
		glVertexPointer(2, GL_FLOAT, 0, 0);
		glEnableClientState(GL_VERTEX_ARRAY);
		
		glBindBuffer(GL_ARRAY_BUFFER, mesh.getColor());
		glColorPointer(3, GL_FLOAT, 0, 0);
		glEnableClientState(GL_COLOR_ARRAY);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getList());
		glDrawElements(GL_TRIANGLES, mesh.getCount(), GL_UNSIGNED_INT, 0);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
	}

	/**
	 * Draws sprite sheet
	 * @param ss Sprite sheet
	 */
	public void drawSprites(SpriteSheet ss) {
		for (Sprite sprite : ss.getSpriteList()) {
			glPushMatrix();

			glScalef(sprite.getScale(), sprite.getScale(), 1);
			
			glTranslatef(sprite.getX() + (sprite.getWidth() / 2), sprite.getY() + (sprite.getHeight() / 2), 0);
			glRotatef(sprite.getAngle(), 0, 0, 1);
			glTranslatef(-(sprite.getX() + (sprite.getWidth() / 2)), -(sprite.getY() + (sprite.getHeight() / 2)), 0);
			
			drawTexturedMesh(sprite);
			
			glPopMatrix();
		}
	}

	/**
	 * Draw a string (this is so cool btw)
	 * @param str String to render
	 * @param x X component (leftmost)
	 * @param y Y component (topmost)
	 * @param f Font to render with
	 * @return Width of string written on screen   
	 */
	public float drawText(String str, float x, float y, Font f) {
		float cursor = x;
		
		glEnable(GL_TEXTURE_2D);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		glBindTexture(GL_TEXTURE_2D, f.getTexture());
		
		int vUV = 0;
		int vPos = 0;
		int vList = 0;
		
		char[] buf = str.toCharArray();
		for (int i = 0; i < buf.length; i++) {
			Font.FontData fdata = f.getDataForChar(buf[i]);
			
			vUV = glGenBuffers();
			vPos = glGenBuffers();
			vList = glGenBuffers();
			
			float[] uv = {
				fdata.x, fdata.y,
				fdata.x + fdata.width, fdata.y,
				fdata.x + fdata.width, fdata.y + fdata.height,
				fdata.x, fdata.y + fdata.height
			};
			FloatBuffer bUV = Buffers.createFloatBuffer(uv);
			glBindBuffer(GL_ARRAY_BUFFER, vUV);
			glBufferData(GL_ARRAY_BUFFER, bUV, GL_STATIC_DRAW);
			glTexCoordPointer(2, GL_FLOAT, 0, 0);
			glEnableClientState(GL_TEXTURE_COORD_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			float[] pos = {
				cursor + fdata.xoff, y + fdata.yoff,
				cursor + fdata.xoff + (fdata.width * f.getImageWidth() * f.getScale()), y + fdata.yoff,
				cursor + fdata.xoff + (fdata.width * f.getImageWidth() * f.getScale()), y + (fdata.height * f.getImageWidth() * f.getScale()) + fdata.yoff,
				cursor + fdata.xoff, y + (fdata.height * f.getImageWidth() * f.getScale()) + fdata.yoff
			};
			FloatBuffer bPos = Buffers.createFloatBuffer(pos);
			glBindBuffer(GL_ARRAY_BUFFER, vPos);
			glBufferData(GL_ARRAY_BUFFER, bPos, GL_STATIC_DRAW);
			glVertexPointer(2, GL_FLOAT, 0, 0);
			glEnableClientState(GL_VERTEX_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, 0);

			glBindBuffer(GL_ARRAY_BUFFER, f.getColor());
			glColorPointer(3, GL_FLOAT, 0, 0);
			glEnableClientState(GL_COLOR_ARRAY);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			int[] list = {
				0, 1, 2,
				0, 3, 2
			};
			IntBuffer bList = Buffers.createIntBuffer(list);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vList);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, bList, GL_STATIC_DRAW);
			
			glDrawElements(GL_TRIANGLES, list.length, GL_UNSIGNED_INT, 0);
			
			glDeleteBuffers(vUV);
			glDeleteBuffers(vPos);
			glDeleteBuffers(vList);
			
			cursor += fdata.xadv;
		}

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDisableClientState(GL_VERTEX_ARRAY);
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		
		return (x + cursor);
	}
}
