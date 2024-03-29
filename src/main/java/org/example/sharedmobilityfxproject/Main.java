package org.example.sharedmobilityfxproject;
import org.example.sharedmobilityfxproject.model.Grid;
import org.example.sharedmobilityfxproject.model.Cell;
import org.example.sharedmobilityfxproject.model.Obstacle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    boolean showHoverCursor = true;

    private static final int ROWS = 30;
    private static final int COLUMNS = 60;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

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


    @Override
    public void start(Stage primaryStage) {
        try {
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

            // create grid
            Grid grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);

            KeyboardActions ka = new KeyboardActions(grid);
            // fill grid
            for (int row = 0; row < ROWS; row++) {
                for (int column = 0; column < COLUMNS; column++) {

                    Cell cell = new Cell(column, row);

                    ka.setupKeyboardActions(scene);

                    grid.add(cell, column, row);
                }
            }

            // Initialize Obstacles
            obstacles = new ArrayList<>();
            obstacles.add(new Obstacle(grid, 5, 5));
            obstacles.add(new Obstacle(grid, 10, 5));
            obstacles.add(new Obstacle(grid, 5, 10));

            // Initialize currentCell after the grid has been filled
            ka.currentCell = grid.getCell(0, 0);

            root.getChildren().addAll(grid);

            // create scene and set to stage
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Grid class represents a two-dimensional grid of cells.
     * It is responsible for managing the cells and their layout within the grid.
     */
//    private class Grid extends Pane {
//
//        int rows;
//        int columns;
//
//        double width;
//        double height;
//
//        Cell[][] cells;
//
//        public Grid( int columns, int rows, double width, double height) {
//
//            this.columns = columns;
//            this.rows = rows;
//            this.width = width;
//            this.height = height;
//
//            cells = new Cell[rows][columns];
//
//        }
//
//        /**
//         * Add cell to array and to the UI.
//         */
//        public void add(Cell cell, int column, int row) {
//
//            cells[row][column] = cell;
//
//            double w = width / columns;
//            double h = height / rows;
//            double x = w * column;
//            double y = h * row;
//
//            cell.setLayoutX(x);
//            cell.setLayoutY(y);
//            cell.setPrefWidth(w);
//            cell.setPrefHeight(h);
//
//            getChildren().add(cell);
//
//        }
//
//        public Cell getCell(int column, int row) {
//            return cells[row][column];
//        }
//
//        /**
//         * Unhighlight all cells
//         */
//        public void unhighlight() {
//            for( int row=0; row < rows; row++) {
//                for( int col=0; col < columns; col++) {
//                    cells[row][col].unhighlight();
//                }
//            }
//        }
//    }

//    /**
//     * Cell class represents a single cell within a grid.
//     * It manages the visual representation and state of the cell, such as highlighting.
//     */
//    private class Cell extends StackPane {
//
//        int column;
//        int row;
//
//        public Cell(int column, int row) {
//
//            this.column = column;
//            this.row = row;
//
//            getStyleClass().add("cell");
//
////          Label label = new Label(this.toString());
////
////          getChildren().add(label);
//
//            setOpacity(0.9);
//        }
//
//        public void highlight() {
//            // ensure the style is only once in the style list
//            getStyleClass().remove("cell-highlight");
//
//            // add style
//            getStyleClass().add("cell-highlight");
//        }
//
//        public void unhighlight() {
//            getStyleClass().remove("cell-highlight");
//        }
//
//        public void hoverHighlight() {
//            // ensure the style is only once in the style list
//            getStyleClass().remove("cell-hover-highlight");
//
//            // add style
//            getStyleClass().add("cell-hover-highlight");
//        }
//
//        public void hoverUnhighlight() {
//            getStyleClass().remove("cell-hover-highlight");
//        }
//
//        public String toString() {
//            return this.column + "/" + this.row;
//        }
//    }

//    /**
//     * Obstacle class represents an obstacle on the grid.
//     * It is responsible for managing the state of the cells that form the obstacle.
//     */
//    public class Obstacle {
//
//        private Grid grid;
//        private int column;
//        private int row;
//
//        /**
//         * Constructs an obstacle located at the specified column and row.
//         *
//         * @param grid   The grid on which the obstacle is placed.
//         * @param column The column index of the obstacle's location on the grid.
//         * @param row    The row index of the obstacle's location on the grid.
//         */
//        public Obstacle(Grid grid, int column, int row) {
//            this.grid = grid;
//            this.column = column;
//            this.row = row;
//            createObstacle();
//        }
//
//        /**
//         * Marks the cell at the obstacle's location as an obstacle by changing its color to red.
//         */
//        private void createObstacle() {
//            Cell obstacleCell = grid.getCell(column, row);
//            obstacleCell.getStyleClass().add("cell-obstacle"); // Assuming Cell extends a JavaFX Pane
//        }
//
//        // Methods to get the column and row of the obstacle
//        public int getColumn() {
//            return column;
//        }
//
//        public int getRow() {
//            return row;
//        }
//    }


    /**
     * KeyboardActions class is responsible for handling keyboard input and translating it into actions within the grid.
     * It manages the current cell selection and applies keyboard actions to it.
     */
    public class KeyboardActions {

        private Grid grid;
        public Cell currentCell; // Made public for access in start method
        private int currentRow = 0;
        private int currentColumn = 0;

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
                    // Add more cases as needed
                }
            });
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
            int newRow = Math.min(Math.max(currentRow + dy, 0), grid.getRows() - 1);
            int newColumn = Math.min(Math.max(currentColumn + dx, 0), grid.getColumns() - 1);

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
            }
            // If there is an obstacle, don't move and possibly add some feedback
        }

    }
}