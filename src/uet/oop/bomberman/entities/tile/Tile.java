package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;
import uet.oop.bomberman.level.Coordinates;

/**
 * Entity cố định, không di chuyển
 */
public abstract class Tile extends Entity {
	public Tile(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}

	@Override
	public void render(Screen screen) {
		screen.renderEntity(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y), this);
	}
	
	@Override
	public void update() {
	}
}
