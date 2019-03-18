package snake;

// import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener {

    public static Snake snake;
    public JFrame jframe;
    public Timer timer = new Timer(20, this);
    public Dimension dim;
    public final int SCALE = 25;
    public RenderPanel renderPanel;
    public ArrayList<Point> snakeParts;
    public int tailLength;
    public Point head;
    public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
    public int ticks = 0, direction, currDirection, score, speed;
    public Point cherry;
    public Random r;
    public boolean over, paused, changedDirection;

    public Snake() {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Snake");
        jframe.setSize(805, 703);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.addKeyListener(this);
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.add(renderPanel = new RenderPanel());
        startGame();
        timer.start();
    }

    public void startGame() {
        head = new Point(0, 0);
        direction = DOWN;
        score = 0;
        tailLength = 1;
        r = new Random();
        snakeParts = new ArrayList<Point>();
        cherry = new Point((int) (r.nextInt(jframe.getWidth() - SCALE * 2) / SCALE) * SCALE,
                (int) (r.nextInt(jframe.getHeight() - SCALE * 2) / SCALE) * SCALE);
        over = false;
        paused = false;
        changedDirection = false;
        speed = 5;
    }

    public static void main(String[] args) {
        snake = new Snake();
    }

    public void actionPerformed(ActionEvent e) {
        renderPanel.repaint();
        ticks++;

        if (ticks % speed == 0 && head != null && !over && !paused) {
            changedDirection = true;
            System.out.println(head.x + ", " + head.y + " || " + cherry.x + ", " + cherry.y);
            if (direction == UP) {
                if (head.getY() - SCALE >= 0 && !collision(new Point(head.x, head.y - SCALE))) {
                    snakeParts.add(new Point(head.x, head.y - SCALE));
                    head.y -= SCALE;
                    currDirection = UP;
                } else {
                    over = true;
                }
            }
            if (direction == DOWN) {
                if (head.getY() + SCALE < jframe.getHeight() - SCALE - 3
                        && !collision(new Point(head.x, head.y + SCALE))) {
                    snakeParts.add(new Point(head.x, head.y + SCALE));
                    head.y += SCALE;
                    currDirection = DOWN;
                } else {
                    over = true;
                }
            }
            if (direction == LEFT) {
                if (head.getX() - SCALE >= 0 && !collision(new Point(head.x - SCALE, head.y))) {
                    snakeParts.add(new Point(head.x - SCALE, head.y));
                    head.x -= SCALE;
                    currDirection = LEFT;
                } else {
                    over = true;
                }
            }
            if (direction == RIGHT) {
                if (head.getX() + SCALE <= jframe.getWidth() - SCALE - 5
                        && !collision(new Point(head.x + SCALE, head.y))) {
                    snakeParts.add(new Point(head.x + SCALE, head.y));
                    head.x += SCALE;
                    currDirection = RIGHT;
                } else {
                    over = true;
                }
            }
            if (snakeParts.size() > tailLength) {
                snakeParts.remove(0);
            }
            if (cherry != null) {
                if (head.equals(cherry)) {
                    tailLength++;
                    score++;
                    if (speed - 1 > 0) {
                        if (score % 5 == 0) {
                            speed--;
                        }
                    }
                    cherry.setLocation((int) (r.nextInt(jframe.getWidth() - SCALE * 2) / SCALE) * SCALE,
                            (int) (r.nextInt(jframe.getHeight() - SCALE * 2) / SCALE) * SCALE);
                }
            }
        }
    }

    public boolean collision(Point p) {
        for (Point point : snakeParts) {
            if (p.equals(point)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if ((i == KeyEvent.VK_UP || i == KeyEvent.VK_W) && currDirection != DOWN) {
            direction = UP;
        }
        if ((i == KeyEvent.VK_DOWN || i == KeyEvent.VK_S) && currDirection != UP) {
            direction = DOWN;
        }
        if ((i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A) && currDirection != RIGHT) {
            direction = LEFT;
        }
        if ((i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D) && currDirection != LEFT) {
            direction = RIGHT;
        }
        if (i == KeyEvent.VK_SPACE) {
            if (!over) {
                paused = !paused;
            } else {
                startGame();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}