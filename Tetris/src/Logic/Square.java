
package Logic;
import java.awt.Color;
import java.io.Serializable;
import javax.swing.JLabel;

public class Square implements Serializable{
   
    public JLabel label;
    public boolean empty = true;
    private Color color = Color.DARK_GRAY;

    public Square() {
        label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setText("");
        label.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153))); 
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }    

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    
}
