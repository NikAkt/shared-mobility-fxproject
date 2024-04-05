package org.example.sharedmobilityfxproject.controller;
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
import org.example.sharedmobilityfxproject.Main;
import org.example.sharedmobilityfxproject.model.*;
import org.example.sharedmobilityfxproject.view.GameView;

import java.util.List;
import java.util.Objects;

public class GameController {

    public static Main GemCollector;
    public Map gameMap;
    public Grid grid;
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


    // Boolean flag to track if the game has finished
    static boolean gameFinished = false;

    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;
    public Stage primaryStage;
    public Button btnStartGame;
    public VBox gameModeBox;
    public Button btnExit;
    public VBox buttonBox;
    public VBox imgBox;
    public StackPane root;

    // ****Gem count****
    static int gemCount = 0;
    // Label to keep track of gem count
    static Label gemCountLabel; // Label to display gem count

    @FunctionalInterface
    public interface GemCollector {
        void collectGem();
    }

    // ****Carbon footprint****
    int carbonFootprint = 0;
    Label carbonFootprintLabel; // Label to display carbon footprint


    //Game Start initialise method
    public void startGame(Stage primaryStage) {
        // Start game logic here
        System.out.println("Game Started");
        Grid grid = new Grid(COLUMNS,ROWS,WIDTH,HEIGHT); // Grid 객체 생성
        gameView = new GameView(grid);
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
    public Button createStageButton(String stage, ImageView stageImage, VBox stageSelectionBox, VBox gameModeBox, StackPane root, Stage actionEvent, MediaPlayer mdv) {
        gameView = new GameView(grid);
        Button stageButton = new Button(stage);
        stageButton.setGraphic(stageImage);
        stageButton.setContentDisplay(ContentDisplay.TOP);
        stageButton.setOnAction(event -> gameView.selectStage(stage,stageSelectionBox,gameModeBox, root,actionEvent,mdv));
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
///Transportation
    /**
     * Hail a taxi and change the player's appearance to yellow.
     */


    ///CO2
    public void updateCarbonFootprintLabel() {
        carbonFootprintLabel.setText("Carbon Footprint: " + carbonFootprint);
    }

    public static void increaseGemCount() {
        gemCountLabel = new Label();
        updateGemCountLabel();
        gemCount++;
        gemCountLabel.setText("Gem Count: " + gemCount);
    }
    public static void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
    }

}


