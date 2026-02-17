/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Pong;

/**
 *
 * @author Juli
 */
import Juegos.Pong.Ball;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

//This class must extend JPanel so we can use paintComponent and implement MouseMotionListener to track mouse
public class PongGame extends JPanel implements MouseMotionListener {

    //Constants for window width and height, in case we want to change the width/height later
    static final int WINDOW_WIDTH = 640, WINDOW_HEIGHT = 480;
    private final Ball gameBall;
    private final Paddle userPaddle;
    private final Paddle pcPaddle;
    private int userScore, pcScore;

    private int userMouseY;
    private int bounceCount;
    

    /**
     * Standard constructor for a PongGame
     */
    public PongGame() {

        gameBall = new Ball(300, 200,8, 8, 8, Color.CYAN, 10);
        userPaddle = new Paddle(10, 200, 75, 10, new Color(51, 255, 100));
        pcPaddle = new Paddle(610, 200, 75, 10, new Color(151, 250, 51));

        userMouseY = 0;
        userScore = 0; pcScore = 0;
        bounceCount = 0;

        addMouseMotionListener(this);

    }

    public void reset()
    {
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        gameBall.setX(300);
        gameBall.setY(200);
        gameBall.setCx(8);
        gameBall.setCy(8);
        gameBall.setSpeed(8);
        bounceCount = 0;

    }

    public void paintComponent(Graphics g) {
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        gameBall.paint(g);
        userPaddle.paint(g);
        pcPaddle.paint(g);

        g.setColor(Color.WHITE);
        g.drawString("Puntaje: - Jugador [ " + userScore + " ]   CPU [ " + pcScore + " ]", 250, 20   );

    }

    public void gameLogic() {

        gameBall.moveBall();
        gameBall.bounceBack(0, WINDOW_HEIGHT);
        userPaddle.moveTo(userMouseY);
        pcPaddle.moveTo(gameBall.getY());
        
        if(pcPaddle.checkCollision(gameBall) || userPaddle.checkCollision(gameBall)){

            gameBall.invertX();
            bounceCount++;
            
        }

        if (bounceCount == 3){
            
            bounceCount = 0;
            gameBall.increaseSpeed();
        }

        if(gameBall.getX() < 0){
            
            pcScore++;
            gameBall.speed = 0;
            reset();
            
        }
        else if(gameBall.getX() > WINDOW_WIDTH){

            userScore++;
            gameBall.speed = 0;
            reset();
            
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        userMouseY = e.getY();

    }
}