package Solver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import GUI.Tile;
import Model.Coord;
import Model.Game;
import Model.Move;
import Model.Puzzles;
import Pieces.Bunny;
import Pieces.Fox;
import Pieces.Piece;

/**
 * The solver for the puzzles, with hints and how to solve each puzzle
 * using Depth-First Search
 * @authors Jay McCracken, Matthew Harris
 * 			101066860       101073502
 * @version 1.0.1
 * 	constructor created
 */
public class Solver {

	private ArrayList<Piece> pieces;
	private HashMap<Piece, Boolean> visitedPieces;
	private Game solverGame;
	private Tile[][] board;
	private Queue<String> movesTaken;
	
	public Solver(int puzzleNumber) throws Exception{
		pieces = new ArrayList<Piece>(Puzzles.getPuzzle(puzzleNumber));
		visitedPieces = new HashMap<Piece, Boolean>();
		for(int i = 0; i<pieces.size();i++){
			visitedPieces.put(pieces.get(i),false);
		}
		movesTaken = new LinkedList<String>();
		solverGame = new Game(board, puzzleNumber);
		}
	
	public Solver(Game game) throws Exception{
		pieces = new ArrayList<Piece>(game.getBoard());
		visitedPieces = new HashMap<Piece, Boolean>();
		for(int i = 0; i<pieces.size();i++){
			visitedPieces.put(pieces.get(i),false);
		}
		movesTaken = new LinkedList<String>();
		solverGame = new Game(board, 0 );
		solverGame.setBoard(pieces);
		}
	
	
	public void puzzleBreadthSearch() {
		visitedPieces.replace(pieces.get(0), true);
		ArrayList<Move> moves;
		for(int i =0; i< visitedPieces.size(); i++) {
			moves = avaliableMoves((Piece) visitedPieces.keySet().toArray()[i]);
			while(!moves.isEmpty()){
				if(solverGame.endGame()){
					break;
				}
			}	
		}
		
	}
	
	public ArrayList<Move> avaliableMoves(Piece piece) {
		ArrayList<Move> moves = new ArrayList<Move>();
			for(int i = 1; i < 5;i++) {
				Coord left = new Coord((piece.getCoord().x-i), piece.getCoord().y);
				Coord right = new Coord((piece.getCoord().x+i), piece.getCoord().y);
				Coord up = new Coord((piece.getCoord().x), piece.getCoord().y-i);
				Coord down = new Coord((piece.getCoord().x), piece.getCoord().y+i);
				if(piece.isValidMove(left)) {
					moves.add(new Move(piece.getCoord(),left));
				}
				if(piece.isValidMove(right)) {
					moves.add(new Move(piece.getCoord(),right));
				}
				if(piece.isValidMove(up)) {
					moves.add(new Move(piece.getCoord(),up));
				}
				if(piece.isValidMove(down)) {
					moves.add(new Move(piece.getCoord(),down));
				}
			}
		return moves;	
	}
	
	
	
	public String toString() {
		return null;
	}
}
