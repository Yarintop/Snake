package snake;

import java.awt.Point;

public class CollisionLogic {

    /**
     * In a perfect screen this is redundent calculation, but we can insert more
     * complex items
     */
    public static boolean checkCollisionWithItem(Snake snake, Item item) {

        Point p = snake.getNextLocation();
        if (item.getPoint().equals(p))
            return true;
        boolean verticalFreePass = p.getY() >= item.getPoint().getY() + item.getSize()
                || p.getY() + snake.getHeadSize() <= item.getPoint().getY();
        boolean horizontalFreePass = p.getX() >= item.getPoint().getX() + item.getSize()
                || p.getX() + snake.getHeadSize() <= item.getPoint().getX();
        return !(verticalFreePass || horizontalFreePass);
    }

    public static boolean checkWallCollision(Snake snake, int width, int height) {
        Point p = snake.getNextLocation();
        return p.getX() < 0 || p.getY() < 0 || p.getX() + snake.getHeadSize() > width
                || p.getY() + snake.getHeadSize() > height;
    }

}