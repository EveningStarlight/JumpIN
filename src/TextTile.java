/**
 * 
 * @author Matthew Harris
 * 			101 073 502
 * @version 1.0.2
 * 
 *  This class holds coordinates for other objects
 */
public class TextTile {
	
	public final Coord coord;
	private Piece piece;
	
	public TextTile(Coord coord, Piece piece){
		this.coord = coord;
		this.piece = piece;
	}
	
	public Piece getPiece(){
		return this.piece;
	}
	
	public void setPiece(Piece piece){
		this.piece = piece;
	}
	public boolean isEmpty(){
		return true;
	}
}
