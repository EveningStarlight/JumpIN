package GUI;

import Pieces.*;
import Model.*;

import javax.swing.JButton;

/**
 * 
 * @author Matthew Harris
 * 			101 073 502
 * @version 1.2.0
 * 		Added java doc comments
 *  
 */
public class ButtonTile extends JButton implements Tile{
	
	private static final long serialVersionUID = 1L;
	//The coordinate of the tile
	public final Coord coord;
	//the piece the tile contains
	private Piece piece;
	
	/**
	 * This creates a ButtonTile object with the given coordinate
	 * 
	 * @param coord the coordinate of the newly created tile
	 */
	public ButtonTile(Coord coord){
		super();
		this.coord = coord;
	}

	/**
	 * passes the coordinate of this tile
	 * 
	 * @return returns the coordinate of this tile
	 */
	public Coord getCoord() {
		return coord;
	}

	/**
	 * @return returns the piece contained by this tile
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Sets the contained piece to the one passed
	 * 
	 * @param piece the piece this tile will contain
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	/**
	 * Sets the contents of this tile to null, and passes the old piece as a return value
	 * 
	 * @return Returns the piece removed by the method
	 */
	public Piece removePiece() {
		Piece old = piece;
		this.piece = null;
		return old;
	}

	/**
	 * Returns a boolean that corresponds to whether the tile contains a piece or not
	 * 
	 * @return Returns a boolean that is true if the tile does not contain a piece, and false otherwise
	 */
	public boolean isEmpty() {
		if(getPiece() == null){
			return true;
		}
		else{
			return false;
		}
	}

}
