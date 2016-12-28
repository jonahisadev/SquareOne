package com.amittaigames.engine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;

public class Mesh {

	private float[] pos;
	private float[] color;
	private int[] list;
	
	private int vCount;
	
	private int vPos;
	private int vColor;
	private int vList;
	
	public Mesh(float[] pos, float[] color, int[] list) {
		this.pos = pos;
		this.color = color;
		this.list = list;
		this.vCount = list.length;
		init();
	}
	
	private void init() {
		vPos = glGenBuffers();
		updatePosition(this.pos);
		
		vColor = glGenBuffers();
		updateColor(this.color);
		
		vList = glGenBuffers();
		IntBuffer listBuf = Buffers.createIntBuffer(list);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vList);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, listBuf, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void updatePosition(float[] data) {
		FloatBuffer posBuf = Buffers.createFloatBuffer(data);
		glBindBuffer(GL_ARRAY_BUFFER, vPos);
		glBufferData(GL_ARRAY_BUFFER, posBuf, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void updateColor(float[] color) {
		FloatBuffer colorBuf = Buffers.createFloatBuffer(color);
		glBindBuffer(GL_ARRAY_BUFFER, vColor);
		glBufferData(GL_ARRAY_BUFFER, colorBuf, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void destroy() {
		glDeleteBuffers(vPos);
		glDeleteBuffers(vColor);
		glDeleteBuffers(vList);
	}

	public int getPos() {
		return vPos;
	}

	public int getColor() {
		return vColor;
	}

	public int getList() {
		return vList;
	}

	public int getCount() {
		return vCount;
	}
}