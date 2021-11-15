package uet.oop.bomberman.entities.tile.item;

import uet.oop.bomberman.entities.tile.Underworking_Tile;
import uet.oop.bomberman.graphic.Sprite;

public abstract class Item extends Underworking_Tile {
    public Item(int x, int y, Sprite sprite) {
        super(x, y, sprite);
    }
}
