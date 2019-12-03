package Tests;


import GUI.*;
import Model.*;
import Pieces.*;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Test cases for the Game class 
 * 
 * @author Matthew Harris, Jay McCracken, 	Adam  Prins
 * 			101 073 502,   101 066 860		100 879 683
 * 
 * @version 1.2.0
 * 		Added 5 methods for testing piece swapping
 * 		testSelectTileDestinationFull()
 * 		testSelectTileBunnyOverEmptyTiles()
 * 		testSelectTileFoxOverFullTiles()
 * 		testSelectTileFoxTailFullTile()
 * 		testSelectTileFoxSelectLastTile()
 *
 */
public class GameTest {

    private Game game = null;
    private TextTile[][] board = null;
    private ArrayList<Piece> original;
	private ArrayList<Piece> tester;
	private static final File SAVED_STATE = new File ("src/Model/SavedState.xml");
    
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
        game = new Game(board);
        game.setBoard(Puzzles.getPuzzle(1));
        original = Puzzles.getPuzzle(2);
		tester = Puzzles.getPuzzle(2);
    }

    /**
     * Method to test the Game constructor
     * @throws Exception
     */
	@Test
	public void testGame() throws Exception {
		assertNotNull(game);
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
	 * Method to test the selectTile method in the Game class
	 * for the case that the destination already has a piece
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSelectTileDestinationFull() throws Exception{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Bunny(new Coord(0,0)));
		pieces.add(new Bunny(new Coord(0,1)));
		pieces.add(new Bunny(new Coord(0,2)));
		game.setBoard(pieces);
		
		game.selectTile(pieces.get(0).getCoord());
		game.selectTile(pieces.get(2).getCoord());
	}
	
	/**
	 * Method to test the selectTile method in the Game class
	 * for the case that a Bunny tries to go over an empty Tile
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSelectTileBunnyOverEmptyTiles() throws Exception{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Bunny(new Coord(0,0)));
		pieces.add(new Bunny(new Coord(0,1)));
		game.setBoard(pieces);
		
		game.selectTile(pieces.get(0).getCoord());
		game.selectTile(new Coord(0,4));
	}
	
	/**
	 * Method to test the selectTile method in the Game class
	 * for the case that a Fox tries to go over a full Tile
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSelectTileFoxOverFullTiles() throws Exception{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Bunny(new Coord(1,2)));
		pieces.add(new   Fox(new Coord(1,3),new Coord(1,4)));
		game.setBoard(pieces);
		
		game.selectTile(pieces.get(1).getCoord());
		game.selectTile(new Coord(1,0));
	}
	
	/**
	 * Method to test the selectTile method in the Game class
	 * for the case that a Fox tail tries to go in a Full location
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSelectTileFoxTailFullTile() throws Exception{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Bunny(new Coord(1,4)));
		pieces.add(new   Fox(new Coord(1,0),new Coord(1,1)));
		game.setBoard(pieces);
		
		game.selectTile(pieces.get(1).getCoord());
		game.selectTile(new Coord(1,3));
	}
	
	/**
	 * Method to test the selectTile method in the Game class
	 * for the case that a Fox tail tries to go in a Full location
	 * @throws Exception
	 */
	@Test
	public void testSelectTileFoxSelectLastTile() throws Exception{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Bunny(new Coord(3,4)));
		pieces.add(new   Fox(new Coord(1,0),new Coord(1,1)));
		game.setBoard(pieces);
		
		game.selectTile(pieces.get(1).getCoord());
		game.selectTile(new Coord(1,4));
		assertEquals(new Coord(1,3),pieces.get(1).getCoord());
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
	
	@Test
	public void testUndoMove() throws Exception {
		game.setBoard(tester);
		assertEquals(original.get(3).getCoord(), tester.get(3).getCoord());
		game.selectTile(new Coord(0, 2));
		game.selectTile(new Coord(0, 0));
		assertNotEquals(original.get(3).getCoord(), tester.get(3).getCoord());
		game.undoMove();
		assertEquals(original.get(3).getCoord(), tester.get(3).getCoord());
	}
	
	@Test
	public void testIsUndoEmpty() throws Exception {
		assertEquals(true, game.isUndoEmpty());
		game.setBoard(original);
		game.selectTile(new Coord(0, 2));
		game.selectTile(new Coord(0, 0));
		assertEquals(false, game.isUndoEmpty());
	}

	@Test
	public void testRedoMove() throws Exception {
		testUndoMove();
		game.redoMove();
		assertNotEquals(original.get(3).getCoord(), tester.get(3).getCoord());
	}
	
	@Test
	public void testIsRedoEmpty() throws Exception {
		assertEquals(true, game.isRedoEmpty());
		game.setBoard(original);
		game.selectTile(new Coord(0, 2));
		game.selectTile(new Coord(0, 0));
		assertEquals(true, game.isRedoEmpty());
		game.undoMove();
		assertEquals(false, game.isRedoEmpty());
	}
	
	@Test
	public void testSaveState(){
		game.saveState(0);
		int puzzleNumber=56456565;
		try{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(SAVED_STATE);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("PuzzleNumber");
        Node nNode = nList.item(0);
        if(nNode.getNodeType() == Node.ELEMENT_NODE){
       	 Element eElement = (Element) nNode;
       	 
       	 puzzleNumber = Integer.parseInt(nNode.getTextContent());
        }
		}
		catch(Exception e){
			fail("Created exception");
		}
		assertEquals(0, puzzleNumber);
	}
	
	@Test
	public void testLoadState(){
		game.saveState(0);
		assertEquals(0, game.loadState());
	}
	
}
