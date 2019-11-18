package Pieces;

import Model.*;

import javax.swing.ImageIcon;

/**
 *
 * @author Jay McCracken
 * 			101066860
 * @version 2.0.2
 *		Icons imported more dynamically for better exports
 *
 */
public class Mushroom extends Piece{
	
	// The picture of the mushroom for use on buttons
	public static final ImageIcon ICON = new ImageIcon(Mushroom.class.getClassLoader().getResource("Pieces/ButtonIcons/mushroom.png"));
	
    /**
     * The creation of the mushroom piece, spot selected by coordinates
     * @param coord
     */
    public Mushroom(Coord coord){
    	super(coord);
    }

}
