/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Ajedrez.Engine.player;

import Juegos.Ajedrez.Engine.Alliance;
import Juegos.Ajedrez.Engine.board.Board;
import Juegos.Ajedrez.Engine.board.BoardUtils;
import Juegos.Ajedrez.Engine.board.Move;
import Juegos.Ajedrez.Engine.board.Move.KingSideCastleMove;
import Juegos.Ajedrez.Engine.board.Move.QueenSideCastleMove;
import Juegos.Ajedrez.Engine.pieces.King;
import Juegos.Ajedrez.Engine.pieces.Piece;
import Juegos.Ajedrez.Engine.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static Juegos.Ajedrez.Engine.pieces.Piece.PieceType.ROOK;

/**
 *
 * @author Juli
 */

public final class BlackPlayer extends Player {

    public BlackPlayer(final Board board,
                       final King playerKing,
                       final Collection<Move> whiteStandardLegals,
                       final Collection<Move> blackStandardLegals) {
        super(board, playerKing, blackStandardLegals, whiteStandardLegals);
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {

        if (!hasCastlingRights()) {
            return Collections.emptyList();
        }

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 4 && !this.isInCheck) {
            //blacks king side castle
            if (this.board.getPiece(5) == null && this.board.getPiece(6) == null) {
                final Piece kingSideRook = this.board.getPiece(7);
                if (kingSideRook != null && kingSideRook.isFirstMove() &&
                        BoardUtils.calculateAttacksOnTile(5, opponentLegals).isEmpty() &&
                        BoardUtils.calculateAttacksOnTile(6, opponentLegals).isEmpty() &&
                        kingSideRook.getPieceType() == ROOK) {
                    if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new KingSideCastleMove(this.board, this.playerKing, 6, (Rook) kingSideRook, 5));

                    }
                }
            }
            //blacks queen side castle
            if (this.board.getPiece(1) == null && this.board.getPiece(2) == null &&
                    this.board.getPiece(3) == null) {
                final Piece queenSideRook = this.board.getPiece(0);
                if (queenSideRook != null && queenSideRook.isFirstMove() &&
                        BoardUtils.calculateAttacksOnTile(2, opponentLegals).isEmpty() &&
                        BoardUtils.calculateAttacksOnTile(3, opponentLegals).isEmpty() &&
                        queenSideRook.getPieceType() == ROOK) {
                    if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) {
                        kingCastles.add(
                                new QueenSideCastleMove(this.board, this.playerKing, 2, (Rook) queenSideRook, 3));
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }

    @Override
    public WhitePlayer getOpponent() {
        return this.board.whitePlayer();
    }

    @Override
    public int[] getActivePieces() {
        return this.board.getBlackPieceCoordinates();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public String toString() {
        return Alliance.BLACK.toString();
    }

}
