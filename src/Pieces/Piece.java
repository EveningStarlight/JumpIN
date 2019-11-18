package Pieces;

import Model.*;

import javax.swing.ImageIcon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author Jay McCracken
 * 			101066860
 * @version 2.0.2
 *		Icons imported more dynamically for better exports
 */
public abstract class Piece {
	
	// The location of the piece
	protected Coord coord;
	
	// The picture of the ground and hole for use on buttons
	public static final ImageIcon ICON = new ImageIcon(Piece.class.getClassLoader().getResource("Pieces/ButtonIcons/spaceBlank.png"));
	public static final ImageIcon ICON_HOLE = new ImageIcon(Piece.class.getClassLoader().getResource("Pieces/ButtonIcons/spaceHole.png"));
	
	
	public Piece(Coord coord) {
		this.coord = coord;
	}
	/**
     * Checking if a piece can move, default no
     * @return
     */
	public boolean isValidMove(Coord coord) {
		return false;
	};	
	
	/**
     * Grabbing the specific object
     * @return
     */
	public Piece getPiece() {
		return this;
	};	
	
	/**
     * setting the new coordinate of the piece throws error if it can not
     * @param coord
     */
	public void setCoord(Coord coord) throws Exception{
    	throw new IllegalArgumentException("You can not move this piece.");
    }
	
	/**
     * Grabbing the coordinate of the specific object
     * @return the coordinate
     */
	public Coord getCoord() {
		return this.coord;
	}	
	
	public Element getElement(Document document) {
		return null;
	}
	
	public static Piece importCoord(Element element) {
		return null;
	}
};
