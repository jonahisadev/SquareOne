package com.amittaigames.engine.graphics;

public class Rect {

	private float x;
	private float y;
	private float width;
	private float height;
	private Mesh mesh;
	
	public Rect(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init();
	}

	/**
	 * Initialize a Rectangle with given information
	 */
	private void init() {
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
		
		int[] list = {
			0, 1, 2,
			0, 3, 2
		};
		
		this.mesh = new Mesh(pos, color, list);
	}

	/**
	 * Translate the position
	 * @param x X component
	 * @param y Y component
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
		
		mesh.updatePosition(pos);
	}

	/**
	 * Set the color
	 * @param r Red component
	 * @param g Green component
	 * @param b Blue component
	 */
	public void setColor(int r, int g, int b) {
		float rr = (float)r/255.0f;
		float gg = (float)g/255.0f;
		float bb = (float)b/255.0f;
		
		float[] color = new float[12];
		for (int i = 0; i < 12; i += 3) {
			color[i] = rr;
			color[i + 1] = gg;
			color[i + 2] = bb;
		}
		
		mesh.updateColor(color);
	}

	/**
	 * Call mesh deletion
	 */
	public void delete() {
		this.mesh.delete();
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

	public Mesh getMesh() {
		return mesh;
	}
}