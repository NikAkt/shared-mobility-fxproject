package org.example.sharedmobilityfxproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.awt.Point;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;

// Main class extends Application for JavaFX application
public class Main extends Application {

    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;

    // Grid dimensions and window dimensions
    private static final int ROWS = 30;
    private static final int COLUMNS = 60;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    // Gem count
    int gemCount = 0;

    // Carbon footprint
    int carbonFootprint = 0;

    // Label to keep track of gem count
    Label gemCountLabel; // Label to display gem count

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint

    // Player (will be implemented)
//    private Point player;

    // Obstacles (will be implemented)
    // List to keep track of all obstacles
    private List<Obstacle> obstacles;
//    private int obstacleX;
//    private int obstacleY;
//    private boolean gameOver;
//    private int facingDirection;

//    removed from scene
//    ImageView imageView = new ImageView( new Image( "https://upload.wikimedia.org/wikipedia/commons/c/c7/Pink_Cat_2.jpg"));

    // Finish cell
    private Cell finishCell;

    // Boolean flag to track if the game has finished
    boolean gameFinished = false;

    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create a StackPane to hold all elements
            StackPane root = new StackPane();
            Scene scene = new Scene(root);

            // Settings
            Image icon = new Image(String.valueOf(getClass().getResource("/images/icon.png")));
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("Shared Mobility Application");
            primaryStage.setWidth(WIDTH);
            primaryStage.setHeight(HEIGHT);
            primaryStage.setResizable(false);
//            primaryStage.setFullScreen(true);
//            primaryStage.setFullScreenExitHint("Press esc to minimize !");

            // Create grid for the game
            Grid grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);

            // Create keyboard actions handler
            KeyboardActions ka = new KeyboardActions(grid);
            // Fill grid with cells
            for (int row = 0; row < ROWS; row++) {
                for (int column = 0; column < COLUMNS; column++) {
                    Cell cell = new Cell(column, row);
                    ka.setupKeyboardActions(scene);
                    grid.add(cell, column, row);
                }
            }

            // Create label for gem count
            gemCountLabel = new Label("Gem Count: " + gemCount);
            gemCountLabel.setStyle("-fx-font-size: 16px;");
            gemCountLabel.setAlignment(Pos.TOP_LEFT);
            gemCountLabel.setPadding(new Insets(10));

            // Create label for carbon footprint
            carbonFootprintLabel = new Label("Carbon Footprint: " + carbonFootprint);
            carbonFootprintLabel.setStyle("-fx-font-size: 16px;");
            carbonFootprintLabel.setAlignment(Pos.TOP_LEFT);
            carbonFootprintLabel.setPadding(new Insets(10));

            // Create a VBox to hold the gem count label
            VBox vbox = new VBox(gemCountLabel, carbonFootprintLabel);
            vbox.setAlignment(Pos.TOP_LEFT);

            // Place the gem after the grid is filled and the player's position is initialized
            int gemColumn;
            int gemRow;
            do {
                gemColumn = (int) (Math.random() * COLUMNS);
                gemRow = (int) (Math.random() * ROWS);
            } while (gemColumn == 0 && gemRow == 0); // Ensure gem doesn't spawn at player's starting position
            Gem gem = new Gem(gemColumn, gemRow);
            grid.add(gem, gemColumn, gemRow);

            // Initialise Obstacles
            obstacles = new ArrayList<>();
            obstacles.add(new Obstacle(grid, 5, 5));
            obstacles.add(new Obstacle(grid, 10, 5));
            obstacles.add(new Obstacle(grid, 5, 10));

            // Place the finish cell after the grid is filled and the player's position is initialised
            int finishColumn;
            int finishRow;
            do {
                finishColumn = (int) (Math.random() * COLUMNS);
                finishRow = (int) (Math.random() * ROWS);
            } while ((finishColumn == 0 && finishRow == 0) || (finishColumn == gemColumn && finishRow == gemRow)); // Ensure finish doesn't spawn at player's starting position or gem position
            finishCell = new Cell(finishColumn, finishRow);
            finishCell.getStyleClass().add("finish");
            grid.add(finishCell, finishColumn, finishRow);

            // Initialise currentCell after the grid has been filled
            ka.currentCell = grid.getCell(0, 0);

            // Add background image, grid, and gem count label to the root StackPane
            root.getChildren().addAll(grid, vbox);

            // Create scene and set to stage
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Grid class represents a two-dimensional grid of cells.
     * It is responsible for managing the cells and their layout within the grid.
     */
    private class Grid extends Pane {

        int rows;
        int columns;

        double width;
        double height;

        Cell[][] cells;

        // Constructor to initialise grid dimensions and cell array
        public Grid( int columns, int rows, double width, double height) {
            this.columns = columns;
            this.rows = rows;
            this.width = width;
            this.height = height;
            cells = new Cell[rows][columns];
        }

        /**
         * Add cell to array and to the UI.
         */
        public void add(Cell cell, int column, int row) {
            cells[row][column] = cell;
            double w = width / columns;
            double h = height / rows;
            double x = w * column;
            double y = h * row;
            cell.setLayoutX(x);
            cell.setLayoutY(y);
            cell.setPrefWidth(w);
            cell.setPrefHeight(h);
            getChildren().add(cell);
        }

        // Get cell at given coordinates
        public Cell getCell(int column, int row) {
            return cells[row][column];
        }

        /**
         * Unhighlight all cells
         */
        public void unhighlight() {
            for(int row = 0; row < rows; row++) {
                for(int col = 0; col < columns; col++) {
                    cells[row][col].unhighlight();
                }
            }
        }
    }

    /**
     * Cell class represents a single cell within a grid.
     * It manages the visual representation and state of the cell, such as highlighting.
     */
    private class Cell extends StackPane {

        int column;
        int row;

        // Constructor to initialise cell coordinates
        public Cell(int column, int row) {
            this.column = column;
            this.row = row;
            getStyleClass().add("cell");
            // Label label = new Label(this.toString());
            // getChildren().add(label);
            setOpacity(0.9);
        }

        // Highlight the cell
        public void highlight() {
            // Ensure the style is only coded once in the style list
            getStyleClass().remove("cell-highlight");
            // Add style
            getStyleClass().add("cell-highlight");
        }

        // Unhighlight the cell
        public void unhighlight() {
            getStyleClass().remove("cell-highlight");
        }

        // Highlight the cell when hovering over it
        public void hoverHighlight() {
            // Ensure the style is only coded once in the style list
            getStyleClass().remove("cell-hover-highlight");
            // Add style
            getStyleClass().add("cell-hover-highlight");
        }

        // Unhighlight the cell when hovering over it
        public void hoverUnhighlight() {
            getStyleClass().remove("cell-hover-highlight");
        }

        // Override toString method to provide coordinates
        public String toString() {
            return this.column + "/" + this.row;
        }
    }

    // Gem class representing a gem in the grid
    private class Gem extends Cell {
        // Constructor to initialize gem coordinates
        public Gem(int column, int row) {
            super(column, row);
            getStyleClass().add("gem");
            setUserData("gem"); // Set a custom attribute to identify the gem cell
        }
    }

    /**
     * Obstacle class represents an obstacle on the grid.
     * It is responsible for managing the state of the cells that form the obstacle.
     */
    public class Obstacle {

        private Grid grid;
        private int column;
        private int row;

        /**
         * Constructs an obstacle located at the specified column and row.
         *
         * @param grid   The grid on which the obstacle is placed.
         * @param column The column index of the obstacle's location on the grid.
         * @param row    The row index of the obstacle's location on the grid.
         */
        public Obstacle(Grid grid, int column, int row) {
            this.grid = grid;
            this.column = column;
            this.row = row;
            createObstacle();
        }

        /**
         * Marks the cell at the obstacle's location as an obstacle by changing its color to red.
         */
        private void createObstacle() {
            Cell obstacleCell = grid.getCell(column, row);
            obstacleCell.getStyleClass().add("cell-obstacle"); // Assuming Cell extends a JavaFX Pane
        }

        // Methods to get the column and row of the obstacle
        public int getColumn() {
            return column;
        }

        public int getRow() {
            return row;
        }
    }


    /**
     * KeyboardActions class is responsible for handling keyboard input and translating it into actions within the grid.
     * It manages the current cell selection and applies keyboard actions to it.
     */
    public class KeyboardActions {

        private Grid grid;
        public Cell currentCell; // Made public for access in start method
        private int currentRow = 0;
        private int currentColumn = 0;

        // Constructor to initialise grid
        public KeyboardActions(Grid grid) {
            this.grid = grid;
            // Don't initialise currentCell here
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
                updateCarbonFootprintLabel();
                // Change the color of the player's cell to yellow
                currentCell.setStyle("-fx-background-color: yellow;");
            } else {
                hailTaxi = false;
                // Change the color of the player's cell back to blue
                currentCell.setStyle("-fx-background-color: blue;");
            }
        }

        /**
         * Checks whether the specified cell position coincides with any of the obstacles in the grid.
         *
         * @return true if there is an obstacle at the specified location, otherwise false.
         */
        private boolean isNextCellObstacle(int column, int row) {
            return obstacles.stream().anyMatch(obstacle -> obstacle.getColumn() == column && obstacle.getRow() == row);
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

            int newRow = Math.min(Math.max(currentRow + dy, 0), grid.rows - 1);
            int newColumn = Math.min(Math.max(currentColumn + dx, 0), grid.columns - 1);
            Cell newCell = grid.getCell(newColumn, newRow);

            // Check if the next cell is an obstacle
            if (!isNextCellObstacle(newColumn, newRow)) {
                // Move the player to the new cell because there is no obstacle
                Cell nextCell = grid.getCell(newColumn, newRow);

                // Optionally unhighlight the old cell
                currentCell.unhighlight();
                currentCell = nextCell;
                currentRow = newRow;
                currentColumn = newColumn;

                // Optionally highlight the new cell
                currentCell.highlight();
                // If there is an obstacle, don't move and possibly add some feedback
            }

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
                gemCount++; // Increase gem count
                grid.getChildren().remove(newCell);
                newCell.unhighlight(); // Unhighlight only the gem cell
                grid.add(new Cell(newColumn, newRow), newColumn, newRow); // Replace the gem cell with a normal cell
                updateGemCountLabel(); // Update gem count label
            }
        }
    }

    // Method to update the gem count label
    private void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
        }

    // Method to update the carbon footprint label
    private void updateCarbonFootprintLabel() {
        carbonFootprintLabel.setText("Carbon Footprint: " + carbonFootprint);
    }
}
