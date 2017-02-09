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
	
	private static List<Integer> sources = new ArrayList<>();
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
		Iterator<Integer> sourcei = sources.iterator();
		while (sourcei.hasNext()) {
			int source = sourcei.next();
			if (alGetSourcei(source, AL_SOURCE_STATE) == AL_STOPPED) {
				alDeleteSources(source);
				sourcei.remove();
			}
		}
	}

	/**
	 * Play a sound
	 * @param sound Sound object with data
	 * @param volume Volume (0-100) to play sound at
	 */
	public static void playSound(Sound sound, int volume) {
		int source = alGenSources();
		
		sources.add(source);
		sounds.add(sound);
		
		alSourcef(source, AL_PITCH, 1);
		alSourcef(source, AL_GAIN, (float)volume/100f);
		if (sound.isLoop()) alSourcef(source, AL_LOOPING, AL_TRUE);
		alSourcei(source, AL_BUFFER, sound.getBuffer());
		alSourcePlay(source);
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