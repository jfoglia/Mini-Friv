/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Snake;

/**
 *
 * @author Juli
 */

import javax.swing.*;
import Interfaces.Game;


public class Snake implements Game 
{

    int boardWidth = 600;
    int boardHeight = boardWidth;   
    
    @Override
    public String getNombre()
    {
        return "Snake";
    }
    
    @Override
    public void iniciarJuego()
    {
        JFrame frame = new JFrame(getNombre());
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        SnakeGame snakeGame = new SnakeGame(boardWidth,boardHeight);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
    
}
