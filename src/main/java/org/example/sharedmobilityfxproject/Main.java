package org.example.sharedmobilityfxproject;

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
public class Main extends Application {
    private Button btnStartGame;
    private Button btnExit;
    private MediaPlayer mediaPlayer;
    //Static Box size setting
    private static final double BUTTON_WIDTH = 200;
    private Stage primaryStage;
    private VBox buttonBox;
    private VBox gameModeBox;
    private VBox stageSelectionBox;
    private StackPane root;
    private HBox topRow;
    private HBox bottomRow;
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the image
            this.primaryStage = primaryStage;
            InputStream is = getClass().getResourceAsStream("/images/waybackHome.png");
            if (is == null) {
                System.err.println("Cannot find image file");
                return; // Exit the method if the image file can't be found
            }
            Image backgroundImage = new Image(is);

            // Create an ImageView that is a node for displaying the Image
            ImageView backgroundImageView = new ImageView(backgroundImage);

            // Make the ImageView the size of the image
            backgroundImageView.setPreserveRatio(false);
            backgroundImageView.setFitWidth(primaryStage.getWidth());
            backgroundImageView.setFitHeight(primaryStage.getHeight());

            // Initialize the Media and MediaPlayer for background music
            Media media = new Media(new File("src/main/resources/music/waybackHome.mp3").toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Play the BGM in a loop
            mediaPlayer.play(); // Start playing the BGM

            // Create and configure the "Game Start" button
            btnStartGame = createButton("Game Start", this::showPlayerModeSelection);

            // Create and configure the "Exit" button
            btnExit = createButton("Exit", event -> primaryStage.close());

            // Create a VBox for buttons
            buttonBox = new VBox(20, this.btnStartGame, this.btnExit);
            buttonBox.setAlignment(Pos.CENTER); // Align buttons to center

            // Center the VBox in the StackPane
            StackPane.setAlignment(buttonBox, Pos.CENTER);

            this.root = new StackPane(backgroundImageView,buttonBox);

            // Set up the scene with the StackPane and show the stage
            Scene scene = new Scene(root, 1496, 1117); // Use the same size as the image for a full background
            setupKeyControls(scene);

            primaryStage.setTitle("WayBack Home");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Set up key event handling for button selection using arrow keys and Enter key
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case DOWN:
                        if (this.btnStartGame.isFocused()) {
                            this.btnExit.requestFocus();
                        }
                        break;
                    case UP:
                        if (this.btnExit.isFocused()) {
                            this.btnStartGame.requestFocus();
                        }
                        break;
                    case ENTER:
                        if (this.btnStartGame.isFocused()) {
                            this.btnStartGame.fire();
                        } else if (btnExit.isFocused()) {
                            this.btnExit.fire();
                        }
                        break;
                    default:
                        break;
                }
            });

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    private void showPlayerModeSelection(ActionEvent actionEvent) {
        buttonBox.setVisible(false);
        Button btnOnePlayer = createButton("SinglePlay", event -> showStageSelectionScreen());
        Button btnTwoPlayer = createButton("MultiPlay", event -> showStageSelectionScreen());


        Button backToMenu = createButton("Back", event -> showInitialScreen());
        backToMenu.setMinWidth(BUTTON_WIDTH);
        backToMenu.setMaxWidth(BUTTON_WIDTH);
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

    }
    private void showStageSelectionScreen() {
        if (topRow == null && bottomRow == null) {
            topRow = new HBox(10);
            bottomRow = new HBox(10);
            topRow.setAlignment(Pos.CENTER);
            bottomRow.setAlignment(Pos.CENTER);

            String[] topStages = {"Seoul", "Athens", "Dublin", "Istanbul"};
            String[] bottomStages = {"London", "Vilnius", "Back"};

            for (String stage : topStages) {
                ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
                Button stageButton = createStageButton(stage, stageImage);
                topRow.getChildren().add(stageButton);
            }

            for (String stage : bottomStages) {
                ImageView stageImage = createStageImage(stage); // 예시로 무작위 이미지 생성
                Button stageButton = createStageButton(stage, stageImage);
                bottomRow.getChildren().add(stageButton);
            }
        }

        stageSelectionBox = new VBox(20, topRow, bottomRow);
        stageSelectionBox.setAlignment(Pos.CENTER);

        gameModeBox.setVisible(false);
        root.getChildren().removeAll(buttonBox, gameModeBox);
        root.getChildren().add(stageSelectionBox);
    }
    private ImageView createStageImage(String stageName) {
        // ramdom image load method
        InputStream stageImage = getClass().getResourceAsStream("/images/waybackHome.png");
        if (stageImage == null) {
            System.err.println("Cannot find image file");
        }
        Image backgroundImage = new Image(stageImage);

        ImageView imageView = new ImageView(backgroundImage);
        imageView.setFitHeight(170); //
        imageView.setFitWidth(270);  //
        return imageView;
    }
    private void loadGameScreen(String stageName) {

        // Create the gameplay pane and layout
        // Overall layout container
        BorderPane borderPane = new BorderPane();

        // CO2 Parameter Bar (Vertical)
        ProgressBar co2Bar = new ProgressBar(2.0); // Example value, adjust as needed
        co2Bar.setPrefWidth(60);
        co2Bar.setPrefHeight(400); // Adjust the height as needed
        co2Bar.setStyle("-fx-accent: red;"); // Set the fill color to red

        // Wrap CO2 bar in VBox to align it vertically
        VBox co2Container = new VBox(co2Bar);
        co2Container.setAlignment(Pos.CENTER);


        // Stamina Parameter
        ProgressBar staminaParameter = new ProgressBar(1); // Set to full stamina
        staminaParameter.setPrefHeight(40);
        staminaParameter.setPrefWidth(600);
        staminaParameter.setStyle("-fx-accent: yellow;"); // Set the fill color to red

        // Wrap CO2 bar in VBox to align it vertically
        VBox staminaContainer = new VBox(staminaParameter);
        staminaContainer.setAlignment(Pos.CENTER);

        // Time countdown
        Label timeLabel = new Label("Time left: 360s");
        timeLabel.setAlignment(Pos.TOP_CENTER);

        // Countdown logic
        IntegerProperty timeSeconds = new SimpleIntegerProperty(5);
        new Timeline(
                new KeyFrame(
                        Duration.seconds(timeSeconds.get()),
                        event -> gameOver(primaryStage),
                        new KeyValue(timeSeconds, 0)
                )
        ).play();

        timeSeconds.addListener((obs, oldVal, newVal) -> {
            timeLabel.setText("Time left: " + newVal + "s");
        });
        timeLabel.setAlignment(Pos.CENTER);

        // Placeholder for the map
        Label mapPlaceholder = new Label("Make a map ,Eamonn you Lazy Ass");
        mapPlaceholder.setPrefSize(1200, 800);
        mapPlaceholder.setAlignment(Pos.CENTER);
        mapPlaceholder.setStyle("-fx-background-color: lightgrey; -fx-border-color: black;");

        // Add all to the layout

        // Add Stage name and Time above and below the map
        VBox mapBox = new VBox( timeLabel,mapPlaceholder,staminaParameter);
        mapBox.setAlignment(Pos.CENTER);

        // Add all to the layout
        borderPane.setCenter(mapBox);
        borderPane.setLeft(co2Container); // CO2 bar on the left side of the map


        // Set this layout in the scene
        Scene scene = new Scene(borderPane, 1496, 1117);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Welcome To "+ stageName);
        primaryStage.show();

    }

    private Button createStageButton(String stage, ImageView stageImage) {
        Button stageButton = new Button(stage);
        stageButton.setGraphic(stageImage);
        stageButton.setContentDisplay(ContentDisplay.TOP);
        stageButton.setOnAction(event -> selectStage(stage));
        return stageButton;
    }
    private void selectStage(String stageName) {
        // Here, you would implement what happens when a stage is selected
        // For example, you might load the game scene for the selected stage
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        System.out.println("Stage selected: " + stageName);

        String musicFilePath;
        switch (stageName) {
            case "Seoul":
                musicFilePath = "path/to/seoul/song.mp3";
                break;
            case "Athens":
                // Load Athens stage
                break;
            case "Dublin":
                // Load Dublin stage
                break;
            case "Istanbul":
                // Load Istanbul stage
                break;
            case "London":
                // Load London stage
                break;
            case "Vilnius":
                // Load Vilnius stage
                break;
            case "Back":
                // Go back to the previous screen
                showPlayerModeSelection(null);
                break;
            default:
                // Handle default case if necessary
                break;
        }
        Media gameMusic = new Media(new File("src/main/resources/music/Merry_go.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(gameMusic);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Set the music to loop continuously
        mediaPlayer.play(); // Start playing the new background music

        // This is where you would transition to the actual game play scene
        // For now, just printing out the selection
        System.out.println("You have selected the stage: " + stageName);

        // TODO: Implement the transition to the game play scene for the selected stage
        // You might want to hide the stage selection screen and display the game screen, like so:
        stageSelectionBox.setVisible(false);
        loadGameScreen(stageName);
    }
    private void setupKeyControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DOWN) {
                if (this.btnStartGame.isFocused()) {
                    this.btnExit.requestFocus();
                }
            } else if (event.getCode() == KeyCode.UP) {
                if (this.btnExit.isFocused()) {
                    this.btnStartGame.requestFocus();
                }
            }
        });
    }

    private String normalButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: rgba(255, 255, 240, 0.7); -fx-text-fill: black;";
    }

    private String focusedButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: dodgerblue; -fx-text-fill: white;";
    }

    private void startGame(int playerMode) {
        // Logic to start the game
        System.out.println("Starting " + playerMode + "-player mode");
        // Remove player selection screen and load the main game screen
        // Hide the game mode selection box and show the initial button box again
        gameModeBox.setVisible(false);
        buttonBox.setVisible(true);
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the music when the application is closed
        }
    }
    private Button createButton(String text, EventHandler<ActionEvent> action) {
        Button button = new Button(text);
        button.setMinWidth(BUTTON_WIDTH);
        button.setMaxWidth(BUTTON_WIDTH);
        button.setStyle(normalButtonStyle());
        button.setOnAction(action);
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
    private void setBackground(Stage stage, String imagePath) {
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            throw new IllegalStateException("Cannot find image file");
        }
        Image backgroundImage = new Image(is);
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setPreserveRatio(false);
        backgroundImageView.fitWidthProperty().bind(stage.widthProperty());
        backgroundImageView.fitHeightProperty().bind(stage.heightProperty());
        root.getChildren().add(backgroundImageView);
    }

    private void showInitialScreen() {
        // 다른 UI 요소들을 숨깁니다.
        if (gameModeBox != null) {
            gameModeBox.setVisible(false);
        }
        if (stageSelectionBox != null) {
            stageSelectionBox.setVisible(false);
        }

        // 초기 버튼들을 다시 보여줍니다.
        buttonBox.setVisible(true);

        // root에서 필요 없는 요소들을 제거합니다. 선택적으로 사용
        root.getChildren().removeAll(gameModeBox, stageSelectionBox);

        // root에 buttonBox가 이미 포함되어 있지 않다면 다시 추가합니다.
        if (!root.getChildren().contains(buttonBox)) {
            root.getChildren().add(buttonBox);
        }
    }
    private void gameOver(Stage primaryStage) {
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
    public static void main(String[] args) {
        launch(args);
    }
}
