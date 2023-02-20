// Author       : David W. Collins Jr.
// Date Created : 03/12/2014
// Last Modified: 03/12/2014
// Description  : This Square class is used in the Knights Tour for my
//                Math 271 students.
package ktdemo;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Square class is made specifically for the Knights Tour problem. Each
    square on the chessboard will have a label, either depicting a knight
    or the order sequence number of the solution of the knights tour.
*/
public class Square extends JPanel {
    public static ImageIcon knight;
    private JLabel orderSeq;
    
    /** Constructor initializes the panel, label & adds the label to the panel.
    */
    public Square() {
        super();
        knight = new ImageIcon("knight.jpg");
        
        setBorder(BorderFactory.createLineBorder(Color.BLUE));
        orderSeq = new JLabel(knight);
        orderSeq.setVisible(false);
        orderSeq.setHorizontalAlignment(JLabel.CENTER);
        orderSeq.setVerticalAlignment(JLabel.CENTER);
        add(orderSeq);
    }
    
    /** setLabelFontSize() method will adjust the font size of the labels
        within the grid to the specified value.
    @param n - The size of the font to render.
    */
    public void setLabelFontSize(int n) {
        orderSeq.setFont(new Font("Comic Sans MS", Font.PLAIN, n));
    }
 
    /** showLabel() sets the visibility of the label to the boolean passed.
    @param ch - true or false, to set visible or not visible. 
    */
    public void showLabel(boolean ch) {
        orderSeq.setVisible(ch);
    }
    
    /** setLabel() sets the label to either null or a string
    @param s - the string value corresponding to the name of the label
    */
    public void setLabel(String s) {
        orderSeq.setIcon(null);
        orderSeq.setText(s);
    }
}
