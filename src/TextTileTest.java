import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class TextTileTest {
	
	TextTile textTile;
	Bunny bun;
	@Before
	public void setUp() throws Exception {
		textTile = new TextTile(new Coord(2, 2));
		bun = new Bunny(textTile.getCoord());
		textTile.setPiece(bun);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTextTile() {
		TextTile textTile1 = new TextTile(new Coord(3, 3));
		assertNotNull(textTile1);
		assertEquals(new Coord(3, 3), textTile1.getCoord());
	}

	@Test
	public void testGetPiece() {
		assertNotNull(textTile);
		assertEquals(bun, textTile.getPiece());
	}

	@Test
	public void testSetPiece() {
		Bunny bun2 = new Bunny(new Coord(2, 1));
		assertNotNull(textTile);
		assertEquals(bun, textTile.getPiece());
		textTile.setPiece(bun2);
		assertEquals(bun2, textTile.getPiece());
	}

	@Test
	public void testIsEmpty() {
		TextTile textTile1 = new TextTile(new Coord(3, 3));
		assertNotNull(textTile1);
		assertTrue(textTile1.isEmpty());
		textTile1.setPiece(bun);
		assertFalse(textTile1.isEmpty());
	}

	@Test
	public void testGetCoord() {
		assertNotNull(textTile);
		Coord coord = new Coord(2, 2);
		assertEquals(coord, textTile.getCoord());
	}

	@Test
	public void testRemovePiece() {
		assertNotNull(textTile);
		assertFalse(textTile.isEmpty());
		textTile.removePiece();
		assertTrue(textTile.isEmpty());
	}

}
