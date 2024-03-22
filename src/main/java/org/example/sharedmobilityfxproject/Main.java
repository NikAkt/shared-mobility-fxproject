package org.example.sharedmobilityfxproject;

import javafx.application.Application;
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

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the image
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

            // Selection Btn Setting
            // Create buttons with a specific style
            Button btnStartGame = new Button("GAME START");
            btnStartGame.setMinWidth(BUTTON_WIDTH);
            btnStartGame.setMaxWidth(BUTTON_WIDTH);
            btnStartGame.setStyle(normalButtonStyle());
            //hover colour change
            btnStartGame.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (isNowFocused) {
                    btnStartGame.setStyle(focusedButtonStyle());
                } else {
                    btnStartGame.setStyle(normalButtonStyle());
                }
            });

            btnStartGame.setOnAction(event -> startGame());
            btnStartGame.setFocusTraversable(true);

            Button btnExit = new Button("EXIT");
            btnExit.setMinWidth(BUTTON_WIDTH);
            btnExit.setMaxWidth(BUTTON_WIDTH);
            btnExit.setStyle(normalButtonStyle());
            //hover colour change
            btnExit.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
                if (isNowFocused) {
                    btnExit.setStyle(focusedButtonStyle());
                } else {
                    btnExit.setStyle(normalButtonStyle());
                }
            });
            btnExit.setOnAction(event -> primaryStage.close()); // Close the application
            btnExit.setFocusTraversable(true);

            // Create a VBox for buttons
            VBox buttonBox = new VBox(20, btnStartGame, btnExit);
            buttonBox.setAlignment(Pos.CENTER); // Align buttons to center

            // Center the VBox in the StackPane
            StackPane.setAlignment(buttonBox, Pos.CENTER);

            StackPane root = new StackPane(backgroundImageView,buttonBox);

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

    private String normalButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: rgba(255, 255, 240, 0.7); -fx-text-fill: black;";
    }

    private String focusedButtonStyle() {
        return "-fx-font-size: 24px; -fx-background-color: dodgerblue; -fx-text-fill: white;";
    }

    private void startGame() {
        // Logic to start the game
        System.out.println("Game is starting!");
    }

    @Override
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Stop the music when the application is closed
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
