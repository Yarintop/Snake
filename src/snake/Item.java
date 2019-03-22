package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Item {
    private Point point;
    private int size;

    public Item(int x, int y, int size) {
        point = new Point(x, y);
        this.size = size;
    }

    public Item(Point p, int size) {
        this((int) p.getX(), (int) p.getY(), size);
    }

    public int getSize() {
        return size;
    }

    public Point getPoint() {
        return point;
    }

    public void draw(Graphics g) {
        Color tmp = g.getColor();
        g.setColor(Color.RED);
        g.fillRect((int) point.getX(), (int) point.getY(), size, size);
        g.setColor(tmp);
    }

}