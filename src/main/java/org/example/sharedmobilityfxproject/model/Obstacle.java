//package org.example.sharedmobilityfxproject.model;
//
///*
//Need to convert Grid and Cell into packages so we can call them in here
// */
//
//
///**
// * Obstacle class represents an obstacle on the grid.
// * It is responsible for managing the state of the cells that form the obstacle.
// */
//public class Obstacle {
//
//    private Grid grid;
//    private int column;
//    private int row;
//
//    /**
//     * Constructs an obstacle located at the specified column and row.
//     *
//     * @param grid   The grid on which the obstacle is placed.
//     * @param column The column index of the obstacle's location on the grid.
//     * @param row    The row index of the obstacle's location on the grid.
//     */
//    public Obstacle(Grid grid, int column, int row) {
//        this.grid = grid;
//        this.column = column;
//        this.row = row;
//        createObstacle();
//    }
//
//    /**
//     * Marks the cell at the obstacle's location as an obstacle by changing its color to red.
//     */
//    private void createObstacle() {
//        Cell obstacleCell = grid.getCell(column, row);
//        obstacleCell.setStyle("-fx-background-color: red;"); // Assuming Cell extends a JavaFX Pane
//    }
//
//    /**
//     * Checks if the specified cell is the obstacle's location and ends the game if so.
//     *
//     * @param cell The cell to check against the obstacle's location.
//     */
//    public void checkCollision(Cell cell) {
//        if (grid.getCell(column, row) == cell) {
//            endGame();
//        }
//    }
//
//    /**
//     * Ends the game by handling the game over condition.
//     */
//    private void endGame() {
//        // Implement your game over logic here.
//        System.out.println("Game Over!");
//    }
//}
