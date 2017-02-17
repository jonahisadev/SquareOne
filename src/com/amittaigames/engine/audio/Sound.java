package com.amittaigames.engine.audio;

import com.amittaigames.engine.util.FileIO;

import java.io.File;

import static org.lwjgl.openal.AL10.*;

public class Sound {

	private int vbo;
	private WaveData waveData;
	private boolean loop = false;
	private int source;
	
	/**
	 * @param path Path to WAV file
	 * @parm external Is the file external to the JAR file or not?
	 */
	public Sound(String path, boolean external) {
		byte[] fileData = FileIO.readExternalBinary(path, (int)new File(path).length());
		waveData = new WaveData(fileData);
		
		vbo = alGenBuffers();
		alBufferData(vbo, waveData.getFormat(), waveData.getData(), waveData.getSampleRate());
	}

	/**
	 * Set sound to loop or not before playback
	 * @param loop Loop state
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	/**
	 * Log information about the WAV file loaded
	 */
	public void logWAVInfo() {
		System.out.println("Channels: " + waveData.getChannels());
		System.out.println("Samples: " + waveData.getSamples());
		System.out.println("Sample Rate: " + waveData.getSampleRate() + " Hz");
		System.out.println("Raw Sound Size (bytes): " + waveData.getSize());
	}
	
	/**
	 * Free OpenAL buffers
	 */
	public void delete() {
		alDeleteSources(source);
		alDeleteBuffers(vbo);
	}

	/**
	 * Get OpenAL buffer
	 * @return OpenAL buffer
	 */
	public int getBuffer() {
		return vbo;
	}

	/**
	 * Get WAV data
	 * @return WAV data
	 */
	public WaveData getWaveData() {
		return waveData;
	}

	/**
	 * Check if sound is a loop
	 * @return Loop boolean
	 */
	public boolean isLoop() {
		return loop;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}
}