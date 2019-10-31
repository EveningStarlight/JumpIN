import junit.framework.*;

public class UnitTest extends TestCase{
	
	private Game game;
	private TextTile[][] board;
	
	protected void setUp() throws Exception{
		board = new TextTile[5][5];
		for (int x=0; x<5; x++) {
			for (int y=0; y<5; y++) {
				board[x][y]= new TextTile(new Coord(x,y));
			}
		}
		game = new Game(board,1);
	}
	
	public void testSelectTile() throws Exception{
		Coord coordinate = new Coord(2,0);
		game.selectTile(coordinate);
		assertEquals(coordinate, game.getSelectedTile().getCoord());
	}
	
}
