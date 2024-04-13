package org.example.sharedmobilityfxproject;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import javafx.util.Duration;
import org.example.sharedmobilityfxproject.controller.GameController;
import org.example.sharedmobilityfxproject.controller.KeyboardActionController;
import org.example.sharedmobilityfxproject.controller.SceneController;
import org.example.sharedmobilityfxproject.model.*;
import org.example.sharedmobilityfxproject.model.tranportMode.Bus;
import org.example.sharedmobilityfxproject.model.tranportMode.Taxi;
import org.example.sharedmobilityfxproject.view.GameView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Main class extends Application for JavaFX application
public class Main extends Application {

    public SceneController sceneController;
    public GameController gameController;

    // Label to keep track of total carbon footprint
    Label carbonFootprintLabel; // Label to display carbon footprint


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            GameView gameView = new GameView(primaryStage);
            SceneController sceneController = new SceneController(gameView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
