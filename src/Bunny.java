import javax.swing.ImageIcon;

/**
 *
 * @author Jay McCracken
 * 			101066860
 * @version 2.0.1
 *		fields now represent their static nature
 */
public class Bunny extends Piece{
	
	// The picture of the Bunny and the Bunny in a hole for use on buttons
	public static final ImageIcon ICON = new ImageIcon("ButtonIcons/bunnyBlank.png");
	public static final ImageIcon ICON_HOLE = new ImageIcon("ButtonIcons/bunnyHole.png");

    /**
     * The creation of the bunny piece, spot selected by coordinates
     * @param coord
     */
    public Bunny(Coord coord){
    	super(coord);
    }

    /**
     * This piece can only move vertically or horizontally and must jump over at
     * least one piece. this ensures the request is valid based only on the x and y positions.
     * 
     * @return true is proposed coordinate is valid, false if it is not
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
     * @param coord the new coordinates of the bunny
     */
    public void setCoord(Coord coord){
        this.coord = coord;
    }
}
