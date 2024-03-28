package org.example.sharedmobilityfxproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.File;
import java.io.InputStream;
import javafx.scene.input.KeyCode;

public class Main extends Application {
    private Button btnStartGame;
    private Button btnExit;
    private MediaPlayer mediaPlayer;
    //Static Box size setting
    private static final double BUTTON_WIDTH = 200;
    private Stage primaryStage;
    private VBox buttonBox;
    private VBox gameModeBox;
    private StackPane root;
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
            Scene scene = new Scene(root, 1082, 1117); // Use the same size as the image for a full background
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

        Button btnOnePlayer = createButton("1-Player Game", event -> startGame(1));
        btnOnePlayer.setMinWidth(BUTTON_WIDTH);
        btnOnePlayer.setMaxWidth(BUTTON_WIDTH);
        btnOnePlayer.setStyle("-fx-font-size: 24px;");
        btnOnePlayer.setOnAction(event -> startGame(1));

        Button btnTwoPlayer = createButton("2-Player Game", event -> startGame(2));
        btnTwoPlayer.setMinWidth(BUTTON_WIDTH);
        btnTwoPlayer.setMaxWidth(BUTTON_WIDTH);
        btnTwoPlayer.setStyle("-fx-font-size: 24px;");
        btnTwoPlayer.setOnAction(event -> startGame(2));

        Button backToMenu = createButton("Back", event -> startGame(2));
        backToMenu.setMinWidth(BUTTON_WIDTH);
        backToMenu.setMaxWidth(BUTTON_WIDTH);
        backToMenu.setStyle("-fx-font-size: 24px;");

        // Create the game mode selection box if not already created
        if (gameModeBox == null) {
            gameModeBox = new VBox(20, btnOnePlayer, btnTwoPlayer);
            gameModeBox.setAlignment(Pos.CENTER);
        }
        // Add the game mode box to the root stack pane, making it visible
        if (!root.getChildren().contains(gameModeBox)) {
            root.getChildren().add(gameModeBox);
        }

        // Make the game mode selection box visible
        gameModeBox.setVisible(true);

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
    public static void main(String[] args) {
        launch(args);
    }
}
