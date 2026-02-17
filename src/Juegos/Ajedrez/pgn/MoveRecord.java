/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Ajedrez.pgn;

/**
 *
 * @author Juli
 */

public final class MoveRecord {

    private final int moveNumber;
    private final String player;
    private final String san; // Standard Algebraic Notation
    private final String fenBefore;
    private final String fenAfter;

    public MoveRecord(final int moveNumber,
                      final String player,
                      final String san,
                      final String fenBefore,
                      final String fenAfter) {
        this.moveNumber = moveNumber;
        this.player = player;
        this.san = san;
        this.fenBefore = fenBefore;
        this.fenAfter = fenAfter;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getPlayer() {
        return player;
    }

    public String getSan() {
        return san;
    }

    public String getFenBefore() {
        return fenBefore;
    }

    public String getFenAfter() {
        return fenAfter;
    }

    @Override
    public String toString() {
        return "MoveRecord{" +
                "moveNumber=" + moveNumber +
                ", player='" + player + '\'' +
                ", san='" + san + '\'' +
                ", fenBefore='" + fenBefore + '\'' +
                ", fenAfter='" + fenAfter + '\'' +
                '}';
    }
}
