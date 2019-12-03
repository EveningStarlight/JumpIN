package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUI.Tile;
import Model.Coord;
import Model.Game;
import Model.LevelBuilder;
import Pieces.Bunny;
import Pieces.Mushroom;
import Pieces.Piece;

public class LevelBuilderTest {

	Tile[][] board = Game.buildBoard();
	ArrayList<Piece> goodPieces = new ArrayList<Piece>();
	ArrayList<Piece> badPieces = new ArrayList<Piece>();
	Game game;
	@Before
	public void setUp() throws Exception {
	game = new Game(board);
	goodPieces.add(new Bunny(new Coord(2, 0)));
	goodPieces.add(new Mushroom(new Coord(2, 1)));
	badPieces.add(new Bunny(new Coord(2, 0)));
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSave() {
		game.setBoard(goodPieces);
		assertTrue(LevelBuilder.save(game));
		game.setBoard(badPieces);
		assertFalse(LevelBuilder.save(game));
	}

}
