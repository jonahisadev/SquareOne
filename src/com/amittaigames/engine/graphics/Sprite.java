package com.amittaigames.engine.graphics;

import static org.lwjgl.opengl.GL15.*;

public class Sprite extends TexturedMesh {

	private SpriteSheet parent;
	
	private float x;
	private float y;
	private float width;
	private float height;
	
	public Sprite(float[] pos, float[] color, float[] coords, int[] list, int tex, SpriteSheet parent) {
		super("", pos, color, coords, list, false, tex);
		this.parent = parent;
		
		this.x = pos[0];
		this.y = pos[1];
		this.width = pos[2] - this.x;
		this.height = pos[5] - this.y;
	}
	
	public void setImageLocation(int sheet_x, int sheet_y) {
		updateCoords(parent.getUVForSprite(sheet_x, sheet_y));
	}
	
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

}
