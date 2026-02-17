/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Ajedrez.pgn;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Juli
 */

public class ValidGame implements Game {

    private final PGNGameTags tags;
    private final List<MoveRecord> moves;

    public ValidGame(final PGNGameTags tags,
                     final List<MoveRecord> moves) {
        this.tags = tags;
        this.moves = moves;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public PGNGameTags getTags() {
        return tags;
    }

    @Override
    public void saveGame(final MySqlGamePersistence persistence) {
        try {
            persistence.saveValidGame(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to persist game: ", e);
        }
    }

    public List<MoveRecord> getMoves() {
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValidGame)) return false;
        ValidGame validGame = (ValidGame) o;
        return Objects.equals(tags, validGame.tags) &&
                Objects.equals(moves, validGame.moves);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags, moves);
    }

    @Override
    public String toString() {
        return "ValidGame{" +
                "tags=" + tags +
                ", moves=" + moves +
                '}';
    }
}
