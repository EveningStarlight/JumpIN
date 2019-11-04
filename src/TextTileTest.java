import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the TextTile class 
 * 
 * @author Matthew Harris,
 * 			101 073 502
 *
 */
public class TextTileTest {
	
	TextTile textTile;
	Bunny bun;
	
	/**
	* Method that runs before all the test methods 
	* to set up a test coord object to test
	*/
	@Before
	public void setUp() throws Exception {
		textTile = new TextTile(new Coord(2, 2));
		bun = new Bunny(textTile.getCoord());
		textTile.setPiece(bun);
	}

	/**
	* Method to test the TextTile constructor
	*/
	@Test
	public void testTextTile() {
		TextTile textTile1 = new TextTile(new Coord(3, 3));
		assertNotNull(textTile1);
		assertEquals(new Coord(3, 3), textTile1.getCoord());
	}

	/**
	 * Method to test the getPiece method in the TextTile class
	 */
	@Test
	public void testGetPiece() {
		assertNotNull(textTile);
		assertEquals(bun, textTile.getPiece());
	}

	/**
	 * Method to test the setPiece method in the TextTile class
	 */
	@Test
	public void testSetPiece() {
		Bunny bun2 = new Bunny(new Coord(2, 1));
		assertNotNull(textTile);
		assertEquals(bun, textTile.getPiece());
		textTile.setPiece(bun2);
		assertEquals(bun2, textTile.getPiece());
	}

	/**
	 * Method to test the isEmpty method in the TextTile class
	 */
	@Test
	public void testIsEmpty() {
		TextTile textTile1 = new TextTile(new Coord(3, 3));
		assertNotNull(textTile1);
		assertTrue(textTile1.isEmpty());
		textTile1.setPiece(bun);
		assertFalse(textTile1.isEmpty());
	}

	/**
	 * Method to test the getCoord method in the TextTile class
	 */
	@Test
	public void testGetCoord() {
		assertNotNull(textTile);
		Coord coord = new Coord(2, 2);
		assertEquals(coord, textTile.getCoord());
	}

	/**
	 * Method to test the removePiece method in the TextTile class
	 */
	@Test
	public void testRemovePiece() {
		assertNotNull(textTile);
		assertFalse(textTile.isEmpty());
		textTile.removePiece();
		assertTrue(textTile.isEmpty());
	}

}
