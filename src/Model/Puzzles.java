package Model;

import Pieces.*;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.*;
import org.w3c.dom.*;

/**
 * A collection of the puzzles that can be played through for the game JumpIN
 * 
 * @authors Adam Prins
 * 			100 879 683
 * @version 3.0.1
 * 		Refactored some variable names and removed unused imports.
 *
 */
public class Puzzles {
	
	private static final File PREDEFINED_PUZZLES = new File ("src/Model/Puzzles.xml");
	private static final File USER_PUZZLES = new File ("src/Model/UserPuzzles.xml");

	/**
	 * @param puzzleNumber the puzzle you would like to initialize
	 * @return returns an ArrayList of Pieces that can be used to populate the game board
	 * @throws Exception 
	 */
	public static ArrayList<Piece> getPuzzle(int puzzleNumber) {
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		try {
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

	         Document doc = dBuilder.parse(PREDEFINED_PUZZLES);
	         doc.getDocumentElement().normalize();

	         XPath xPath =  XPathFactory.newInstance().newXPath();

	         String expression = "//Puzzle";	        
	         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate( doc, XPathConstants.NODESET);

	         if (0<puzzleNumber && puzzleNumber<nodeList.getLength()) {
	        	 Node nNode = nodeList.item(puzzleNumber);
	        	 if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	                 Element eElement = (Element) nNode;
	                 NodeList pieceNodes = eElement.getElementsByTagName("Piece");
	                 for (int i=0; i<pieceNodes.getLength(); i++ ) {
	                	 Element pElement = (Element) pieceNodes.item(i);
	                	 int x = Integer.parseInt(pElement.getElementsByTagName("X_Head").item(0).getTextContent());
	                	 int y = Integer.parseInt(pElement.getElementsByTagName("Y_Head").item(0).getTextContent());
	                	 String type = pElement.getElementsByTagName("Type").item(0).getTextContent();
	                	 Coord coord = new Coord(x,y);
	                	 
	                	 if ("Mushroom".equals(type))		pieces.add(new Mushroom(coord));
	                	 else if ("Bunny".equals(type)) 	pieces.add(new Bunny(coord));
	                	 else if ("Fox".equals(type)) {
	                		 int xTail = Integer.parseInt(pElement.getElementsByTagName("X_Tail").item(0).getTextContent());
	                		 int yTail = Integer.parseInt(pElement.getElementsByTagName("Y_Tail").item(0).getTextContent());
	                		 Coord coordTail = new Coord(xTail,yTail);
	                		 pieces.add(new Fox(coord,coordTail));
	                	 }
	                	 else {
	                		 throw new IllegalArgumentException("The type of Piece: " + type + " is not a valid Piece.");
	                	 }
	                 }
	        	 }
	         }
	         else {
	        	 throw new IllegalArgumentException("The Puzzle Number: " + puzzleNumber + " is not a valid Puzzle.");
	         }
	         
	         
	      } catch (Exception e) {
	         System.out.print("Puzzle XML Exception: " + e.getMessage());
	      }
		return pieces;
	}

	/**
	 * 
	 * @return the number of puzzles in the array. Since the first puzzle is not configured for play,
	 * this is one higher than the puzzleNumber of the last puzzle
	 */
	public static int getMaxPuzzle() {
		try {
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

	         Document doc = dBuilder.parse(PREDEFINED_PUZZLES);
	         doc.getDocumentElement().normalize();

	         XPath xPath =  XPathFactory.newInstance().newXPath();

	         String expression = "//Puzzle";	        
	         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate( doc, XPathConstants.NODESET);

	         return nodeList.getLength();
		} catch(Exception e) {
			System.out.print("getMaxPuzzle Exception: " + e.getMessage());
		}
		return -1;
	}
	
}
