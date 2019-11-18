package Model;

import GUI.*;
import Pieces.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Overall game implementation 
 * contains the board and is in charge of swapping pieces
 * @authors Adam Prins, Matthew Harris
 * 			100 879 683, 101 073 502
 * @version 2.4.3
 * 		Fix in getBoard() so that it no longer passes duplicates due to a Fox's tail
 * 
 * 
 */
public class Game {
	
	/* The size of the board (5x5) */
	public static final int BOARD_SIZE=5;
	/* the game board */
	private Tile board[][];
	/* The currently selected tile */
	private Tile selectedTile;
	/* The undo and redo stacks */
	private Stack<Move> undoStack;
	private Stack<Move> redoStack;
	/* The list of all Bunny pieces on the game board */
	private ArrayList<Bunny> buns;
	
	private static final File SAVED_STATE = new File ("src/Model/SavedState.xml");
	
	
	/**
	 * sets up the game
	 * Create a new game using a preestablished board
	 * fetches the puzzleArray
	 * calls setBoard to populate the pieces using the puzzleArray from Puzzles
	 * 
	 * @param board the board that is to be used by Game. This should usually be a ButtonTile array
	 * @param puzzleNum the puzzle that is to be initialized from Puzzles
	 * @throws Exception 
	 */
	public Game(Tile[][] board, int puzzleNum) throws Exception {
		
		this.board=board;
		undoStack = new Stack<Move>();
		redoStack = new Stack<Move>();
		buns  = new ArrayList<Bunny>();
		
		setBoard(Puzzles.getPuzzle(puzzleNum));
	}
	
	
	/**
	 * used for playing the game
	 * 
	 * selects a tile if there is not currently a selected tile
	 * deselects the tile if it was already the selected tile
	 * Otherwise, it tries to swap the tiles
	 * 
	 * @param coord the newly selected tile.
	 * @throws Exception 
	 */
	public void selectTile(Coord coord) throws Exception {
		
		//If there is no tile selected, try to select this tile
		if (selectedTile==null) {
			if (this.getTile(coord).isEmpty() || this.getTile(coord).getPiece() instanceof Mushroom) {
				throw new IllegalArgumentException("This piece cannot be selected");
			}
			selectedTile=this.getTile(this.getTile(coord).getPiece().getCoord());
		}
		//If there is a tile selected, and its this one, deselect it
		else if (selectedTile==this.getTile(coord)){
			selectedTile=null;
		}
		else {
			try {
				if (canSwapPiece(coord)) swapPiece(coord, true);
				else {
					Piece piece = selectedTile.getPiece();
					selectTile(((Fox)piece).tailToHead(coord));
				}
			} catch(Exception e) {
				selectedTile=null;
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
		selectedTile=null;
		for (Piece piece:boardArr) {
			this.getTile(piece.getCoord()).setPiece(piece);
			
			if (piece instanceof Fox) {
				Fox fox = (Fox) piece;
				this.getTile(fox.getTail()).setPiece(piece);
			}
			if (piece instanceof Bunny) {
				buns.add((Bunny)piece);
			}
		}
	}
	
	/**
	 * Returns an ArrayList of Pieces that are a copy of the current board state.
	 * This allows for manipulation of tiles for solving, without changing the current board state.
	 * 
	 * @return an ArrayList of Pieces that copy the current board state
	 */
	public ArrayList<Piece> getBoard(){
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for(int i = 0; i< BOARD_SIZE; i++){
			for(int j = 0; j<BOARD_SIZE; j++){
				if(!board[i][j].isEmpty()){
					Piece piece = board[i][j].getPiece(); 
					if (piece instanceof Fox && piece.getCoord().equals(new Coord(i,j))) {
						Fox fox = (Fox) piece;
						try {
							pieces.add(new Fox(piece.getCoord(),fox.getTail()));
						} catch (Exception e) {}
					}
					else if (piece instanceof Bunny) {
						pieces.add(new Bunny(piece.getCoord()));
					}
					else if (piece instanceof Mushroom) {
						pieces.add(new Mushroom(piece.getCoord()));
					}
				}
				
			}
		}
		return pieces;
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
			selectedTile=this.getTile(move.COORD_NEW);
			swapPiece(move.COORD_OLD, false);
			redoStack.add(move);
		}
	}
	
	/**
	 * 
	 * @return returns true if the undoStack is empty
	 */
	public boolean isUndoEmpty() {
		return undoStack.isEmpty();
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

			selectedTile=this.getTile(move.COORD_OLD);
			swapPiece(move.COORD_NEW, false);
			undoStack.add(move);
		}
	}
	
	/**
	 * 
	 * @return returns true if the redoStack is empty
	 */
	public boolean isRedoEmpty() {
		return redoStack.isEmpty();
	}
	
	/**
	 * 
	 * @return returns the currently selected tile
	 */
	public Tile getSelectedTile() {
		return selectedTile;
	}
	
	/**
	 * Checks to see if swapping this piece is valid.
	 * bunnies need to jump over filled spaces,
	 * foxes need to side though empty spaces
	 * mushrooms don't move
	 * 
	 * @param coord destination of piece stored at TileSelected
	 * @return returns true if the move is valid, false if it is possible the a valid move for a fox tail
	 * and throws an exception otherwise.
	 */
	private boolean canSwapPiece(Coord coord) {
		Piece piece = selectedTile.getPiece();

		if (!(piece.isValidMove(coord)) ) {
			if (piece instanceof Fox && piece.isValidMove(((Fox)piece).tailToHead(coord))) {
				return false;
			}
			throw new IllegalArgumentException("Not a valid move.");
		}
		else { //Makes sure the attempted move is valid
			try {
				checkDestination(coord);
				checkTilesBetween(coord);
				checkTail(coord);
			} catch (Exception e) {
				throw e;
			}
			//If the for loop hasn't thrown an error, swap the pieces
			return true;

		}
	}
	
	/**
	 * Checks to see if swapping this piece is valid.
	 * bunnies need to jump over filled spaces,
	 * foxes need to side though empty spaces
	 * mushrooms don't move
	 * 
	 * This will largely be used by the solver class
	 * 
	 * @param destination of piece stored at origin
	 * @param origin of piece that will determine if destination is a valid move.
	 * @return returns true if the move is valid, false if it is possible the a valid move for a fox tail
	 * and throws an exception otherwise.
	 */

	public boolean canSwapPiece(Coord origin, Coord destination) {
		Tile savedTile = selectedTile;
		selectedTile = this.getTile(origin);
		boolean validMove = false;
		
		try { validMove = canSwapPiece(destination); } catch (Exception e) {}
		
		selectedTile = savedTile;
		return validMove;
	}

	/**
	 * Checks to see if the destination tile is a valid tile to move to.
	 * 
	 * @param coord destination of piece stored at TileSelected
	 * @throws IllegalArgumentException Throws if the destination is not empty
	 */
	private void checkDestination(Coord coord) throws IllegalArgumentException {
		Piece piece = selectedTile.getPiece();
		if (!(this.getTile(coord).isEmpty()) && 
				!piece.equals(this.getTile(coord).getPiece())) {
			throw new IllegalArgumentException("The destination must be empty.");
		}
	}

	/**
	 * Checks to see if the attempted location of the tail is a valid position.
	 * 
	 * @param coord destination of piece stored at TileSelected
	 * @throws IllegalArgumentException Throws if the tail position is not valid
	 */
	private void checkTail(Coord coord) throws IllegalArgumentException {
		Piece piece = selectedTile.getPiece();
		if (piece instanceof Fox ) {
			Coord head = piece.getCoord();
			Coord tail = ((Fox)piece).getTail();
			
			Coord newTail = new Coord(coord.x + tail.x - head.x,
									  coord.y + tail.y - head.y);
			
			if ( !(this.getTile(newTail).isEmpty() || piece.equals(this.getTile(newTail).getPiece())) ) {
				throw new IllegalArgumentException("The tail must end in an empty spot");
			}
		}
	}


	/**
	 * Checks to see if the tiles between the piece and its destination is valid.
	 * For a Bunny it is valid if they contain a Piece
	 * For a Fox it is valid if they do not contain any Pieces
	 * 
	 * @param coord destination of piece stored at TileSelected
	 * @throws IllegalArgumentException Throws if tiles between the selected tile and the destination tile are not valid
	 */
	private void checkTilesBetween(Coord coord) throws IllegalArgumentException {
		Piece piece = selectedTile.getPiece();
		boolean empty;
		
		if (piece instanceof Bunny ) {
			empty=true; // empty spaces throw error
		}else if (piece instanceof Fox ) {
			empty=false; // full spaces throw error
		}else{
			selectedTile=null;
			throw new IllegalArgumentException("only Bunnies and Foxes can move.");
		}
		
		
		int x = Math.min(selectedTile.getCoord().x, coord.x);		//Lower y value
		int xMax = Math.max(selectedTile.getCoord().x, coord.x);	//Higher y value
		int xChange=0;
		int y = Math.min(selectedTile.getCoord().y, coord.y);		//Lower y value
		int yMax = Math.max(selectedTile.getCoord().y, coord.y);	//Higher y value
		int yChange=0;
		
		if 		(x==xMax) yChange=1;
		else if (y==yMax) xChange=1;
		else {
			selectedTile=null;
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
				selectedTile=null;
				if (piece instanceof Bunny) throw new IllegalArgumentException("The bunny cannot hop over empty spaces");
				if (piece instanceof Fox) throw new IllegalArgumentException("The Fox cannot slide through full spaces");
			}
			x+=xChange;
			y+=yChange;
		}
	}
	
	/**
	 * Swaps two pieces. All moves should be valid if this method is called.
	 * 
	 * @param coord the coordinate of the tile that selectedTile is to swap pieces with
	 * @param changeStack used to decided if the undo/redo Stacks should be changed
	 */
	private void swapPiece(Coord coord, boolean changeStack) {
		
		if (selectedTile.getPiece() instanceof Fox) {
			Fox fox = (Fox) selectedTile.getPiece();
			
			this.getTile(fox.getTail()).removePiece(); 
			this.getTile(coord).setPiece(selectedTile.removePiece());
			fox.setCoord(coord);
			this.getTile(fox.getTail()).setPiece(fox);
			
		}
		else { 
			try {
				selectedTile.getPiece().setCoord(coord);
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.getTile(coord).setPiece(selectedTile.removePiece());
		}
		if (changeStack) {
			undoStack.add(new Move(selectedTile.getCoord(),coord));
			redoStack.clear();
		}
		
		selectedTile=null;
		
	}
	
	/**
	 * checks to see if all the bunnies are in the holes
	 * if they are, the game is won and this returns true
	 * 
	 * @return returns true if this puzzle has been solved
	 */
	public boolean endGame(){
		int total = buns.size();
		int count = 0;
		for(int i = 0; i < buns.size(); i++){
			if(buns.get(i).getCoord().isHole()) {
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
		selectedTile=null;
		buns.clear();
		undoStack.clear();
		redoStack.clear();
		for(Tile[] tileLine:board) {
			for(Tile tile:tileLine) {
				tile.removePiece();
			}
		}
	}
	
	
	public void saveState() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document document = dBuilder.newDocument();
	        
	        Element rootElement = document.createElement("SavedState");
	        
	        /*
	        Element levelElement = document.createElement("Level");
	        levelElement.appendChild(document.createTextNode("2"));
	        rootElement.appendChild(levelElement);
	        */
	        
	        ArrayList<Piece> pieces = getBoard();
	        Element pieceRootElement = document.createElement("Pieces");
	        for (Piece piece:pieces) {
	        	//TODO Jay replace these first two lines with a call to the piece to get the Element
	        	Element pieceElement = document.createElement("Piece");
	        	pieceElement.appendChild(document.createTextNode(piece.getCoord().toString()));
	        	pieceRootElement.appendChild(pieceElement);
	        }
	        rootElement.appendChild(pieceRootElement);
	        
	        Element undoRootElement = document.createElement("UndoStack");
	        for (Move move:undoStack) {
	        	undoRootElement.appendChild(move.getElement(document));
	        }
	        rootElement.appendChild(undoRootElement);
	        
	        Element redoRootElement = document.createElement("RedoStack");
	        for (Move move:redoStack) {
	        	redoRootElement.appendChild(move.getElement(document));
	        }
	        rootElement.appendChild(redoRootElement);
	        
	        document.appendChild(rootElement);
	        
	        Transformer tr = TransformerFactory.newInstance().newTransformer();
            tr.setOutputProperty(OutputKeys.INDENT, "yes");
            tr.setOutputProperty(OutputKeys.METHOD, "xml");
            tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            // send DOM to file
            tr.transform(new DOMSource(document), 
                                 new StreamResult(new FileOutputStream(SAVED_STATE.getPath())));
	        
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}
