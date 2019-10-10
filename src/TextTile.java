/**
 * 
 * @author Matthew Harris
 * 			101 073 502
 * @version 1.0.5
 * 
 *  This class controls the tiles
 */
public class TextTile implements Tile{
	
	public final Coord coord;
	private Piece piece;
	
	/**
	 * Constructor for the text tile class takes in a coorinate to determine its location
	 * @param coord the coordinate location of tiles on the board
	 */
	public TextTile(Coord coord){
		this.coord = coord;
	}
	
	/**
	 * returns what piece is on the tile if there is one
	 * @return what piece is on the tile or null is there is none
	 */
	public Piece getPiece(){
		return this.piece;
	}
	
	/**
	 * sets what piece is one the tile
	 */
	public void setPiece(Piece piece){
		this.piece = piece;
	}
	
	/**
	 * check whether or not there is a piece on the tile
	 * @return true is the tile is null and false if the tile has a piece on it
	 */
	public boolean isEmpty(){
		if(getPiece() == null){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * return the coordinate of the tile on the board
	 * @return the coordinate of the tile
	 */
	public Coord getCoord() {
		return coord;
	}
	/**
	 * Clear what piece is on the tile the return what piece was there
	 * @return returns the piece that was on the tile before clearing
	 */
	public Piece removePiece() {
		Piece old = piece;
		this.piece = null;
		return old;
	}
	
	/**
	 * Print the name of the piece on the tile or empty is there is none 
	 */
	public void print(){
		if(isEmpty()==true){
			System.out.print("Empty");
		}
		else if(isEmpty()==false){
			System.out.print(getPiece());
		}
	}
}
