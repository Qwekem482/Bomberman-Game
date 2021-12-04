package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.FileLevelLoader;

import java.util.LinkedList;
import java.util.Random;

public class AIMedium {
	private final Random random = new Random();
	private final Enemy enemy;
	private final Board board;
	private double speed;
	private final LinkedList<Integer> queueX;
	private final LinkedList<Integer> queueY;
	private final int[][] trace = new int[20][40];
	private final int[][] distance = new int[20][40];

	private final int[] gapX = {-1, 0, 1, 0};
	private final int[] gapY = {0, 1, 0, -1};


	public AIMedium(Enemy enemy, Board board)
	{
		this.enemy = enemy;
		this.board = board;
		queueX = new LinkedList<Integer>();
		queueY = new LinkedList<Integer>();
	}

	public int calculateDirection() {
		int sx = Coordinates.pixelToTile(enemy.getY() - 1);
		int sy = Coordinates.pixelToTile(enemy.getX());
		Bomber bomber = board.getBomber();
		for(int i = 0; i < 20; ++i) {
			for(int j = 0; j < 40; ++j) {
				trace[i][j] = -1;
				distance[i][j] = 0;
			}
		}
		queueX.clear();
		queueY.clear();
		trace[sx][sy] = -2;
		queueX.add(sx);
		queueY.add(sy);
		while (!queueX.isEmpty())
		{
			int x = queueX.pollFirst();
			int y = queueY.pollFirst();
			int detectingRadius = 5;
			if (distance[x][y] > detectingRadius) continue;
			for(int direction = 0; direction < 4; ++direction)
			{
				int u = x + gapX[direction];
				int v = y + gapY[direction];
				if (trace[u][v] != -1) continue;
				trace[u][v] = (direction + 2) & 3;
				Bomb bomb = board.getBombAt(v, u);
				if (bomb != null && !enemy.getOnBomb().contains(bomb)) continue;
				int xc = Coordinates.tileToPixel(v);
				int yc = Coordinates.tileToPixel(u);
				if (!FileLevelLoader.emptyCell(xc, yc, board)) continue;
				if (xc - 10 < bomber.getX() && bomber.getX() < xc + Game.TILES_SIZE - 2
						&& yc + 2 <= bomber.getY() && bomber.getY() <= yc + Game.TILES_SIZE + 14) {
					while (u + gapX[trace[u][v]] != sx || v + gapY[trace[u][v]] != sy) {
						int d = trace[u][v];
						u += gapX[d];
						v += gapY[d];
					}
					speed = 1;
					return (trace[u][v] + 2) & 3;
				}
				distance[u][v] = distance[x][y] + 1;
				queueX.add(u);
				queueY.add(v);
			}
		}
		if (random.nextInt(5) == 0) {
			speed = 1;
		}
		else {
			speed = 0.5;
		}
		return random.nextInt(4);
	}

	public double calculateSpeed() {
		return speed;
	}

}
