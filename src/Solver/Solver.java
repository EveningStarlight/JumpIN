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
		solverGame = new Game(board, 0);
		solverGame.setBoard(pieces);
		}
	
	
	public ArrayList<Move> puzzleBreadthSearch() throws Exception{
		ArrayList<ArrayList<Move>> superMoves = new ArrayList<ArrayList<Move>>();
		superMoves.add(new ArrayList<Move>());
		int i=0;
		do {
			doAllMoves(superMoves.get(i));
			ArrayList<Move> moves=avaliableMoves();
			for (int j=0; j<moves.size(); j++){
				ArrayList<Move> currMove=(ArrayList<Move>) superMoves.get(i).clone();
				currMove.add(moves.get(j));
				superMoves.add(currMove);
				try{
				solverGame.selectTile(currMove.get(currMove.size()-1).COORD_OLD);
				solverGame.selectTile(currMove.get(currMove.size()-1).COORD_NEW);
				}
				catch(Exception e){
					
				}
				if(solverGame.endGame()){
					return currMove;
				}
				else{
					solverGame.undoMove();
				}
			}
			while(!solverGame.isUndoEmpty()){
				solverGame.undoMove();
			}
			i++;
			if(superMoves.size()>1000){
				System.out.println("NOT FAR ENOUGH");
				break;
			}
		}while (i<superMoves.size());
		return null;
	}
	
	private void doAllMoves(ArrayList<Move> arrayList) throws Exception{
		for (Move move: arrayList) {
			solverGame.selectTile(move.COORD_OLD);
			solverGame.selectTile(move.COORD_NEW);
		}
	}

	private ArrayList<Move> avaliableMoves() {
		ArrayList<Move> allMoves = new ArrayList<Move>();
		for(int i = 0; i<pieces.size();i++){
			Piece piece = pieces.get(i);
			for(int j = 0; j < 5;j++) {
				Coord x = new Coord(j, piece.getCoord().y);
				Coord y = new Coord(piece.getCoord().x, j);
				if(solverGame.canSwapPiece(piece.getCoord(), x)) {
					allMoves.add(new Move(piece.getCoord(),x));
				}
				if(solverGame.canSwapPiece(piece.getCoord(), y)) {
					allMoves.add(new Move(piece.getCoord(),y));
				}
			}
			}
		return allMoves;	
	}
	
	
	@Override
	public String toString() {
		ArrayList<Move> path;
		try {
			path = puzzleBreadthSearch();
			String s = path.get(0).toString();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
