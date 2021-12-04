package uet.oop.bomberman.entities.tile.destroyable;

import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphic.Sprite;

/**
 * Destroyable entities
 */
public class DestroyableTile extends Tile {

    private int animate = 0;
    protected boolean destroyed = false;
    protected int disappearTime = 20;
    protected Sprite belowSprite = Sprite.grass;

    public DestroyableTile(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    @Override
    public void update() {
        if (destroyed) {
            int MAX_ANIMATE = 7500;
            if (animate < MAX_ANIMATE) {
                animate++;
            } else {
                animate = 0;
            }
            if (disappearTime > 0) {
                disappearTime--;
            }
            else {
                remove();
            }
        }
    }

    public void destroy() {
        destroyed = true;
    }


    public void addBelowSprite(Sprite sprite) {
        belowSprite = sprite;
    }

    protected Sprite movingSprite(Sprite normal, Sprite sprite1, Sprite sprite2) {
        int calc = animate % 30;
        if(calc < 10) {
            return normal;
        }
        if(calc < 20) {
            return sprite1;
        }
        return sprite2;
    }

}

