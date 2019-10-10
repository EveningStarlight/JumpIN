/**
 * 
 * @author Matthew Harris
 * 			101 073 502
 * @version 1.0.4
 * 
 *  This class controls the tiles
 */
public class TextTile implements Tile{
	
	public final Coord coord;
	private Piece piece;
	
	public TextTile(Coord coord){
		this.coord = coord;
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

	public Coord getCoord() {
		return coord;
	}

	public Piece removePiece() {
		Piece old = piece;
		this.piece = null;
		return old;
	}
	public void print(){
		
	}
}
