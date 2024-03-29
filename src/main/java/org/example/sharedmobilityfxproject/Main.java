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
            if (obstacles.stream().anyMatch(obstacle -> obstacle.getColumn() == newColumn && obstacle.getRow() == newRow);) {
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