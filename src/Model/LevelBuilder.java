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
 * @version 1.1.0
 * 		Fixed the input file
 */
public class LevelBuilder{
	
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	public LevelBuilder(ArrayList<Piece> pieces){
		this.pieces=pieces;
	}
	
	private boolean isSolvable() throws Exception{
		Solver solution = new Solver(pieces);
		if(solution.puzzleBreadthSearch()==null){
			return false;
		}
		else{
			return true;
		}
	}
	
	public boolean save(){
		try{
			if(isSolvable()){
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			    Document document = documentBuilder.parse(Puzzles.PUZZLES);
			    Element root = document.getDocumentElement();
		        Element puzzleRootElement = document.createElement("Puzzle");
		        Element numberElement = document.createElement("Number");
		        numberElement.appendChild(document.createTextNode(Integer.toString(Puzzles.getMaxPuzzle()+1)));
		        puzzleRootElement.appendChild(numberElement);
		        for (Piece piece:pieces){
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