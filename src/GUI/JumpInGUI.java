package GUI;

import Pieces.*;
import Solver.Solver;
import Model.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * GUI implementation 
 * holds the game layout and the other intractable elements
 *  
 * @authors Adam Prins, Jay McCracken, Matthew Harris
 * 		   100 879 683, 101 066 860,   101 073 502
 * 
 * @version 2.1.0
 * 		Added support for LevelBuilder
 *		
 */
public class JumpInGUI implements ActionListener {
	
	public static void main(String[] args) {
		new JumpInGUI();
	}
	
	private int puzzleNumber=1;
	
	/* JMenu File items */
    private JMenuItem resetItem;
    private JMenuItem quitItem;
    private JMenuItem saveItem;
    private JMenuItem loadItem;
    
    
    /* JMenu Level items */
    private JMenuItem levelSelect;
    private JMenuItem levelSolver;
    private JMenuItem levelHint;
    private JMenuItem levelBuilderItem;
    
    /* the selected tile and the game board */
    private ButtonTile selectedTile;
    private ButtonTile board[][];
    
    /* The undo and redo Buttons */
    private JButton undo;
    private JButton redo;
    
    /* The next level button */
    private JButton nextLevel;
    
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
	public JumpInGUI() {
		JFrame frame = new JFrame("JumpIN"); 
	    Container contentPane = frame.getContentPane(); 
	    contentPane.setLayout(new GridBagLayout());
	    // get the content pane so we can put stuff in
	
	    createMenus(frame);
	    createBoardPanel(contentPane);
	    createPanelSpacing(contentPane);
	    createInterfacePanel(contentPane);
	    
	    frame.setPreferredSize(new Dimension(750,600));
	    frame.pack(); // pack contents into our frame
        frame.setResizable(false); // we can resize it
        frame.setVisible(true); // make it visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops the program when the x is pressed
        
        try {
			game = new Game(board);
			game.setBoard(Puzzles.getPuzzle(puzzleNumber));
			drawButtons();
		} catch (Exception e) {
			output.setText(e.getMessage());
		}
	}
	
	/**
	 * Creates the menus for this GUI
	 * @param frame the frame that we will add the menus to
	 */
	private void createMenus(JFrame frame) {
		JMenuBar menubar = new JMenuBar();
	    frame.setJMenuBar(menubar); // add menu bar to our frame
	
	    //Creates and adds menus to our JFrame
	    JMenu fileMenu = new JMenu("File");
	    JMenu levelMenu = new JMenu("Level Options");
	    menubar.add(fileMenu);
	    menubar.add(levelMenu);
	    
	    //Populates the fileMenu
	    saveItem = new JMenuItem("Save");
	    loadItem = new JMenuItem("Load");
	    resetItem = new JMenuItem("Reset");
	    quitItem = new JMenuItem("Quit");
	    fileMenu.add(saveItem);
	    fileMenu.add(loadItem);
	    fileMenu.add(resetItem);
	    fileMenu.add(quitItem);
	    saveItem.addActionListener(this);
	    loadItem.addActionListener(this);
	    resetItem.addActionListener(this); 
	    quitItem.addActionListener(this);
	    
	    //Populates the levelMenu
	    levelSelect = new JMenuItem("Level Select");
	    levelBuilderItem = new JMenuItem("Level Builder");
	    levelSolver = new JMenuItem("Level Solution");
	    levelHint = new JMenuItem("Hint");
	    levelMenu.add(levelSelect);
	    levelMenu.add(levelBuilderItem);
	    levelMenu.add(levelSolver);
	    levelMenu.add(levelHint);
	    levelSelect.addActionListener(this);
	    levelBuilderItem.addActionListener(this);
	    levelHint.addActionListener(this);
	    levelSolver.addActionListener(this);
	}
	
	/**
	 * Creates the content of the board Panel and adds it to the given contentPane
	 * @param contentPane the contentPane of the frame that we are adding this JPanel to
	 */
	private void createBoardPanel(Container contentPane) {
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
	private void createPanelSpacing(Container contentPane) {
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
	private void createInterfacePanel(Container contentPane) {
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
	    undo = new JButton("undo");
	    undo.setPreferredSize(new Dimension(100,40));
	    undo.addActionListener(this);
	    interfacePanel.add(undo, c);
	    
	    c.gridx = 2;
	    redo = new JButton("redo");
	    redo.setPreferredSize(new Dimension(100,40));
	    redo.addActionListener(this);
	    interfacePanel.add(redo, c);
	    
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
	    nextLevel = new JButton("Next Level");
	    nextLevel.setPreferredSize(new Dimension(150,30));
	    nextLevel.addActionListener(this);
	    nextLevel.setEnabled(false);
	    interfacePanel.add(nextLevel,c);
	    
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
        else if (o instanceof JMenuItem){ // it's a JMenuItem
            JMenuItem item = (JMenuItem)o;
            actionOnJMenu(item);
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
		if (button == undo) {
			try {
				game.undoMove();
				selectedTile=null;
				drawButtons();
			} catch (Exception exception) {
				 output.setText(exception.getMessage());
			}
		}
		else if (button == redo) {
			try {
				game.redoMove();
				selectedTile=null;
				drawButtons();
			} catch (Exception exception) {
				output.setText(exception.getMessage());
			}
		}
		else if (button == nextLevel) {
			puzzleNumber++;
			if (puzzleNumber<0) {
				throw new IllegalArgumentException("I don't know how you got here for next level, honest.");
			} else if (puzzleNumber<Puzzles.getMaxPuzzle()) { 
				setBoard(puzzleNumber);
			} else {
				setBoard(0);
				JOptionPane.showMessageDialog(new JFrame(), "Congratulations!\nYou have compleated all the available Puzzles.\nI hope you look forward to our next release!","YOU WIN",JOptionPane.INFORMATION_MESSAGE,Bunny.ICON_HOLE);
			}
		}
	}
	
	/**
	 * This method handles the pressing of a JMenuItem
	 * 
	 * @param item the item that was selected
	 */
	private void actionOnJMenu(JMenuItem item) {
		if (item == resetItem) {
        	setBoard(puzzleNumber);
        } 
        else if (item == quitItem) {
            System.exit(0);
        }
        else if (item == saveItem) {
        	game.saveState(puzzleNumber);
        }
        else if (item == loadItem) {
        	puzzleNumber=game.loadState();
        	output.setText("Puzzle: "+puzzleNumber);
        	drawButtons();
        }
        else if(item==levelBuilderItem) {
        	new LevelBuilderGUI();
        }else if (item == levelSolver) {
        	try {
				Solver solution = new Solver(game);
				JFrame f = new JFrame();
				JOptionPane.showMessageDialog(f, solution.toString());
			} catch (Exception e1) {
				System.out.println("Level Solver error: " +e1.getMessage());
			}
        }//end levelSolver
        else if (item == levelHint) {
        	try {     		
				Solver solution = new Solver(game);
				Move hint = solution.getNextMove();
				board[hint.COORD_OLD.x][hint.COORD_OLD.y].setBorder(BorderFactory.createLineBorder(Color.orange));
				
				if (board[hint.COORD_OLD.x][hint.COORD_OLD.y].getPiece() instanceof Fox) {
					Coord foxCoord = ((Fox) board[hint.COORD_OLD.x][hint.COORD_OLD.y].getPiece()).getTail();
					board[foxCoord.x][foxCoord.y].setBorder(BorderFactory.createLineBorder(Color.orange));
				}
				
				board[hint.COORD_NEW.x][hint.COORD_NEW.y].setBorder(BorderFactory.createLineBorder(Color.magenta));
			} catch (Exception e1) {
				System.out.println("Level Solver error: " +e1.getMessage());
			}
        }//end levelHint
        else if (item == levelSelect) {
        	String level="0";
        	int maxPuzzles = (Puzzles.getMaxPuzzle() - 1);
        	int levelNumber = 0;
    		int counter = 0;
    		while(levelNumber >= Puzzles.getMaxPuzzle() || levelNumber < 1) {
    			switch(counter) {
    				case 0:
    					level =  JOptionPane.showInputDialog("Please enter the level you wish to go to (1-" + maxPuzzles + ")");
    					break;
    				case 1: 
    					level =  JOptionPane.showInputDialog("Reminder: You must select a level number in the range (1-" + maxPuzzles + ")");
    					break;
    				case 2: 
    					level =  JOptionPane.showInputDialog("Excuse me, I said between (1-" + maxPuzzles + ")");
    					break;
    				case 3: 
    					level =  JOptionPane.showInputDialog("You are serious? Between (1-" + maxPuzzles + ")");
    					break;
    				case 4: 
    					level =  JOptionPane.showInputDialog("Alright last time... Between (1-" + maxPuzzles + ")");
    					break;
    				case 5: 
    					JFrame f = new JFrame();
    					JOptionPane.showMessageDialog(f, "YOU LOSE");
    					System.exit(0);
    			}
    			try {	
    				levelNumber = Integer.parseInt(level);
                } catch (NumberFormatException | NullPointerException nfe) {}
    			counter++;
    		}//end While
        	setBoard(levelNumber);
        }//end levelSelect
	}
    
    /**
     * Handles the logic of setting the board
     * 
     * @param puzzleNumber the puzzle number that is to be set up
     */
    private void setBoard(int puzzleNumber) {
    	this.puzzleNumber=puzzleNumber;
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
		undo.setEnabled(true);
		redo.setEnabled(true);
		nextLevel.setEnabled(false);
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

		undo.setEnabled(false);
		redo.setEnabled(false);
		nextLevel.setEnabled(true);
    }
    
    /**
     * Adding icons to the buttons
     */
    private void drawButtons() {
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
    	
    	undo.setEnabled(!game.isUndoEmpty());
    	redo.setEnabled(!game.isRedoEmpty());
    }
    
    
}
