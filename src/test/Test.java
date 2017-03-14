package test;

import com.amittaigames.engine.*;
import com.amittaigames.engine.graphics.*;
import com.amittaigames.engine.util.Keys;

public class Test extends CoreGame {

	private Rect rect;
	private SpriteSheet ss;
	private boolean pressed = false;
	private int cycle = 0;
	
	public static void main(String[] args) {
		Window.enable("anti_alias");
		Window.init("Test", 800, 600, false, new Test());
	}
	
	@Override
	public void init() {
		rect = new Rect(100, 100, 100, 100);
		
		ss = new SpriteSheet("/textures/spritesheet.png", 16, 16, 0, false);
		ss.createSprite(100, 100, 128, 128, 2, 1);
		
		//Sound test = new Sound("/Users/batman/Music/MLG.wav", true);
		//test.logWAVInfo();
		
		//AudioPlayer.playSound(test, 100);
	}

	@Override
	public void render(Render r) {
		r.clear(0, 255, 128);
		//r.drawTexturedRect(rect);
		
		r.drawSprites(ss);
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
		
		if (Window.isKeyDown(Keys.KEY_SPACE)) {
			if (!pressed) {
				if (cycle == 3)
					cycle = 0;
				else
					cycle++;
				
				ss.getSpriteByIndex(0).setImageLocation(cycle, 1);
				pressed = true;
			}
		} else {
			pressed = false;
		}
		
		ss.getSpriteByIndex(0).translate(10 * delta, 0);
		ss.getSpriteByIndex(0).rotate(5 * delta);
		
		Window.setTitle("Test - FPS: " + Window.getCurrentFPS());
	}

	@Override
	public void cleanUp() {
		rect.delete();
	}
	
}
