/**
 *
 * @author Jay McCracken
 * 			101066860
 * @version 1.0.0
 *
 * Creation of a bunny piece, this piece can move vertically or horizontally
 *
 */
public class Bunny implements Piece{
    private Coord coord;

    /**
     * The creation of the bunny piece, spot selected by coordinates
     * @param coord
     */
    public Bunny(Coord coord){
        this.coord = coord;
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
     * Grabbing the specific bunny object
     * @return
     */
    public Piece getPiece(){
        return this;
    }

    /**
     * set new coordinates of the bunny
     * @param coord
     */
    public void setCoord(Coord coord){
        this.coord = coord;
    }

    /**
     * Grabbing the coordinate of the specific bunny object
     * @return the coordinate
     */
    public Coord getCoord(){
        return this.coord;
    }

}
