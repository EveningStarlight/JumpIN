package Tests;


import Model.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the Bunny class 
 * 
 * @author Matthew Harris,
 * 			101 073 502
 *
 */
public class CoordTest {

	Coord coord1;
	
	/**
	* Method that runs before all the test methods 
	* to set up a test coord object to test
	*/
	@Before
	public void setUp() throws Exception {
		coord1 = new Coord(2, 2);
	}

	/**
	* Method to test the Coord constructor
	*/
	@Test
	public void testCoord() {
		Coord coord1 = new Coord(1, 2);
		assertEquals(1, coord1.x);
		assertEquals(2, coord1.y);
	}
	
	/**
	* Method to test the Coord constructor using a illegal argument
	*/
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalCoord() {
		Coord coord2 = new Coord(5, 5);
	}

	/**
	 * Method to test the equalsObject method in the Coord class
	 */
	@Test
	public void testEqualsObject() {
		Coord coord2 = new Coord(2, 2);
		Coord coord3 = new Coord(3, 3);
		assertTrue(coord1.equals(coord2));
		assertFalse(coord1.equals(coord3));
	}

	/**
	 * Method to test the toString method in the Coord class
	 */
	@Test
	public void testToString() {
		assertNotNull(coord1);
		assertTrue(coord1.equals(new Coord(2, 2)));
		assertEquals("(2,2)", coord1.toString());
	}

	/**
	 * Method to test the isHole method in the Coord class
	 */
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
