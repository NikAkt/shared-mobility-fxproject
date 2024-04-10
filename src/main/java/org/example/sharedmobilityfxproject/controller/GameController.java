package org.example.sharedmobilityfxproject.controller;
import org.example.sharedmobilityfxproject.controller.GameController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import org.example.sharedmobilityfxproject.Main;
import org.example.sharedmobilityfxproject.model.*;
import org.example.sharedmobilityfxproject.model.tranportMode.Bus;
import org.example.sharedmobilityfxproject.view.GameView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameController {

    public Grid grid;
    private static final String GEM_COLLECT_SOUND = "/music/gem_collected.mp3";    // Grid dimensions and window dimensions
    public static GameView gameView;
    // ****JavaElement****
    public Stage primaryStage;
    public Button btnStartGame;
    public VBox gameModeBox;
    public Button btnExit;
    public VBox buttonBox;
    public VBox imgBox;
    public StackPane root;
    public static Label gemCountLabel;

    //****FXML ****
    @FXML
    public ProgressBar progressBar;

    int co2Points = 0;
    int staminaPoints = 0;

    @FXML
    public Text text;
    // Boolean flag to control hover cursor visibility
    boolean showHoverCursor = true;

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

    public GameController() {
    }

    // Label to keep track of gem count
    @FunctionalInterface
    public interface GemCollector {
        void collectGem();
    }

    // ****Carbon footprint****
    int carbonFootprint = 0;
    Label carbonFootprintLabel; // Label to display carbon footprint

    //Game Start initialise method
    public void startGame(Stage primaryStage) {
        // Start game logic here
        System.out.println("Game Started");
        Grid grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT); // Grid 객체 생성
        gameView = new GameView(grid);
        gameView.showInitialScreen(primaryStage);
    }

    public void setupKeyControls(Scene scene) {
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

    public Button createStageButton(String stage, ImageView stageImage, VBox stageSelectionBox, VBox gameModeBox, StackPane root, Stage actionEvent, MediaPlayer mdv) {
        gameView = new GameView(grid);
        Button stageButton = new Button(stage);
        if (!stage.equals("Dublin")) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-1); // 채도를 -1로 설정하여 흑백으로 만듦
            stageImage.setEffect(colorAdjust);

            Label xMark = new Label("X");
            xMark.setFont(new Font("Arial", 100)); // "X"의 폰트와 크기 설정
            xMark.setStyle("-fx-text-fill: red;"); // "X"의 색상 설정

            // 버튼의 그래픽을 스테이지 이미지와 "X" 마크로 설정
            StackPane buttonGraphic = new StackPane();
            buttonGraphic.getChildren().addAll(stageImage, xMark);
            stageButton.setGraphic(buttonGraphic);
        } else {
            stageButton.setGraphic(stageImage);
        }

        stageButton.setContentDisplay(ContentDisplay.TOP);
        stageButton.setOnAction(event -> gameView.selectStage(stage, stageSelectionBox, gameModeBox, root, actionEvent, mdv));
        return stageButton;
    }

    public Button createButton(String text, EventHandler<ActionEvent> action) {
        Button button = new Button(text);
        if (gameView.btnFont != null) {
            button.setFont(gameView.btnFont);
        } else {
            System.out.println("Failed to load custom font. Using default font.");
        }
        button.setMinWidth(BUTTON_WIDTH);
        button.setMaxWidth(BUTTON_WIDTH);
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

    public String normalButtonStyle() {
        return "-fx-font-family: 'blueShadow'; -fx-font-size: 24px; -fx-background-color: rgba(255, 255, 240, 0.7); -fx-text-fill: black;";
    }

    public String focusedButtonStyle() {
        return "-fx-font-family: 'blueShadow'; -fx-font-size: 24px; -fx-background-color: dodgerblue; -fx-text-fill: white; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";
    }

///Transportation

    /**
     * Hail a taxi and change the player's appearance to yellow.
     */


    ///CO2
    public void updateCarbonFootprintLabel() {
        carbonFootprintLabel.setText("Carbon Footprint: " + carbonFootprint);
    }

    public static void increaseGemCount() {
        gameView.gemCountLabel = new Label();
        updateGemCountLabel();
        gemCount++;
        gameView.gemCountLabel.setText("Gem Count: " + gemCount);
    }

//    public static void updateGemCountLabel() {
//        System.out.print(gemCount);//works
//        gameView.gemCountLabel.setText("Gem Count: " + gemCount); //null
//    }


    public boolean containsPointInArray(ArrayList<int[]> array, int x, int y) {
        for (int[] coordinates : array) {
            if (coordinates[0] == x && coordinates[1] == y) {
                return true;
            }
        }
        return false;
    }

    // Method to update the gem count label
    public static void updateGemCountLabel() {
        gemCountLabel.setText("Gem Count: " + gemCount);
    }

    // Method to play the gem collect sound
    void playGemCollectSound() {
        Media sound = new Media(Objects.requireNonNull(getClass().getResource(GEM_COLLECT_SOUND)).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        // Release resources after sound finishes playing3211
        mediaPlayer.setOnEndOfMedia(mediaPlayer::dispose);
    }

    // Method to update the carbon footprint label
//    private void updateCarbonFootprintLabel() {
//        carbonFootprintLabel.setText("Carbon Footprint: " + carbonFootprint);
//    }

}


