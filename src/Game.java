import java.util.ArrayList;
import java.util.Stack;

/**
 * Overall game implementation 
 * contains the board and is in charge of swapping pieces
 * @author Adam Prins
 * 			100 879 683
 * @version 1.4.0
 * 		Major rework of trySwapPiece
 * 		removes all duplicate lines with one while loop
 */
public class Game {
	public static final int BOARD_SIZE=5;
	private Tile board[][];
	private Tile tileSelected;
	private Stack<Move> undoStack;
	private Stack<Move> redoStack;
	
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
		
		for (int x=0; x<BOARD_SIZE; x++) {
			for (int y=0; y<BOARD_SIZE; y++) {
				board[x][y]=new TextTile(new Coord(x,y));
			}
		}
		
		//TODO use puzzleNum to populate board with pieces
		//setBoard();
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
				System.out.println(e);
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
		
		if (piece.isValidMove(coord)) { //Makes sure the attempted move is valid
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
			
			//Will test to make sure all in between squares are filled or empty
			while (x<xMax && y<yMax) {
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
	 * 
	 * @param coord the coord of the tile that tileSelected is to swap pieces with
	 * @param changeStack used to decided if the undo/redo Stacks should be changed
	 */
	private void swapPiece(Coord coord, boolean changeStack) {
		
		if (tileSelected.getPiece() instanceof Fox) {
			Fox fox = (Fox) tileSelected.getPiece();
			
			this.getTile(fox.getTail()).removePiece(); 
			this.getTile(coord).setPiece(tileSelected.removePiece());
			this.getTile(fox.getTail()).setPiece(tileSelected.removePiece());
			
		}
		else { 
			this.getTile(coord).setPiece(tileSelected.removePiece());
		}
		if (changeStack) {
			undoStack.add(new Move(tileSelected.getCoord(),coord));
			redoStack.clear();
		}
		
		tileSelected=null;
		
	}
	
	/**
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
	
}
