import java.util.ArrayList;

/**
 * A collection of the puzzles that can be played through for the game JumpIN
 * 
 * @author Adam Prins, Matthew Harris
 * 			100 879 683, 101 073 502
 * @version 1.1.1
 * 		puzzle number 0 now correctly called for testing and doesn't throw exception. (1 wasn't an else if)
 *
 */
public class Puzzles {

	/**
	 * @param puzzleNumber the puzzle you would like to initialize
	 * @return returns an ArrayList of Pieces that can be used to populate the game board
	 * @throws Exception 
	 */
	public static ArrayList<Piece> getPuzzle(int puzzleNumber) throws Exception {
		ArrayList<Piece> array = new ArrayList<Piece>();
		try {
			if (puzzleNumber==0) { // Testing puzzle number. Not to be called while playing the game
			}
			else if (puzzleNumber==1) {
				array.add(new Mushroom(new Coord(2,1)));
				array.add(new Bunny(new Coord(2,0)));
			}
			else if (puzzleNumber==2) {
				array.add(new Mushroom(new Coord(3,2)));
				array.add(new Mushroom(new Coord(0,1)));
				array.add(new Bunny(new Coord(4,2)));
				array.add(new Bunny(new Coord(0,2)));
			}
			else if (puzzleNumber==3) {
				array.add(new Fox(new Coord(3,1), new Coord(4,1)));
				array.add(new Bunny(new Coord(0,2)));
			}
			else if (puzzleNumber==4) {
				array.add(new Fox(new Coord(3,1), new Coord(4,1)));
				array.add(new Fox(new Coord(1,0), new Coord(1,1)));
				array.add(new Mushroom(new Coord(2,3)));
				array.add(new Bunny(new Coord(0,2)));
			}
			else if (puzzleNumber==5) {
				array.add(new Fox(new Coord(3,3), new Coord(4,3)));
				array.add(new Fox(new Coord(1,0), new Coord(1,1)));
				array.add(new Mushroom(new Coord(0,2)));
				array.add(new Bunny(new Coord(0,3)));
			}
			else if (puzzleNumber==6) {
				array.add(new Fox(new Coord(3,1), new Coord(4,1)));
				array.add(new Fox(new Coord(1,0), new Coord(1,1)));
				array.add(new Mushroom(new Coord(0,2)));
				array.add(new Bunny(new Coord(0,3)));
				array.add(new Bunny(new Coord(2, 4)));
			}
			else {
				throw new IllegalArgumentException("The puzzle number: " + puzzleNumber + " is not in bounds");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return array;
	}

	
	
	
}
