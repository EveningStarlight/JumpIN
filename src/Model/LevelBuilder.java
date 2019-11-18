package Model;

import java.util.ArrayList;
import Pieces.*;
import Solver.Solver;
import java.util.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;

import javax.xml.parsers.*;

/**
 * The level builder it writes a possible level to the puzzles xml 
 * @author Matthew Harris
 * 			101073502
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
			try{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		    Document document = documentBuilder.parse("Puzzles.xml");
		    Element root = document.getDocumentElement();
	        Element puzzleRootElement = document.createElement("Puzzle");
	        Element numberElement = document.createElement("Number");
	        numberElement.appendChild(document.createTextNode(Integer.toString(Puzzles.getMaxPuzzle()+1)));
	        puzzleRootElement.appendChild(numberElement);
	        for (Piece piece:pieces){
	        	puzzleRootElement.appendChild(piece.getElement(document));
	        }
	        	return true;
			}
			catch(Exception e){
				return false;
			}
		}
		else{
			return false;
		}
		}
		catch(Exception e){
			return false;
		}
	}
}