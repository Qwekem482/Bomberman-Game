package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.FileLevelLoader;

import java.util.LinkedList;
import java.util.Random;

public class AIHard {
	private final Random random = new Random();
	private final Enemy enemy;
	private final Board board;
	private int direction;
	private final LinkedList<Integer> queueX;
	private final LinkedList<Integer> queueY;
	private final int[][] trace = new int[20][40];
	private final int[][] distance = new int[20][40];

	private final int[] gapX1 = {-1, 0, 1, 0};
	private final int[] gapY1 = {0, 1, 0, -1};
	private final double[] gapX2 = {0, 16, 0, -0.5};
	private final double[] gapY2 = {-16.5, -1, 0, -1};
	private final int[] permutation = {0, 1, 2, 3};


	public AIHard(Enemy enemy, Board board) {
		this.enemy = enemy;
		this.board = board;
		queueX = new LinkedList<>();
		queueY = new LinkedList<>();
	}

	public boolean isSafe(int x, int y) {
		int radius = Game.getBombRadius();
		for(int direction = 0; direction < 4; ++direction) {
			int u = x, v = y;
			for(int i = 0; i <= radius; ++i) {
				Bomb bomb = board.getBombAt(v, u);
				if (bomb != null) return false;
				Entity entity = board.getEntityAt(Coordinates.tileToPixel(v), Coordinates.tileToPixel(u));
				if (entity instanceof LayeredEntity)
					if (((LayeredEntity) entity).getTopEntity() instanceof Brick || ((LayeredEntity) entity).getTopEntity() instanceof Portal) break;
				if (entity instanceof Wall) break;
				u += gapX1[direction];
				v += gapY1[direction];
			}
		}
		return true;
	}

	public int safeMove()
	{
		for(int i = 0; i < 4; ++i)
		{
			int j = random.nextInt(4);
			int temp = permutation[i];
			permutation[i] = permutation[j];
			permutation[j] = temp;
		}
		for(int i = 0; i < 4; ++i)
		{
			double xc = enemy.getX() + gapX2[permutation[i]];
			double yc = enemy.getY() + gapY2[permutation[i]];
			Bomb bomb = board.getBombAt(Coordinates.pixelToTile(xc), Coordinates.pixelToTile(yc));
			if (bomb != null && enemy.getOnBomb().contains(bomb)) continue;
			if (FileLevelLoader.emptyCell(xc, yc, board) && isSafe(Coordinates.pixelToTile(yc), Coordinates.pixelToTile(xc)))
				return permutation[i];
		}
		return 5;
	}

	public int calculateDirection() {
		int sx = Coordinates.pixelToTile(enemy.getY() - 1);
		int sy = Coordinates.pixelToTile(enemy.getX());
		if (isSafe(sx, sy)) return safeMove();
		for(int i = 0; i < 20; ++i)
			for(int j = 0; j < 40; ++j) {
				trace[i][j] = -1;
				distance[i][j] = 0;
			}
		queueX.clear();
		queueY.clear();
		trace[sx][sy] = -2;
		queueX.add(sx);
		queueY.add(sy);
		while (!queueX.isEmpty()) {
			int x = queueX.pollFirst();
			int y = queueY.pollFirst();
			int detectingRadius = 5;
			if (distance[x][y] > detectingRadius) continue;
			for(int direction = 0; direction < 4; ++direction) {
				int u = x + gapX1[direction];
				int v = y + gapY1[direction];
				if (trace[u][v] != -1) continue;
				trace[u][v] = (direction + 2) & 3;
				Bomb bomb = board.getBombAt(v, u);
				if (bomb != null && !enemy.getOnBomb().contains(bomb)) continue;
				int xc = Coordinates.tileToPixel(v);
				int yc = Coordinates.tileToPixel(u);
				if (!FileLevelLoader.emptyCell(xc, yc, board)) continue;
				if (isSafe(u, v)) {
					while (u + gapX1[trace[u][v]] != sx || v + gapY1[trace[u][v]] != sy) {
						int d = trace[u][v];
						u += gapX1[d];
						v += gapY1[d];
					}
					return (trace[u][v] + 2) & 3;
				}
				distance[u][v] = distance[x][y] + 1;
				queueX.add(u);
				queueY.add(v);
			}
		}
		return random.nextInt(4);
	}

}
