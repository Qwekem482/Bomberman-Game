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

    private Keyboard input;
    private boolean running = false;
    private boolean paused = true;

    private Board board;
    private Screen screen;
    private Frame frame;

    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();

    public Game(Frame frame) {
        frame = frame;
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

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.pixels[i];
        }

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
}
