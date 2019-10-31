import javax.swing.ImageIcon;

/**
 *
 * @author Jay McCracken
 * 			101066860
 * @version 2.0.0
 *
 * Creation of a mushroom piece, the mushroom can not move and is stuck in place
 * it set in a certain spot during creation
 *
 */
public class Mushroom extends Piece{
	
	// The picture of the mushroom for use on buttons
	public static final ImageIcon icon = new ImageIcon("ButtonIcons/mushroom.png");
	
    /**
     * The creation of the mushroom piece, spot selected by coordinates
     * @param coord
     */
    public Mushroom(Coord coord){
    	super(coord);
    }

}
