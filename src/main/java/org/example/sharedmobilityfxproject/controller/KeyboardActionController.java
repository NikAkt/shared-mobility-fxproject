package org.example.sharedmobilityfxproject.controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.model.*;
import org.example.sharedmobilityfxproject.model.tranportMode.Bus;
import org.example.sharedmobilityfxproject.model.tranportMode.Taxi;
import org.example.sharedmobilityfxproject.view.GameView;

import java.util.ArrayList;
import java.util.List;


public class KeyboardActionController {

    //class call
    public GameController gameController;
    public GameView gameView;
    public Gem gem;
    public Grid grid;
    public Cell currentCell; // Made public for access in start method
    public Cell playerUnosCell; // Made public for access in start method
    public int currentRow;
    public int currentColumn;

    // Newly Added
    public boolean playerMovementEnabled = true;
    public boolean onBus = false;
    public boolean inTaxi = false;
    public Player playerUno;

    public List<Obstacle> obstacles;
    public ArrayList<int[]> busStopCoordinates;
    public Cell finishCell;
    public Taxi taximan;
    public Bus busman;

    // Constructor to initialise grid
    public KeyboardActionController(GameView gameView, Grid grid, List<Obstacle> obstacles, ArrayList<int[]> busStopCoordinates, Cell finishCell, Taxi taximan, Bus busman) {
        this.gameView = gameView;
        this.grid = grid;
        this.obstacles = obstacles;
        this.busStopCoordinates = busStopCoordinates;
        this.finishCell = finishCell;
        this.taximan = taximan;
        // Don't initialize currentCell here
    }

    // Schedule the bus to move every second
    Timeline busMovementTimeline = new Timeline(new KeyFrame(Duration.seconds(.1), event -> {
        busStop targetBusStop = busman.nextStop(); // Assuming this method correctly returns the next bus stop
        if(taximan.hailed&&!taximan.arrived){
            moveTaxiTowardsPlayer(grid, taximan);
        }
        if (!busman.isWaiting){


            moveBusTowardsBusStop(grid, busman, targetBusStop, playerUno);

            // Here's the updated part
            if (onBus) {
                // Update player's coordinates to match the bus when the player is on the bus
                playerUno.setCellByCoords(grid, busman.getX(), busman.getY());
                System.out.println("Player coordinates (on bus): " + playerUno.getCoordX() + ", " + playerUno.getCoordY());
                //Increase carbon footprint amount as long as player is on the bus
                carbonFootprint+=0.2; //subject to change
                gameController.updateCarbonFootprintLabel();
            }}
        else{
            if(busman.waitTime ==0){
                busman.waitASec();
            }
            else{
                busman.waitTime--;
            }
        }
    }));

            busMovementTimeline.setCycleCount(Animation.INDEFINITE);
            busMovementTimeline.play();
            
    public void setupKeyboardActions(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if(this.inTaxi){
                switch (event.getCode()) {
                    case D -> movePlayer(1, 0);
                    case A -> movePlayer(-1, 0);
                    case W -> movePlayer(0, -1);
                    case S -> movePlayer(0, 1);
                    case T -> this.inTaxi =false;
                    case E -> togglePlayerMovement();
                    case C ->
                            System.out.println("The player is located at coordinates: (" + playerUno.getCoordX() + ", " + playerUno.getCoordY() + ")" +
                                    "\nPlayer is currently " + (onBus ? "on the bus." : "not on the bus.") +
                                    "\nPlayer is " + (playerMovementEnabled ? "moving." : "waiting.") +
                                    "\nBus is at coordinates: (" + busman.getX() + "," + busman.getY() + ")");
                }
            } else if (playerMovementEnabled) {
                switch (event.getCode()) {
                    case D -> movePlayer(1, 0);
                    case A -> movePlayer(-1, 0);
                    case W -> movePlayer(0, -1);
                    case S -> movePlayer(0, 1);
//                    case T -> hailTaxi();
                    case E -> togglePlayerMovement();
                }
            } else if (event.getCode() == KeyCode.E) {
                togglePlayerMovement();
            }
        });
    }

    private void moveBus(Grid grid,Bus bus, int newX, int newY) {
        // Move the bus to the new position (newX, newY) on the grid
//        System.out.println("BUS MOVING TO :  "+newX+"  "+newY+". GET OUT THE FUCKING WAY");
//        int x = bus.getX();
//        int y = bus.getY();
        //Cell cell = grid.getCell(x,y);

        grid.moveCell(bus, newX, newY);

        bus.setX(newX);
        bus.setY(newY);
        //grid.add(cell,cell.getColumn(),cell.getRow());

    }
    private boolean canMoveBusTo(int x, int y) {
        // Implement logic to check if the bus can move to (x, y) considering obstacles
        // Return true if it can move, false if there's an obstacle
        return obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == x && obstacle.getRow() == y);
    }
    private void moveTaxi(Grid grid,Taxi bus, int newX, int newY) {
        // Move the bus to the new position (newX, newY) on the grid

        grid.moveCell(bus, newX, newY);

        bus.setX(newX);
        bus.setY(newY);
        //grid.add(cell,cell.getColumn(),cell.getRow());

    }
    
    public void moveTaxiTowardsPlayer(Grid grid, Taxi bus) {

        if (bus.getX()==playerUno.getCoordX()&&bus.getY()==playerUno.getCoordY()&&taximan.arrived&&!inTaxi){
            inTaxi = true;

        }
//        System.out.println(distanceIfMoveX+"   "+distanceIfMoveY);
        else {
            if ((bus.getX() < playerUno.getCoordX() || bus.getX() > playerUno.getCoordX()) && bus.flagMove == 0) {
//            System.out.println("----------- moving x ---------");
                // Move horizontally towards the bus stop, if not blocked
                int newX = bus.getX() + (bus.getX() < playerUno.getCoordX() ? 1 : -1);
                if (canMoveBusTo(newX, bus.getY())) {
                    moveTaxi(grid, bus, newX, bus.getY());
                } else if (canMoveBusTo(bus.getX(), bus.getY() + (bus.getY() < playerUno.getCoordY() ? 1 : -1))) {
                    // Move vertically as a fallback
                    moveTaxi(grid, bus, bus.getX(), bus.getY() + (bus.getY() < playerUno.getCoordY() ? 1 : -1));
                }
            } else if (bus.getY() < playerUno.getCoordY() || bus.getY() > playerUno.getCoordY()) {
//            System.out.println("----------- moving y ---------");
                // Move vertically towards the bus stop, if not blocked
                int newY = bus.getY() + (bus.getY() < playerUno.getCoordY() ? 1 : -1);
                if (canMoveBusTo(bus.getX(), newY)) {

                    moveTaxi(grid, bus, bus.getX(), newY);
                } else if (canMoveBusTo(bus.getX() + 1, bus.getY())) {
                    // Move horizontally as a fallbackf
                    if (bus.flagMove == 0) {
                        bus.flagMove = 1;
                    }
                    moveTaxi(grid, bus, bus.getX() + +1, bus.getY());
                }
            }
            //arriving at stop logic
            else if (bus.getX() == playerUno.getCoordX() && bus.getY() == playerUno.getCoordY()) {
                System.out.println("----------- Taxi arrived ---------");
                bus.arrived = true;

                if (bus.hailed && playerUno.getCoordX() == bus.getX() && playerUno.getCoordY() == bus.getY()) {
                    System.out.println("----------- You just got in the taxia---------");
                    inTaxi = true;

                } else if (inTaxi) {

                    System.out.println("----------- Press E to get off  ---------");
                    inTaxi = true;

                }
            } else if (bus.getY() == playerUno.getCoordY()) {
                bus.flagMove = 0;
            }

        }}

    private void movePlayer(int dx, int dy) {
        int newRow = Math.min(Math.max(playerUno.getCoordY() + dy, 0), grid.getRows() - 1);
        int newColumn = Math.min(Math.max(playerUno.getCoordX() + dx, 0), grid.getColumns() - 1);
        if (canMoveTo(newColumn, newRow)) {
            playerUno.getCell().unhighlight();
            playerUno.setCell(grid.getCell(newColumn, newRow));
            playerUno.getCell().highlight();
            interactWithCell(playerUno.getCell());
        }
    }

    private boolean canMoveTo(int x, int y) {
        return obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == x && obstacle.getRow() == y);
    }

    private void interactWithCell(Cell cell) {
        if ("gem".equals(cell.getUserData())) {
            collectGem(cell);
        } else if (cell instanceof busStop) {
            interactWithBusStop((busStop) cell);
        } else if (cell == finishCell) {
            finishGame();
        }
    }

    private void collectGem(Cell gemCell) {
        grid.getChildren().remove(gemCell);
        grid.add(new Cell(gemCell.getColumn(), gemCell.getRow()), gemCell.getColumn(), gemCell.getRow());
        gameController.playGemCollectSound();
    }

    private void interactWithBusStop(busStop stop) {
        System.out.println("Interacted with Bus Stop at: " + stop.getX() + ", " + stop.getY());
    }

    private void finishGame() {
//        gameFinished = true;
        Label levelCompleteLabel = new Label("Level Complete");
        levelCompleteLabel.setStyle("-fx-font-size: 24px;");
        StackPane root = (StackPane) grid.getScene().getRoot();
        root.getChildren().add(levelCompleteLabel);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> ((Stage) grid.getScene().getWindow()).close());
        pause.play();
    }

//    private void hailTaxi() {
//        hailTaxi = !hailTaxi;
//        currentCell.setStyle(hailTaxi ? "-fx-background-color: yellow;" : "-fx-background-color: blue;");
//        if (hailTaxi) {
//            carbonFootprint += 75;
//            updateCarbonFootprintLabel();
//        }
//    }

    private void togglePlayerMovement() {
        if (this.onBus) {
            int[] playerLocation = {playerUno.getCoordX(), playerUno.getCoordY()};
            boolean atBusStop = busStopCoordinates.stream()
                    .anyMatch(location -> location[0] == playerLocation[0] && location[1] == playerLocation[1]);
            if (atBusStop) {
                playerMovementEnabled = true;
                this.onBus = false;
                System.out.println("You got off the bus.");
            } else {
                System.out.println("You can only get off the bus at a bus stop.");
            }
        } else if (playerUno.getCell() instanceof busStop) {
            playerMovementEnabled = !playerMovementEnabled;
            System.out.println(playerMovementEnabled ? "Impatient" : "Waiting for bus");
            ((busStop) playerUno.getCell()).setPlayerHere(!playerMovementEnabled);
        }
    }
}