package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.audio.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;
import uet.oop.bomberman.level.Coordinates;

import java.awt.*;
import java.util.ArrayList;

public abstract class Enemy extends Character {

	protected int points;
	protected double speed;
	protected final double MAX_STEPS;
	protected final double rest;
	protected double steps;
	protected int finalAnimation = 30;
	protected Sprite deadSprite;
	private static int numberOfEnemy = 0;
	protected ArrayList<Bomb> onBomb = new ArrayList<Bomb>();
	protected double[] gapX1 = {0, 16, 0, -0.5};
	protected double[] gapY1 = {-16.5, -1, 0, -1};
	protected double[] gapX2 = {2.5, 13.5, 13, 2};
	protected double[] gapY2 = {-13.5, -14, -3, -2.5};
	protected double[] gapX3 = {0.5, 15.5, 15, 0};
	protected double[] gapY3 = {-15.5, -16, -1, -0.5};

	public Enemy(int x, int y, Board board, Sprite dead, double speed, int points) {
		super(x, y, board);
		numberOfEnemy++;
		this.points = points;
		this.speed = speed;
		MAX_STEPS = Game.TILES_SIZE / this.speed;
		rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
		steps = MAX_STEPS;
		timeAfter = 20;
		deadSprite = dead;
	}

	public static void setNumberOfEnemy(int numberOfEnemy) {
		Enemy.numberOfEnemy = numberOfEnemy;
	}

	public static int getNumberOfEnemy() {
		return numberOfEnemy;
	}

	@Override
	public void update() {
		animate();
		if(!live) {
			afterKill();
			return;
		}
		calculateMove();
		checkCollide();
		checkOutOfBomb();
		board.getBomber().checkCollide();
	}

	public void addBomb(Bomb bomb)
	{
		onBomb.add(bomb);
	}

	protected void checkOutOfBomb() {
		for(int i = onBomb.size() - 1; i >= 0; --i) {
			Bomb bomb = onBomb.get(i);
			boolean out = true;
			for (int corner = 0; corner < 4; ++corner) {
				int cornerX = (int) (x + gapX3[corner]);
				int cornerY = (int) (y + gapY3[corner]);
				if (board.getBombAt(Coordinates.pixelToTile(cornerX), Coordinates.pixelToTile(cornerY)) == bomb)
				{
					out = false;
					break;
				}
			}
			if (out) onBomb.remove(bomb);
		}
	}
	
	@Override
	public void render(Screen screen) {
		
		if(live)
			chooseSprite();
		else {
			if(timeAfter > 0) {
				sprite = deadSprite;
				animate = 0;
			} else {
				sprite = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
			}
				
		}
			
		screen.renderEntity((int) x, (int) y - sprite.SIZE, this);
	}

	void checkCollide() {
		if (!live) return;
		for(int corner = 0; corner < 4; ++corner) {
			int cellX = Coordinates.pixelToTile(x + gapX2[corner]);
			int cellY = Coordinates.pixelToTile(y + gapY2[corner]);
			Entity entity = board.getEntity(cellX, cellY, this);
			if (entity instanceof FlameSegment) {
				kill();
				return;
			}
		}
	}

	@Override
	public void move(double x1, double y1) {
		if(!live) return;
		if (canMove(x, y)) {
			y += y1;
			x += x1;
		}
	}
	
	@Override
	public void kill() {
		if(!live) {
			return;
		}
		live = false;
		numberOfEnemy--;
		board.addPoints(points);
		Message message = new Message("+" + points, getXMessage(), getYMessage(), 2, Color.white, 14);
		board.addMessage(message);
		Sound.makeSound("EnemyDies");
	}
	
	
	@Override
	protected void afterKill() {
		if(timeAfter > 0) {
			--timeAfter;
		}
		else {
			if(finalAnimation > 0) {
				--finalAnimation;
			}
			else {
				remove();
			}
		}
	}
	
	protected abstract void chooseSprite();

	public ArrayList<Bomb> getOnBomb() {
		return onBomb;
	}
}
