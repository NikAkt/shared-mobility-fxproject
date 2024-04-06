

package org.example.sharedmobilityfxproject.model;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.example.sharedmobilityfxproject.Main;
import org.example.sharedmobilityfxproject.controller.GameController;


import java.io.File;
import java.util.Objects;

public class Gem extends Cell {
    public boolean isCollected = false; // Flag to track if the gem has been collected
    public static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3"; // Path to the gem collect sound file
    public final Main main;
    public GameController gameController;
    // Constructor to initialise gem coordinates
    public Gem(int column, int row, Main gemCollector) {
        super(column, row);
        this.main = gemCollector; // Corrected here
        getStyleClass().add("gem");
        setUserData("gem"); // Set a custom attribute to identify the gem cell
    }


    // Override the highlight method to play the gem collect sound and increment the gem count
    @Override
    public void highlight() {
        super.highlight();
        if (!isCollected) {
            GameController.increaseGemCount(); // Corrected here
            System.out.println("Gem collected!!"); //debug'
            System.out.println();

            playGemCollectSound(); // Play the gem collect sound
            isCollected = true; // Set the flag to true after the gem is collected
        }
    }


    // Method to play the gem collect sound
    private void playGemCollectSound() {
        Media sound = new Media(new File("src/main/resources/music/sonic_ring.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        // Release resources after sound finishes playing
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }
}

