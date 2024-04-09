package org.example.sharedmobilityfxproject;
import org.example.sharedmobilityfxproject.model.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.scene.input.KeyCode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.model.Player;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.model.tranportMode.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;
import org.example.sharedmobilityfxproject.controller.GameController;
import org.example.sharedmobilityfxproject.controller.SceneController;

// Main class extends Application for JavaFX application
public class Main extends Application {

    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    private static final int ROWS = 80;
    private static final int COLUMNS = 120;
    private static final double WIDTH = 1300;
    private static final double HEIGHT = 680;

    // Gem count
    static int gemCount = 0;

    // Carbon footprint
    float carbonFootprint = 0;

    // Label to keep track of gem count
    static Label gemCountLabel; // Label to display gem count
    // GYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSS
//    public SceneController sceneController;
//    public GameController gameController;
//    GYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSS

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint

    // Player (will be implemented)
//    private Point player;

    // Obstacles
    // List to keep track of all obstacles
    private List<Obstacle> obstacles;
    public ArrayList<int[]> busStopCoordinates;
    public ArrayList<int[]> obstacleCoordinates;

//    removed from scene
//    ImageView imageView = new ImageView( new Image( "https://upload.wikimedia.org/wikipedia/commons/c/c7/Pink_Cat_2.jpg"));

    // Finish cell
    private Cell finishCell;
    private Bus busman;
    // Boolean flag to track if the game has finished
    boolean gameFinished = false;

    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void increaseGemCount() {
        gemCount++;
        updateGemCountLabel();
    }
    @FunctionalInterface
    public interface GemCollector {
        void collectGem();
    }

//    GYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSS
//    public static void main(String[] args) {
//        launch(args);
//    }
//@Override
//public void start(Stage primaryStage) throws Exception {
//    try {
//        gameController = new GameController();
//        gameController.startGame(primaryStage);
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
//
//}
//    GYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSSGYUWONSSSSSS

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create a StackPane to hold all elements
            StackPane root = new StackPane();
            Scene scene = new Scene(root);
            busStopCoordinates = new ArrayList<>();
            obstacleCoordinates = new ArrayList<>();

            // Settings
            Image icon = new Image(String.valueOf(getClass().getResource("/images/icon.png")));
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("Shared Mobility Application");
            primaryStage.setWidth(WIDTH);
            primaryStage.setHeight(HEIGHT);
            primaryStage.setResizable(false);
//            primaryStage.setFullScreen(true);
//            primaryStage.setFullScreenExitHint("Press esc to minimize !");

            // Create grid for the game
            Grid grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);

            // Create keyboard actions handler
            KeyboardActions ka = new KeyboardActions(grid);
            // Fill grid with cells
            for (int row = 0; row < ROWS; row++) {
                for (int column = 0; column < COLUMNS; column++) {
                    Cell cell = new Cell(column, row);
                    ka.setupKeyboardActions(scene);
                    grid.add(cell, column, row);
                }
            }

            //bus SHITE
            busStop busS1 = new busStop(4,4);
            busStop busS2 = new busStop(110,4);
            busStop busS3 = new busStop(57,25);
            busStop busS4 = new busStop(110,64);
            busStop busS5 = new busStop(57,64);
            busStop busS6 = new busStop(4,64);
            busStop busS7 = new busStop(4,34);

            busStopCoordinates.add(new int[]{busS1.getX(), busS1.getY()});
            busStopCoordinates.add(new int[]{busS2.getX(), busS2.getY()});
            busStopCoordinates.add(new int[]{busS3.getX(), busS3.getY()});
            busStopCoordinates.add(new int[]{busS4.getX(), busS4.getY()});
            busStopCoordinates.add(new int[]{busS5.getX(), busS5.getY()});
            busStopCoordinates.add(new int[]{busS6.getX(), busS6.getY()});
            busStopCoordinates.add(new int[]{busS7.getX(), busS7.getY()});

            ArrayList busStops  = new ArrayList<>();
            busStops.add(busS1);
            busStops.add(busS2);
            busStops.add(busS3);
            busStops.add(busS4);
            busStops.add(busS5);
            busStops.add(busS6);
            busStops.add(busS7);

            busman = new Bus(busStops,4, 4);
            for (int i = 0; i < busman.list().size(); i++){
                busStop stop = busman.list().get(i);
                grid.add(stop,stop.getX(), stop.getY());
            }

            grid.add(busman,busman.getX(), busman.getY());// Example starting position

            // Schedule the bus to move every second


            // Create label for gem count
            gemCountLabel = new Label("Gem Count: " + gemCount);
            gemCountLabel.setStyle("-fx-font-size: 16px;");
            gemCountLabel.setAlignment(Pos.TOP_LEFT);
            gemCountLabel.setPadding(new Insets(10));

            // Create label for carbon footprint
            carbonFootprintLabel = new Label("Carbon Footprint: " + String.format("%.1f", carbonFootprint));
            carbonFootprintLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
            carbonFootprintLabel.setAlignment(Pos.TOP_LEFT);
            carbonFootprintLabel.setPadding(new Insets(10));

            // Create a VBox to hold the gem count label
            VBox vbox = new VBox(gemCountLabel, carbonFootprintLabel);
            vbox.setAlignment(Pos.TOP_LEFT);


            // Initialise Obstacles for x = 0
            // Initialise Obstacles
            // Initialise Obstacles
            obstacles = new ArrayList<>();

            // Define the size of the obstacle blocks and the gap between them
            int obstacleWidth = 5;
            int obstacleHeight = 3;
            int gap = 5;

            // Calculate the number of obstacle blocks in each direction
            int numBlocksX = (COLUMNS - 2 * gap) / (obstacleWidth + gap);
            int numBlocksY = (ROWS - 2 * gap) / (obstacleHeight + gap);


            // For each block position
            for (int bx = 0; bx < numBlocksX; bx++) {
                for (int by = 0; by < numBlocksY; by++) {
                    // Calculate the top-left corner of the block
                    int x = gap + bx * (obstacleWidth + gap);
                    int y = gap + by * (obstacleHeight + gap);

                    // Create the obstacle block
                    for (int i = 0; i < obstacleWidth; i++) {
                        for (int j = 0; j < obstacleHeight; j++) {
                            obstacles.add(new Obstacle(grid, x + i, y + j));
                            List<Integer> coordinatePair = new ArrayList<>();

                            coordinatePair.add(x + i);
                            coordinatePair.add(y + j);
                        }
                    }
                }
            }
            for (Obstacle obstacle : obstacles) {
                obstacleCoordinates.add(new int[]{obstacle.getColumn(), obstacle.getRow()});
            }

            generateGems(grid, 5); // Replace 5 with the number of gems you want to generate

            // Place the finish cell after the grid is filled and the player's position is initialised
            int finishColumn;
            int finishRow;
            finishColumn=102;
            finishRow=58;
//            do {
//                finishColumn = (int) (Math.random() * COLUMNS);
//                finishRow = (int) (Math.random() * ROWS);
//            } while ((finishColumn == 0 && finishRow == 0) || grid.getCell(finishColumn, finishRow).getUserData() != null); // Ensure finish doesn't spawn at player's starting position or on a gem
            finishCell = new Cell(finishColumn, finishRow);
            finishCell.getStyleClass().add("finish");
            grid.add(finishCell, finishColumn, finishRow);

            // Initialise Player
            Player playerUno = new Player(0,0,10,1,10,0);
            playerUno.initCell(grid);
            ka.playerUno = playerUno;

            // Initialize currentCell after the grid has been filled
            ka.currentCell = grid.getCell(0, 0);

            // Add background image, grid, and gem count label to the root StackPane
            root.getChildren().addAll(grid, vbox);
//            System.out.println(busS1.getX());
            // create scene and set to stage
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

            // Schedule the bus to move every second
            Timeline busMovementTimeline = new Timeline(new KeyFrame(Duration.seconds(.1), event -> {
                busStop targetBusStop = busman.nextStop(); // Assuming this method correctly returns the next bus stop
                if (!busman.isWaiting){


                moveBusTowardsBusStop(grid, busman, targetBusStop, ka, playerUno);

                // Here's the updated part
                if (ka.onBus) {
                    // Update player's coordinates to match the bus when the player is on the bus
                    playerUno.setCellByCoords(grid, busman.getX(), busman.getY());
                    System.out.println("Player coordinates (on bus): " + playerUno.getCoordX() + ", " + playerUno.getCoordY());
                    //Increase carbon footprint amount as long as player is on the bus
                    carbonFootprint+=0.2; //subject to change
                    updateCarbonFootprintLabel();
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

            // Player animation
            Timeline playerMovementTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

            }));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }

    public void moveBusTowardsBusStop(Grid grid,Bus bus, busStop stop,KeyboardActions ka, Player playerUno) {
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
                moveBus(grid ,bus, newX, bus.getY());
            } else if (canMoveBusTo(bus.getX(), bus.getY() + (bus.getY() < stop.getY() ? 1 : -1))) {
                // Move vertically as a fallback
                moveBus(grid ,bus, bus.getX(), bus.getY() + (bus.getY() < stop.getY() ? 1 : -1));
            }
        }

        else if (bus.getY()<stop.getY()||bus.getY()>stop.getY()){
//            System.out.println("----------- moving y ---------");
            // Move vertically towards the bus stop, if not blocked
            int newY = bus.getY() + (bus.getY() < stop.getY() ? 1 : -1);
            if (canMoveBusTo(bus.getX(), newY)) {

                moveBus(grid,bus, bus.getX(), newY);
            }
            else if (canMoveBusTo(bus.getX() +1, bus.getY())) {
                // Move horizontally as a fallback
                if (bus.flagMove == 0){
                    bus.flagMove =1;
                }
                moveBus(grid,bus, bus.getX() + +1, bus.getY());
            }
        }
        //arriving at stop logic
        else if (bus.getX()==stop.getX()&&bus.getY()==stop.getY()){
            System.out.println("----------- ARRIVED..... GET THE FUCK OUT ---------");
            bus.waitASec();
            bus.list().add(bus.list().remove(0));
            System.out.println("now going towards :"+bus.nextStop());
            if(!ka.playerMovementEnabled&&playerUno.getCoordX()==bus.getX()&&playerUno.getCoordY()==bus.getY()){
                System.out.println("----------- You just got on the bus ---------");
            ka.onBus = true;

        }else if(ka.onBus){
                System.out.println("----------- You arrived at  ---------"+stop);
                System.out.println("----------- Press E to get off  ---------");
                ka.onBus = true;

            }
        }
        else if (bus.getY()==stop.getY()) {
         bus.flagMove=0;
        }

    }

    private boolean canMoveBusTo(int x, int y) {
        // Implement logic to check if the bus can move to (x, y) considering obstacles
        // Return true if it can move, false if there's an obstacle
        return obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == x && obstacle.getRow() == y);
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

public class KeyboardActions {
    private boolean playerMovementEnabled = true;
    private boolean onBus = false;
    private Grid grid;
    public Cell currentCell;
    public Player playerUno;

    public KeyboardActions(Grid grid) {
        this.grid = grid;
    }

    public void setupKeyboardActions(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (playerMovementEnabled) {
                switch (event.getCode()) {
                    case D -> movePlayer(1, 0);
                    case A -> movePlayer(-1, 0);
                    case W -> movePlayer(0, -1);
                    case S -> movePlayer(0, 1);
                    case T -> hailTaxi();
                    case E -> togglePlayerMovement();
                }
            } else if (event.getCode() == KeyCode.E) {
                togglePlayerMovement();
            }
        });
    }

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
        playGemCollectSound();
    }

    private void interactWithBusStop(busStop stop) {
        System.out.println("Interacted with Bus Stop at: " + stop.getX() + ", " + stop.getY());
    }

    private void finishGame() {
        gameFinished = true;
        Label levelCompleteLabel = new Label("Level Complete");
        levelCompleteLabel.setStyle("-fx-font-size: 24px;");
        StackPane root = (StackPane) grid.getScene().getRoot();
        root.getChildren().add(levelCompleteLabel);
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> ((Stage) grid.getScene().getWindow()).close());
        pause.play();
    }

    private void hailTaxi() {
        hailTaxi = !hailTaxi;
        currentCell.setStyle(hailTaxi ? "-fx-background-color: yellow;" : "-fx-background-color: blue;");
        if (hailTaxi) {
            carbonFootprint += 75;
            updateCarbonFootprintLabel();
        }
    }

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

    public void printArrayContents() {
        System.out.println("Obstacle Coordinates:");
        for (int[] coordinates : obstacleCoordinates) {
            System.out.println("X: " + coordinates[0] + ", Y: " + coordinates[1]);
        }

        System.out.println("Bus Stop Coordinates:");
        for (int[] coordinates : busStopCoordinates) {
            System.out.println("X: " + coordinates[0] + ", Y: " + coordinates[1]);
        }
    }

    // Place the gem after the grid is filled and the player's position is initialized
    public void generateGems(Grid grid, int numberOfGems) {
        for (int i = 0; i < numberOfGems; i++) {
            int gemColumn;
            int gemRow;
            do {
                gemColumn = (int) (Math.random() * COLUMNS);
                gemRow = (int) (Math.random() * ROWS);
            } while (containsPointInArray(busStopCoordinates, gemColumn, gemRow) || containsPointInArray(obstacleCoordinates, gemColumn, gemRow));

            Gem gem = new Gem(gemColumn, gemRow,this);
            grid.add(gem, gemColumn, gemRow);
        }
    }

    public boolean containsPointInArray(ArrayList<int[]> array, int x, int y) {
        for (int[] coordinates : array) {
            if (coordinates[0] == x && coordinates[1] == y) {
                return true;
            }
        }
        return false;
    }

    // Method to update the gem count label
    private static void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
        }

    // Method to play the gem collect sound
    private void playGemCollectSound() {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(GEM_COLLECT_SOUND)).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        // Release resources after sound finishes playing3211
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }

    // Method to update the carbon footprint label
    private void updateCarbonFootprintLabel() {
        carbonFootprintLabel.setText("Carbon Footprint: " + carbonFootprint);
    }
}