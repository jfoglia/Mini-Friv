/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Juegos.Ajedrez.Engine.board;

import Juegos.Ajedrez.Engine.pieces.Piece;
import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableMap;

/**
 *
 * @author Juli
 */

public abstract class Tile {
    
    protected final int tileCoordinate;
    
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();
    private static final Map<Integer, EmptyTile> createAllPossibleEmptyTiles()
    {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        
        for(int i = 0; i < 64; i++)
        {
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap);
    }
    
    public static Tile createTile(final int tileCoordinate, final Piece piece)
    {
        return piece != null ? new OccupiedTile(tileCoordinate, piece): EMPTY_TILES_CACHE.get(tileCoordinate);
    }

    private Tile(int tileCoordinate) 
    {
        this.tileCoordinate = tileCoordinate;
    }
    
    public abstract boolean isTileOccupied();
    
    public abstract Piece getPiece();
    
    public static final class EmptyTile extends Tile
    {
        private EmptyTile(final int coordinate)
        {
            super(coordinate);
        }
        
        @Override
        public boolean isTileOccupied()
        {
            return false;
        }
        
        @Override
        public Piece getPiece()
        {
            return null;
        }
    }
    
    public static final class OccupiedTile extends Tile
    {
        private final Piece pieceOnTile;

        private OccupiedTile(int tileCoordinate, Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }
        
        @Override
        public boolean isTileOccupied()
        {
            return true;
        }
        
        @Override 
        public Piece getPiece()
        {
            return this.pieceOnTile;
        }
        
    }
    
    
    
}
