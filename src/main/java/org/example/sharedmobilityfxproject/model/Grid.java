package org.example.sharedmobilityfxproject.model;

import javafx.scene.layout.Pane;


/**
 * Grid class represents a two-dimensional grid of cells.
 * It is responsible for managing the cells and their layout within the grid.
 */
public class Grid extends Pane {
//60 x 30

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
        initializeCells();

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

    private void initializeCells() {

    }

    public Cell getCell(int column, int row) {
        if (cells != null && row >= 0 && row < rows && column >= 0 && column < columns) {
            return cells[row][column];
        } else {
            return null; // 셀이 없을 경우 null 반환
        }
    }


    /**
     * Unhighlight all cells
     */
    public void unhighlight() {
        for( int row=0; row < rows; row++) {
            for( int col=0; col < columns; col++) {
                cells[row][col].unhighlight();
            }
        }
    }


    public int getRows() {
        return rows;
    }


    public int getColumns() {
        return columns;
    }
}
