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

public class GameController {

    private Map gameMap;
    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    public static final double BUTTON_WIDTH = 200;
    public GameView gameView;
    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
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
    int gemCount = 0;

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


    private void playGemCollectSound() {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(GEM_COLLECT_SOUND)).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        // Release resources after sound finishes playing3211
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }

    public static class KeyboardActions {

        private Grid grid;
        public Cell currentCell; // Made public for access in start method
        public Cell playerUnosCell; // Made public for access in start method
        private int currentRow = 0;
        private int currentColumn = 0;

        // Constructor to initialise grid
        public KeyboardActions(Grid grid) {
            this.grid = grid;
            // Don't initialize currentCell here
        }

        public void setupKeyboardActions(Scene scene) {
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case RIGHT -> moveSelection(1, 0);
                    case LEFT -> moveSelection(-1, 0);
                    case UP -> moveSelection(0, -1);
                    case DOWN -> moveSelection(0, 1);
                    case H -> currentCell.highlight();
                    case U -> currentCell.unhighlight();
                    // Player
                    case D -> movePLayer(1, 0);
                    case A -> movePLayer(-1, 0);
                    case W -> movePLayer(0, -1);
                    case S -> movePLayer(0, 1);
                    case T -> hailTaxi();
                    // Add more cases as needed
                }
            });
        }




        /**
         * Hail a taxi and change the player's appearance to yellow.
         */
        private void hailTaxi() {
            if (!hailTaxi) {
                hailTaxi = true;
                // Increase carbon footprint
                carbonFootprint += 75;
//                updateCarbonFootprintLabel();
                // Change the color of the player's cell to yellow
                currentCell.setStyle("-fx-background-color: yellow;");
            } else {
                hailTaxi = false;
                // Change the color of the player's cell back to blue
                currentCell.setStyle("-fx-background-color: blue;");
            }
        }

        /**
         * Attempts to move the current selection by a specified number of columns and rows.
         * The movement is performed if the destination cell is not an obstacle.
         *
         * @param dx The number of columns to move. A positive number moves right, a negative number moves left.
         * @param dy The number of rows to move. A positive number moves down, a negative number moves up.
         */
        private void moveSelection(int dx, int dy) {
            // Check if the game is finished, if so, return without allowing movement
            if (gameFinished) {
                return;
            }

            int newRow = Math.min(Math.max(currentRow + dy, 0), grid.getRows() - 1);
            int newColumn = Math.min(Math.max(currentColumn + dx, 0), grid.getColumns() - 1);
            Cell newCell = grid.getCell(newColumn, newRow);

            // Check if the next cell is an obstacle
            if (obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == newColumn && obstacle.getRow() == newRow)) {
                // Move the player to the new cell because there is no obstacle
                Cell nextCell = grid.getCell(newColumn, newRow);

                // Optionally unhighlight the old cell
                currentCell.unhighlight();

                currentCell = nextCell;
                currentRow = newRow;
                currentColumn = newColumn;

                // Optionally highlight the new cell
                currentCell.highlight();
            }
            // If there is an obstacle, don't move and possibly add some feedback
            if (newCell == finishCell) {
                // Player reached the finish cell
                gameFinished = true; // Set game as finished
                // Display "Level Complete" text
                Label levelCompleteLabel = new Label("Level Complete");
                levelCompleteLabel.setStyle("-fx-font-size: 24px;");
                StackPane root = (StackPane) grid.getScene().getRoot();
                root.getChildren().add(levelCompleteLabel);

                // Exit the game after five seconds
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(event -> ((Stage) grid.getScene().getWindow()).close());
                pause.play();
            }

            if ("gem".equals(newCell.getUserData())) {
                grid.getChildren().remove(newCell);
                newCell.unhighlight(); // Unhighlight only the gem cell
                grid.add(new Cell(newColumn, newRow), newColumn, newRow); // Replace the gem cell with a normal cell
//                updateGemCountLabel(); // Update gem count label
            }
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

        private void movePLayer(int dx, int dy) {
            int newRow = Math.min(Math.max(playerUnosCell.getRow() + dy, 0), grid.getRows() - 1);
            int newColumn = Math.min(Math.max(playerUnosCell.getColumn() + dx, 0), grid.getColumns() - 1);

            // Check if the next cell is an obstacle
            if (obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == newColumn && obstacle.getRow() == newRow)) {
                // Move the player to the new cell because there is no obstacle
                Cell nextCell = grid.getCell(newColumn, newRow);

                // Optionally unhighlight the old cell
                playerUnosCell.unhighlight();

                playerUnosCell = nextCell;

                // Optionally highlight the new cell
                playerUnosCell.highlight();
            }
            // If there is an obstacle, don't move and possibly add some feedback
        }
    }
//    private void updateGemCountLabel() {
//        gemCountLabel.setText("Gem Count: " + gemCount);
//    }
//
//    // Method to play the gem collect sound
//
//
//    // Method to update the carbon footprint label
//    private void updateCarbonFootprintLabel() {
//        carbonFootprintLabel.setText("Carbon Footprint: " + carbonFootprint);
//    }

}




