package org.example.sharedmobilityfxproject.model;

import javafx.scene.layout.Pane;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.io.File;

/**
 * Grid class represents a two-dimensional grid of cells.
 * It is responsible for managing the cells and their layout within the grid.
 */
public class Grid extends Pane {

    public int rows;
    public int columns;

    public double width;
    public double height;

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

    public void moveCell(Cell cell, int column, int row){
        updateCellPosition(cell,column, row);
    }
    public void updateCellPosition(Cell cell, int column, int row) {
        double w = width / columns;
        double h = height / rows;
        double newX = w * column;
        double newY = h * row;


            TranslateTransition tt = new TranslateTransition(Duration.seconds(.1), cell);
            tt.setToX(newX);
            tt.setToY(newY);
            tt.play();


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

    // Sets color of that cell
    public void setCellColor(int column, int row, String color) {
        cells[row][column].setStyle("-fx-background-color: " + color + ";");
    }
    public void grass(int column, int row){
        String imagePath = "src/main/resources/images/grass.png";
        File file = new File(imagePath);
        String absolutePath = file.toURI().toString();
        setPrefHeight(10);  // Set the preferred height to 10 pixels
        setPrefWidth(3);
        cells[row][column].setStyle("-fx-background-image: url('" + absolutePath + "');" +
                "-fx-background-size: contain; -fx-background-position: center center;");
    }
    public void water(int column, int row){
        String imagePath = "src/main/resources/images/water.png";
        File file = new File(imagePath);
        String absolutePath = file.toURI().toString();
        setPrefHeight(10);  // Set the preferred height to 10 pixels
        setPrefWidth(3);
        cells[row][column].setStyle("-fx-background-image: url('" + absolutePath + "');" +
                "-fx-background-size: contain; -fx-background-position: center center;");
    }
}
