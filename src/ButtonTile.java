import javax.swing.JButton;

/**
 * 
 * @author Matthew Harris
 * 			101 073 502
 * @version 1.0.2
 * 
 *  This class holds coordinates for other objects
 */
public class ButtonTile extends JButton implements Tile{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final Coord coord;
	private Piece piece;
	
	public ButtonTile(Coord coord, Piece piece){
		super();
		this.coord = coord;
		this.piece = piece;
	}

	public Coord getCoord() {
		return coord;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Piece removePiece() {
		Piece old = piece;
		this.piece = null;
		return old;
	}

	public boolean isEmpty() {
		if(getPiece() == null){
			return true;
		}
		else{
			return false;
		}
	}

}
