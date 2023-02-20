// Author       : David W. Collins Jr.
// Date Created : 03/12/2014
// Last Modified: 03/13/2014
// Description  : This is the Knights Tour GUI class for my Math 271 class.
//                The knights tour will allow the user to select a starting
//                square, and display a path (the tour) for a standard knight
//                to traverse the chessboard and visit each and every square
//                on the board once and only once. 
//
//                This class defines the board and displays it. Board size is
//                allowed to change, via the menu. Actual implementation of
//                the knights tour is created by the student, from a method
//                findSolution() in the class KTSolution.
//
// Concerns     : The current GUI implementation is simply a grid. The 
//                possibility of overlaying an opaque panel over the grid
//                is still being considered to visually show the path of the
//                knight as it tours the board.
//package ktdemo;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/** GUI class for the KnightsTour
*/
public class KnightsTour {
    
    private static final int EXITS = 8;
    private static final int INVALID_MOVE = 100;
    
    private JFrame window;
    private ChessBoard board;
    private JLabel title;
    private JMenuBar menuBar;
    private JMenu menuGrid;
    private JMenuItem[] menuOption;
    private Timer timer;
    private int moveCount;
    private Point[] solution;
    
    private int[][] path;
    private static final int[][] OFFSETS = new int[][]{{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -1}};
    
    /** No-arg constructor initializes the GUI:
        window - the main frame 
        board  - the chessboard
        title  - blurb how to start the tour.
        menuBar - the menu bar
        menuGrid - the grid menu
        menuOption[] - the array of possible board sizes
        timer - the timer for the animation
        moveCount - helps cycle through the solution for animation.
    */
    public KnightsTour() {
        // Create the window
        window = new JFrame("The Knights Tour");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800,600);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setLayout(new BorderLayout());
        
        // Create the chessboard
        board = new ChessBoard();
        board.addMouseListener(new StartTourListener());

        // Create the title blurb
        title = new JLabel("Click on any square to start the Knights Tour.");
        title.setHorizontalAlignment(JLabel.CENTER);
       
        // Create the MenuBar & Menu
        menuBar = new JMenuBar();
        menuGrid = new JMenu("Grid Sizes");
        menuOption= new JMenuItem[5];
        for(int i=0; i<4; i++) {
            menuOption[i] = new JMenuItem("Make " + (2*i+6) + " x " + (2*i+6));
            menuOption[i].setActionCommand("" + (2*i+6));
            menuOption[i].addActionListener(new GridChangeListener());
            menuGrid.add(menuOption[i]); 
        }
        menuOption[4] = new JMenuItem("Custom Board Size...");
        menuOption[4].addActionListener(new GridChangeListener());
        menuOption[4].setActionCommand("Custom");
        menuGrid.add(menuOption[4]);
        menuBar.add(menuGrid);
        
        // Add & Display!
        window.setJMenuBar(menuBar);
        window.add(title, BorderLayout.PAGE_START);
        window.add(board, BorderLayout.CENTER);
        window.setVisible(true);
        
        // Create timer & move counter
        timer = new Timer(500, new TimerListener());
        moveCount = 0;
    }

    /** GridChangeListener class will listen for menu changes
        for the chess board and update the board to meet the change.
    */
    private class GridChangeListener implements ActionListener {

       @Override
       public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Custom")) {
                JOptionPane.showMessageDialog(window,"Feature not implemented yet.",
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                int val = Integer.parseInt(e.getActionCommand());
  
                // Remove the old board, create and add a new one.
                if(board.getCurSize() != val) {
                    window.remove(board);
                    board = new ChessBoard(val);
                    board.addMouseListener(new StartTourListener());
                    window.add(board, BorderLayout.CENTER);
                    window.revalidate();
                    window.repaint();
                }
             }
        }
    } // end of GridChangeListener class
 
    /** TimerListener class is the actual animation of watching the knight
        visit all the squares on the chessboard. For each Point in the
        'solution', the knight is displayed. For each point that has already
        been previously displayed, the sequence number will be shown. 
        For more information, see StartTourListener class.
    */
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(moveCount < board.getCurSize() * board.getCurSize()) {
                board.showKnight(solution[moveCount].x, solution[moveCount].y, moveCount);
                moveCount++;
            }
            else {
                moveCount = 0;
                timer.stop();
                JOptionPane.showMessageDialog(window, "Knights Tour Completed!" ,
                        "Finished.", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
   
    /** StartTourListener class will begin the animation sequence.
        Animation sequence is controlled through the timer, see 
        TimerListener class above. Before the timer is started, the
        solution must first be found. This solution array depends on 
        where the knight will start - assigned by the user click.
        Once the solution is found, the sequence number is assigned
        to each of the squares in the chessboard in order to show
        the user the sequence after the knight leaves a particular square.
    */
    private class StartTourListener implements MouseListener {

        @Override // Not implemented
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            Point p = new Point(e.getY()/board.getSquareHeight() , e.getX()/board.getSquareWidth());
            boolean foundIt = findSolution(p);
            if(foundIt) {
                board.setSquareNames(solution);
                timer.start();
            }
            else
                JOptionPane.showMessageDialog(window, "Unable to find a solution "
                        + " for the Knights Tour.", "Oh no!", JOptionPane.WARNING_MESSAGE);
        }

        @Override // Not implemented
        public void mouseReleased(MouseEvent e) {
        }

        @Override // Not implemented
        public void mouseEntered(MouseEvent e) {
        }

        @Override // Not implemented
        public void mouseExited(MouseEvent e) {
        }
    } // end of StartTourListener class
    
    /**
     * The non recursive method to find the knights tour given a specific starting point
     * @author Nikolas Leslie
     * @param start A point representing the position on the board in which the knight starts
     * @return A boolean saying if a solution to the tour was found
     */
    private boolean findSolution(Point start) {
        solution = new Point[board.getCurSize() * board.getCurSize()];
        
        path = new int[board.getCurSize()][board.getCurSize()];
        for(int i = 0; i < board.getCurSize(); i++){
            for(int j = 0; j < board.getCurSize(); j++){
                path[i][j] = -1;
                if(j == (int)start.getX() && i == (int)start.getY()){
                    path[i][j] = 0;
                }
            }
        }
        
        if(findSolution((int)start.getX(), (int)start.getY(), 1, solution.length)){
            int count = 0;
            while(count < solution.length){
                for(int i = 0; i < board.getCurSize(); i++){
                    for(int j = 0; j < board.getCurSize(); j++){
                        if(path[i][j] == count){
                            solution[count] = new Point(j, i);
                            count++;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * A recursive method to find the path of the knights tour
     * @author Nikolas Leslie
     * @param x The current x value of the knight
     * @param y The current y value of the knight
     * @param count The move number currently on
     * @param size The number of total squares on the board
     * @return A boolean representing if a solution to the tour was found
     */
    private boolean findSolution(int x, int y, int count, int size){
        if(count >= size){
            return true;
        }
        
        for(int[] offset: OFFSETS){
            if(validMove(x, y, offset) && path[y + offset[1]][x + offset[0]] == -1){
                path[y + offset[1]][x + offset[0]] = count;
                if(findSolution(x + offset[0], y + offset[1], count + 1, size)){
                    return true;
                }
                path[y + offset[1]][x + offset[0]] = -1;
            }
        }
        
        return false;
    }

    /**
     * A method that tells if a move is within the bounds of the board
     * @author Nikolas leslie
     * @param x The starting x value
     * @param y The starting y value
     * @param offset The x and y offsets representing the move
     * @return A boolean representing if the move is within bounds of the board
     */
    private boolean validMove(int x, int y, int[] offset){
        return (x + offset[0] >= 0 && x + offset[0] < board.getCurSize()) && (y + offset[1] >= 0 && y + offset[1] < board.getCurSize());
    }
}
