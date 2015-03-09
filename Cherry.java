/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiSnake;

import java.awt.Point;
import java.util.Random;

public class Cherry {
    public Random random = new Random();
    private int type = 0;//MultiSnake.random.nextInt(3);
    private Point location = new Point(random.nextInt(191), random.nextInt(107));
    public void Cherry(){
        
    }
    
    public void Cherry(int type){
        this.type = type;
    }

    public int getType() {
        return type;
    }
    
    public int resetType(){//change cherry type
        int prob;
        prob = random.nextInt(100);//final 100%(100)
        if(prob<=40){//40% chance
            return 0;
        }
        if(prob>40 && prob<=60){//20%
            return 1;
        }
        if(prob>60 && prob<=80){//20%
            return 2;
        }
        if(prob>80){//20%
            return 3;
        }
        return 0;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
    
    public void setNewLocation(){
        this.location = new Point(MultiSnake.random.nextInt(191),MultiSnake.random.nextInt(107));
    }
    
    public void setNewType(){
        this.type = MultiSnake.random.nextInt(3);
    }
    
}
