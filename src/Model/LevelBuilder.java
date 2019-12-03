package Model;

import java.io.FileOutputStream;
import java.util.ArrayList;
import Pieces.*;
import Solver.Solver;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

/**
 * The level builder it writes a possible level to the puzzles xml 
 * @author Matthew Harris
 * 			101073502
 * @version 1.3.1
 * 		Puzzle number was outputting 1 too high.
 */
public class LevelBuilder{
	
	/**
	 * Checks if the game just created can be solved
	 * 
	 * @return returns true if solvable, false otherwise
	 */
	private static boolean isSolvable(Game game) {
		Solver solution = new Solver(game.getBoard());
		if(solution.puzzleBreadthSearch()==null){
			return false;
		}
		else{
			return true;
		}
	}
	
	/**
	 * If the new game is solvable, set up the board to be in the
	 * XML and then save the game to the XML
	 * 
	 * @return returns true is it save probably if solvable, false if otherwise
	 */
	public static boolean save(Game game){
		try{
			if(isSolvable(game)){
				ArrayList<Piece> boardPieces = game.getBoard();
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			    Document document = documentBuilder.parse(Puzzles.PUZZLES);
			    Element root = document.getDocumentElement();
		        Element puzzleRootElement = document.createElement("Puzzle");
		        Element numberElement = document.createElement("Number");
		        numberElement.appendChild(document.createTextNode(Integer.toString(Puzzles.getMaxPuzzle())));
		        puzzleRootElement.appendChild(numberElement);
		        for (Piece piece:boardPieces){
		        	puzzleRootElement.appendChild(piece.getElement(document));
		        }
		        root.appendChild(puzzleRootElement);
		        Transformer tr = TransformerFactory.newInstance().newTransformer();
	            tr.setOutputProperty(OutputKeys.INDENT, "yes");
	            tr.setOutputProperty(OutputKeys.METHOD, "xml");
	            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
	
	            // send DOM to file
	            tr.transform(new DOMSource(document), 
	                                 new StreamResult(new FileOutputStream(Puzzles.PUZZLES.getAbsolutePath())));
		        
		        	return true;
			}

		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return false;
	}
}