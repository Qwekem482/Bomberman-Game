package uet.oop.bomberman.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

/**
 * Link tham khảo: https://c5zone.forumotion.com/t7528-thu-thuat-phat-nhac-mp3-trong-java.
 */
public class BackgroundMusic {

    private static Clip clip;
    private static boolean playing = false;

    public static void playMusic()
    {
        if (playing) return;
        try {
            playing = true;
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/audios/BackgroundMusic.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = volume.getMaximum() - volume.getMinimum();
            volume.setValue((range * 0.9f) + volume.getMinimum());
            clip.start();

        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void stopMusic()
    {
        if (!playing) {
            return;
        }
        playing = false;
        clip.stop();
    }

}
