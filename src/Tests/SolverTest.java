package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GUI.TextTile;
import Model.*;
import Solver.Solver;

/**
 * 
 * @author Jay McCracken	Adam Prins
 * 			101 066 860		100 879 683
 * 
 * @version 1.0.0
 * 		First implementation of all tests
 *
 */
public class SolverTest {
	
	TextTile board[][];
	Game game;
	Solver solver1;
	Move move1;
	Move move2;
	
	
	@Before
	public void setup() throws Exception {
		board = new TextTile[Game.BOARD_SIZE][Game.BOARD_SIZE];
		for (int x=0; x<Game.BOARD_SIZE; x++) {
			for (int y=0; y<Game.BOARD_SIZE; y++) {
				 board[x][y]= new TextTile(new Coord(x,y));
			}
		}
		game = new Game(board);
		game.setBoard(Puzzles.getPuzzle(2));
		solver1 = new Solver(3);
		move1 = new Move (new Coord(3,1), new Coord(0,1));
		move2 = new Move (new Coord(0,2), new Coord(0,0));
	}
	
	/**
	 * tests that the solvers are constructed successfully.
	 */
	@Test
	public void testSolver() {
		try {
		
		Solver solver2 = new Solver(game);
		
		assertFalse(solver1==null);
		assertFalse(solver2==null);
		} catch( Exception e ) {
			fail("caught an Exception: " + e.getMessage());
		}
	}
	
	/**
	 * tests that the path the solver finds is correct
	 */
	@Test
	public void testPuzzleBredthSearch() {
		ArrayList<Move> path;
		try {
			path = solver1.puzzleBreadthSearch();
			assertEquals(2,path.size());
			assertEquals(move1, path.get(0));
			assertEquals(move2, path.get(1));
		} catch (Exception e) {
			fail("caught an Exception: " + e.getMessage());
		}
	}
	
	/**
	 * tests that the toString method works
	 */
	@Test
	public void testToString() {
		try {
			String s = move1.toString() + "\n" + move2.toString() + "\n";
			assertEquals(s,solver1.toString());
		} catch (Exception e) {
			fail("caught an Exception: " + e.getMessage());
		}
	}

}
