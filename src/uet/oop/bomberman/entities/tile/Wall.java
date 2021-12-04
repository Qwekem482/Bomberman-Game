package uet.oop.bomberman.entities.tile;


import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphic.Sprite;

public class Wall extends Tile {
	public Wall(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
}
