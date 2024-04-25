package org.example.sharedmobilityfxproject.view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

import javafx.scene.input.KeyCode;

import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.sharedmobilityfxproject.controller.GameController;

import org.example.sharedmobilityfxproject.controller.SceneController;
import org.example.sharedmobilityfxproject.model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.Main;
import org.example.sharedmobilityfxproject.controller.MainController;
import org.example.sharedmobilityfxproject.model.Cell;

import java.io.*;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.sharedmobilityfxproject.model.Player;
import javafx.animation.PauseTransition;

public class GameView {

    // **** Class call ****
    public GameView gameView;
    public MainController mainController;
    public GameController ka;
    public Gem gem;
    public Obstacle obstacle;
    public Timer timer;
    // ****JavaFX load****
    public VBox gameModeBox;
    public Main main;
//    public boolean isMetroSceneActive = false;
    public VBox buttonBox;
    public Button getGameStartbtn;
    public Button gameLoadGamebtn;
    public Button ExitBtn;
    public Button gameCreditbtn;
    public Boolean flagLoadGame = false;
    public Button stageBtn;
    public StackPane root;
    public MediaPlayer mediaPlayer;
    public HBox topRow;
    public HBox bottomRow;
    public Stage primaryStage;
    public VBox stageSelectionBox;
    public static Label gemCountLabel;
    public static Label nearestGem;
    public static Label direction;
    public SceneController sceneController;

    // **** Variables Setting ****
    // Label to keep track of gem count

    //**** Cell ****
    //Finish cell
    public Cell finishCell;

    // **** Obstacles ****
    // List to keep track of all obstacles
    public List<Obstacle> obstacles;
    public Player playerUno;
    // Gem count
    static int gemCount = 10;
    public int gemX = 0;
    public int gemY = 0;
    public String toGem;
    // Carbon footprint
    int carbonFootprint = 0;

    //**** Stage Clear Flags ****
    private Map<String, Boolean> stageClearFlags;
    private BooleanProperty gameEndFlag = new SimpleBooleanProperty(false);

    //**** Game Controller ****
    public Stage gameOverDialog;
    public boolean isTimeOut;
    public boolean isGemCollectedEnough;
    public boolean isCO2Safe;
    // **** Stamina ****
    double staminagauge;
    public StackPane mainBackground;

    {
        staminagauge = 100;
    }

    double co2Gauge;

    {
        co2Gauge = 0;
    }

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint

    //Grid setting
    // Boolean flag to control hover cursor visibility

    //**** Game Over Listener ****
    public GameOverListener gameOverListener;
    public boolean gameOverFlag = false;
    public static final double BUTTON_WIDTH = 200;
    public Button gameEndbtn;
    IntegerProperty timeSeconds;
    // **** Font Setting ****
    Font titleFont = Font.loadFont("file:src/main/resources/font/blueShadow.ttf", 70);
    Font creditFont = Font.loadFont("file:src/main/resources/font/blueShadow.ttf", 50);
    Font contentFont = Font.loadFont("file:src/main/resources/font/blueShadow.ttf", 25);
    Font btnFont = Font.loadFont("file:src/main/resources/font/blueShadow.ttf", 15);

    // From MAIN OF MERGE STARTS

    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    private int ROWS = 80;
    private int COLUMNS = 120;
    private static final double WIDTH = 1300;
    private static final double HEIGHT = 680;

    public Grid grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);

    private Scene scene;
//    private Scene metroScene;
 //   private StackPane metroLayer;
   // private Grid metroGrid;
    public Media bgv = new Media(new File("src/main/resources/videos/opening.mp4").toURI().toString());
    public Image logoImage = new Image(new File("src/main/resources/images/Way_Back_Home.png").toURI().toString());
    public MediaPlayer bgmediaPlayer = new MediaPlayer(bgv);
    public MediaView mediaView = new MediaView(bgmediaPlayer);
    public ImageView imageView = new ImageView(logoImage);
    public ArrayList gemlist;

    // Boolean flag to track if the game has finished
    boolean gameFinished = false;
    private ScrollPane scrollPane;

    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;

    private String viewType;
    public Scale scale;

    // **** Resources of Main Game ****
    ProgressBar staminaBar;
    ProgressBar co2Bar;
    Label staminaLabel;
    Label co2Label;

    public GameView(Stage primaryStage) {
        this.staminaBar = new ProgressBar(1.0);
        this.staminaLabel = new Label("Stamina: 100%");
        //initialise the co2 bar
        this.co2Bar = new ProgressBar(0);
        this.co2Label = new Label("CO2: 0");
        this.primaryStage = primaryStage;
        this.flagLoadGame = false;

        initializeStageClearFlags();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Sets up the main game settings.
     * This includes stopping any currently playing video, starting the game background music, and displaying a popup dialog.
     * The popup dialog provides a notice and a start message to the player, and includes a "Let's Rock!" button to close the dialog and start the game.
     * After the dialog is closed, a timer is started with a delay of 5 seconds.
     */
    public void mainGameSetting() {
        if(!flagLoadGame){
            //Stop the video
            mediaView.getMediaPlayer().stop();

            //BGM Stop
            Media gameMusic1 = new Media(new File("src/main/resources/music/mainBGM.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(gameMusic1);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
            mediaPlayer.setVolume(0.7);
            decreaseVolume();

            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            dialog.initStyle(StageStyle.UNDECORATED);

            // Start Pop up
            VBox popupVbox = new VBox(10);
            popupVbox.setAlignment(Pos.CENTER);
            popupVbox.setPrefWidth(400);
            popupVbox.setPrefHeight(700);
            popupVbox.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");

            Label noticeLabel = new Label("Notice");
            noticeLabel.setFont(titleFont);
            noticeLabel.setAlignment(Pos.TOP_CENTER);

            Label startMessageLabel = new Label(
                    "Eco and Friendly, who cherish the environment, are roaming the city." +
                            " collecting Gems needed for their journey. " +
                            "\nTry to gather the Gems in the most eco-friendly way possible."
            );
            startMessageLabel.setWrapText(true);
            startMessageLabel.setAlignment(Pos.CENTER);
            startMessageLabel.setFont(contentFont);
            // Close Button
            Button closeButton = new Button("Let's Rock!");
            if (contentFont != null) {

                closeButton.setFont(this.btnFont);
            } else {
                System.out.println("Failed to load custom font. Using default font.");
            }
            closeButton.requestFocus();
            popupVbox.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    closeButton.fire();
                }
            });
            closeButton.setPrefSize(160, 80); // Set the preferred size of the button
            closeButton.setOnAction(e -> {
                dialog.close();

                PauseTransition wait = new PauseTransition(Duration.seconds(5));
                wait.setOnFinished(event -> System.out.println("5 Seconds past"));
                wait.play();
            });
            // Add labels and close button to VBox
            popupVbox.getChildren().addAll(noticeLabel, startMessageLabel, closeButton);
            VBox.setMargin(closeButton, new Insets(20, 0, 0, 0));

            // Scene and stage setup
            Scene dialogScene = new Scene(popupVbox);
            dialog.setScene(dialogScene);
            dialog.showAndWait();

        }

    }


    public void setupMainMenu() {
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100);
        bgmediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmediaPlayer.play();

        getGameStartbtn = createButton("Game Start");
        gameLoadGamebtn = createButton("Load Game");
        gameCreditbtn = createButton("Game Credit");
        ExitBtn = createButton("Exit");

        getGameStartbtn.setFocusTraversable(true);
        gameLoadGamebtn.setFocusTraversable(true);
        gameCreditbtn.setFocusTraversable(true);
        ExitBtn.setFocusTraversable(true);

        applyButtonStyles(getGameStartbtn, true);
        applyButtonStyles(gameLoadGamebtn, false);
        applyButtonStyles(gameCreditbtn, false);
        applyButtonStyles(ExitBtn, false);

        VBox buttonBox = new VBox(40, getGameStartbtn,gameLoadGamebtn, gameCreditbtn, ExitBtn);
        buttonBox.setAlignment(Pos.CENTER);
        VBox.setMargin(buttonBox, new Insets(90, 0, 0, 0));

        StackPane root = new StackPane(mediaView, imageView, buttonBox);
        StackPane.setAlignment(imageView, Pos.TOP_CENTER);
        StackPane.setMargin(imageView, new Insets(50, 0, 0, 0));
        StackPane.setAlignment(buttonBox, Pos.CENTER);

        scene = new Scene(root, WIDTH, HEIGHT);
    }

    public void setupGameScene(String stageName) {

        mainGameSetting();
        StackPane root = new StackPane();
        scene = new Scene(root, WIDTH, HEIGHT);

        if (scale == null) {
            scale = new Scale(3, 3);  // Set the initial scale TODO: scale changed here
        } else {
            scale.setX(3);
            scale.setY(3);
        }

        boolean scaleExists = grid.getTransforms().stream().anyMatch(transform -> transform instanceof Scale);
        if (!scaleExists) {
            grid.getTransforms().add(scale);  // Only add scale if not already present
        }

        // Game Lable Setting

        // CO2 ProgressBar setup
        this.co2Bar = new ProgressBar(this.co2Gauge);
        this.co2Bar.setPrefWidth(400);
        this.co2Bar.setPrefHeight(40);  // Adjust the height accordingly
        this.co2Bar.setStyle("-fx-accent: red;");
        this.co2Bar.setRotate(270);
        this.co2Bar.setStyle("-fx-border-color: yellow; -fx-border-width: 2px; -fx-padding: 5px;");
        this.co2Bar.setStyle("-fx-accent: red; -fx-opacity: 0.5;");  // 50% opacity

        // CO2 Label setup
        this.co2Label = new Label("CO2: " + this.co2Gauge);
        this.co2Label.setFont(new Font("Arial", 16));  // Set the font directly
        this.co2Label.setFont(contentFont);  // Assuming 'contentFont' is already defined elsewhere
        this.co2Label.setStyle("-fx-background-color: transparent; -fx-padding: 5px;");

        Label transportLabel = new Label("TAXI[T] / BUS[E]");
        transportLabel.setFont(contentFont);

        // VBox for vertical layout
        VBox co2VBox = new VBox(transportLabel,this.co2Bar, this.co2Label);  // Add ProgressBar first, then the Label
        VBox.setMargin(transportLabel, new Insets(0, 0, 120, 50));

        co2VBox.setPrefHeight(600);
        VBox.setMargin(this.co2Bar, new Insets(150, 0, 0, -150)); // Top margin of 100
        VBox.setMargin(this.co2Label, new Insets(200, 0, 0, 0));  // Add some space between the bar and the label
        StackPane.setMargin(co2VBox, new Insets(0, 0, 0, 5)); // Left margin of 5
        co2VBox.setAlignment(Pos.CENTER_LEFT);

        // "Stamina" text
        this.staminaLabel = new Label("Stamina:" +
                " " + this.staminagauge + "%");

        this.staminaLabel.setFont(javafx.scene.text.Font.font(14));
        this.staminaLabel.setFont(contentFont);

        this.staminaBar = new ProgressBar(staminagauge);
        this.staminaBar.setPrefWidth(1000);
        this.staminaBar.setPrefHeight(40);
        this.staminaBar.setStyle("-fx-accent: yellow; -fx-opacity: 0.5;");

        // Stamina container setup
        VBox staminaContainer = new VBox(staminaLabel, staminaBar);
        staminaContainer.setAlignment(Pos.BOTTOM_CENTER);
        StackPane.setMargin(staminaContainer, new Insets(0, 0, 20, 0)); // Add margin at the bottom if needed

        // Stage Name
        Text mapNameTest = new Text("Welcome to " + stageName);
        mapNameTest.setFont(contentFont);

        // Time countdown
        Label timeLabel = new Label();
        timeLabel.setAlignment(Pos.TOP_CENTER);

        // Countdown logic
        timeSeconds = new SimpleIntegerProperty(30
        ); // TODO: Timing
        new Timeline(
                new KeyFrame(
                        Duration.seconds(timeSeconds.get()),
                        event -> gameOver(primaryStage, stageName),
                        new KeyValue(timeSeconds, 0)
                )

        ).play();

        timeSeconds.addListener((obs, oldVal, newVal) -> {
            timeLabel.setText("Time left: " + newVal + "s");
            timeLabel.setFont(javafx.scene.text.Font.font(40));
            timeLabel.setFont(contentFont);
        });
        timeLabel.setAlignment(Pos.CENTER);

        VBox timeContainer = new VBox(mapNameTest, timeLabel);
        timeContainer.setAlignment(Pos.TOP_CENTER);
        StackPane.setMargin(timeContainer, new Insets(20, 0, 0, 0));

        // Create label for gem count
        gemCountLabel = new Label("Gem Count: " + gemCount);
        gemCountLabel.setFont(contentFont);
        gemCountLabel.setAlignment(Pos.TOP_LEFT);
        gemCountLabel.setPadding(new Insets(10));

        nearestGem = new Label("Nearest Gem: " +gemX+" "+gemY);
        nearestGem.setFont(contentFont);
        nearestGem.setAlignment(Pos.TOP_LEFT);
        nearestGem.setPadding(new Insets(10));

        direction= new Label("Move: " +toGem);
        direction.setFont(contentFont);
        direction.setAlignment(Pos.TOP_LEFT);
        direction.setPadding(new Insets(10));
        VBox gemContainer = new VBox(gemCountLabel,nearestGem,direction);
        gemContainer.setAlignment(Pos.TOP_RIGHT);

        // Settings
        Image icon = new Image(new File("src/main/resources/images/icon.png").toURI().toString());
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Shared Mobility Application");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);

        // Add background image, grid, and gem count label to the root StackPane
        root.getChildren().addAll(grid, gemContainer, timeContainer, co2VBox, staminaContainer);

        // create scene and set to stage
        File cssFile = new File("src/main/resources/css/application.css");
        scene.getStylesheets().add("file:" + cssFile);
       // initializeMetroSystem();
    }

    public Button getBtnExit() {
        System.out.println("getBtnExit in GameView");
        return ExitBtn;
    }

    public Button getGameCreditbtn() {
        System.out.println("GameCreditBtn in GameView");
        return gameCreditbtn;
    }

    public Button getGameStartbtn() {
        System.out.println("GameStartBtn in GameView");
        return getGameStartbtn;
    }


    public Button createStageButton(String stage, ImageView stageImage) {
        Button stageBtn = new Button(stage);
        boolean isStageCleared = stageClearFlags.getOrDefault(stage, false);
        System.out.println("Current isStageCleared: " + stage + " " + isStageCleared);

        // If the stage is cleared, just set the image without any marks
        stageBtn.setGraphic(stageImage);
        stageBtn.setContentDisplay(ContentDisplay.TOP);
        return stageBtn;
    }


    public Button createButton(String text) {
        Button button = new Button(text);
        if (this.btnFont != null) {
            button.setFont(this.btnFont);
        } else {
            System.out.println("Failed to load custom font. Using default font.");
        }
        button.setMinWidth(BUTTON_WIDTH);
        button.setMaxWidth(BUTTON_WIDTH);
        button.setFocusTraversable(true);
        //hover colour change
        button.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                button.setStyle(focusedButtonStyle());
            } else {
                button.setStyle(normalButtonStyle());
            }
        });


        return button;
    }


    public String normalButtonStyle() {
        return "-fx-font-family: 'blueShadow'; -fx-font-size: 24px; -fx-background-color: rgba(255, 255, 240, 0.7); -fx-text-fill: black;";
    }


    public String focusedButtonStyle() {
        return "-fx-font-family: 'blueShadow'; -fx-font-size: 24px; -fx-background-color: dodgerblue; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";
    }


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void increaseGemCount() {
        gemCount++;
        updateGemCountLabel();
    }

//    private void initializeMetroSystem() {
//        metroLayer = new StackPane();
//        metroLayer.setStyle("-fx-background-color: #CCCCCC;");
//        metroGrid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);
//        // Initialize metro cells
//        for (int row = 0; row < ROWS; row++) {
//            for (int column = 0; column < COLUMNS; column++) {
//                Cell cell = new Cell(column, row);
//                // Make sure cells are visible
//                metroGrid.add(cell, column, row);
//            }
//        }
//        metroStop under1 = new metroStop(2, 30);
//        metroGrid.add(under1, 2, 30);
////        playerUno.initCell(metroGrid);
//        Label testLabel = new Label("Metro System Active");
//
//        metroLayer.getChildren().addAll(metroGrid, testLabel);
//        metroScene = new Scene(metroLayer, WIDTH, HEIGHT);
//        File cssFile = new File("src/main/resources/css/application.css");
//        metroScene.getStylesheets().add("file:" + cssFile);
//
//    }


    /*
  swithc
  if flag true
      swith\ch scene
      flag = !flag
   */
//    public void switchSceneToMetro() {
//        if (isMetroSceneActive) {
//            primaryStage.setScene(metroScene);
//
//
//        }
//        if (!isMetroSceneActive) {
//            primaryStage.setScene(scene);
//
//        }
//    }


    public void showStageSelectionScreen() {
        //bring the Stage in gameView
        try {
            gameEndFlag.set(false);
            System.out.println("ShowStageSelectionScreen in GameView");
            bgmediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmediaPlayer.play();


            List<Button> allButtons = new ArrayList<>();
            if (topRow == null && bottomRow == null) {
                topRow = new HBox(10);
                bottomRow = new HBox(10);
                topRow.setAlignment(Pos.CENTER);
                bottomRow.setAlignment(Pos.CENTER);


                String[] topStages = {"Manhattan", "Dublin","Tokyo", "Athens"};
                String[] bottomStages = {"Vilnius", "Istanbul"};


                //List up the stages
                for (String stage : topStages) {
                    ImageView stageImage = createStageImage(stage);
                    Button stageButton = createStageButton(stage, stageImage);
                    stageButton.setFont(this.btnFont);
                    stageButton.setOnAction(event -> {
                    });
                    topRow.getChildren().add(stageButton);
                    allButtons.add(stageButton);
                }


                for (String stage : bottomStages) {
                    ImageView stageImage = createStageImage(stage);
                    Button stageButton = createStageButton(stage, stageImage);
                    stageButton.setFont(this.btnFont);
                    stageButton.setOnAction(event -> {
                    });
                    bottomRow.getChildren().add(stageButton);
                    allButtons.add(stageButton);
                }
            }
            stageSelectionBox = new VBox(100, topRow, bottomRow);
            stageSelectionBox.setAlignment(Pos.CENTER);


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


            stageSelectionBox.requestFocus();
            stageSelectionBox.setAlignment(Pos.CENTER);


            //Add new StageSelectionBox
            StackPane root = new StackPane(mediaView, stageSelectionBox);
            StackPane.setAlignment(stageSelectionBox, Pos.CENTER);
            scene = new Scene(root, WIDTH, HEIGHT);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public ImageView createStageImage(String stageName) {
        String imagePath = switch (stageName) {
            case "Seoul" -> "/images/seoul.jpg";
            case "Manhattan" -> "/images/manhattan.png";
            case "Tokyo" -> "/images/tokyo.png";
            case "Athens" -> "/images/athens.png";
            case "Dublin" -> "/images/dublin.png";
            case "Vilnius" -> "/images/vilnius.png";
            case "Istanbul" -> "/images/istanbul.png";
            case "Home" -> "/images/home.png";
            case "Back" -> "/images/home.png";
            default -> "/images/Way_Back_Home.png.png";
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
    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
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

    public static void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
    }
    public void updateGemLoc() {
        nearestGem.setText("Nearest Gem: " +gemX+" "+gemY);
    }
    public void updateGemDirec() {
        direction.setText("Move: " +toGem);
    }
    /**
     * Displays a game over dialog.
     * The dialog is a modal window with no title bar, containing a label with a game over message.
     * The dialog is displayed for 7 seconds, after which it is automatically closed.
     * The dialog can also be closed by clicking anywhere within it.
     */

    private void gameOver(Stage primaryStage, String stageName) {
        if(!flagLoadGame){
            System.out.println("Game Over endFLag" + gameEndFlag);
            isTimeOut = timeSeconds.get() <= 0;
            isGemCollectedEnough = gemCount >= 5;
            isCO2Safe = co2Gauge < 100;

            // Calculate result based on game conditions
            Text resultLabel;
            if (isCO2Safe && isGemCollectedEnough && isTimeOut) {
                resultLabel = new Text("Stage Clear - Requirements met!");
                resultLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: green;");
            } else {
                resultLabel = new Text("Stage Fail - Requirements not met");
                resultLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
            }
            resultLabel.setFont(contentFont);


            // Score calculation
            int scorePerGem = 100;
            double scorePenaltyPer10CO2 = 10;
            int finalScore = (gemCount * scorePerGem) - (int) (co2Gauge / 10.0 * scorePenaltyPer10CO2);


            Text gameOverGem = new Text("Total Gems: " + gemCount);
            Text gameOverCo2 = new Text("Total CO2: " + String.format("%.1f", co2Gauge));
            gameOverGem.setStyle("-fx-font-size: 20px; -fx-text-fill: lightgreen;");
            gameOverGem.setFont(contentFont);
            gameOverCo2.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
            gameOverCo2.setFont(contentFont);


            Text finalScoreLabel = new Text("Final Score: " + finalScore);
            finalScoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: blue;");
            finalScoreLabel.setFont(contentFont);


            gameOverDialog = new Stage();
            gameOverDialog.initModality(Modality.APPLICATION_MODAL);
            gameOverDialog.initOwner(primaryStage);
            gameOverDialog.initStyle(StageStyle.UNDECORATED);


            Text gameOverLabel = new Text("GAME OVER\nGoodbye " + stageName + "!");
            gameOverLabel.textAlignmentProperty().setValue(TextAlignment.CENTER);
            gameOverLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: red;");
            gameOverLabel.setFont(titleFont);


            VBox dialogVBox = new VBox(10, gameOverLabel, gameOverGem, gameOverCo2, finalScoreLabel, resultLabel);
            dialogVBox.setAlignment(Pos.CENTER);
            dialogVBox.setStyle("-fx-padding: 20; -fx-background-color: rgba(255, 255, 255, 0.8);");


            Scene dialogScene = new Scene(new StackPane(dialogVBox), 500, 400);
            gameOverDialog.setScene(dialogScene);
            gameOverDialog.show();


            gameEndbtn = new Button("Close");
            gameEndbtn.setFont(btnFont);
            dialogVBox.getChildren().add(gameEndbtn);
            gameEndFlag.set(true);
            gameEndbtn.requestFocus();
            System.out.println("Game Over endFlag: " + gameEndFlag.get());

        }else{
            flagLoadGame = false;
        }

        gameEndbtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                gameEndbtn.fire();
            }
        });
    }

    public BooleanProperty gameEndFlagProperty() {
        return gameEndFlag;
    }


    /**
     * Applies a specific style to a button based on its focus state.
     * The style includes the font family, font size, background color, text color, and text shadow.
     * If the button is focused, the background color is set to dodgerblue, the text color is set to white, and a drop shadow effect is applied.
     * If the button is not focused, the background color is set to a semi-transparent white, the text color is set to black, and no drop shadow effect is applied.
     * A listener is added to the button's focused property to reapply the style whenever the focus state changes.
     *
     * @param button  The button to which the style is being applied.
     * @param focused The focus state of the button.
     */
    public void applyButtonStyles(Button button, boolean focused) {

        String fontFamily = btnFont.getName(); // Get the font name from the Font object
        String fontSize = "24px";
        String backgroundColor = focused ? "dodgerblue" : "rgba(255, 255, 240, 0.7)";
        String textColor = focused ? "white" : "black";
        String textShadow = focused ? "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);" : "";

        button.setStyle(String.format("-fx-font-family: '%s'; -fx-font-size: %s; -fx-background-color: %s; -fx-text-fill: %s; %s",
                fontFamily, fontSize, backgroundColor, textColor, textShadow));
        button.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            applyButtonStyles(button, isNowFocused);
        });
    }

    /**
     * Initializes the stage clear flags for each stage in the game.
     * The flags are stored in a HashMap where the key is the stage name and the value is a boolean indicating whether the stage has been cleared.
     * By default, only the "Dublin" stage is set to cleared (true), all other stages are not cleared (false).
     */
    private ArrayList<String> stageOrder;

    public boolean isStageCleared(String stage) {
        return stageClearFlags.getOrDefault(stage, false);

    }

    public Map<String, Boolean> getStageClearFlags() {
        return stageClearFlags;
    }

    public void setStageClearFlags(Map<String, Boolean> stageClearFlags) {
        this.stageClearFlags = stageClearFlags;
    }

    /**
     * Displays an educational popup with a random message from a list of messages stored in a JSON file.
     * The popup is a modal dialog with a title, a random educational message, and a close button.
     * After the dialog is closed, a timer is started with a delay of 5 seconds.
     * If the JSON file cannot be read or the list of messages is empty, an exception is caught and its stack trace is printed.
     *
     * @throws Exception If there is an error reading the JSON file or displaying the popup.
     */
    public void educationalPopup() {
        try {

            FileInputStream ins = new FileInputStream("src/main/resources/data/educational_messages.json");
            InputStreamReader inr = new InputStreamReader(ins, "UTF-8");
            ObjectMapper mapper = new ObjectMapper();

            // Read the JSON content as a List
            List<String> messages = mapper.readValue(inr, new TypeReference<List<String>>() {
            });

            // Ensure the list is not empty and select a random message
            if (!messages.isEmpty()) {
                Random rand = new Random();
                String randomMessage = messages.get(rand.nextInt(messages.size()));

                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(this.primaryStage);
                dialog.initStyle(StageStyle.UNDECORATED);

                VBox popupVbox = new VBox(60);
                popupVbox.setAlignment(Pos.CENTER);
                popupVbox.setPrefWidth(450);
                popupVbox.setPrefHeight(700);
                popupVbox.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");

                Label noticeLabel = new Label("Environmental \n      Fun Fact!");
                noticeLabel.setFont(creditFont);
                noticeLabel.setAlignment(Pos.TOP_CENTER);

                Label educationalMsgLabel = new Label();
                educationalMsgLabel.setText(randomMessage);
                educationalMsgLabel.setWrapText(true);
                educationalMsgLabel.setAlignment(Pos.CENTER);
                educationalMsgLabel.setFont(contentFont);

                // Close Button
                Button closeButton = new Button("Close");
                if (contentFont != null) {
                    closeButton.setFont(this.btnFont);
                } else {
                    System.out.println("Failed to load custom font. Using default font.");
                }
                closeButton.setPrefSize(160, 80);
                closeButton.setOnAction(e -> {
                    dialog.close();
                    PauseTransition wait = new PauseTransition(Duration.seconds(5));
                    wait.setOnFinished(event -> System.out.println("5 Seconds past"));
                    wait.play();
                });
                popupVbox.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ENTER) {
                        closeButton.fire();
                    }
                });
                // Add labels and close button to VBox
                popupVbox.getChildren().addAll(noticeLabel, educationalMsgLabel, closeButton);
                VBox.setMargin(closeButton, new Insets(20, 0, 0, 0));
                Scene dialogScene = new Scene(popupVbox);
                dialog.setScene(dialogScene);
                dialog.showAndWait();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /// Game Credit

    /**
     * Displays a credit popup dialog.
     * The dialog is a modal window with no title bar, containing a label with the title "Game Credit", a message with the credits, and a close button.
     * After the dialog is closed, a timer is started with a delay of 5 seconds.
     * The dialog is positioned in the center of the primary stage.
     */
    public void showCredit() {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.primaryStage);
        dialog.initStyle(StageStyle.UNDECORATED);

        VBox popupVbox = new VBox(10);
        popupVbox.setAlignment(Pos.CENTER);
        popupVbox.setPrefWidth(450);
        popupVbox.setPrefHeight(700);
        popupVbox.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");

        Label noticeLabel = new Label("Game Credit");
        noticeLabel.setFont(creditFont);
        noticeLabel.setAlignment(Pos.TOP_CENTER);

        Label startMessageLabel = new Label(
                "COMP30820 -JAVA Programming\n" +
                        "          My Dearest team mates\n          OilWrestlingLovers :)" +
                        " \n          Nick aktoudianakis" + "\n          MustaFa Yilmaz" + "\n          Eamonn Walsh" + "\n        Matas Martinaitis\"    and \n          Gyuwon Jung"

        );
        startMessageLabel.setWrapText(true);
        startMessageLabel.setAlignment(Pos.CENTER);
        startMessageLabel.setFont(contentFont);

        // Close Button
        Button closeButton = new Button("Close");
        if (contentFont != null) {
            closeButton.setFont(this.btnFont);
        } else {
            System.out.println("Failed to load custom font. Using default font.");
        }
        closeButton.setPrefSize(160, 80); // Set the preferred size of the button
        closeButton.setOnAction(e -> {
            dialog.close(); // Close the popup
            // Start the timer after the popup is closed
            PauseTransition wait = new PauseTransition(Duration.seconds(5));
            wait.setOnFinished(event -> System.out.println("5 Seconds past"));
            wait.play();
        });
        // Add labels and close button to VBox
        popupVbox.getChildren().addAll(noticeLabel, startMessageLabel, closeButton);
        VBox.setMargin(closeButton, new Insets(20, 0, 0, 0)); // Set the margin for the close button

        popupVbox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                closeButton.fire();
            }
        });
        // Scene and stage setup
        Scene dialogScene = new Scene(popupVbox);
        dialog.setOnShown(event -> {
            dialog.setX(this.primaryStage.getX() + this.primaryStage.getWidth() / 2 - dialog.getWidth() / 2);
            dialog.setY(this.primaryStage.getY() + this.primaryStage.getHeight() / 2 - dialog.getHeight() / 2);
        });
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }


    // To pass it to the controller
    public int getGemCount() {
        return gemCount;
    }

    // This is temporary, later on the function that updates the gem count
    public void setGemCoount(int gemCountNew) {
        gemCount = gemCountNew;
        updateGemCountLabel();
    }


    public List<Button> getAllStageButtons() {
        List<Button> buttons = new ArrayList<>();
        buttons.addAll(topRow.getChildren().stream().map(node -> (Button) node).collect(Collectors.toList()));
        buttons.addAll(bottomRow.getChildren().stream().map(node -> (Button) node).collect(Collectors.toList()));
        return buttons;
    }

    //**** Transport Co2 Methods ****
    public void increaseCo2Gauge(double amount) {
        this.co2Gauge += amount;
        this.updateCo2Label();
        this.co2Bar.setProgress(co2Gauge / 100.0);
    }

    public void updateCo2Label() {
        this.co2Label.setText("CO2: " + String.format("%.1f", this.co2Gauge));

    }

    public double getCo2Gauge() {
        return this.co2Gauge;
    }

    public void gameFail() {
        System.out.println("Game Fail");
        gameOver(primaryStage, "Stage");
    }

    /**
     * Displays a stage alert dialog with a predefined message.
     * The dialog is a modal window with no title bar, containing a label with the title "Notice", a predefined message, and a close button.
     * The dialog can be closed by clicking the close button or pressing the ENTER key.
     * The dialog is owned by the primary stage of the application.
     *
     * @param message The message to be displayed in the dialog. This parameter is not used in the current implementation.
     */
    public void showStageAlert(String message) {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage); // primaryStage needs to be accessible
        dialog.initStyle(StageStyle.UNDECORATED);

        VBox popupVbox = new VBox();
        popupVbox.setPrefWidth(300);
        popupVbox.setPrefHeight(200);

        Label noticeLabel = new Label("Notice");
        noticeLabel.setFont(Font.font(titleFont.getFamily(), FontWeight.BOLD, 20));
        noticeLabel.setPadding(new Insets(10, 0, 0, 0));
        noticeLabel.setAlignment(Pos.TOP_CENTER);

        Label messageLabel = new Label("This stage cannot be accessed yet.\nPlease complete the previous stages first.");
        messageLabel.setFont(Font.font(contentFont.getFamily(), FontWeight.NORMAL, 16));
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);

        Button closeButton = new Button("Close");
        closeButton.setFont(Font.font(this.btnFont.getFamily(), FontWeight.BOLD, 20));
        closeButton.setOnAction(e -> dialog.close());
        closeButton.setPadding(new Insets(0)); // Set padding to 0

        VBox.setVgrow(closeButton, Priority.ALWAYS);
        closeButton.setPrefWidth(Double.MAX_VALUE);
        closeButton.setAlignment(Pos.BOTTOM_CENTER);

// Ensure there's space between the label and the close button
        VBox.setMargin(closeButton, new Insets(20, 0, 0, 0));

        popupVbox.getChildren().addAll(noticeLabel, messageLabel, closeButton);
        closeButton.requestFocus();
        popupVbox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                closeButton.fire();
            }
        });
        Scene dialogScene = new Scene(popupVbox);
        dialog.setScene(dialogScene);
        dialog.showAndWait();


    }

    public void setCo2Gauge(double co2Gauge) {
        this.co2Gauge = co2Gauge;
    }

    /**
     * Increments the gem count by one and updates the gem count label.
     * This method is typically called when a player collects a gem in the game.
     */
    public void incrementGemCount() {
        gemCount++;
        updateGemCountLabel();
        educationalPopup();
    }

    /**
     * Updates the stamina value and the corresponding UI elements.
     * The method sets the new stamina value, calculates the fraction of the maximum stamina it represents,
     * updates the progress bar to reflect this fraction, and updates the stamina label with the new stamina value.
     * It also prints the updated stamina value and progress to the console.
     *
     * @param newStamina The new stamina value to be set.
     */
    public void updateStamina(double newStamina) {
        this.staminagauge = newStamina;
        double staminaFraction = newStamina / 100.0;
        this.staminaBar.setProgress(staminaFraction);
        this.staminaLabel.setText("Stamina: " + newStamina + "%");

    }

    /**
     * Plays the 'no stamina' sound effect.
     * This method is typically called when the player's stamina is depleted in the game.
     * The sound effect file is located in the 'src/main/resources/music' directory.
     */
    public void playNoStaminaSound() {
        Media no_stamina_effect = new Media(new File("src/main/resources/music/no_stamina.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(no_stamina_effect);
        mediaPlayer.play();
    }

    /**
     * Increments the gem count by one and updates the gem count label.
     * This method is typically called when a player collects a gem in the game.
     */


    private void decreaseVolume() {
        double currentVolume = mediaPlayer.getVolume();
        double newVolume = Math.max(0.0, currentVolume - 0.2); // Decrease volume by 0.2, ensuring it doesn't go below 0
        mediaPlayer.setVolume(newVolume);
    }

    private void initializeStageClearFlags() {
        stageClearFlags = new LinkedHashMap<>();
        stageOrder = new ArrayList<>(Arrays.asList("Manhattan", "Dublin", "Tokyo","Athens","Vilnius", "Istanbul"));
        for (String city : stageOrder) {
            stageClearFlags.put(city, false);
        }
        // Assuming Dublin is already cleared as per your requirement
        stageClearFlags.put("Manhattan", true);
        stageClearFlags.put("Dublin", false); //TODO: manually set the first stage to cleared, need to change this
        stageClearFlags.put("Tokyo", false);
    }

    public Button getEndStage() {
        return gameEndbtn;
    }

    public void gemCountReset() {
        gemCount = 0;
        updateGemCountLabel();
    }


    public void setNextStageCleared(String currentStageName) {
        int currentIndex = stageOrder.indexOf(currentStageName);
        if (currentIndex >= 0 && currentIndex < stageOrder.size() - 1) {
            String nextStageName = stageOrder.get(currentIndex + 1);

            stageClearFlags.put(nextStageName, true);
        }
    }


    private boolean isNextStage(String stage) {
        // Find the first stage that is not cleared
        for (String s : stageOrder) {
            if (!stageClearFlags.get(s)) {
                return s.equals(stage);
            }
        }
        return false;
    }

    /**
     * Returns the number of rows in the grid.
     *
     * @return The number of rows in the grid.
     */
    public int getROWS() {
        return ROWS;
    }

    /**
     * Returns the number of columns in the grid.
     *
     * @return The number of columns in the grid.
     */
    public int getCOLUMNS() {
        return COLUMNS;
    }

    /**
     * Sets the number of rows in the grid.
     *
     * @param ROWSnew The new number of rows in the grid.
     */
    public void setROWS(int ROWSnew) {
        ROWS = ROWSnew;
    }

    /**
     * Sets the number of columns in the grid.
     *
     * @param COLUMNSnew The new number of columns in the grid.
     */
    public void setCOLUMNS(int COLUMNSnew) {
        COLUMNS = COLUMNSnew;
    }

    public ButtonBase getGameLoadbtn() {
        System.out.println("getEndStage in GameView");
        return gameLoadGamebtn;
    }

    private Object loadSerializedObject(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return ois.readObject();
        }
    }

    public void loadGameSaveWindow() {
        Stage saveDialog = new Stage();
        saveDialog.initModality(Modality.APPLICATION_MODAL);
        saveDialog.initOwner(this.primaryStage);
        saveDialog.initStyle(StageStyle.UNDECORATED);

        VBox popupVbox = new VBox(10);
        popupVbox.setAlignment(Pos.CENTER);
        popupVbox.setPrefWidth(450);
        popupVbox.setPrefHeight(700);
        popupVbox.setStyle("-fx-padding: 20; -fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");

        Label noticeLabel = new Label("Game Load");
        noticeLabel.setFont(creditFont);
        noticeLabel.setAlignment(Pos.TOP_CENTER);

        Button loadSavedGameButton1 = createButton("Saved Game 1");
        applyButtonStyles(loadSavedGameButton1,true);
        loadSavedGameButton1.requestFocus();
        loadSavedGameButton1.setFocusTraversable(true);

        loadSavedGameButton1.setOnAction(e -> {
            File saveFile = new File("gameSave.ser"); // Ensure this is the correct path to your .ser file
//            try {
//                // Load the serialized object from the .ser file
//                GameSave savedGame = (GameSave) loadSerializedObject(saveFile);
//
//
//                System.out.println("Game loaded successfully!");
//            } catch (IOException | ClassNotFoundException ex) {
//                ex.printStackTrace();
//                // Handle the error scenario, maybe show an error message on the UI
//                System.out.println("Failed to load the game.");
//            }
        });

        // Close Button
        Button loadCloseButton = new Button("Close");
        if (contentFont != null) {
            loadCloseButton.setFont(this.btnFont);
        } else {
            System.out.println("Failed to load custom font. Using default font.");
        }
        loadCloseButton.setPrefSize(120, 60); // Set the preferred size of the button
        // Add labels and close button to VBox
        popupVbox.getChildren().addAll( noticeLabel, loadSavedGameButton1, loadCloseButton);
        VBox.setMargin(loadCloseButton, new Insets(20, 0, 0, 0)); // Set the margin for the close button

        // Scene and stage setup
        Scene dialogScene = new Scene(popupVbox);
        saveDialog.setOnShown(event -> {
            saveDialog.setX(this.primaryStage.getX() + this.primaryStage.getWidth() / 2 - saveDialog.getWidth() / 2);
            saveDialog.setY(this.primaryStage.getY() + this.primaryStage.getHeight() / 2 - saveDialog.getHeight() / 2);
        });
        popupVbox.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loadCloseButton.fire();
            }
        });
        loadCloseButton.setOnAction(e -> saveDialog.close());
        saveDialog.setScene(dialogScene);
        saveDialog.show();

    }

}