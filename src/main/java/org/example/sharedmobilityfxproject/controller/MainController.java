package org.example.sharedmobilityfxproject.controller;


import javafx.fxml.FXML;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.ProgressBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.model.*;
import org.example.sharedmobilityfxproject.model.tranportMode.Bicycle;
import org.example.sharedmobilityfxproject.view.GameView;

import java.util.ArrayList;
import java.util.Objects;


public class MainController implements GameOverListener {


    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    private GameController gameController;
    private SceneController sceneController;
    public GameView gameView;
    // ****JavaElement****
    public Stage primaryStage;
    public Button btnStartGame;
    public VBox gameModeBox;
    public VBox buttonBox;
    public VBox imgBox;
    public StackPane root;
    public static Label gemCountLabel;

    int co2Points = 0;
    int staminaPoints = 0;

    public static final double BUTTON_WIDTH = 200;
    public static final int ROWS = 80;
    public static final int COLUMNS = 120;
    public static final double WIDTH = 1300;
    public static final double HEIGHT = 680;
    public MediaPlayer mediaPlayer;
    public VBox stageSelectionBox;


    // Finish cell
    private Cell finishCell;
    // Boolean flag to track if the game has finished
    static boolean gameFinished = false;
    // Boolean flag to track if the player is in a taxi
    boolean hailTaxi = false;


    // ****Gem count****
    public static int gemCount = 0;


    public MainController(SceneController sceneController, GameView gameView) {
        this.gameView = gameView;
        this.sceneController = sceneController;
        this.gameController = initGameController();

        this.startGame();

    }


    private GameController initGameController() {
        Player playerUno = new Player(0, 0, 100, 1, 10);;
        return new GameController(sceneController, gameView, playerUno);
    }


    public void startGame() {
        System.out.println("MainController startGame");
        sceneController.initMainMenu();

        setupKeyControls(gameView.getScene());

        //Main Menu Buttons Listeners
        gameView.getGameStartbtn().requestFocus();
        gameView.getGameCreditbtn().setOnAction(event -> SceneController.switchToGameCredits());
        gameView.getBtnExit().setOnAction(event -> System.exit(0));
        gameView.getGameStartbtn().setOnAction(event -> this.mapSelectionScene());

    }
    /**
     * Switches the scene to the stage selection scene.
     * This method sets an action for each stage button. When a stage button is clicked, it checks if the stage has been cleared.
     * If the stage has not been cleared, it displays a message to clear the previous stages first.
     * If the stage has been cleared, it starts the game for the selected stage.
     */
    public void mapSelectionScene() {
        sceneController.switchStageChoose();
        gameView.getAllStageButtons().forEach(button -> {
            button.setOnAction(event -> {
                String stage = button.getText();
                if (!gameView.isStageCleared(stage)) {
                    sceneController.mapClearCheck("This stage has not been cleared yet.\nPlease clear the previous stages first. :)");
                } else {
                    gameController.startPlayingGame(stage);
                    System.out.println("stageName in MainController: " + stage);
                }
            });
        });
    }
    /**
     * Sets up key controls for the provided scene.
     * This method sets an action for the ENTER key. When the ENTER key is pressed, it triggers the action of the currently focused button.
     *
     * @param scene The scene for which the key controls are to be set up.
     */
    private void setupKeyControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    Button focusedButton = (Button) scene.focusOwnerProperty().get();
                    focusedButton.fire();
                    break;
            }
        });
    }

    // Label to keep track of gem count


    // ****Carbon footprint****
    int carbonFootprint = 0;
    Label carbonFootprintLabel; // Label to display carbon footprint




    public boolean containsPointInArray(ArrayList<int[]> array, int x, int y) {
        for (int[] coordinates : array) {
            if (coordinates[0] == x && coordinates[1] == y) {
                return true;
            }
        }
        return false;
    }


    // Method to play the gem collect sound
    void playGemCollectSound() {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(GEM_COLLECT_SOUND)).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();


        // Release resources after sound finishes playing3211
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }

    @Override
    public void onGameOver() {
        sceneController.switchStageChoose();
    }

    ///CO2
    public void updateCarbonFootprintLabel() {
        carbonFootprintLabel.setText("Carbon Footprint: " + carbonFootprint);
    }

}
