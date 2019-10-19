import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Overall game implementation 
 * contains the board and is in charge of swapping pieces
 * @author Adam Prins
 * 			100 879 683
 * @version 1.6.0
 * 		SelectTile now throws caught error
 * 
 * 		trySwapPieces now checks for empty destination
 * 		trySwapPieces while loop now ignores when currTile holds the same Piece as piece
 * 
 * 		swapPieces now correctly changes the pieces coordinates
 * 
 * 		printGameBoard now shows selected tile
 * 
 * 		Main now selects one tile at a time (this fixes an error where the first selection would be invalid, but the second would be valid)
 * 
 * @author Alexander Beimers and Matthew Harris
 * @version 1.5.0
 * 		Code from consoleIO (author Alexander) now merged into game
 * 		ConsoleIO is now removed
 * 
 * 		main now allows for user input
 * 		main uses input to select tiles
 * 
 * 		bunny array added
 * 		endGame added, checks to see if all bunnies are in the holes. if true, the player has won.
 * 		
 * 		
 */
public class Game {
	
	public static final int BOARD_SIZE=5;
	private Tile board[][];
	private Tile tileSelected;
	private Stack<Move> undoStack;
	private Stack<Move> redoStack;
	private ArrayList<Bunny> buns;
	
	
	/**
	 * sets up the game
	 * populates the board with coordinates
	 * fetches the puzzleArray
	 * calls setBoard to populate the pieces using the puzzleArray
	 * 
	 * @param puzzleNum the puzzle that is to be initialized
	 */
	public Game(int puzzleNum){
		
		board = new Tile[BOARD_SIZE][BOARD_SIZE];
		undoStack = new Stack<Move>();
		redoStack = new Stack<Move>();
		buns  = new ArrayList<Bunny>();
		
		for (int x=0; x<BOARD_SIZE; x++) {
			for (int y=0; y<BOARD_SIZE; y++) {
				board[x][y]=new TextTile(new Coord(x,y));
			}
		}
		Bunny bun1 = new Bunny(new Coord(4,2));
		Bunny bun2 = new Bunny(new Coord(0,2));
		buns.add(bun1);
		buns.add(bun2);
		ArrayList<Piece> pieceArr = new ArrayList<Piece>();
		pieceArr.add(new Fox(new Coord(1,3), new Coord(1,4)));
		pieceArr.add(bun1);
		pieceArr.add(new Mushroom(new Coord(3,2)));
		pieceArr.add(bun2);
		pieceArr.add(new Mushroom(new Coord(0,1)));
		setBoard(pieceArr);
		
	}
	
	/**
	 * used for playing the game
	 * 
	 * selects a tile if there is not currently a selected tile
	 * deselects the tile if it was already the selected tile
	 * Otherwise, it tries to swap the tiles
	 * 
	 * @param coord the newly selected tile.
	 */
	public void selectTile(Coord coord) {
		
		//If there is no tile selected, try to select this tile
		if (tileSelected==null) {
			if (this.getTile(coord).isEmpty() || this.getTile(coord).getPiece() instanceof Mushroom) {
				throw new IllegalArgumentException("This piece cannot be selected");
			}
			tileSelected=this.getTile(this.getTile(coord).getPiece().getCoord());
		}
		//If there is a tile selected, and its this one, deselect it
		else if (tileSelected==this.getTile(coord)){
			tileSelected=null;
		}
		else {
			try {
				trySwapPiece(coord);
			} catch(Exception e) {
				tileSelected=null;
				throw e;
			}
		}
	}
	
	/**
	 * gets the tile with the given coordinates 
	 * 
	 * @param coord the coordinates of the tile you are trying to acquire
	 * @return the tile found at the given coordinates
	 */
	public Tile getTile(Coord coord) {
		return board[coord.x][coord.y];
	}
	
	/**
	 * Populates the board with pieces as described in the passed array
	 * 
	 * @param boardArr describes how to populate the board
	 */
	public void setBoard(ArrayList<Piece> boardArr) {
		clearBoard();
		tileSelected=null;
		for (Piece piece:boardArr) {
			this.getTile(piece.getCoord()).setPiece(piece);
			
			if (piece instanceof Fox) {
				Fox fox = (Fox) piece;
				this.getTile(fox.getTail()).setPiece(piece);
			}
		}
	}
	
	/**
	 * undoes the last move made as described on the undoStack
	 */
	public void undoMove() {
		if (undoStack.isEmpty()) {
			throw new IllegalArgumentException("There is nothing to undo");
		}
		else {
			Move move = undoStack.pop();
			tileSelected=this.getTile(move.coordNew);
			swapPiece(move.coordOld, false);
			redoStack.add(move);
			if (undoStack.isEmpty()) {
				//TODO disable undo button
			}
		}
	}
	
	/**
	 * redoes the next move made as described on the redoStack
	 */
	public void redoMove() {
		if (redoStack.isEmpty()) {
			throw new IllegalArgumentException("There is nothing to redo");
		}
		else {
			Move move = redoStack.pop();

			tileSelected=this.getTile(move.coordOld);
			swapPiece(move.coordNew, false);
			undoStack.add(move);
			
			if (redoStack.isEmpty()) {
				//TODO disable redo button
			}
		}
	}
	
	/**
	 * Attempts to swap two pieces
	 * bunnies need to jump over filled spaces,
	 * foxes need to side though empty spaces
	 * mushrooms don't move
	 * 
	 * @param coord destination of piece stored at TileSelected
	 */
	private void trySwapPiece(Coord coord) {
		Piece piece = tileSelected.getPiece();
		if (!(piece.isValidMove(coord)) ) {
			throw new IllegalArgumentException("Not a valid move.");
		}
		else if (!(this.getTile(coord).isEmpty()) && 
				!piece.equals(this.getTile(coord).getPiece())) {
			throw new IllegalArgumentException("The destination must be empty.");
		}
		else { //Makes sure the attempted move is valid
			boolean empty;
			
			if (piece instanceof Bunny ) {
				empty=true; // empty spaces throw error
			}else if (piece instanceof Fox ) {
				empty=false; // full spaces throw error
			}else{
				tileSelected=null;
				throw new IllegalArgumentException("only Bunnies and Foxes can move.");
			}
			
			
			int x = Math.min(tileSelected.getCoord().x, coord.x);		//Lower y value
			int xMax = Math.max(tileSelected.getCoord().x, coord.x);	//Higher y value
			int xChange=0;
			int y = Math.min(tileSelected.getCoord().y, coord.y);		//Lower y value
			int yMax = Math.max(tileSelected.getCoord().y, coord.y);	//Higher y value
			int yChange=0;
			
			if 		(x==xMax) yChange=1;
			else if (y==yMax) xChange=1;
			else {
				tileSelected=null;
				throw new IllegalArgumentException("Can not attempt a diagonal swap.");
			}
			x+=xChange;
			y+=yChange;
			//Will test to make sure all in between squares are filled or empty
			while (x<xMax || y<yMax) {
				Tile tileCurr = this.getTile(new Coord(x,y)); 
				if (piece.equals(tileCurr.getPiece())) {
					
				}
				else if (tileCurr.isEmpty()==empty) {
					tileSelected=null;
					if (piece instanceof Bunny) throw new IllegalArgumentException("The bunny cannot hop over empty spaces");
					if (piece instanceof Fox) throw new IllegalArgumentException("The Fox cannot slide through full spaces");
				}
				x+=xChange;
				y+=yChange;
			}
			//If the for loop hasn't thrown an error, swap the pieces
			swapPiece(coord, true);

		}
	}
	
	/**
	 * Swaps two pieces. All moves should be valid if this method is called.
	 * 
	 * @param coord the coordinate of the tile that tileSelected is to swap pieces with
	 * @param changeStack used to decided if the undo/redo Stacks should be changed
	 */
	private void swapPiece(Coord coord, boolean changeStack) {
		
		if (tileSelected.getPiece() instanceof Fox) {
			Fox fox = (Fox) tileSelected.getPiece();
			
			this.getTile(fox.getTail()).removePiece(); 
			this.getTile(coord).setPiece(tileSelected.removePiece());
			fox.setCoord(coord);
			this.getTile(fox.getTail()).setPiece(fox);
			
		}
		else { 
			tileSelected.getPiece().setCoord(coord);
			this.getTile(coord).setPiece(tileSelected.removePiece());
		}
		if (changeStack) {
			undoStack.add(new Move(tileSelected.getCoord(),coord));
			redoStack.clear();
		}
		
		tileSelected=null;
		
	}
	
	/**
	 * checks to see if all the bunnies are in the holes
	 * if they are, the game is won and this returns true
	 * 
	 * @return returns true if this puzzle has been solved
	 */
	private boolean endGame(){
		int total = buns.size();
		int count = 0;
		for(int i = 0; i < buns.size(); i++){
			if(		buns.get(i).getCoord().equals(new Coord(0, 0)) || 
					buns.get(i).getCoord().equals(new Coord(4, 0)) || 
					buns.get(i).getCoord().equals(new Coord(2, 2)) || 
					buns.get(i).getCoord().equals(new Coord(0, 4)) || 
					buns.get(i).getCoord().equals(new Coord(4, 4))) {
				count ++;
			}
		}
		if(count == total){
			return true;
		}
		else{
		return false;
		}
		
	}
	
	/**
	 * clears the board for reuse
	 * removes all Pieces from the board
	 */
	private void clearBoard() {
		tileSelected=null;
		for(Tile[] tileLine:board) {
			for(Tile tile:tileLine) {
				tile.removePiece();
			}
		}
	}
	
	/**
	 * Prints out the name of the piece in 3 chars or 3 spaces if there is no piece 
	 * 
	 * @param piece The piece you want represented on the board
	 */
	public static void printPiece(Piece piece) {
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
	public void printGameBoard() {
		System.out.println("-------------------------------");
		for (int y = 0; y < Game.BOARD_SIZE; y++) {
			System.out.print("|");
			for (int x = 0; x < Game.BOARD_SIZE; x++) {
				if (tileSelected!=null && tileSelected.getCoord().x==x && tileSelected.getCoord().y==y) {
					System.out.print("•");
					printPiece(this.getTile(new Coord(x, y)).getPiece());
					System.out.print("•|");
				}
				else if (((x == 0 || x == 4) && (y == 0 || y == 4)) || (x == 2 && y == 2)) {
					System.out.print(">");
					printPiece(this.getTile(new Coord(x, y)).getPiece());
					System.out.print("<|");
				} else {
					System.out.print(" ");
					printPiece(this.getTile(new Coord(x, y)).getPiece());
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
	 */
	public static void main(String args[]) {
		Scanner reader = new Scanner(System.in);
		Game game = new Game(0);
		int xcoord;
		int ycoord;
		while (!game.endGame()) {
			game.printGameBoard();
			System.out.println("Select a Tile: ");
			System.out.print("input x coordinate: ");
			xcoord = reader.nextInt();
			System.out.print("input y coordinate: ");
			ycoord = reader.nextInt();
			System.out.println();
			Coord coord = new Coord(xcoord, ycoord);

			try {
				game.selectTile(coord);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("GAME OVER YOU WIN!!!!!");
		reader.close();
	}
}
