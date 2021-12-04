package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphic.IRender;
import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;
import uet.oop.bomberman.level.Coordinates;

/**
 * Lớp đại diện cho tất cả thực thể trong game (Bomber, Enemy, Wall, Brick,...)
 */
public abstract class Entity implements IRender {

	protected double x;
	protected double y;
	protected boolean remove = false;
	protected Sprite sprite;

	@Override
	public abstract void update();

	@Override
	public abstract void render(Screen screen);
	
	public void remove() {
		remove = true;
	}
	
	public boolean isRemoved() {
		return remove;
	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getXTile() {
		return Coordinates.pixelToTile(x + sprite.SIZE / 2);
	}
	
	public int getYTile() {
		return Coordinates.pixelToTile(y - sprite.SIZE / 2);
	}
}
