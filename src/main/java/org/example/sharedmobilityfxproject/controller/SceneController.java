package org.example.sharedmobilityfxproject.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.view.GameView;

public class SceneController {
    private static GameView gameView;
    public MainController mainController;
//    private Parent root;


    public SceneController(GameView gameView) {
        this.gameView = gameView;
    }

    public void initMainMenu() {
        System.out.println("initMainMenu");
        gameView.setupMainMenu();
        gameView.getPrimaryStage().setScene(gameView.getScene());
        gameView.getPrimaryStage().setTitle("WayBackHome :)");
        gameView.getPrimaryStage().show();

    }
    public static void switchToGameCredits(){
        System.out.println("switchToGameCredits in SceneController");
        gameView.showCredit();
    }

    public  void switchStageChoose() {
        System.out.println("SwitchStageChoose in SceneController");
        gameView.showStageSelectionScreen();
        gameView.getPrimaryStage().setScene(gameView.getScene());
        gameView.getPrimaryStage().setTitle("WayBackHome :)");
        gameView.getPrimaryStage().show();
    }

    public  void switchToGameScene(){
        System.out.println(" switchToGameScene in SceneController");
        gameView.selectStage();

    }
//    public void switchToStageChoose() {
//        GameView mainMenuView = new GameView(this, "StageChoose",stage);
//        stage.setScene(mainMenuView.getScene());
//    }
//    public void switchToGameView() {
//        GameView gameView = new GameView(this, "GamePlay",stage);
//        stage.setScene(gameView.getScene());
//    }
//    public void switchToGameOver() {
//        GameView gameOverView = new GameView(this, "GameOver",stage);
//        stage.setScene(gameOverView.getScene());
//    }


//    public void loadGameMap(ActionEvent event) throws IOException {
//        System.out.println("loadGameMap");
//        root = FXMLLoader.load(getClass().getResource("mainMap.fxml"));
//        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//
//    }
}
