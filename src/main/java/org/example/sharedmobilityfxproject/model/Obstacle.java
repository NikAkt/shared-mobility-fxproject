package org.example.sharedmobilityfxproject.model;

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
        int[] columns;
        int[] rows;
        columns = new int[]{column};
        rows = new int[]{row};
        for (int col : columns) {
            for (int r : rows) {
                if (col >= 0 && col < grid.getColumns() && r >= 0 && r < grid.getRows()) {
                    Cell obstacleCell = grid.getCell(col, r);
                    obstacleCell.getStyleClass().add("cell-obstacle");
                }
            }
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
