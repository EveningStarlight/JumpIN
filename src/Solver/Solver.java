package Solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import GUI.ButtonTile;
import GUI.Tile;
import Model.Coord;
import Model.Game;
import Model.Puzzles;
import Pieces.Piece;

/**
 * The solver for the puzzles, with hints and how to solve each puzzle
 * @authors Jay McCracken, Matthew Harris
 * 			101066860       101073502
 * @version 1.0.1
 * 	constructor created
 */
public class Solver {

	private ArrayList<Piece> pieces;
	private HashMap<Piece, Boolean> visitedPieces;
	private Game game;
	private Tile[][] board;
	private Queue<String> movesTaken;
	
	public Solver(int puzzleNumber) throws Exception{
		pieces = new ArrayList<Piece>(Puzzles.getPuzzle(puzzleNumber));
		visitedPieces = new HashMap<Piece, Boolean>();
		movesTaken = new LinkedList<String>();
		game = new Game(board, puzzleNumber);
		}
	
	public Solver() throws Exception{
		pieces = new ArrayList<Piece>(Puzzles.getPuzzle(puzzleNumber));
		visitedPieces = new HashMap<Piece, Boolean>();
		movesTaken = new LinkedList<String>();
		game = new Game(board, puzzleNumber);
		}
	
	
	public void puzzleDepthSearch() {
//		 for (all bunnys in pieces){
//		 	visitedPieces.put(bunny);
//		  	bunnyDepthSearch(bunny.getCoord);
//		}
//		 
//		 
//		 
	}
	
	public String toString() {
		return null;
	}
}
