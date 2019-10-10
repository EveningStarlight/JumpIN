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
		if(getPiece() == null){
			return true;
		}
		else{
			return false;
		}
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
		if(isEmpty()==true){
			System.out.print("Empty");
		}
		else if(isEmpty()==false){
			System.out.print(getPiece());
		}
	}
}
