package org.example.sharedmobilityfxproject.model;

import javafx.scene.layout.Pane;

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
        double w = width / columns;
        double h = height / rows;
        double newX = w * newColumn;
        double newY = h * newRow;

        // Animate the move of the cell to the new coordinates
        cell.animateMove(newX, newY);

        // Remove the reference from the old position only if it's the same cell
        // This avoids removing the cell reference if another cell has been moved to this position.
        if (cells[cell.getRow()][cell.getColumn()] == cell) {
            cells[cell.getRow()][cell.getColumn()] = null;
        }

        // Check if the new cell position is already occupied
        if (cells[newRow][newColumn] != null) {
            // Optionally handle the case where the new cell position is already occupied
            // For example, you might want to merge cells, replace the content, or log an error
        }

        // Move the cell to the new position in the array
        cells[newRow][newColumn] = cell; // Set new position

        // Update the cell's internal coordinates
        cell.setColumn(newColumn);
        cell.setRow(newRow);
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
