package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.graphic.Screen;

/**
 * Bomber (player) and enemy
 */
public abstract class Character extends AnimatedEntity {

    protected Board board;
    protected int direction = 2;
    protected boolean live = true;
    public int time = 40;

    public Character(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(Screen screen);

    protected abstract void calculateMove();

    protected abstract void move(double x, double y);

    public abstract void kill();

    protected abstract void afterKill();

    protected abstract boolean canMove(double x, double y);

    protected double getXMessage() {
        return (this.x * Game.SCALE) + (sprite.SIZE / 2 * Game.SCALE);
    }

    protected double getYMessage() {
        return (this.y * Game.SCALE) - (sprite.SIZE / 2 * Game.SCALE);
    }

}
