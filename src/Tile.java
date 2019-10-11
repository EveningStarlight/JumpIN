/**
 * 
 * @author Matthew Harris
 * 			101 073 502
 * @version 1.0.2
 * 
 *  This this is a interface for the tiles
 */
public interface Tile {

    public Coord getCoord();

    public Piece getPiece();

    public void setPiece(Piece piece);
    
    public boolean isEmpty();
    
    public Piece removePiece();
}
