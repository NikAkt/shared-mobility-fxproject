package org.example.sharedmobilityfxproject.controller;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.model.*;
import org.example.sharedmobilityfxproject.view.GameView;

import java.util.ArrayList;
import java.util.Objects;




public class KeyBoradActionController {

    //class call
    public GameController gameController;
    public GameView gameView;
    public Gem gem;
    public Grid grid;
    public Cell currentCell; // Made public for access in start method
    public Cell playerUnosCell; // Made public for access in start method
    public int currentRow;
    public int currentColumn;

    // Newly Added
    private boolean playerMovementEnabled = true;
    private boolean onBus = false;
    private Grid grid;
    public Cell currentCell;
    public Player playerUno;

    // Constructor to initialise grid
    public KeyBoradActionController(GameView gameView,Grid grid) {
        this.gameView = gameView;
        this.grid = grid;
        // Don't initialize currentCell here
    }

    public void setupKeyboardActions(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (playerMovementEnabled) {
                switch (event.getCode()) {
                    case D -> movePlayer(1, 0);
                    case A -> movePlayer(-1, 0);
                    case W -> movePlayer(0, -1);
                    case S -> movePlayer(0, 1);
                    case T -> hailTaxi();
                    case E -> togglePlayerMovement();
                }
            } else if (event.getCode() == KeyCode.E) {
                togglePlayerMovement();
            }
        });
    }

    private void movePlayer(int dx, int dy) {
        int newRow = Math.min(Math.max(playerUno.getCoordY() + dy, 0), grid.getRows() - 1);
        int newColumn = Math.min(Math.max(playerUno.getCoordX() + dx, 0), grid.getColumns() - 1);
        if (canMoveTo(newColumn, newRow)) {
            playerUno.getCell().unhighlight();
            playerUno.setCell(grid.getCell(newColumn, newRow));
            playerUno.getCell().highlight();
            interactWithCell(playerUno.getCell());
        }
    }

    private boolean canMoveTo(int x, int y) {
        return obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == x && obstacle.getRow() == y);
    }

    private void interactWithCell(Cell cell) {
        if ("gem".equals(cell.getUserData())) {
            collectGem(cell);
        } else if (cell instanceof busStop) {
            interactWithBusStop((busStop) cell);
        } else if (cell == finishCell) {
            finishGame();
        }
    }

    private void collectGem(Cell gemCell) {
        grid.getChildren().remove(gemCell);
        grid.add(new Cell(gemCell.getColumn(), gemCell.getRow()), gemCell.getColumn(), gemCell.getRow());
        playGemCollectSound();
    }

    private void interactWithBusStop(busStop stop) {
        System.out.println("Interacted with Bus Stop at: " + stop.getX() + ", " + stop.getY());
    }

    private void finishGame() {
        gameFinished = true;
        Label levelCompleteLabel = new Label("Level Complete");
        levelCompleteLabel.setStyle("-fx-font-size: 24px;");
        StackPane root = (StackPane) grid.getScene().getRoot();
        root.getChildren().add(levelCompleteLabel);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> ((Stage) grid.getScene().getWindow()).close());
        pause.play();
    }

    private void hailTaxi() {
        hailTaxi = !hailTaxi;
        currentCell.setStyle(hailTaxi ? "-fx-background-color: yellow;" : "-fx-background-color: blue;");
        if (hailTaxi) {
            carbonFootprint += 75;
            updateCarbonFootprintLabel();
        }
    }

    private void togglePlayerMovement() {
        if (this.onBus) {
            int[] playerLocation = {playerUno.getCoordX(), playerUno.getCoordY()};
            boolean atBusStop = busStopCoordinates.stream()
                    .anyMatch(location -> location[0] == playerLocation[0] && location[1] == playerLocation[1]);
            if (atBusStop) {
                playerMovementEnabled = true;
                this.onBus = false;
                System.out.println("You got off the bus.");
            } else {
                System.out.println("You can only get off the bus at a bus stop.");
            }
        } else if (playerUno.getCell() instanceof busStop) {
            playerMovementEnabled = !playerMovementEnabled;
            System.out.println(playerMovementEnabled ? "Impatient" : "Waiting for bus");
            ((busStop) playerUno.getCell()).setPlayerHere(!playerMovementEnabled);
        }
    }
}

public void printArrayContents() {
    System.out.println("Obstacle Coordinates:");
    for (int[] coordinates : obstacleCoordinates) {
        System.out.println("X: " + coordinates[0] + ", Y: " + coordinates[1]);
    }

    System.out.println("Bus Stop Coordinates:");
    for (int[] coordinates : busStopCoordinates) {
        System.out.println("X: " + coordinates[0] + ", Y: " + coordinates[1]);
    }
}

// Place the gem after the grid is filled and the player's position is initialized
public void generateGems(Grid grid, int numberOfGems) {
    for (int i = 0; i < numberOfGems; i++) {
        int gemColumn;
        int gemRow;
        do {
            gemColumn = (int) (Math.random() * COLUMNS);
            gemRow = (int) (Math.random() * ROWS);
        } while (containsPointInArray(busStopCoordinates, gemColumn, gemRow) || containsPointInArray(obstacleCoordinates, gemColumn, gemRow));

        Gem gem = new Gem(gemColumn, gemRow,this);
        grid.add(gem, gemColumn, gemRow);
    }
}

public boolean containsPointInArray(ArrayList<int[]> array, int x, int y) {
    for (int[] coordinates : array) {
        if (coordinates[0] == x && coordinates[1] == y) {
            return true;
        }
    }
    return false;
}

// Method to update the gem count label
private static void updateGemCountLabel() {
    gemCountLabel.setText("Gem Count: " + gemCount);
}

// Method to play the gem collect sound
private void playGemCollectSound() {
    Media sound = new Media(Objects.requireNonNull(getClass().getResource(GEM_COLLECT_SOUND)).toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();

    // Release resources after sound finishes playing3211
    mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
}

// Method to update the carbon footprint label
private void updateCarbonFootprintLabel() {
    carbonFootprintLabel.setText("Carbon Footprint: " + carbonFootprint);
}
}