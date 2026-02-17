/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Ajedrez.Engine.player.ai;

import Juegos.Ajedrez.Engine.board.Board;

/**
 *
 * @author Juli
 */

public interface BoardEvaluator {

    int evaluate(Board board, int depth);

}
