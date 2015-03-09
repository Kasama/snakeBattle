/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiSnake;

import java.awt.Point;
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

    public void increaseTicks() {
        this.ticks++;
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

    public void player() {//Point head){//int direction, int score, ArrayList<Point> snakeParts, int tailLength, Point head){
        this.ticks
                = this.tailLength = 7;
        this.direction = MultiSnake.random.nextInt(3);
        this.score = 0;
        this.head = head;
        this.snakeParts = snakeParts;
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
            for (int i = 0; i < this.accessSnakeParts().size(); i++) {//teleports whole snake 60 units ahead
                if (this.getDirection() == 0) {
                    this.setHead(new Point(this.getHead().x, this.getHead().y - 60));
                    this.accessSnakeParts().set(i, new Point(this.accessSnakeParts().get(i).x, this.accessSnakeParts().get(i).y - 60));
                }
                if (this.getDirection() == 1) {
                    this.setHead(new Point(this.getHead().x, this.getHead().y + 60));
                    this.accessSnakeParts().set(i, new Point(this.accessSnakeParts().get(i).x, this.accessSnakeParts().get(i).y + 60));
                }
                if (this.getDirection() == 2) {
                    this.setHead(new Point(this.getHead().x - 60, this.getHead().y));
                    this.accessSnakeParts().set(i, new Point(this.accessSnakeParts().get(i).x - 60, this.accessSnakeParts().get(i).y));
                }
                if (this.getDirection() == 3) {
                    this.setHead(new Point(this.getHead().x + 60, this.getHead().y));
                    this.accessSnakeParts().set(i, new Point(this.accessSnakeParts().get(i).x + 60, this.accessSnakeParts().get(i).y));
                }
                this.setTeleport(false);
            }
        }
    }
}
