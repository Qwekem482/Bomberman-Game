package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphic.IRender;
import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.graphic.Sprite;

/**
 * Abstract class cho các entities
 */
public abstract class Entity implements IRender {

    protected double x;
    protected double y;
    protected boolean removed = false;
    protected Sprite sprite;

    public Entity(double x, double y, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.sprite = sprite;
    }

    public Entity() {
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

    public int getXTile() {
        return Coordinates.pixelToTile(x + sprite.SIZE / 2);
    }

    public int getYTile() {
        return Coordinates.pixelToTile(y - sprite.SIZE / 2);
    }
}
