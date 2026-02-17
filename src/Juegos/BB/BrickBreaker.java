/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Juegos.BB;

import javax.swing.JFrame;
import Interfaces.Game;

/**
 *
 * @author USER
 */
public class BrickBreaker implements Game {
    
    @Override
    public void iniciarJuego()
    {
        JFrame obj = new JFrame();
        BrickGame gameplay = new BrickGame();
        obj.setBounds(10,10,700,600);
        obj.setTitle("BrickBreaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        obj.setLocationRelativeTo(null);
        obj.add(gameplay);
    }
    
    @Override
    public String getNombre()
    {
        return "BrickBraker";
    }
}
