package com.amittaigames.engine.graphics;

import com.amittaigames.engine.CoreGame;
import com.amittaigames.engine.audio.AudioPlayer;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {

	// Window related items
	private static long window;
	
	// Classes to be used in the game loop
	private static CoreGame game;
	private static Render render;

	// FPS garbage
	private static long lastFPS;
	private static int LAST = 0;
	private static int FPS;
	
	/**
	 * Create window and OpenGL context
	 * @param title Window title
	 * @param width Window width
	 * @param height Window height
	 * @param game CoreGame instance
	 */
	public static void init(String title, int width, int height, CoreGame game) {
		if (!glfwInit()) {
			System.err.println("Could not initialize window system!");
			System.exit(1);
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		window = glfwCreateWindow(width, height, title, 0, 0);
		if (window == 0) {
			glfwTerminate();
			System.err.println("Could not create window!");
			System.exit(1);
		}
		
		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		GLFWVidMode vidMode = glfwGetVideoMode(window);
		glfwSetWindowPos(window, (vidMode.width() / 2) - (width / 2), (vidMode.height() / 2) - (height / 2));
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width, height, 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		Window.game = game;
		Window.render = new Render();
		
		AudioPlayer.init();
		
		start();
	}

	/**
	 * Start the game loop
	 */
	private static void start() {
		game.init();
		
		double last;
		double now;
		
		float delta = 1.0f;
		
		while (!glfwWindowShouldClose(window)) {
			last = glfwGetTime();

			game.update(delta);
			game.render(render);
			
			if (AudioPlayer.isInitialized())
				AudioPlayer.runAudioLoop();
			
			glfwSwapBuffers(window);
			glfwPollEvents();
			
			now = glfwGetTime();
			delta = (float)(now - last) * 10.0f;
		}
		
		game.cleanUp();
		AudioPlayer.destroy();
		
		glfwDestroyWindow(window);
		glfwTerminate();
		
		System.exit(0);
	}

	/**
	 * Set window title
	 * @param title Title of window
	 */
	public static void setTitle(String title) {
		glfwSetWindowTitle(window, title);
	}

	/**
	 * Get current frames per second being rendered on screen
	 * @return FPS on screen
	 */
	public static int getCurrentFPS() {
		int ret = LAST;
		if (glfwGetTime() * 1000 - lastFPS > 1000) {
			ret = FPS;
			LAST = FPS;
			FPS = 0;
			lastFPS += 1000;
		}
		FPS++;
		return ret;
	}

	/**
	 * Set the window to close
	 */
	public static void close() {
		glfwSetWindowShouldClose(window, true);
	}

	/**
	 * Check if a key is pressed
	 * @param key Key code
	 * @return True if the key is pressed, false if otherwise
	 */
	public static boolean isKeyDown(int key) {
		int code = glfwGetKey(window, key);
		
		if (code == GLFW_PRESS)
			return true;
		else
			return false;
	}

}
