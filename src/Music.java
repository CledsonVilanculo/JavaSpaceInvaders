import java.io.File;
import javax.sound.sampled.*;

public class Music {
    public static void playMusic() {
        try {
            File music = new File("sounds/music.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(music);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception _) {}
    }

    public static void playSoundEffect(String nomeDoSom) {
        try {
            File music = new File("sounds/" + nomeDoSom);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(music);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception _) {}
    }
}
