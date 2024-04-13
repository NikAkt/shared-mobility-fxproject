package org.example.sharedmobilityfxproject.controller;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.view.GameView;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class SceneController {
    public GameController gameController;
    private Stage stage;
    private Scene scene;
//    private Parent root;


    public SceneController(Stage stage) {
        this.stage = stage;
        this.scene = new Scene(new StackPane(), 1496, 1117); // 기본 Scene 설정

        stage.setScene(this.scene);
    }
    public void initFirstScene() { switchToMainMenu();}

    public void switchToMainMenu() {
        GameView mainMenuView = new GameView(this, "MainMenu",stage);
        gameController = new GameController(mainMenuView);
        stage.setScene(mainMenuView.getScene());
        stage.show();
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
