package org.example.sharedmobilityfxproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javafx.util.Duration;
import org.example.sharedmobilityfxproject.controller.GameController;
import org.example.sharedmobilityfxproject.controller.KeyboardActionController;
import org.example.sharedmobilityfxproject.controller.SceneController;
import org.example.sharedmobilityfxproject.model.*;
import org.example.sharedmobilityfxproject.model.tranportMode.Bus;
import org.example.sharedmobilityfxproject.model.tranportMode.Taxi;
import org.example.sharedmobilityfxproject.view.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Main class extends Application for JavaFX application
public class Main extends Application {

    public SceneController sceneController;
    public GameController gameController;

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            System.out.println("Game Started");

            GameView gameView = new GameView();
            gameView.showInitialScreen(primaryStage);
            gameController = new GameController(gameView);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


/////////////////////////////////////////////////////////////////////
package org.example.sharedmobilityfxproject;
import org.example.sharedmobilityfxproject.model.*;

        import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.model.Player;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.model.tranportMode.Bus;
import org.example.sharedmobilityfxproject.model.tranportMode.Taxi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Arrays;

// Main class extends Application for JavaFX application
public class Main extends Application {

    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    private static final int ROWS = 80;
    private static final int COLUMNS = 120;
    private static final double WIDTH = 1300;
    private static final double HEIGHT = 680;
    private boolean isMetroSceneActive = false;
    // Gem count
    static int gemCount = 0;

    // Carbon footprint
    float carbonFootprint = 0;

    // Label to keep track of gem count
    static Label gemCountLabel; // Label to display gem count

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
    private Taxi taximan;
    boolean gameFinished = false;
    private Pane metroLayer;
    private Scene metroScene;
    private Scene scene;
    private Grid grid;
    private StackPane root;
    private Grid metroGrid;
    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;

    public static void increaseGemCount() {
        gemCount++;
        updateGemCountLabel();
    }
    @FunctionalInterface
    public interface GemCollector {
        void collectGem();
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            // Create a StackPane to hold all elements
            StackPane root = new StackPane();
            scene = new Scene(root);
            busStopCoordinates = new ArrayList<>();
            obstacleCoordinates = new ArrayList<>();


            // Settings
            Image icon = new Image(String.valueOf(getClass().getResource("/images/icon.png")));
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("PokemonGo");
            primaryStage.setWidth(WIDTH);
            primaryStage.setHeight(HEIGHT);
            primaryStage.setResizable(false);
//            primaryStage.setFullScreen(true);
//            primaryStage.setFullScreenExitHint("Press esc to minimize !");

            // Create grid for the game
            grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);

            // Create keyboard actions handler
            KeyboardActions ka = new KeyboardActions(grid);
            KeyboardActions km = new KeyboardActions(metroGrid);
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
            metroStop metroEntrance1 = new metroStop(2,4);

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
            taximan= new Taxi (58,28);
            for (int i = 0; i < busman.list().size(); i++){
                busStop stop = busman.list().get(i);
                grid.add(stop,stop.getX(), stop.getY());
            }
            grid.add(metroEntrance1,metroEntrance1.getX(),metroEntrance1.getY());
            grid.add(busman,busman.getX(), busman.getY());// Example starting position
            grid.add(taximan, taximan.getX(), taximan.getY());
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

            // Initialise currentCell after the grid has been filled
            // Initialise Player
            Player playerUno = new Player(0,0,10,1,10,0);
            playerUno.initCell(grid);
            ka.playerUno = playerUno;
            initializeMetroSystem(ka);
            // Initialize currentCell after the grid has been filled
            ka.currentCell = grid.getCell(0, 0);

            // Add background image, grid, and gem count label to the root StackPane
            root.getChildren().addAll(grid, vbox);
//            System.out.println(busS1.getX());
            // create scene and set to stage
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();
            Timeline busMovementTimeline = new Timeline(new KeyFrame(Duration.seconds(.1), event -> {
                busStop targetBusStop = busman.nextStop(); // Assuming this method correctly returns the next bus stop
                if(taximan.hailed&&!taximan.arrived){
                    moveTaxiTowardsPlayer(grid, taximan, ka);
                }
                if (!busman.isWaiting){


                    moveBusTowardsBusStop(grid, busman, targetBusStop, ka);

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


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initializeMetroSystem(KeyboardActions player) {
        metroLayer = new StackPane();
        metroLayer.setStyle("-fx-background-color: #CCCCCC;");
        metroGrid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);
        // Initialize metro cells
        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                Cell cell = new Cell(column, row);
                // Make sure cells are visible
                metroGrid.add(cell, column, row);
            }
        }
        metroStop under1 = new metroStop(2,4);
        metroGrid.add(under1,2,4);
        player.playerUno.initCell(metroGrid);
        Label testLabel = new Label("Metro System Active");

        metroLayer.getChildren().addAll(metroGrid,testLabel);
        metroScene = new Scene(metroLayer, WIDTH, HEIGHT);
        metroScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());

    }

    public void moveTaxiTowardsPlayer(Grid grid, Taxi bus, KeyboardActions ka) {

        if (bus.getX()==ka.playerUno.getCoordX()&&bus.getY()==ka.playerUno.getCoordY()&&taximan.arrived&&!ka.inTaxi){
            ka.inTaxi = true;

        }
//        System.out.println(distanceIfMoveX+"   "+distanceIfMoveY);
        else {
            if ((bus.getX() < ka.playerUno.getCoordX() || bus.getX() > ka.playerUno.getCoordX()) && bus.flagMove == 0) {
//            System.out.println("----------- moving x ---------");
                // Move horizontally towards the bus stop, if not blocked
                int newX = bus.getX() + (bus.getX() < ka.playerUno.getCoordX() ? 1 : -1);
                if (canMoveBusTo(newX, bus.getY())) {
                    moveTaxi(grid, bus, newX, bus.getY());
                } else if (canMoveBusTo(bus.getX(), bus.getY() + (bus.getY() < ka.playerUno.getCoordY() ? 1 : -1))) {
                    // Move vertically as a fallback
                    moveTaxi(grid, bus, bus.getX(), bus.getY() + (bus.getY() < ka.playerUno.getCoordY() ? 1 : -1));
                }
            } else if (bus.getY() < ka.playerUno.getCoordY() || bus.getY() > ka.playerUno.getCoordY()) {
//            System.out.println("----------- moving y ---------");
                // Move vertically towards the bus stop, if not blocked
                int newY = bus.getY() + (bus.getY() < ka.playerUno.getCoordY() ? 1 : -1);
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
            else if (bus.getX() == ka.playerUno.getCoordX() && bus.getY() == ka.playerUno.getCoordY()) {
                System.out.println("----------- Taxi arrived ---------");
                bus.arrived = true;

                if (bus.hailed && ka.playerUno.getCoordX() == bus.getX() && ka.playerUno.getCoordY() == bus.getY()) {
                    System.out.println("----------- You just got in the taxia---------");
                    ka.inTaxi = true;

                } else if (ka.inTaxi) {

                    System.out.println("----------- Press E to get off  ---------");
                    ka.inTaxi = true;

                }
            } else if (bus.getY() == ka.playerUno.getCoordY()) {
                bus.flagMove = 0;
            }

        }}

    public void moveBusTowardsBusStop(Grid grid,Bus bus, busStop stop,KeyboardActions ka) {
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
            if(!ka.playerMovementEnabled&&ka.playerUno.getCoordX()==bus.getX()&&ka.playerUno.getCoordY()==bus.getY()){
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
    private void moveTaxi(Grid grid,Taxi bus, int newX, int newY) {
        // Move the bus to the new position (newX, newY) on the grid

        grid.moveCell(bus, newX, newY);

        bus.setX(newX);
        bus.setY(newY);
        //grid.add(cell,cell.getColumn(),cell.getRow());

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

    /**
     * KeyboardActions class is responsible for handling keyboard input and translating it into actions within the grid.
     * It manages the current cell selection and applies keyboard actions to it.
     */
    public class KeyboardActions {
        private boolean playerMovementEnabled = true;
        private boolean onBus = false;
        public boolean inTaxi = false;
        private Grid grid;
        public Cell currentCell; // Made public for access in start method
        public Player playerUno; // Made public for access in start method
        private int currentRow = 0;
        private int currentColumn = 0;
        //        public void setX(int i ){
//            this.playerUnosCell.setRow(i);
//        }
//        public void setY(int j ){
//            this.playerUnosCell.setColumn(j);
//        }
//        public int getX(){
//            return this.currentColumn;
//        }
//        public int getY(){
//            return this.currentRow;
//        }
        // Constructor to initialise grid
        public KeyboardActions(Grid grid) {
            this.grid = grid;
            // Don't initialize currentCell here
        }
        private void collectGem(Cell gemCell) {
            grid.getChildren().remove(gemCell);
            gemCell.unhighlight(); // Unhighlight only the gem cell
            grid.add(new Cell(gemCell.getColumn(), gemCell.getRow()), gemCell.getColumn(), gemCell.getRow()); // Replace the gem cell with a normal cell
            playGemCollectSound();
        }

        private void interactWithBusStop(busStop stop) {
            // Implement what happens when interacting with a bus stop
            // For example, showing a UI prompt to board a bus, or automatically boarding
            System.out.println("Interacted with Bus Stop at: " + stop.getX() + ", " + stop.getY());
            // Additional logic for boarding the bus, updating stats, etc.
        }

        public void setupKeyboardActions(Scene scene) {
            scene.setOnKeyPressed(event -> {
                if (playerMovementEnabled) {

                    if(this.inTaxi){
                        switch (event.getCode()) {
                            case D -> movePLayer(2, 0);
                            case A -> movePLayer(-2, 0);
                            case W -> movePLayer(0, -2);
                            case S -> movePLayer(0, 2);
                            case T -> this.inTaxi =false;
                            case E -> togglePlayerMovement();
                            case C ->
                                    System.out.println("The player is located at coordinates: (" + playerUno.getCoordX() + ", " + playerUno.getCoordY() + ")" +
                                            "\nPlayer is currently " + (onBus ? "on the bus." : "not on the bus.") +
                                            "\nPlayer is " + (playerMovementEnabled ? "moving." : "waiting.") +
                                            "\nBus is at coordinates: (" + busman.getX() + "," + busman.getY() + ")");
                        }}

                    // Player
                    else{ switch (event.getCode()) {
                        case D -> movePLayer(1, 0);
                        case A -> movePLayer(-1, 0);
                        case W -> movePLayer(0, -1);
                        case S -> movePLayer(0, 1);
                        case T -> hailTaxi();
                        case E -> togglePlayerMovement();
                        case C ->System.out.println("The player is located at coordinates: (" + playerUno.getCoordX() + ", " + playerUno.getCoordY() + ")" +
                                "\nPlayer is currently " + (onBus ? "on the bus." : "not on the bus.") +
                                "\nPlayer is " + (playerMovementEnabled ? "moving." : "waiting.") +
                                "\nBus is at coordinates: (" + busman.getX() + "," + busman.getY() + ")");

                    }}

                }

                // Add more cases as needed
                else{switch (event.getCode()) {case E -> togglePlayerMovement();}}
            });
        }


        private void togglePlayerMovement() {
            if (this.onBus) {
                // Convert the player's current cell coordinates into a Point for easy comparison
                int[] playerLocation = {playerUno.getCoordX(), playerUno.getCoordY()}; // Ensure the order of coordinates is (x, y)
                System.out.println(playerLocation[0] + "  " + playerLocation[1]);

                // Check if the player's current location matches any bus stop location
                boolean atBusStop = busStopCoordinates.stream()
                        .anyMatch(location -> location[0] == playerLocation[0] && location[1] == playerLocation[1]); // Compare coordinates individually

                if (atBusStop) {
                    // Allow the player to get off the bus
                    playerMovementEnabled = true;
                    this.onBus = false;
                    System.out.println("You got off the bus.");
                } else {
                    System.out.println("You can only get off the bus at a bus stop.");
                }
            }
            else{if (playerUno.getCell() instanceof busStop&&!this.onBus) {
                playerMovementEnabled = !playerMovementEnabled;
                if (!playerMovementEnabled) {
                    // Player decides to wait at a bus stop
                    System.out.println("----------- Waiting for bus ---------"+playerMovementEnabled);
                    ((busStop) playerUno.getCell()).setPlayerHere(true); // Mark the player as waiting at the bus stop

                } else {
                    // Player decides to continue moving
                    if (!this.onBus) {
                        System.out.println("----------- Impatient fuck ---------");
                        ((busStop) playerUno.getCell()).setPlayerHere(false); // Mark the player as no longer waiting at the bus stop
                    }

                }}}
        }

        /**
         * Hail a taxi and change the player's appearance to yellow.
         */
        private void hailTaxi() {
            if (taximan.hailed) {
                taximan.hailed = !taximan.hailed;
            }
            else{
                taximan.hailed = true;
            }
        }

        /**
         * Attempts to move the current selection by a specified number of columns and rows.
         * The movement is performed if the destination cell is not an obstacle.
         *
         * @param dx The number of columns to move. A positive number moves right, a negative number moves left.
         * @param dy The number of rows to move. A positive number moves down, a negative number moves up.
         */
        private void moveSelection(int dx, int dy) {
            // Check if the game is finished, if so, return without allowing movement
            if (gameFinished) {
                return;
            }

            int newRow = Math.min(Math.max(currentRow + dy, 0), grid.getRows() - 1);
            int newColumn = Math.min(Math.max(currentColumn + dx, 0), grid.getColumns() - 1);
            Cell newCell = grid.getCell(newColumn, newRow);

            // Check if the next cell is an obstacle
            if (obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == newColumn && obstacle.getRow() == newRow)) {
                // Move the player to the new cell because there is no obstacle
                Cell nextCell = grid.getCell(newColumn, newRow);

                // Optionally unhighlight the old cell
                currentCell.unhighlight();

                currentCell = nextCell;
                currentRow = newRow;
                currentColumn = newColumn;

                // Optionally highlight the new cell
                currentCell.highlight();
            }
            // If there is an obstacle, don't move and possibly add some feedback
            if (newCell == finishCell) {
                // Player reached the finish cell
                gameFinished = true; // Set game as finished
                // Display "Level Complete" text
                Label levelCompleteLabel = new Label("Level Complete");
                levelCompleteLabel.setStyle("-fx-font-size: 24px;");
                StackPane root = (StackPane) grid.getScene().getRoot();
                root.getChildren().add(levelCompleteLabel);

                // Exit the game after five seconds
                PauseTransition pause = new PauseTransition(Duration.seconds(5));
                pause.setOnFinished(event -> ((Stage) grid.getScene().getWindow()).close());
                pause.play();
            }

            if ("gem".equals(newCell.getUserData())) {
                grid.getChildren().remove(newCell);
                newCell.unhighlight(); // Unhighlight only the gem cell
                grid.add(new Cell(newColumn, newRow), newColumn, newRow); // Replace the gem cell with a normal cell
                updateGemCountLabel(); // Update gem count label
            }
        }
        public Grid getMetroGrid() {
            return metroGrid;
        }
        private void movePLayer(int dx, int dy) {

            int newRow = Math.min(Math.max(playerUno.getCoordY() + dy, 0), grid.getRows() - 1);
            int newColumn = Math.min(Math.max(playerUno.getCoordX() + dx, 0), grid.getColumns() - 1);
            Cell newCell = grid.getCell(newColumn, newRow);
            Cell metroCell = metroGrid.getCell(newColumn, newRow);
            if(isMetroSceneActive){
                playerUno.getCell().unhighlight();
                playerUno.setCell(metroCell);
                playerUno.getCell().highlight();
                System.out.println("player pos: " +playerUno.getCoordX()+" "+playerUno.getCoordY());
            }
            if (playerUno.getCell() instanceof metroStop) {

                isMetroSceneActive = !isMetroSceneActive; // Metro scene is now active
                Stage primaryStage = (Stage) grid.getScene().getWindow();
                playerUno.isUnderground = true;
                System.out.println(this.grid);
                this.grid =metroGrid;
                if(isMetroSceneActive){
                    primaryStage.setScene(metroScene);
                    this.setupKeyboardActions(metroScene);

                }
                if(!isMetroSceneActive){
                    primaryStage.setScene(scene);
                    this.setupKeyboardActions(scene);
                }

                metroLayer.requestFocus();
                playerUno.setCellByCoords(this.grid,newColumn,newRow);
                System.out.println("Player has entered a metro entrance" + this.grid);this.setupKeyboardActions(metroScene);

            }
            // Check if the next cell is an obstacle
            if (obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == newColumn && obstacle.getRow() == newRow)) {
                // Move the player to the new cell because there is no obstacle
                Cell nextCell = grid.getCell(newColumn, newRow);
                Cell nextMetroCell = metroGrid.getCell(newColumn, newRow);
                // Optionally unhighlight the old cell
                playerUno.getCell().unhighlight();


                playerUno.setCell(nextCell);


                if (inTaxi) {
                    // Assuming taximan is accessible from here, or find a way to access it
                    moveTaxi(grid, taximan, newColumn, newRow);

                }
                // Check for interaction with a gem
                if ("gem".equals(playerUno.getCell().getUserData())) {
                    collectGem(playerUno.getCell());
                    System.out.println("PLayer has entered busstop");
                }

                // Check for interaction with a bus stop
                if (playerUno.getCell() instanceof busStop) {
                    interactWithBusStop((busStop) playerUno.getCell());
                    System.out.println("player has entered stop");
                }
                if (newCell == finishCell) {
                    // Player reached the finish cell
                    gameFinished = true; // Set game as finished
                    // Display "Level Complete" text
                    Label levelCompleteLabel = new Label("Level Complete");
                    levelCompleteLabel.setStyle("-fx-font-size: 24px;");
                    StackPane root = (StackPane) grid.getScene().getRoot();
                    root.getChildren().add(levelCompleteLabel);

                    // Exit the game after five seconds
                    PauseTransition pause = new PauseTransition(Duration.seconds(5));
                    pause.setOnFinished(event -> ((Stage) grid.getScene().getWindow()).close());
                    pause.play();
                }
                // Optionally highlight the new cell
                playerUno.getCell().highlight();
            }
            // If there is an obstacle, don't move and possibly add some feedback
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