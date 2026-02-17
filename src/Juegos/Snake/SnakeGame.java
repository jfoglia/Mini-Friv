/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Snake;

/**
 *
 * @author Juli
 */

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{

    @Override
    public void keyTyped(KeyEvent e) 
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(directionChanged) return;
        
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1)
        {
            velocityX = 0;
            velocityY = -1;
            directionChanged = true;
        }else if(e.getKeyCode() == KeyEvent.VK_DOWN  && velocityY != -1)
        {
            velocityX = 0;
            velocityY = 1;
            directionChanged = true;
        }else if(e.getKeyCode() == KeyEvent.VK_LEFT  && velocityX != 1)
        {
            velocityX = -1;
            velocityY = 0;
            directionChanged = true;
        }else if(e.getKeyCode() == KeyEvent.VK_RIGHT &&  velocityX != -1)
        {
            velocityX = 1;
            velocityY = 0;
            directionChanged = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    private class Tile
    {
        int x;
        int y;
        Tile(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
    int boardWidth;
    int boardHeight;
    int tileSize = 25;
    
    //snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;
    
    //Food
    Tile food;
    Random random;
    
    //game logic
    Timer gameLoop;
    int velocityX;
    int velocityY;
    boolean gameOver = false;
    
    boolean directionChanged = false;
    
    SnakeGame(int boardWidth, int boardHeight)
    {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        
        snakeHead = new Tile(5,5);
        snakeBody = new ArrayList<Tile>();
        
        food = new Tile(10,10);
        
        random = new Random();
        placeFood();
        
        velocityX = 0;
        velocityY = 0;
        
        gameLoop = new Timer(100,this);
        gameLoop.start();
        
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g)
    {
//        for(int i = 0; i < boardWidth/tileSize; i++)
//        {
//            g.drawLine(i*tileSize, 0, i*tileSize, boardHeight);
//            g.drawLine(0, i*tileSize, boardWidth, i*tileSize);
//        }
        //Snake Head
        g.setColor(Color.green);
//        g.fillRect(snakeHead.x* tileSize, snakeHead.y * tileSize, tileSize, tileSize);
        g.fill3DRect(snakeHead.x* tileSize, snakeHead.y * tileSize, tileSize, tileSize, true);
        
        
        //Snake Body
        for(int i = 0; i < snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i);
            //g.fillRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize);
            g.fill3DRect(snakePart.x * tileSize, snakePart.y * tileSize, tileSize, tileSize, true);
        }
        
        //food
        g.setColor(Color.red);
        //g.fillRect(food.x*tileSize, food.y *tileSize, tileSize, tileSize);
        g.fill3DRect(food.x*tileSize, food.y *tileSize, tileSize, tileSize, true);
        
        //Score
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        if(gameOver)
        {
            g.setColor(Color.red);
            g.drawString("Game Over: " + String.valueOf(snakeBody.size()), tileSize-16, tileSize);
        }else
        {
            g.drawString("Score: " + String.valueOf(snakeBody.size()), tileSize -16, tileSize);
        }
        
        
        

    }
    
    public void placeFood()
    {
        food.x = random.nextInt(boardWidth/tileSize);
        food.y = random.nextInt(boardHeight/tileSize);
        for(int i = 0; i<snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i);
            if(snakePart.x == food.x && snakePart.y == food.y)
            {
                food.x = random.nextInt(boardWidth/tileSize);
                food.y = random.nextInt(boardHeight/tileSize);
            }
        }
        
    }
    
    public boolean collision(Tile tile1, Tile tile2)
    {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }
    
    public void move()
    {
        //comer
        if(collision(snakeHead, food))
        {
            snakeBody.add(new Tile(food.x, food.y));
            placeFood();
        }
        
        //snake body
        for(int i = snakeBody.size()-1; i >= 0; i--)
        {
            Tile snakePart = snakeBody.get(i);
            if(i == 0)
            {
                snakePart.x = snakeHead.x;
                snakePart.y = snakeHead.y;
            }
            else
            {
                Tile prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        
        //snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;
        
        // game over
        for(int i = 0; i < snakeBody.size(); i++)
        {
            Tile snakePart = snakeBody.get(i);
            
            if(collision(snakeHead, snakePart))
            {
                gameOver = true;
            }
        }
        
        if(snakeHead.x * tileSize < 0 || snakeHead.x *tileSize > boardWidth || snakeHead.y*tileSize < 0 || snakeHead.y*tileSize >boardHeight)
        {
            gameOver = true;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        move();
        repaint();
        directionChanged = false;
        if(gameOver)
        {
            reiniciar(gameOver);
            gameLoop.stop();
            
        }
    }
    
    public void reiniciar(boolean gameOver)
    {
        JButton restart = new JButton();
        JFrame rFrame = new JFrame("Fin del juego");
        JPanel rPanel = new JPanel();
        JPanel rPanel2 = new JPanel();
        JLabel mensaje = new JLabel();
        
        
        rFrame.setVisible(true);
        rFrame.setSize(250, 200);
        rFrame.setLocationRelativeTo(null);
        rFrame.setResizable(false);
        rFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        
        mensaje.setBackground(Color.darkGray);
        mensaje.setForeground(Color.white);
        mensaje.setFont(new Font("Arial", Font.BOLD,20));
        mensaje.setHorizontalAlignment(JLabel.CENTER);
        mensaje.setText("Deseas jugar de nuevo?");
        mensaje.setOpaque(true);
        
        rPanel2.setLayout(new GridBagLayout());
        rPanel2.add(restart);
        rPanel2.setBackground(Color.darkGray);
        rPanel2.setForeground(Color.white);        
        
        rPanel.setLayout(new BorderLayout());
        rPanel.add(rPanel2, BorderLayout.CENTER);
        rPanel.add(mensaje, BorderLayout.NORTH);
        rPanel.setBackground(Color.darkGray);
        rPanel.setForeground(Color.white);
        rPanel.setFont(new Font("Arial", Font.BOLD,15));
        rPanel.setFocusable(false);  
        
        rFrame.add(rPanel);
        
        restart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(gameOver == true)
                {
                    Snake tt = new Snake();
                    tt.iniciarJuego();   
                }
            }
        });

    }
}
