package org.example.sharedmobilityfxproject;
import org.example.sharedmobilityfxproject.controller.GameController;

import javafx.application.Application;

import javafx.stage.Stage;

// Main class extends Application for JavaFX application
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            GameController gameController = new GameController();
            gameController.startGame(primaryStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }


}