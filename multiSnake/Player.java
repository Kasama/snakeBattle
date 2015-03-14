/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiSnake;

import java.awt.Color;
import java.awt.Point;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Player {

    private int direction;
    private int score;
    private ArrayList<Point> snakeParts = new ArrayList<Point>();
    private int tailLength;
    private Point head;
    private int ticksOnDirection = 0;
    private int ticks = 0;
    private boolean hasTeleport = false;
    private int stamina = 0;
    private boolean isShiftPressed = false;
    private int hasLasers = 10;
    private boolean hasBomb;
    private Point bombLocation;
    private boolean bombOnMap;
    private Color bombColor;
    private int bombColorBrightness;
    private boolean colorState;
    
    public void player() {
        this.direction = 0;
        this.score = 0;
        this.head = head;
        this.snakeParts = snakeParts;
        this.ticksOnDirection = 0;
        this.colorState = true;
        this.hasBomb = false;
    }
    
    public Color getBombColor() {
        return bombColor;
    }
    
    public void dynamizeBombColorBrightness(){
        int r = bombColor.getRed();
        int g = bombColor.getGreen();
        int b = bombColor.getBlue();
        /*System.out.println(r+" "+g+" "+b);
        if(r ==255 || g == 255 || b == 255){
            colorState = true;
        }
        if(r == 0 || g == 0 || b == 0){
            colorState = false;
        }
        if(colorState){
            this.setBombColor(new Color(r-1,g-1,b-1));
        }else{
            this.setBombColor(new Color(r+1,g+1,b+1));
        }
        System.out.println(r+" "+g+" "+b);*/
        //this.setBombColor(new Color((int)abs(sin(ticks)*255),(int)abs(cos(ticks)*255),(int)abs(sin(cos(ticks)*255))));
        this.setBombColor(new Color(MultiSnake.random.nextInt(255),MultiSnake.random.nextInt(255),MultiSnake.random.nextInt(255)));
        
    }
    
    public void setBombColor(Color bombColor) {
        this.bombColor = bombColor;
    }
    public void setHasBomb(boolean hasBomb) {
        this.hasBomb = hasBomb;
    }


    public int getStamina() {
        return stamina;
    }

    public int getHasLasers() {
        return hasLasers;
    }

    public void setHasLasers(int hasLasers) {
        this.hasLasers = hasLasers;
    }
    
    public boolean getHasBomb(){
        return this.hasBomb;
    }

    public Point getBombLocation() {
        return bombLocation;
    }

    public void setBombLocation(Point bombLocation) {
        this.bombLocation = bombLocation;
    }

    public void setBombOnMap(boolean bombOnMap) {
        this.bombOnMap = bombOnMap;
    }
    
    public boolean getBombOnMap(){
        return this.bombOnMap;
    }
    
    public void deployBomb(){
        if(this.getHasBomb() && !this.getBombOnMap()){
            this.setBombLocation(new Point(this.accessSnakeParts().get(0).x, this.accessSnakeParts().get(0).y));
        }
    }

    public void run() {
        if (this.getStamina() > 0) {
            if (this.getDirection() == 0 && this.getTicks() % 4 == 0) {
                this.setHead(new Point(this.getHead().x, this.getHead().y - 1));
                for (int j = 0; j < this.accessSnakeParts().size(); j++) {
                    this.accessSnakeParts().set(j, new Point(this.accessSnakeParts().get(j).x, this.accessSnakeParts().get(j).y - 1));
                }
            }
            if (this.getDirection() == 1 && this.getTicks() % 4 == 0) {
                this.setHead(new Point(this.getHead().x, this.getHead().y + 1));
                for (int j = 0; j < this.accessSnakeParts().size(); j++) {
                    this.accessSnakeParts().set(j, new Point(this.accessSnakeParts().get(j).x, this.accessSnakeParts().get(j).y + 1));
                }
            }
            if (this.getDirection() == 2 && this.getTicks() % 4 == 0) {
                this.setHead(new Point(this.getHead().x - 1, this.getHead().y));
                for (int j = 0; j < this.accessSnakeParts().size(); j++) {
                    this.accessSnakeParts().set(j, new Point(this.accessSnakeParts().get(j).x - 1, this.accessSnakeParts().get(j).y));
                }
            }
            if (this.getDirection() == 3 && this.getTicks() % 4 == 0) {
                this.setHead(new Point(this.getHead().x + 1, this.getHead().y));
                for (int j = 0; j < this.accessSnakeParts().size(); j++) {
                    this.accessSnakeParts().set(j, new Point(this.accessSnakeParts().get(j).x + 1, this.accessSnakeParts().get(j).y));
                }
            }
            this.decreaseStamina(1);
        }
    }

    public void setIsShiftPressed(boolean isShiftPressed) {
        this.isShiftPressed = isShiftPressed;
    }
    
    public boolean getIsShiftPressed(){
        return this.isShiftPressed;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public void increaseStamina(int amount) {
        this.stamina += amount;
    }

    public void decreaseStamina(int amount) {
        this.stamina -= amount;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public void setTeleport(boolean bool) {
        this.hasTeleport = bool;
    }

    public boolean getTeleport() {
        return this.hasTeleport;
    }

    public void moveSnake(int dir) {
        if (dir == 0) {
            if (this.getDirection() != 1 && this.getDirection() != 0) {
                this.setDirection(0);
                this.resetTicksOnDirection();
            }
        }
        if (dir == 1) {
            if (this.getDirection() != 0 && this.getDirection() != 1) {
                this.setDirection(1);
                this.resetTicksOnDirection();
            }
        }
        if (dir == 2) {
            if (this.getDirection() != 3 && this.getDirection() != 2) {
                this.setDirection(2);
                this.resetTicksOnDirection();
            }
        }
        if (dir == 3) {
            if (this.getDirection() != 2 && this.getDirection() != 3) {
                this.setDirection(3);
                this.resetTicksOnDirection();
            }
        }
    }

    public void increaseTicks(int amount) {
        this.ticks += amount;
    }

    public int getTicksOnDirection() {
        return ticksOnDirection;
    }

    public void increaseTicksOnDirection() {
        this.ticksOnDirection++;
    }

    public void resetTicksOnDirection() {
        this.ticksOnDirection = 0;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {
        this.score += score;
    }

    public ArrayList<Point> accessSnakeParts() {
        return this.snakeParts;
    }

    public Point getSnakeParts(int index) {
        return snakeParts.get(index);
    }

    public void ClearSnakeParts() {
        this.snakeParts.clear();
    }

    public void removeSnakeParts(int index) {
        this.snakeParts.remove(index);
    }

    public void addSnakeParts() {
        this.snakeParts.add(this.getHead());
    }

    public int getSnakePartsSize() {
        return this.snakeParts.size();
    }

    public int getTailLength() {
        return tailLength;
    }

    public void setTailLength(int tailLength) {
        this.tailLength = tailLength;
    }

    public void increaseTailLength(int index) {
        this.tailLength += index;
    }

    public Point getHead() {
        return head;
    }

    public void setHead(Point head) {
        this.head = head;
    }

    public void teleportAhead() {
        if (this.getTeleport()) {
            if (this.getDirection() == 0) {//up
                this.setHead(new Point(this.getHead().x, this.getHead().y - 10));
            }

            if (this.getDirection() == 1) {//down
                this.setHead(new Point(this.getHead().x, this.getHead().y + 10));
            }

            if (this.getDirection() == 2) {//left
                this.setHead(new Point(this.getHead().x - 10, this.getHead().y));
            }

            if (this.getDirection() == 3) {//right
                this.setHead(new Point(this.getHead().x + 10, this.getHead().y));
            }
            this.ClearSnakeParts();
            for (int i = 0; i < this.accessSnakeParts().size(); i++) {//teleports whole snake 30 units ahead
                if (this.getDirection() == 0) {
                    this.accessSnakeParts().set(i, new Point(this.accessSnakeParts().get(i).x, this.accessSnakeParts().get(i).y - 10));
                }
                if (this.getDirection() == 1) {
                    this.accessSnakeParts().set(i, new Point(this.accessSnakeParts().get(i).x, this.accessSnakeParts().get(i).y + 10));
                }
                if (this.getDirection() == 2) {
                    this.accessSnakeParts().set(i, new Point(this.accessSnakeParts().get(i).x - 10, this.accessSnakeParts().get(i).y));
                }
                if (this.getDirection() == 3) {
                    this.accessSnakeParts().set(i, new Point(this.accessSnakeParts().get(i).x + 10, this.accessSnakeParts().get(i).y));
                }
                this.setTeleport(false);
            }
        }

    }

}
