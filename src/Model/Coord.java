package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * 
 * This class holds coordinates for other objects
 * 
 * @author Adam Prins
 * 			100 879 683
 * @version 1.5.0
 *		Added xml element support for saving Coords
 * 		Added xml import support
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
	
	/**
	 * 
	 * @return true if this location is a hole. (0,0), (0,4), (4,0), (4,4), (2,2)
	 */
	public Boolean isHole() {
		return ((x==0 || x==4) && (y==0 || y==4)) || (x==2 && y==2);
	}
	
	/**
	 * This creates an Element that represents this Coord for use in Saving into an XML
	 * 
	 * @param document document that will help create the Coord Element
	 * @return the Element created to Represent the Coord
	 */
	public Element getElement(Document document) {
		
		Element XElement = document.createElement("X");
		Element YElement = document.createElement("X");
		Element coordElement = document.createElement("Coord");
		
		XElement.appendChild(document.createTextNode(Integer.toString(x)));
		YElement.appendChild(document.createTextNode(Integer.toString(y)));
		
		coordElement.appendChild(XElement);
		coordElement.appendChild(YElement);
		
		return coordElement;
	}
	
	/**
	 * This method imports an Element that contains data for a Coord and 
	 * returns a new Coord created from those values.
	 * 
	 * @param element that is to be used to create a new Coord
	 * @return the Coord created using the passed Element
	 */
	public static Coord importCoord(Element element) {
		int x = Integer.parseInt(element.getElementsByTagName("X").item(0).getTextContent());
   	 	int y = Integer.parseInt(element.getElementsByTagName("Y").item(0).getTextContent());
   	 	
   	 	return new Coord(x,y);
	}
}
