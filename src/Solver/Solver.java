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
 * @version 1.3.0
 * 		Generalized the Constructors
 */
public class Solver {

	private ArrayList<Piece> pieces;
	private Game solverGame;
	private Tile[][] board;
	
	/**
	 * constructor for the solver, creation of a new game and board
	 * @param piecesArg the ArrayList of Pieces that will populate the board
	 */
	public Solver(ArrayList<Piece> piecesArg) throws Exception{
		board = Game.buildBoard();
		pieces = new ArrayList<Piece>(piecesArg);
		solverGame = new Game(board);
		solverGame.setBoard(pieces);
	}
	
	/**
	 * @param puzzleNumber The puzzle number that will populate the board
	 */
	public Solver(int puzzleNumber) throws Exception{
		this(Puzzles.getPuzzle(puzzleNumber));
	}
	
	/**
	 * @param game The game that for which you want to solve
	 */
	public Solver(Game game) throws Exception{
		this(new ArrayList<Piece>(game.getBoard()));
	}
	
	
	/**
	 * Search function, using breadth first method, stores a array list of moves
	 * that will be the final solution. it checks all the avaliable moves and undoes the
	 * move it it wont work.
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Move> puzzleBreadthSearch() throws Exception{
		ArrayList<ArrayList<Move>> superMoves = new ArrayList<ArrayList<Move>>();
		superMoves.add(new ArrayList<Move>());
		int i=0;
		do {
			doAllMoves(superMoves.get(i));
			
			Move previousMove;
			previousMove=null;
			if (superMoves.get(i).size()==0)  previousMove=null;
			else  previousMove=superMoves.get(i).get(superMoves.get(i).size()-1);
			
			ArrayList<Move> moves = avaliableMoves(previousMove);
			for (int j=0; j<moves.size(); j++){
				ArrayList<Move> currMove = (ArrayList<Move>) superMoves.get(i).clone();
				currMove.add(moves.get(j));
				superMoves.add(currMove);
				try{
					solverGame.selectTile(currMove.get(currMove.size()-1).COORD_OLD);
					solverGame.selectTile(currMove.get(currMove.size()-1).COORD_NEW);
				}
				catch(Exception e){}
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
			if(superMoves.size()>10000){
				break;
			}
		}while (i<superMoves.size());
		return null;
	}
	
	/**
	 * Does all the move in the array list passed to it
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
	 * Finds all the avaliable moves a current piece can take, 
	 * returns an array of moves
	 * @return
	 */
	private ArrayList<Move> avaliableMoves(Move previousMove) {
		ArrayList<Move> allMoves = new ArrayList<Move>();
		for(int i = 0; i<pieces.size();i++){
			Piece piece = pieces.get(i);
			for(int j = 0; j < 5;j++) {
				Move x = new Move(piece.getCoord(),new Coord(j, piece.getCoord().y));
				Move y = new Move(piece.getCoord(),new Coord(piece.getCoord().x, j));
				addPossibleMove(allMoves, previousMove, x);
				addPossibleMove(allMoves, previousMove, y);
			}
		}
		return allMoves;	
	}
	
	/**
	 * after a move is made see if that piece has any new moves that could be possiable
	 * @param allMoves
	 * @param previousMove
	 * @param move
	 * @return
	 */
	private ArrayList<Move> addPossibleMove(ArrayList<Move> allMoves, Move previousMove,  Move move) {
		boolean validSwap = solverGame.canSwapPiece(move.COORD_OLD, move.COORD_NEW);
		boolean notSameCoord = !move.COORD_OLD.equals(move.COORD_NEW);
		
		if (previousMove==null) {
			if (validSwap && notSameCoord) allMoves.add(move);
		}
		else {
			boolean notReverseMove = !move.isReverseMove(previousMove);
			Piece newPiece = solverGame.getTile(move.COORD_OLD).getPiece();
			Piece oldPiece = solverGame.getTile(previousMove.COORD_NEW).getPiece();
			boolean notSameFox = !((newPiece instanceof Fox) && newPiece.equals(oldPiece));
			
			if (validSwap && notSameCoord && notReverseMove && notSameFox) allMoves.add(move);
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
	 * converting the arraylist of moves to strings for printing reasons
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
