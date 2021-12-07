package uet.oop.bomberman.Entities.Bomb;

import uet.oop.bomberman.Entities.Entity;
import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class FlameSegment extends Entity {
    protected boolean last;

    public FlameSegment(int x, int y, int direction, boolean last) {
        this.x = x;
        this.y = y;
        this.last = last;
        switch (direction) {
            case 0:
                if(!last) {
                    sprite = Sprite.explosion_vertical2;
                } else {
                    sprite = Sprite.explosion_vertical_top_last2;
                }
                break;
            case 1:
                if(!last) {
                    sprite = Sprite.explosion_horizontal2;
                } else {
                    sprite = Sprite.explosion_horizontal_right_last2;
                }
                break;
            case 2:
                if(!last) {
                    sprite = Sprite.explosion_vertical2;
                } else {
                    sprite = Sprite.explosion_vertical_down_last2;
                }
                break;
            case 3:
                if(!last) {
                    sprite = Sprite.explosion_horizontal2;
                } else {
                    sprite = Sprite.explosion_horizontal_left_last2;
                }
                break;
        }
    }

    @Override
    public void render(Screen screen) {
        int xt = Coordinates.tileToPixel(x);
        int yt = Coordinates.tileToPixel(y);
        screen.renderEntity(xt, yt , this);
    }

    @Override
    public void update() {}
}
