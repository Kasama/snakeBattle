/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiSnake;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class MultiSnake implements ActionListener, KeyListener {
    
    public JFrame jframe;
    public RenderPanel renderPanel;
    public Timer timer = new Timer(20, this);
    public static MultiSnake snake;
    public Dimension dim;
    public Cherry cherry = new Cherry();
    public int ticks = 0;//direction 0 = UP, DOWN=1, LEFT = 2, RIGHT = 3
    public static final int scale = 10;
    public static Random random;
    public boolean paused;
    public int over;
    public boolean showHeadPos;
    public int gameSpeed = 8;//the less the faster
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    public Player player1 = new Player();
    public Player player2 = new Player();
    
    public MultiSnake() {
        dim = toolkit.getScreenSize();
        jframe = new JFrame("Snake Multiplayer");
        ImageIcon icon = new ImageIcon(getClass().getResource("icon.png"));
        jframe.setIconImage(icon.getImage());
        jframe.setSize(dim.width, dim.height);
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage("Transparent.png"), new Point(0, 0), "blankCursor");//renders cursor transparent
        jframe.setCursor(cursor);
        jframe.add(renderPanel = new RenderPanel());
        jframe.addKeyListener(this);
        jframe.setExtendedState(Frame.MAXIMIZED_BOTH);
        jframe.setUndecorated(true);
        jframe.setVisible(true);//must be last jframe command
        jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        startGame();
    }
    
    public static void main(String[] args) {
        snake = new MultiSnake();
    }
    
    public void startGame() {
        over = 0;
        paused = false;
        ticks = 0;
        random = new Random();
        spawnSnakes();
        player1.ClearSnakeParts();
        player2.ClearSnakeParts();
        player1.setTailLength(7);
        player2.setTailLength(7);
        timer.start();
    }
    
    public void spawnSnakes() {//sets direction and head location of each snake
        player1.setHead(new Point(48, 53));
        player2.setHead(new Point(143, 53));
    }
    
    public void spawnCherry() {//head meets cherry
        if (player1.getHead().equals(cherry.getLocation())) {
            if (cherry.getType() == 1) {
                player1.setTeleport(true);
            }
            player1.increaseScore(10);
            player2.increaseTailLength(1);
            createCherry();
        }
        if (player2.getHead().equals(cherry.getLocation())) {
            if (cherry.getType() == 1) {
                player2.setTeleport(true);
            }
            player2.increaseScore(10);
            player1.increaseTailLength(1);
            //cherry.setLocation(random.nextInt(191), random.nextInt(107));
            createCherry();
        }
    }
    
    public void createCherry() {
        cherry.setNewLocation();
        cherry.setNewType();
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        renderPanel.repaint();
        renderPanel.repaint();
        renderPanel.repaint();
        renderPanel.repaint();
        player1.increaseTicksOnDirection();
        player2.increaseTicksOnDirection();
        //player1.increaseTicks();
        //player2.increaseTicks();
        ticks++;//old game cycle
        if (ticks % gameSpeed == 0 && player1.getHead() != null && player2.getHead() != null && over == 0 && !paused) {
            player1.accessSnakeParts().add(player2.getHead());
            if (player1.getDirection() == 0 && checkInsideOfBound() == 0 && noTailAt(player1.getHead().x, player1.getHead().y - 1)) {//PLAYER 1
                player1.setHead(new Point(player1.getHead().x, player1.getHead().y - 1));
            } else if (player1.getDirection() == 1 && checkInsideOfBound() == 0 && noTailAt(player1.getHead().x, player1.getHead().y + 1)) {
                player1.setHead(new Point(player1.getHead().x, player1.getHead().y + 1));
            } else if (player1.getDirection() == 2 && checkInsideOfBound() == 0 && noTailAt(player1.getHead().x - 1, player1.getHead().y)) {
                player1.setHead(new Point(player1.getHead().x - 1, player1.getHead().y));
            } else if (player1.getDirection() == 3 && checkInsideOfBound() == 0 && noTailAt(player1.getHead().x + 1, player1.getHead().y)) {
                player1.setHead(new Point(player1.getHead().x + 1, player1.getHead().y));
            } else {
                over = -1;
            }
            if (player1.getSnakePartsSize() > player1.getTailLength()) {
                player1.removeSnakeParts(0);
            }
        }
        if (ticks % gameSpeed == 0 && player1.getHead() != null && player2.getHead() != null && over == 0 && !paused) {
            player2.accessSnakeParts().add(player1.getHead());
            if (player2.getDirection() == 0 && checkInsideOfBound() == 0 && noTailAt(player2.getHead().x, player2.getHead().y - 1)) {//PLAYER 2
                player2.setHead(new Point(player2.getHead().x, player2.getHead().y - 1));
            } else if (player2.getDirection() == 1 && checkInsideOfBound() == 0 && noTailAt(player2.getHead().x, player2.getHead().y + 1)) {
                player2.setHead(new Point(player2.getHead().x, player2.getHead().y + 1));
            } else if (player2.getDirection() == 2 && checkInsideOfBound() == 0 && noTailAt(player2.getHead().x - 1, player2.getHead().y)) {
                player2.setHead(new Point(player2.getHead().x - 1, player2.getHead().y));
            } else if (player2.getDirection() == 3 && checkInsideOfBound() == 0 && noTailAt(player2.getHead().x + 1, player2.getHead().y)) {
                player2.setHead(new Point(player2.getHead().x + 1, player2.getHead().y));
            } else {
                over = 1;
            }
            if (player2.getSnakePartsSize() > player2.getTailLength()) {
                player2.removeSnakeParts(0);
            }
            
        }
        if (cherry != null) { //generates cherry after first one is eaten
            spawnCherry();
        }
    }
    
    
    
    @Override
    public void keyPressed(KeyEvent e) {//direction 0 = UP, DOWN=1, LEFT = 2, RIGHT = 3
        int i = e.getKeyCode();
        switch (i) {
            case KeyEvent.VK_LEFT: //Player 2
                if (player2.getDirection() != 3 && player2.getDirection() != 2) {
                    player2.setDirection(2);
                    player2.resetTicksOnDirection();
                }
                break;
            case KeyEvent.VK_UP:
                if (player2.getDirection() != 1 && player2.getDirection() != 0) {
                    player2.setDirection(0);
                    player2.resetTicksOnDirection();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (player2.getDirection() != 2 && player2.getDirection() != 3) {
                    player2.setDirection(3);
                    player2.resetTicksOnDirection();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (player2.getDirection() != 0 && player2.getDirection() != 1) {
                    player2.setDirection(1);
                    player2.resetTicksOnDirection();
                }
                break;
            
            case KeyEvent.VK_A: //Player 1
                if (player1.getDirection() != 3 && player1.getDirection() != 2) {
                    player1.setDirection(2);
                    player1.resetTicksOnDirection();
                }
                break;
            case KeyEvent.VK_W:
                if (player1.getDirection() != 1 && player1.getDirection() != 0) {
                    player1.setDirection(0);
                    player1.resetTicksOnDirection();
                }
                break;
            case KeyEvent.VK_D:
                if (player1.getDirection() != 2 && player1.getDirection() != 3) {
                    player1.setDirection(3);
                    player1.resetTicksOnDirection();
                }
                break;
            case KeyEvent.VK_S:
                if (player1.getDirection() != 0 && player1.getDirection() != 1) {
                    player1.setDirection(1);
                    player1.resetTicksOnDirection();
                }
                break;
            
            case KeyEvent.VK_SPACE:
                if (over != 0) {
                    startGame();
                } else {
                    paused = !paused;
                }
                break;
            case KeyEvent.VK_0:
                showHeadPos = !showHeadPos;
                if (gameSpeed == 10) {
                    gameSpeed = 30;
                } else {
                    gameSpeed = 10;
                }
                break;
            case KeyEvent.VK_SHIFT:
                gameSpeed = 2;
                break;
            case KeyEvent.VK_ESCAPE://exits the game
                System.exit(0);
        }
    }
    
    private int checkInsideOfBound() {
        if (player1.getHead().x > 191 || player1.getHead().x < 0 || player1.getHead().y < 0 || player1.getHead().y > 107) {
            over = -1;
        }
        if (player2.getHead().x > 191 || player2.getHead().x < 0 || player2.getHead().y < 0 || player2.getHead().y > 107) {
            over = 1;
        }
        return 0;
    }
    
    private boolean noTailAt(int x, int y) {
        if (player1.getHead().equals(player2.getHead())) {
            if (player1.getTicksOnDirection() < player2.getTicksOnDirection()) {
                over = 1;
            } else if (player1.getTicksOnDirection() > player2.getTicksOnDirection()) {
                over = -1;
            }
        }
        for (int i = 0; i < player1.getSnakePartsSize(); i++) {
            if (player1.getSnakeParts(i).equals(new Point(x, y))) {
                return false;
            }
        }
        for (int i = 0; i < player2.getSnakePartsSize(); i++) {
            if (player2.getSnakeParts(i).equals(new Point(x, y))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_SHIFT || i == KeyEvent.KEY_LOCATION_RIGHT) {
            gameSpeed = 10;
        }
    }
    
}
