
package Logic;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadTimer extends Thread{
    
    public int seconds = 0;
    public int level = 0;
    private int levelThreshold = 120;
    private Board board;
    private boolean running;
    private boolean paused = false;

    public ThreadTimer(Board board) {
        this.board = board;
        this.running = true;
    }
    
    @Override
    public void run(){
        board.showLevel(level);
        while(running){
            
            board.showTimer(seconds);
            
            try {
                 sleep(1000);
             } catch (InterruptedException ex) {
                 System.out.println(ex.getMessage());
             }
            
            seconds += 1;
            if (levelUp()){
                board.threadGame.speedUp();
                this.level += 1;
                board.showLevel(level);
            }
            
            while (paused){
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ThreadGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void pause() {
        this.paused = !paused;
    }
    
    public boolean levelUp(){
        if (seconds - (levelThreshold*level) >= levelThreshold && seconds < 1200)
            return true;
        return false;
    }

}

