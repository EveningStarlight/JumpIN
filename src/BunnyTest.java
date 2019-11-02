import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class BunnyTest {
	
	Bunny bun;
	@Before
	public void setUp() throws Exception {
		bun = new Bunny(new Coord(3, 3));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsValidMove() {
		assertNotNull(bun);
		assertEquals(new Coord(3,3), bun.getCoord());
		assertFalse(bun.isValidMove(new Coord(2,3)));
		assertFalse(bun.isValidMove(new Coord(3,2)));
		assertFalse(bun.isValidMove(new Coord(4,3)));
		assertFalse(bun.isValidMove(new Coord(3,4)));
		assertFalse(bun.isValidMove(new Coord(4,4)));
		assertFalse(bun.isValidMove(new Coord(2,2)));
		assertFalse(bun.isValidMove(new Coord(2,4)));
		assertFalse(bun.isValidMove(new Coord(4,2)));
		bun.setCoord(new Coord(2, 2));
		assertEquals(new Coord(2,2), bun.getCoord());
		assertFalse(bun.isValidMove(new Coord(1,1)));
		assertFalse(bun.isValidMove(new Coord(4,0)));
		assertFalse(bun.isValidMove(new Coord(0,4)));
		assertFalse(bun.isValidMove(new Coord(4,4)));
		assertTrue(bun.isValidMove(new Coord(2,0)));
		assertTrue(bun.isValidMove(new Coord(4,2)));
		assertTrue(bun.isValidMove(new Coord(2,4)));
		assertTrue(bun.isValidMove(new Coord(0,2)));
		
	}

	@Test
	public void testSetCoord() {
		assertNotNull(bun);
		assertEquals(new Coord(3,3), bun.getCoord());
		bun.setCoord(new Coord(2,1));
		assertEquals(new Coord(2,1),bun.getCoord());
		
	}

	@Test
	public void testBunny() {
		Coord coordinate  = new Coord(4, 2);
		Bunny testBun = new Bunny(coordinate);
		assertNotNull(testBun);
		assertEquals(coordinate, testBun.getCoord());
	}

}
