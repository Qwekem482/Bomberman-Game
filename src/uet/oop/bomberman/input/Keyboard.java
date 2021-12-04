package uet.oop.bomberman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
    private boolean[] key = new boolean[120];
    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;
    public boolean space;

    public void update() {
        up = key[KeyEvent.VK_UP] || key[KeyEvent.VK_W];
        down = key[KeyEvent.VK_DOWN] || key[KeyEvent.VK_S];
        left = key[KeyEvent.VK_LEFT] || key[KeyEvent.VK_A];
        right = key[KeyEvent.VK_RIGHT] || key[KeyEvent.VK_D];
        space = key[KeyEvent.VK_SPACE] || key[KeyEvent.VK_X];
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        key[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        key[e.getKeyCode()] = false;
    }
}
