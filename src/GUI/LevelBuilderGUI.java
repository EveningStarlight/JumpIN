package GUI;

import Pieces.*;
import Solver.Solver;
import Model.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

/**
 * GUI for LevelBuilder
 * holds the game layout and the other intractable elements
 *  
 * @authors Adam Prins Matthew Harris
 * 		   100 879 683  101073502
 * 
 * @version 1.1.0
 * 		GUI now supports the placement and removal of Pieces
 *		
 */
public class LevelBuilderGUI implements ActionListener {
	
    /* the selected tile and the game board */
    private ButtonTile selectedTile;
    private ButtonTile board[][];
    private ButtonTile pieces[][];
    
    /*The Buttons*/
    private JButton save;
    private JButton reset;
    
    /* The output fields */
    private JLabel outputStatic;
    private JLabel output;
    
    /* The Game */
    private Game game;

    /**
     * Constructor of the GUI 
     * Initializes the frame and configures the layouts
     * Sets all listeners
     */
	public LevelBuilderGUI() {
		JFrame frame = new JFrame("JumpIN Level Editor"); 
	    Container contentPane = frame.getContentPane(); 
	    contentPane.setLayout(new GridBagLayout());
	    // get the content pane so we can put stuff in
	
	    createBoardPanel(contentPane);
	    createPanelSpacing(contentPane);
	    createInterfacePanel(contentPane);
	    
	    frame.setPreferredSize(new Dimension(800,600));
	    frame.pack(); // pack contents into our frame
        frame.setResizable(false); // we cann't resize it
        frame.setVisible(true); // make it visible
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //closes only the builder on X
        
        try {
			game = new Game(board);
			drawButtons();
		} catch (Exception e) {
			output.setText(e.getMessage());
		}
	}
	
	/**
	 * Creates the content of the board Panel and adds it to the given contentPane
	 * @param contentPane the contentPane of the frame that we are adding this JPanel to
	 */
	protected void createBoardPanel(Container contentPane) {
		JPanel boardPanel = new JPanel();
	    boardPanel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    
        
	    /* The button that is clicked to increment the counter. */
	    board = new ButtonTile[Game.BOARD_SIZE][Game.BOARD_SIZE];
		
		for (int x=0; x<Game.BOARD_SIZE; x++) {
			for (int y=0; y<Game.BOARD_SIZE; y++) {
                c.gridx = x;			c.gridy = y;
                
                board[x][y]= new ButtonTile(new Coord(x,y));
                board[x][y].setPreferredSize(new Dimension(100,100));
                board[x][y].setMargin(new Insets(0,0,0,0));
                board[x][y].setBorder(BorderFactory.createLineBorder(Color.black));
                board[x][y].setEnabled(true);
                boardPanel.add(board[x][y],c);
                board[x][y].addActionListener(this);
                
			}
		}
		
		c = new GridBagConstraints();
	    c.anchor = GridBagConstraints.LINE_START;
	    c.gridx=0;			c.gridy=0;
	    c.gridwidth=6;		c.gridheight=6;
	    c.ipadx = 10; 		c.ipady = 10;
	    c.weightx=0;
	    contentPane.add(boardPanel,c);
	}
	
	/**
	 * Creates the spacing between the two JPanels
	 * @param contentPane the contentPane of the frame that we are adding this JPanel to
	 */
	protected void createPanelSpacing(Container contentPane) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx=6;			c.gridy=0;
		c.gridwidth=1;		c.gridheight=6;
	    c.ipadx = 10; 		c.ipady = 5;	//c.ipadx fully controls the space between contentPane and interfacePanel
	    c.weightx=0;
	    contentPane.add(Box.createGlue(),c);
	}
	
	/**
	 * Creates the Interface JPanel and adds it to the given contentPane
	 * @param contentPane the contentPane of the frame that we are adding this JPanel to
	 */
	protected void createInterfacePanel(Container contentPane) {
		JPanel interfacePanel = new JPanel();
	    interfacePanel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.anchor = (GridBagConstraints.LINE_START);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    
	    c.gridx = 0;
	    c.weightx=1;
	    interfacePanel.add(Box.createGlue(), c);
	    
	    c.gridx = 1;			c.gridy = 0;
	    c.weightx=0;			c.weighty=0;
	    
	    save = new JButton("save");
	    save.setPreferredSize(new Dimension(100,40));
	    save.addActionListener(this);
	    interfacePanel.add(save, c);
	    
	    
	    c.gridx = 2;
	    
	    reset = new JButton("reset");
	    reset.setPreferredSize(new Dimension(100,40));
	    reset.addActionListener(this);
	    interfacePanel.add(reset, c);
	    
	    
	    c.gridx = 3;
	    c.weightx=1;
	    interfacePanel.add(Box.createGlue(), c);
	    
	    outputStatic = new JLabel("Output: ");
	    c.gridx = 0;			c.gridy = 5;
	    interfacePanel.add(outputStatic,c);
	    
	    c.gridx = 0;			c.gridy = 6;
	    c.weightx=1;
	    c.gridwidth=4;
	    output = new JLabel("Game Start");
	    output.setPreferredSize(new Dimension(150,30));
	    interfacePanel.add(output,c);
	    
	    c.gridx = 0;			c.gridy = 10;
	    c.weightx=1;			c.weighty=1;
	    c.gridwidth=4;
	    
	    
	    int maxX=2;
	    int maxY=3;
	    JPanel pieceTiles = new JPanel();
	    pieceTiles.setLayout(new GridLayout(maxY,maxX));
	    pieces = new ButtonTile[maxX][maxY];
	    for (int y=0; y<maxY; y++) {	
	    	for (int x=0; x<maxX; x++) {
	    		pieces[x][y]=new ButtonTile(new Coord(x,y));
	    		pieces[x][y].setPreferredSize(new Dimension(100,100));
	    		pieces[x][y].setMargin(new Insets(0,0,0,0));
	    		pieces[x][y].setBorder(BorderFactory.createLineBorder(Color.black));
	    		pieces[x][y].setEnabled(true);
	    		pieces[x][y].addActionListener(this);
	    		pieceTiles.add(pieces[x][y]);
	    	}
	    }
	    
	    c.weightx=1;			c.weighty=1;
	    c.gridwidth=2;			c.gridheight=3;
	    c.gridx = 1;			c.gridy = 12;
	    interfacePanel.add(pieceTiles,c);
	    
	    try {
		    pieces[0][0].setPiece(new Mushroom(new Coord(0,0)));
		    pieces[0][1].setPiece(new Bunny(new Coord(0,1)));
		    Fox foxV = new Fox(new Coord(1,0),new Coord(1,1));
		    pieces[1][0].setPiece(foxV);
		    pieces[1][1].setPiece(foxV);
		    Fox foxH = new Fox(new Coord(0,2),new Coord(1,2));
		    pieces[0][2].setPiece(foxH);
		    pieces[1][2].setPiece(foxH);
	    } catch (Exception e) {}
	    
	    
	    
	    
	    c.gridx = 0;			c.gridy = 20;
	    c.weightx=1;			c.weighty=1;
	    c.gridwidth=3;
	    interfacePanel.add(Box.createGlue(),c);
	    
		c.gridx=7;				c.gridy=0;
		c.gridwidth=4;			c.gridheight=6;
	    c.ipadx = 5; 			c.ipady = 5;
	    c.weightx=1;
	    contentPane.add(interfacePanel,c);
	}
	
	
	/** 
	 * This action listener is called when the user clicks on 
     * any of the GUI's buttons. 
     */
    public void actionPerformed(ActionEvent e)
    {
        Object o = e.getSource(); // get the action 
        

        // see if it's a JButton
        if (o instanceof ButtonTile) {
        	ButtonTile button = (ButtonTile) o;
        	actionOnButtonTile(button);
        } 
        else if (o instanceof JButton) {
        	JButton button = (JButton) o;
        	actionOnJButton(button);
        }
    }
    
    /**
     * This method handles the pressing of a ButtonTile
     * 
     * @param button the button that was pressed
     */
    private void actionOnButtonTile(ButtonTile button) {
    	output.setText(" ");
    	for(ButtonTile[] tileLine:board) {
			for(ButtonTile tile:tileLine) {
				if (button==tile) {
					if (!button.isEmpty()) {
						Piece piece = button.getPiece();
						game.getTile(piece.getCoord()).removePiece();
						if (piece instanceof Fox) game.getTile(((Fox)piece).getTail()).removePiece();
					}
					if (selectedTile!=null){
						Piece piece = selectedTile.getPiece();
						if (piece instanceof Bunny) {
							button.setPiece(new Bunny(button.getCoord()));
							output.setText("Bunny created at: " + button.getCoord().toString());
						}
						else if (piece instanceof Mushroom) {
							button.setPiece(new Mushroom(button.getCoord()));
							output.setText("Mushroom created at: " + button.getCoord().toString());
						}
						else if (piece instanceof Fox) {
							Coord coord = button.getCoord();
							Coord head = piece.getCoord();
							Coord tail = ((Fox)piece).getTail();
							
							Coord newTail = new Coord(coord.x + tail.x - head.x,
													  coord.y + tail.y - head.y);
							if (game.getTile(newTail).isEmpty()) {
								try {
									Fox fox = new Fox(coord, newTail);
									button.setPiece(fox);
									game.getTile(newTail).setPiece(fox);
									output.setText("Fox created at: " + coord.toString());
								} catch (Exception e) {
									System.out.println("Placing Fox Exception: " + e.getMessage());
								}
							}
						}
						
					}
				}
			}
		}
    	for(ButtonTile[] tileLine:pieces) {
			for(ButtonTile tile:tileLine) {
				if (button==tile) {
					if (selectedTile==null || tile.getPiece()!=selectedTile.getPiece()) {
						Coord coord = tile.getPiece().getCoord();
						selectedTile=pieces[coord.x][coord.y];
					}
					else {
						selectedTile=null;
					}
				}
			}
		}
		drawButtons();
    }
    
    /**
     * This method handles the pressing of a JButton
     * 
     * @param button the button that was pressed
     * @throws Exception 
     */
	private void actionOnJButton(JButton button){
		if(button == save){
			LevelBuilder lb = new LevelBuilder(game.getBoard());
			if(lb.save()==true){
				output.setText("Level Saved");
			}
			else{
				output.setText("Level Not Solvable");
			}
		}
		if(button == reset){
			output.setText(" ");
			for(Tile[] tileLine:board) {
				for(Tile tile:tileLine) {
					tile.removePiece();
				}
			}
			drawButtons();
		}
	}
	
    /**
     * Adding icons to the buttons
     */
    private void drawButtons() {
    	for(ButtonTile[] tileLine:board) {
			for(ButtonTile tile:tileLine) {
				drawTile(tile);
			}
		}
    	for(ButtonTile[] tileLine:pieces) {
			for(ButtonTile tile:tileLine) {
				drawTile(tile);
			}
		}
    	if (selectedTile!=null)  {
			selectedTile.setSelected(true);
			selectedTile.setBorder(BorderFactory.createLineBorder(Color.white));
			if (selectedTile.getPiece() instanceof Fox) {
				Coord tailCoord = ((Fox) selectedTile.getPiece()).getTail();
				ButtonTile tail = pieces[tailCoord.x][tailCoord.y];
				tail.setSelected(true);
				tail.setBorder(BorderFactory.createLineBorder(Color.white));
			}
		}
    }
    
    private void drawTile(ButtonTile tile) {
    	Piece piece = tile.getPiece();
		tile.setBorder(BorderFactory.createLineBorder(Color.black));
		tile.setSelected(false);
		if (piece == null) {
			if (tile.getCoord().isHole()) {
				tile.setIcon(Piece.ICON_HOLE);
			}else tile.setIcon(Piece.ICON);
			
		} else if (piece instanceof Bunny) {
			if (tile.getCoord().isHole()) {
				tile.setIcon(Bunny.ICON_HOLE);
			}else tile.setIcon(Bunny.ICON);
			
		} else if (piece instanceof Mushroom) {
			tile.setIcon(Mushroom.ICON);
		} else if (piece instanceof Fox) {
			if (tile.getCoord().equals(piece.getCoord())) {
				tile.setIcon(Fox.ICON_HEAD);
			}else tile.setIcon(Fox.ICON_TAIL);	
		}
    }
}
