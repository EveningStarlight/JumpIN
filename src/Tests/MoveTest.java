package Tests;

import Model.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Test cases for the Move class
 * 
 * @author Adam Prins
 * 			100 879 683
 * @version 1.0.0
 * 		First implementation of Move tests
 *
 */
public class MoveTest {

	Coord coord1;
	Coord coord2;
	
	Move move1;
	Move move2;
	Move move3;
	Move move4;
	
	/**
	* Method that runs before all the test methods 
	* to set up a test coord object to test
	*/
	@Before
	public void setUp() throws Exception {
		coord1 = new Coord(0,0);
		coord2 = new Coord(0,1);
		move1 = new Move(coord1, coord2 ); //base move
		move2 = new Move(coord1, coord2 ); //equal to move1
		move3 = new Move(coord2, coord1 ); //reverse of move1
		move4 = new Move( new Coord(3,1), new Coord(3,4)); //no relation to move1
	}

	/**
	* Method to test the Move constructor
	*/
	@Test
	public void testMove() {
		assertFalse(move1==null);
		assertFalse(move2==null);
		assertFalse(move3==null);
		assertFalse(move4==null);
		
		assertEquals(coord1,move1.COORD_OLD);
		assertEquals(coord2,move1.COORD_NEW);
		
		assertEquals(coord2,move3.COORD_OLD);
		assertEquals(coord1,move3.COORD_NEW);
		
	}

	/**
	 * Method to test the equals Object method in the Move class
	 */
	@Test
	public void testEqualsObject() {
		assertTrue(move1.equals(move2));
	}
	
	/**
	 * Method to test the isReverseMove
	 */
	@Test
	public void testIsReverseMove() {
		assertTrue(move1.isReverseMove(move3));
	}

	/**
	 * Method to test the toString method in the Move class
	 */
	@Test
	public void testToString() {
		assertEquals("(0,0) to (0,1)",move1.toString());
		assertEquals("(0,1) to (0,0)",move3.toString());
	}
}
