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
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Overall game implementation 
 * contains the board and is in charge of swapping pieces
 * @authors Adam Prins, Matthew Harris
 * 			100 879 683, 101 073 502
 * @version 2.6.0
 * 		Added stackControl() to improve undo/redo methods
 * 		Improved method documentation
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
	public Game(Tile[][] board) throws Exception {
		
		this.board=board;
		undoStack = new Stack<Move>();
		redoStack = new Stack<Move>();
		buns  = new ArrayList<Bunny>();
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
			stackControl(undoStack, redoStack, undoStack.peek().COORD_NEW, undoStack.peek().COORD_OLD);
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
			stackControl(redoStack, undoStack, redoStack.peek().COORD_OLD, redoStack.peek().COORD_NEW);
		}
	}
	
	/**
	 * Handles the movement moving from one stack to the other stack
	 * 
	 * @param origin undo/redo stack we are popping from
	 * @param destination undo/redo stack we are popping to
	 * @param selection coordinate of the piece
	 * @param swap coordinate of the destination
	 */
	private void stackControl(Stack<Move> origin, Stack<Move> destination, Coord selection, Coord swap) {
		selectedTile=this.getTile(selection);
		swapPiece(swap, false);
		destination.add(origin.pop());
	}
	
	/**
	 * 
	 * @return returns true if the undoStack is empty
	 */
	public boolean isUndoEmpty() {
		return undoStack.isEmpty();
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
	
	/**
	 * Saves the current state of the board as an XML file
	 */
	public void saveState(Integer puzzleNumber) {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	        Document document = dBuilder.newDocument();
	        
	        Element rootElement = document.createElement("SavedState");
	        Element numberElement = document.createElement("PuzzleNumber");
	        numberElement.appendChild(document.createTextNode(puzzleNumber.toString()));
	        rootElement.appendChild(numberElement);
	        ArrayList<Piece> pieces = getBoard();
	        Element pieceRootElement = document.createElement("Pieces");
	        for (Piece piece:pieces) {
	        	Element pieceElement = piece.getElement(document);
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
			e.printStackTrace();
		}
	}
	
	/**
	 * This loads a saved state from XML into the current game
	 * 
	 * @return returns the puzzle number so the GUI is able to select the next puzzle properly
	 */
	public int loadState(){
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		int puzzleNumber = 0;
		try {
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(SAVED_STATE);
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("PuzzleNumber");
	         Node nNode = nList.item(0);
	         if(nNode.getNodeType() == Node.ELEMENT_NODE){
	        	 Element eElement = (Element) nNode;
	        	 puzzleNumber = Integer.parseInt(nNode.getTextContent());
	         }
	         //This recreates the current piece locations for the board
	         nList = doc.getElementsByTagName("Pieces");
	         nNode = nList.item(0);
	         if(nNode.getNodeType() == Node.ELEMENT_NODE){
	        	 Element eElement = (Element) nNode;
                 NodeList pieceNodes = eElement.getElementsByTagName("Piece");
                 for (int i=0; i<pieceNodes.getLength(); i++ ) {
                	 Element pElement = (Element) pieceNodes.item(i);
                	 int x = Integer.parseInt(pElement.getElementsByTagName("X_Head").item(0).getTextContent());
                	 int y = Integer.parseInt(pElement.getElementsByTagName("Y_Head").item(0).getTextContent());
                	 String type = pElement.getElementsByTagName("Type").item(0).getTextContent();
                	 Coord coord = new Coord(x,y);
                	 
                	 if ("Mushroom".equals(type))		pieces.add(new Mushroom(coord));
                	 else if ("Bunny".equals(type)) 	pieces.add(new Bunny(coord));
                	 else if ("Fox".equals(type)) {
                		 int xTail = Integer.parseInt(pElement.getElementsByTagName("X_Tail").item(0).getTextContent());
                		 int yTail = Integer.parseInt(pElement.getElementsByTagName("Y_Tail").item(0).getTextContent());
                		 Coord coordTail = new Coord(xTail,yTail);
                		 pieces.add(new Fox(coord,coordTail));
                	 }
                 }
             }
	         setBoard(pieces);
	         //This sets up the undo stack to its recorded state
	         nList = doc.getElementsByTagName("UndoStack");
	         nNode = nList.item(0);
	         if(nNode.getNodeType() == Node.ELEMENT_NODE){
	        	 Element eElement = (Element) nNode;
	        	 NodeList moveNodes = eElement.getElementsByTagName("Move");
	        	 for (int i=0; i<moveNodes.getLength(); i++ ) {
	        		 Element mElement = (Element) moveNodes.item(i);
		        	 NodeList oldCNodes = mElement.getElementsByTagName("OldCoord");
		        	 Node oldCNode = oldCNodes.item(0);
		        	 Coord oldCoord = null;
		        	 if(oldCNode.getNodeType() == Node.ELEMENT_NODE){
		        		 Element oldCElement = (Element) oldCNode;
		        		 int x = Integer.parseInt(oldCElement.getElementsByTagName("X").item(0).getTextContent());
		        		 int y = Integer.parseInt(oldCElement.getElementsByTagName("Y").item(0).getTextContent());
		        		 oldCoord = new Coord(x, y);
		        	 }
		        	 NodeList newCNodes = mElement.getElementsByTagName("NewCoord");
		        	 Node newCNode = newCNodes.item(0);
		        	 Coord newCoord = null;
		        	 if(newCNode.getNodeType() == Node.ELEMENT_NODE){
		        		 Element newCElement = (Element) newCNode;
		        		 int x = Integer.parseInt(newCElement.getElementsByTagName("X").item(0).getTextContent());
		        		 int y = Integer.parseInt(newCElement.getElementsByTagName("Y").item(0).getTextContent());
		        		 newCoord = new Coord(x, y);
		        	 }
		        	 Move move = new Move(oldCoord, newCoord);
		        	 undoStack.add(move);
	         }
	         }
	         //This sets up the redo stack to its recorded state
	         nList = doc.getElementsByTagName("RedoStack");
	         nNode = nList.item(0);
	         if(nNode.getNodeType() == Node.ELEMENT_NODE){
	        	 Element eElement = (Element) nNode;
	        	 NodeList moveNodes = eElement.getElementsByTagName("Move");
	        	 for (int i=0; i<moveNodes.getLength(); i++ ) {
	        		 Element mElement = (Element) moveNodes.item(i);
		        	 NodeList oldCNodes = mElement.getElementsByTagName("OldCoord");
		        	 Node oldCNode = oldCNodes.item(0);
		        	 Coord oldCoord = null;
		        	 if(oldCNode.getNodeType() == Node.ELEMENT_NODE){
		        		 Element oldCElement = (Element) oldCNode;
		        		 int x = Integer.parseInt(oldCElement.getElementsByTagName("X").item(0).getTextContent());
		        		 int y = Integer.parseInt(oldCElement.getElementsByTagName("Y").item(0).getTextContent());
		        		 oldCoord = new Coord(x, y);
		        	 }
		        	 NodeList newCNodes = mElement.getElementsByTagName("NewCoord");
		        	 Node newCNode = newCNodes.item(0);
		        	 Coord newCoord = null;
		        	 if(newCNode.getNodeType() == Node.ELEMENT_NODE){
		        		 Element newCElement = (Element) newCNode;
		        		 int x = Integer.parseInt(newCElement.getElementsByTagName("X").item(0).getTextContent());
		        		 int y = Integer.parseInt(newCElement.getElementsByTagName("Y").item(0).getTextContent());
		        		 newCoord = new Coord(x, y);
		        	 }
		        	 Move move = new Move(oldCoord, newCoord);
		        	 redoStack.add(move);
	         }
	         }
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return puzzleNumber;
	}
	
	/**
	 * This builds a double array of text tiles for use in other methods
	 * 
	 * @return an empty double array of text tiles
	 */
	public static Tile[][] buildBoard(){
		Tile[][] board = new TextTile[Game.BOARD_SIZE][Game.BOARD_SIZE];
		for (int x=0; x<Game.BOARD_SIZE; x++) {
			for (int y=0; y<Game.BOARD_SIZE; y++) {
				 board[x][y]= new TextTile(new Coord(x,y));
			}
		}
		return board;
	}
}
