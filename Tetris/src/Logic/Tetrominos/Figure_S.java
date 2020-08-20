
package Logic.Tetrominos;
import Logic.Figure;
import Logic.Square;
import java.awt.Color;
import Logic.Board;

public class Figure_S extends Figure{

    public Figure_S() {
        index_x = 3;
        color = Color.GREEN;
        tetromino[2][2].setEmpty(false);
        tetromino[1][2].setEmpty(false);
        tetromino[1][3].setEmpty(false);
        tetromino[0][3].setEmpty(false);
    }

@Override
    public void rotate(Board board) {
    
        Square [][] matrizNueva = new Square[3][3];
        Square [][] matrizTemp = new Square[3][3];
        Square [][] matrixTempComplete = new Square[4][4];
        int index = 0;
        board.threadGame.refreshPaint();
        
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                matrizNueva[i][j] = this.tetromino[j][i+1];
            }
        }
        
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                index = 6 + i - (j*3);
                int posY = findTetrominoY(index,3);
                int posX = findTetrominoX(index,3);
                matrizTemp[posY][posX] = matrizNueva[i][j];
            }
        }
        
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                matrixTempComplete[i][j] = new Square();
            }
        }
        for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    matrixTempComplete[j][i+1] = matrizTemp[i][j];
                }
            }
        
        if (canRotate(matrixTempComplete, board)){
            for(int i = 0; i < 3; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    this.tetromino[j][i+1] = matrizTemp[i][j];
                }
            }
        }
        board.threadGame.paint(tetromino, color);
    }
    
}
