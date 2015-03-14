/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiSnake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import static multiSnake.MultiSnake.snake;

@SuppressWarnings("serial")
public class RenderPanel extends JPanel {
    public static Color newOrange = new Color(255,106,0);
    public static Color newPink = new Color(255,0,238);
    
    @Override
    protected void paintComponent(Graphics g) {
        //initialize frame
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, snake.jframe.getSize().width, snake.jframe.getSize().height);
        //snake body rendering
        g.setColor(newOrange);
        for(Point point : snake.player1.accessSnakeParts()){
            g.fillRect(point.x * MultiSnake.scale, point.y * MultiSnake.scale, MultiSnake.scale, MultiSnake.scale);
        }
        g.setColor(Color.red);
        for(Point point : snake.player2.accessSnakeParts()){
            g.fillRect(point.x * MultiSnake.scale, point.y * MultiSnake.scale, MultiSnake.scale, MultiSnake.scale);
        }
        //render each snakes' head
        g.setColor(newOrange);//orange
        g.fillRect(snake.player1.getHead().x * snake.scale, snake.player1.getHead().y * snake.scale, MultiSnake.scale, MultiSnake.scale);
        g.setColor(Color.red);
        g.fillRect(snake.player2.getHead().x * snake.scale, snake.player2.getHead().y * snake.scale, MultiSnake.scale, MultiSnake.scale);
        //cherry type
        if(snake.cherry.getType()==0)//regular cherry
            g.setColor(Color.white);
        if(snake.cherry.getType()==1)//teleport
            g.setColor(Color.blue);
        if(snake.cherry.getType()==2)//bomb
            g.setColor(Color.green);
        if(snake.cherry.getType()==3)//
            g.setColor(Color.PINK);
        //renders cherry
        g.fillRect(snake.cherry.getLocation().x*snake.scale, snake.cherry.getLocation().y*snake.scale, snake.scale, snake.scale);
        //hud
        String time = ""+(MultiSnake.snake.ticks/20);
        if(snake.player1.getTeleport())
            g.drawString(""+snake.player1.getTeleport(),0,15);
        if(snake.player2.getTeleport())
            g.drawString(""+snake.player2.getTeleport(),0,15);
        if(snake.showAllPos){
            String hud = "Player 1 Head Pos: ("+snake.player1.getHead().x+","+snake.player1.getHead().y+")";
            g.drawString(hud, 0, 85);
            hud = "Player 2 Head Pos: ("+snake.player2.getHead().x+","+snake.player2.getHead().y+")";
            g.drawString(hud, 0, 95);
            g.drawString("Cherry Pos: ("+snake.cherry.getLocation().x+","+snake.cherry.getLocation().y+")",0,105);
            g.drawString(time, 0, 115);
        }
        //render bombs
        g.setColor(snake.player1.getBombColor());
        if(!snake.player1.getBombOnMap() && snake.player1.getHasBomb()){
            g.fillRect(snake.player1.getBombLocation().x*snake.scale,snake.player1.getBombLocation().y*snake.scale,snake.scale,snake.scale);
        }
        snake.player1.dynamizeBombColorBrightness();
        
        g.setColor(snake.player2.getBombColor());
        if(!snake.player2.getBombOnMap() && snake.player1.getHasBomb()){
            g.fillRect(snake.player2.getBombLocation().x*snake.scale,snake.player2.getBombLocation().y*snake.scale,snake.scale,snake.scale);
        }
        snake.player2.dynamizeBombColorBrightness();
        //renders stamina
        g.setColor(Color.CYAN);
        for (int i = 0; i < snake.player1.getStamina(); i++) {
            g.drawRect(0+i, 10, 1, snake.scale);
        }
        for (int i = 0; i < snake.player2.getStamina(); i++) {
            g.drawRect(0+i, 30, 1, snake.scale);
        }
        //render paused game || game over
        g.setColor(Color.blue);
        g.setFont(g.getFont().deriveFont(0,100));
        if(snake.paused){
            g.drawString("PAUSED", 0, 75);
        }
        if(snake.over==-1){
            Color randomColor = new Color(snake.random.nextInt(256),snake.random.nextInt(256),snake.random.nextInt(256));
            g.setColor(randomColor);
            g.drawString("PLAYER 2 WINS", snake.random.nextInt(10), snake.random.nextInt(10)+250);
            g.setFont(g.getFont().deriveFont(0,50));
            g.drawString("Score:"+snake.player2.getScore(), snake.random.nextInt(10)+50, snake.random.nextInt(10)+300);
        }
        if(snake.over==1){
            Color randomColor = new Color(snake.random.nextInt(256),snake.random.nextInt(256),snake.random.nextInt(256));
            g.setColor(randomColor);
            g.drawString("PLAYER 1 WINS", snake.random.nextInt(10), snake.random.nextInt(10)+250);
            g.setFont(g.getFont().deriveFont(0,50));
            g.drawString("Score:"+snake.player1.getScore(), snake.random.nextInt(10)+50, snake.random.nextInt(10)+300);
        }
        
    }
}
