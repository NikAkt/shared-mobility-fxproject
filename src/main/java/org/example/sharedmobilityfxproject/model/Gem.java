package org.example.sharedmobilityfxproject.model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class Gem extends Cell {
    public boolean isCollected = false; // Flag to track if the gem has been collected
    private static final String GEM_COLLECT_SOUND = "/resources/music/gem_collected.mp3"; // Path to the gem collect sound file
    private GemCollector collector;

    // Constructor to initialise gem coordinates
    public Gem(int column, int row, GemCollector collector) {
        super(column, row);
        this.collector = collector;
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
            collector.collectGem(); // Call the collectGem method on the GemCollector instance
            updateGemCountLabel(); // Update the gem count label
            isCollected = true; // Set the flag to true after the gem is collected
        }
    }
    // Method to update the gem count label
    public void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
    }

    // Method to play the gem collect sound
    public void playGemCollectSound() {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(GEM_COLLECT_SOUND)).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        // Release resources after sound finishes playing3211
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }
}
