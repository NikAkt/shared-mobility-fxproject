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

    public void moveBusTowardsBusStop(Grid grid, Bus bus, busStop stop, KeyboardActionController ka, Player playerUno) {
        // Calculate the Manhattan distance for both possible next steps
        int distanceIfMoveX = Math.abs((bus.getX()) - stop.getX()) ;
        int distanceIfMoveY = Math.abs(bus.getY() - stop.getY());
//        System.out.println("--------------------");
//        System.out.println(stop.getX()+"   "+ stop.getY());

//        System.out.println(distanceIfMoveX+"   "+distanceIfMoveY);
        if ((bus.getX()<stop.getX()||bus.getX()>stop.getX())&&bus.flagMove==0 ) {
//            System.out.println("----------- moving x ---------");
            // Move horizontally towards the bus stop, if not blocked
            int newX = bus.getX() + (bus.getX() < stop.getX() ? 1 : -1);
            if (canMoveBusTo(newX, bus.getY())) {
                moveBus(grid ,bus, newX, bus.getY());
            } else if (canMoveBusTo(bus.getX(), bus.getY() + (bus.getY() < stop.getY() ? 1 : -1))) {
                // Move vertically as a fallback
                moveBus(grid ,bus, bus.getX(), bus.getY() + (bus.getY() < stop.getY() ? 1 : -1));
            }
        }

        else if (bus.getY()<stop.getY()||bus.getY()>stop.getY()){
//            System.out.println("----------- moving y ---------");
            // Move vertically towards the bus stop, if not blocked
            int newY = bus.getY() + (bus.getY() < stop.getY() ? 1 : -1);
            if (canMoveBusTo(bus.getX(), newY)) {

                moveBus(grid,bus, bus.getX(), newY);
            }
            else if (canMoveBusTo(bus.getX() +1, bus.getY())) {
                // Move horizontally as a fallback
                if (bus.flagMove == 0){
                    bus.flagMove =1;
                }
                moveBus(grid,bus, bus.getX() + +1, bus.getY());
            }
        }
        //arriving at stop logic
        else if (bus.getX()==stop.getX()&&bus.getY()==stop.getY()){
            System.out.println("----------- ARRIVED..... GET THE FUCK OUT ---------");
            bus.waitASec();
            bus.list().add(bus.list().remove(0));
            System.out.println("now going towards :"+bus.nextStop());
            if(!ka.playerMovementEnabled&&playerUno.getCoordX()==bus.getX()&&playerUno.getCoordY()==bus.getY()){
                System.out.println("----------- You just got on the bus ---------");
                ka.onBus = true;

            }else if(ka.onBus){
                System.out.println("----------- You arrived at  ---------"+stop);
                System.out.println("----------- Press E to get off  ---------");
                ka.onBus = true;

            }
        }
        else if (bus.getY()==stop.getY()) {
            bus.flagMove=0;
        }

    }

    private boolean canMoveBusTo(int x, int y) {
        // Implement logic to check if the bus can move to (x, y) considering obstacles
        // Return true if it can move, false if there's an obstacle
        return obstacles.stream().noneMatch(obstacle -> obstacle.getColumn() == x && obstacle.getRow() == y);
    }

    private void moveBus(Grid grid,Bus bus, int newX, int newY) {
        // Move the bus to the new position (newX, newY) on the grid
//        System.out.println("BUS MOVING TO :  "+newX+"  "+newY+". GET OUT THE FUCKING WAY");
//        int x = bus.getX();
//        int y = bus.getY();
        //Cell cell = grid.getCell(x,y);

        grid.moveCell(bus, newX, newY);

        bus.setX(newX);
        bus.setY(newY);
        //grid.add(cell,cell.getColumn(),cell.getRow());

    }


    public void printArrayContents() {
        System.out.println("Obstacle Coordinates:");
        for (int[] coordinates : obstacleCoordinates) {
            System.out.println("X: " + coordinates[0] + ", Y: " + coordinates[1]);
        }

        System.out.println("Bus Stop Coordinates:");
        for (int[] coordinates : busStopCoordinates) {
            System.out.println("X: " + coordinates[0] + ", Y: " + coordinates[1]);
        }
    }

    // Place the gem after the grid is filled and the player's position is initialized
    public void generateGems(Grid grid, int numberOfGems) {
        for (int i = 0; i < numberOfGems; i++) {
            int gemColumn;
            int gemRow;
            do {
                gemColumn = (int) (Math.random() * COLUMNS);
                gemRow = (int) (Math.random() * ROWS);
            } while (containsPointInArray(busStopCoordinates, gemColumn, gemRow) || containsPointInArray(obstacleCoordinates, gemColumn, gemRow));

            Gem gem = new Gem(gemColumn, gemRow,this);
            grid.add(gem, gemColumn, gemRow);
        }
    }

    public boolean containsPointInArray(ArrayList<int[]> array, int x, int y) {
        for (int[] coordinates : array) {
            if (coordinates[0] == x && coordinates[1] == y) {
                return true;
            }
        }
        return false;
    }

    // Method to update the gem count label
    private static void updateGemCountLabel() {
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


