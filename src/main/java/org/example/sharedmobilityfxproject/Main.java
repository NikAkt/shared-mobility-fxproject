package org.example.sharedmobilityfxproject;
import org.example.sharedmobilityfxproject.model.*;

import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.model.Player;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.model.tranportMode.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    int carbonFootprint = 0;

    // Label to keep track of gem count
    static Label gemCountLabel; // Label to display gem count

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint

    // Player (will be implemented)
//    private Point player;

    // Obstacles
    // List to keep track of all obstacles
    private List<Obstacle> obstacles;

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

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create a StackPane to hold all elements
            StackPane root = new StackPane();
            Scene scene = new Scene(root);

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

            busStop busS1 = new busStop(getRandomNumber(20,100),getRandomNumber(20,60));
            busStop busS2 = new busStop(getRandomNumber(20,100),getRandomNumber(20,60));

            ArrayList busStops  = new ArrayList<>();
            busStops.add(busS1);
            busStops.add(busS2);
            busman = new Bus(busStops,15, 15);
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
            carbonFootprintLabel = new Label("Carbon Footprint: " + carbonFootprint);
            carbonFootprintLabel.setStyle("-fx-font-size: 16px;");
            carbonFootprintLabel.setAlignment(Pos.TOP_LEFT);
            carbonFootprintLabel.setPadding(new Insets(10));

            // Create a VBox to hold the gem count label
            VBox vbox = new VBox(gemCountLabel, carbonFootprintLabel);
            vbox.setAlignment(Pos.TOP_LEFT);


            // Initialise Obstacles for x = 0
            obstacles = new ArrayList<>();
            obstacles.add(new Obstacle(grid, 0, 3));
            obstacles.add(new Obstacle(grid, 0, 4));
            obstacles.add(new Obstacle(grid, 0, 5));

            int positionX0 = 8;
            int countX0 = 0;
            while (countX0 < 67) {
                obstacles.add(new Obstacle(grid, 0, positionX0));
                obstacles.add(new Obstacle(grid, 0, positionX0 + 1));
                obstacles.add(new Obstacle(grid, 0, positionX0 + 2));
                positionX0 += 5;
                countX0 += 3;
            }

            // Initialise Obstacles
            obstacles = new ArrayList<>();

// Define x positions
            int[] xPositions = {3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 17, 18, 19, 20, 21, 24, 25, 26, 27, 28, 31, 32, 33, 34, 35, 38, 39, 40, 41, 42, 45, 46, 47, 48, 49, 52, 53, 54, 55, 56, 59, 60, 61, 62, 63};

// For each x position
            for (int x : xPositions) {
                obstacles.add(new Obstacle(grid, x, 3));
                obstacles.add(new Obstacle(grid, x, 4));
                obstacles.add(new Obstacle(grid, x, 5));

                int positionX = 8;
                int countX = 0;
                while (countX < 67) {
                    obstacles.add(new Obstacle(grid, x, positionX));
                    obstacles.add(new Obstacle(grid, x, positionX + 1));
                    obstacles.add(new Obstacle(grid, x, positionX + 2));
                    positionX += 5;
                    countX += 3;
                }
            }

            generateGems(grid, 5); // Replace 5 with the number of gems you want to generate

            // Place the finish cell after the grid is filled and the player's position is initialised
            int finishColumn;
            int finishRow;
            do {
                finishColumn = (int) (Math.random() * COLUMNS);
                finishRow = (int) (Math.random() * ROWS);
            } while ((finishColumn == 0 && finishRow == 0) || grid.getCell(finishColumn, finishRow).getUserData() != null); // Ensure finish doesn't spawn at player's starting position or on a gem
            finishCell = new Cell(finishColumn, finishRow);
            finishCell.getStyleClass().add("finish");
            grid.add(finishCell, finishColumn, finishRow);

            // Initialise currentCell after the grid has been filled
            // Initialise Player
            Player playerUno = new Player(25,25,10,1,10,0);
            ka.playerUnosCell = grid.getCell(playerUno.getCoordY(), playerUno.getCoordY());

            // Initialize currentCell after the grid has been filled
            ka.currentCell = grid.getCell(0, 0);

            // Add background image, grid, and gem count label to the root StackPane
            root.getChildren().addAll(grid, vbox);
            System.out.println(busS1.getX());
            // create scene and set to stage
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
            Timeline busMovementTimeline = new Timeline(new KeyFrame(Duration.seconds(.15), event -> {
                // Assuming 'busman' is your Bus instance and 'busS1' is the target bus stop
                // You might need logic here to select the appropriate bus stop from a list if you have multiple
                busStop targetBusStop = busman.nextStop(); // For example purposes, using a direct reference

                // Call your method to move the bus towards the bus stop
                moveBusTowardsBusStop(grid,busman, targetBusStop);

                // Your existing code might already handle updating the bus position on the grid
                // through the moveBus method called within moveBusTowardsBusStop
            }));
            busMovementTimeline.setCycleCount(Animation.INDEFINITE);
            busMovementTimeline.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void moveBusTowardsBusStop(Grid grid,Bus bus, busStop stop) {
        // Calculate the Manhattan distance for both possible next steps
        int distanceIfMoveX = Math.abs((bus.getX()) - stop.getX()) ;
        int distanceIfMoveY = Math.abs(bus.getY() - stop.getY());
        System.out.println("--------------------");
        System.out.println(stop.getX()+"   "+ stop.getY());

        System.out.println(distanceIfMoveX+"   "+distanceIfMoveY);
        if ((bus.getX()<stop.getX()||bus.getX()>stop.getX())&&bus.flagMove==0 ) {
            System.out.println("----------- moving x ---------");
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
            System.out.println("----------- moving y ---------");
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
        }else if (bus.getX()==stop.getX()&&bus.getY()==stop.getY()){
            System.out.println("----------- ARRIVED..... GET THE FUCK OUT ---------");
            bus.list().add(bus.list().remove(0));
            System.out.println("now going towards :"+bus.nextStop());
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
        System.out.println("BUS MOVING TO :  "+newX+"  "+newY+". GET OUT THE FUCKING WAY");
        grid.moveCell(bus, newX, newY);
        bus.setX(newX);
        bus.setY(newY);

    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * KeyboardActions class is responsible for handling keyboard input and translating it into actions within the grid.
     * It manages the current cell selection and applies keyboard actions to it.
     */

    public class KeyboardActions {

        private Grid grid;
        public Cell currentCell; // Made public for access in start method
        public Cell playerUnosCell; // Made public for access in start method
        private int currentRow = 0;
        private int currentColumn = 0;

        // Constructor to initialise grid
        public KeyboardActions(Grid grid) {
            this.grid = grid;
            // Don't initialize currentCell here
        }
        private void collectGem(Cell gemCell) {
            grid.getChildren().remove(gemCell);
            gemCell.unhighlight(); // Unhighlight only the gem cell
            grid.add(new Cell(gemCell.getColumn(), gemCell.getRow()), gemCell.getColumn(), gemCell.getRow()); // Replace the gem cell with a normal cell
            Main.increaseGemCount(); // Update gem count label
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
                switch (event.getCode()) {
                    case RIGHT -> moveSelection(1, 0);
                    case LEFT -> moveSelection(-1, 0);
                    case UP -> moveSelection(0, -1);
                    case DOWN -> moveSelection(0, 1);
                    case H -> currentCell.highlight();
                    case U -> currentCell.unhighlight();
                    // Player
                    case D -> movePLayer(1, 0);
                    case A -> movePLayer(-1, 0);
                    case W -> movePLayer(0, -1);
                    case S -> movePLayer(0, 1);
                    case T -> hailTaxi();
                    // Add more cases as needed
                }
            });
        }

        /**
         * Hail a taxi and change the player's appearance to yellow.
         */
        private void hailTaxi() {
            if (!hailTaxi) {
                hailTaxi = true;
                // Increase carbon footprint
                carbonFootprint += 75;
                updateCarbonFootprintLabel();
                // Change the color of the player's cell to yellow
                currentCell.setStyle("-fx-background-color: yellow;");
            } else {
                hailTaxi = false;
                // Change the color of the player's cell back to blue
                currentCell.setStyle("-fx-background-color: blue;");
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

        private void movePLayer(int dx, int dy) {
            int newRow = Math.min(Math.max(playerUnosCell.getRow() + dy, 0), grid.getRows() - 1);
            int newColumn = Math.min(Math.max(playerUnosCell.getColumn() + dx, 0), grid.getColumns() - 1);

            // Check if the next cell is an obstacle
            if (obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == newColumn && obstacle.getRow() == newRow)) {
                // Move the player to the new cell because there is no obstacle
                Cell nextCell = grid.getCell(newColumn, newRow);

                // Optionally unhighlight the old cell
                playerUnosCell.unhighlight();

                playerUnosCell = nextCell;

                // Check for interaction with a gem
                if ("gem".equals(playerUnosCell.getUserData())) {
                    collectGem(playerUnosCell);
                    System.out.println("PLayer has entered busstop");
                }

                // Check for interaction with a bus stop
                if (playerUnosCell instanceof busStop) {
                    interactWithBusStop((busStop) playerUnosCell);
                    System.out.println("player has entered stop");
                }

                // Optionally highlight the new cell
                playerUnosCell.highlight();
            }
            // If there is an obstacle, don't move and possibly add some feedback
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
            } while ((gemColumn == 0 && gemRow == 0) || grid.getCell(gemColumn, gemRow).getUserData() != null); // Ensure gem doesn't spawn at player's starting position or on another gem

            Gem gem = new Gem(gemColumn, gemRow,this);
            grid.add(gem, gemColumn, gemRow);
        }
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