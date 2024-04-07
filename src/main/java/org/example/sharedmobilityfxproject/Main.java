package org.example.sharedmobilityfxproject;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import javafx.application.Application;

import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.controller.GameController;
import org.example.sharedmobilityfxproject.controller.SceneController;

// Main class extends Application for JavaFX application
public class Main extends Application {
    public SceneController sceneController;
    public GameController gameController;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
                gameController = new GameController();
                gameController.startGame(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}