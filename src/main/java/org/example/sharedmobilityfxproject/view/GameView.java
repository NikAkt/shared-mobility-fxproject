package org.example.sharedmobilityfxproject.view;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
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
import org.example.sharedmobilityfxproject.model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.Main;
import org.example.sharedmobilityfxproject.model.Cell;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

import java.util.*;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.sharedmobilityfxproject.model.Player;
import javafx.animation.PauseTransition;

public class GameView {

    // **** Class call ****
    public Timer timer;
    // ****JavaFX load****
    public VBox gameModeBox;
    public Main main;
    public boolean isMetroSceneActive = false;
    public VBox buttonBox;
    public Button getGameStartbtn;
    public Button ExitBtn;
    public Button gameCreditbtn;
    public Button stageBtn;
    public MediaPlayer mediaPlayer;
    public HBox topRow;
    public HBox bottomRow;
    public Stage primaryStage;
    public VBox stageSelectionBox;
    public static Label gemCountLabel;

    // **** Resources of Main Game ****
    ProgressBar staminaBar;
    ProgressBar co2Bar;
    Label staminaLabel;
    Label co2Label;
    // **** Variables Setting ****
    // Label to keep track of gem count

    //**** Cell ****
    //Finish cell
    public Cell finishCell;

    // **** Obstacles ****
    // List to keep track of all obstacles
    public List<Obstacle> obstacles;
    public Grid grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);
    public Player playerUno;
    // Gem count
    static int gemCount = 0;
    // Carbon footprint
    int carbonFootprint = 0;

    //**** Stage Clear Flags ****
    private Map<String, Boolean> stageClearFlags;

    // **** Stamina ****
    double staminagauge;

    {
        staminagauge = 100;
    }

    double co2Gauge;

    {
        co2Gauge = 0;
    }

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint
    //**** Game Over Listener ****
    private GameOverListener gameOverListener;
    public static final double BUTTON_WIDTH = 200;

    // **** Font Setting ****
    public Font titleFont = Font.loadFont(getClass().getResourceAsStream("/font/blueShadow.ttf"), 70);
    public Font creditFont = Font.loadFont(getClass().getResourceAsStream("/font/blueShadow.ttf"), 50);
    public Font contentFont = Font.loadFont(getClass().getResourceAsStream("/font/blueShadow.ttf"), 25);
    public Font btnFont = Font.loadFont(getClass().getResourceAsStream("/font/blueShadow.ttf"), 15);
    // From MAIN OF MERGE STARTS

    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;
    private static final int ROWS = 80;
    private static final int COLUMNS = 120;
    private static final double WIDTH = 1300;
    private static final double HEIGHT = 680;

    private Scene scene;
    private Scene metroScene;
    private StackPane metroLayer;
    private Grid metroGrid;
    public Media bgv = new Media(new File("src/main/resources/videos/opening.mp4").toURI().toString());
    public Image logoImage = new Image(new File("src/main/resources/images/Way_Back_Home.png").toURI().toString());
    public MediaPlayer bgmediaPlayer = new MediaPlayer(bgv);
    public MediaView mediaView = new MediaView(bgmediaPlayer);
    public ImageView imageView = new ImageView(logoImage);
    // Boolean flag to track if the game has finished
    boolean gameFinished = false;
    private ScrollPane scrollPane;

    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;

    public GameView(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeStageClearFlags();

        //initialise the stamina bar
        this.staminaBar = new ProgressBar(1.0);
        this.staminaLabel = new Label("Stamina: 100%");

        //initialise the co2 bar
        this.co2Bar = new ProgressBar(0);
        this.co2Label = new Label("CO2: 0");
    }

    /**
     * Initializes the stage clear flags for each stage in the game.
     * The flags are stored in a HashMap where the key is the stage name and the value is a boolean indicating whether the stage has been cleared.
     * By default, only the "Dublin" stage is set to cleared (true), all other stages are not cleared (false).
     */
    private void initializeStageClearFlags() {
        stageClearFlags = new HashMap<>();
        stageClearFlags.put("Dublin", true);
        stageClearFlags.put("Athens", false);
        stageClearFlags.put("Seoul", false);
        stageClearFlags.put("Vilnius", false);
        stageClearFlags.put("Istanbul", false);
    }

    public boolean isStageCleared(String stage) {
        return stageClearFlags.getOrDefault(stage, false);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Sets up the main menu of the game.
     * This includes the creation and styling of the game start, game credit, and exit buttons.
     * The buttons are added to a VBox which is then added to a StackPane along with the media view and image view.
     * The media view is set to play indefinitely and the image view is set to preserve its original aspect ratio.
     * The scene is then created with the StackPane as its root and a size of 1496x1117.
     */
    public void setupMainMenu() {

        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100);
        bgmediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgmediaPlayer.play();

        getGameStartbtn = createButton("Game Start");
        gameCreditbtn = createButton("Game Credit");
        ExitBtn = createButton("Exit");
        gameCreditbtn.setFocusTraversable(true);
        gameCreditbtn.setFocusTraversable(true);
        ExitBtn.setFocusTraversable(true);

        applyButtonStyles(getGameStartbtn, false);
        applyButtonStyles(gameCreditbtn, false);
        applyButtonStyles(ExitBtn, false);

        VBox buttonBox = new VBox(40, getGameStartbtn, gameCreditbtn, ExitBtn);
        buttonBox.setAlignment(Pos.CENTER);

        StackPane root = new StackPane(mediaView, imageView, buttonBox);
        StackPane.setAlignment(imageView, Pos.TOP_CENTER);
        StackPane.setMargin(imageView, new Insets(50, 0, 0, 0));
        StackPane.setAlignment(buttonBox, Pos.CENTER);

        scene = new Scene(root, WIDTH, HEIGHT);

    }

    /**
     * Sets up the game scene for a specific stage.
     * This includes setting up the game grid, CO2 and stamina bars, countdown timer, and gem count label.
     * The stage name is displayed at the top of the scene.
     * The scene is then created with a StackPane as its root and a size of WIDTH x HEIGHT.
     * The metro system is initialized and the scene is set to the primary stage.
     *
     * @param stageName The name of the stage for which the game scene is being set up.
     */
    public void setupGameScene(String stageName) {
        // **** Game Setup ****
        mainGameSetting();

        // **** Start Main Game ****
        StackPane mapStackPane = new StackPane();
        Scale scaleTransform = new Scale();
        scaleTransform.setX(0.8);
        scaleTransform.setY(0.8);
        grid.getTransforms().add(scaleTransform);

        mapStackPane.setPadding(new Insets(40));
        mapStackPane.setAlignment(Pos.BOTTOM_LEFT);
        mapStackPane.setMaxSize(1280, 720);


        // CO2 ProgressBar setup
        this.co2Bar = new ProgressBar(this.co2Gauge);
        this.co2Bar.setPrefWidth(500);  // Adjust the preferred width to fit the vertical layout
        this.co2Bar.setPrefHeight(40);  // Adjust the height accordingly
        this.co2Bar.setStyle("-fx-accent: red;");
        this.co2Bar.setRotate(270);

        // CO2 Label setup
        this.co2Label = new Label("CO2: " + this.co2Gauge);
        this.co2Label.setFont(new Font("Arial", 16));  // Set the font directly
        this.co2Label.setFont(contentFont);  // Assuming 'contentFont' is already defined elsewhere

        // VBox for vertical layout
        VBox co2VBox = new VBox();  // Add ProgressBar first, then the Label
        co2VBox.getChildren().add(this.co2Bar);
        co2VBox.getChildren().add(this.co2Label);
        VBox.setMargin(this.co2Bar, new Insets(200, 0, 0, -200)); // Top margin of 100
        co2VBox.setAlignment(Pos.CENTER);  // Center the contents in the VBox
        VBox.setMargin(this.co2Label, new Insets(240, 0, 0, -190));  // Add some space between the bar and the label

        co2VBox.setPrefHeight(600);

        // "Stamina" text
        this.staminaLabel = new Label("Stamina:" +
                " " + this.staminagauge + "%");

        this.staminaLabel.setFont(javafx.scene.text.Font.font(14));
        this.staminaLabel.setFont(contentFont);

        this.staminaBar = new ProgressBar(staminagauge);
        this.staminaBar.setPrefWidth(1000);
        this.staminaBar.setPrefHeight(40);
        this.staminaBar.setStyle("-fx-accent: yellow;");

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
        IntegerProperty timeSeconds = new SimpleIntegerProperty(160);
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

        VBox gemContainer = new VBox(gemCountLabel);
        gemContainer.setAlignment(Pos.TOP_RIGHT);

        // StackPane setup
        StackPane stackRoot = new StackPane();
        stackRoot.setAlignment(Pos.TOP_RIGHT);
        stackRoot.setPadding(new Insets(0));

        StackPane.setMargin(mapStackPane, new Insets(40, 0, 45, 120));
        mapStackPane.getChildren().clear();
        mapStackPane.getChildren().add(grid);

        AnchorPane anchorRoot = new AnchorPane();


        AnchorPane.setTopAnchor(mapStackPane, 0.0);
        AnchorPane.setBottomAnchor(mapStackPane, 0.0);
        AnchorPane.setLeftAnchor(mapStackPane, 60.0);
        AnchorPane.setRightAnchor(mapStackPane, 0.0);


        AnchorPane.setBottomAnchor(staminaContainer, 10.0);
        AnchorPane.setLeftAnchor(staminaContainer, 0.0);
        AnchorPane.setRightAnchor(staminaContainer, 0.0);
        staminaContainer.setMaxWidth(Double.MAX_VALUE);


        AnchorPane.setTopAnchor(timeContainer, 20.0);
        AnchorPane.setLeftAnchor(timeContainer, 0.0);
        AnchorPane.setRightAnchor(timeContainer, 0.0);
        timeContainer.setMaxWidth(Double.MAX_VALUE);


        AnchorPane.setTopAnchor(co2VBox, 10.0);  // Top anchor with a margin
        AnchorPane.setLeftAnchor(co2VBox, 0.0);

        AnchorPane.setTopAnchor(gemContainer, 10.0);
        AnchorPane.setRightAnchor(gemContainer, 10.0);
        anchorRoot.getChildren().addAll(mapStackPane, co2VBox, staminaContainer, timeContainer, gemContainer);


        // Settings
        Image icon = new Image(String.valueOf(getClass().getResource("/images/icon.png")));
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle("Shared Mobility Application");
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);
        primaryStage.setResizable(false);
        scene = new Scene(anchorRoot, WIDTH, HEIGHT);


        // create scene and set to stage
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());
        initializeMetroSystem();

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Sets up the main game settings.
     * This includes stopping any currently playing video, starting the game background music, and displaying a popup dialog.
     * The popup dialog provides a notice and a start message to the player, and includes a "Let's Rock!" button to close the dialog and start the game.
     * After the dialog is closed, a timer is started with a delay of 5 seconds.
     */
    public void mainGameSetting() {
        //Stop the video
        mediaView.getMediaPlayer().stop();

        //BGM Stop
        Media gameMusic1 = new Media(new File("src/main/resources/music/mainBGM.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(gameMusic1);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

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

            closeButton.setFont(btnFont);
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

    /**
     * Creates a button for a specific game stage.
     * The button is labeled with the stage name and decorated with an image.
     * If the stage has not been cleared yet, the image is desaturated and an "X" mark is added on top of it.
     * The button's content display is set to TOP, meaning the text (stage name) is displayed below the image.
     *
     * @param stage      The name of the stage for which the button is being created.
     * @param stageImage The image to be used as the button's graphic.
     * @return The created button.
     */
    public Button createStageButton(String stage, ImageView stageImage) {
        stageBtn = new Button(stage);
        boolean isStageCleared = stageClearFlags.getOrDefault(stage, false);

        if (!isStageCleared) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-1);
            stageImage.setEffect(colorAdjust);

            Label xMark = new Label("X");
            xMark.setFont(new Font("Arial", 100));
            xMark.setStyle("-fx-text-fill: red;");

            StackPane buttonGraphic = new StackPane(stageImage, xMark);
            stageBtn.setGraphic(buttonGraphic);
        } else {
            stageBtn.setGraphic(stageImage);
        }

        stageBtn.setContentDisplay(ContentDisplay.TOP);


        return stageBtn;
    }

    // Stage Clear Method: Set cleared flag to true
    //
    public void clearStage(String stage) {
        stageClearFlags.put(stage, true);
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


    /**
     * Returns the CSS style string for a normal button.
     * The style includes the font family, font size, background color, and text color.
     *
     * @return A string representing the CSS style for a normal button.
     */
    public String normalButtonStyle() {
        return "-fx-font-family: 'blueShadow'; -fx-font-size: 24px; -fx-background-color: rgba(255, 255, 240, 0.7); -fx-text-fill: black;";
    }


    public String focusedButtonStyle() {
        return "-fx-font-family: 'blueShadow'; -fx-font-size: 24px; -fx-background-color: dodgerblue; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";
    }


    //*** Please check the method below ****
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void initializeMetroSystem() {
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
        metroStop under1 = new metroStop(2, 30);
        metroGrid.add(under1, 2, 30);
//        playerUno.initCell(metroGrid);
        Label testLabel = new Label("Metro System Active");

        metroLayer.getChildren().addAll(metroGrid, testLabel);
        metroScene = new Scene(metroLayer, WIDTH, HEIGHT);
        metroScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/application.css")).toExternalForm());

    }

    public void switchSceneToMetro() {
        if (isMetroSceneActive) {
            primaryStage.setScene(metroScene);


        }
        if (!isMetroSceneActive) {
            primaryStage.setScene(scene);

        }
    }

    /**
     * Displays the stage selection screen of the game.
     * This includes playing the background media, creating stage selection buttons for each stage, and setting up key event handlers.
     * The stage selection buttons are added to two HBox containers (topRow and bottomRow), which are then added to a VBox (stageSelectionBox).
     * The stageSelectionBox is added to a StackPane along with the media view.
     * The scene is then created with the StackPane as its root and a size of 1496x1117.
     * If an exception occurs during the setup, it is caught and its stack trace is printed.
     */
    public void showStageSelectionScreen() {
        //bring the Stage in gameView
        try {
            System.out.println("ShowStageSelectionScreen in GameView");
            bgmediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmediaPlayer.play();

            List<Button> allButtons = new ArrayList<>();
            if (topRow == null && bottomRow == null) {
                topRow = new HBox(10);
                bottomRow = new HBox(10);
                topRow.setAlignment(Pos.CENTER);
                bottomRow.setAlignment(Pos.CENTER);

                String[] topStages = {"Dublin", "Athens", "Seoul"};
                String[] bottomStages = {"Vilnius", "Istanbul"};

                //List up the stages
                for (String stage : topStages) {
                    ImageView stageImage = createStageImage(stage);
                    Button stageButton = createStageButton(stage, stageImage);
                    stageButton.setFont(btnFont);
                    stageButton.setOnAction(event -> {
                    });
                    topRow.getChildren().add(stageButton);
                    allButtons.add(stageButton);
                }

                for (String stage : bottomStages) {
                    ImageView stageImage = createStageImage(stage);
                    Button stageButton = createStageButton(stage, stageImage);
                    stageButton.setFont(btnFont);
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

    /**
     * Retrieves all stage selection buttons from the top and bottom rows.
     * The buttons are collected from the children of the topRow and bottomRow HBox containers.
     *
     * @return A list of all stage selection buttons.
     */
    public List<Button> getAllStageButtons() {
        List<Button> buttons = new ArrayList<>();
        buttons.addAll(topRow.getChildren().stream().map(node -> (Button) node).collect(Collectors.toList()));
        buttons.addAll(bottomRow.getChildren().stream().map(node -> (Button) node).collect(Collectors.toList()));
        return buttons;
    }

    /**
     * Creates an ImageView for a specific game stage.
     * The image is selected based on the stage name and is loaded from the resources directory.
     * The image is then set to a specific height and width.
     * If the image cannot be found, an IllegalStateException is thrown.
     *
     * @param stageName The name of the stage for which the image is being created.
     * @return An ImageView containing the stage image.
     * @throws IllegalStateException If the image for the stage cannot be found.
     */
    public ImageView createStageImage(String stageName) {
        String imagePath = switch (stageName) {
            case "Seoul" -> "/images/seoul.jpg";
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
        imageView.setFitHeight(200);
        imageView.setFitWidth(230);
        return imageView;
    }

    public int getRows() {
        return ROWS;
    }

    public int getColumns() {
        return COLUMNS;
    }

    /**
     * Transitions from the stage selection screen to the main game page.
     * This includes stopping any currently playing media, loading and playing the main game background music.
     * The actual transition to the game play scene is not implemented in this method.
     */
    public void selectStage() {
        //This function is describing between Mapselection and MainGamePage

        //Video Stop(MineCraft)
        mediaView.getMediaPlayer().stop();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        //New Music Load
        Media gameMusic1 = new Media(new File("src/main/resources/music/mainBGM.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(gameMusic1);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set the music to loop continuously
        mediaPlayer.play(); // Start playing the new background music

        // This is where you would transition to the actual game play scene
        // For now, just printing out the selection
        // You might want to hide the stage selection screen and display the game screen, like so:

    }


    public static void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
    }

    /**
     * Displays a game over dialog.
     * The dialog is a modal window with no title bar, containing a label with a game over message.
     * The dialog is displayed for 7 seconds, after which it is automatically closed.
     * The dialog can also be closed by clicking anywhere within it.
     */

    private void gameOver(Stage primarOveryStage, String stageName) {
        //Result Calculating
        Text resultLabel;
        if (gemCount >= 5) {
            resultLabel = new Text("Stage Clear!");
            resultLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: green;");
            setNextStageFlag(stageName);
            resultLabel.setFont(contentFont);
        } else {
            resultLabel = new Text("Stage Fail");
            resultLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
            resultLabel.setFont(contentFont);
        }

        // GameOver method
        int scorePerGem = 100;
        double scorePenaltyPer10CO2 = 10;
        int finalScore = (gemCount * scorePerGem) - (int) (co2Gauge / 10.0 * scorePenaltyPer10CO2);

        Text gameOverGem = new Text("Total Gems: " + gemCount);
        Text gameOverCo2 = new Text("Total CO2: " + String.format("%.1f", co2Gauge));

        gameOverGem.setStyle("-fx-font-size: 20px; -fx-text-fill: black;");
        gameOverGem.setFont(contentFont);
        gameOverCo2.setStyle("-fx-font-size: 20px; -fx-text-fill: red;");
        gameOverCo2.setFont(contentFont);

        Text finalScoreLabel = new Text("Final Score: " + finalScore);
        finalScoreLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: blue;");
        finalScoreLabel.setFont(contentFont);

        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.initStyle(StageStyle.UNDECORATED); // 창 제목 표시줄 제거

        Text gameOverLabel = new Text("GAME OVER" + "\nGoodbye " + stageName + "!");

        gameOverLabel.textAlignmentProperty().setValue(TextAlignment.CENTER);
        gameOverLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: red;");
        gameOverLabel.setFont(creditFont);
        // Layout
        VBox dialogVBox = new VBox(10, gameOverLabel, gameOverGem, gameOverCo2, finalScoreLabel);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setStyle("-fx-padding: 20; -fx-background-color: rgba(255, 255, 255, 0.8);");
        dialogVBox.getChildren().add(1, resultLabel);

        StackPane dialogPane = new StackPane(dialogVBox);
        dialogPane.setAlignment(Pos.CENTER);
        dialogPane.setStyle("-fx-padding: 20; -fx-background-color: rgba(255, 255, 255, 0.8);");

        Scene dialogScene = new Scene(dialogPane, 500, 400);

        dialog.setScene(dialogScene);
        dialog.show();

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> {
            dialog.close();
            if (gameOverListener != null) {
                gameOverListener.onGameOver();  // Notify the listener
            }
        });

        dialogVBox.getChildren().add(closeButton);
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
                    closeButton.setFont(btnFont);
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
                        " \n          Nick aktoudianakis" + "\n          MustaFa Yilmaz" + "\n          Eamonn Walsh" + "\n          and \n          Gyuwon Jung"

        );
        startMessageLabel.setWrapText(true);
        startMessageLabel.setAlignment(Pos.CENTER);
        startMessageLabel.setFont(contentFont);

        // Close Button
        Button closeButton = new Button("Close");
        if (contentFont != null) {
            closeButton.setFont(btnFont);
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

        // Scene and stage setup
        Scene dialogScene = new Scene(popupVbox);
        dialog.setOnShown(event -> {
            dialog.setX(this.primaryStage.getX() + this.primaryStage.getWidth() / 2 - dialog.getWidth() / 2);
            dialog.setY(this.primaryStage.getY() + this.primaryStage.getHeight() / 2 - dialog.getHeight() / 2);
        });
        dialog.setScene(dialogScene);
        dialog.showAndWait();
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
        noticeLabel.setPadding(new Insets(10, 0, 10, 0));
        noticeLabel.setAlignment(Pos.TOP_CENTER);

        Label messageLabel = new Label("This stage cannot be cleared yet.\nPlease clear the previous stages first.");
        messageLabel.setFont(Font.font(contentFont.getFamily(), FontWeight.NORMAL, 16));
        messageLabel.setWrapText(true);
        messageLabel.setAlignment(Pos.CENTER);

        Button closeButton = new Button("Close");
        closeButton.setFont(Font.font(btnFont.getFamily(), FontWeight.BOLD, 20));
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
        System.out.println("Updated Stamina: " + this.staminagauge + ", Progress: " + staminaFraction);
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

    private void setNextStageFlag(String currentStage) {
        List<String> stageNames = new ArrayList<>(stageClearFlags.keySet());
        for (int i = 0; i < stageNames.size(); i++) {
            if (stageNames.get(i).equals(currentStage)) {
                if (i + 1 < stageNames.size()) {
                    stageClearFlags.put(stageNames.get(i + 1), true);
                }
                break;
            }
        }

        // Check if all stages are cleared
        boolean allCleared = true;
        for (boolean flag : stageClearFlags.values()) {
            if (!flag) {
                allCleared = false;
                break;
            }
        }

        // If all stages are cleared, possibly do something or nothing
        if (allCleared) {
            // All stages are cleared, you may want to do something here or just leave it empty
        }
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

    public void setCo2Gauge(double co2Gauge) {
        this.co2Gauge = co2Gauge;
    }
}