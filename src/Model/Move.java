package Model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A simple class that holds a record of the game moves as a collection 
 * of the previous location and the new current location
 * 
 * @author Adam Prins
 * 			100 879 683
 * 
 * @version 1.1.1
 * 		xml for Move is called Move
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
	
	/**
	 * This creates an Element that represents this Move for use in Saving into an XML
	 * 
	 * @param document document that will help create the Move Element
	 * @return the Element created to Represent the Move
	 */
	public Element getElement(Document document) {
		
		Element oldElement = document.createElement("OldCoord");
		Element newElement = document.createElement("NewCoord");
		Element moveElement = document.createElement("Move");
		
		oldElement.appendChild(COORD_OLD.getElement(document));
		newElement.appendChild(COORD_NEW.getElement(document));
		
		moveElement.appendChild(oldElement);
		moveElement.appendChild(newElement);
		
		return moveElement;
	}
	
	/**
	 * This method imports an Element that contains data for a Move and 
	 * returns a new Move created from those values.
	 * 
	 * @param element that is to be used to create a new Move
	 * @return the Move created using the passed Element
	 */
	public static Move importCoord(Element element) {
		Coord oldCoord = Coord.importCoord((Element)element.getElementsByTagName("OldCoord").item(0));
		Coord newCoord = Coord.importCoord((Element)element.getElementsByTagName("NewCoord").item(0));
   	 	
   	 	return new Move(oldCoord,newCoord);
	}
	
}
