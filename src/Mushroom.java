/**
 *
 * @author Jay McCracken
 * 			101066860
 * @version 1.0.0
 *
 * Creation of a mushroom piece, the mushroom can not move and is stuck in place
 * it set in a certian spot during creation
 *
 */
public class Mushroom implements Piece{
    private Coord coord;

    /**
     * The creation of the mushroom piece, spot selected by coordanites
     * @param coord
     */
    public Mushroom(Coord coord){
        this.coord = coord;
    }

    /**
     * This piece is not able to move, therefore returns false
     * @return
     */
    public boolean isValidMove(Coord coord){
        return false;
    }

    /**
     * Grabbing the specific mushroom object
     * @return
     */
    public Piece getPiece(){
        return this;
    }

    /**
     * This method will throw an exception as it can not be moved
     * the param has no importantace
     * @param coord
     */
    public void setCoord(Coord coord){
        //TODO
        //replace with exception
        System.out.print("You can not move this piece.");
    }

    /**
     * Grabbing the coordinate of the specific mushroom object
     * @return the coordinate
     */
    public Coord getCoord(){
        return this.coord;
    }

}
