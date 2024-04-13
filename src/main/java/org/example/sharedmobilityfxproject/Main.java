package org.example.sharedmobilityfxproject;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.example.sharedmobilityfxproject.controller.GameController;
import org.example.sharedmobilityfxproject.controller.MainController;
import org.example.sharedmobilityfxproject.controller.SceneController;
import org.example.sharedmobilityfxproject.model.Player;
import org.example.sharedmobilityfxproject.view.GameView;

// Main class extends Application for JavaFX application
public class Main extends Application {

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            GameView gameView = new GameView(primaryStage);
            SceneController sceneController = new SceneController(gameView);
            // This is for main menu commenting until GYUWON FIXES
//            MainController mainController = new MainController(sceneController, gameView);

            // Initialise Player
            Player playerUno = new Player(0,0,10,1,10,0);

            // Pass every control to GameController it initializes directly the game auto
            GameController gameController = new GameController(sceneController, gameView, playerUno);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
