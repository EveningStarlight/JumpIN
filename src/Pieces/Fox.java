package Pieces;

import Model.*;

import javax.swing.ImageIcon;

/**
 *
 * @author Jay McCracken
 * 			101066860
 * 
 * @version 2.0.1
 * fields now represent their static nature
 *
 */

public class Fox extends Piece{
	
	// The location of the head of the fox and tail of the fox
    private Coord head;
    private Coord tail;

    //the images to be placed on the buttons
    public static final ImageIcon ICON_HEAD = new ImageIcon("ButtonIcons/foxHead.png");
    public static final ImageIcon ICON_TAIL = new ImageIcon("ButtonIcons/foxTail.png");



    /**
     * The creation of the fox piece, spot selected by coordinates
     * @param head the location of the head of the fox
     * @param tail the location of the tail of the fox
     */
    public Fox(Coord Head, Coord Tail) throws Exception{
    	super(Head);
    	if (((Tail.x - Head.x) > 1 || (Tail.y - Head.y) > 1) && (Tail.x == Head.x || Tail.y == Head.y)){
    		throw new IllegalArgumentException("This piece cannot placed like that");
    	}else {
    		this.head = Head;
    		this.tail = Tail;
    	}
    }

    /**
     * This piece can only move in the direction the fox is oriented this ensures the request
     * is asking for that.
     * @return true is proposed coordinate is valid, false if it is not
     */
    public boolean isValidMove(Coord coord){
        if (coord.x == 4 ||coord.y == 4) { //if the head is on row or column 3 and wants to move down say that it is not a vaild move
        	return false;
        }
    	if(this.head.x == coord.x && this.tail.x == coord.x) {
        	return true;
        } else if(this.head.y == coord.y && this.tail.y == coord.y) {
        	return true;
        }else {
    	return false;
        }
    }

    /**
     * setting the coordinate of the fox determined by the orientation of it (the head of the fox is always up or to the left)
     * @param coord The new coordinate of the head of the fox
     */
    public void setCoord(Coord coord){
        if (this.head.y == this.tail.y) {
        	this.head = coord;
        	this.tail = new Coord ((coord.x + 1), coord.y);
        }else {
        	this.head = coord;
        	this.tail = new Coord (coord.x,(coord.y + 1));
        }   
    }

    /**
     * grabbing the coordinate of the head of the fox
     * @return the coordinate of the head
     */
    public Coord getCoord(){
        return this.head;
    }
    
    /**
     * grabbing the coordinate of the tail of the fox
     * @return the coordinate of the tail
     */
    public Coord getTail() {
    	return this.tail;
    }
}