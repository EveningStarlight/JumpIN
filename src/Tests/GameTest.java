package Tests;


import GUI.*;
import Model.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the Game class 
 * 
 * @author Matthew Harris,
 * 			101 073 502
 *
 */
public class GameTest {

    private Game game = null;
    private TextTile[][] board = null;
    
   /**
    * Method that runs before all the test methods 
    * to set up a test game to test
    */
    @Before
    public void setUp() throws Exception{
        board = new TextTile[5][5];
        for (int x=0; x<5; x++) {
            for (int y=0; y<5; y++) {
                board[x][y]= new TextTile(new Coord(x,y));
            }
        }
        game = new Game(board,1);
    }

    /**
     * Method to test the Game constructor
     * @throws Exception
     */
	@Test
	public void testGame() throws Exception {
		Game testGame = new Game(board,1);
		assertNotNull(testGame);
	}

	/**
	 * Method to test the selectTile method in the Game class
	 * @throws Exception
	 */
	@Test
	public void testSelectTile() throws Exception {
		Coord coordinate = new Coord(2,0);
        game.selectTile(coordinate);
        assertEquals(coordinate, game.getSelectedTile().getCoord());
		game.selectTile(coordinate);
		assertNull(game.getSelectedTile());
	}
	
	/**
	 * Method to test the selectTile method in the Game class
	 * for the case that the tile is unselectable
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSelectTileUnselectableTile() throws Exception{
		Coord coordinate = new Coord(2,1);
		game.selectTile(coordinate);
	}
	
	/**
	 * Method to test the selectTile method in the Game class
	 * for the case that the coordinate is game of the range 
	 * of the game board
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSelectTileTileOutOfRange() throws Exception{
		Coord coordinate = new Coord(5,5);
		game.selectTile(coordinate);
	}

	/**
	 * Method to test the getTile method in the Game class
	 * @throws Exception
	 */
	@Test
	public void testGetTile() {
		Coord coordinate = new Coord(2,0);
		assertEquals(board[2][0], game.getTile(coordinate));
	}
	
	/**
	 * Method to test the setBoard method in the Game class
	 */
	@Test
	public void testSetBoard() {
		game.setBoard(Puzzles.getPuzzle(2));
		Coord coordBun1 = new Coord(0,2);
		Coord coordBun2 = new Coord(4,2);
		Coord coordShr1 = new Coord(1,2);
		Coord coordShr2 = new Coord(4,1);
		assertEquals(board[0][2].getPiece(), game.getTile(coordBun1).getPiece());
		assertEquals(board[4][2].getPiece(), game.getTile(coordBun2).getPiece());
		assertEquals(board[1][2].getPiece(), game.getTile(coordShr1).getPiece());
		assertEquals(board[4][1].getPiece(), game.getTile(coordShr2).getPiece());
	}

	/**
	 * Method to test the endGame method in the Game class
	 * @throws Exception 
	 */
	@Test
	public void testEndGame() throws Exception {
		assertNotNull(game);
		assertFalse(game.endGame());
		game.selectTile(new Coord(2, 0));
		game.selectTile(new Coord(2, 2));
		assertTrue(game.endGame());
	}

}
