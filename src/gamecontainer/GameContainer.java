/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gamecontainer;

/**
 *
 * @author Juli
 */
import Juegos.Ahorcado;
import Juegos.TicTacToe;
import Juegos.Snake.Snake;
import Juegos.pacMan.PacMan;
import Juegos.spaceInvaders.SpaceInvaders;
import Juegos.Ajedrez.gui.Table;
import Juegos.BB.BrickBreaker;
import Juegos.Pong.Pong;
import Juegos.minessweeper.Minesweeper;

public class GameContainer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Table ajedrez = new Table();
        //ajedrez.iniciarJuego();
        Ahorcado ah = new Ahorcado();
        //ah.iniciarJuego();
        TicTacToe tt = new TicTacToe();
        //tt.iniciarJuego();
        Snake sg = new Snake();
        //sg.iniciarJuego();
        PacMan pacMan = new PacMan();
        //pacMan.iniciarJuego();
        SpaceInvaders sI = new SpaceInvaders();
        //sI.iniciarJuego();
        Pong pong = new Pong();
        //pong.iniciarJuego();
        BrickBreaker bb = new BrickBreaker();
        //bb.iniciarJuego();
        Minesweeper bM = new Minesweeper();
        //bM.iniciarJuego();
        
        
    }
    
}
