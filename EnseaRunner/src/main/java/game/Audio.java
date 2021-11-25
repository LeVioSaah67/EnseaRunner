package game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Audio {

    private static Clip clip;

    public Audio(String song) {

        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(song));
            clip = AudioSystem.getClip();
            clip.open(audio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Clip getClip() {
        return clip;
    }

    public void play() {clip.start();}
    public static void stop() {if(clip!=null) {clip.stop(); }}

    public static void playSound(String song) {
        Audio a = new Audio(song);
        a.play();
    }

}
