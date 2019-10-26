/**
 * 
 * This class holds coordinates for other objects
 * 
 * @author Adam Prins
 * 			100 879 683
 * @version 1.3.0
 * 		Added toString method (from GUI branch)
 * 		Added JavaDoc comments
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
	
	/**
	 * Determines if a given object is equal to this coordinate
	 * They are said to be equal if they are both coordinates, and their
	 * x and y values are the same.
	 * 
	 * @return returns true if the coordinates are equal. (x1=x2, y1=y2)
	 */
	@Override
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
	
	/**
	 * returns a string representation of the coordinate
	 * 
	 * @return returns the coordinate in the form of (x,y)
	 */
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
