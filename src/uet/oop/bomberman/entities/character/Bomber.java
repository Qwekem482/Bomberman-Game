package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.audio.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.*;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.graphic.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.FileLevelLoader;
import uet.oop.bomberman.graphic.Screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    private final List<Bomb> bombs;
    protected Keyboard input;
    private boolean moving;

    private final ArrayList<Bomb> bombList = new ArrayList<Bomb>();

    private final int[] gapX1 = {9, 10, 9, 1};
    private final int[] gapY1 = {-15, -1, 0, -1};
    private final int[] gapX2 = {2, 10, 2, 1};
    private final int[] gapY2 = {-15, -14, 0, -14};
    private final int[] gapX3 = {2, 9, 9, 2};
    private final int[] gapY3 = {-14, -14, -3, -3};
    private final int[] gapX4 = {-8, -8, 8, 8};
    private final int[] gapY4 = {-8, 8, -8, 8};

    protected int timeBetweenBombs = 0;


    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        bombs = this.board.getBombs();
        input = this.board.getInput();
        sprite = Sprite.player_right;
    }

    @Override
    public void update() {
        clearBombs();
        if (!live) {
            afterKill();
            return;
        }
        if (timeBetweenBombs > 0) {
            timeBetweenBombs--;
        }
        animate();
        calculateMove();
        checkCollide();
        checkOutOfBomb();
        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();
        if (live) {
            chooseSprite();
        }
        else {
            sprite = Sprite.player_dead1;
        }

        screen.renderEntity((int) x, (int) y - sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(board, this);
        Screen.setOffset(xScroll, 0);
    }

    private void detectPlaceBomb() {
        if (input.space && timeBetweenBombs == 0 && Game.getBombRate() > 0) {
            placeBomb(Coordinates.pixelToTile(x + 5), Coordinates.pixelToTile(y - 8));
            Game.addBombRate(-1);
            timeBetweenBombs = 15;
        }
    }

    protected void placeBomb(int x, int y) {
        Bomb bomb = new Bomb(x, y, board);
        board.addBomb(bomb);
        bombList.add(bomb);
        int xc = Coordinates.tileToPixel(x);
        int yc = Coordinates.tileToPixel(y);
        Iterator<Character> itr = board.characters.iterator();
        Character cur;
        while(itr.hasNext()) {
            cur = itr.next();
            if (cur instanceof  Enemy)
                if(xc - 15 <= cur.getX() && cur.getX() <= xc + 15 && yc + 1 <= cur.getY() && cur.getY() <= yc + Game.TILES_SIZE + 15)
                    ((Enemy) cur).addBomb(bomb);
        }
        Sound.makeSound("SetBomb");
    }

    protected void checkOutOfBomb()
    {
        for(int i = bombList.size() - 1; i >= 0; --i) {
            Bomb bomb = bombList.get(i);
            boolean out = true;
            for (int corner = 0; corner < 4; ++corner) {
                int cornerX = (int) x + gapX3[corner];
                int cornerY = (int) y + gapY3[corner];
                if (board.getBombAt(Coordinates.pixelToTile(cornerX), Coordinates.pixelToTile(cornerY)) == bomb)
                {
                    out = false;
                    break;
                }
            }
            if (out) {
                bombList.remove(bomb);
            }
        }
    }

    private void clearBombs() {
        Iterator<Bomb> bs = bombs.iterator();
        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }
    }


    @Override
    public void kill() {
        if (!live) return;
        Sound.makeSound("BomberDies");
        live = false;
        board.addlive(-1);
    }

    @Override
    protected void afterKill() {
        if (timeAfter > 0) --timeAfter;
        else {
            if (board.getlive() < 1) board.endGame();
            else board.restartLevel();
        }
    }

    @Override
    protected void calculateMove() {
        int dx = 0, dy = 0;
        if(input.up) dy--;
        if(input.down) dy++;
        if(input.left) dx--;
        if(input.right) dx++;
        if(dx != 0 || dy != 0)  {
            moving = true;
            for(int i = 1, movement = (int) Game.getBomberSpeed(); i <= movement; ++i)
                move(dx, dy);
        } else moving = false;
    }

    @Override
    public boolean canMove(double dx, double dy) {
        if (!live) return false;
        int direction = this.direction;
        if (dy < 0) direction = 0;
        if (dx > 0) direction = 1;
        if (dy > 0) direction = 2;
        if (dx < 0) direction = 3;

        int x1 = (int) x + gapX1[direction], y1 = (int) y + gapY1[direction];
        int x2 = (int) x + gapX2[direction], y2 = (int) y + gapY2[direction];

        Bomb bomb;
        bomb = board.getBombAt(Coordinates.pixelToTile(x1), Coordinates.pixelToTile(y1));
        if (bomb != null && bombList.indexOf(bomb) == -1) return false;
        bomb = board.getBombAt(Coordinates.pixelToTile(x2), Coordinates.pixelToTile(y2));
        if (bomb != null && bombList.indexOf(bomb) == -1) return false;

        return (FileLevelLoader.emptyCell(x + gapX1[direction], y + gapY1[direction], board) &&
                FileLevelLoader.emptyCell(x + gapX2[direction], y + gapY2[direction], board));
    }

    public void checkCollide() {
        for (int corner = 0 ; corner < 4; ++corner)
        {
            double cornerX = x + gapX3[corner];
            double cornerY = y + gapY3[corner];
            for(int i = 0; i < 4; ++i) {
                int cellX = Coordinates.pixelToTile(cornerX + gapX4[i]);
                int cellY = Coordinates.pixelToTile(cornerY + gapY4[i]);

                Entity entity = board.getEntity(cellX, cellY, this);
                if (entity instanceof Enemy && ((Character) entity).live) {
                    double cx = entity.getX();
                    double cy = entity.getY();
                    if (x - Game.TILES_SIZE + 2 <= cx && cx < x + 10 && y - 14 <= cy && cy <= y + Game.TILES_SIZE - 2) {
                        kill();
                        return;
                    }
                }
            }
            int cellX = Coordinates.pixelToTile(cornerX);
            int cellY = Coordinates.pixelToTile(cornerY);
            Entity entity = board.getEntity(cellX, cellY, this);
            if (entity instanceof FlameSegment) {
                kill();
                return;
            }
            if (entity instanceof LayeredEntity layeredEntity) {
                if (layeredEntity.getTopEntity() instanceof Item item) {
                    if (item instanceof BombItem) Game.addBombRate(1);
                    if (item instanceof FlameItem) Game.addBombRadius(1);
                    if (item instanceof SpeedItem && Game.getBomberSpeed() == 1) Game.addBomberSpeed(1);
                    Sound.makeSound("CollectItem");
                    item.remove();
                    layeredEntity.update();
                }
                if (layeredEntity.getTopEntity() instanceof Portal)
                {
                    Sound.makeSound("EnterPortal");
                    board.nextLevel();
                }
            }
        }
    }

    @Override
    public void move(double x1, double y1) {
        if (x1 > 0) direction = 1;
        if (x1 < 0) direction = 3;
        if (y1 < 0) direction = 0;
        if (y1 > 0) direction = 2;

        if (x1 != 0 && canMove(x1, 0)) {
            x += x1;

        }
        if (y1 != 0 && canMove(0, y1))
        {
            y += y1;

        }

    }

    private void chooseSprite() {
        switch (direction) {
            case 0 -> {
                sprite = Sprite.player_up;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
            }
            case 2 -> {
                sprite = Sprite.player_down;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
            }
            case 3 -> {
                sprite = Sprite.player_left;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
            }
            default -> {
                sprite = Sprite.player_right;
                if (moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
            }
        }
    }
}
