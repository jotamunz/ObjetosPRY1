
package Logic.Tetrominos;
import Logic.Figure;
import Logic.Square;
import java.awt.Color;
import Logic.Board;

public class Figure_O extends Figure{

    public Figure_O() {
        index_x = 4;
        color = Color.YELLOW;
        tetromino[0][2].setEmpty(false);
        tetromino[1][2].setEmpty(false);
        tetromino[0][3].setEmpty(false);
        tetromino[1][3].setEmpty(false);
    }

    @Override
    public void rotate(Board board) {
        return;
    }

}
