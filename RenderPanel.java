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
    @Override
    protected void paintComponent(Graphics g) {
        //initialize frame
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, snake.jframe.getSize().width, snake.jframe.getSize().height);
        g.setColor(Color.WHITE);
        //snake body rendering
        g.setColor(Color.orange);
        for(Point point : snake.player1.accessSnakeParts()){
            g.fillRect(point.x * MultiSnake.scale, point.y * MultiSnake.scale, MultiSnake.scale, MultiSnake.scale);
        }
        g.setColor(Color.red);
        for(Point point : snake.player2.accessSnakeParts()){
            g.fillRect(point.x * MultiSnake.scale, point.y * MultiSnake.scale, MultiSnake.scale, MultiSnake.scale);
        }
        g.setColor(Color.red);
        g.fillRect(snake.player1.getHead().x * snake.scale, snake.player1.getHead().y * snake.scale, MultiSnake.scale, MultiSnake.scale);//render each snakes' head
        g.setColor(Color.magenta);
        g.fillRect(snake.player2.getHead().x * snake.scale, snake.player2.getHead().y * snake.scale, MultiSnake.scale, MultiSnake.scale);
        //cherry type
        if(snake.cherry.getType()==0)//regular cherry
            g.setColor(Color.white);
        if(snake.cherry.getType()==1)//slowed ticks
            g.setColor(Color.blue);
        if(snake.cherry.getType()==2)//bomb ??
            g.setColor(Color.green);
        if(snake.cherry.getType()==3)//teleport
            g.setColor(Color.PINK);
        g.fillRect(snake.cherry.getLocation().x*snake.scale, snake.cherry.getLocation().y*snake.scale, snake.scale, snake.scale);//renders cherry
        //hud
        String time = ""+snake.ticks;
        if(snake.player1.getTeleport())
            g.drawString(""+snake.player1.getTeleport(),1700,15);
        if(snake.player2.getTeleport())
            g.drawString(""+snake.player2.getTeleport(),1700,15);
        if(snake.showHeadPos){
            String hud = "Player 1 Head Pos: ("+snake.player1.getHead().x+","+snake.player1.getHead().y+")";
            g.drawString(hud, 675, 15);
            hud = "Player 2 Head Pos: ("+snake.player2.getHead().x+","+snake.player2.getHead().y+")";
            g.drawString(hud,675, 25);
        }
        //render paused game || game over
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(0,100));
        if(snake.paused){
            g.drawString("PAUSED", snake.jframe.getWidth()/4, snake.jframe.getHeight()/2);
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
