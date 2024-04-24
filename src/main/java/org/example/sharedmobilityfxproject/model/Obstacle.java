package org.example.sharedmobilityfxproject.model;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.util.Random;

/**
 * Obstacle class represents an obstacle on the grid.
 * It is responsible for managing the state of the cells that form the obstacle.
 */
public class Obstacle {
    private Node obstacleVisual;

    private Grid grid;
    private int column;
    private int row;
    private boolean isManhattanBuilding;

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
        int[] columns;
        int[] rows;
        columns = new int[]{column};
        rows = new int[]{row};
        for (int col : columns) {
            for (int r : rows) {
                File folder = new File("src/main/resources/images/obstacles");
                File[] listOfFiles = folder.listFiles();
                Random random = new Random();
                File file = listOfFiles[random.nextInt(listOfFiles.length)];
                String imagePath = file.toURI().toString();

                if (column >= 0 && column < grid.getColumns() && row >= 0 && row < grid.getRows()) {
                    Cell obstacleCell = grid.getCell(column, row);
                    // Apply CSS directly to style the cell
                    obstacleCell.setStyle("-fx-background-image: url('" + imagePath + "');" +
                            "-fx-background-size: contain;" +  // Fit the image within the cell
                            "-fx-background-position: center center;" +
                            "-fx-background-repeat: no-repeat;");

            }}
        }
    }

    // Methods to get the column and row of the obstacle
    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
