package Solver;

import java.util.ArrayList;

import GUI.TextTile;
import GUI.Tile;
import Model.Coord;
import Model.Game;
import Model.Move;
import Model.Puzzles;
import Pieces.*;

/**
 * The solver for the puzzles, with hints and how to solve each puzzle
 * using Depth-First Search
 * @authors Jay McCracken, Matthew Harris
 * 			101066860       101073502
 * @version 1.1.3
 * 		(repush) updated toString() to pass the entire path instead of only the first
 * 		removed unused fields and imports
 */
public class Solver {

	private ArrayList<Piece> pieces;
	private Game solverGame;
	private Tile[][] board;
	
	/**
	 * 
	 * @param puzzleNumber
	 * @throws Exception
	 */
	public Solver(int puzzleNumber) throws Exception{
		board = new TextTile[Game.BOARD_SIZE][Game.BOARD_SIZE];
		pieces = new ArrayList<Piece>(Puzzles.getPuzzle(puzzleNumber));
		for (int x=0; x<Game.BOARD_SIZE; x++) {
			for (int y=0; y<Game.BOARD_SIZE; y++) {
				 board[x][y]= new TextTile(new Coord(x,y));
			}
		}
		solverGame = new Game(board, puzzleNumber);
		}
	
	/**
	 * 
	 * @param game
	 * @throws Exception
	 */
	public Solver(Game game) throws Exception{
		board = new TextTile[Game.BOARD_SIZE][Game.BOARD_SIZE];
		pieces = new ArrayList<Piece>(game.getBoard());
		for (int x=0; x<Game.BOARD_SIZE; x++) {
			for (int y=0; y<Game.BOARD_SIZE; y++) {
				 board[x][y]= new TextTile(new Coord(x,y));
			}
		}
		solverGame = new Game(board, 1);
		solverGame.setBoard(pieces);
		}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Move> puzzleBreadthSearch() throws Exception{
		ArrayList<ArrayList<Move>> superMoves = new ArrayList<ArrayList<Move>>();
		superMoves.add(new ArrayList<Move>());
		int i=0;
		do {
			doAllMoves(superMoves.get(i));
			ArrayList<Move> moves = avaliableMoves();
			for (int j=0; j<moves.size(); j++){
				ArrayList<Move> currMove = (ArrayList<Move>) superMoves.get(i).clone();
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
	
	/**
	 * 
	 * @param arrayList
	 * @throws Exception
	 */
	private void doAllMoves(ArrayList<Move> arrayList) throws Exception{
		for (Move move: arrayList) {
			solverGame.selectTile(move.COORD_OLD);
			solverGame.selectTile(move.COORD_NEW);
		}
	}

	/**
	 * 
	 * @return
	 */
	private ArrayList<Move> avaliableMoves() {
		ArrayList<Move> allMoves = new ArrayList<Move>();
		for(int i = 0; i<pieces.size();i++){
			Piece piece = pieces.get(i);
			for(int j = 0; j < 5;j++) {
				Coord x = new Coord(j, piece.getCoord().y);
				Coord y = new Coord(piece.getCoord().x, j);
				if(solverGame.canSwapPiece(piece.getCoord(), x) && !piece.getCoord().equals(x)) {
					allMoves.add(new Move(piece.getCoord(),x));
				}
				if(solverGame.canSwapPiece(piece.getCoord(), y) && !piece.getCoord().equals(y)) {
					allMoves.add(new Move(piece.getCoord(),y));
				}
			}
			}
		return allMoves;	
	}
	
	/**
	 * Gets the next move to help solve the puzzle as according to the solver
	 * @return next Move needed to solve the puzzle
	 */
	public Move getNextMove() {
		ArrayList<Move> path;
		try {
			path = puzzleBreadthSearch();
			return path.get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		throw new RuntimeException("Failed to find solution");
	}
	
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		ArrayList<Move> path;
		try {
			String s = "";
			path = puzzleBreadthSearch();
			for (Move move:path) {
				s+=move.toString()+"\n";
			}
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
