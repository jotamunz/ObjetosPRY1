
package Main;
import GUI.*;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Tetris {

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        new Menu().setVisible(true);

    }
}
