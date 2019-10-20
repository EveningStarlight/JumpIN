import java.util.ArrayList;

/**
 * A collection of the puzzles that can be played through for the game JumpIN
 * 
 * @author Adam Prins
 * 			100 879 683
 * @version 1.0.0
 * 		First creation of Puzzles
 * 		contains the first 4 puzzles
 *
 */
public class Puzzles {

	/**
	 * @param puzzleNumber the puzzle you would like to initialize
	 * @return returns an ArrayList of Pieces that can be used to populate the game board
	 */
	public static ArrayList<Piece> getPuzzle(int puzzleNumber) {
		ArrayList<Piece> array = new ArrayList<Piece>();
		if (puzzleNumber==0) {
		}
		if (puzzleNumber==1) {
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
			array.add(new Mushroom(new Coord(2,2)));
			array.add(new Bunny(new Coord(0,2)));
		}
		else {
			throw new IllegalArgumentException("The puzzle number: " + puzzleNumber + " is not in bounds");
		}
		
		return array;
	}

	
	
	
}
