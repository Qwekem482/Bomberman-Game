package uet.oop.bomberman;

import uet.oop.bomberman.audio.BackgroundMusic;
import uet.oop.bomberman.audio.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Message;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.FileLevelLoader;
import uet.oop.bomberman.level.LevelLoader;
import uet.oop.bomberman.graphic.Screen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board {
    protected LevelLoader levelLoader;
    protected Game game;
    protected Keyboard _input;
    protected Screen screen;

    public Entity[] entities;
    public List<Character> characters = new ArrayList<>();
    protected List<Bomb> bombs = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();

    private int screenToShow = -1; //1:endgame, 2:changelevel, 3:paused, 4:victory

    private int time = Game.TIME;
    private int points = Game.POINTS;
    private int live = 5;

    private int previousPoints;
    private double previousBomberSpeed;
    private int previousBombRate;
    private int previousBombRadius;

    public Board(Game game, Keyboard input, Screen screen) {
        this.game = game;
        _input = input;
        this.screen = screen;
        loadLevel(1); //start in level 1
    }

    public void update() {
        if( game.isPaused() ) return;
        BackgroundMusic.playMusic();
        updateEntities();
        updateCharacters();
        updateBombs();
        updateMessages();
        detectEndGame();

        for (int i = 0; i < characters.size(); i++) {
            Character a = characters.get(i);
            if(a.isRemoved()) characters.remove(i);
        }
    }

    public void render(Screen screen) {
        if( game.isPaused() ) return;

        //only render the visible part of screen
        int x0 = Screen.xOffset >> 4; //tile precision, -> left X
        int x1 = (Screen.xOffset + screen.getWidth() + Game.TILES_SIZE) / Game.TILES_SIZE; // -> right X
        int y0 = Screen.yOffset >> 4;
        int y1 = (Screen.yOffset + screen.getHeight()) / Game.TILES_SIZE; //render one tile plus to fix black margins

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                entities[x + y * levelLoader.getWidth()].render(screen);
            }
        }

        renderBombs(screen);
        renderCharacter(screen);

    }

    public int getlive() { return this.live; }

    public void addlive(int i) { live += i; }

    public void restartLevel() {
        BackgroundMusic.stopMusic();
        points = previousPoints;
        Game.setBomberSpeed(previousBomberSpeed);
        Game.setBombRate(previousBombRate);
        Game.setBombRadius(previousBombRadius);
        loadLevel((levelLoader.getLevel()));
    }

    public void nextLevel() {
        if (levelLoader.getLevel() < Game.LEVELNUMBER)
        {
            BackgroundMusic.stopMusic();
            loadLevel(levelLoader.getLevel() + 1);
        }
        else
        {
            screenToShow = 4;
            BackgroundMusic.stopMusic();
            Sound.makeSound("Victory");
            game.resetScreenDelay();
            game.pause();
        }
    }

    public void loadLevel(int level) {
        time = Game.TIME;
        screenToShow = 2;
        game.resetScreenDelay();
        game.pause();
        characters.clear();
        bombs.clear();
        messages.clear();
        Enemy.setNumberOfEnemy(0);
        previousPoints = points;
        previousBomberSpeed = Game.getBomberSpeed();
        previousBombRate = Game.getBombRadius();
        previousBombRadius = Game.getBombRadius();

        try {
            levelLoader = new FileLevelLoader(this, level);
            entities = new Entity[levelLoader.getHeight() * levelLoader.getWidth()];

            levelLoader.createEntities();
        } catch (LoadLevelException e) {
            endGame();
        }
    }

    protected void detectEndGame() {
        if(time < 0)
        {
            Sound.makeSound("OutOfTime");
            addlive(-1);
            if (live == 0) endGame();
            else restartLevel();
        }
    }

    public void endGame() {
        screenToShow = 1;
        BackgroundMusic.stopMusic();
        Sound.makeSound("GameOver");
        game.resetScreenDelay();
        game.pause();
    }


    public void drawScreen(Graphics g) {
        switch (screenToShow) {
            case 1:
                screen.drawEndGame(g, points);
                break;
            case 2:
                screen.drawChangeLevel(g, levelLoader.getLevel());
                break;
            case 3:
                screen.drawPaused(g);
                break;
            case 4:
                screen.drawVictory(g, points);
                break;
        }
    }

    public Entity getEntity(double x, double y, Character m) {

        Entity res = null;

        res = getFlameSegmentAt((int)x, (int)y);
        if( res != null) return res;

        res = getBombAt(x, y);
        if( res != null) return res;

        res = getCharacterAtExcluding((int)x, (int)y, m);
        if( res != null) return res;

        res = getEntityAt(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y));

        return res;
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public Bomb getBombAt(double x, double y) {
        Iterator<Bomb> bs = bombs.iterator();
        Bomb b;
        while(bs.hasNext()) {
            b = bs.next();
            if(b.getX() == (int)x && b.getY() == (int)y)
                return b;
        }

        return null;
    }

    public Bomber getBomber() {
        Iterator<Character> itr = characters.iterator();

        Character cur;
        while(itr.hasNext()) {
            cur = itr.next();

            if(cur instanceof Bomber)
                return (Bomber) cur;
        }

        return null;
    }

    public Character getCharacterAtExcluding(int x, int y, Character a) {
        Iterator<Character> itr = characters.iterator();

        Character cur;
        while(itr.hasNext()) {
            cur = itr.next();
            if(cur == a) {
                continue;
            }

            if(cur.getXTile() == x && cur.getYTile() == y) {
                return cur;
            }

        }

        return null;
    }

    public FlameSegment getFlameSegmentAt(int x, int y) {
        Iterator<Bomb> bs = bombs.iterator();
        Bomb b;
        while(bs.hasNext()) {
            b = bs.next();

            FlameSegment e = b.flameAt(x, y);
            if(e != null) {
                return e;
            }
        }

        return null;
    }

    public Entity getEntityAt(double x, double y) {
        int xc = Coordinates.pixelToTile(x);
        int yc = Coordinates.pixelToTile(y);
        return entities[xc + yc * levelLoader.getWidth()];
    }

    public void addEntity(int pos, Entity e) {
        entities[pos] = e;
    }

    public void addCharacter(Character e) {
        characters.add(e);
    }

    public void addBomb(Bomb e) {
        bombs.add(e);
    }

    public void addMessage(Message e) {
        messages.add(e);
    }

    protected void renderCharacter(Screen screen) {
        Iterator<Character> itr = characters.iterator();

        while(itr.hasNext())
            itr.next().render(screen);
    }

    protected void renderBombs(Screen screen) {
        Iterator<Bomb> itr = bombs.iterator();

        while(itr.hasNext())
            itr.next().render(screen);
    }

    public void renderMessages(Graphics g) {
        Message m;
        for (int i = 0; i < messages.size(); i++) {
            m = messages.get(i);

            g.setFont(new Font("Arial", Font.PLAIN, m.getSize()));
            g.setColor(m.getColor());
            g.drawString(m.getMessage(), (int)m.getX() - Screen.xOffset  * Game.SCALE, (int)m.getY());
        }
    }

    protected void updateEntities() {
        if( game.isPaused() ) return;
        for (int i = 0; i < entities.length; i++) {
            entities[i].update();
        }
    }

    protected void updateCharacters() {
        if( game.isPaused() ) return;
        Iterator<Character> itr = characters.iterator();

        while(itr.hasNext() && !game.isPaused())
            itr.next().update();
    }

    protected void updateBombs() {
        if( game.isPaused() ) return;
        Iterator<Bomb> itr = bombs.iterator();

        while(itr.hasNext())
            itr.next().update();
    }

    protected void updateMessages() {
        if( game.isPaused() ) return;
        Message m;
        int left;
        for (int i = 0; i < messages.size(); i++) {
            m = messages.get(i);
            left = m.getDuration();

            if(left > 0)
                m.setDuration(--left);
            else
                messages.remove(i);
        }
    }

    public int subtractTime() {
        if(game.isPaused())
            return this.time;
        else
            return this.time--;
    }

    public Keyboard getInput() {
        return _input;
    }

    public LevelLoader getLevel() {
        return levelLoader;
    }

    public Game getGame() {
        return game;
    }

    public int getShow() {
        return screenToShow;
    }

    public void setShow(int i) {
        screenToShow = i;
    }

    public int getTime() {
        return time;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public int getWidth() {
        return levelLoader.getWidth();
    }

    public int getHeight() {
        return levelLoader.getHeight();
    }

}
