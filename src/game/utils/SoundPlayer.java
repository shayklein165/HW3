package game.utils;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    public static void playSound(String fileName) {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(SoundPlayer.class.getResourceAsStream("sounds/" + fileName));
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }
}