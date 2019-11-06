package Solver;

import java.util.ArrayList;

import GUI.ButtonTile;
import GUI.Tile;
import Model.Game;
import Model.Puzzles;
import Pieces.Piece;

/**
 * The solver for the puzzles, with hints and how to solve each puzzle
 * @authors Jay McCracken, Matthew Harris
 * 			101066860       101073502
 */
public class Solver {

	private ArrayList<Piece> pieces;
	private Game game;
	private Tile[][] board;
	
	public Solver(int puzzleNumber) throws Exception{
		pieces = new ArrayList<Piece>(Puzzles.getPuzzle(puzzleNumber));
		game = new Game(board, puzzleNumber);
		}
	
}
