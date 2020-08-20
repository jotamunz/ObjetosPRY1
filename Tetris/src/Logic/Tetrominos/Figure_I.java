
package Logic.Tetrominos;
import Logic.Figure;
import Logic.Square;
import java.awt.Color;
import Logic.Board;

public class Figure_I extends Figure{

    public Figure_I() {
        index_x = 3;
        color = Color.CYAN;
        tetromino[1][0].setEmpty(false);
        tetromino[1][1].setEmpty(false);
        tetromino[1][2].setEmpty(false);
        tetromino[1][3].setEmpty(false);
    }

    @Override
    public void rotate(Board board) {
    
        Square [][] matrizTemp = new Square[4][4];
        int index = 0;     
        board.threadGame.refreshPaint();

        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++)
            {
                index = 12 + i - (j*4);
                int posY = findTetrominoY(index,4);
                int posX = findTetrominoX(index,4);
                matrizTemp[posY][posX] = this.tetromino[i][j];
            }
        } 
        
        if (canRotate(matrizTemp, board)){
            for(int i = 0; i < 4; i++)
            {
                for(int j = 0; j < 4; j++)
                {
                    this.tetromino[i][j] = matrizTemp[i][j];
                }
            }
        }
        board.threadGame.paint(tetromino, color);
    }
    
}
