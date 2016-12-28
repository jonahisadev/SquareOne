package com.amittaigames.engine;

public class TexturedRect {

	private float x;
	private float y;
	private float width;
	private float height;
	private TexturedMesh mesh;
	
	public TexturedRect(float x, float y, float width, float height, String path, boolean external) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		init(path, external);
	}

	/**
	 * Initialize a TexturedRect with given information
	 * @param path Path to texture
	 * @param external Internal or external image
	 */
	private void init(String path, boolean external) {
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
		
		float[] coords = {
			0, 0,
			1, 0,
			1, 1,
			0, 1
		};
		
		int[] list = {
			0, 1, 2,
			0, 3, 2
		};
		
		mesh = new TexturedMesh(path, pos, color, coords, list, external);
	}

	/**
	 * Translate position of the texture
	 * @param x X component
	 * @param y Y component
	 */
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
		
		float[] pos = {
			this.x, this.y,
			this.x + this.width, this.y,
			this.x + this.width, this.y + this.y,
			this.x, this.y + this.height
		};
		
		mesh.updatePosition(pos);
	}

	/**
	 * Set the color of the texture
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

	public TexturedMesh getMesh() {
		return mesh;
	}
}