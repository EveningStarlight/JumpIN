package Model;

/**
 * A simple class that holds a record of the game moves as a collection 
 * of the previous location and the new current location
 * 
 * @author Adam Prins
 * 			100 879 683
 * 
 * @version 1.0.1
 * 		renamed fields to reflect static nature
 * 		added documentation
 */
public class Move {

	/* previous coordinate */
	public final Coord COORD_OLD;
	/* new coordinate */
	public final Coord COORD_NEW;
	
	/**
	 * 
	 * @param coordOld The previous location's coordinate
	 * @param coordNew The new location's coordinate
	 */
	public Move(Coord coordOld, Coord coordNew) {
		this.COORD_OLD=coordOld;
		this.COORD_NEW=coordNew;
	}
	
}
