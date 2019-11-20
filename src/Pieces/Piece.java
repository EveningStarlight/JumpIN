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
		Element PieceElement = document.createElement("Piece");
		Element TypeElement = document.createElement("Type");
		Element X_HeadElement = document.createElement("X_Head");
		Element Y_HeadElement = document.createElement("Y_Head");
		
		if(this instanceof Mushroom) {
			TypeElement.appendChild(document.createTextNode("Mushroom"));
		}else if (this instanceof Bunny) {
			TypeElement.appendChild(document.createTextNode("Bunny"));
		}else if (this instanceof Fox) {
			TypeElement.appendChild(document.createTextNode("Fox"));
			
			Element X_TailElement = document.createElement("X_Tail");
			Element Y_TailElement = document.createElement("Y_Tail");
			
			X_TailElement.appendChild(document.createTextNode(Integer.toString(((Fox) this.getPiece()).getTail().x)));
			Y_TailElement.appendChild(document.createTextNode(Integer.toString(((Fox) this.getPiece()).getTail().y)));
			
			PieceElement.appendChild(X_TailElement);
			PieceElement.appendChild(Y_TailElement);
		}

		X_HeadElement.appendChild(document.createTextNode(Integer.toString(this.getCoord().x)));
		Y_HeadElement.appendChild(document.createTextNode(Integer.toString(this.getCoord().y)));
		
		PieceElement.appendChild(TypeElement);
		PieceElement.appendChild(X_HeadElement);
		PieceElement.appendChild(Y_HeadElement);
		
		return PieceElement;
	}
	
};
