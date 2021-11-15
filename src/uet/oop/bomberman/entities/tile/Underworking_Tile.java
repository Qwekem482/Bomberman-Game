// Under-Working Class
// Not Complete Yet
package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.Underworking_Entity;
import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;

public abstract class Underworking_Tile extends Underworking_Entity {

    public Underworking_Tile(int x, int y, Sprite sprite) {
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
     * Under-working
     */
    /**
     * Được gọi liên tục.
     * update hình ảnh của entity
     *
     * @param screen
     */
    @Override
    public void render(Screen screen) {

    }
}
