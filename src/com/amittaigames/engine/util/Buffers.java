package com.amittaigames.engine.util;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffers {

	/**
	 * Creates a FloatBuffer for use by OpenGL
	 * @param data The float array to be converted
	 * @return FloatBuffer for OpenGL
	 */
	public static FloatBuffer createFloatBuffer(float[] data) {
		FloatBuffer buf = BufferUtils.createFloatBuffer(data.length);
		buf.put(data).flip();
		return buf;
	}

	/**
	 * Creates an IntBuffer for use by OpenGL
	 * @param data The int array to be converted
	 * @return IntBuffer for OpenGL
	 */
	public static IntBuffer createIntBuffer(int[] data) {
		IntBuffer buf = BufferUtils.createIntBuffer(data.length);
		buf.put(data).flip();
		return buf;
	}

	/**
	 * Creates an ByteBuffer for use by OpenGL
	 * @param data The byte array to be converted
	 * @return ByteBuffer for OpenGL
	 */
	public static ByteBuffer createByteBuffer(byte[] data) {
		ByteBuffer buf = BufferUtils.createByteBuffer(data.length);
		buf.put(data).flip();
		return buf;
	}

}
