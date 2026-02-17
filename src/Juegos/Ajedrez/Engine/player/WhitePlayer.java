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

public final class WhitePlayer extends Player {

    public WhitePlayer(final Board board,
                       final King playerKing,
                       final Collection<Move> whiteStandardLegals,
                       final Collection<Move> blackStandardLegals) {
        super(board, playerKing, whiteStandardLegals, blackStandardLegals);
    }

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentLegals) {

        if(!hasCastlingRights()) {
            return Collections.emptyList();
        }

        final List<Move> kingCastles = new ArrayList<>();

        if(this.playerKing.isFirstMove() && this.playerKing.getPiecePosition() == 60 && !this.isInCheck()) {
            //whites king side castle
            if(this.board.getPiece(61) == null && this.board.getPiece(62) == null) {
                final Piece kingSideRook = this.board.getPiece(63);
                if(kingSideRook != null && kingSideRook.isFirstMove()) {
                    if(BoardUtils.calculateAttacksOnTile(61, opponentLegals).isEmpty() &&
                       BoardUtils.calculateAttacksOnTile(62, opponentLegals).isEmpty() &&
                       kingSideRook.getPieceType() == ROOK) {
                        if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new KingSideCastleMove(this.board, this.playerKing, 62, (Rook) kingSideRook, 61));
                        }
                    }
                }
            }
            //whites queen side castle
            if(this.board.getPiece(59) == null && this.board.getPiece(58) == null &&
               this.board.getPiece(57) == null) {
                final Piece queenSideRook = this.board.getPiece(56);
                if(queenSideRook != null && queenSideRook.isFirstMove()) {
                    if(BoardUtils.calculateAttacksOnTile(58, opponentLegals).isEmpty() &&
                       BoardUtils.calculateAttacksOnTile(59, opponentLegals).isEmpty() && queenSideRook.getPieceType() == ROOK) {
                        if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 52)) {
                            kingCastles.add(new QueenSideCastleMove(this.board, this.playerKing, 58, (Rook) queenSideRook, 59));
                        }
                    }
                }
            }
        }
        return Collections.unmodifiableList(kingCastles);
    }

    @Override
    public BlackPlayer getOpponent() {
        return this.board.blackPlayer();
    }

    @Override
    public int[] getActivePieces() {
        return this.board.getWhitePieceCoordinates();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public String toString() {
        return Alliance.WHITE.toString();
    }

}
