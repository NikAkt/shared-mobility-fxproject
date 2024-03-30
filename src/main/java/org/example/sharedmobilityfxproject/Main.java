package org.example.sharedmobilityfxproject;
import org.example.sharedmobilityfxproject.controller.KeyboardActions;
import org.example.sharedmobilityfxproject.model.Grid;
import org.example.sharedmobilityfxproject.model.Cell;
import org.example.sharedmobilityfxproject.model.Obstacle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    boolean showHoverCursor = true;

    private static final int ROWS = 30;
    private static final int COLUMNS = 60;
    private static final double WIDTH = 800;
    private static final double HEIGHT = 600;

    // Player (will be implemented)
//    private Point player;

    // Obstacles
    // List to keep track of all obstacles
    private List<Obstacle> obstacles;

//    removed from scene
//    ImageView imageView = new ImageView( new Image( "https://upload.wikimedia.org/wikipedia/commons/c/c7/Pink_Cat_2.jpg"));


    @Override
    public void start(Stage primaryStage) {
        try {
            StackPane root = new StackPane();
            Scene scene = new Scene(root);

            // Settings
            Image icon = new Image(String.valueOf(getClass().getResource("/images/icon.png")));
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("Shared Mobility Application");
            primaryStage.setWidth(WIDTH);
            primaryStage.setHeight(HEIGHT);
            primaryStage.setResizable(false);
//            primaryStage.setFullScreen(true);
//            primaryStage.setFullScreenExitHint("Press esc to minimize !");

            // create grid
            Grid grid = new Grid(COLUMNS, ROWS, WIDTH, HEIGHT);

            KeyboardActions ka = new KeyboardActions(grid);
            // fill grid
            for (int row = 0; row < ROWS; row++) {
                for (int column = 0; column < COLUMNS; column++) {

                    Cell cell = new Cell(column, row);

                    ka.setupKeyboardActions(scene);

                    grid.add(cell, column, row);
                }
            }

            // Initialize Obstacles
            obstacles = new ArrayList<>();
            obstacles.add(new Obstacle(grid, 5, 5));
            obstacles.add(new Obstacle(grid, 10, 5));
            obstacles.add(new Obstacle(grid, 5, 10));

            // Initialize currentCell after the grid has been filled
            ka.currentCell = grid.getCell(0, 0);

            root.getChildren().addAll(grid);

            // create scene and set to stage
            scene.getStylesheets().add(getClass().getResource("/application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


}