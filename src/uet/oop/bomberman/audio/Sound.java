package uet.oop.bomberman.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Link tham khảo: https://helpex.vn/question/lam-cach-nao-de-phat-am-thanh-trong-java-5cbbb237ae03f60a1ccdbff3
 */
public class Sound {

    public static void makeSound(String sound)
    {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/audios/" + sound + ".wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
