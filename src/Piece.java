import javax.swing.ImageIcon;

/**
 * 
 * @author Jay McCracken
 * 			101066860
 * @version 2.0.0
 * 
 * This is the interface for the generic piece
 * Objects implementing this will be the Bunny, Mushroom, and Fox
 *
 */
public abstract class Piece {
	/**
	 * Variable to store the image for the piece
	 */
	protected Coord coord;
	public static final ImageIcon icon = new ImageIcon("ButtonIcons/spaceBlank.png");
	public static final ImageIcon iconHole = new ImageIcon("ButtonIcons/SpaceHole.png");
	
	
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
     * setting the new coordanate of the piece throws error if it can not
     * @param coord
     */
	public void setCoord(Coord coord) throws Exception{};	
	
	/**
     * Grabbing the coordinate of the specific object
     * @return the coordinate
     */
	public Coord getCoord() {
		return this.coord;
	};	
}
