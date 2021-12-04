package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.enemy.ai.AIEasy;
import uet.oop.bomberman.graphic.Sprite;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.FileLevelLoader;

public class Balloon extends Enemy {
	private AIEasy ai;

	public Balloon(int x, int y, Board board) {
		super(x, y, board, Sprite.balloon_dead, 0.5, 100);
		sprite = Sprite.balloon_left1;
		ai = new AIEasy();
		direction = ai.calculateDirection();
	}

	@Override
	public void calculateMove() {
		int xi = (int) x;
		int yi = (int) y;
		if (xi == x && yi == y && xi % 16 == 0 && yi % 16 == 0) {
			direction = ai.calculateDirection();
		}

		int dx = 0, dy = 0;
		switch (direction) {
			case 0 -> dy--;
			case 1 -> dx++;
			case 2 -> dy++;
			case 3 -> dx--;
		}

		if (dx != 0 || dy != 0)
			move(dx * speed, dy * speed);
	}

	@Override
	public boolean canMove(double x, double y) {
		int xc = (int) (x + gapX1[direction]);
		int yc = (int) (y + gapY1[direction]);
		Bomb bomb = board.getBombAt(Coordinates.pixelToTile(xc), Coordinates.pixelToTile(yc));
		if (bomb != null && !onBomb.contains(bomb)) return false;
		return FileLevelLoader.emptyCell(xc, yc, board);
	}

	@Override
	protected void chooseSprite() {
		switch (direction) {
			case 0, 1 -> sprite = Sprite.movingSprite(Sprite.balloon_right1, Sprite.balloon_right2, Sprite.balloon_right3, animate, 60);
			case 2, 3 -> sprite = Sprite.movingSprite(Sprite.balloon_left1, Sprite.balloon_left2, Sprite.balloon_left3, animate, 60);
		}
	}
}
