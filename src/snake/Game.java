package snake;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable, KeyListener {
    final static long serialVersionUID = 1L;

    private final int WIDTH;
    private final int HEIGHT;

    private boolean isRunning = false;
    private boolean isPaused;
    private boolean isGameOver;
    private int score = 0;

    private Thread gameThread;
    private int updateSpeed = 200; // update speed in milliseconds
    private int standardSize = 25;

    private Snake snake;
    private Item cherryItem;

    public Game(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        gameThread = new Thread(this);
        addKeyListener(this);
        initGame();

    }

    public void initGame() {
        snake = new Snake(10 * standardSize, 10 * standardSize, standardSize); // should be less hard coded
        isGameOver = false;
        score = 0;
        updateSpeed = 200;
        respawnCherry();
    }

    private void respawnCherry() {
        Random r = new Random();
        Point cherryPoint = new Point((int) (r.nextInt(WIDTH - standardSize * 2) / standardSize) * standardSize,
                (int) (r.nextInt(HEIGHT - standardSize * 2) / standardSize) * standardSize);
        cherryItem = new Item(cherryPoint, standardSize);
    }

    public void gameStart() {
        isRunning = true;
        gameThread.start();
    }

    public void gameStop() {
        isRunning = false;
        try {
            gameThread.join();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Could not stop thread");
        }
    }

    public void pauseGame() {
        isPaused = !isPaused;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_W:
            snake.setDirection(Snake.UP);
            break;
        case KeyEvent.VK_DOWN:
        case KeyEvent.VK_S:
            snake.setDirection(Snake.DOWN);
            break;
        case KeyEvent.VK_LEFT:
        case KeyEvent.VK_A:
            snake.setDirection(Snake.LEFT);
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_D:
            snake.setDirection(Snake.RIGHT);
            break;
        case KeyEvent.VK_SPACE:
            if (isGameOver)
                initGame();
            else
                pauseGame();
            break;
        }
    }

    @Override
    public void run() {
        while (isRunning) {
            draw();
            updateGame();
            try {
                Thread.sleep(updateSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        if (isPaused || isGameOver)
            return;
        if (CollisionLogic.checkCollisionWithItem(snake, cherryItem)) {
            snake.eatCherry();
            respawnCherry();
            score++;
            if (score % 4 == 0 && updateSpeed > 41)
                updateSpeed -= 40;
        }
        if (CollisionLogic.checkWallCollision(snake, WIDTH, HEIGHT)) {
            isGameOver = true;
            return;
        }

        if (!snake.updateSnake())
            isGameOver = true;
    }

    public void draw() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            return;
        }

        // Drawing logic
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(Color.PINK);
        g.fillRect(0, 0, getWidth(), getHeight());
        cherryItem.draw(g);
        snake.draw(g);
        g.setColor(Color.BLUE);
        g.drawString("Score: " + score, 50, 30);
        //

        bs.show();
        g.dispose();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

}