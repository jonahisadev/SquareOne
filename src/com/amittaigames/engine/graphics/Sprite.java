package com.amittaigames.engine.graphics;

import static org.lwjgl.opengl.GL15.*;

public class Sprite extends TexturedMesh {

	private SpriteSheet parent;
	
	private float x;
	private float y;
	private float width;
	private float height;
	
	private float angle;
	private float scale = 1f;
	
	public Sprite(float[] pos, float[] color, float[] coords, int[] list, int tex, SpriteSheet parent) {
		super("", pos, color, coords, list, false, tex);
		this.parent = parent;
		
		this.x = pos[0];
		this.y = pos[1];
		this.width = pos[2] - this.x;
		this.height = pos[5] - this.y;
	}

	/**
	 * Set the tile to use for the sprite
	 * @param sheet_x Tile X
	 * @param sheet_y Tile Y
	 */
	public void setImageLocation(int sheet_x, int sheet_y) {
		updateCoords(parent.getUVForSprite(sheet_x, sheet_y));
	}

	/**
	 * Translate the sprite
	 * @param x X direction
	 * @param y Y direction
	 */
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
		
		float[] pos = {
			this.x, this.y,
			this.x + this.width, this.y,
			this.x + this.width, this.y + this.height,
			this.x, this.y + this.height
		};
		
		updatePosition(pos);
	}

	/**
	 * Directly set position of sprite
	 * @param x X position
	 * @param y Y position
	 */
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;

		float[] pos = {
				this.x, this.y,
				this.x + this.width, this.y,
				this.x + this.width, this.y + this.height,
				this.x, this.y + this.height
		};

		updatePosition(pos);
	}

	/**
	 * Rotate the sprite
	 * @param angle Angle to offset current angle
	 */
	public void rotate(float angle) {
		this.angle += angle;
	}

	/**
	 * Scale sprite
	 * @param scale Scale
	 */
	public void scale(float scale) {
		this.scale += scale;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
}
