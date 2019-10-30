import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Jay McCracken
 * 			101066860
 * @version 2.0.0
 *
 * Creation of a mushroom piece, the mushroom can not move and is stuck in place
 * it set in a certian spot during creation
 *
 */
public class Mushroom extends Piece{
	public static final ImageIcon icon = new ImageIcon("ButtonIcons/Mushroom.png");
    /**
     * The creation of the mushroom piece, spot selected by coordanites
     * @param coord
     */
    public Mushroom(Coord coord){
    	super(coord);
    }

    /**
     * This method will throw an exception as it can not be moved
     * the param has no importantace
     * @param coord
     */
    public void setCoord(Coord coord) throws Exception{
    	throw new IllegalArgumentException("You can not move this piece.");
    }



}
