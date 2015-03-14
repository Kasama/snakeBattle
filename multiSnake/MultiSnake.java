/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiSnake;

import java.awt.Color;
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
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.Timer;

public class MultiSnake implements ActionListener, KeyListener {

    public JFrame jframe;
    public RenderPanel renderPanel;
    public Timer timer = new Timer(5, this);
    public static MultiSnake snake;
    public Cherry cherry = new Cherry();
    public int ticks = 0;//direction 0 = UP, DOWN=1, LEFT = 2, RIGHT = 3
    public static final int scale = 10;
    public static Random random;
    public boolean paused;
    public int over;
    public boolean showAllPos;
    public int gameSpeed = 10;//the less the faster
    static Toolkit toolkit = Toolkit.getDefaultToolkit();
    public static Dimension screenDim = toolkit.getScreenSize();
    public Player player1 = new Player();
    public Player player2 = new Player();

    public MultiSnake() {
        jframe = new JFrame("Snake Multiplayer");
        ImageIcon icon = new ImageIcon(getClass().getResource("icon.png"));
        jframe.setIconImage(icon.getImage());
        jframe.setSize(screenDim.width, screenDim.height);
        Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage("Transparent.png"), new Point(0, 0), "blankCursor");//renders cursor transparent
        jframe.setCursor(cursor);
        jframe.add(renderPanel = new RenderPanel());
        jframe.addKeyListener(this);
        //jframe.setExtendedState(Frame.MAXIMIZED_BOTH);
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
        timer.start();
    }

    public void spawnSnakes() {//sets direction and head location of each snake
        player1.setHead(new Point((screenDim.width / scale - 1) / 2, (screenDim.height / scale - 1) / 2));//48,45
        player2.setHead(new Point((screenDim.width / scale - 1) / 2 + 1, (screenDim.height / scale - 1) / 2));//48,45
        player1.setTailLength(10);
        player2.setTailLength(10);
        player1.setTicks(0);
        player2.setTicks(0);
        player1.setDirection(0);
        player2.setDirection(0);
        player1.ClearSnakeParts();
        player2.ClearSnakeParts();
        player1.setStamina(750);
        player2.setStamina(750);
        player1.setBombColor(RenderPanel.newOrange);
        player2.setBombColor(Color.RED);
//        player1.setBombOnMap(false);
//        player2.setBombOnMap(false);
        player1.setTeleport(false);
        player2.setTeleport(false);
    }
    
    public void spawnCherry() {//head meets cherry
        if (player1.getHead().equals(cherry.getLocation())) {
            if (cherry.getType() == 1) {
                player1.setTeleport(true);
            }
            if (cherry.getType() == 2){
                player1.setHasBomb(true);
            }
            player1.increaseScore(10);
            player1.increaseTailLength(1);
            player1.increaseStamina(50);
            createCherry();
        }
        if (player2.getHead().equals(cherry.getLocation())) {
            if (cherry.getType() == 1) {
                player2.setTeleport(true);
            }
            if (cherry.getType() == 2){
                player2.setHasBomb(true);
            }
            player2.increaseScore(10);
            player2.increaseTailLength(1);
            player2.increaseStamina(50);
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
        player1.increaseTicksOnDirection();
        player2.increaseTicksOnDirection();
        player1.increaseTicks(7);
        player2.increaseTicks(7);
        if (player1.getTicks() % gameSpeed == 0 && player1.getHead() != null && player2.getHead() != null && over == 0 && !paused) {
            player1.accessSnakeParts().add(player1.getHead());
            if(player1.getHead().equals(player2.getBombLocation())){
                player1.decreaseStamina(50);
                player2.setBombOnMap(false);
            }
            if(player1.getIsShiftPressed())
                player1.run();
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
        if (player2.getTicks() % gameSpeed == 0 && player1.getHead() != null && player2.getHead() != null && over == 0 && !paused) {
            player2.accessSnakeParts().add(player2.getHead());
            if(player2.getHead().equals(player1.getBombLocation())){
                player2.decreaseStamina(50);
                player1.setBombOnMap(false);
            }
            if(player2.getHead().equals(player1.getBombLocation()) || player2.getHead().equals(player2.getBombLocation())){
                player2.decreaseStamina(50);
                player2.setBombOnMap(false);
            }
            if(player2.getIsShiftPressed())
                player2.run();
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


    private int checkInsideOfBound() {
        if (player1.getHead().x > screenDim.width / scale - 1 || player1.getHead().x < 0 || player1.getHead().y < 0 || player1.getHead().y > screenDim.height / scale - 1) {
            System.out.println("player 1 out of bound");
            over = -1;
        }
        if (player2.getHead().x > screenDim.width / scale - 1 || player2.getHead().x < 0 || player2.getHead().y < 0 || player2.getHead().y > screenDim.height / scale - 1) {
            System.out.println("player 2 out of bound");
            System.out.println(screenDim.width + "" + screenDim.height);
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
    public void keyPressed(KeyEvent e) {//direction 0 = UP, DOWN=1, LEFT = 2, RIGHT = 3//INSTEAD CHECK IF KEY IS DOWN
        int i = e.getKeyCode();
        int keyLocation = e.getKeyLocation();
        switch (i) {
            case KeyEvent.VK_UP:
                player2.moveSnake(0);
                break;
            case KeyEvent.VK_DOWN:
                player2.moveSnake(1);
                break;
            case KeyEvent.VK_LEFT: //Player 2
                player2.moveSnake(2);
                break;
            case KeyEvent.VK_RIGHT:
                player2.moveSnake(3);
                break;
            case KeyEvent.VK_A: //Player 1
                player1.moveSnake(2);
                break;
            case KeyEvent.VK_W:
                player1.moveSnake(0);
                break;
            case KeyEvent.VK_D:
                player1.moveSnake(3);
                break;
            case KeyEvent.VK_S:
                player1.moveSnake(1);
                break;

            case KeyEvent.VK_SPACE:
                if (over != 0) {
                    startGame();
                } else {
                    paused = !paused;
                }
                break;
            case KeyEvent.VK_0:
                showAllPos = !showAllPos;
                if(gameSpeed == 10)
                    gameSpeed = 282;
                else
                    gameSpeed = 10;
                break;
            case KeyEvent.VK_SHIFT:
                if (2 == keyLocation) {
                    player1.run();
                }
                if (3 == keyLocation) {
                    player2.run();
                }
                break;
            case KeyEvent.VK_1:
                player1.teleportAhead();
                break;
            case KeyEvent.VK_2:
                player1.deployBomb();
                player1.setBombOnMap(true);
                break;
            case KeyEvent.VK_PERIOD:
                player2.deployBomb();
                player2.setBombOnMap(true);
                break;
            case KeyEvent.VK_SEMICOLON:
                player2.teleportAhead();
                break;
            case KeyEvent.VK_ESCAPE://exits the game
                System.exit(0);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        int keyLocation = e.getKeyLocation();
         if (i == KeyEvent.VK_SHIFT) {
            if(keyLocation == 2)
                player1.setIsShiftPressed(false);
            if(keyLocation == 3)
                player2.setIsShiftPressed(false);
        }
    }

}
