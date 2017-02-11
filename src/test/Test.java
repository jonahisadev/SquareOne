package test;

import com.amittaigames.engine.*;
import com.amittaigames.engine.audio.AudioPlayer;
import com.amittaigames.engine.audio.Sound;
import com.amittaigames.engine.graphics.*;
import com.amittaigames.engine.util.Keys;

public class Test extends CoreGame {

	private Rect rect;
	
	public static void main(String[] args) {
		Window.init("Test", 800, 600, new Test());
	}
	
	@Override
	public void init() {
		rect = new Rect(100, 100, 100, 100);
		rect.setColor(255, 255, 255);
		Sound test = new Sound("/Users/batman/Music/MLG.wav", true);
		test.logWAVInfo();
		
		//AudioPlayer.playSound(test, 75);
	}

	@Override
	public void render(Render r) {
		r.clear(0, 255, 128);
		r.drawRect(rect);
	}

	@Override
	public void update(float delta) {
		if (Window.isKeyDown(Keys.KEY_D))
			rect.translate(20 * delta, 0);
		if (Window.isKeyDown(Keys.KEY_A))
			rect.translate(-20 * delta, 0);
		if (Window.isKeyDown(Keys.KEY_W))
			rect.translate(0, -20 * delta);
		if (Window.isKeyDown(Keys.KEY_S))
			rect.translate(0, 20 * delta);
		
		if (Window.isKeyDown(Keys.KEY_LEFT))
			rect.rotate(10 * delta);
		
		Window.setTitle("Test - FPS: " + Window.getCurrentFPS());
	}

	@Override
	public void cleanUp() {
		rect.delete();
	}
	
}
