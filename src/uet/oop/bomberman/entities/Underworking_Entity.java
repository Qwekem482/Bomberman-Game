// Under-Working Class
// Not Complete Yet
// 6/8 method.
package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphic.IRender;
import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;

/**
 * Abstract class cho các entities
 */
public abstract class Underworking_Entity implements IRender {

    protected double x;
    protected double y;
    protected boolean removed = false;
    protected Sprite sprite;

    public Underworking_Entity(double x, double y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public Underworking_Entity() {
    }

    public boolean isRemove() {
        return removed;
    }

    public void remove() {
        this.removed = true;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Được gọi liên tục.
     * update trạng thái entity và xử lý event
     */
    @Override
    public abstract void update();

    /**
     * Được gọi liên tục.
     * update hình ảnh của entity
     */
    @Override
    public abstract void render(Screen screen);

    /**
     * Under-Working
     * @return
     */
    public int getXTile() {
        return 0;
    }

    /**
     * Under-Working
     * @return
     */
    public int getYTile() {
        return 0;
    }
}
