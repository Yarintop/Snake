package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Font;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 700);
        Snake snake = Snake.snake;
        g.setColor(Color.WHITE);
        g.setFont(new Font("CooperBlack", Font.PLAIN, 15));
        for (Point point : snake.snakeParts) {
            g.fillRect(point.x, point.y, snake.SCALE, snake.SCALE);
        }
        g.fillRect(snake.head.x, snake.head.y, snake.SCALE, snake.SCALE);
        g.setColor(Color.RED);
        g.fillRect(snake.cherry.x, snake.cherry.y, snake.SCALE, snake.SCALE);
        g.setColor(Color.BLUE);
        g.drawString("Score:" + String.valueOf(snake.score), 0, 15);
    }

}