import java.util.ArrayList;

/**
 * A collection of the puzzles that can be played through for the game JumpIN
 * 
 * @authors Adam Prins, Matthew Harris
 * 			100 879 683, 101 073 502
 * @version 2.0.1
 * 		Removed duplicate puzzle
 *
 */
public class Puzzles {
	
	// How to populate a static array:
	// https://alvinalexander.com/source-code/java/how-create-populate-static-list-arraylist-linkedlist-syntax-in-java
	static ArrayList<ArrayList<Piece>> arrayArray = new ArrayList<ArrayList<Piece>>() {{
		try {
		// Puzzle 0 - Used for Testing. Not to be called while playing the game
		add(new ArrayList<Piece>() {{
			add(new Mushroom(new Coord(2,1)));
			add(new Bunny(new Coord(2,0)));
		}});
		// Puzzle 1
		add(new ArrayList<Piece>() {{
			add(new Mushroom(new Coord(2,1)));
			add(new Bunny(new Coord(2,0)));
		}});
		// Puzzle 2
		add(new ArrayList<Piece>() {{
			add(new Mushroom(new Coord(3,2)));
			add(new Mushroom(new Coord(0,1)));
			add(new Bunny(new Coord(4,2)));
			add(new Bunny(new Coord(0,2)));
		}});
		// Puzzle 3
		add(new ArrayList<Piece>() {{
			add(new Fox(new Coord(3,1), new Coord(4,1)));
			add(new Bunny(new Coord(0,2)));
		}});
		// Puzzle 4
		add(new ArrayList<Piece>() {{
			add(new Fox(new Coord(3,1), new Coord(4,1)));
			add(new Fox(new Coord(1,0), new Coord(1,1)));
			add(new Mushroom(new Coord(0,2)));
			add(new Bunny(new Coord(0,3)));
		}});
		// Puzzle 5
		add(new ArrayList<Piece>() {{
			add(new Fox(new Coord(3,1), new Coord(4,1)));
			add(new Fox(new Coord(1,0), new Coord(1,1)));
			add(new Mushroom(new Coord(0,2)));
			add(new Bunny(new Coord(0,3)));
			add(new Bunny(new Coord(2,4)));
		}});
		// Puzzle 6
		add(new ArrayList<Piece>() {{
			add(new Fox(new Coord(3,0), new Coord(3,1)));
			add(new Mushroom(new Coord(0,1)));
			add(new Mushroom(new Coord(1,2)));
			add(new Mushroom(new Coord(2,3)));
			add(new Bunny(new Coord(4,2)));
			add(new Bunny(new Coord(2,4)));
		}});
		// Puzzle end Bunnies!
		add(new ArrayList<Piece>() {{
			add(new Bunny(new Coord(0,0)));
			add(new Bunny(new Coord(0,4)));
			add(new Bunny(new Coord(2,2)));
			add(new Bunny(new Coord(4,0)));
			add(new Bunny(new Coord(4,4)));
			add(new Bunny(new Coord(2,1)));
		}});
		}
		catch(Exception e) {
			
		}
	}};
	

	/**
	 * @param puzzleNumber the puzzle you would like to initialize
	 * @return returns an ArrayList of Pieces that can be used to populate the game board
	 * @throws Exception 
	 */
	public static ArrayList<Piece> getPuzzle(int puzzleNumber) {
		if (puzzleNumber < 0 || puzzleNumber > getMaxPuzzle()) puzzleNumber=1;
		
		ArrayList<Piece> array = new ArrayList<Piece>();
		for (Piece piece:arrayArray.get(puzzleNumber)) {
			if (piece instanceof Fox) {
				Fox fox = (Fox) piece;
				try {
				array.add(new Fox(fox.getCoord(), fox.getTail()));
				} catch (Exception e) {}
			}
			else if (piece instanceof Bunny) {
				array.add(new Bunny(piece.getCoord()));
			}
			else if (piece instanceof Mushroom) {
				array.add(new Mushroom(piece.getCoord()));
			}
		}
		return array;
	}

	/**
	 * 
	 * @return the number of puzzles in the array. Since the first puzzle is not configured for play,
	 * this is one higher than the puzzleNumber of the last puzzle
	 */
	public static int getMaxPuzzle() {
		return arrayArray.size();
	}
	
	
	
}
