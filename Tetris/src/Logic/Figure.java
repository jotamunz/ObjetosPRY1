
package Logic;
import static Logic.Board.DIMENSION;
import java.awt.Color;
import java.io.Serializable;

public abstract class Figure implements Serializable{
   public Square[][] tetromino = new Square[4][4];
   protected Color color;
   protected int index_x;

    public Figure() {
        for (int i=0; i < 4; i++){
            for (int j=0; j < 4; j++){
                this.tetromino[i][j] = new Square();
                this.tetromino[i][j].label.setBounds(DIMENSION*i, DIMENSION*j, DIMENSION, DIMENSION);
            }
        }
    }
    
    public int getIndex_x() {
        return index_x;
    }
        
    public abstract void rotate(Board board);
    
    public boolean canRotate(Square[][] matrix, Board board){
        for (int i = 0; i < 4; i++){ //columns
            for (int j = 0; j < 4; j++){ //rows
                if (!matrix[i][j].isEmpty() && (board.getIndex_y() + j) >= 0){
                    if ((board.getIndex_x() + i) >= board.WIDTH || (board.getIndex_x() + i) < 0 || (board.getIndex_y() + j) >= board.HEIGHT || !board.logicalBoard[(board.getIndex_x() + i)][board.getIndex_y() + j].isEmpty())
                        return false;
                }
            }
        }
        return true;
    }
    
    public int findTetrominoX(int index,int tamanoMatriz)
    {
        int currentIndex = 0;

        for(int i = 0; i < tamanoMatriz; i++)
        {
            for(int j = 0; j < tamanoMatriz; j++)
            {
                if(currentIndex == index)
                    return j;
                currentIndex ++;
            }
        }
        return 0;
    }

    public int findTetrominoY(int index,int tamanoMatriz)
    {
        int currentIndex = 0;

        for(int i = 0; i < tamanoMatriz; i++)
        {
            for(int j = 0; j < tamanoMatriz; j++)
            {
                if(currentIndex == index)
                    return i;
                currentIndex ++;
            }
        }
        return 0;
    }
   
    
}
