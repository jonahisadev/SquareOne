package com.amittaigames.engine.audio;

import org.lwjgl.openal.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;

public class AudioPlayer {
	
	private static long device;
	private static long context;
	
	private static boolean initialized = false;
	
	//private static List<Integer> sources = new ArrayList<>();
	private static List<Sound> sounds = new ArrayList<>();

	/**
	 * Initialize OpenAL context. Do NOT call this! It is called in the main loop
	 */
	public static void init() {
		try {
			device = alcOpenDevice((ByteBuffer)null);
			context = alcCreateContext(device, (IntBuffer)null);
			if (!alcMakeContextCurrent(context)) {
				System.err.println("Could not initialize OpenAL!");
				return;
			}
			AL.createCapabilities(ALC.createCapabilities(device));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		initialized = true;
		
		alListener3f(AL_POSITION, 0, 0, 0);
		alListener3f(AL_VELOCITY, 0, 0, 0);
	}

	/**
	 * Run playing audio checks. Do NOT call this! It is called in the main loop
	 */
	public static void runAudioLoop() {
		Iterator<Sound> soundi = sounds.iterator();
		while (soundi.hasNext()) {
			Sound sound = soundi.next();
			if (alGetSourcei(sound.getSource(), AL_SOURCE_STATE) == AL_STOPPED) {
				alDeleteSources(sound.getSource());
				sound.delete();
				soundi.remove();
			}
		}
	}

	/**
	 * Play a sound
	 * @param sound Sound object with data
	 * @param volume Volume (0-100) to play sound at
	 * @return Index in sound list to sound
	 */
	public static int playSound(Sound sound, int volume) {
		sound.setSource(alGenSources());
		
		//sources.add(source);
		int listi = sounds.size();
		sounds.add(sound);
		
		alSourcef(sound.getSource(), AL_PITCH, 1);
		alSourcef(sound.getSource(), AL_GAIN, (float)volume/100f);
		if (sound.isLoop()) alSourcef(sound.getSource(), AL_LOOPING, AL_TRUE);
		alSourcei(sound.getSource(), AL_BUFFER, sound.getBuffer());
		alSourcePlay(sound.getSource());
		
		return listi;
	}

	/**
	 * Pause a sound
	 * @param sound Sound to be stopped
	 */
	public static void pauseSound(Sound sound) {
		alSourcePause(sound.getSource());
	}
	
	/**
	 * Set a sound's volume
	 * @param sound Sound's volume to be set
	 * @param volume Volume to set sound's volume to
	 */
	public static void setSoundVolume(Sound sound, int volume) {
		alSourcef(sound.getSource(), AL_GAIN, (float)volume/100f);
		alSourcePause(sound.getSource());
		alSourcePlay(sound.getSource());
	}
	
	/**
	 * Stop a sound
	 * @param sound Sound to be stopped
	 */
	public static void stopSound(Sound sound) {
		alSourceStop(sound.getSource());
	}

	/**
	 * Destroy OpenAL context and clean up. Do NOT call this! It is called in the main loop
	 */
	public static void destroy() {
		if (initialized)
			alcDestroyContext(context);
			alcCloseDevice(device);
		
		for (Sound sound : sounds) {
			alDeleteBuffers(sound.getBuffer());
		}
		sounds.clear();
	}

	/**
	 * See if AudioPlayer has been initialized
	 * @return Initialized boolean
	 */
	public static boolean isInitialized() {
		return initialized;
	}

}