/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Juegos.Ajedrez.Engine.player.ai;

import Juegos.Ajedrez.Engine.board.Board;
import Juegos.Ajedrez.Engine.board.Move;

/**
 *
 * @author Juli
 */

public interface MoveStrategy {

    long getNumBoardsEvaluated();

    Move execute(Board board);

}
