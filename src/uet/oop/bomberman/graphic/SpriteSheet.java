package uet.oop.bomberman.graphic;


public class SpriteSheet {
    public String path;
    public int Size;
    public int[] pixels;

    public SpriteSheet(String s, int i) {
        this.path = s;
        this.Size = i;
        pixels = new int[Size * Size];
    }

    public static SpriteSheet Picture = new SpriteSheet("/textures/classic.png", 256);


}
