
package Logic;
import GUI.Display;
import GUI.PauseDisplay;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Logic.Tetrominos.*;

public class Board {
    
    public static int WIDTH = 10;
    public static int HEIGHT = 20;
    public static int DIMENSION = 30;
    
    private int index_x = 3;
    private int index_y = -3;
    private KeyListener listener;
    private Display display = new Display();
    private PauseDisplay pauseDisplay = new PauseDisplay(this);
    public Square[][] logicalBoard = new Square[WIDTH][HEIGHT];
    public Figure[] figures = new Figure[3];
    public Square[][] figureBoard = new Square[4][8];
    public ThreadGame threadGame;
    public ThreadTimer threadTimer;
    public SavedGame save;
    private SavedScore scoreBoard = new SavedScore();
    private String user;
    
    public Board(SavedGame save, String user) { 
        this.save = save;
        this.user = user;
        this.listener = new KeyListener(){
            
            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override           
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch( keyCode ) { 
                    case KeyEvent.VK_UP:
                        rotate();
                        break;
                    case KeyEvent.VK_DOWN:
                        moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT :
                        moveRight();
                        break;
                    case KeyEvent.VK_ESCAPE :
                        pause();
                        break;
                 }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {

            }
            
        };
        generateBoard();
        display.setVisible(true);
        display.addKeyListener(listener);
        threadGame = new ThreadGame(this);
        threadTimer = new ThreadTimer(this);
        scoreBoard.Upload();
    }
    
    public Board(SavedGame save, String user, Square[][] logicalBoard, Square[][] figureBoard, Figure[] figures, int level, int seconds, int score, int lines, double speed){
        this.save = save;
        this.user = user;
        this.logicalBoard = logicalBoard;
        this.figureBoard = figureBoard;
        this.figures = figures;

        this.listener = new KeyListener(){
            
            @Override
            public void keyTyped(KeyEvent e) {
            }
            
            @Override           
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch( keyCode ) { 
                    case KeyEvent.VK_UP:
                        rotate();
                        break;
                    case KeyEvent.VK_DOWN:
                        moveDown();
                        break;
                    case KeyEvent.VK_LEFT:
                        moveLeft();
                        break;
                    case KeyEvent.VK_RIGHT :
                        moveRight();
                        break;
                    case KeyEvent.VK_ESCAPE :
                        pause();
                        break;
                 }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {

            }
            
        };
        recoverBoard();
        display.setVisible(true);
        display.addKeyListener(listener);
        threadGame = new ThreadGame(this);
        threadTimer = new ThreadTimer(this);
        
        this.threadTimer.level = level;
        this.threadTimer.seconds = seconds;
        this.threadGame.score = score;
        this.threadGame.lines = lines;
        this.threadGame.speed = speed;
        scoreBoard.Upload();
        threadGame.refreshPaint();
        
    }
    
    public void generateBoard(){
        for(int i=0; i<WIDTH; i++){
            for(int j=0; j<HEIGHT; j++){
                logicalBoard[i][j] = new Square();
                logicalBoard[i][j].label.setBounds(DIMENSION*i, DIMENSION*j, DIMENSION, DIMENSION);
                display.jPanel_Board.add(logicalBoard[i][j].label);
            }
        }
        for (int i=0; i<3; i++){
            this.figures[i] = generateFigure();
        }
        for (int i=0; i<4; i++){
            for (int j=0; j<8; j++){
                figureBoard[i][j] = new Square();
                figureBoard[i][j].label.setBounds(DIMENSION*i, DIMENSION*j, DIMENSION, DIMENSION);
                display.jPanel_Figures.add(figureBoard[i][j].label);
            }
        }
    }
    
    public void recoverBoard(){ 
        for(int i=0; i<WIDTH; i++){
            for(int j=0; j<HEIGHT; j++){
                logicalBoard[i][j].label.setBounds(DIMENSION*i, DIMENSION*j, DIMENSION, DIMENSION);
                display.jPanel_Board.add(logicalBoard[i][j].label);
            }
        }
        for (int i=0; i<4; i++){
            for (int j=0; j<8; j++){
                figureBoard[i][j].label.setBounds(DIMENSION*i, DIMENSION*j, DIMENSION, DIMENSION);
                display.jPanel_Figures.add(figureBoard[i][j].label);
            }
        }
    }
    
    public int getIndex_x (){
        return this.index_x;
    }
    
    public void setIndex_x(int index_x) {
        this.index_x = index_x;
    }

    public int getIndex_y() {
        return index_y;
    }

    public void setIndex_y(int index_y) {
        this.index_y = index_y;
    }
    
    public void moveLeft(){
        boolean canTurn = true;
        for (int j = 0; j < 4 && canTurn; j++){ //rows
            for (int k = 0; k < 4 && canTurn; k++){ //columns
                if (!figures[0].tetromino[k][j].isEmpty() && (index_y + j) >= 0){
                    if (((index_x + k)-1) < 0 || !logicalBoard[(index_x + k)-1][(index_y + j)].isEmpty())
                        canTurn = false;
                    break;
                }
            }
        }
        if (canTurn){
            index_x--;
            threadGame.refreshPaint();
            threadGame.paint(figures[0].tetromino, figures[0].color);
            if (crash(index_x,index_y))
                threadGame.setDropDone(true);
        }
    }
 
    public void moveRight(){
        boolean canTurn = true;
        for (int j = 0; j < 4 && canTurn; j++){ //rows
            for (int k = 3; k >= 0 && canTurn; k--){ //columns
                if (!figures[0].tetromino[k][j].isEmpty() && (index_y + j) >= 0){
                    if (((index_x + k)+1) >= WIDTH || threadGame.isMovingBlocked() || !logicalBoard[(index_x + k)+1][(index_y + j)].isEmpty())
                        canTurn = false;
                    break;
                }
            }
        }
        if (canTurn){
            index_x++;
            threadGame.refreshPaint();
            threadGame.paint(figures[0].tetromino, figures[0].color);
            if (crash(index_x,index_y))
                threadGame.setDropDone(true);
        }
    }
    
    public void moveDown(){
        boolean canMove = true;
        for (int j = 0; j < 4 && canMove; j++){ //columns
            for (int k = 3; k >= 0 && canMove; k--){ //rows
                if (!figures[0].tetromino[j][k].isEmpty() && (index_y + j) >= 0){
                    if (((index_y + k)+2) >= HEIGHT || threadGame.isMovingBlocked() || !logicalBoard[index_x + j][(index_y + k)+1].isEmpty() || !logicalBoard[index_x + j][(index_y + k)+2].isEmpty())
                        canMove = false;
                    break;
                }
            }
        }
        if (canMove){
            index_y++;
            threadGame.refreshPaint();
            threadGame.paint(figures[0].tetromino, figures[0].color);
        }
    }
    
    public void rotate(){
        figures[0].rotate(this);
    }
    
    public boolean crash(int index_x, int index_y){
        boolean crash = false;
        for (int j = 0; j < 4 && !crash; j++){ // columns
            for (int k = 3; k >= 0 && !crash; k--){ //rows
                if (!figures[0].tetromino[j][k].isEmpty() && (index_y+k) >= 0 && (index_x+j) < WIDTH && (index_x+j) >= 0){
                    if (((index_y + k)+1) >= HEIGHT || !logicalBoard[index_x + j][(index_y + k) + 1].isEmpty())
                        crash = true;   
                    break;
                }
            }
        }
        return crash;
    }
    
    public void generateNextFigure(){
        for (int i = 0; i < 2; i++){
            this.figures[i] = this.figures[i+1];
        }
        this.figures[2] = generateFigure();
    }
    
    private Figure generateFigure(){
        Figure figure = null;
        int random = (int) Math.floor(Math.random()*7);
        switch(random)
        {
            case 0: 
                figure = new Figure_I();
                break;
            case 1: 
                figure = new Figure_L();
                break;
            case 2:
                figure = new Figure_O(); 
                break;
            case 3: 
                figure = new Figure_S();  
                break;
            case 4: 
                figure = new Figure_T();  
                break;
            case 5: 
                figure = new Figure_Z();
                break;
            case 6: 
                figure = new Figure_J();  
                break;
        }
        return figure;
    }
    
    public boolean isLineFull(int heightPos)
    {
        for(int i=0; i<WIDTH; i++){
            if(logicalBoard[i][heightPos].isEmpty()){
                return false;
            }
        }
        return true;
    }
    
    public int linesFull()
    {
        int res = 0; 
        for(int i=0; i<HEIGHT; i++){
            if(isLineFull(i))
                res ++;                
        }        
        return res;
    }
    
    public int addScore()
    {
        int res = 0; 
        for(int i=0; i<HEIGHT; i++){
            if(isLineFull(i))
                res ++;                
        }        
        if(res >= 3 )
            res ++;
        return res;
    }
    
    public void rewriteDisplay()
    {
        Square copyBoard[][] = new Square[WIDTH][HEIGHT];
        int indexY = HEIGHT-1;
        
        for(int i=0; i<WIDTH; i++)
            for(int j=0; j<HEIGHT; j++)
            {
                copyBoard[i][j] = new Square();
                copyBoard[i][j].label.setBounds(DIMENSION*i, DIMENSION*j, DIMENSION, DIMENSION);
            }
        
        for(int j = HEIGHT-1; j >= 0; j--){
            if (!isLineFull(j))
            {
                for(int i = WIDTH-1; i >= 0; i--){
                    copyBoard[i][indexY].empty = this.logicalBoard[i][j].empty;
                    copyBoard[i][indexY].setColor(this.logicalBoard[i][j].getColor());
                }
                indexY --;
            }              
        }

        for(int j = HEIGHT-1; j >= 0; j--){
            for(int i = WIDTH-1; i >= 0; i--)
            {
                this.logicalBoard[i][j].empty = copyBoard[i][j].empty;
                this.logicalBoard[i][j].setColor(copyBoard[i][j].getColor());
                this.logicalBoard[i][j].label.setBackground(logicalBoard[i][j].getColor());
            }
        }
    }
    
    public void showScore(int score){
        this.display.jLabel_Score.setText(String.valueOf(score));
    }
    
    public void showGameOver(){
        this.display.jLabel_GameOver.setText("GAME OVER");
    }
    
    public void showTimer(int time){
        this.display.jLabel_Timer.setText(String.valueOf(time));
    }
    
    public void showLevel(int level){
        this.display.jLabel_Level.setText(String.valueOf(level));
    }
    
    public void showLines(int lines){
        this.display.jLabel_Lines.setText(String.valueOf(lines));
    }
    
    public boolean gameOver(){
        for (int i = 0; i<WIDTH; i++){
            if (!this.logicalBoard[i][0].isEmpty())
                return true;
        }
        return false;
    }
    
    public void pause(){
        threadGame.pause();
        threadTimer.pause();
        pauseDisplay.setVisible(true);
    }
    
    public void startGame(){
        threadGame.start();
        threadTimer.start();
    }
    
    public void saveGame(){
        save.user = this.user;
        save.logicalBoard = this.logicalBoard;
        save.figureBoard = this.figureBoard;
        save.figures = this.figures;
        save.level = this.threadTimer.level;
        save.seconds = this.threadTimer.seconds;
        save.score = this.threadGame.score;
        save.lines = this.threadGame.lines;
        save.speed = this.threadGame.speed;
        
        save.Save();
    }
    
    public void saveScore(){
        scoreBoard.add(new Score(this.user, this.threadGame.score));
        scoreBoard.Save();
    }
}



