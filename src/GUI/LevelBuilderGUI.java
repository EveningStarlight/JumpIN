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
 * @author Adam Prins
 * 		   100 879 683
 * 
 * @version 1.0.0
 * 		First instantiation of the LevelBuilder
 *		
 */
public class LevelBuilderGUI implements ActionListener {
	
    /* the selected tile and the game board */
    private ButtonTile selectedTile;
    private ButtonTile board[][];
    
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
	    
	    frame.setPreferredSize(new Dimension(750,600));
	    frame.pack(); // pack contents into our frame
        frame.setResizable(false); // we can resize it
        frame.setVisible(true); // make it visible
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //stops the program when the x is pressed
        
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
	    /*
	    undo = new JButton("undo");
	    undo.setPreferredSize(new Dimension(100,40));
	    undo.addActionListener(this);
	    interfacePanel.add(undo, c);
	    */
	    
	    c.gridx = 2;
	    /*
	    redo = new JButton("redo");
	    redo.setPreferredSize(new Dimension(100,40));
	    redo.addActionListener(this);
	    interfacePanel.add(redo, c);
	    */
	    
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
	    /*
	    nextLevel = new JButton("Next Level");
	    nextLevel.setPreferredSize(new Dimension(150,30));
	    nextLevel.addActionListener(this);
	    nextLevel.setEnabled(false);
	    interfacePanel.add(nextLevel,c);
	    */
	    
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
    	try {
    		game.selectTile(button.getCoord());
    		selectedTile = (ButtonTile) game.getSelectedTile();
    		
    		drawButtons();
    		if (game.endGame()) endGame();
    		
    	} catch (Exception exception) {
    		output.setText(exception.getMessage());
    		selectedTile=null;
    		drawButtons();
    	}
    }
    
    /**
     * This method handles the pressing of a JButton
     * 
     * @param button the button that was pressed
     */
	private void actionOnJButton(JButton button) {
		
	}
	
	
    /**
     * Handles the logic of setting the board
     * 
     * @param puzzleNumber the puzzle number that is to be set up
     */
    private void setBoard(int puzzleNumber) {
    	//this.puzzleNumber=puzzleNumber;
    	if (selectedTile!=null)  selectedTile.setSelected(false);
		selectedTile=null;
		
		for(ButtonTile[] tileLine:board) {
			for(ButtonTile tile:tileLine) {
				tile.setEnabled(true);
			}
		}
		try {
			game.setBoard(Puzzles.getPuzzle(puzzleNumber));
		} catch(Exception e) {
			System.out.print(e.getMessage());
		}
		drawButtons();
		output.setText("Welcome to puzzle: " + puzzleNumber);
    }
    
    private void endGame() {
    	if (selectedTile!=null)  selectedTile.setSelected(false);
		selectedTile=null;
		
		for(ButtonTile[] tileLine:board) {
			for(ButtonTile tile:tileLine) {
				tile.setEnabled(false);
			}
		}
    }
    
    /**
     * Adding icons to the buttons
     */
    protected void drawButtons() {
    	for(ButtonTile[] tileLine:board) {
			for(ButtonTile tile:tileLine) {
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
    	if (selectedTile!=null)  {
			selectedTile.setSelected(true);
			selectedTile.setBorder(BorderFactory.createLineBorder(Color.white));
			if (selectedTile.getPiece() instanceof Fox) {
				ButtonTile tail = (ButtonTile) game.getTile(((Fox) selectedTile.getPiece()).getTail());
				tail.setSelected(true);
				tail.setBorder(BorderFactory.createLineBorder(Color.white));
			}
		}
    }
}
