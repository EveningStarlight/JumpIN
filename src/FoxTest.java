import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class FoxTest {
	
	Fox fox;

	@Before
	public void setUp() throws Exception {
		fox = new Fox(new Coord(2, 1),new Coord(3, 1));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsValidMove() {
		assertNotNull(fox);
		assertEquals(new Coord(2,1), fox.getCoord());
		assertEquals(new Coord(3,1), fox.getTail());
		assertFalse(fox.isValidMove(new Coord(4, 1)));
		assertFalse(fox.isValidMove(new Coord(2, 0)));
		assertFalse(fox.isValidMove(new Coord(2, 2)));
		assertFalse(fox.isValidMove(new Coord(2, 3)));
		assertFalse(fox.isValidMove(new Coord(2, 4)));
		assertFalse(fox.isValidMove(new Coord(1, 0)));
		assertTrue(fox.isValidMove(new Coord(0, 1)));
	}

	@Test
	public void testSetCoord() {
		assertNotNull(fox);
		assertEquals(new Coord(2,1), fox.getCoord());
		assertEquals(new Coord(3,1), fox.getTail());
		fox.setCoord(new Coord(0, 1));
		assertEquals(new Coord(0,1), fox.getCoord());
		assertEquals(new Coord(1,1), fox.getTail());
	}

	@Test
	public void testGetCoord() {
		assertNotNull(fox);
		Coord coord = new Coord(2, 1);
		assertEquals(coord, fox.getCoord());
	}

	@Test
	public void testFox() throws Exception {
		Fox fox1 = new Fox(new Coord(2, 1),new Coord(3, 1));
		assertNotNull(fox1);
		Fox fox2 = new Fox(new Coord(1, 1),new Coord(1, 2));
		assertNotNull(fox2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFoxIllegal() throws Exception {
		Fox fox1 = new Fox(new Coord(2, 1),new Coord(4, 1));
		assertNotNull(fox1);
	}

	@Test
	public void testGetTail() {
		assertNotNull(fox);
		Coord coord = new Coord(3, 1);
		assertEquals(coord, fox.getTail());
	}

}
