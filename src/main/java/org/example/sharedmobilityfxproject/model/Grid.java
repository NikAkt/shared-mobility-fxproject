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

    Cell[][] cells;

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
        cells[cell.getRow()][cell.getColumn()] = null; // Remove from old position
        cells[newRow][newColumn] = cell; // Place in new position
        updateCellPosition(cell, newColumn, newRow); // Update visual position
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
