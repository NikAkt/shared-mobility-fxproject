package org.example.sharedmobilityfxproject.controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
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
    public Cell currentCell; // Made public for access in start method
    public Cell playerUnosCell; // Made public for access in start method
    public int currentRow;
    public int currentColumn;

    // Newly Added
    public boolean playerMovementEnabled = true;
    public boolean onBus = false;
    public boolean inTaxi = false;
    public Player playerUno;

    public List<Obstacle> obstacles = new ArrayList<>();
    public ArrayList<int[]> obstacleCoordinates;
    public boolean hailTaxi = false;
    public Cell finishCell;
    public Taxi taximan;
    public Bus busman;
    public ArrayList<busStop> busStops = new ArrayList<>();
    public ArrayList<int[]> busStopCoordinates = new ArrayList<>();

    @FunctionalInterface
    public interface GemCollector {
        void collectGem();
    }

    // Constructor to initialise grid
    public KeyboardActionController(GameView gameView, Player playerUno) {
        this.gameView = gameView;
        this.playerUno = playerUno;


        // Fill the grid with cells
        for (int row = 0; row < gameView.getRows(); row++) {
            for (int column = 0; column < gameView.getColumns(); column++) {
                Cell cell = new Cell(column, row);
                gameView.grid.add(cell, column, row);
            }
        }

        // Create label for carbon footprint
//            carbonFootprintLabel = new Label("Carbon Footprint: " + String.format("%.1f", carbonFootprint));
//            carbonFootprintLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
//            carbonFootprintLabel.setAlignment(Pos.TOP_LEFT);
//            carbonFootprintLabel.setPadding(new Insets(10));

        // Create a VBox to hold the gem count label
//            VBox vbox = new VBox(gemCountLabel, carbonFootprintLabel);
//            vbox.setAlignment(Pos.TOP_LEFT);


        // Initialise Obstacles for x = 0
        // Initialise Obstacles
        // Initialise Obstacles
//            obstacles = new ArrayList<>();

        // Define the size of the obstacle blocks and the gap between them
        int obstacleWidth = 5;
        int obstacleHeight = 3;
        int gap = 5;


        // Calculate the number of obstacle blocks in each direction
        int numBlocksX = (gameView.getColumns() - 2 * gap) / (obstacleWidth + gap);
        int numBlocksY = (gameView.getRows() - 2 * gap) / (obstacleHeight + gap);


        // For each block position
        for (int bx = 0; bx < numBlocksX; bx++) {
            for (int by = 0; by < numBlocksY; by++) {
                // Calculate the top-left corner of the block
                int x = gap + bx * (obstacleWidth + gap);
                int y = gap + by * (obstacleHeight + gap);

                // Create the obstacle block
                for (int i = 0; i < obstacleWidth; i++) {
                    for (int j = 0; j < obstacleHeight; j++) {
                        obstacles.add(new Obstacle(gameView.grid, x + i, y + j));
                        List<Integer> coordinatePair = new ArrayList<>();

                        coordinatePair.add(x + i);
                        coordinatePair.add(y + j);
                    }
                }
            }
        }
        obstacleCoordinates = new ArrayList<>();

        for (Obstacle obstacle : obstacles) {
            obstacleCoordinates.add(new int[]{obstacle.getColumn(), obstacle.getRow()});
        }
        System.out.println("Obstacle Coordinates: ");

        generateGems(gameView.grid, 5); // Replace 5 with the number of gems you want to generate

        // Place the finish cell after the grid is filled and the player's position is initialised
        int finishColumn;
        int finishRow;
        finishColumn=102;
        finishRow=58;

        finishCell = new Cell(finishColumn, finishRow);
        finishCell.getStyleClass().add("finish");
        gameView.grid.add(finishCell, finishColumn, finishRow);

        //bus SHITE
        busStop busS1 = new busStop(4,4);
        busStop busS2 = new busStop(110,4);
        busStop busS3 = new busStop(57,25);
        busStop busS4 = new busStop(110,64);
        busStop busS5 = new busStop(57,64);
        busStop busS6 = new busStop(4,64);
        busStop busS7 = new busStop(4,34);


        this.busStopCoordinates.add(new int[]{busS1.getX(), busS1.getY()});
        this.busStopCoordinates.add(new int[]{busS2.getX(), busS2.getY()});
        this.busStopCoordinates.add(new int[]{busS3.getX(), busS3.getY()});
        this.busStopCoordinates.add(new int[]{busS4.getX(), busS4.getY()});
        this.busStopCoordinates.add(new int[]{busS5.getX(), busS5.getY()});
        this.busStopCoordinates.add(new int[]{busS6.getX(), busS6.getY()});
        this.busStopCoordinates.add(new int[]{busS7.getX(), busS7.getY()});


        busStops.add(busS1);
        busStops.add(busS2);
        busStops.add(busS3);
        busStops.add(busS4);
        busStops.add(busS5);
        busStops.add(busS6);
        busStops.add(busS7);



        this.busman = new Bus(busStops,4, 4);
        this.taximan = new Taxi (58,28);
        for (int i = 0; i < this.busman.list().size(); i++){
            busStop stop = this.busman.list().get(i);
            gameView.grid.add(stop,stop.getX(), stop.getY());
        }

        gameView.grid.add(this.busman,this.busman.getX(), this.busman.getY());// Example starting position
        gameView.grid.add(taximan, taximan.getX(), taximan.getY());

        // Don't initialize currentCell here

        // Schedule the bus to move every second
        Timeline busMovementTimeline = new Timeline(new KeyFrame(Duration.seconds(.1), event -> {
            busStop targetBusStop = this.busman.nextStop(); // Assuming this method correctly returns the next bus stop
            if(taximan.hailed&&!taximan.arrived){
                moveTaxiTowardsPlayer(taximan);
            }
            if (!this.busman.isWaiting){


                moveBusTowardsBusStop(this.busman, targetBusStop);

                // Here's the updated part
                if (this.onBus) {
                    // Update player's coordinates to match the bus when the player is on the bus
                    this.playerUno.setCellByCoords(gameView.grid, this.busman.getX(), this.busman.getY());
                    System.out.println("Player coordinates (on bus): " + this.playerUno.getCoordX() + ", " + this.playerUno.getCoordY());
                    //Increase carbon footprint amount as long as player is on the bus
                    double carbonFootprint = 0.2; //subject to change
//                    gameController.updateCarbonFootprintLabel();
                }}
            else{
                if(this.busman.waitTime ==0){
                    this.busman.waitASec();
                }
                else{
                    this.busman.waitTime--;
                }
            }
        }));

        busMovementTimeline.setCycleCount(Animation.INDEFINITE);
        busMovementTimeline.play();

        // give playerUno a cell goddamit
        this.playerUno.initCell(gameView.grid);
    }

    private void generateGems(Grid grid, int numberOfGems) {
        for (int i = 0; i < numberOfGems; i++) {
            int gemColumn;
            int gemRow;
            do {
                gemColumn = (int) (Math.random() * gameView.getColumns());
                gemRow = (int) (Math.random() * gameView.getRows());
            } while ((gemColumn == 0 && gemRow == 0) || grid.getCell(gemColumn, gemRow).getUserData() != null); // Ensure gem doesn't spawn at player's starting position or on another gem


            Gem gem = new Gem(gemColumn, gemRow);
            grid.add(gem, gemColumn, gemRow);
        }
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
                } else if (canMoveBusTo(bus.getX(), bus.getY() + (bus.getY() <playerUno.getCoordY() ? 1 : -1))) {
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

    public void moveBusTowardsBusStop(Bus bus, busStop stop) {
        // Calculate the Manhattan distance for both possible next steps
        int distanceIfMoveX = Math.abs((bus.getX()) - stop.getX()) ;
        int distanceIfMoveY = Math.abs(bus.getY() - stop.getY());
//        System.out.println("--------------------");
//        System.out.println(stop.getX()+"   "+ stop.getY());

//        System.out.println(distanceIfMoveX+"   "+distanceIfMoveY);
        if ((bus.getX()<stop.getX()||bus.getX()>stop.getX())&&bus.flagMove==0 ) {
//            System.out.println("----------- moving x ---------");
            // Move horizontally towards the bus stop, if not blocked
            int newX = bus.getX() + (bus.getX() < stop.getX() ? 1 : -1);
            if (canMoveBusTo(newX, bus.getY())) {
                moveBus(bus, newX, bus.getY());
            } else if (canMoveBusTo(bus.getX(), bus.getY() + (bus.getY() < stop.getY() ? 1 : -1))) {
                // Move vertically as a fallback
                moveBus(bus, bus.getX(), bus.getY() + (bus.getY() < stop.getY() ? 1 : -1));
            }
        }

        else if (bus.getY()<stop.getY()||bus.getY()>stop.getY()){
//            System.out.println("----------- moving y ---------");
            // Move vertically towards the bus stop, if not blocked
            int newY = bus.getY() + (bus.getY() < stop.getY() ? 1 : -1);
            if (canMoveBusTo(bus.getX(), newY)) {

                moveBus(bus, bus.getX(), newY);
            }
            else if (canMoveBusTo(bus.getX() +1, bus.getY())) {
                // Move horizontally as a fallback
                if (bus.flagMove == 0){
                    bus.flagMove =1;
                }
                moveBus(bus, bus.getX() + +1, bus.getY());
            }
        }
        //arriving at stop logic
        else if (bus.getX()==stop.getX()&&bus.getY()==stop.getY()){
            System.out.println("----------- ARRIVED..... GET THE FUCK OUT ---------");
            bus.waitASec();
            bus.list().add(bus.list().remove(0));
            System.out.println("now going towards :"+bus.nextStop());
            if(!playerMovementEnabled&&playerUno.getCoordX()==bus.getX()&&playerUno.getCoordY()==bus.getY()){
                System.out.println("----------- You just got on the bus ---------");
                this.onBus = true;

            }else if(this.onBus){
                System.out.println("----------- You arrived at  ---------"+stop);
                System.out.println("----------- Press E to get off  ---------");
                this.onBus = true;

            }
        }
        else if (bus.getY()==stop.getY()) {
            bus.flagMove=0;
        }

    }

    public void setupKeyboardActions(KeyCode key) {
        if(this.inTaxi){
            switch (key) {
                case D -> movePlayer(2, 0);
                case A -> movePlayer(-2, 0);
                case W -> movePlayer(0, -2);
                case S -> movePlayer(0, 2);
                case T -> this.inTaxi =false;
                case E -> togglePlayerMovement();
                case C ->
                        System.out.println("The player is located at coordinates: (" + playerUno.getCoordX() + ", " + playerUno.getCoordY() + ")" +
                                "\nPlayer is currently " + (onBus ? "on the bus." : "not on the bus.") +
                                "\nPlayer is " + (playerMovementEnabled ? "moving." : "waiting.") +
                                "\nBus is at coordinates: (" + busman.getX() + "," + busman.getY() + ")");
            }}else if (playerMovementEnabled) {
                switch (key) {
                    case D -> movePlayer(1, 0);
                    case A -> movePlayer(-1, 0);
                    case W -> movePlayer(0, -1);
                    case S -> movePlayer(0, 1);
                  case T -> hailTaxi();
                    case E -> togglePlayerMovement();
                }
            } else if (key == KeyCode.E) {
                togglePlayerMovement();
            }
    }
    private void moveTaxi(Grid grid,Taxi bus, int newX, int newY) {
        // Move the bus to the new position (newX, newY) on the grid

        grid.moveCell(bus, newX, newY);

        bus.setX(newX);
        bus.setY(newY);
        //grid.add(cell,cell.getColumn(),cell.getRow());

    }

    private void moveBus(Bus bus, int newX, int newY) {
        // Move the bus to the new position (newX, newY) on the grid
//        System.out.println("BUS MOVING TO :  "+newX+"  "+newY+". GET OUT THE FUCKING WAY");
//        int x = bus.getX();
//        int y = bus.getY();
        //Cell cell = grid.getCell(x,y);

        gameView.grid.moveCell(bus, newX, newY);

        bus.setX(newX);
        bus.setY(newY);
        //grid.add(cell,cell.getColumn(),cell.getRow());

    }
    private boolean canMoveBusTo(int x, int y) {
        // Implement logic to check if the bus can move to (x, y) considering obstacles
        // Return true if it can move, false if there's an obstacle
        return obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == x && obstacle.getRow() == y);
    }

    private void movePlayer(int dx, int dy) {
        System.out.println(playerUno);
        int newRow = Math.min(Math.max(playerUno.getCoordY() + dy, 0), gameView.grid.getRows() - 1);
        int newColumn = Math.min(Math.max(playerUno.getCoordX() + dx, 0), gameView.grid.getColumns() - 1);
        if (canMoveTo(newColumn, newRow)) {
            playerUno.getCell().unhighlight();
            playerUno.setCell(gameView.grid.getCell(newColumn, newRow));
            playerUno.getCell().highlight();
            interactWithCell(playerUno.getCell());

        }
        if (inTaxi) {
            // Assuming taximan is accessible from here, or find a way to access it
            moveTaxi(gameView.grid, taximan, newColumn, newRow);

        }
    }

    private boolean canMoveTo(int x, int y) {
        return this.obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == x && obstacle.getRow() == y);
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
        gameView.grid.getChildren().remove(gemCell);
        gameView.grid.add(new Cell(gemCell.getColumn(), gemCell.getRow()), gemCell.getColumn(), gemCell.getRow());
//        gameController.playGemCollectSound();
    }

    private void interactWithBusStop(busStop stop) {
        System.out.println("Interacted with Bus Stop at: " + stop.getX() + ", " + stop.getY());
    }

    private void finishGame() {
//        gameFinished = true;
        Label levelCompleteLabel = new Label("Level Complete");
        levelCompleteLabel.setStyle("-fx-font-size: 24px;");
        StackPane root = (StackPane) gameView.grid.getScene().getRoot();
        root.getChildren().add(levelCompleteLabel);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> ((Stage) gameView.grid.getScene().getWindow()).close());
        pause.play();
    }

    private void hailTaxi() {
        if (taximan.hailed) {
            taximan.hailed = !taximan.hailed;
        }
        else{
            taximan.hailed = true;
        }
    }
    private void togglePlayerMovement() {
        if (onBus) {
            int[] playerLocation = {playerUno.getCoordX(), playerUno.getCoordY()};
            boolean atBusStop = busStopCoordinates.stream()
                    .anyMatch(location -> location[0] == playerLocation[0] && location[1] == playerLocation[1]);
            if (atBusStop) {
                playerMovementEnabled = true;
                onBus = false;
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

    private void moveTaxi(Taxi bus, int newX, int newY) {
        // Move the bus to the new position (newX, newY) on the grid

        gameView.grid.moveCell(bus, newX, newY);

        bus.setX(newX);
        bus.setY(newY);
        //grid.add(cell,cell.getColumn(),cell.getRow());

    }

    public void moveTaxiTowardsPlayer(Taxi bus) {

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
                    moveTaxi(bus, newX, bus.getY());
                } else if (canMoveBusTo(bus.getX(), bus.getY() + (bus.getY() < playerUno.getCoordY() ? 1 : -1))) {
                    // Move vertically as a fallback
                    moveTaxi(bus, bus.getX(), bus.getY() + (bus.getY() < playerUno.getCoordY() ? 1 : -1));
                }
            } else if (bus.getY() < playerUno.getCoordY() || bus.getY() > playerUno.getCoordY()) {
//            System.out.println("----------- moving y ---------");
                // Move vertically towards the bus stop, if not blocked
                int newY = bus.getY() + (bus.getY() < playerUno.getCoordY() ? 1 : -1);
                if (canMoveBusTo(bus.getX(), newY)) {

                    moveTaxi(bus, bus.getX(), newY);
                } else if (canMoveBusTo(bus.getX() + 1, bus.getY())) {
                    // Move horizontally as a fallbackf
                    if (bus.flagMove == 0) {
                        bus.flagMove = 1;
                    }
                    moveTaxi(bus, bus.getX() + +1, bus.getY());
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
}