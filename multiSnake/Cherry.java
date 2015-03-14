package multiSnake;

import java.awt.Point;
import java.util.Random;

public class Cherry {
    public Random random = new Random();
    private int type = 0;//MultiSnake.random.nextInt(3);
    private Point location = new Point(random.nextInt(MultiSnake.screenDim.width/MultiSnake.scale)-1,random.nextInt(MultiSnake.screenDim.height/MultiSnake.scale)-1);
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
        if(prob>40 && prob<=100){//20% (teleport)
            return 1;
        }
        if(prob>60 && prob<=80){//20% (bomb)
            return 2;
        }
        /*if(prob>80){//20%
            return 3;
        }*/
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
        this.location = new Point(MultiSnake.random.nextInt(MultiSnake.screenDim.width/MultiSnake.scale)-1,MultiSnake.random.nextInt(MultiSnake.screenDim.height/MultiSnake.scale)-1);//191,107
    }
    
    public void setNewType(){
        this.type = MultiSnake.random.nextInt(3);
    }
    
}
