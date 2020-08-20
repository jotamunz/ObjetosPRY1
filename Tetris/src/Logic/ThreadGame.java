
package Logic;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadGame extends Thread {
    
    private Board board;
    private boolean running;
    private boolean paused = false;
    public double speed = 0.55; 
    public int score = 0;
    public int lines = 0;
    private Figure currentFigure;
    private boolean movingBlocked;
    private boolean dropDone;

    public ThreadGame(Board board) {
        this.board = board;
        this.running = true;
    }

    @Override
    public void run() {
        boolean gameOver = false;
        board.showScore(score);
        board.showLines(lines);
        while (running){
            currentFigure = board.figures[0];
            displayNextFigures();
            board.setIndex_x(currentFigure.getIndex_x()); 
            board.setIndex_y(-3);
            int currentColumn = board.getIndex_x(); 
            int currentRow = board.getIndex_y();
            this.dropDone = false;
            this.movingBlocked = false;
            while (!this.dropDone && currentRow < board.HEIGHT) {
          
                currentRow = board.getIndex_y();
                currentColumn = board.getIndex_x(); 
                
                //pinta la figura
                paint(currentFigure.tetromino, currentFigure.color);
                
                //sleep para observar la figura
                try {
                    sleep((long)(speed * 1000));
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
                
                //si llega al final o choca la caida termino y quiebra el loop
                if (board.crash(currentColumn,currentRow)){
                    try {
                        sleep((long)(speed * 1000));
                    } catch (InterruptedException ex) {
                        System.out.println(ex.getMessage());
                    }
                    this.movingBlocked = true;
                    currentRow = board.getIndex_y();
                    currentColumn = board.getIndex_x();
                    if (!board.crash(currentColumn,currentRow)){
                        dropDone = false;
                        this.movingBlocked = false;
                    }
                    else
                        this.dropDone = true;
                }
                
                //si la caida termino deja la figura permanentemente
                if (this.dropDone){
                    currentRow = board.getIndex_y();
                    currentColumn = board.getIndex_x();
                    for (int j = 0; j < 4; j++){
                        for (int k = 0; k < 4; k++){
                            if (!currentFigure.tetromino[j][k].isEmpty() && (currentRow+k) >= 0){
                                board.logicalBoard[currentColumn + j][currentRow + k].setEmpty(false);
                                board.logicalBoard[currentColumn + j][currentRow + k].setColor(currentFigure.color);
                            }
                        }
                    }
                    //si la caida genero puntos los actualiza y borra la linea
                    if(board.linesFull() != 0){
                        this.score += board.addScore();
                        this.lines += board.linesFull();
                        board.showScore(score);
                        board.showLines(lines);
                        board.rewriteDisplay();
                    }
                    board.generateNextFigure();;
                }
                //sino la borra para repintarla
                else
                    refreshPaint();
                
                //si llega al tope acaba el juego
                if (board.gameOver()){
                    gameOver = true;
                    break;
                }
                
                while (paused){
                    paint(currentFigure.tetromino, currentFigure.color);
                    try {
                        sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ThreadGame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                //aumentar una fila
                board.setIndex_y(board.getIndex_y()+1);
            }
            if(gameOver){
                board.showGameOver();
                board.saveScore();
                running = false;
                board.threadTimer.setRunning(false);
            }
        }
    }
    
    public void pause(){
        this.paused = !paused;
    }

    public void speedUp() {
        if (speed > 0.10)
            this.speed -= 0.05;
    }
    
    public void paint(Square[][] tetromino, Color color){
        for (int j = 0; j < 4; j++){ 
            for (int k = 0; k < 4; k++){ 
                if (!tetromino[j][k].isEmpty() && (board.getIndex_y()+k) >= 0){
                    board.logicalBoard[board.getIndex_x() + j][board.getIndex_y() + k].label.setBackground(color);
                }
            }
        }
    }
    
    public void refreshPaint(){
        for(int i=0; i<board.WIDTH; i++){
            for(int j=0; j<board.HEIGHT; j++){
                if (board.logicalBoard[i][j].isEmpty()){
                    board.logicalBoard[i][j].label.setBackground(Color.DARK_GRAY);
                }
            }
        }
    }
    
    public void displayNextFigures(){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (!board.figures[1].tetromino[i][j].isEmpty())
                    board.figureBoard[i][j].label.setBackground(board.figures[1].color);
                else
                    board.figureBoard[i][j].label.setBackground(Color.DARK_GRAY);
            }
        }
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (!board.figures[2].tetromino[i][j].isEmpty())
                    board.figureBoard[i][j+4].label.setBackground(board.figures[2].color);
                else
                    board.figureBoard[i][j+4].label.setBackground(Color.DARK_GRAY);
            }
        }
    }

    public boolean isMovingBlocked() {
        return movingBlocked;
    }

    public void setDropDone(boolean dropDone) {
        this.dropDone = dropDone;
    }
    
    
}

