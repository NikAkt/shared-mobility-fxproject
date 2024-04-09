package org.example.sharedmobilityfxproject;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.example.sharedmobilityfxproject.controller.GameController;
import org.example.sharedmobilityfxproject.controller.SceneController;

// Main class extends Application for JavaFX application
public class Main extends Application {

    public SceneController sceneController;
    public GameController gameController;

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint

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


