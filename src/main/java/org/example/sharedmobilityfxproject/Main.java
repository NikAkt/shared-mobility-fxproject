package org.example.sharedmobilityfxproject;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) {
        // Create a Player object
        org.example.sharedmobilityfxproject.Player player = new org.example.sharedmobilityfxproject.Player(0, 0);
        Application.launch(org.example.sharedmobilityfxproject.Mapper.class, args);
        // Print initial player position
        System.out.println("Initial player position: (" + Player.getCoordX() + ", " + Player.getCoordY() + ")");

        // Move the player
        Player.moveRight();
        Player.moveDown();

        // Print new player position
        System.out.println("New player position: (" + Player.getCoordX() + ", " + Player.getCoordY() + ")");
    }
}
