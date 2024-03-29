package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.audio.Sound;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;

import java.util.Iterator;

public class Bomb extends AnimatedEntitiy {
    protected double timeToExplode = 120; //2 seconds
    public int timeAfter = 10;

    protected Board board;
    protected Flame[] flames;
    protected boolean exploded = false;

    public Bomb(int x, int y, Board board) {
        this.x = x;
        this.y = y;
        this.board = board;
        sprite = Sprite.bomb;
    }

    @Override
    public void update() {
        if(timeToExplode > 0)
            timeToExplode--;
        else {
            if(!exploded)
                explode();
            else
                updateFlames();

            if(timeAfter > 0)
                timeAfter--;
            else
                remove();
        }
        animate();
    }

    @Override
    public void render(Screen screen) {
        if(exploded) {
            sprite =  Sprite.bomb_exploded2;
            renderFlames(screen);
        } else
            sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60);

        int xt = (int) x << 4;
        int yt = (int) y << 4;
        screen.renderEntity(xt, yt , this);
    }

    public void renderFlames(Screen screen) {
        for (Flame flame : flames) {
            flame.render(screen);
        }
    }

    public void updateFlames() {
        for (Flame flame : flames) {
            flame.update();
        }
    }

    /**
     * Xử lý Bomb nổ
     */
    protected void explode() {
        exploded = true;

        Iterator<Character> itr = board.characters.iterator();
        Character cur;
        while(itr.hasNext()) {
            cur = itr.next();
            if(cur.getXTile() == x && cur.getYTile() == y) cur.kill();
        }
        flames = new Flame[4];
        for (int i = 0 ; i < 4; i++)
            flames[i] = new Flame((int) x, (int) y, i, Game.getBombRadius(), board);
        Sound.makeSound("Explode");
    }

    public FlameSegment flameAt(int x, int y) {
        if(!exploded) return null;

        for (Flame flame : flames) {
            if (flame == null) {
                return null;
            }
            FlameSegment e = flame.flameSegmentAt(x, y);
            if (e != null) {
                return e;
            }
        }
        return null;
    }
}
