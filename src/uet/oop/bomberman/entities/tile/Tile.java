package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;

public abstract class Tile extends Entity {

    public Tile(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }

    /**
     * Được gọi liên tục.
     * update trạng thái entity và xử lý event
     */
    @Override
    public void update() {
    }

    /**
     * Được gọi liên tục.
     * update hình ảnh của entity
     */
    @Override
    public void render(Screen screen) {
        screen.renderEntity(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y), this);
    }
}
