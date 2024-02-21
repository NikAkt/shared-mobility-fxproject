package org.example.sharedmobilityfxproject;
import javafx.application.Application;
import static javafx.application.Application.launch;

import org.example.sharedmobilityfxproject.model.Map;
import org.example.sharedmobilityfxproject.model.Player;

public class Main {
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
}
