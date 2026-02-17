/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Juegos.Ajedrez.pgn;

import java.util.List;

/**
 *
 * @author Juli
 */

public interface Game {
    boolean isValid();
    PGNGameTags getTags();
    List<MoveRecord> getMoves();
    void saveGame(MySqlGamePersistence persistence);
}
