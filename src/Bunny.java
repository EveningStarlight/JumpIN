import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Jay McCracken
 * 			101066860
 * @version 2.0.0
 *
 * Creation of a bunny piece, this piece can move vertically or horizontally
 *
 */
public class Bunny extends Piece{
	
	public static final ImageIcon icon = new ImageIcon("ButtonIcons/bunnyBlank.png");
	public static final ImageIcon iconHole = new ImageIcon("ButtonIcons/bunnyHole.png");

    /**
     * The creation of the bunny piece, spot selected by coordinates
     * @param coord
     */
    public Bunny(Coord coord){
    	super(coord);
    }

    /**
     * This piece can only move vertically or horizontally this ensures the request
     * is asking for that.
     * @return true is they can make the move they are requesting, false if they can not
     */
    public boolean isValidMove(Coord coord){
        if(this.coord.x == coord.x && (Math.abs(this.coord.y - coord.y) > 1)) {
        	return true;
        } else if(this.coord.y == coord.y && (Math.abs(this.coord.x - coord.x) > 1)) {
        	return true;
        }else {
    	return false;
        }
    }

    /**
     * set new coordinates of the bunny
     * @param coord
     */
    public void setCoord(Coord coord){
        this.coord = coord;
    }
}
