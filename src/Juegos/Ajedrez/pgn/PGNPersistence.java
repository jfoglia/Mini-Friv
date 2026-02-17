/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Ajedrez.pgn;

import Juegos.Ajedrez.Engine.board.Board;
import Juegos.Ajedrez.Engine.board.Move;
import Juegos.Ajedrez.Engine.player.Player;

/**
 *
 * @author Juli
 */

public interface PGNPersistence {

    void persistGame(Game game);

    Move getNextBestMove(Board board, Player player, String gameText);

}
