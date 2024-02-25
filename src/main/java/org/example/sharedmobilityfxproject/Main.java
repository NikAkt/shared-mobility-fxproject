package org.example.sharedmobilityfxproject;
import javafx.application.Application;
import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.sharedmobilityfxproject.model.Map;
import org.example.sharedmobilityfxproject.model.Player;

public class Main extends Application{
    public static void main(String[] args) {
        // Create a Player object
        Player player = new Player(0, 0);
        Application.launch(Map.class, args);
        // Print initial player position
        System.out.println("Initial player position: (" + Player.getCoordX() + ", " + Player.getCoordY() + ")");

        // Move the player
        Player.moveRight();
        Player.moveDown();

        // Print new player position
        System.out.println("New player position: (" + Player.getCoordX() + ", " + Player.getCoordY() + ")");
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Below is for creating another stage named stageName
//        Stage stageName = new Stage();
//        Most basic node type is Group type
        Group root = new Group(); // create the node
        Scene scene = new Scene(root); // pass the node to the scene
        stage.setScene(scene); // pass the scene to the stage
        stage.show(); // stage > scene > node
    }
}
