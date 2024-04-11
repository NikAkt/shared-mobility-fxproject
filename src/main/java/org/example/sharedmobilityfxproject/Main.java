package org.example.sharedmobilityfxproject;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.example.sharedmobilityfxproject.controller.GameController;
import org.example.sharedmobilityfxproject.controller.KeyboardActionController;
import org.example.sharedmobilityfxproject.controller.SceneController;
import org.example.sharedmobilityfxproject.model.Player;
import org.example.sharedmobilityfxproject.view.GameView;

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
            System.out.println("Game Started");

            Group root = new Group();
            Scene scene = new Scene(root,1496, 1117);
            GameView gameView = new GameView();
            gameView.showInitialScreen(primaryStage);
            gameController = new GameController(gameView,scene,primaryStage);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


/////////////////////////////////////////////////////////////////////
