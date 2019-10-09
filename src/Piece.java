/**
 * 
 * @author Adam Prins
 * 			100 879 673
 * @version 1.0.0
 * 
 * This is the interface for the generic piece
 * Objects implementing this will be the Bunny, Mushroom, and Fox
 *
 */
public interface Piece {
	public boolean isValidMove();
	public Piece getPiece();
	public void setCoord(Coord coord);
	public Coord getCoord();
}
