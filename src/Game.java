import java.util.ArrayList;
import java.util.Stack;

public class Game {
	public static final int BOARD_SIZE=5;
	private Tile board[][];
	private Tile tileSelected;
	private Stack<Move> undoStack;
	private Stack<Move> redoStack;
	
	/**
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
	}
	
	/**
	 * 
	 * @param coord the newly selected tile.
	 */
	public void selectTile(Coord coord) {
		if (tileSelected==null) {
			tileSelected=this.getTile(coord);
		}
		else if (tileSelected==this.getTile(coord)){
			
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
	 * 
	 * @param coord the coordinates of the tile you are trying to acquire
	 * @return the tile found at the given coordinates
	 */
	public Tile getTile(Coord coord) {
		return board[coord.x][coord.y];
	}
	
	/**
	 * 
	 * @param boardArr
	 */
	public void setBoard(ArrayList<Piece> boardArr) {
		//TODO implement
	}
	
	/**
	 * 
	 */
	public void undoMove() {
		if (undoStack.isEmpty()) {
			//TODO throw Exception
		}
		else {
			Move move = undoStack.pop();
			this.getTile(move.coordOld).setPiece(this.getTile(move.coordNew.removePiece()));
			redoStack.add(move);
			if (undoStack.isEmpty()) {
				//TODO disable undo button
			}
		}
	}
	
	/**
	 * 
	 */
	public void redoMove() {
		if (redoStack.isEmpty()) {
			//TODO throw Exception
		}
		else {
			Move move = redoStack.pop();
			this.getTile(move.coordNew).setPiece(this.getTile(move.coordOld.removePiece()));
			undoStack.add(move);
			if (redoStack.isEmpty()) {
				//TODO disable redo button
			}
		}
	}
	
	/**
	 * 
	 * @param coord 
	 */
	private void swapPiece(Coord coord) {
		
		
		if (this.getTile(coord).getPiece().isValidMove()) {
			undoStack.add(new Move(tileSelected.coord,coord));
		}
	}
	
}
