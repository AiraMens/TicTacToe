import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Here's the class implementing the new features(and more) specified in the assignment instructions.
 * TicTacToeGUI extends TicTacToe and provides a graphical user 
 * interface for playing Tic Tac Toe. It includes buttons for 
 * moves, game status display, and menu options for starting a
 * new game or quitting.
 *
 * @author Ayra Mensah
 * @version April 8, 2024
 */
public class TicTacToeGUI extends TicTacToe
{
    
    private int x;
    private JButton buttons[][] = new JButton[3][3];
    private JTextArea status;
    

    /**
     * Constructor for objects of class TicTacToe2
     * Sets up the GUI components, including buttons, game 
     * status display, and menu bar.
     */
    public TicTacToeGUI() 
    {
        JFrame frame = new JFrame("TicTacToeGUI");
        frame.setSize(250, 250);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0; 
        c.weighty = 1.0; 
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int indexi = i;
                final int indexj = j;
                buttons[i][j] = new JButton();
                buttons[i][j].setMargin(new Insets(0, 0, 0, 0));
                buttons[i][j].setFont(new Font("Comic Sans MS", Font.BOLD, 28));
                buttons[i][j].setPreferredSize(new Dimension(100, 100)); 
                
                //IMPLEMENTING FEEDBACK FROM CEP TO MAKE GAME MORE LIVELY
                
                //an array of colors for the buttons
                Color[] buttonColors = {Color.RED, Color.YELLOW, Color.GREEN, Color.YELLOW, Color.RED};
                // a random color from the array
                buttons[i][j].setBackground(buttonColors[(i + j) % buttonColors.length]);
                //white for better contrast
                buttons[i][j].setForeground(Color.BLACK);
                //border to be raised and thicker
                buttons[i][j].setBorder(BorderFactory.createRaisedBevelBorder());
                //update to buttons for a nicer appearance
                buttons[i][j].setMargin(new Insets(100, 100, 100, 100));

                buttons[i][j].addActionListener(e -> performAction(indexi, indexj)); // Use lambda expression
                c.gridx = j; // Set the column index
                c.gridy = i; // Set the row index
                panel.add(buttons[i][j], c);
            }
        }
        
        status = new JTextArea(3, 20);
        status.setEditable(false);
        
        
        
        frame.setJMenuBar(createMenuBar()); // Add menu bar
        
        frame.add(panel, BorderLayout.CENTER); // Add panel to the center
        frame.add(status, BorderLayout.SOUTH);
        
       
        
        clearBoard();
        
        frame.setVisible(true);
        frame.pack();
    }
    
    
    /**
     * Creates the menu bar with game options (New Game, Quit).
     * 
     * @return the created menu bar
     */
    private JMenuBar createMenuBar() 
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem quitMenuItem = new JMenuItem("Quit");
    
        // Adding action listeners to menu items
        newMenuItem.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                clearBoard(); // Starting a new game
                print(); // Updating 
            }
        });
    
        quitMenuItem.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                System.exit(0); // Quit the game
            }
        });
    
        // Set shortcuts for menu items
        newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
    
        // Add menu items to the menu
        gameMenu.add(newMenuItem);
        gameMenu.add(quitMenuItem);
    
        // Add the menu to the menu bar
        menuBar.add(gameMenu);
    
        return menuBar;
    }

     /**
     * This performs the action corresponding to clicking a 
     * button in the game grid. It also updates the game state 
     * and interface.
     * 
     * @param i the row index of the button clicked
     * @param j the column index of the button clicked
     */
    public void performAction(int i, int j) 
     {
        if (winner.equals(EMPTY)) {
            // Check if the button is not already marked
            if (board[i][j].equals(EMPTY)) {
                // Update the button text with the current player's mark
                buttons[i][j].setText(player);
                
                // Update the board
                board[i][j] = player;
                
                // Update the number of free squares
                numFreeSquares--;
                
                // Check for winner or tie
                if (haveWinner(i, j)) {
                    winner = player;
                } else if (numFreeSquares == 0) {
                    winner = TIE;
                }
                
                // Update the game status
                print();
                
                // Change to the next player only if the game is still in progress
                if (winner.equals(EMPTY)) {
                    player = (player.equals(PLAYER_X)) ? PLAYER_O : PLAYER_X;
                    // Update the status text to display the current player's turn
                    status.setText("Game's in progress: " + player + "'s turn\n\n");
                }
            }
        }
    }


    /**
     * Updates the game status display with current game state.
     */
    public void print() 
    {
        StringBuilder s = new StringBuilder();
        // Check if the game is still in progress or if it's over
        if (winner.equals(EMPTY)) {
            s.append("Game's in progress: " + player + "'s turn\n\n");
        } else {
            s.append("Game over: ");
            if (winner.equals(TIE)) {
                s.append("It's a tie!");
            } else {
                s.append(winner + " wins!");
            }
        }

        status.setText(s.toString());
    }
    
    /**
     * Clears the game board and resets the game state for a new game.
     */
    public void clearBoard() {
        // Reset the board to contain only empty cells
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
                buttons[i][j].setText(""); // Clear the text on the buttons
            }
        }
    
        // Reset other game variables
        winner = EMPTY;
        player = PLAYER_X;
        numFreeSquares = 9;
    
        // Update the game status
        print();
    }


}
