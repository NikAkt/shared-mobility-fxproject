package org.example.sharedmobilityfxproject.model;

import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Grid class represents a two-dimensional grid of cells.
 * It is responsible for managing the cells and their layout within the grid.
 */
public class Grid extends Pane {

    int rows;
    int columns;

    double width;
    double height;

    private Cell[][] cells;


    public Grid(int columns, int rows, double width, double height) {

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
        updateCellPosition(cell, column, row);
    }

    public Cell getCell(int column, int row) {
        return cells[row][column];
    }

    public void moveCell(Cell cell, int newColumn, int newRow) {
        // Calculate the new position in pixels
        double w = width / columns;
        double h = height / rows;
        double newX = w * newColumn;
        double newY = h * newRow;

        // Create a translate transition
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), cell);
        transition.setToX(newX - cell.getLayoutX());
        transition.setToY(newY - cell.getLayoutY());
        transition.setOnFinished(event -> {
            // Update the cell's logical position in the array after the animation completes
            cells[cell.getRow()][cell.getColumn()] = null;
            cells[newRow][newColumn] = cell;
            cell.setColumn(newColumn);
            cell.setRow(newRow);

            // Update layout to match the logical position (optional if you want to reset layout coordinates post-animation)
            cell.setLayoutX(newX);
            cell.setLayoutY(newY);
        });

        // Start the animation
        transition.play();
    }

    private void updateCellPosition(Cell cell, int column, int row) {
        double w = width / columns;
        double h = height / rows;
        double x = w * column;
        double y = h * row;

        cell.setLayoutX(x);
        cell.setLayoutY(y);
        cell.setPrefWidth(w);
        cell.setPrefHeight(h);

        if (!getChildren().contains(cell)) {
            getChildren().add(cell);
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
