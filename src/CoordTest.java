import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CoordTest {

	Coord coord1;
	@Before
	public void setUp() throws Exception {
		coord1 = new Coord(2, 2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCoord() {
		Coord coord1 = new Coord(1, 2);
		assertEquals(1, coord1.x);
		assertEquals(2, coord1.y);
	}
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalCoord() {
		Coord coord2 = new Coord(5, 5);
	}

	@Test
	public void testEqualsObject() {
		Coord coord2 = new Coord(2, 2);
		Coord coord3 = new Coord(3, 3);
		assertTrue(coord1.equals(coord2));
		assertFalse(coord1.equals(coord3));
	}

	@Test
	public void testToString() {
		assertNotNull(coord1);
		assertTrue(coord1.equals(new Coord(2, 2)));
		assertEquals("(2,2)", coord1.toString());
	}

	@Test
	public void testIsHole() {
		Coord coord1 = new Coord(0, 0);
		Coord coord2 = new Coord(4, 0);
		Coord coord3 = new Coord(0, 4);
		Coord coord4 = new Coord(4, 4);
		Coord coord5 = new Coord(2, 2);
		Coord coord6 = new Coord(3, 3);
		assertTrue(coord1.isHole());
		assertTrue(coord2.isHole());
		assertTrue(coord3.isHole());
		assertTrue(coord4.isHole());
		assertTrue(coord5.isHole());
		assertFalse(coord6.isHole());
	}

}
