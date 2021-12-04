package uet.oop.bomberman.entities;

import uet.oop.bomberman.graphic.Screen;

import java.awt.*;

public class Message extends Entity {
    protected String message;
    protected int duration;
    protected Color color;
    protected int size;

    public Message(String message, int duration, Color color, int size) {
        this.message = message;
        this.duration = duration;
        this.color = color;
        this.size = size;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getMessage() {
        return message;
    }

    public int getDuration() {
        return duration;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
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
    }
}
