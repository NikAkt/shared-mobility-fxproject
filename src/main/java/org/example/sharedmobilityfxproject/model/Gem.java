package org.example.sharedmobilityfxproject.model;
import org.example.sharedmobilityfxproject.controller.MainController;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


import java.io.File;

import static org.example.sharedmobilityfxproject.controller.SceneController.gameView;

public class Gem extends Cell {
    public boolean isCollected = false; // Flag to track if the gem has been collected
    public MainController mainController;


    // Constructor to initialise gem coordinates

    public Gem(int column, int row) {
        super(column, row);
        getStyleClass().add("gem");
        setUserData("gem");
        String imagePath = getClass().getResource("/images/gem.png").toExternalForm();




        // Set the fixed size of the Gem node to match the cell size
        setPrefHeight(10);  // Set the preferred height to 10 pixels
        setPrefWidth(8);    // Set the preferred width to 8 pixels


        this.setStyle("-fx-background-image: url('" + imagePath + "');" +
                "-fx-background-size: contain; -fx-background-position: center center;");
        this.getStyleClass().add("gem");// Set a custom attribute to identify the gem cell

    }

    // Override the highlight method to play the gem collect sound and increment the gem count
    @Override
    public void highlight() {
        super.highlight();
        if (!isCollected) {
            System.out.println("Gem collected"); //debug
            playGemCollectSound(); // Play the gem collect sound
            gameView.incrementGemCount(); // Increment the gem count
            isCollected = true; // Set the flag to true after the gem is collected
        }
    }

    // Method to play the gem collect sound
    private void playGemCollectSound() {
        Media sound = new Media(new File("src/main/resources/music/sonic_ring.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
//        private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3"; // Path to the gem collect sound file

        // Release resources after sound finishes playing
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }
}