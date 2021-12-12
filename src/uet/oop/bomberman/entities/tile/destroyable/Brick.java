package uet.oop.bomberman.entities.tile.destroyable;


import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Brick extends DestroyableTile {
	
	public Brick(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render(Screen screen) {
		if (isRemoved()) return;
		int x = Coordinates.tileToPixel(this.x);
		int y = Coordinates.tileToPixel(this.y);
		
		if(destroyed) {
			sprite = movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.brick_exploded2);
			
			screen.renderEntityWithBelowSprite(x, y, this, belowSprite);
		}
		else
			screen.renderEntity( x, y, this);
	}
	
}
