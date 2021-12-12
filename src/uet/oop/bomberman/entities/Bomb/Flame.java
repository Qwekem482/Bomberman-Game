package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.level.Coordinates;
import uet.oop.bomberman.graphic.Screen;

public class Flame extends Entity {
    protected Board _board;
    protected int _direction;
    protected int xOrigin, yOrigin;
    protected FlameSegment[] _flameSegments = new FlameSegment[0];

    private final int[] gapX = {0, 1, 0, -1};
    private final int[] gapY = {-1, 0, 1, 0};

    /**
     *
     * @param x hoành độ bắt đầu của Flame
     * @param y tung độ bắt đầu của Flame
     * @param direction là hướng của Flame
     * @param radius độ dài cực đại của Flame
     */
    public Flame(int x, int y, int direction, int radius, Board board) {
        xOrigin = x;
        yOrigin = y;
        this.x = x;
        this.y = y;
        _direction = direction;
        _board = board;
        createFlameSegments();
    }

    /**
     * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
     */
    private void createFlameSegments() {
        /**
         * tính toán độ dài Flame, tương ứng với số lượng segment
         */
        int radius = calculatePermitedDistance();
        _flameSegments = new FlameSegment[radius];
        for (int i = 1; i <= radius; i++) {
            Entity entity = _board.getEntityAt(Coordinates.tileToPixel(this.x + i*gapX[_direction]), Coordinates.tileToPixel(this.y + i*gapY[_direction]));
            if (entity instanceof LayeredEntity layeredEntity) {
                entity = layeredEntity.getTopEntity();
                if (entity instanceof Item)
                {
                    entity.remove();
                    layeredEntity.update();
                }
                else
                if (entity instanceof Brick) ((Brick) entity).destroy();
            }
            _flameSegments[i - 1] = new FlameSegment((int) this.x + i * gapX[_direction], (int) this.y + i * gapY[_direction], _direction, (i == radius));
        }
    }

    /** Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn */
    private int calculatePermitedDistance() {
        int radius = Game.getBombRadius();
        for (int i = 1; i <= radius; i++) {

            Entity entity = _board.getEntityAt((this.x + i*gapX[_direction])*Game.TILES_SIZE, (this.y + i*gapY[_direction])*Game.TILES_SIZE );
            if (entity instanceof LayeredEntity)
                if (((LayeredEntity) entity).getTopEntity() instanceof Brick) return i;
                else
                if(((LayeredEntity) entity).getTopEntity() instanceof Portal) return i - 1;
            if (entity instanceof Wall) {
                return i - 1;
            }
        }
        return radius;
    }

    public FlameSegment flameSegmentAt(int x, int y) {
        for (FlameSegment flameSegment : _flameSegments) {
            if (flameSegment.getX() == x && flameSegment.getY() == y)
                return flameSegment;
        }
        return null;
    }

    @Override
    public void update() {}

    @Override
    public void render(Screen screen) {
        for (FlameSegment flameSegment : _flameSegments) {
            flameSegment.render(screen);
        }
    }
}
