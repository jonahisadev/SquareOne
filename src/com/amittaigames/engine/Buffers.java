package com.amittaigames.engine;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffers {

	public static FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
		buf.put(data).flip();
		return buf;
	}
	
	public static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buf = BufferUtils.createIntBuffer(data.length);
		buf.put(data).flip();
		return buf;
	}

}
