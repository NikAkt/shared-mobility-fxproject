package org.example.sharedmobilityfxproject.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.view.GameView;

public class SceneController {
    private GameView gameView;
    public MainController mainController;
    private Stage stage;
    private Scene scene;
//    private Parent root;


    public SceneController(GameView gameView) {
        this.gameView = gameView;
    }

    public void initMainMenu() {
        gameView.getPrimaryStage().setTitle("Simple Game");
        gameView.getPrimaryStage().show();

    }
    public void switchToMainMenu() {

    }
    public void switchToStageChoose() {
        GameView mainMenuView = new GameView(this, "StageChoose",stage);
        stage.setScene(mainMenuView.getScene());
    }
    public void switchToGameView() {
        GameView gameView = new GameView(this, "GamePlay",stage);
        stage.setScene(gameView.getScene());
    }
    public void switchToGameOver() {
        GameView gameOverView = new GameView(this, "GameOver",stage);
        stage.setScene(gameOverView.getScene());
    }


    public void switchScene(String sceneName) {
        GameView newView = new GameView(this, sceneName,stage);
        stage.setScene(newView.getScene());
    }

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
