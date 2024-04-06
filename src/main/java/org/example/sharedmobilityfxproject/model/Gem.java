

package org.example.sharedmobilityfxproject.model;
import org.example.sharedmobilityfxproject.controller.GameController;

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
    public GameController gameController;
    // Constructor to initialise gem coordinates

    public Gem(int column, int row) {
        super(column, row);
        getStyleClass().add("gem");
        setUserData("gem"); // Set a custom attribute to identify the gem cell
    }


    // Override the highlight method to play the gem collect sound and increment the gem count
    @Override
    public void highlight() {
        super.highlight();
        if (!isCollected) {
            System.out.println("Gem collected"); //debug
            playGemCollectSound(); // Play the gem collect sound
            gameController.gemCount++; // Increment the gem count
            gameController.updateGemCountLabel(); // Update the gem count label
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

