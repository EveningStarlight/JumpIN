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
	private ArrayList<Piece> visitedPieces;
	private Game solverGame;
	private Tile[][] board;
	private Queue<String> movesTaken;
	
	public Solver(int puzzleNumber) throws Exception{
		pieces = new ArrayList<Piece>(Puzzles.getPuzzle(puzzleNumber));
		visitedPieces = new ArrayList<Piece>();
		for(int i = 0; i<pieces.size();i++){
			visitedPieces.add(pieces.get(i));
		}
		movesTaken = new LinkedList<String>();
		solverGame = new Game(board, puzzleNumber);
		}
	
	public Solver(Game game) throws Exception{
		pieces = new ArrayList<Piece>(game.getBoard());
		visitedPieces = new ArrayList<Piece>();
		for(int i = 0; i<pieces.size();i++){
			visitedPieces.add(pieces.get(i));
		}
		movesTaken = new LinkedList<String>();
		solverGame = new Game(board, 0 );
		solverGame.setBoard(pieces);
		}
	
	
	public ArrayList<Move> puzzleBreadthSearch() throws Exception {
		ArrayList<ArrayList<Move>> superMoves = new ArrayList<ArrayList<Move>>();
		superMoves.add(new ArrayList<Move>());
		int i=0;
		do {
			doAllMoves(superMoves.get(i));
			ArrayList<Move> moves=avaliableMoves(pieces.get(i));
			for (int j=0; j<moves.size(); j++) {
				ArrayList<Move> currMove=(ArrayList<Move>) superMoves.get(i).clone();
				currMove.add(moves.get(j));
				superMoves.add(currMove);
			}
		i++;
		}while (i<superMoves.size());
		return null;
	}
	
	private void doAllMoves(ArrayList<Move> arrayList) {
		
	}

	public ArrayList<Move> avaliableMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int i = 0; i<pieces.size();i++){
			Piece piece = pieces.get(i);
			for(int j = 0; j < 5;j++) {
				Coord x = new Coord(j, piece.getCoord().y);
				Coord y = new Coord(piece.getCoord().x, j);
				if() {
					moves.add(new Move(piece.getCoord(),left));
				}
				if(piece.isValidMove(right)) {
					moves.add(new Move(piece.getCoord(),right));
				}
			}
			}
		return moves;	
	}
	
	
	
	public String toString() {
		return null;
	}
}
