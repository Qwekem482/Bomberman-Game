package uet.oop.bomberman.entities.character;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.audio.Sound;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.FlameSegment;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.level.FileLevelLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {
    private List<Bomb> _bombs;
    protected Keyboard _input;
    private boolean _moving;

    private ArrayList<Bomb> _onBomb = new ArrayList<Bomb>();

    private int[] gapX1 = {9, 10, 9, 1};
    private int[] gapY1 = {-15, -1, 0, -1};
    private int[] gapX2 = {2, 10, 2, 1};
    private int[] gapY2 = {-15, -14, 0, -14};
    private int[] gapX3 = {2, 9, 9, 2};
    private int[] gapY3 = {-14, -14, -3, -3};
    private int[] gapX4 = {-8, -8, 8, 8};
    private int[] gapY4 = {-8, 8, -8, 8};

    protected int _timeBetweenPutBombs = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = board.getBombs();
        _input = board.getInput();
        _sprite = Sprite.player_right;
    }
}
