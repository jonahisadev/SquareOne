package test;

import com.amittaigames.engine.CoreGame;
import com.amittaigames.engine.graphics.Rect;
import com.amittaigames.engine.graphics.Render;
import com.amittaigames.engine.graphics.Window;

public class BasicTest extends CoreGame {

	private Rect quad;
	
	public static void main(String[] args) {
		// Create a 800x600 window
		Window.init("SquareOne - BasicTest", 800, 600, false, new BasicTest());
	}

	@Override
	public void init() {
		// Create a 128x128 rect at (64, 64) on the screen
		quad = new Rect(64, 64, 128, 128);
		
		// Set the color to a green
		quad.setColor(0, 255, 128);
	}

	@Override
	public void render(Render render) {
		// Clear the screen buffer
		// Also set the color to a white
		render.clear(234, 234, 234);
		
		// Draw the rectangle
		render.drawRect(quad);
	}

	@Override
	public void update(float delta) {

	}

	@Override
	public void cleanUp() {
		// Delete the OpenGL buffers
		quad.delete();
	}
	
}