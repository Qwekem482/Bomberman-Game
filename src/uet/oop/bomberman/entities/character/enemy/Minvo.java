package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.enemy.ai.AIHard;
import uet.oop.bomberman.graphic.Sprite;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.FileLevelLoader;

public class Minvo extends Enemy {
	private AIHard ai;

	public Minvo(int x, int y, double speed, Board board) {
		super(x, y, board, Sprite.minvo_dead, speed, 200);
		sprite = Sprite.minvo_left1;
		ai = new AIHard(this, this.board);
		direction = 2;
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
			case 0, 1 -> sprite = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animate, 60);
			case 2, 3 -> sprite = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animate, 60);
		}
	}
}
