import java.util.ArrayList;
import java.util.Stack;

/**
 * Overall game implementation 
 * contains the board and is in charge of swapping pieces
 * @author Adam Prins
 * 			100 879 683
 * @version 1.1.0
 * 		Addition of the swapPieces code
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
	 * TODO GUI will have buttons not be selectable if mushroom or other conditions
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
		//TODO implement
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
			this.getTile(move.coordOld).setPiece(this.getTile(move.coordNew).removePiece());
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
			this.getTile(move.coordNew).setPiece(this.getTile(move.coordOld).removePiece());
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
		
		if (tileSelected.getPiece().isValidMove(coord)) { //Makes sure the attempted move is valid
			
			if (tileSelected.getPiece() instanceof Bunny ) {
				//Will test to make sure all in between squares are filled
				if (tileSelected.getCoord().x == coord.x) {
					int y = Math.min(tileSelected.getCoord().y, coord.y);		//Lower y value
					int yMax = Math.max(tileSelected.getCoord().y, coord.y);	//Higher y value
					
					while (++y<yMax) { //Will iterate over all tiles between the two coordinates
						Tile tileCurr = this.getTile(new Coord(coord.x,y)); 
						if (tileCurr.isEmpty()) {
							throw new IllegalArgumentException("The bunny cannot hop over empty spaces");
						}
					}
					//If the for loop hasn't thrown an error, swap the pieces
					swapPiece(coord);
				}
				else if(tileSelected.getCoord().y == coord.y) {
					int x = Math.min(tileSelected.getCoord().x, coord.x);		//Lower x value
					int xMax = Math.max(tileSelected.getCoord().x, coord.x);	//Higher x value
					
					while (++x<xMax) { //Will iterate over all tiles between the two coordinates
						Tile tileCurr = this.getTile(new Coord(x,coord.y));
						if (tileCurr.isEmpty()) {
							throw new IllegalArgumentException("The bunny cannot hop over empty spaces");
						}
					}
					//If the for loop hasn't thrown an error, swap the pieces
					swapPiece(coord);
				}
				else {
					throw new IllegalArgumentException("Can not attempt a diagonal swap.");
				}
			}
			
			else if (tileSelected.getPiece() instanceof Fox) {
				//Will test to make sure all in between squares are empty
				if (tileSelected.getCoord().x == coord.x) {
					int y = Math.min(tileSelected.getCoord().y, coord.y);		//Lower y value
					int yMax = Math.max(tileSelected.getCoord().y, coord.y);	//Higher y value
					
					while (++y<yMax) { //Will iterate over all tiles between the two coordinates
						Tile tileCurr = this.getTile(new Coord(coord.x,y)); 
						if (!tileCurr.isEmpty() ||
								!tileCurr.getPiece().equals(tileSelected.getPiece())) {
							throw new IllegalArgumentException("The Fox can only pass threw empty spaces");
						}
					}
					//If the for loop hasn't thrown an error, swap the pieces
					swapPiece(coord);
				}
				else if(tileSelected.getCoord().y == coord.y) {
					int x = Math.min(tileSelected.getCoord().x, coord.x);		//Lower x value
					int xMax = Math.max(tileSelected.getCoord().x, coord.x);	//Higher x value
					
					while (++x<xMax) { //Will iterate over all tiles between the two coordinates
						Tile tileCurr = this.getTile(new Coord(x,coord.y));
						if (!tileCurr.isEmpty() ||
								!tileCurr.getPiece().equals(tileSelected.getPiece())) {
							throw new IllegalArgumentException("The Fox can only pass threw empty spaces");
						}
					}
					//If the for loop hasn't thrown an error, swap the pieces
					swapPiece(coord);
				}
				else {
					throw new IllegalArgumentException("Can not attempt a diagonal swap.");
				}
			}
			else {
				throw new IllegalArgumentException("only Bunnies and Foxes can move.");
			}
			
		}
	}
	
	private void swapPiece(Coord coord) {
		this.getTile(coord).setPiece(tileSelected.removePiece());
		undoStack.add(new Move(tileSelected.getCoord(),coord));
		redoStack.clear();
		
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
