package org.example.sharedmobilityfxproject;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaView;
import org.example.sharedmobilityfxproject.model.*;

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

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//gyuwon
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.File;
import java.io.InputStream;
import javafx.scene.input.KeyCode;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import org.example.sharedmobilityfxproject.view.GameView;

// Main class extends Application for JavaFX application
public class Main extends Application {

    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    private static final int ROWS = 30;
    private static final int COLUMNS = 60;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    // Gem count
    int gemCount = 0;

    // Carbon footprint
    int carbonFootprint = 0;

    // Label to keep track of gem count
    Label gemCountLabel; // Label to display gem count

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

    // Boolean flag to track if the game has finished
    boolean gameFinished = false;

    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;
    private Stage primaryStage;
    private MediaPlayer mediaPlayer;
    private Button btnStartGame;
    private MenuElement menuElement;
    private Button btnExit;
    private VBox buttonBox;
    private VBox imgBox;
    private GameView gameView;
    public StackPane root;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the image
            this.primaryStage = primaryStage;
            Media bgv = new Media(new File("src/main/resources/videos/opening.mp4").toURI().toString());
            Image logoImage = new Image(new File("src/main/resources/images/Way_Back_Home.png").toURI().toString());
            MediaPlayer bgmediaPlayer = new MediaPlayer(bgv);
            MediaView mediaView = new MediaView(bgmediaPlayer);
            ImageView imageView = new ImageView(logoImage);
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(100); // You can adjust this value as needed
            if (bgv == null) {
                System.err.println("Cannot find image file");
                return; // Exit the method if the image file can't be found
            }


            // Initialize the Media and MediaPlayer for background music
            Media media = new Media(new File("src/main/resources/music/waybackHome.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Play the BGM in a loop
            mediaPlayer.play(); // Start playing the BGM

            bgmediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmediaPlayer.play();
            // Create and configure the "Game Start" button
            btnStartGame = menuElement.createButton("Game Start", gameView.showPlayerModeSelection());

            // Create and configure the "Exit" button
            btnExit = menuElement.createButton("Exit", event -> primaryStage.close());

            // Create a VBox for buttons
            buttonBox = new VBox(20, this.btnStartGame, this.btnExit, imageView);
            imgBox = new VBox(20, imageView);
            buttonBox.setAlignment(Pos.CENTER); // Align buttons to center
            imgBox.setAlignment(Pos.TOP_CENTER);

            // Center the VBox in the StackPane
            StackPane.setAlignment(buttonBox, Pos.CENTER);
            StackPane.setAlignment(imgBox, Pos.CENTER);

            this.root = new StackPane();
            this.root.getChildren().add(mediaView);

            // Set up the scene with the StackPane and show the stage
            Scene scene = new Scene(root, 1496, 1117); // Use the same size as the image for a full background
            this.setupKeyControls(scene);

            this.root.getChildren().add(buttonBox);
            this.root.getChildren().add(imgBox);
            primaryStage.setTitle("WayBackHome by OilWrestlingLovers");
            primaryStage.setScene(scene);
            primaryStage.show();












//
//            // Create a StackPane to hold all elements
//            StackPane root = new StackPane();
//            Scene scene = new Scene(root);
//
//            // Settings
//            Image icon = new Image(String.valueOf(getClass().getResource("/images/icon.png")));
//            primaryStage.getIcons().add(icon);
//            primaryStage.setTitle("Shared Mobility Application");
//            primaryStage.setWidth(WIDTH);
//            primaryStage.setHeight(HEIGHT);
//            primaryStage.setResizable(false);
////            primaryStage.setFullScreen(true);
////            primaryStage.setFullScreenExitHint("Press esc to minimize !");
//
//            // Create grid for the game
//            Grid grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);
//
//            // Create keyboard actions handler
//            KeyboardActions ka = new KeyboardActions(grid);
//            // Fill grid with cells
//            for (int row = 0; row < ROWS; row++) {
//                for (int column = 0; column < COLUMNS; column++) {
//                    Cell cell = new Cell(column, row);
//                    ka.setupKeyboardActions(scene);
//                    grid.add(cell, column, row);
//                }
//            }
//
//            // Create label for gem count
//            gemCountLabel = new Label("Gem Count: " + gemCount);
//            gemCountLabel.setStyle("-fx-font-size: 16px;");
//            gemCountLabel.setAlignment(Pos.TOP_LEFT);
//            gemCountLabel.setPadding(new Insets(10));
//
//            // Create label for carbon footprint
//            carbonFootprintLabel = new Label("Carbon Footprint: " + carbonFootprint);
//            carbonFootprintLabel.setStyle("-fx-font-size: 16px;");
//            carbonFootprintLabel.setAlignment(Pos.TOP_LEFT);
//            carbonFootprintLabel.setPadding(new Insets(10));
//
//            // Create a VBox to hold the gem count label
//            VBox vbox = new VBox(gemCountLabel, carbonFootprintLabel);
//            vbox.setAlignment(Pos.TOP_LEFT);
//
//            // Place the gem after the grid is filled and the player's position is initialized
//            int gemColumn;
//            int gemRow;
//            do {
//                gemColumn = (int) (Math.random() * COLUMNS);
//                gemRow = (int) (Math.random() * ROWS);
//            } while (gemColumn == 0 && gemRow == 0); // Ensure gem doesn't spawn at player's starting position
//            Gem gem = new Gem(gemColumn, gemRow);
//            grid.add(gem, gemColumn, gemRow);
//
//            // Initialise Obstacles
//            obstacles = new ArrayList<>();
//            obstacles.add(new Obstacle(grid, 5, 5));
//            obstacles.add(new Obstacle(grid, 10, 5));
//            obstacles.add(new Obstacle(grid, 5, 10));
//
//            // Place the finish cell after the grid is filled and the player's position is initialised
//            int finishColumn;
//            int finishRow;
//            do {
//                finishColumn = (int) (Math.random() * COLUMNS);
//                finishRow = (int) (Math.random() * ROWS);
//            } while ((finishColumn == 0 && finishRow == 0) || (finishColumn == gemColumn && finishRow == gemRow)); // Ensure finish doesn't spawn at player's starting position or gem position
//            finishCell = new Cell(finishColumn, finishRow);
//            finishCell.getStyleClass().add("finish");
//            grid.add(finishCell, finishColumn, finishRow);
//
//            // Initialise currentCell after the grid has been filled
//            // Initialise Player
//            Player playerUno = new Player(25,25,10,1,10,0);
//            ka.playerUnosCell = grid.getCell(playerUno.getCoordY(), playerUno.getCoordY());
//
//            // Initialize currentCell after the grid has been filled
//            ka.currentCell = grid.getCell(0, 0);
//
//            // Add background image, grid, and gem count label to the root StackPane
//            root.getChildren().addAll(grid, vbox);
//
//            // create scene and set to stage
//            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
//            primaryStage.setScene(scene);
//            primaryStage.show();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupKeyControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                if (btnStartGame.isFocused()) {
                    btnExit.requestFocus();
                }
            } else if (event.getCode() == KeyCode.UP) {
                if (btnExit.isFocused()) {
                    btnStartGame.requestFocus();
                }
            }
        });
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

                // Optionally highlight the new cell
                playerUnosCell.highlight();
            }
            // If there is an obstacle, don't move and possibly add some feedback
        }
    }

    // Method to update the gem count label
    private void updateGemCountLabel() {
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