/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Juegos.Pong;

/**
 *
 * @author USER
 */
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Interfaces.Game;

public class Pong implements Game{
    
    static JFrame f = new JFrame("Pong");
    
    @Override
    public void iniciarJuego()
    {
        f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        f.setResizable(false);
        f.setSize(655,510);
        f.setLocationRelativeTo(null);
        PongGame game = new PongGame();
        f.add(game);
        f.setVisible(true);

        Timer timer = new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                game.gameLogic();
                game.repaint();
                
            }
        });

        timer.start();
        
    }
    
    @Override
    public String getNombre()
    {
        return "Pong";
    }
}