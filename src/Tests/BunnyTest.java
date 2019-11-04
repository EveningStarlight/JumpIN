package Tests;


import Model.*;
import Pieces.*;

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
public class BunnyTest {
	
	Bunny bun;
	
	/**
	* Method that runs before all the test methods 
	* to set up a test bunny object to test
	*/
	@Before
	public void setUp() throws Exception {
		bun = new Bunny(new Coord(3, 3));
	}

	/**
	 * Method to test the isValidMove method in the Bunny class
	 */
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

	/**
	 * Method to test the setCoord method in the Bunny class
	 */
	@Test
	public void testSetCoord() {
		assertNotNull(bun);
		assertEquals(new Coord(3,3), bun.getCoord());
		bun.setCoord(new Coord(2,1));
		assertEquals(new Coord(2,1),bun.getCoord());
		
	}

	/**
    * Method to test the Game constructor
    */
	@Test
	public void testBunny() {
		Coord coordinate  = new Coord(4, 2);
		Bunny testBun = new Bunny(coordinate);
		assertNotNull(testBun);
		assertEquals(coordinate, testBun.getCoord());
	}

}
