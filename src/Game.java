import java.util.ArrayList;
import java.util.Stack;

/**
 * Overall game implementation 
 * contains the board and is in charge of swapping pieces
 * @author Adam Prins
 * 			100 879 683
 * @version 1.0.0
 * 		First implementation of the code
 * 		Basic usage of all functions in the UML
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
		if (tileSelected==null) {
			tileSelected=this.getTile(coord);
		}
		else if (tileSelected==this.getTile(coord)){
			tileSelected=null;
		}
		else {
			try {
				swapPiece(coord);
			} catch(Exception e) {
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
			//TODO throw Exception
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
			//TODO throw Exception
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
	private void swapPiece(Coord coord) {
		
		
		if (this.getTile(coord).getPiece().isValidMove()) {
			undoStack.add(new Move(tileSelected.getCoord(),coord));
		}
	}
	
	/**
	 * removes all Pieces from the board
	 */
	private void clearBoard() {
		for(Tile[] tileLine:board) {
			for(Tile tile:tileLine) {
				tile.removePiece();
			}
		}
	}
	
}
