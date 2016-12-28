package com.amittaigames.engine;

public abstract class CoreGame {

	public abstract void init();
	public abstract void render(Render r);
	public abstract void update(float delta);
	public abstract void cleanUp();

}
