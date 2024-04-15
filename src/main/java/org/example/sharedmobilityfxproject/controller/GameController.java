package org.example.sharedmobilityfxproject.controller;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.model.*;
import org.example.sharedmobilityfxproject.model.tranportMode.Bicycle;
import org.example.sharedmobilityfxproject.model.tranportMode.Bus;
import org.example.sharedmobilityfxproject.model.tranportMode.Taxi;
import org.example.sharedmobilityfxproject.view.GameView;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import java.util.ArrayList;
import java.util.List;



public class GameController{
    private boolean playerTimeout = true;
    private ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    private SceneController sceneController;
    //class call
    public MainController mainController;
    private GameView gameView;
    public Gem gem;
    public Cell currentCell; // Made public for access in start method
    public Cell playerUnosCell; // Made public for access in start method
    public int currentRow;
    public int currentColumn;

    //GameStart Flag
    private boolean isGameStarted = false;
    // Newly Added
    public boolean playerMovementEnabled = true;
    public boolean onBus = false;
    public boolean inTaxi = false;
    public boolean onBicycle = false;
    public Player playerUno;


    //Stamina related
    private int lastX, lastY;  // Last coordinates of the player
    private int stationaryTime = 0;  // Time to stay

    public List<Obstacle> obstacles = new ArrayList<>();
    public ArrayList<int[]> obstacleCoordinates;

    public Cell finishCell;
    public Taxi taximan;
    public Bus busman;
    public Bicycle cycleman;
    public ArrayList<busStop> busStops = new ArrayList<>();
    public ArrayList<int[]> busStopCoordinates = new ArrayList<>();
    private Timer timer = new Timer();  // Create a Timer object
    private ScrollPane scrollPane;
    private GameOverListener gameOverListener;

    private void enableMovementAfterDelay() {
        playerTimeout = false;  // Disable further moves immediately when this method is called
        int delayInMilliseconds = (int) (playerUno.speedTime * 1000);  // Convert seconds to milliseconds
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // This will run in a background thread, make sure to run UI updates on the JavaFX thread
                javafx.application.Platform.runLater(() -> {
                    playerTimeout = true;  // Re-enable moves after the specified time
                });
            }
        }, delayInMilliseconds);
    }

    //Player Movement Check for Stamina
    private int moveCounter = 0;
    @FunctionalInterface
    public interface GemCollector {
        void collectGem();
    }

    // Constructor to initialise grid
    public GameController(SceneController sceneController, GameView gameView, Player playerUno) {
        this.sceneController = sceneController;
        this.gameView = gameView;
        this.playerUno = playerUno;
        lastX = playerUno.getCoordX();
        lastY = playerUno.getCoordY();

        //Counting staying time on the spot
        Timeline checkStationary = new Timeline(new KeyFrame(Duration.seconds(1), e -> checkAndIncreaseStamina()));
        checkStationary.setCycleCount(Timeline.INDEFINITE);
        checkStationary.play();

    }
    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }
    public void startPlayingGame(String stageName) {
        this.sceneController.initGameScene(stageName);
        this.isGameStarted = true;
        System.out.println("GameController startPlayingGame");

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

//        // Sample map array
//        int[][] mapArray = {
//                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
//                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}
//        };

        // Start filling the grid with obstacles and other stuff
        Map map = new Map();
        fillGridWithMapArray(map);





        obstacleCoordinates = new ArrayList<>();

        for (Obstacle obstacle : obstacles) {
            obstacleCoordinates.add(new int[]{obstacle.getColumn(), obstacle.getRow()});
        }
        System.out.println("Obstacle Coordinates: ");

        generateGems(gameView.grid, 5); // Replace 5 with the number of gems you want to generate

        // Place the finish cell after the grid is filled and the player's position is initialised
        int finishColumn;
        int finishRow;
        finishColumn = 102;
        finishRow = 58;

        finishCell = new Cell(finishColumn, finishRow);
        finishCell.getStyleClass().add("finish");
        gameView.grid.add(finishCell, finishColumn, finishRow);

//        //bus SHITE
//        busStop busS1 = new busStop(4,4);
//        busStop busS2 = new busStop(110,4);
//        busStop busS3 = new busStop(57,25);
//        busStop busS4 = new busStop(110,64);
//        busStop busS5 = new busStop(57,64);
//        busStop busS6 = new busStop(4,64);
//        busStop busS7 = new busStop(4,34);

        metroStop metro1 = new metroStop(2,30);
        gameView.grid.add(metro1,2,30);

//        busStopCoordinates.add(new int[]{busS1.getX(), busS1.getY()});
//        busStopCoordinates.add(new int[]{busS2.getX(), busS2.getY()});
//        busStopCoordinates.add(new int[]{busS3.getX(), busS3.getY()});
//        busStopCoordinates.add(new int[]{busS4.getX(), busS4.getY()});
//        busStopCoordinates.add(new int[]{busS5.getX(), busS5.getY()});
//        busStopCoordinates.add(new int[]{busS6.getX(), busS6.getY()});
//        busStopCoordinates.add(new int[]{busS7.getX(), busS7.getY()});
//
//
//        busStops.add(busS1);
//        busStops.add(busS2);
//        busStops.add(busS3);
//        busStops.add(busS4);
//        busStops.add(busS5);
//        busStops.add(busS6);
//        busStops.add(busS7);



        busman = new Bus(busStops,3, 4);

        taximan = new Taxi (58,28);
        cycleman= new Bicycle(10,4);
        for (int i = 0; i < busman.list().size(); i++){
            busStop stop = busman.list().get(i);
            gameView.grid.add(stop, stop.getX(), stop.getY());
        }

        gameView.grid.add(busman, busman.getX(), busman.getY());// Example starting position
        gameView.grid.add(taximan, taximan.getX(), taximan.getY());
        gameView.grid.add(cycleman, cycleman.getX(), cycleman.getY());

        // Schedule the bus to move every second
        Timeline busMovementTimeline = new Timeline(new KeyFrame(Duration.seconds(.1), event -> {
            busStop targetBusStop = busman.nextStop(); // Assuming this method correctly returns the next bus stop
            if (taximan.hailed && !taximan.arrived) {
                moveTaxiTowardsPlayer(gameView.grid, taximan);
            }
            if (onBicycle) {
                if (cycleman.bikeTime == 0) {
                    onBicycle = false;
                }
                if (cycleman.bikeTime >= 1) {
                    cycleman.bikeTime -= 1;
                }
            }
            if (!busman.isWaiting) {


                moveBusTowardsBusStop(busman, targetBusStop);

                // Here's the updated part
                if (onBus) {
                    // Update player's coordinates to match the bus when the player is on the bus
                    playerUno.setCellByCoords(gameView.grid, busman.getX(), busman.getY());
                    System.out.println("Player coordinates (on bus): " + playerUno.getCoordX() + ", " + playerUno.getCoordY());
                    //Increase carbon footprint amount as long as player is on the bus
                    double carbonFootprint = 0.2; //subject to change
//                    gameController.updateCarbonFootprintLabel();
                }
            } else {
                if (busman.waitTime == 0) {
                    busman.waitASec();
                } else {
                    busman.waitTime--;
                }
            }
        }));

        busMovementTimeline.setCycleCount(Animation.INDEFINITE);
        busMovementTimeline.play();


        // give playerUno a cell goddamit
        playerUno.initCell(gameView.grid);

        gameView.getScene().setOnKeyPressed(e -> setupKeyboardActions(e.getCode()));

    }

    /**
     * This method fills the grid based on the contents of a map array from a Map object.
     * It initializes a 2D array with dimensions 80x120 and attempts to retrieve the map array.
     * If an exception occurs, it prints the stack trace.
     * Each cell in the array is processed based on its value:
     * - 1: Creates and adds an Obstacle to the list.
     * - 2: Colors the cell green.
     * - 3: Colors the cell blue.
     * - 4: Marks the cell as a bus stop.
     * - 9: Also creates and adds an Obstacle, similar to 1.
     * The method handles each cell by its specified action, enhancing the game's visual and functional complexity.
     */
    public void fillGridWithMapArray(Map map) {
        int[][] mapArray = new int[80][120];  // Default map size initialization
        try {
            mapArray = map.getMapArray();  // Attempt to retrieve the map array
        } catch (Exception e) {
            e.printStackTrace();  // Print any errors encountered
        }

        for (int row = 0; row < mapArray.length; row++) {
            for (int column = 0; column < mapArray[row].length; column++) {
                switch (mapArray[row][column]) {
                    case 1:  // Cell indicates an obstacle
                    case 9:  // Cell also indicates an obstacle (same behavior as 1)
                        Obstacle obstacle = new Obstacle(gameView.grid, column, row);
                        obstacles.add(obstacle);
                        break;
                    case 2:  // Color the cell green
                        gameView.grid.setCellColor(column, row, "GREEN");
                        break;
                    case 3:  // Color the cell blue
                        gameView.grid.setCellColor(column, row, "BLUE");
                        break;
                    case 4:  // Mark as bus stop
                        busStop busS = new busStop(column,row);
                        busStopCoordinates.add(new int[]{busS.getX(), busS.getY()});
                        busStops.add(busS);
                        break;
                    default:
                        // Optionally handle default case if needed
                        break;
                }
            }
        }
    }


    private void generateGems(Grid grid, int numberOfGems) {
        for (int i = 0; i < numberOfGems; i++) {
            int gemColumn;
            int gemRow;
            boolean isObstacle;
            do {
                gemColumn = (int) (Math.random() * gameView.getColumns());
                gemRow = (int) (Math.random(

                ) * gameView.getRows());
                int finalGemColumn = gemColumn;
                int finalGemRow = gemRow;
                //It is called before gemGeneration
                // obstacleCoordinates is the list of obstacles's coordinates
                isObstacle = obstacleCoordinates.stream().anyMatch(coords -> coords[0] == finalGemColumn && coords[1] == finalGemRow);
                //Check
            } while (isObstacle || (gemColumn == 0 && gemRow == 0) || grid.getCell(gemColumn, gemRow).getUserData() != null); // Ensure gem doesn't spawn at player's starting position or on another gem


            Gem gem = new Gem(gemColumn, gemRow);
            grid.add(gem, gemColumn, gemRow);
        }
    }

    public void moveTaxiTowardsPlayer(Grid grid, Taxi bus) {
        System.out.println("moving taxi");
        if (bus.getX() == playerUno.getCoordX() && bus.getY() == playerUno.getCoordY() && taximan.arrived && !inTaxi) {
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

        }
    }

    public void moveBusTowardsBusStop(Bus bus, busStop stop) {
        // Calculate the Manhattan distance for both possible next steps
        int distanceIfMoveX = Math.abs((bus.getX()) - stop.getX());
        int distanceIfMoveY = Math.abs(bus.getY() - stop.getY());
//        System.out.println("--------------------");
//        System.out.println(stop.getX()+"   "+ stop.getY());

//        System.out.println(distanceIfMoveX+"   "+distanceIfMoveY);
        if ((bus.getX() < stop.getX() || bus.getX() > stop.getX()) && bus.flagMove == 0) {
//            System.out.println("----------- moving x ---------");
            // Move horizontally towards the bus stop, if not blocked
            int newX = bus.getX() + (bus.getX() < stop.getX() ? 1 : -1);
            if (canMoveBusTo(newX, bus.getY())) {
                moveBus(bus, newX, bus.getY());
            } else if (canMoveBusTo(bus.getX(), bus.getY() + (bus.getY() < stop.getY() ? 1 : -1))) {
                // Move vertically as a fallback
                moveBus(bus, bus.getX(), bus.getY() + (bus.getY() < stop.getY() ? 1 : -1));
            }
        } else if (bus.getY() < stop.getY() || bus.getY() > stop.getY()) {
//            System.out.println("----------- moving y ---------");
            // Move vertically towards the bus stop, if not blocked
            int newY = bus.getY() + (bus.getY() < stop.getY() ? 1 : -1);
            if (canMoveBusTo(bus.getX(), newY)) {

                moveBus(bus, bus.getX(), newY);
            } else if (canMoveBusTo(bus.getX() + 1, bus.getY())) {
                // Move horizontally as a fallback
                if (bus.flagMove == 0) {
                    bus.flagMove = 1;
                }
                moveBus(bus, bus.getX() + +1, bus.getY());
            }
        }
        //arriving at stop logic
        else if (bus.getX() == stop.getX() && bus.getY() == stop.getY()) {
            System.out.println("----------- ARRIVED..... GET THE FUCK OUT ---------");
            bus.waitASec();
            bus.list().add(bus.list().remove(0));
            System.out.println("now going towards :" + bus.nextStop());
            if (!playerMovementEnabled && playerUno.getCoordX() == bus.getX() && playerUno.getCoordY() == bus.getY()) {
                System.out.println("----------- You just got on the bus ---------");
                this.onBus = true;

            } else if (this.onBus) {
                System.out.println("----------- You arrived at  ---------" + stop);
                System.out.println("----------- Press E to get off  ---------");
                this.onBus = true;

            }
        } else if (bus.getY() == stop.getY()) {
            bus.flagMove = 0;
        }

    }

    public void setupKeyboardActions(KeyCode key) {
        if (this.inTaxi) {
            switch (key) {
                case D -> movePlayer(2, 0);
                case A -> movePlayer(-2, 0);
                case W -> movePlayer(0, -2);
                case S -> movePlayer(0, 2);
                case T -> this.inTaxi = false;
                case E -> togglePlayerMovement();
                case C ->
                        System.out.println("The player is located at coordinates: (" + playerUno.getCoordX() + ", " + playerUno.getCoordY() + ")" +
                                "\nPlayer is currently " + (onBus ? "on the bus." : "not on the bus.") +
                                "\nPlayer is " + (playerMovementEnabled ? "moving." : "waiting.") +
                                "\nBus is at coordinates: (" + busman.getX() + "," + busman.getY() + ")");
            }
        } else if (onBicycle && playerMovementEnabled && playerTimeout) {
            System.out.println("Bicycle time: " + cycleman.bikeTime + " you are still on bike");
            switch (key) {
                case D -> movePlayer(5, 0);
                case A -> movePlayer(-5, 0);
                case W -> movePlayer(0, -5);
                case S -> movePlayer(0, 5);
                case C ->
                        System.out.println("The player is located at coordinates: (" + playerUno.getCoordX() + ", " + playerUno.getCoordY() + ")" +
                                "\nPlayer is currently " + (onBicycle ? "on bicycle." : "not on the bicycle.") +
                                "\nPlayer is " + (playerMovementEnabled ? "moving." : "waiting.") +
                                "\nBicycle is at coordinates: (" + cycleman.getX() + "," + cycleman.getY() + ")");
            }
            enableMovementAfterDelay();
        } else if (playerMovementEnabled && playerTimeout) {
            switch (key) {
                case D -> movePlayer(1, 0);
                case A -> movePlayer(-1, 0);
                case W -> movePlayer(0, -1);
                case S -> movePlayer(0, 1);
                case T -> hailTaxi();
                case E -> togglePlayerMovement();
            }
            enableMovementAfterDelay();
        } else if (key == KeyCode.E) {
            togglePlayerMovement();
        } else if (key == KeyCode.E) {
            togglePlayerMovement();
        }
    }

    private void moveTaxi(Grid grid, Taxi bus, int newX, int newY) {
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

    /**
     * Moves the player in the game based on the provided direction.
     * This method checks if the player has enough stamina to move. If not, it plays a sound and returns.
     * If the player has enough stamina, it sets the player's status to walking and calculates the new position.
     * If the player is in the metro scene, it updates the player's position.
     * If the player is at a metro stop, it switches the scene to the metro scene and updates the player's position.
     * If the player can move to the new position, it updates the player's position and interacts with the cell.
     * If the player is in a taxi and not walking, it moves the taxi to the new position.
     * If the player is walking, it increments the move counter and decreases the player's stamina every 5 moves.
     *
     * @param dx The change in the x-coordinate (horizontal direction) of the player's position.
     * @param dy The change in the y-coordinate (vertical direction) of the player's position.
     */
    private void movePlayer(int dx, int dy) {


        if (playerUno.getStamina() <= 0) {
            System.out.println("Not enough stamina to move.");
            gameView.playNoStaminaSound();
            return;
        }

        playerUno.setIsWalking(true);
        int newRow = Math.min(Math.max(playerUno.getCoordY() + dy, 0), gameView.grid.getRows() - 1);
        int newColumn = Math.min(Math.max(playerUno.getCoordX() + dx, 0), gameView.grid.getColumns() - 1);
        Cell newCell = gameView.grid.getCell(newColumn, newRow);

        if (gameView.isMetroSceneActive) {
            playerUno.getCell().unhighlight();

            playerUno.getCell().highlight();
            System.out.println("player pos: " + playerUno.getCoordX() + " " + playerUno.getCoordY());
        }

        if (playerUno.getCell() instanceof metroStop) {
            gameView.isMetroSceneActive = !gameView.isMetroSceneActive;
            gameView.switchSceneToMetro();// Metro scene is now active
            Stage primaryStage = (Stage) gameView.grid.getScene().getWindow();
            playerUno.isUnderground = true;
            System.out.println(gameView.grid);


            playerUno.setCellByCoords(gameView.grid, newColumn, newRow);
            System.out.println("Player has entered a metro entrance" + gameView.grid);
        }
        if (canMoveTo(newColumn, newRow)) {
            playerUno.getCell().unhighlight();
            playerUno.setX(newColumn);
            playerUno.setY(newRow);
//            gameView.grid.updateCellPosition(playerUno.getCell(),playerUno.getCoordX(),playerUno.getCoordY());
            playerUno.setCell(gameView.grid.getCell(newColumn, newRow), gameView.grid);
            playerUno.getCell().highlight();
            interactWithCell(playerUno.getCell());
            if (inTaxi && !(playerUno.isWalking)) {
                // Assuming taximan is accessible from here, or find a way to access it
                moveTaxi(gameView.grid, taximan, newColumn, newRow);
            }
            //MoveCounter for walking and decrease stamina every 5 moves
            if (playerUno.getIsWalking()) {
                moveCounter++;
                System.out.println("Move Counter: " + moveCounter);
                //Decrease stamina every 5 moves
                if (moveCounter >= 5) {
                    playerUno.decreaseStamina();
                    gameView.updateStamina(playerUno.getStamina());
                    moveCounter = 0;
                }
            }
        }
        interactWithCell(gameView.grid.getCell(newColumn, newRow));
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
            System.out.println("Interacting with gem ");
            collectGem(cell);
        } else if (cell instanceof busStop) {
            interactWithBusStop((busStop) cell);
        } else if (cell == finishCell) {
            finishGame();
        } else if (cell instanceof Bicycle) {
            System.out.println("You just got on the bike");
            onBicycle = true;
            cycleman.bikeTime=300;
            System.out.println(onBicycle
            );
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
    /**
     * Checks and increases the player's stamina if the player is stationary.
     * This method checks if the player's current coordinates are the same as the last recorded coordinates.
     * If they are the same, it increments the stationary time.
     * If the stationary time is greater than or equal to 1, it increases the player's stamina, updates the stamina in the game view, and resets the stationary time.
     * If the player's current coordinates are not the same as the last recorded coordinates, it resets the stationary time and updates the last recorded coordinates to the current coordinates.
     */
    private void checkAndIncreaseStamina() {
        if (isGameStarted && playerUno.getStamina() < 100) {
            if (playerUno.getCoordX() == lastX && playerUno.getCoordY() == lastY) {
                stationaryTime += 1;
                if (stationaryTime >= 1) {
                    playerUno.increaseStamina();  // 스태미나 회복
                    gameView.updateStamina(playerUno.getStamina());
                    stationaryTime = 0;
                }
            } else {
                stationaryTime = 0;
                lastX = playerUno.getCoordX();
                lastY = playerUno.getCoordY();
            }

        }
    }

}