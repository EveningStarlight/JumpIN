/**
 * 
 * This class holds coordinates for other objects
 * 
 * @author Adam Prins
 * 			100 879 683
 * @version 1.2.0
 * 		Will now only accept valid coordinate pairs as arguments in the constructor
 * 
 *  
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
		if (x<0 || x>=Game.BOARD_SIZE || y<0 || y>=Game.BOARD_SIZE) {
			throw new IllegalArgumentException("coordinates can only go from 0-"+(Game.BOARD_SIZE-1));
		}
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
