package org.example.sharedmobilityfxproject.controller;
import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.model.Cell;
import org.example.sharedmobilityfxproject.model.Gem;
import org.example.sharedmobilityfxproject.model.Grid;
import org.example.sharedmobilityfxproject.view.GameView;
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

    // Constructor to initialise grid
    public KeyBoradActionController(GameView gameView,Grid grid) {
        this.gameView = gameView;
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
    public void hailTaxi() {
        if (!gameController.hailTaxi) {
            gameController.hailTaxi = true;
            // Increase carbon footprint
            gameController.carbonFootprint += 75;
            gameController.updateCarbonFootprintLabel();
            // Change the color of the player's cell to yellow
            currentCell.setStyle("-fx-background-color: yellow;");
        } else {
            gameController.hailTaxi = false;
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
    public void moveSelection(int dx, int dy) {
        gameView = new GameView(grid);
        if (gameView != null && gameView.obstacles != null) { // gameView가 null이 아닌지 확인
            if (gameView.obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == dx && obstacle.getRow() == dy)) {
                // Move the player to the new cell because there is no obstacle
                currentCell = new Cell(dx, dy);
                Cell nextCell = this.grid.getCell(dx, dy);

                if (currentCell != null) { //
                    // Optionally un-highlight the old cell
                    currentCell.unhighlight();
                }
                currentCell = nextCell;
                currentRow = dy;
                currentColumn = dx;

                // Optionally highlight the new cell
                if (currentCell != null) {
                    // Optionally highlight the new cell
                    currentCell.highlight();
                }
            }
        }else {
            System.err.println("GameView or obstacles are null.");
        }

        // Check if the game is finished, if so, return without allowing movement
        if (GameController.gameFinished) {
            return;
        }
        int newRow = Math.min(Math.max(currentRow + dy, 0), this.grid.getRows() - 1);
        int newColumn = Math.min(Math.max(currentColumn + dx, 0), this.grid.getColumns() - 1);
        Cell newCell = this.grid.getCell(newColumn, newRow);


        // Check if the next cell is an obstacle
        if (gameView.obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == newColumn && obstacle.getRow() == newRow)) {
            // Move the player to the new cell because there is no obstacle

            Cell nextCell = this.grid.getCell(newColumn, newRow);
            if (currentCell != null) { // currentCell이 null이 아닌지 확인
                // Optionally un-highlight the old cell
                currentCell.unhighlight();
            }

            // Optionally un-highlight the old cell
            currentCell.unhighlight();
            currentCell = nextCell;
            currentRow = newRow;
            currentColumn = newColumn;

            // Optionally highlight the new cell
            currentCell.highlight();
        }
        // If there is an obstacle, don't move and possibly add some feedback
        if (newCell == gameView.finishCell) {
            // Player reached the finish cell
            //Please check here!!
            GameController.gameFinished = true; // Set game as finished
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
            newCell.unhighlight(); // Un-highlight only the gem cell
            grid.add(new Cell(newColumn, newRow), newColumn, newRow); // Replace the gem cell with a normal cell
//            gem.updateGemCountLabel(); // Update gem count label
        }
    }

    public void movePLayer(int dx, int dy) {
        int newRow = Math.min(Math.max(playerUnosCell.getRow() + dy, 0), grid.getRows() - 1);
        int newColumn = Math.min(Math.max(playerUnosCell.getColumn() + dx, 0), grid.getColumns() - 1);
        gameView = new GameView(grid);
        // Check if the next cell is an obstacle
        if (gameView.obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == newColumn && obstacle.getRow() == newRow)) {
            // Move the player to the new cell because there is no obstacle
            Cell nextCell = grid.getCell(newColumn, newRow);

            // Optionally Un-highlight the old cell
            playerUnosCell.unhighlight();

            playerUnosCell = nextCell;

            // Optionally highlight the new cell
            playerUnosCell.highlight();
        }
        // If there is an obstacle, don't move and possibly add some feedback
    }
}