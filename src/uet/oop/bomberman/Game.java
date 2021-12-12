package uet.oop.bomberman;

import uet.oop.bomberman.graphic.Screen;
import uet.oop.bomberman.gui.Frame;
import uet.oop.bomberman.input.Keyboard;


import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas {
    public static final int TILES_SIZE = 16;
    public static final int WIDTH = TILES_SIZE * (31 / 2);
    public static final int HEIGHT = 13 * TILES_SIZE;
    public static final int LEVELNUMBER = 5;

    public static int SCALE = 3;
    public static final String title = "BOMBERMAN GAME";

    private static final int BOMBRATE = 1;
    private static final int BOMBRADIUS = 1;
    private static final double BOMBERSPEED = 1.0;

    public static final int TIME = 120;
    public static final int POINTS = 0;

    protected static int SCREENDELAY = 3;

    protected static int bombRate = BOMBRATE;
    protected static int bombRadius = BOMBRADIUS;
    protected static double bomberSpeed = BOMBERSPEED;

    protected int _screenDelay = SCREENDELAY;

    private final Keyboard input;
    private boolean paused = true;

    private Board board;
    private Screen screen;
    private Frame frame;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game(Frame frame) {
        this.frame = frame;
        frame.setTitle(title);

        screen = new Screen(WIDTH, HEIGHT);
        input = new Keyboard();

        board = new Board(this, input, screen);
        addKeyListener(input);
    }


    private void renderGame() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        board.render(screen);

        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        board.renderMessages(g);
        g.dispose();
        bs.show();
    }

    private void renderScreen() {
        BufferStrategy bs = getBufferStrategy();
        if(bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();
        Graphics g = bs.getDrawGraphics();
        board.drawScreen(g);
        g.dispose();
        bs.show();
    }

    private void update() {
        input.update();
        board.update();
    }

    public void start() {
        boolean running = true;
        long  lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0; //nanosecond, 60 frames per second
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();
        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1) {
                update();
                updates++;
                delta--;
            }

            if(paused) {
                if(_screenDelay <= 0) {
                    board.setShow(-1);
                    paused = false;
                }

                renderScreen();
            } else {
                renderGame();
            }


            frames++;
            if(System.currentTimeMillis() - timer > 1000) {
                frame.setTime(board.subtractTime());
                frame.setPoints(board.getPoints());
                timer += 1000;
                frame.setTitle(title + " | " + updates + " rate, " + frames + " fps");
                updates = 0;
                frames = 0;

                if(board.getShow() == 2)
                    _screenDelay--;
            }
        }
    }

    public static void setBomberSpeed(double bomberSpeed) {
        Game.bomberSpeed = bomberSpeed;
    }

    public static double getBomberSpeed() {
        return bomberSpeed;
    }

    public static void setBombRate(int bombRate) {
        Game.bombRate = bombRate;
    }

    public static int getBombRate() {
        return bombRate;
    }

    public static void setBombRadius(int bombRadius) {
        Game.bombRadius = bombRadius;
    }

    public static int getBombRadius() {
        return bombRadius;
    }

    public static void addBomberSpeed(double i) {
        bomberSpeed += i;
    }

    public static void addBombRadius(int i) {
        bombRadius += i;
    }

    public static void addBombRate(int i) {
        bombRate += i;
    }

    public void resetScreenDelay() {
        _screenDelay = SCREENDELAY;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isPaused() {
        return paused;
    }

    public void pause() {
        paused = true;
    }
}
