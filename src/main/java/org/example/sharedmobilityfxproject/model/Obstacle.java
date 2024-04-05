package org.example.sharedmobilityfxproject.model;


/**
 * Obstacle class represents an obstacle on the grid.
 * It is responsible for managing the state of the cells that form the obstacle.
 */
public class Obstacle {
    public Cell cell;
    public Grid grid;
    public int column;
    public int row;
    public Cell obstacleCell;

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
        createObstacle(column,row,grid);
    }

    /**
     * Marks the cell at the obstacle's location as an obstacle by changing its color to red.
     */
    public void createObstacle(int column, int row, Grid grid) {
        if (grid != null) {
            Cell obstacleCell = grid.getCell(column, row);
            if (obstacleCell != null) {
                obstacleCell.getStyleClass().add("cell-obstacle"); // Assuming Cell extends a JavaFX Pane
            } else {
                System.err.println("Cell at column " + column + ", row " + row + " is null.");
            }
        } else {
            System.err.println("Grid is null.");
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
