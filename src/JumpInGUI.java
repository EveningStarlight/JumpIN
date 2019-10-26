import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 * GUI implementation 
 * holds the game layout and the other intractable elements
 *  
 * @author Adam Prins
 * 			100 879 683
 * @version 1.3.1
 * 		Merging from GUI branch onto master
 * 		
 */
public class JumpInGUI implements ActionListener {
	
	public static void main(String[] args) {
		new JumpInGUI();
	}
	
	public static final int BOARD_SIZE = 5;
	
	/* The reset menu item */
    private JMenuItem resetItem;
    
    /* The quit menu item */
    private JMenuItem quitItem;
    
    /* the selected tile and the game board */
    private ButtonTile tileSelected;
    private ButtonTile board[][];
    
    /* The undo and redo Buttons */
    private JButton undo;
    private JButton redo;
    
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
	
	    JMenuBar menubar = new JMenuBar();
	    frame.setJMenuBar(menubar); // add menu bar to our frame
	
	    JMenu fileMenu = new JMenu("Options"); // create a menu
	    menubar.add(fileMenu); // and add to our menu bar
	
	    resetItem = new JMenuItem("Reset"); // create a menu item called "Reset"
	    fileMenu.add(resetItem); // and add to our menu
	
	    quitItem = new JMenuItem("Quit"); // create a menu item called "Quit"
	    fileMenu.add(quitItem); // and add to our menu
	
	    // listen for menu selections
	    resetItem.addActionListener(this); 
	    quitItem.addActionListener(this); // create an anonymous inner class
	
	    
	    
	    
	    JPanel interfacePanel = new JPanel();
	    interfacePanel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
	    c.anchor = (GridBagConstraints.LINE_START);
	    c.fill = GridBagConstraints.HORIZONTAL;
	    
	    c.weightx=0;
	    c.weighty=0;
	    c.gridx = 0;
	    c.gridy = 0;
	    undo = new JButton("undo");
	    undo.setPreferredSize(new Dimension(75,40));
	    undo.addActionListener(this);
	    interfacePanel.add(undo, c);
	    
	    c.gridx = 1;
	    redo = new JButton("redo");
	    redo.setPreferredSize(new Dimension(75,40));
	    redo.addActionListener(this);
	    interfacePanel.add(redo, c);
	    
	    c.gridx = 2;
	    c.weightx=1;
	    interfacePanel.add(Box.createGlue(), c);
	    
	    outputStatic = new JLabel("Output: ");
	    c.gridx = 0;
	    c.gridy = 5;
	    interfacePanel.add(outputStatic,c);
	    c.weightx=1;
	    c.gridx = 0;
	    c.gridwidth=2;
	    c.gridy = 6;
	    output = new JLabel("Game Start");
	    output.setPreferredSize(new Dimension(200,30));
	    interfacePanel.add(output,c);
	    
	    c.weightx=1;
	    c.weighty=1;
	    c.gridx = 0;
	    c.gridy = 20;
	    c.gridwidth=3;
	    interfacePanel.add(Box.createGlue(),c);
	    
	    
	    JPanel boardPanel = new JPanel();
	    boardPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        
        
	    /* The button that is clicked to increment the counter. */
	    board = new ButtonTile[BOARD_SIZE][BOARD_SIZE];
		
		for (int x=0; x<BOARD_SIZE; x++) {
			for (int y=0; y<BOARD_SIZE; y++) {
                c.gridx = x;
                c.gridy = y;
                board[x][y]= new ButtonTile(new Coord(x,y));
                board[x][y].setPreferredSize(new Dimension(100,100));
                board[x][y].setMargin(new Insets(0,0,0,0));
                board[x][y].setEnabled(true);
                boardPanel.add(board[x][y],c);
                board[x][y].addActionListener(this);
			}
		}
	    
		
		
	    c = new GridBagConstraints();
	    c.anchor = GridBagConstraints.LINE_START;
	    c.gridx=0;	c.gridwidth=6;
	    c.gridy=0;	c.gridheight=6;
	    c.ipadx = 10; c.ipady = 10;
	    c.weightx=0;
	    contentPane.add(boardPanel,c);
	    
	    c.gridx=6;	c.gridwidth=4;
	    c.gridy=0;	c.gridheight=6;
	    c.ipadx = 5; c.ipady = 5;
	    c.weightx=1;
	    contentPane.add(interfacePanel,c);
	    
	    frame.setPreferredSize(new Dimension(800,600));
	    frame.pack(); // pack contents into our frame
        frame.setResizable(true); // we can resize it
        frame.setVisible(true); // make it visible
        
        game = new Game(board);
        //drawButtons();

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
        	try {
        		game.selectTile(button.getCoord());
        		if (tileSelected==null) {
            		button.setSelected(true);
                	tileSelected = button;
            	}
            	else if (tileSelected == button) {
            		button.setSelected(false);
            		tileSelected = null;
            	}
            	else {
            		tileSelected.setSelected(false);
            		button.setSelected(true);
            		tileSelected = button;
            	}
        	} catch (Exception exception) {
        		output.setText(exception.getMessage());
        		tileSelected=null;
        	}
        	
        	
        } 
        else if (o instanceof JButton) {
        	JButton button = (JButton) o;
        	 if (button == undo) {
        		 try {
        		 game.undoMove();
        		 } catch (Exception exception) {
        			 output.setText(exception.getMessage());
        		 }
        	 }
        	 else if (button == redo) {
        		 try {
        			 game.redoMove();
        		 } catch (Exception exception) {
        			 output.setText(exception.getMessage());
        		 }
        	 }
        }
        else if (o instanceof JMenuItem){ // it's a JMenuItem
            JMenuItem item = (JMenuItem)o;

            if (item == resetItem) {
            } 
            else if (item == quitItem) {
                System.exit(0);
            }
        }

    }
    
    private void drawButtons() {
    	for(ButtonTile[] tileLine:board) {
			for(ButtonTile tile:tileLine) {
				Piece p = tile.getPiece();
				
			}
		}
    }
    
}
