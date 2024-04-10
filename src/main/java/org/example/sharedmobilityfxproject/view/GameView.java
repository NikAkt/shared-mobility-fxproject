package org.example.sharedmobilityfxproject.view;

import javafx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.controller.KeyboardActionController;

import org.example.sharedmobilityfxproject.controller.SceneController;
import org.example.sharedmobilityfxproject.model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.Main;
import org.example.sharedmobilityfxproject.controller.GameController;
import org.example.sharedmobilityfxproject.model.Cell;
import org.example.sharedmobilityfxproject.model.tranportMode.Bus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.sharedmobilityfxproject.model.Player;
import javafx.animation.PauseTransition;



public class GameView {

    // **** Class call ****
    public GameView gameView;
    public GameController gameController;
    public KeyboardActionController ka;
    public Gem gem;
    public Obstacle obstacle;
    public Timer timer;

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
    public SceneController sceneController;
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


    // **** Stamina ****
    int staminagauge;

    {
        staminagauge = 0;
    }

    int co2Gauge;

    {
        co2Gauge = 0;
    }

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint

    //Grid setting
    // Boolean flag to control hover cursor visibility
//    boolean showHoverCursor = true;
//    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
//    private static final int ROWS = 30;
//    private static final int COLUMNS = 60;
//    private static final double WIDTH = 800;
//    private static final double HEIGHT = 600;

    // **** Font Setting ****
    public Font titleFont = Font.loadFont(getClass().getResourceAsStream("/font/blueShadow.ttf"), 70);
    public Font contentFont = Font.loadFont(getClass().getResourceAsStream("/font/blueShadow.ttf"), 25);
    public Font btnFont = Font.loadFont(getClass().getResourceAsStream("/font/blueShadow.ttf"), 15);


    // From MAIN OF MERGE STARTS

    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    private static final int ROWS = 80;
    private static final int COLUMNS = 120;
    private static final double WIDTH = 1300;
    private static final double HEIGHT = 680;


    public ArrayList<int[]> busStopCoordinates;
    public ArrayList<int[]> obstacleCoordinates;

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

    // From MAIN OF MERGE ENDING

    public GameView(Grid grid) {
        ka = new KeyboardActionController(this, grid, obstacles, busStopCoordinates, finishCell);
        gameController = new GameController();
//        gameController.initializeObstacles(grid);
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
        Button btnStartGame = gameController.createButton("Game Start", event -> showPlayerModeSelection(primaryStage, buttonBox, root, bgmediaPlayer));
        // Create and configure the "Exit" button
        Button btnExit = gameController.createButton("Exit", event -> primaryStage.close());
        //Font Set

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
        Scene scene = new Scene(this.root, 1496, 1117); // Use the same size as the image for a full background
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

    public void showStageSelectionScreen(Stage actionEvent, MediaPlayer mdv) {
        try {
            List<Button> allButtons = new ArrayList<>();
            if (topRow == null && bottomRow == null) {
                topRow = new HBox(10);
                bottomRow = new HBox(10);
                topRow.setAlignment(Pos.CENTER);
                bottomRow.setAlignment(Pos.CENTER);

                String[] topStages = {"Dublin", "Athens", "Seoul", "Istanbul"};
                String[] bottomStages = {"Vilnius", "Back"};

                for (String stage : topStages) {
                    ImageView stageImage = createStageImage(stage);
                    Button stageButton = gameController.createStageButton(stage, stageImage, stageSelectionBox, gameModeBox, root, actionEvent, mdv);
                    topRow.getChildren().add(stageButton);
                    allButtons.add(stageButton);
                }

                for (String stage : bottomStages) {
                    ImageView stageImage = createStageImage(stage);
                    Button stageButton = gameController.createStageButton(stage, stageImage, stageSelectionBox, gameModeBox, root, actionEvent, mdv);
                    bottomRow.getChildren().add(stageButton);
                    allButtons.add(stageButton);
                }
            }

            stageSelectionBox = new VBox(100, topRow, bottomRow);
            stageSelectionBox.setAlignment(Pos.CENTER);
            gameModeBox.setVisible(false);

            stageSelectionBox.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    Node focusedNode = stageSelectionBox.getScene().getFocusOwner();
                    if (focusedNode instanceof Button StageFocusedButton) {
                        if (allButtons.contains(StageFocusedButton)) {
                            StageFocusedButton.fire();
                        }
                    }
                } else if (event.getCode() == KeyCode.DOWN) {
                    System.out.println("move Down");
                } else if (event.getCode() == KeyCode.UP) {
                    System.out.println("move Up");
                }
            });

            stageSelectionBox.requestFocus(); // 키 이벤트를 받을 수 있도록 포커스 설정

            stageSelectionBox.setAlignment(Pos.CENTER);
            gameModeBox.setVisible(false);

            root.getChildren().removeAll(buttonBox, gameModeBox);
            root.getChildren().add(stageSelectionBox);

        } catch (Exception e) {
            e.printStackTrace();
        }


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
        Image is = new Image(new File("src/main/resources/" + imagePath).toURI().toString());
        if (is == null) {
            throw new IllegalStateException("Cannot find image for stage: " + stageName);
        }
        ImageView imageView = new ImageView(is);
        imageView.setFitHeight(200); // 이미지 높이를 설정
        imageView.setFitWidth(230);  // 이미지 너비를 설정
        return imageView;
    }

    // This is where the game screen is loaded MAIN WILL BE HERE
    public void loadGameScreen(String stageName, Stage primaryStage) {

        try {
//            final Stage dialog = new Stage();
//            dialog.initModality(Modality.APPLICATION_MODAL);
//            dialog.initOwner(primaryStage);
//            dialog.initStyle(StageStyle.UNDECORATED);
//
//            // Start Pop up
//            //right left margin 20px 씩
//            //width 200 height 180
//            VBox popupVbox = new VBox(10);
//            popupVbox.setAlignment(Pos.CENTER);
//            popupVbox.setPrefWidth(400);
//            popupVbox.setPrefHeight(700);
//            popupVbox.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");
//
//            Label noticeLabel = new Label("Notice");
//            noticeLabel.setFont(titleFont);
//            noticeLabel.setAlignment(Pos.TOP_CENTER);
//
//            Label startMessageLabel = new Label(
//                    "Eco and Friendly, who cherish the environment, are roaming the city." +
//                            " collecting Gems needed for their journey. " +
//                            "\nTry to gather the Gems in the most eco-friendly way possible."
//            );
//            startMessageLabel.setWrapText(true);
//            startMessageLabel.setAlignment(Pos.CENTER);
//            startMessageLabel.setFont(contentFont);
//
//            // Close Button
//            Button closeButton = new Button("Let's Rock!");
//            if (contentFont != null) {
//                // 로드된 폰트를 버튼에 적용합니다.
//                closeButton.setFont(btnFont);
//            } else {
//                System.out.println("Failed to load custom font. Using default font.");
//            }
//            closeButton.setPrefSize(160, 80); // Set the preferred size of the button
//            closeButton.setOnAction(e -> {
//                dialog.close(); // Close the popup
//                // Start the timer after the popup is closed
//                PauseTransition wait = new PauseTransition(Duration.seconds(5));
//                wait.setOnFinished(event -> System.out.println("5 Seconds past"));
//                wait.play();
//            });
//
//            // Add labels and close button to VBox
//            popupVbox.getChildren().addAll(noticeLabel, startMessageLabel, closeButton);
//            VBox.setMargin(closeButton, new Insets(20, 0, 0, 0)); // Set the margin for the close button
//
//// Scene and stage setup
//            Scene dialogScene = new Scene(popupVbox);
//            dialog.setScene(dialogScene);
//            dialog.showAndWait();
//
//
//            // **** Start Pop up ****
//            BorderPane borderPane = new BorderPane();
//
//            // CO2 Parameter Bar (Vertical)
//            ProgressBar co2Bar = new ProgressBar(co2Gauge); // Example value, adjust as needed
//            co2Bar.setPrefWidth(60);
//            co2Bar.setPrefHeight(600); // Adjust the height as needed
//            co2Bar.setStyle("-fx-accent: red;"); // Set the fill color to red
//            VBox.setMargin(co2Bar, new Insets(0, 0, 0, 80)); // 상단 마진 설정
//            // Wrap CO2 bar in VBox to align it vertically
//            VBox co2Container = new VBox(co2Bar);
//            co2Container.setAlignment(Pos.CENTER);
//
//            // Stamina Parameter
//            ProgressBar staminaParameter = new ProgressBar(staminagauge); // Set to full stamina
//            staminaParameter.setPrefHeight(60);
//            staminaParameter.setPrefWidth(1200);
//            staminaParameter.setStyle("-fx-accent: yellow;"); // Set the fill color to red
//
//            // "Stamina" 텍스트 생성
////            Text staminaText = new Text("Stamina");
////            staminaText.setFont(javafx.scene.text.Font.font(14)); // 폰트 크기 설정
//
//            // Wrap CO2 bar in VBox to align it vertically
//            VBox staminaContainer = new VBox();
////            staminaContainer.getChildren().add(staminaText);
//            staminaContainer.getChildren().add(staminaParameter); // 상단에 텍스트 추가
//            VBox.setMargin(staminaContainer, new Insets(50, 0, 0, 0)); // 상단 마진 설정
//            staminaContainer.setAlignment(Pos.CENTER); // 컨테이너 내의 항목을 중앙 정렬
//
//
//            // Time countdown
//            Label timeLabel = new Label();
//            timeLabel.setAlignment(Pos.TOP_CENTER);
//
//            // Countdown logic
//            IntegerProperty timeSeconds = new SimpleIntegerProperty(180);
//            new Timeline(
//                    new KeyFrame(
//                            Duration.seconds(timeSeconds.get()),
//                            event -> gameOver(primaryStage),
//                            new KeyValue(timeSeconds, 0)
//                    )
//            ).play();
//
//            timeSeconds.addListener((obs, oldVal, newVal) -> {
//                timeLabel.setText("Time left: " + newVal + "s");
//                timeLabel.setFont(javafx.scene.text.Font.font(40));
//            });
//            timeLabel.setAlignment(Pos.CENTER);
//
//            // Placeholder for the map
//            Label mapPlaceholder = new Label();
//            mapPlaceholder.setPrefSize(1200, 600);
//            mapPlaceholder.setAlignment(Pos.CENTER);
//            mapPlaceholder.setStyle("-fx-border-color: black; -fx-border-width: 2; -fx-border-style: solid;");


            // Add all to the layout


            // Set this layout in the scene
//            Scene scene = new Scene(borderPane, 1496, 1117);
//            primaryStage.setScene(scene);

            // Create a StackPane to hold all elements
//            Stage gridStage = new Stage();
//            gridStage.initOwner(primaryStage);
            StackPane root = new StackPane();
            Scene scene = new Scene(root, WIDTH, HEIGHT); // Assuming WIDTH and HEIGHT are declared and initialized
//            gridStage.setScene(scene);
//            Stage gridStage = new Stage();
//            gridStage.initOwner(primaryStage);
//            StackPane root = new StackPane();
//            Scene scene = new Scene(root);
//            primaryStage.setTitle("Welcome To " + stageName);
//            primaryStage.setFullScreen(true);
//            primaryStage.show();
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

            ArrayList busStops  = new ArrayList<busStop>();
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
//            carbonFootprintLabel = new Label("Carbon Footprint: " + String.format("%.1f", carbonFootprint));
//            carbonFootprintLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: red;");
//            carbonFootprintLabel.setAlignment(Pos.TOP_LEFT);
//            carbonFootprintLabel.setPadding(new Insets(10));

            // Create a VBox to hold the gem count label
//            VBox vbox = new VBox(gemCountLabel, carbonFootprintLabel);
//            vbox.setAlignment(Pos.TOP_LEFT);
            System.out.println("I am here");

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

            ka.obstacles = obstacles;
            ka.busStopCoordinates = busStopCoordinates;
            ka.finishCell = finishCell;
            ka.grid = grid;



            // Add background image, grid, and gem count label to the root StackPane
            root.getChildren().addAll(grid);
//            System.out.println(busS1.getX());
            // create scene and set to stage
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

            // Schedule the bus to move every second
            Timeline busMovementTimeline = new Timeline(new KeyFrame(Duration.seconds(.1), event -> {
                busStop targetBusStop = busman.nextStop(); // Assuming this method correctly returns the next bus stop
                if (!busman.isWaiting){


                    gameController.moveBusTowardsBusStop(grid, busman, targetBusStop, ka, playerUno);

                    // Here's the updated part
                    if (ka.onBus) {
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

            // Player animation
            Timeline playerMovementTimeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {

            }));
        } catch (Exception e) {
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

        Media gameMusic = new Media(new File("src/main/resources/music/mainBGM.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(gameMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set the music to loop continuously
        mediaPlayer.play(); // Start playing the new background music
        // This is where you would transition to the actual game play scene
        // For now, just printing out the selection
        System.out.println("You have selected the stage: " + stageName);
        // You might want to hide the stage selection screen and display the game screen, like so:
        root.setVisible(false);
        root.getChildren().removeAll(buttonBox, this.gameModeBox);
        loadGameScreen(stageName, actionEvent);

    }

    public EventHandler<ActionEvent> showPlayerModeSelection(Stage actionEvent, VBox buttonBox, StackPane root, MediaPlayer mdv) {
        gameController = new GameController(); // #TODO: why is this here?
        root.getChildren().removeAll(buttonBox);
        Button btnOnePlayer = gameController.createButton("SinglePlay", event -> this.showStageSelectionScreen(actionEvent, mdv));
        Button btnTwoPlayer = gameController.createButton("MultiPlay", event -> this.showStageSelectionScreen(actionEvent, mdv));
        Button backToMenu = gameController.createButton("Back", event -> showInitialScreen(primaryStage));
        backToMenu.setMinWidth(gameController.BUTTON_WIDTH);
        backToMenu.setMaxWidth(gameController.BUTTON_WIDTH);
        backToMenu.setStyle("-fx-font-size: 24px;");

        // Create the game mode selection box if not already created
        if (gameModeBox == null) {
            gameModeBox = new VBox(20, btnOnePlayer, btnTwoPlayer, backToMenu);
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
    }

    ;

    public static void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
    }

    private void gameOver(Stage primarOveryStage) {
        // GameOver method
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.initStyle(StageStyle.UNDECORATED); // 창 제목 표시줄 제거

        Label gameOverLabel = new Label("GAME OVER, Life is not Easy, Let's Go to Code!");
        gameOverLabel.setAlignment(Pos.CENTER);
        gameOverLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: red;");

        StackPane dialogPane = new StackPane(gameOverLabel);
        dialogPane.setAlignment(Pos.CENTER);
        dialogPane.setStyle("-fx-padding: 20; -fx-background-color: rgba(255, 255, 255, 0.8);");

        Scene dialogScene = new Scene(dialogPane, 800, 400);

        dialog.setScene(dialogScene);
        dialog.show();

        // 5초 후 팝업 닫기
        PauseTransition delay = new PauseTransition(Duration.seconds(7));
        delay.setOnFinished(e -> dialog.close());
        delay.play();

        // 아무곳이나 클릭하면 팝업 닫기
        dialogScene.setOnMouseClicked(e -> dialog.close());
    }


}
