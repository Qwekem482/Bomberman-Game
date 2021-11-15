package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphic.Sprite;

/**
 * Abstract class cho các animated entities
 */
public abstract class AnimatedEntity extends Underworking_Entity {
    protected int animate = 0;
    protected final int MAX_ANIMATE = 7500;

    public AnimatedEntity(double x, double y, Sprite sprite) {
        super(x, y, sprite);
    }

    public AnimatedEntity() {
    }

    protected void animate() {
        if(animate < MAX_ANIMATE) {
            animate++;
        } else {
            animate = 0;
        }
    }
}
