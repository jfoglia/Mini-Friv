/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Ajedrez.Engine.pieces;

import Juegos.Ajedrez.Engine.Alliance;
import Juegos.Ajedrez.Engine.board.Board;
import Juegos.Ajedrez.Engine.board.BoardUtils;
import Juegos.Ajedrez.Engine.board.Move;
import Juegos.Ajedrez.Engine.board.Move.MajorAttackMove;
import Juegos.Ajedrez.Engine.board.Move.MajorMove;
import Juegos.Ajedrez.Engine.board.MoveUtils;

/**
 *
 * @author Juli
 */

import java.util.*;

public final class Queen extends Piece {

    private final static int[] CANDIDATE_MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };
    private final static Map<Integer, MoveUtils.Line[]> PRECOMPUTED_CANDIDATES = computeCandidates();

    Queen(final Alliance alliance,
          final int piecePosition,
          final boolean isFirstMove) {
        super(PieceType.QUEEN, alliance, piecePosition, isFirstMove);
    }

    private static Map<Integer, MoveUtils.Line[]> computeCandidates() {
        final Map<Integer, MoveUtils.Line[]> candidates = new HashMap<>();
        for (int position = 0; position < BoardUtils.NUM_TILES; position++) {
            List<MoveUtils.Line> lines = new ArrayList<>();
            for (int offset : CANDIDATE_MOVE_COORDINATES) {
                int destination = position;
                final MoveUtils.Line line = new MoveUtils.Line();
                while (BoardUtils.isValidTileCoordinate(destination)) {
                    if (isFirstColumnExclusion(destination, offset) ||
                            isEightColumnExclusion(destination, offset)) {
                        break;
                    }
                    destination += offset;
                    if (BoardUtils.isValidTileCoordinate(destination)) {
                        line.addCoordinate(destination);
                    }
                }
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
            if (!lines.isEmpty()) {
                candidates.put(position, lines.toArray(new MoveUtils.Line[0]));
            }
        }
        return Collections.unmodifiableMap(candidates);
    }


    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final MoveUtils.Line line : PRECOMPUTED_CANDIDATES.get(this.piecePosition)) {
            for (final int candidateDestinationCoordinate : line.getLineCoordinates()) {
                final Piece pieceAtDestination = board.getPiece(candidateDestinationCoordinate);
                if (pieceAtDestination == null) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Alliance pieceAlliance = pieceAtDestination.getPieceAllegiance();
                    if (this.pieceAlliance != pieceAlliance) {
                        legalMoves.add(new MajorAttackMove(board, this, candidateDestinationCoordinate,
                                pieceAtDestination));
                    }
                    break;
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    @Override
    public int locationBonus() {
        return this.pieceAlliance.queenBonus(this.piecePosition);
    }

    @Override
    public Queen getMovedPiece(final Move move) {
        return PieceUtils.INSTANCE.getQueen(move.getMovedPiece().getPieceAllegiance(), move.getDestinationCoordinate(), true);
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    private static boolean isFirstColumnExclusion(final int position,
                                                  final int offset) {
        return BoardUtils.FIRST_COLUMN.get(position) && ((offset == -9)
                || (offset == -1) || (offset == 7));
    }

    private static boolean isEightColumnExclusion(final int position,
                                                  final int offset) {
        return BoardUtils.EIGHTH_COLUMN.get(position) && ((offset == -7)
                || (offset == 1) || (offset == 9));
    }

}
