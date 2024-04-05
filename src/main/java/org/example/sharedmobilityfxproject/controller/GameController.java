package org.example.sharedmobilityfxproject.controller;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.model.*;
import org.example.sharedmobilityfxproject.view.GameView;

import java.util.List;
import java.util.Objects;
import org.example.sharedmobilityfxproject.model.Grid;
public class GameController {

    public Map gameMap;
    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    public static final double BUTTON_WIDTH = 200;
    public GameView gameView;

    public static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    public static final int ROWS = 30;
    public static final int COLUMNS = 60;
    public static final double WIDTH = 800;
    public static final double HEIGHT = 600;
    public MediaPlayer mediaPlayer;
    public VBox stageSelectionBox;
    // Obstacles
    // List to keep track of all obstacles
    public List<Obstacle> obstacles;

    // Finish cell
    public Cell finishCell;

    // Boolean flag to track if the game has finished
    boolean gameFinished = false;

    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;
    public Stage primaryStage;
    public Button btnStartGame;
    public VBox gameModeBox;
    public Button btnExit;
    public VBox buttonBox;
    public VBox imgBox;
    public StackPane root;

    // Gem count
    static int gemCount = 0;
    // Label to keep track of gem count
    static Label gemCountLabel; // Label to display gem count



    // Carbon footprint
    int carbonFootprint = 0;

    public void startGame(Stage primaryStage) {
        // Start game logic here
        System.out.println("Game Started");
        gameView = new GameView();
        gameView.showInitialScreen(primaryStage);
        //gameMap.render();
    }
    public void setupKeyControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                if (btnStartGame.isFocused()) {
                    btnExit.requestFocus();
                }
            } else if (event.getCode() == KeyCode.UP) {
                if (btnExit.isFocused()) {
                    btnStartGame.requestFocus();
                }
            }
        });
    }
    public Button createStageButton(String stage, ImageView stageImage, VBox stageSelectionBox, VBox gameModeBox, StackPane root, Stage actionEvent) {
        gameView = new GameView();
        Button stageButton = new Button(stage);
        stageButton.setGraphic(stageImage);
        stageButton.setContentDisplay(ContentDisplay.TOP);
        stageButton.setOnAction(event -> gameView.selectStage(stage,stageSelectionBox,gameModeBox, root,actionEvent));
        return stageButton;
    }

    public Button createButton(String text, EventHandler<ActionEvent> action) {
        Button button = new Button(text);
        button.setMinWidth(BUTTON_WIDTH);
        button.setMaxWidth(BUTTON_WIDTH);
        button.setStyle(normalButtonStyle());
        button.setOnAction(action);
        button.setFocusTraversable(true);

        //hover colour change
        button.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                button.setStyle(focusedButtonStyle());
            } else {
                button.setStyle(normalButtonStyle());
            }
        });

        return button;
    }

    public String normalButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: rgba(255, 255, 240, 0.7); -fx-text-fill: black;";
    }

    public String focusedButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: dodgerblue; -fx-text-fill: white;";
    }
    public void playGemCollectSound() {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(GEM_COLLECT_SOUND)).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        // Release resources after sound finishes playing3211
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }




    /// Related Gem
    public static void increaseGemCount() {
        gemCount++;
        updateGemCountLabel();
    }

    // Method to update the gem count label
    public static void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
    }

///Transportation
    /**
     * Hail a taxi and change the player's appearance to yellow.
     */


}



