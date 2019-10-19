import java.util.ArrayList;

/**
 * Impementation of console controls and display
 * contains the main function (for now) and controls printing to it
 * @author Alexander Beimers
 * 			101 070 233
 * @version 1.0.0
 * 		Has printPiece and printgameBoard working as intended
 * 		Still to do is player text controls
 */
public class ConsoleIO {
	
	/**
	 * Prints out the name of the piece in 3 chars or 3 spaces if there is no piece 
	 * 
	 * @param piece object which you want to print
	 */
	public static void printPiece(Tile piece) {
		if (piece.getPiece() instanceof Bunny) {
			System.out.print("Bun");
		} else if (piece.getPiece() instanceof Mushroom) {
			System.out.print("Shr");
		} else if (piece.getPiece() instanceof Fox) {
			System.out.print("Fox");
		} else {
			System.out.print("   ");
		}
	}
	
	/**
	 * Prints out the game board and all pieces on it 
	 * 
	 * @param game object to be printed
	 */
	public static void printGameBoard(Game game) {
		System.out.println("-------------------------------");
		for (int y = 0; y < game.BOARD_SIZE; y++) {
			System.out.print("|");
			for (int x = 0; x < game.BOARD_SIZE; x++) {
				if (((x == 0 || x == 4) && (y == 0 || y == 4)) || (x == 2 && y == 2)) {
					System.out.print(">");
					printPiece(game.getTile(new Coord(x, y)));
					System.out.print("<|");
				} else {
					System.out.print(" ");
					printPiece(game.getTile(new Coord(x, y)));
					System.out.print(" |");
				}
				
			}
			System.out.println();
			System.out.println("-------------------------------");
		}
	}
	
	/**
	 * Main game loop for text based implementation
	 * creates and populates a game board
	 * 
	 * @param String args[]
	 * TODO implement main game loop and player interaction
	 */
	public static void main(String args[]) {
		boolean quitFlag = false;
		
		Game game = new Game(0);
		ArrayList<Piece> pieceArr = new ArrayList<Piece>();
		pieceArr.add(new Fox(new Coord(1,3), new Coord(1,4)));
		pieceArr.add(new Bunny(new Coord(4,2)));
		pieceArr.add(new Mushroom(new Coord(3,2)));
		pieceArr.add(new Bunny(new Coord(0,1)));
		pieceArr.add(new Mushroom(new Coord(1,1)));
		
		game.setBoard(pieceArr);
		
		printGameBoard(game);
		
		//while (!quitFlag) {
			
		//}
	}
}
