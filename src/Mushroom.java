import javax.swing.ImageIcon;

/**
 *
 * @author Jay McCracken
 * 			101066860
 * @version 2.0.1
 *		fields now represent their static nature
 *
 */
public class Mushroom extends Piece{
	
	// The picture of the mushroom for use on buttons
	public static final ImageIcon ICON = new ImageIcon("ButtonIcons/mushroom.png");
	
    /**
     * The creation of the mushroom piece, spot selected by coordinates
     * @param coord
     */
    public Mushroom(Coord coord){
    	super(coord);
    }

}
