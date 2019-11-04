import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the Fox class 
 * 
 * @author Matthew Harris,
 * 			101 073 502
 *
 */
public class FoxTest {
	
	Fox fox;

	/**
	* Method that runs before all the test methods 
	* to set up a test Fox object to test
	*/
	@Before
	public void setUp() throws Exception {
		fox = new Fox(new Coord(2, 1),new Coord(3, 1));
	}

	/**
	 * Method to test the isValidMove method in the Fox class
	 */
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

	/**
	 * Method to test the setCoord method in the Fox class
	 */
	@Test
	public void testSetCoord() {
		assertNotNull(fox);
		assertEquals(new Coord(2,1), fox.getCoord());
		assertEquals(new Coord(3,1), fox.getTail());
		fox.setCoord(new Coord(0, 1));
		assertEquals(new Coord(0,1), fox.getCoord());
		assertEquals(new Coord(1,1), fox.getTail());
	}

	/**
	 * Method to test the getCoord method in the Fox class
	 */
	@Test
	public void testGetCoord() {
		assertNotNull(fox);
		Coord coord = new Coord(2, 1);
		assertEquals(coord, fox.getCoord());
	}

	/**
	* Method to test the Fox constructor
	* @throws Exception
	*/
	@Test
	public void testFox() throws Exception {
		Fox fox1 = new Fox(new Coord(2, 1),new Coord(3, 1));
		assertNotNull(fox1);
		Fox fox2 = new Fox(new Coord(1, 1),new Coord(1, 2));
		assertNotNull(fox2);
	}
	
	/**
	* Method to test the Fox constructor in the case of illegal arguments
	* @throws Exception
	*/
	@Test(expected = IllegalArgumentException.class)
	public void testFoxIllegal() throws Exception {
		Fox fox1 = new Fox(new Coord(2, 1),new Coord(4, 1));
		assertNotNull(fox1);
	}

	/**
	 * Method to test the getCoord method in the Fox class
	 */
	@Test
	public void testGetTail() {
		assertNotNull(fox);
		Coord coord = new Coord(3, 1);
		assertEquals(coord, fox.getTail());
	}

}
