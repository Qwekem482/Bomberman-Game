package uet.oop.bomberman.graphic;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Tất cả sprite (hình ảnh game) được lưu trữ vào một ảnh duy nhất
 * Class này giúp lấy ra các sprite riêng từ 1 ảnh chung duy nhất đó
 * Link tham khảo: https://helpex.vn/question/lam-the-nao-de-trich-xuat-mot-phan-cua-hinh-anh-nay-trong-java-dong-cua-60ce1e67f137b0e523f87e63.
 */

public class SpriteSheet {
    public String _path;
    public final int SIZE;
    public int[] _pixels;

    public SpriteSheet(String path, int size) {
        this._path = path;
        this.SIZE = size;
        _pixels = new int[size * size];
        load();
    }

    public static SpriteSheet tiles = new SpriteSheet("/textures/classic.png", 256);

    private void load() {
        try {
            URL a = SpriteSheet.class.getResource(_path);
            BufferedImage image = ImageIO.read(a);
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, _pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
