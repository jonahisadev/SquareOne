package com.amittaigames.engine.audio;

import com.amittaigames.engine.util.Buffers;

import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

public class WaveData {

	private int channels;
	private int samples;
	private int format;
	private int sampleRate;
	private int size;
	
	private ByteBuffer data;
	
	/**
	 * @param fileData Raw bytes from WAV file
	 */
	public WaveData(byte[] fileData) {
		if (fileData[0] != (byte)'R' ||
				fileData[1] != (byte)'I' ||
				fileData[2] != (byte)'F' ||
				fileData[3] != (byte)'F') {
			System.err.println("Corrupt WAV file!");
			return;
		}
		
		// CHANNELS
		byte channel_a = fileData[22];
		byte channel_b = fileData[23];
		this.channels = (channel_b << 4) | channel_a;
		
		// SAMPLES
		byte sample_a = fileData[34];
		byte sample_b = fileData[35];
		this.samples = (sample_b << 4) | sample_a;
		
		// FORMAT
		if (samples != 8 && samples != 16) {
			System.err.println("WAV must be 8-bit or 16-bit!");
		}
		
		switch (samples) {
			case 8: {
				if (channels > 1)
					format = AL_FORMAT_STEREO8;
				else
					format = AL_FORMAT_MONO8;
			}
			
			case 16: {
				if (channels > 1)
					format = AL_FORMAT_STEREO16;
				else
					format = AL_FORMAT_MONO16;
			}
		}
		
		// SAMPLE RATE
		int rate_a = Byte.toUnsignedInt(fileData[24]);
		int rate_b = Byte.toUnsignedInt(fileData[25]);
		int rate_c = Byte.toUnsignedInt(fileData[26]);
		int rate_d = Byte.toUnsignedInt(fileData[27]);
		this.sampleRate = (rate_d << 24) | (rate_c << 16) | (rate_b << 8) | rate_a;
		
		// DATA
		int size_a = Byte.toUnsignedInt(fileData[40]);
		int size_b = Byte.toUnsignedInt(fileData[41]);
		int size_c = Byte.toUnsignedInt(fileData[42]);
		int size_d = Byte.toUnsignedInt(fileData[43]);
		this.size = (size_d << 24) | (size_c << 16) | (size_b << 8) | size_a;
		
		byte[] soundData = new byte[size];
		System.arraycopy(fileData, 44, soundData, 0, size);
		this.data = Buffers.createByteBuffer(soundData);
	}

	public int getChannels() {
		return channels;
	}

	public int getSamples() {
		return samples;
	}

	public int getFormat() {
		return format;
	}

	public int getSampleRate() {
		return sampleRate;
	}

	public int getSize() {
		return size;
	}

	public ByteBuffer getData() {
		return data;
	}
}