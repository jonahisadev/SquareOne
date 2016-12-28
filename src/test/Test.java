package test;

import com.amittaigames.engine.*;

public class Test extends CoreGame {

	private Rect rect;
	private Font mainFont;
	private TexturedRect trect;
	
	public static void main(String[] args) {
		Window.init("Test", 800, 600, new Test());
	}
	
	@Override
	public void init() {
		rect = new Rect(100, 100, 100, 100);
		rect.setColor(128, 128, 128);
		
		Font.load("/fonts/Helvetica", 1, 0.5f);
		mainFont = Font.get("Helvetica 1");
		
		trect = new TexturedRect(400, 400, 64, 64, "/textures/Brick.png", false);
	}

	@Override
	public void render(Render r) {
		r.clear(0, 255, 128);
		r.drawRect(rect);
		
		r.drawText("X: " + (int)rect.getX(), 5, 5, mainFont);
		r.drawText("Y: " + (int)rect.getY(), 5, 40, mainFont);
		
		r.drawTexturedMesh(trect.getMesh());
	}

	@Override
	public void update(float delta) {
		if (Window.isKeyDown(Keys.KEY_D)) {
			rect.translate(20 * delta, 0);
		}
		if (Window.isKeyDown(Keys.KEY_A)) {
			rect.translate(-20 * delta, 0);
		}
		if (Window.isKeyDown(Keys.KEY_W)) {
			rect.translate(0, -20 * delta);
		}
		if (Window.isKeyDown(Keys.KEY_S)) {
			rect.translate(0, 20 * delta);
		}
	}

	@Override
	public void cleanUp() {
		rect.getMesh().destroy();
	}
	
}
