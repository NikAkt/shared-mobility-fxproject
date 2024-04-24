package org.example.sharedmobilityfxproject.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.view.GameView;

public class SceneController {
    public static GameView gameView;
    public MainController mainController;
//    private Parent root;


    public SceneController(GameView gameView) {
        this.gameView = gameView;
    }

    public static void isGoingToNext() {
        if (gameView != null) {
            System.out.println("isGoingToNext in SceneController");
            gameView.showStageSelectionScreen();
            gameView.getPrimaryStage().setScene(gameView.getScene());
            gameView.getPrimaryStage().setTitle("WayBackHome :)");
            gameView.getPrimaryStage().show();
        } else {
            System.out.println("GameView is not initialized");
        }
    }

    public static void loadGameSave() {
        System.out.println("loadGameSave in SceneController");
        gameView.loadGameSaveWindow();
    }

    /**
     * Initializes the main menu of the game.
     * This method sets up the main menu, sets the scene to the main menu, sets the title of the primary stage, and shows the primary stage.
     */
    public void initMainMenu() {
        System.out.println("initMainMenu");
        gameView.setupMainMenu();
        gameView.getPrimaryStage().setScene(gameView.getScene());
        gameView.getPrimaryStage().setTitle("WayBackHome :)");
        gameView.getPrimaryStage().show();

    }

    /**
     * Switches the scene to the game credits.
     * This method is typically called when the 'Game Credits' button is clicked in the main menu.
     */
    public static void switchToGameCredits(){
        System.out.println("switchToGameCredits in SceneController");
        gameView.showCredit();
    }

    /**
     * Switches the scene to the stage selection screen.
     * This method displays the stage selection screen, sets the scene to the stage selection screen, sets the title of the primary stage, and shows the primary stage.
     */
    public void switchStageChoose() {
        System.out.println("SwitchStageChoose in SceneController");
        gameView.showStageSelectionScreen();
        gameView.getPrimaryStage().setScene(gameView.getScene());
        gameView.getPrimaryStage().setTitle("WayBackHome :)");
        gameView.getPrimaryStage().show();
    }


    public void mapClearCheck(String msg) {
        gameView.showStageAlert(msg);
    }

    /**
     * Initializes the game scene for a specific stage.
     * This method sets up the game scene for the provided stage, sets the scene to the game scene, sets the title of the primary stage, and shows the primary stage.
     *
     * @param stageName The name of the stage for which the game scene is to be initialized.
     */
    public void initGameScene(String stageName) {
        System.out.println("initGameScene in SceneController");
        gameView.setupGameScene(stageName);

        gameView.getPrimaryStage().setScene(gameView.getScene());
        gameView.getPrimaryStage().setTitle("WayBackHome :)");
        gameView.getPrimaryStage().show();
    }

    public void increaseCo2GaugeUpdate(Double co2Amount){
        gameView.increaseCo2Gauge(co2Amount);
    }

    public double getCo2Gauge() {
        return gameView.getCo2Gauge();
    }

    public void setCo2Gauge(double co2Gauge) {
        gameView.setCo2Gauge(co2Gauge);
    }

    public void missionFail() {
        gameView.gameFail();
        gameView.gameOverFlag = true;
    }
}
