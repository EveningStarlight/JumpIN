package Pieces;

import Model.*;

import javax.swing.ImageIcon;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * @author Jay McCracken
 * 			101066860
 * @version 2.2.0
 *		Separated getElement so that its implementation is more generic
 *		getElement no longer uses any if statements
 *		appendTypeElement uses getClass().getSimpleName() instead of staticlly giving strings in if statements
 *		appendCoordElement handles the coordinates of the pieces
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
	
	/**
	 * The set up of a piece element for the XML
	 * 
	 * @param document the document in charge of handling the XML creation
	 * @return the XML element that represents this given piece
	 */
	public Element getElement(Document document) {
		Element pieceElement = document.createElement("Piece");
		
		appendTypeElement(document, pieceElement);
		appendCoordElement(document, pieceElement);
		
		return pieceElement;
	}
	
	/**
	 * This method appends the piece type to the document
	 * 
	 * @param document document the document in charge of handling the XML creation
	 * @param pieceElement the element that we are appending to
	 */
	protected void appendTypeElement(Document document, Element pieceElement) {
		Element typeElement = document.createElement("Type");
		typeElement.appendChild(document.createTextNode(this.getClass().getSimpleName()));
		pieceElement.appendChild(typeElement);
	}
	
	/**
	 * This method appends coordinate data to the document
	 * 
	 * @param document document the document in charge of handling the XML creation
	 * @param pieceElement the element that we are appending to
	 */
	protected void appendCoordElement(Document document, Element pieceElement) {
		Element X_HeadElement = document.createElement("X_Head");
		Element Y_HeadElement = document.createElement("Y_Head");
		X_HeadElement.appendChild(document.createTextNode(Integer.toString(this.getCoord().x)));
		Y_HeadElement.appendChild(document.createTextNode(Integer.toString(this.getCoord().y)));
		pieceElement.appendChild(X_HeadElement);
		pieceElement.appendChild(Y_HeadElement);
	}
	
};
