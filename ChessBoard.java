// Author       : David W. Collins Jr.
// Date Created : 03/12/2014
// Last Modified: 03/12/2014
// Description  : This ChessBoard class is used in the Knights Tour for my
//                Math 271 students. The ChessBoard will consist of many
//                "Squares", another class of this project.
package ktdemo;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/** ChessBoard class is a basic chessboard, although, the size can vary.
    This particular class can handle any size chessboard.
*/
public class ChessBoard extends JPanel {

    private int curSize;
    private Square[][] square;

    /** No-arg constructor simply uses the default chessboard size: 8x8.
    */
    public ChessBoard() {
        this(8);
    }
    
    /** Constructor will initialize the size of the board and will
        create the appropriate amount of Squares that comprise of the board.
    @param n - Specifies the size of the board, n x n.
    */
    public ChessBoard(int n) {
        super();
        curSize = n;
        setLayout(new GridLayout(curSize, curSize));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Color temp = new Color(50,150,255);
        // Create the array
        square = new Square[curSize][curSize];
        for(int i=0; i<curSize; i++)
            for(int j=0; j<curSize; j++) {
                square[i][j] = new Square();
                square[i][j].setLabelFontSize(300/n);
                if( (i+j)%2 == 0)
                    square[i][j].setBackground(Color.WHITE);
                else
                    square[i][j].setBackground(temp);
                add(square[i][j]);
            }        
    } // end contructor
    
    /** getCurSize() For an n x n chessboard, this method will return
        size 'n'.
    @return The linear dimension of the chessboard.
    */
    public int getCurSize() {
        return curSize;
    }

    /** showKnight() attempts to show the knight on a particular square.
        For all squares with a "name" less than the current index, the
        value will be shown on the chessboard. This is because the knight
        has already visited this square.
    @param x - the x coordinate of where the knight will be shown.
    @param y - the y coordinate of where the knight will be shown.
    @param index - the current count of the Knights Tour solution.
    */
    public void showKnight(int x, int y, int index) {
        for(int i=0; i<curSize; i++)
            for(int j=0; j<curSize; j++) {
                if(index >= Integer.parseInt(square[i][j].getName())) {
                    square[i][j].setLabel(square[i][j].getName());
                    square[i][j].setVisible(true);
                }
                else
                    square[i][j].showLabel(false);
            }
        square[x][y].showLabel(true);
            
    }
    
    /** getSquareHeight() returns the height of an individual square.
    @return The height of one square, in pixels.
    */
    public int getSquareHeight() {
        return square[0][0].getHeight();
    }
    
    /** getSquareWidth() returns the width of an individual square.
    @return The width of one square, in pixels.
    */
    public int getSquareWidth() {
        return square[0][0].getWidth();
    }
    
    /** setSquareNames() will take the solution array of the Knights Tour
        and set the chessboard squares with a name that corresponds to the
        sequence number. Used to determine which squares have already been
        visited when displaying the animation.
    @param p - the solution array of Points
    */
    public void setSquareNames(Point[] p) {
        for(int i=0; i<p.length; i++) {
            square[p[i].x][p[i].y].setName("" + (i+1));
        }
    }
}
