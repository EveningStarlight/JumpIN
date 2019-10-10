/**
 * 
 * @author Jay McCracken
 * 			101066860
 * @version 1.0.0
 * 
 * This is the interface for the generic piece
 * Objects implementing this will be the Bunny, Mushroom, and Fox
 *
 */
public interface Piece {
	public boolean isValidMove();	//Can the piece move
	public Piece getPiece();	//get what piece object
	public void setCoord(Coord coord);	//set the coordinate of the object
	public Coord getCoord();	//get the coordinate of the object
}
