package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item {
    private Point point;
    private int size;
    private int r = 1;
    private int gr = 1;
    private int b = 1;
    private BufferedImage image;

    public Item(int x, int y, int size) {
        point = new Point(x, y);
        this.size = size;
        try {
            image = ImageIO.read(new File("src/res/cherry2.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
        r = (r * 5) % 254;
        gr = (gr * 2) % 255;
        b = (b * 3) % 254;
        g.setColor(new Color(r, gr, b));
        // g.fillRect((int) point.getX(), (int) point.getY(), size, size);
        g.drawImage(image, (int) point.getX(), (int) point.getY(), size, size, null);
        g.setColor(tmp);
    }

}