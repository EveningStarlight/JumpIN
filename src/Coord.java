/**
 * 
 * @author Adam Prins
 * 			100 879 683
 * @version 1.1.0
 * 		added equals method
 * 
 *  This class holds coordinates for other objects
 */

public class Coord {
	
	//Holds the x and y positions of an object
	public final int x;
	public final int y;
	
	/**
	 * Creates the Coord object and sets the values of x and y.
	 * This object is immutable 
	 * 
	 * @param x the x position of the object
	 * @param y the y position of the object 
	 */
	public Coord(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Coord)) {
			return false;
		}
		else {
			Coord coord = (Coord) o;
			
			if (coord.x==this.x && coord.y==this.y) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}
