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
import org.example.sharedmobilityfxproject.view.GameView;

import java.util.ArrayList;
import java.util.Objects;


public class MainController {
    private GameController gameController;
    private SceneController sceneController;
    public GameView gameView;

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

    // ****Carbon footprint****
    int carbonFootprint = 0;
    Label carbonFootprintLabel; // Label to display carbon footprint

    // SceneController GameController Setting
    public MainController(SceneController sceneController, GameView gameView) {
        this.gameView = gameView;
        this.sceneController = sceneController;
        this.gameController = initGameController();
        this.startGame();
    }


    private GameController initGameController() {
        Player playerUno = new Player(0, 0, 10, 1, 10, 0);
        return new GameController(sceneController, gameView, playerUno);
    }

    /**
     * Initializes the main game.
     * This method sets up the main menu, key controls, and button listeners for the game.
     * The 'Start Game' button is focused by default.
     * The 'Game Credits' button switches the scene to the game credits.
     * The 'Exit' button exits the application.
     * The 'Start Game' button switches the scene to the map selection.
     */
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


    ///CO2
    public void updateCarbonFootprintLabel() {
        carbonFootprintLabel.setText("Carbon Footprint: " + carbonFootprint);
    }


    public boolean containsPointInArray(ArrayList<int[]> array, int x, int y) {
        for (int[] coordinates : array) {
            if (coordinates[0] == x && coordinates[1] == y) {
                return true;
            }
        }
        return false;
    }

}
