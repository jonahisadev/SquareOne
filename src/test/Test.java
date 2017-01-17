package test;

import com.amittaigames.engine.*;
import com.amittaigames.engine.graphics.*;
import com.amittaigames.engine.util.Keys;

public class Test extends CoreGame {

	private Rect rect;
	private Rect test;
	
	public static void main(String[] args) {
		Window.init("Test", 800, 600, new Test());
	}
	
	@Override
	public void init() {
		rect = new Rect(100, 100, 100, 100);
		rect.setColor(255, 255, 255);
		
		test = new Rect(500, 50, 50, 50);
		test.setColor(0, 0, 0);

		Animator.translate(rect, 1000, 500, 100);
	}

	@Override
	public void render(Render r) {
		r.clear(0, 255, 128);
		r.drawRect(rect);
		
		r.drawRect(test);
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void cleanUp() {
		rect.delete();
		test.delete();
	}
	
}
