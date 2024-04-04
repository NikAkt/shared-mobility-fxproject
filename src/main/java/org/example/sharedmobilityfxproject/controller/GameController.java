package org.example.sharedmobilityfxproject.controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.model.Cell;
import org.example.sharedmobilityfxproject.model.Map;
import org.example.sharedmobilityfxproject.model.MenuFrontElement;
import org.example.sharedmobilityfxproject.model.Obstacle;
import org.example.sharedmobilityfxproject.view.GameView;

import java.io.File;
import java.util.List;

public class GameController {
    private Map gameMap;
    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    public static final double BUTTON_WIDTH = 200;
    public GameView gameView;
    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    private static final int ROWS = 30;
    private static final int COLUMNS = 60;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;
    public MediaPlayer mediaPlayer;
    // Obstacles
    // List to keep track of all obstacles
    private List<Obstacle> obstacles;

    // Finish cell
    private Cell finishCell;

    // Boolean flag to track if the game has finished
    boolean gameFinished = false;

    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;
    public Stage primaryStage;
    public Button btnStartGame;
    public VBox gameModeBox;
    public MenuFrontElement menuFrontElement;
    public Button btnExit;
    public VBox buttonBox;
    public VBox imgBox;
    public StackPane root;

    // Gem count
    int gemCount = 0;

    // Carbon footprint
    int carbonFootprint = 0;

    public GameController() {
        // Initialize the game map with default dimensions
        this.gameMap = new Map();
    }

    public void startGame(Pane gamePane) {
        // Start game logic here
        gameMap.render(gamePane);
        System.out.println("Game Started");
        gameView= new GameView();

        // Label to keep track of gem count
        Label gemCountLabel; // Label to display gem count

        // Label to keep track of total carbon footprint
        Label carbonFootprintLabel; // Label to display carbon footprint
        gameView.showInitialScreen();
    }
    private void setupKeyControls(Scene scene) {
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
    public Button createStageButton(String stage, ImageView stageImage) {
        Button stageButton = new Button(stage);
        stageButton.setGraphic(stageImage);
        stageButton.setContentDisplay(ContentDisplay.TOP);
        stageButton.setOnAction(event -> gameView.selectStage(stage));
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
}


