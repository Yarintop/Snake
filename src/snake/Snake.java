package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

public class Snake {

    public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;

    private int size;
    private int dx;
    private int dy;

    public ArrayList<Point> snakeParts;
    public int tailLength;
    public Point head;
    public int direction, currDirection;

    public Snake(int x, int y, int size) {
        head = new Point(x, y);
        direction = DOWN;
        dy = size;
        tailLength = 1;
        snakeParts = new ArrayList<Point>();
        this.size = size;

    }

    public int getHeadSize() {
        return size;
    }

    private boolean checkSelfCollision() {
        for (Point bodyPart : snakeParts)
            if (getNextLocation().equals(bodyPart))
                return true;
        return false;
    }

    public void eatCherry() {
        tailLength++;

    }

    public boolean updateSnake() {
        if (checkSelfCollision())
            return false;
        snakeParts.add(new Point(head.x + dx, head.y + dy));
        head.x += dx;
        head.y += dy;
        direction = currDirection;
        if (snakeParts.size() > tailLength) {
            snakeParts.remove(0);
        }
        return true;
    }

    public Point getNextLocation() {
        return new Point(head.x + dx, head.y + dy);
    }

    public boolean collision(Point p) {
        for (Point point : snakeParts) {
            if (p.equals(point)) {
                return true;
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        Color tmp = g.getColor();
        g.setColor(Color.white);
        for (Point p : snakeParts)
            g.fillRect((int) p.getX(), (int) p.getY(), size, size);
        g.fillRect((int) head.getX(), (int) head.getY(), size, size);
        g.setColor(tmp);
    }

    public void setDirection(int direction) {
        if (this.direction <= DOWN && direction <= DOWN || this.direction > DOWN && direction > DOWN)
            return;
        switch (direction) {
        case UP:
            dx = 0;
            dy = -size;
            break;
        case DOWN:
            dx = 0;
            dy = size;
            break;
        case RIGHT:
            dx = size;
            dy = 0;
            break;
        case LEFT:
            dy = 0;
            dx = -size;
            break;
        }
        currDirection = direction;
    }

}