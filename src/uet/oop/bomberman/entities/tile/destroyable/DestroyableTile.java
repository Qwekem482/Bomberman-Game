package uet.oop.bomberman.entities.tile.destroyable;

import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphic.Sprite;

/**
 * Đối tượng cố định có thể bị phá hủy
 */
public class DestroyableTile extends Tile {

	private final int MAX_ANIMATE = 7500;
	private int animate = 0;
	protected boolean destroyed = false;
	protected int disappearTime = 20;
	protected Sprite belowSprite = Sprite.grass;

	public DestroyableTile(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void update() {
		if (destroyed) {
			if (animate < MAX_ANIMATE) animate++; else animate = 0;
			if (disappearTime > 0) {
				disappearTime--;
			}
			else
				remove();
		}
	}

	public void destroy() {
		destroyed = true;
	}

	
	public void addBelowSprite(Sprite sprite) {
		belowSprite = sprite;
	}
	
	protected Sprite movingSprite(Sprite normal, Sprite s1, Sprite s2) {
		int calculation = animate % 30;
		if(calculation < 10) {
			return normal;
		}
		if(calculation < 20) {
			return s1;
		}
		return s2;
	}
	
}
