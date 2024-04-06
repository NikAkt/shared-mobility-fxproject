package org.example.sharedmobilityfxproject.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import org.example.sharedmobilityfxproject.controller.KeyBoradActionController;
import org.example.sharedmobilityfxproject.model.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.Main;
import org.example.sharedmobilityfxproject.controller.GameController;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class GameView {

    // **** Class call ****
    public GameView gameView;
    public GameController gameController;
    public Gem gem;
    public Obstacle obstacle;
    // ****JavaFX load****
    public VBox gameModeBox;
    public Main main;
    public VBox buttonBox;
    public StackPane root;
    public MediaPlayer mediaPlayer;
    public HBox topRow;
    public HBox bottomRow;
    public Stage primaryStage;
    public VBox stageSelectionBox;
    public static Label gemCountLabel;
    // **** Variables Setting ****
    // Label to keep track of gem count

    //**** Cell ****
    //Finish cell
    public Cell finishCell;

    // **** Obstacles ****
    // List to keep track of all obstacles
    public List<Obstacle> obstacles;

    // Gem count
    static int gemCount = 0;
    // Carbon footprint
    int carbonFootprint = 0;

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint

    //Grid setting
    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    private static final int ROWS = 30;
    private static final int COLUMNS = 60;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;


    public GameView(Grid grid) {
        gameController = new GameController();
        this.obstacles = new ArrayList<>();
        gameController.initializeObstacles(grid);
    }

    public void showInitialScreen(Stage primaryStage) {
        gameController = new GameController();
        Media bgv = new Media(new File("src/main/resources/videos/opening.mp4").toURI().toString());
        Image logoImage = new Image(new File("src/main/resources/images/Way_Back_Home.png").toURI().toString());
        MediaPlayer bgmediaPlayer = new MediaPlayer(bgv);
        MediaView mediaView = new MediaView(bgmediaPlayer);
        ImageView imageView = new ImageView(logoImage);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100); // You can adjust this value as needed

        bgmediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmediaPlayer.play();

        this.root = new StackPane();
        // Create and configure the "Game Start" button
        Button btnStartGame = gameController.createButton("Game Start", event -> showPlayerModeSelection(primaryStage,buttonBox,root, bgmediaPlayer));

        // Create and configure the "Exit" button
        Button btnExit = gameController.createButton("Exit", event -> primaryStage.close());

        // Create a VBox for buttons
        buttonBox = new VBox(20, btnStartGame, btnExit, imageView);
        VBox imgBox = new VBox(20, imageView);
        buttonBox.setAlignment(Pos.CENTER); // Align buttons to center
        imgBox.setAlignment(Pos.TOP_CENTER);

        // Center the VBox in the StackPane
        StackPane.setAlignment(buttonBox, Pos.CENTER);
        StackPane.setAlignment(imgBox, Pos.CENTER);

        this.root.getChildren().add(mediaView);

        // Set up the scene with the StackPane and show the stage
        Scene scene = new Scene(this.root , 1496, 1117); // Use the same size as the image for a full background
        gameController.setupKeyControls(scene);

        this.root.getChildren().add(buttonBox);
        this.root.getChildren().add(imgBox);

        // Set focus on the "Game Start" button initially
        btnStartGame.requestFocus();

        primaryStage.setTitle("WayBackHome by OilWrestlingLovers");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true); // Set the stage to full screen
        primaryStage.show();
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DOWN:
                    if (btnStartGame.isFocused()) {
                        btnExit.requestFocus();
                    }
                    break;
                case UP:
                    if (btnExit.isFocused()) {
                        btnStartGame.requestFocus();
                    }
                    break;
                case ENTER:
                    if (btnStartGame.isFocused()) {
                        btnStartGame.fire();
                    } else if (btnExit.isFocused()) {
                        btnExit.fire();
                    }
                    break;
                default:
                    break;
            }
        });

    }

    public void showStageSelectionScreen(Stage actionEvent,MediaPlayer mdv) {

        if (topRow == null && bottomRow == null) {
            topRow = new HBox(10);
            bottomRow = new HBox(10);
            topRow.setAlignment(Pos.CENTER);
            bottomRow.setAlignment(Pos.CENTER);

            String[] topStages = {"Seoul", "Athens", "Dublin", "Istanbul"};
            String[] bottomStages = {"Vilnius", "Back"};

            for (String stage : topStages) {
                ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
                Button stageButton = gameController.createStageButton(stage, stageImage,stageSelectionBox,gameModeBox,root,actionEvent,mdv);
                topRow.getChildren().add(stageButton);
            }

            for (String stage : bottomStages) {
                ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
                Button stageButton = gameController.createStageButton(stage, stageImage,stageSelectionBox,gameModeBox, root, actionEvent, mdv);
                bottomRow.getChildren().add(stageButton);
            }
        }

        stageSelectionBox = new VBox(100, topRow, bottomRow);
        stageSelectionBox.setAlignment(Pos.CENTER);
        gameModeBox.setVisible(false);

        root.getChildren().removeAll(buttonBox, gameModeBox);
        root.getChildren().add(stageSelectionBox);

    }
    public ImageView createStageImage(String stageName) {
        String imagePath = switch (stageName) {
            case "Seoul" -> "/images/seoul.jpg"; // 서울 이미지 경로
            case "Athens" -> "/images/athens.png"; // 아테네 이미지 경로
            case "Dublin" -> "/images/dublin.png"; // 더블린 이미지 경로
            case "Vilnius" -> "/images/vilnius.png"; // 더블린 이미지 경로
            case "Istanbul" -> "/images/istanbul.png"; // 더블린 이미지 경로
            case "Home" -> "/images/home.png"; // 더블린 이미지 경로
            case "Back" -> "/images/home.png";
            default ->
                // 기본 이미지 또는 에러 처리
                    "/images/Way_Back_Home.png.png";
        };
        System.out.println(imagePath);
        Image is = new Image(new File("src/main/resources/"+imagePath).toURI().toString());
        System.out.println(is);

        if (is == null) {
            throw new IllegalStateException("Cannot find image for stage: " + stageName);
        }
        ImageView imageView = new ImageView(is);
        imageView.setFitHeight(200); // 이미지 높이를 설정
        imageView.setFitWidth(230);  // 이미지 너비를 설정
        return imageView;
    }
    public void loadGameScreen(String stageName, Stage actionEvent) {
        try {
            primaryStage = actionEvent;
            // Create a StackPane to hold all elements
            StackPane root = new StackPane();
            Scene scene = new Scene(root);

            // Settings

            Image icon = new Image(new File("src/main/resources/ico.png").toURI().toString());
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("Shared Mobility Application");
            primaryStage.setWidth(WIDTH);
            primaryStage.setHeight(HEIGHT);
            primaryStage.setFullScreen(true); // Set the stage to full screen

            // Create grid for the game
            Grid grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);


            // Create keyboard actions handler

            KeyBoradActionController ka = new KeyBoradActionController(gameView, grid);
            // Fill grid with cells
            Cell cell = null;
            for (int row = 0; row < ROWS; row++) {
                for (int column = 0; column < COLUMNS; column++) {
                    cell = new Cell(column, row);
                    ka.setupKeyboardActions(scene);
                    grid.add(cell, column, row);
                }
            }

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

            int gemColumn;
            int gemRow;
            do {
                gemColumn = (int) (Math.random() * COLUMNS);
                gemRow = (int) (Math.random() * ROWS);
            } while (gemColumn == 0 && gemRow == 0); // Ensure gem doesn't spawn at player's starting position
            Gem gem = new Gem(gemColumn, gemRow);
            grid.add(gem, gemColumn, gemRow);

            // Initialise Obstacles for x = 0 only
            obstacles = new ArrayList<>();

            // Start at row index 3 and create groups of three obstacles with a space of two
            int positionX0 = 3;
            int countX0 = 0;
            while (countX0 < 67) {
                obstacles.add(new Obstacle(grid, 0, positionX0));
                obstacles.add(new Obstacle(grid, 0, positionX0 + 1));
                obstacles.add(new Obstacle(grid, 0, positionX0 + 2));
                positionX0 += 5;
                countX0 += 3;
            }


//            // Initialise Obstacles
//            obstacles = new ArrayList<>();
//            obstacles.add(new Obstacle(grid, 5, 5));
//            obstacles.add(new Obstacle(grid, 10, 5));
//            obstacles.add(new Obstacle(grid, 5, 10));

            int[] xPositions = {3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 17, 18, 19, 20, 21, 24, 25, 26, 27, 28, 31, 32, 33, 34, 35, 38, 39, 40, 41, 42, 45, 46, 47, 48, 49, 52, 53, 54, 55, 56, 59, 60, 61, 62, 63};


            // For each x position
            for (int x : xPositions) {
                int positionX = 3;
                int countX = 0;
                while (countX < 67) {
                    obstacles.add(new Obstacle(grid, x, positionX));
                    obstacles.add(new Obstacle(grid, x, positionX + 1));
                    obstacles.add(new Obstacle(grid, x, positionX + 2));
                    positionX += 5;
                    countX += 3;
                }
            }

            // Place the finish cell after the grid is filled and the player's position is initialised
            int finishColumn;
            int finishRow;
            do {
                finishColumn = (int) (Math.random() * COLUMNS);
                finishRow = (int) (Math.random() * ROWS);
            } while ((finishColumn == 0 && finishRow == 0) || (finishColumn == gemColumn && finishRow == gemRow)); // Ensure finish doesn't spawn at player's starting position or gem position
            finishCell = new Cell(finishColumn, finishRow);
            finishCell.getStyleClass().add("finish");
            grid.add(finishCell, finishColumn, finishRow);

            // Initialise currentCell after the grid has been filled
            // Initialise Player
            Player playerUno = new Player(25, 25, 10, 1, 10, 0);
            ka.playerUnosCell = grid.getCell(playerUno.getCoordY(), playerUno.getCoordY());


            // Initialize currentCell after the grid has been filled
            ka.currentCell = grid.getCell(0, 0);


            // Add background image, grid, and gem count label to the root StackPane
            root.getChildren().addAll(grid, vbox);

            // Colour cells
            // Define cells with coordinates and colors
            cell.colorCell(70, 70, "red");
            cell.colorCell(75, 75, "yellow");


            generateGems(grid, 5); // Replace 5 with the number of gems you want to generate
            // create scene and set to stage

            scene.getStylesheets().add(new File("src/main/resources/css/application.css").toURI().toString());
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch(Exception e){
            e.printStackTrace();

        }


    }

    // Place the gem after the grid is filled and the player's position is initialized
    private void generateGems(Grid grid, int numberOfGems) {
        for (int i = 0; i < numberOfGems; i++) {
            int gemColumn;
            int gemRow;
            do {
                gemColumn = (int) (Math.random() * COLUMNS);
                gemRow = (int) (Math.random() * ROWS);
            } while ((gemColumn == 0 && gemRow == 0) || grid.getCell(gemColumn, gemRow).getUserData() != null); // Ensure gem doesn't spawn at player's starting position or on another gem


            Gem gem = new Gem(gemColumn, gemRow);
            grid.add(gem, gemColumn, gemRow);
        }
    }

    public void selectStage(String stageName, VBox stageSelectionBox, VBox gameModeBox, StackPane root, Stage actionEvent, MediaPlayer mdv) {
        mdv.stop();
        root.getChildren().remove(stageSelectionBox);
        root.getChildren().remove(gameModeBox);

        gameController = new GameController();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        System.out.println("Stage selected: " + stageName);
        String musicFilePath;
        switch (stageName) {
            case "Seoul":
                musicFilePath = "/music/pokemon.mp3";
                break;
            case "Athens":
                musicFilePath = "/music/pokemon.mp3";
                break;
            case "Dublin":
                musicFilePath = "/music/pokemon.mp3";
                break;
            case "Istanbul":
                musicFilePath = "/music/pokemon.mp3";
                break;
            case "Vilnius":
                musicFilePath = "/music/pokemon.mp3";
                break;

            default:
                // Handle default case if necessary
                break;
        }
        Media gameMusic = new Media(new File("src/main/resources/music/pokemon.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(gameMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set the music to loop continuously
        mediaPlayer.play(); // Start playing the new background music
        // This is where you would transition to the actual game play scene
        // For now, just printing out the selection
        System.out.println("You have selected the stage: " + stageName);

        // You might want to hide the stage selection screen and display the game screen, like so:
        root.setVisible(false);
        root.getChildren().removeAll(buttonBox, this.gameModeBox);
        loadGameScreen(stageName,actionEvent);
    }

    public EventHandler<ActionEvent> showPlayerModeSelection(Stage actionEvent, VBox buttonBox, StackPane root, MediaPlayer mdv) {
        gameController = new GameController();
        root.getChildren().removeAll(buttonBox );
        Button btnOnePlayer = gameController.createButton("SinglePlay", event -> this.showStageSelectionScreen(actionEvent,mdv));
        Button btnTwoPlayer = gameController.createButton("MultiPlay", event -> this.showStageSelectionScreen(actionEvent,mdv));
        Button backToMenu = gameController.createButton("Back", event -> this.gameView.showInitialScreen(primaryStage));
        backToMenu.setMinWidth(gameController.BUTTON_WIDTH);
        backToMenu.setMaxWidth(gameController.BUTTON_WIDTH);
        backToMenu.setStyle("-fx-font-size: 24px;");

        // Create the game mode selection box if not already created
        if (gameModeBox == null) {
            gameModeBox = new VBox(20, btnOnePlayer, btnTwoPlayer,backToMenu);
            gameModeBox.setAlignment(Pos.CENTER);
        }
        // Add the game mode box to the root stack pane, making it visible
        if (!root.getChildren().contains(gameModeBox)) {
            root.getChildren().add(gameModeBox);
        }

        // Make the game mode selection box visible
        gameModeBox.setVisible(true);
        // Create and configure the scene
        root.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case DOWN:
                    if (btnOnePlayer.isFocused()) {
                        btnTwoPlayer.requestFocus();
                    } else if (btnTwoPlayer.isFocused()) {
                        backToMenu.requestFocus();
                    } else {
                        btnOnePlayer.requestFocus(); // Wrap around to the first button
                    }

                    break;
                case UP:
                    if (btnOnePlayer.isFocused()) {
                        btnTwoPlayer.requestFocus();
                    } else if (btnTwoPlayer.isFocused()) {
                        backToMenu.requestFocus();
                    } else {
                        btnOnePlayer.requestFocus(); // Wrap around to the first button
                    }

                    break;
                case ENTER:
                    if (btnOnePlayer.isFocused()) {
                        btnOnePlayer.fire();
                    } else if (btnTwoPlayer.isFocused()) {
                        btnTwoPlayer.fire();
                    } else if (backToMenu.isFocused()) {
                        backToMenu.fire();
                    }
                    break;
                default:
                    break;
            }
        });
        return null;
        };

    public void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
    }
}
